package com.android.websocialall.ui.signup.view;

public interface SignupView {
    public void onServerError();
    public void onInternetError();
    public void onStartLoading();
    public void onLoadingFinished();
}
