package com.hyx.hku.equations.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.hyx.hku.equations.R;

public class HomePageActivity extends AppCompatActivity {

  Button btn_start_quiz;

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
  }
}
