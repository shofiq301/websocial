package com.android.websocialall.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;

import com.android.websocialall.R;
import com.android.websocialall.databinding.ActivitySignUpBinding;
import com.android.websocialall.helper.ShowMessage;
import com.android.websocialall.ui.login.LoginActivity;
import com.android.websocialall.ui.profile.ProfileActivty;
import com.android.websocialall.ui.signup.models.SignupResponse;
import com.android.websocialall.ui.signup.view.SignupView;
import com.android.websocialall.ui.verify.PhoneVerifyActivity;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener , SignupView {

    private ActivitySignUpBinding activitySignUpBinding;
    private boolean isOkEmail = true, isPassOk=true,isOkRetypePass = true;

    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpViewModel= new ViewModelProvider(this).get(SignUpViewModel.class);
        activitySignUpBinding= DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        String redString = getResources().getString(R.string.signup_text);
        activitySignUpBinding.txtSignupText.setText(Html.fromHtml(redString));

        activitySignUpBinding.btnSignUp.setOnClickListener(this);
        activitySignUpBinding.txtSignupText.setOnClickListener(this);

        //Text handler
        activitySignUpBinding.edidEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkHandler();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        activitySignUpBinding.edidPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkHandler();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        activitySignUpBinding.edidConfirmPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkHandler();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        activitySignUpBinding.includeLayout.toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));
        activitySignUpBinding.includeLayout.txtHeader.setText(getResources().getString(R.string.sign_up1));
        setSupportActionBar(activitySignUpBinding.includeLayout.toolbar);
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources()
//                .getColor(R.color.colorPrimary)));
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);

//        activitySignUpBinding.includeLayout.toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white));
        activitySignUpBinding.includeLayout.imgBack.setVisibility(View.INVISIBLE);
//                .setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //What to do on back clicked
//                finish();
//            }
//        });

        signUpViewModel.getSignupResponseMutableLiveData().observe(this, new Observer<SignupResponse>() {
            @Override
            public void onChanged(SignupResponse signupResponse) {
                if (signupResponse!=null){
                    setDataToView(signupResponse);
                }
            }
        });
    }

    private void setDataToView(SignupResponse signupResponse) {
        if (signupResponse.getStatus().intValue()==200){
            new ShowMessage(SignUpActivity.this).showTost(signupResponse.getMsg());
            startActivity(new Intent(SignUpActivity.this, ProfileActivty.class));
            finish();
        }
        else {
            new ShowMessage(SignUpActivity.this).showTost(signupResponse.getMsg());
        }
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        if (id==R.id.btn_sign_up){
                    if (!activitySignUpBinding.edidEmail.getText().toString().equals("") &&
                            !activitySignUpBinding.edidPassword.getText().toString().equals("") && isOkRetypePass){
                sighupCall();
            }
        }
        if (id==R.id.txt_signup_text){
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }
    }

    private void sighupCall() {
        signUpViewModel.setSignupResponseMutableLiveData(SignUpActivity.this,activitySignUpBinding.edidEmail.getText().toString(),activitySignUpBinding.edidPassword.getText().toString(), SignUpActivity.this);
    }

    private void checkHandler() {
        if (!Patterns.EMAIL_ADDRESS.matcher(activitySignUpBinding.edidEmail.getText().toString()).matches() || activitySignUpBinding.edidEmail.getText().toString().equals("")) {
            activitySignUpBinding.edidEmail.setError("Please provide a valid email address");
            isOkEmail = false;
        } else
            isOkEmail = true;
        if (activitySignUpBinding.edidPassword.getText().toString().length() < 4) {
            activitySignUpBinding.edidPassword.setError("Password not strong");
            isPassOk = false;
        } else
            isPassOk= true;

        if (!activitySignUpBinding.edidPassword.getText().toString().equals(activitySignUpBinding.edidConfirmPass.getText().toString())) {
            activitySignUpBinding.edidConfirmPass.setError("Password not matched");
            isOkRetypePass = false;
        } else
            isOkRetypePass= true;
    }

    @Override
    public void onServerError() {
        new ShowMessage(this).showTost(getString(R.string.server_error_messae));
    }

    @Override
    public void onInternetError() {
        new ShowMessage(this).showTost(getString(R.string.internet_error_text));
    }

    @Override
    public void onStartLoading() {
        activitySignUpBinding.spinKit.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingFinished() {
        activitySignUpBinding.spinKit.setVisibility(View.GONE);
    }
}
