package com.hyx.hku.equations.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.hyx.hku.equations.activitys.HomePageActivity;

public class UIHelper {

  public static void showHome(Activity context) {
    Intent intent = new Intent(context, HomePageActivity.class);
    context.startActivity(intent);
  }
}
