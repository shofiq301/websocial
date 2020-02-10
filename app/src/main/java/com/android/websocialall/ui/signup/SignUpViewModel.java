package com.android.websocialall.ui.signup;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.websocialall.helper.ConnectionChecker;
import com.android.websocialall.network.provider.RetrofitClient;
import com.android.websocialall.ui.signup.models.SignupResponse;
import com.android.websocialall.ui.signup.view.SignupView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<SignupResponse> signupResponseMutableLiveData;

    public SignUpViewModel() {
        this.signupResponseMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<SignupResponse> getSignupResponseMutableLiveData() {
        return signupResponseMutableLiveData;
    }

    public void setSignupResponseMutableLiveData(Context context, String email, String password, final SignupView signupView) {
        signupView.onStartLoading();
        if (ConnectionChecker.isConnected(context)){
            RetrofitClient.getInstance().getApi().getSignupRes(email, password).enqueue(new Callback<SignupResponse>() {
                @Override
                public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                    signupView.onLoadingFinished();
                    if (response.isSuccessful()){
                        signupResponseMutableLiveData.setValue(response.body());
                    }
                    else {
                        signupView.onServerError();
                    }
                }

                @Override
                public void onFailure(Call<SignupResponse> call, Throwable t) {
                    signupView.onInternetError();
                    signupView.onLoadingFinished();
                }
            });
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    signupView.onLoadingFinished();
                    signupView.onInternetError();
                }
            },100);
        }
    }
}
