package com.example.usman.appretail;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by Fahad Tanwir on 1/30/2018.
 */

public class CustomerActivity extends Activity {

    FragmentManager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_layout);

        manager=getFragmentManager();


    }
}
