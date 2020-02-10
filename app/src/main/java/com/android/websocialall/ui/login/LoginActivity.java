package com.android.websocialall.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.text.Html;
import android.view.View;

import com.android.websocialall.R;
import com.android.websocialall.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding= DataBindingUtil.setContentView(this, R.layout.activity_login);

        String redString = getResources().getString(R.string.signin_text);
        String redString1 = getResources().getString(R.string.forget_pass);
        loginBinding.txtLoginText.setText(Html.fromHtml(redString));
        loginBinding.txtForgetText.setText(Html.fromHtml(redString1));


        loginBinding.includeLayout.toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        loginBinding.includeLayout.txtHeader.setText(getResources().getString(R.string.sign_in));
        setSupportActionBar(loginBinding.includeLayout.toolbar);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
//                .getColor(R.color.colorPrimary)));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        activitySignUpBinding.includeLayout.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));
        loginBinding.includeLayout.imgBack.setVisibility(View.INVISIBLE);
    }
}
