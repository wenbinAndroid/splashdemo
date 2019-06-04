package com.wb.start;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory;
import com.bumptech.glide.request.transition.Transition;

public class SplashActivity extends AppCompatActivity {
    private static final String IAMGE = "http://n.sinaimg" +
            ".cn/mil/8_img/upload/e1815041/783/w950h633/20190604/0ed9-hxyuaph1391886.png";
    private static final int SPLASH_TIME = 2000;
    private static final int TYPE_1 = 1;
    private static final int TYPE_2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        showAd();
    }

    private void showAd() {
        ImageView iv = findViewById(R.id.iv_ad);
        HandlerControl control = new HandlerControl(this, iv);
        control.sendEmptyMessageDelayed(TYPE_1, SPLASH_TIME);
    }


    static class HandlerControl extends Handler {
        private SplashActivity mContext;
        private ImageView iv;

        public HandlerControl(SplashActivity context, ImageView iv) {
            mContext = context;
            this.iv = iv;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == TYPE_1) {
                Glide.with(mContext).load(IAMGE)
                        .transition
                                (DrawableTransitionOptions
                                        .with(new DrawableCrossFadeFactory.Builder
                                                (500).setCrossFadeEnabled(true).build()))
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(Drawable resource,
                                                        Transition<? super Drawable> transition) {
                                //获取顶部的图层来显示广告
                                final LayerDrawable layerDrawable = (LayerDrawable) ContextCompat
                                        .getDrawable(mContext, R
                                                .drawable.main_splash);
                                //通过ID将图片写入图层
                                layerDrawable.setDrawableByLayerId(R.id.splash_top, resource);
                                //显示图层
                                iv.setImageDrawable(layerDrawable);
                                //跳转
                                sendEmptyMessageDelayed(TYPE_2, 3 * SPLASH_TIME);
                            }
                        });
            } else {
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                mContext.finish();
            }
        }
    }


}
