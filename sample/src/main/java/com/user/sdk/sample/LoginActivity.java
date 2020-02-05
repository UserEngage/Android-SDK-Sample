package com.user.sdk.sample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.user.sdk.UserCom;
import com.user.sdk.customer.Customer;
import com.user.sdk.customer.CustomerUpdateCallback;
import com.user.sdk.customer.RegisterResponse;
import com.user.sdk.notification.UserComNotification;
import com.user.sdk.sample.notification.UserComNotificationHandler;

import java.util.UUID;

/**
 * Created by michalik on 26.08.17
 */

public class LoginActivity extends Activity {

    public static final String TAG = LoginActivity.class.getSimpleName();

    Button loginButton;
    EditText firstNameInput;
    EditText idInput;
    EditText mailInput;
    EditText lastNameInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.btn_signup);
        firstNameInput = findViewById(R.id.input_name);
        lastNameInput = findViewById(R.id.input_last_name);
        mailInput = findViewById(R.id.input_email);
        idInput = findViewById(R.id.input_password);

        setLoginListener();
        UserCom.getInstance().handleRouteFromNotification(getIntent());
        @Nullable UserComNotification notification = UserComNotification.create(getIntent().getExtras());
        if (notification != null) {
            Toast.makeText(this, "Opened from notification", Toast.LENGTH_SHORT).show();
            UserComNotificationHandler.getInstance().putNotification(notification);
        }
    }

    void setLoginListener() {
        loginButton.setOnClickListener(__ -> {
            if (isNetworkAvailable()) {
                registerUserComCustomer();
            } else {
                Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerUserComCustomer() {

        String myId = UUID.randomUUID().toString();

        Customer customer = new Customer()
                .id(myId) // id of your user
                .firstName(nullOrEmpty(firstNameInput.getText().toString()) ? "" : firstNameInput.getText().toString())
                .lastName(nullOrEmpty(lastNameInput.getText().toString()) ? "" : lastNameInput.getText().toString())
                .email(nullOrEmpty(mailInput.getText().toString()) ? "" : mailInput.getText().toString())
                .attr("attr1", "value1");

        Log.d(TAG, "registerUserComCustomer: " + customer.toFlat());

        UserCom.getInstance().register(customer, new CustomerUpdateCallback() {
            @Override
            public void onSuccess(RegisterResponse response) {
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });

        Log.d(TAG, "registerUserComCustomer: " + customer.toFlat());

        UserCom.getInstance().register(customer, new CustomerUpdateCallback() {
            @Override
            public void onSuccess(RegisterResponse response) {
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e(TAG, "onFailure: ", throwable);
            }
        });
        
        Toast.makeText(this, "Successfully sent 2 pings", Toast.LENGTH_SHORT).show();

    }

    private static boolean nullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
