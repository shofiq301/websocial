package com.android.websocialall.ui.intro.fragments.slider_2;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.websocialall.R;
import com.android.websocialall.databinding.SecondSliderFragmentBinding;
import com.android.websocialall.ui.signup.SignUpActivity;

public class SecondSlider extends Fragment implements View.OnClickListener {

    private SecondSliderViewModel mViewModel;
    private SecondSliderFragmentBinding secondSliderFragmentBinding;

    public static SecondSlider newInstance() {
        return new SecondSlider();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        secondSliderFragmentBinding= DataBindingUtil.inflate(inflater, R.layout.second_slider_fragment,container,false);
        return secondSliderFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(SecondSliderViewModel.class);
        // TODO: Use the ViewModel

        secondSliderFragmentBinding.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.btn_sign_up){
            startActivity(new Intent(getContext(), SignUpActivity.class));
            ((Activity) getActivity()).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }
    }
}
