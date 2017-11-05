package com.hyx.hku.equations.model;

import java.util.Random;

public class Question {

  public static enum QUESTION_TYPE {
    LINEAR, QUADRATIC
  }

  public static enum QUESTION_STATE {
    CORRECT, WRONG, GIVEN_UP
  }

  private String str_equation;
  private QUESTION_TYPE question_type;
  private QUESTION_STATE question_state = QUESTION_STATE.GIVEN_UP;

  private int A = 0;
  private int B = 0;
  private int C = 0;

  private double solution_x1;
  private double solution_x2;

  private double answer_x1;
  private double answer_x2;

  private int index;

  private double times; //seconds

  public Question(int index) {
    this.index = index;
    if (index < 5)
      question_type = QUESTION_TYPE.LINEAR;
    else
      question_type = QUESTION_TYPE.QUADRATIC;

    str_equation = generaterQuestion(index);
    calculateResult();
  }

  public void answer(double x1, double x2) {
    answer_x1 = x1;
    answer_x2 = x2;
    if (checkResult()) {
      question_state = QUESTION_STATE.CORRECT;
    } else {
      question_state = QUESTION_STATE.WRONG;
    }
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
    while (A == 0) {
      A = random.nextInt(198) % (199) - 99;
    }
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

    while ((B * B - 4 * A * C) < 0) {
      A = random.nextInt(198) % (199) - 99;
      B = random.nextInt(198) % (199) - 99;
      C = random.nextInt(198) % (199) - 99;
    }

    if (A != 0)
      question.append(A + "X^2");
    if (B < 0) {
      question.append(" - " + Math.abs(B) + "X");
    } else if (B == 0) {
    } else {
      if (A != 0)
        question.append(" + ");
      question.append(Math.abs(B) + "X");
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
      solution_x1 = -((double) B / (double) A);
      solution_x1 = (double) (Math.round(solution_x1 * 100) / 100.0);
    } else {
      solution_x1 = (-B + Math.sqrt(B * B - 4 * A * C)) / (2 * (double) A);
      solution_x2 = (-B - Math.sqrt(B * B - 4 * A * C)) / (2 * (double) A);
      solution_x1 = (double) (Math.round(solution_x1 * 100) / 100.0);
      solution_x2 = (double) (Math.round(solution_x2 * 100) / 100.0);
    }
  }

  private boolean checkResult() {
    if (isLinear()) {
      if (areEqual(solution_x1, answer_x1))
        return true;
      else
        return false;
    } else {
      if (areEqual(solution_x1, answer_x1) && areEqual(solution_x2, answer_x2)) {
        return true;
      } else if (areEqual(solution_x1, answer_x2) && areEqual(solution_x2, answer_x1)) {
        return true;
      } else {
        return false;
      }
    }
  }

  private boolean areEqual(double A, double B) {
    if (Math.abs(A - B) < 1e-6)
      return true;
    return false;
  }

  public String getEquationStr() {
    return str_equation;
  }

  public boolean isLinear() {
    if (question_type == QUESTION_TYPE.LINEAR)
      return true;
    return false;
  }

  public double getSolution_x1() {
    return solution_x1;
  }

  public double getSolution_x2() {
    return solution_x2;
  }

  public boolean isCorrect() {
    return question_state == QUESTION_STATE.CORRECT;
  }

  public QUESTION_STATE getQuestionState() {
    return question_state;
  }

  public void setTimes(double duration) {
    times = duration;
  }

  public double getTimes() {
    return times;
  }
}
