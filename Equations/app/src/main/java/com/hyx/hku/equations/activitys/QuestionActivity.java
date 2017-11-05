package com.hyx.hku.equations.activitys;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
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
import com.hyx.hku.equations.model.Question;

import net.lemonsoft.lemonhello.LemonHello;
import net.lemonsoft.lemonhello.LemonHelloAction;
import net.lemonsoft.lemonhello.LemonHelloInfo;
import net.lemonsoft.lemonhello.LemonHelloView;
import net.lemonsoft.lemonhello.interfaces.LemonHelloActionDelegate;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionActivity extends AppCompatActivity {

  private TextView tv_question;
  private TextView tv_question_number;
  private EditText et_answer_x1;
  private EditText et_answer_x2;
  private TextView tv_time;
  private ViewGroup container;
  private FloatingActionButton fab;
  private Button btnSubmit;
  private Button btnNext;
  private View layout_answer_x2;
  private TextView tvResult;

  private MediaPlayer player;

  private ArrayList<Question> question_list = new ArrayList<>();

  private int index = 0;

  private Timer timer = new Timer();

  private int seconds = 0;

  private long startTime;
  private long endTime;

  private boolean next_tips = true;

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
        question_list.clear();
        finish();
      }
    });

    fab = (FloatingActionButton) findViewById(R.id.fab);
    tv_question = (TextView) findViewById(R.id.tv_question);
    tv_question_number = (TextView) findViewById(R.id.tv_question_number);
    et_answer_x1 = (EditText) findViewById(R.id.et_answer_x1);
    et_answer_x2 = (EditText) findViewById(R.id.et_answer_x2);
    tv_time = (TextView) findViewById(R.id.tv_time);
    container = (ViewGroup) findViewById(R.id.container);
    btnSubmit = (Button) findViewById(R.id.btnSubmit);
    btnNext = (Button) findViewById(R.id.btnNext);
    layout_answer_x2 = findViewById(R.id.layout_answer_x2);
    tvResult = (TextView) findViewById(R.id.tvResult);

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
          if (TextUtils.isEmpty(et_answer_x1.getText())) {
            et_answer_x1.requestFocus();
            et_answer_x1.setError("Please input your answer!", getResources().getDrawable(R.drawable.error));
            return;
          }
        } else {
          if (TextUtils.isEmpty(et_answer_x1.getText())) {
            et_answer_x1.requestFocus();
            et_answer_x1.setError("Please input your answer!", getResources().getDrawable(R.drawable.error));
            return;
          }
          if (TextUtils.isEmpty(et_answer_x2.getText())) {
            et_answer_x2.requestFocus();
            et_answer_x2.setError("Please input your answer!", getResources().getDrawable(R.drawable.error));
            return;
          }
        }

        double answer_x1 = 0.0;
        double answer_x2 = 0.0;

        try {
          answer_x1 = Double.parseDouble(et_answer_x1.getText().toString());
        } catch (NumberFormatException e) {
          et_answer_x1.requestFocus();
          et_answer_x1.setError("Invalid input!", getResources().getDrawable(R.drawable.error));
          return;
        }

        if (!isLinear()) {
          try {
            answer_x2 = Double.parseDouble(et_answer_x2.getText().toString());
          } catch (NumberFormatException e) {
            et_answer_x2.requestFocus();
            et_answer_x2.setError("Invalid input!", getResources().getDrawable(R.drawable.error));
            return;
          }
        }

        Question current_question = question_list.get(index);

        endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        current_question.setTimes(Math.round(duration * 100) / 100.0 / 1000.0);

        current_question.answer(answer_x1, answer_x2);

        if (index < 9) {
          if (current_question.isCorrect()) {
            tvResult.setText("Correct answer!" + "\n");
          } else {
            tvResult.setText("Wrong answer!" + "\n" +
                    "The correct value of X1 is: " + current_question.getSolution_x1() + "\n"
                    + "The correct value of X2 is: " + current_question.getSolution_x2());
          }
        } else if (index == 9) {
          showResult();
        }

        btnSubmit.setEnabled(false);
      }
    });

    btnNext.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(final View v) {

        if (next_tips && question_list.get(index).getQuestionState() == Question.QUESTION_STATE.GIVEN_UP) {
          LemonHello.getInformationHello("Prompt", "Are you sure you want to give up?")
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
                      if (index < 9) {
                        index++;
                        showNextQuestion();
                      }
                    }
                  }))
                  .show(QuestionActivity.this);
          next_tips = false;
        } else {
          if (index < 9) {
            index++;
            showNextQuestion();
          } else {
            showResult();
          }
        }
      }
    });

    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (player.isPlaying()) {
          player.pause();
          Snackbar.make(view, "The music is already close! ", Snackbar.LENGTH_LONG).show();
          fab.setImageResource(R.drawable.music_open);
        } else {
          player.start();
          Snackbar.make(view, "The music is already open! ", Snackbar.LENGTH_LONG).show();
          fab.setImageResource(R.drawable.music_close);
        }
      }
    });
  }

  private void setUpData() {

    player = MediaPlayer.create(this, R.raw.bg_music);

    LemonHello.getSuccessHello("Tips", "Click the SUBMIT button to submit your answers and click the next button to continue.\n\nNotes: If your answers are not integers, please round them to 2 decimal places.")
            .addAction(new LemonHelloAction("OK, I Know!", new LemonHelloActionDelegate() {
              @Override
              public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                helloView.hide();
                showNextQuestion();

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
            })).setIcon(BitmapFactory.decodeResource(getResources(), R.drawable.question))
            .show(QuestionActivity.this);
  }

  public void cancel_x1(View view) {
    et_answer_x1.setText("");
  }

  public void cancel_x2(View view) {
    et_answer_x2.setText("");
  }

  private boolean isLinear() {
    return index < 5;
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

  private void showResult() {

    int count_correct = 0;
    int count_wrong = 0;
    int count_given_up = 0;

    double total_time = 0;
    int count = 0;

    for (int i = 0; i < question_list.size(); i++) {
      Question question = question_list.get(i);
      switch (question.getQuestionState()) {
        case CORRECT:
          count_correct++;
          total_time += question.getTimes();
          count++;
          break;
        case WRONG:
          count_wrong++;
          total_time += question.getTimes();
          count++;
          break;
        case GIVEN_UP:
          count_given_up++;
          break;
      }
    }

    String content_correct = "Correct : " + count_correct;
    String content_wrong = "Wrong : " + count_wrong;
    String content_given_up = "Given up : " + count_given_up;

    double avg_time = 0.00;
    if (count > 0) {
      avg_time = total_time / count;
      avg_time = Math.round(avg_time * 100) / 100.0;
    }

    String content = content_correct + "\n" + content_wrong + '\n' + content_given_up + "\n" + "Average time : " + avg_time + "s";

    LemonHello.getSuccessHello("You have finished all the questions", content)
            .addAction(new LemonHelloAction("OK, I Know!", new LemonHelloActionDelegate() {
              @Override
              public void onClick(LemonHelloView helloView, LemonHelloInfo helloInfo, LemonHelloAction helloAction) {
                helloView.hide();
                finish();
              }
            }))
            .show(QuestionActivity.this);
  }

  private void showNextQuestion() {
    Question default_question = new Question(index);
    question_list.add(default_question);
    tv_question.setText("The equation : " + default_question.getEquationStr());
    tv_question_number.setText("Question  " + (index + 1));
    tvResult.setText("");
    btnSubmit.setEnabled(true);
    et_answer_x1.setText("");
    et_answer_x2.setText("");
    if (index >= 5) {
      layout_answer_x2.setVisibility(View.VISIBLE);
    } else {
      layout_answer_x2.setVisibility(View.GONE);
    }
    seconds = 0;
    tv_time.setText(formatTime(seconds));
    startTime = System.currentTimeMillis();
  }
}
