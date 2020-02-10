package com.android.websocialall.ui.verify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.android.websocialall.R;
import com.android.websocialall.databinding.ActivityPhoneVerifyBinding;
import com.android.websocialall.helper.ShowMessage;
import com.android.websocialall.ui.profile.ProfileActivty;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.rilixtech.widget.countrycodepicker.Country;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;


public class PhoneVerifyActivity extends AppCompatActivity implements View.OnClickListener {

    private int currentStep = 0;
    private ActivityPhoneVerifyBinding phoneVerifyBinding;


    private static String uniqueIdentifier = null;
    private static final String UNIQUE_ID = "UNIQUE_ID";
    private static final long ONE_HOUR_MILLI = 60 * 60 * 1000;

    private static final String TAG = "FirebasePhoneNumAuth";
    private FirebaseAuth firebaseAuth;
    private String phoneNumber = "+880";

    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String phoneNumber1="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phoneVerifyBinding = DataBindingUtil.setContentView(this, R.layout.activity_phone_verify);
        mAuth = FirebaseAuth.getInstance();

        phoneVerifyBinding.stepView.setStepsNumber(3);
        phoneVerifyBinding.stepView.go(0, true);
        phoneVerifyBinding.layout1.setVisibility(View.VISIBLE);
        firebaseAuth = FirebaseAuth.getInstance();

        phoneVerifyBinding.ccp.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected(Country selectedCountry) {
                phoneNumber = "+" + selectedCountry.getPhoneCode();
            }
        });


        phoneVerifyBinding.submit1.setOnClickListener(this);
        phoneVerifyBinding.submit2.setOnClickListener(this);
        phoneVerifyBinding.submit3.setOnClickListener(this);
        phoneVerifyBinding.resendCode.setOnClickListener(this);

        mCallbacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                e.printStackTrace();
                new ShowMessage(PhoneVerifyActivity.this).showTost(e.getMessage());
                Log.d(TAG, "onVerificationFailed: "+e.getMessage());
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.submit1) {
            try {
                startVerify();
            } catch (NumberParseException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.submit2) {
            sendVerificationCode();
        } else if (id == R.id.submit3) {
            submitCode();
        }
        else if (id == R.id.resend_code) {
            resendVerificationCode();
        }
    }
    public void resendVerificationCode() {
        String resendNumber = phoneNumber+phoneVerifyBinding.phonenumber.getText().toString();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                resendNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks,
                mResendToken);
    }

    private void submitCode() {
        if (currentStep < phoneVerifyBinding.stepView.getStepCount() - 1) {
            currentStep++;
            phoneVerifyBinding.stepView.go(currentStep, true);
        } else {
            phoneVerifyBinding.stepView.done(true);
        }
        phoneVerifyBinding.spinKit.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                phoneVerifyBinding.spinKit.setVisibility(View.GONE);
                startActivity(new Intent(PhoneVerifyActivity.this, ProfileActivty.class));
                finish();
            }
        }, 3000);
    }

    private void sendVerificationCode() {
        String verificationCode = phoneVerifyBinding.pinView.getText().toString();
        if (verificationCode.isEmpty()) {
            new ShowMessage(PhoneVerifyActivity.this).showTost("Enter verification code");
        } else {
            phoneVerifyBinding.spinKit.setVisibility(View.VISIBLE);
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, verificationCode);
            signInWithPhoneAuthCredential(credential);
        }
    }

    private void startVerify() throws NumberParseException {
        String phoneNumber1 = phoneNumber + phoneVerifyBinding.phonenumber.getText().toString();
        phoneVerifyBinding.phonenumberText.setText(phoneNumber1);

        if (TextUtils.isEmpty(phoneNumber)) {
            phoneVerifyBinding.phonenumber.setError("Enter a Phone Number");
            phoneVerifyBinding.phonenumber.requestFocus();
        } else {
            if (currentStep < phoneVerifyBinding.stepView.getStepCount() - 1) {
                currentStep++;
                phoneVerifyBinding.stepView.go(currentStep, true);
            } else {
                phoneVerifyBinding.stepView.done(true);
            }
            phoneVerifyBinding.layout1.setVisibility(View.GONE);
            phoneVerifyBinding.layout2.setVisibility(View.VISIBLE);
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
            Phonenumber.PhoneNumber numberProto = phoneUtil.parse(phoneNumber1, "BD");
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneUtil.format(numberProto, PhoneNumberUtil.PhoneNumberFormat.E164), // Phone number to verify
                    60, // Timeout duration
                    TimeUnit.SECONDS, // Unit of timeout
                    PhoneVerifyActivity.this, // Activity (for callback binding)
                    mCallbacks); // OnVerificationStateChangedCallbacks
        }
    }



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            phoneVerifyBinding.spinKit.setVisibility(View.GONE);
                            if (currentStep < phoneVerifyBinding.stepView.getStepCount() - 1) {
                                currentStep++;
                                phoneVerifyBinding.stepView.go(currentStep, true);
                            } else {
                                phoneVerifyBinding.stepView.done(true);
                            }
                            phoneVerifyBinding.layout1.setVisibility(View.GONE);
                            phoneVerifyBinding.layout2.setVisibility(View.GONE);
                            phoneVerifyBinding.layout3.setVisibility(View.VISIBLE);
                        } else {
                            phoneVerifyBinding.spinKit.setVisibility(View.GONE);
                            new ShowMessage(PhoneVerifyActivity.this).showTost("Something wrong");
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                            }
                        }
                    }
                });
    }

}
