package com.hyx.hku.equations.activitys;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hyx.hku.equations.R;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionActivity extends AppCompatActivity {

  private TextView tv_question;
  private TextView tv_question_number;
  private EditText et_answer_x;
  private EditText et_answer_y;
  private TextView tv_time;
  private ViewGroup container;
  private FloatingActionButton fab;
  private Button btnSubmit;
  private Button btnNext;

  private int A;
  private int B;
  private int C;

  private double x;
  private double y;

  private int index = 0;

  private boolean isMusicOpen = false;

  private Timer timer = new Timer();

  private int seconds = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_question);

    setUpView();
    setListener();
    setUpData();
  }

  private void setUpView() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });

    fab = (FloatingActionButton) findViewById(R.id.fab);
    tv_question = (TextView) findViewById(R.id.tv_question);
    tv_question_number = (TextView) findViewById(R.id.tv_question_number);
    et_answer_x = (EditText) findViewById(R.id.et_answer_x);
    et_answer_y = (EditText) findViewById(R.id.et_answer_y);
    tv_time = (TextView) findViewById(R.id.tv_time);
    container = (ViewGroup) findViewById(R.id.container);
    btnSubmit = (Button) findViewById(R.id.btnSubmit);
    btnNext = (Button) findViewById(R.id.btnNext);

    ViewGroup.LayoutParams lp = container.getLayoutParams();
    lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
    lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
    container.setLayoutParams(lp);
  }

  private void setListener() {
    btnSubmit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (isLinear()) {
          if (TextUtils.isEmpty(et_answer_x.getText())) {
            et_answer_x.requestFocus();
            et_answer_x.setError("Please input your answer!", getResources().getDrawable(R.drawable.error));
            return;
          }
        } else {
          if (TextUtils.isEmpty(et_answer_x.getText())) {
            et_answer_x.requestFocus();
            et_answer_x.setError("Please input your answer!", getResources().getDrawable(R.drawable.error));
            return;
          }
          if (TextUtils.isEmpty(et_answer_y.getText())) {
            et_answer_y.requestFocus();
            et_answer_y.setError("Please input your answer!", getResources().getDrawable(R.drawable.error));
            return;
          }
        }

        calculateResult();

        if (checkResult()) {
          Snackbar.make(v, "Correct answer.", Snackbar.LENGTH_LONG).show();
        } else {
          Snackbar.make(v, "Wrong answer.", Snackbar.LENGTH_LONG).show();
        }

        if (index == 9) {
          submit();
        }
      }
    });

    btnNext.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (index < 9) {
          index++;
          tv_question.setText("The equation : " + generaterQuestion(index));
          tv_question_number.setText("Question: " + (index + 1));
        } else {
          Snackbar.make(v, "This is the last question.", Snackbar.LENGTH_LONG).show();
        }
      }
    });

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (isMusicOpen) {
          Snackbar.make(view, "The music is already close! ", Snackbar.LENGTH_LONG).show();
          isMusicOpen = false;
          fab.setImageResource(R.drawable.music_open);
        } else {
          Snackbar.make(view, "The music is already open! ", Snackbar.LENGTH_LONG).show();
          isMusicOpen = true;
          fab.setImageResource(R.drawable.music_close);
        }
      }
    });
  }

  private void setUpData() {
    tv_question.setText("The equation : " + generaterQuestion(index));

    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        seconds++;
        new Handler(Looper.getMainLooper()).post(new Runnable() {
          @Override
          public void run() {
            tv_time.setText(formatTime(seconds));
          }
        });
      }
    }, 0, 1000);
  }

  private String generaterQuestion(int index) {
    if (index < 5)
      return generaterLinearEquation();
    else if (index < 10)
      return generateQuadraticEquation();
    return "";
  }

  private String generaterLinearEquation() {
    StringBuilder question = new StringBuilder();
    Random random = new Random();

    A = random.nextInt(198) % (199) - 99;
    B = random.nextInt(198) % (199) - 99;

    if (A != 0)
      question.append(A + "X");
    if (B < 0) {
      question.append(" - " + Math.abs(B));
    } else if (B == 0) {
    } else {
      question.append(" + " + Math.abs(B));
    }
    question.append(" = 0");

    return question.toString();
  }

  private String generateQuadraticEquation() {
    StringBuilder question = new StringBuilder();
    Random random = new Random();

    A = random.nextInt(198) % (199) - 99;
    B = random.nextInt(198) % (199) - 99;
    C = random.nextInt(198) % (199) - 99;

    if (A != 0)
      question.append(A + "X^2");
    if (B < 0) {
      question.append(" - " + Math.abs(B) + "X");
    } else if (B == 0) {
    } else {
      if (A != 0)
        question.append(" + ");
      question.append(Math.abs(B) + "Y");
    }

    if (C < 0) {
      question.append(" - " + Math.abs(C));
    } else if (C == 0) {
    } else {
      question.append(" + " + Math.abs(C));
    }
    question.append(" = 0");

    return question.toString();
  }

  private void calculateResult() {
    if (isLinear()) {
      x = -((double) B / (double) A);
      x = (double) (Math.round(x * 100) / 100.0);
    } else {
      x = (-B + Math.sqrt(B * B - 4 * A * C)) / (2 * (double) A);
      y = (-B - Math.sqrt(B * B - 4 * A * C)) / (2 * (double) A);
      x = (double) (Math.round(x * 100) / 100.0);
      y = (double) (Math.round(y * 100) / 100.0);
    }
  }

  private boolean checkResult() {
    if (isLinear()) {
      if (areEqual(x, Double.parseDouble(et_answer_x.getText().toString())))
        return true;
      else
        return false;
    } else {
      if (areEqual(x, Double.parseDouble(et_answer_x.getText().toString())) && areEqual(y, Double.parseDouble(et_answer_y.getText().toString()))) {
        return true;
      } else if (areEqual(x, Double.parseDouble(et_answer_y.getText().toString())) && areEqual(y, Double.parseDouble(et_answer_x.getText().toString()))) {
        return true;
      } else {
        return false;
      }
    }
  }

  public void cancel_x(View view) {
    et_answer_x.setText("");
  }

  public void cancel_y(View view) {
    et_answer_y.setText("");
  }

  private boolean isLinear() {
    return index < 5;
  }

  boolean areEqual(double A, double B) {
    if (Math.abs(A - B) < 1e-6)
      return true;
    return false;
  }

  private String formatTime(int seconds) {
    int temp = 0;
    StringBuffer sb = new StringBuffer();
    temp = seconds / 3600;
    sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");

    temp = seconds % 3600 / 60;
    sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");

    temp = seconds % 3600 % 60;
    sb.append((temp < 10) ? "0" + temp : "" + temp);
    return sb.toString();
  }

  private void submit() {
    LemonHello.getSuccessHello("提示", "恭喜您，集成成功！")
            .addAction(new LemonHelloAction("我知道啦", new LemonHelloActionDelegate() {
              @Override
              public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                helloView.hide();
              }
            }))
            .show(QuestionActivity.this);
  }
}
