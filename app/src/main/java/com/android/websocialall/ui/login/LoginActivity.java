package com.android.websocialall.ui.login;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.websocialall.R;
import com.android.websocialall.databinding.ActivityLoginBinding;
import com.android.websocialall.helper.ConnectionChecker;
import com.android.websocialall.network.provider.RetrofitClient;
import com.android.websocialall.network.repositories.Api;
import com.android.websocialall.ui.login.models.LoginRespons;
import com.android.websocialall.ui.profile.ProfileActivty;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding loginBinding;
    ProgressDialog progressDialog;
    String edid_password, edid_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding= DataBindingUtil.setContentView(this, R.layout.activity_login);

        final Button btn_sign_in = findViewById(R.id.btn_sign_in);
        final EditText edid_email = findViewById(R.id.edid_email);
        final EditText edid_password = findViewById(R.id.edid_password);

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

        btn_sign_in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (edid_email.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Enter email", Toast.LENGTH_LONG).show();
                }else if(edid_password.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_LONG).show();
                }else{
                    signin(edid_email.getText().toString(), edid_password.getText().toString());
                }
            }
        });
    }

    public void signin(String email, String password){

        RetrofitClient.getInstance().getApi().userlogin(email, password).enqueue(new Callback<LoginRespons>() {
            @Override
            public void onResponse(Call<LoginRespons> call, Response<LoginRespons> response) {

                if (response.isSuccessful()){
                    Intent i = new Intent(LoginActivity.this, ProfileActivty.class);
                    startActivity(i);
                    Toast.makeText(LoginActivity.this, response.body().getMsg(), Toast.LENGTH_LONG).show();

                    SharedPreferences sharedPreferences = getSharedPreferences("userdetails", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Email", edid_email);
                    editor.putString("Password", edid_password);
                    editor.commit();
                }else {
                    Toast.makeText(LoginActivity.this, response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRespons> call, Throwable t) {
                Intent i = new Intent(LoginActivity.this,LoginActivity.class);
                startActivity(i);
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

}
