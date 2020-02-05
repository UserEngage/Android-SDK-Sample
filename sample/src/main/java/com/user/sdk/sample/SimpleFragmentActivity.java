package com.user.sdk.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.user.sdk.events.ScreenName;
import com.user.sdk.events.TrackArguments;

@ScreenName(name = "example_activity")
@TrackArguments
public class SimpleFragmentActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_fragment);

        @Nullable Fragment previousFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (previousFragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, SimpleFragment.newInstance("some argument"))
                    .commit();
        }
    }
}
