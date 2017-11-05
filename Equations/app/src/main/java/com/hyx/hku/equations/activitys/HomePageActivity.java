package com.hyx.hku.equations.activitys;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hyx.hku.equations.R;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

public class HomePageActivity extends AppCompatActivity {

  Button btn_start_quiz;
  Button btn_exit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home_page);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    btn_start_quiz = (Button) findViewById(R.id.btn_start_quiz);
    btn_start_quiz.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(HomePageActivity.this, QuestionActivity.class);
        startActivity(intent);
      }
    });

    btn_exit = (Button) findViewById(R.id.btn_exit);
    btn_exit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LemonHello.getInformationHello("Are you sure you want to quit?", "")
                .addAction(new LemonHelloAction("No", new LemonHelloActionDelegate() {
                  @Override
                  public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                    helloView.hide();
                  }
                }))
                .addAction(new LemonHelloAction("Yes", Color.RED, new LemonHelloActionDelegate() {
                  @Override
                  public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                    helloView.hide();
                    finish();
                  }
                }))
                .show(HomePageActivity.this);
      }
    });

  }
}
