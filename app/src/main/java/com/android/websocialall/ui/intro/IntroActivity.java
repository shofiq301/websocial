package com.android.websocialall.ui.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.android.websocialall.R;
import com.android.websocialall.databinding.ActivityIntroBinding;
import com.android.websocialall.ui.intro.adapters.IntroPagerAdapter;
import com.android.websocialall.ui.intro.fragments.slider3.ThirdSlider;
import com.android.websocialall.ui.intro.fragments.slider_1.FirstSlider;
import com.android.websocialall.ui.intro.fragments.slider_2.SecondSlider;
import com.android.websocialall.ui.intro.models.ScreenItem;
import com.google.firebase.FirebaseApp;

import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityIntroBinding activityIntroBinding;
    private IntroPagerAdapter introPagerAdapter;

    private List<ScreenItem> screenItems;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/sf_text_ui_bold.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityIntroBinding= DataBindingUtil.setContentView(this, R.layout.activity_intro);
        FirebaseApp.initializeApp(this);
        setIntroDatat();
        activityIntroBinding.button.setOnClickListener(this);
        activityIntroBinding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==2){
                    activityIntroBinding.button.setVisibility(View.INVISIBLE);
                }
                else {
                    activityIntroBinding.button.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void setIntroDatat() {
        introPagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        introPagerAdapter.addFragment(new FirstSlider(), "");
        introPagerAdapter.addFragment(new SecondSlider(), "");
        introPagerAdapter.addFragment(new ThirdSlider(), "");
        activityIntroBinding.viewPager.setAdapter(introPagerAdapter);
        activityIntroBinding.wormDotsIndicator.setViewPager(activityIntroBinding.viewPager);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.button){
            activityIntroBinding.viewPager.setCurrentItem(activityIntroBinding.viewPager.getCurrentItem()+1);
        }
    }
}
