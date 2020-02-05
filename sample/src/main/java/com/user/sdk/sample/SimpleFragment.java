package com.user.sdk.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.user.sdk.UserCom;
import com.user.sdk.events.ScreenName;
import com.user.sdk.events.TrackArguments;

import java.util.Objects;

@ScreenName(name = "simple_fragment")
@TrackArguments
public class SimpleFragment extends Fragment {

    private static final String ARG_SIMPLE_STRING = "ARG_SIMPLE_STRING";

    public static SimpleFragment newInstance(@Nullable String stringArgument) {
        SimpleFragment fragment = new SimpleFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ARG_SIMPLE_STRING, stringArgument);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UserCom.getInstance().trackScreen(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ((TextView) view).setText(Objects.requireNonNull(getContext()).getString(
                R.string.simpleFragment_content,
                getArgSimpleString()
        ));
    }

    @Nullable
    private String getArgSimpleString() {
        return getArguments() != null ? getArguments().getString(ARG_SIMPLE_STRING) : null;
    }
}
