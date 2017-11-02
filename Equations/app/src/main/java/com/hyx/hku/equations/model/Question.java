package com.hyx.hku.equations.model;

public class Question {

  public static enum QUESTION_TYPE {
    LINEAR, QUADRATIC
  }

  private String Equation;
  private QUESTION_TYPE question_type;

  private double solution_x;
  private double solution_y;

  private double answer_x;
  private double answer_y;

  private int index;

  private boolean correct;

  public QUESTION_TYPE getQuestionType() {
    return question_type;
  }

  public boolean isCorrect() {
    return correct;
  }
}
