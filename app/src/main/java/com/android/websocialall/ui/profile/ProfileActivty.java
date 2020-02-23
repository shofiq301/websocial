package com.android.websocialall.ui.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.android.websocialall.R;
import com.android.websocialall.databinding.ActivityProfileActivtyBinding;

public class ProfileActivty extends AppCompatActivity {

    private ActivityProfileActivtyBinding profileActivtyBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileActivtyBinding= DataBindingUtil.setContentView(this, R.layout.activity_profile_activty);


        profileActivtyBinding.includeLayout.toolbar.setTitle("Bangladesh");
        setSupportActionBar(profileActivtyBinding.includeLayout.toolbar);
    }
}
