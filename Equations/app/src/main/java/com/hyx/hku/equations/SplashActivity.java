package com.hyx.hku.equations;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.facebook.drawee.view.SimpleDraweeView;

public class SplashActivity extends AppCompatActivity {

  private SimpleDraweeView guideImage;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_splash);

    guideImage = (SimpleDraweeView) findViewById(R.id.guideImage);
    Uri uri = Uri.parse("res://" + APPString.PACKAGE_NAME + "/" + R.drawable.welcome);
    guideImage.setImageURI(uri);

    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        UIHelper.showHome(SplashActivity.this);
      }
    }, 2000);
  }
}
