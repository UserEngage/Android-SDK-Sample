package com.user.sdk.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.user.sdk.UserCom;
import com.user.sdk.events.TrackArguments;
import com.user.sdk.notification.UserComNotification;
import com.user.sdk.sample.notification.UserComNotificationHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * Created by jaroslawmichalik on 10.11.2017
 */
@TrackArguments
public class FcmTestActivity extends AppCompatActivity {

    public static final String TAG = FcmTestActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private NotificationsAdapter adapter;
    private Disposable disposable;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm  dd.MM.YYYY", Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_fcm);
        super.onCreate(savedInstanceState);

        FirebaseInstanceId.getInstance()
                .getInstanceId()
                .addOnSuccessListener(instanceIdResult ->
                        ((TextView) findViewById(R.id.activity_fcm_token_text)).setText(instanceIdResult.getToken())
                );

        adapter = new NotificationsAdapter();
        recyclerView = findViewById(R.id.activity_fcm_token_recycler);

        setListContent();
        UserCom.getInstance().trackScreen(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }

    private void setListContent() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);

        disposable = UserComNotificationHandler.getInstance().getNotifications()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        item -> {
                            adapter.add(item);
                            recyclerView.postDelayed(() -> recyclerView.smoothScrollToPosition(0), 200);
                        },
                        t -> Log.e(TAG, "setListContent: ", t)
                );
    }

    class NotificationsAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

        List<UserComNotification> items = new ArrayList<>();

        @NonNull
        @Override
        public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_card_item, parent, false);
            return new NotificationViewHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
            UserComNotification notification = items.get(position);
            holder.title.setText(notification.getTitle());
            holder.date.setText(simpleDateFormat.format(notification.getDate()));
            holder.content.setText(notification.getMessage());
            holder.rawContent.setText(notification.getRawData().toString());
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        void add(UserComNotification notificationItem) {
            items.add(0, notificationItem);
            notifyItemInserted(0);
        }
    }

    class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, content, rawContent;

        NotificationViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.notification_card_title);
            date = itemView.findViewById(R.id.notification_card_item_date);
            content = itemView.findViewById(R.id.notification_card_item_content);
            rawContent = itemView.findViewById(R.id.notification_card_item_raw_content);
        }
    }
}
