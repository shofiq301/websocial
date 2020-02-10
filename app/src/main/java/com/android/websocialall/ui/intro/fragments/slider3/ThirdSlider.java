package com.android.websocialall.ui.intro.fragments.slider3;

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
import com.android.websocialall.databinding.ThirdSliderFragmentBinding;
import com.android.websocialall.ui.signup.SignUpActivity;

public class ThirdSlider extends Fragment implements View.OnClickListener {

    private ThirdSliderViewModel mViewModel;
    private ThirdSliderFragmentBinding thirdSliderFragmentBinding;

    public static ThirdSlider newInstance() {
        return new ThirdSlider();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        thirdSliderFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.third_slider_fragment, container, false);
        return thirdSliderFragmentBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ThirdSliderViewModel.class);
        // TODO: Use the ViewModel

        thirdSliderFragmentBinding.btnGetStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_get_start) {
            getContext().startActivity(new Intent(getContext(), SignUpActivity.class));
            ((Activity) getActivity()).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            getActivity().finish();
        }
    }
}
