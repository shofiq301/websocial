package com.android.websocialall.helper;

import android.app.Activity;
import android.content.Context;

import com.google.android.material.snackbar.Snackbar;

public class ShowMessage {

    Activity activity;

    public ShowMessage(Context context) {
        this.activity = (Activity)context;
    }

    public void showTost(String message){
        Snackbar snack = Snackbar.make(
                (((Activity) activity).findViewById(android.R.id.content)),
                message + "", Snackbar.LENGTH_SHORT);
        snack.setActionTextColor(activity.getResources().getColor(android.R.color.holo_red_light ));
        snack.show();
    }

//    public static void showToastSuccess(Context context, String message){
//        Toasty.success(context,message,Toasty.LENGTH_SHORT).show();
//    }
//    public static void showToastError(Context context, String message){
//        Toasty.error(context,message,Toasty.LENGTH_LONG).show();
//    }
}
