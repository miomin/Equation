package com.hyx.hku.equations;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class UIHelper {

	public final static String TAG = "UIHelper";

	public final static int RESULT_OK = 0x00;
	public final static int REQUEST_CODE = 0x01;

	public static void ToastMessage(Context cont, String msg) {
        if(cont == null || msg == null) {
            return;
        }
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, int msg) {
        if(cont == null || msg <= 0) {
            return;
        }
		Toast.makeText(cont, msg, Toast.LENGTH_SHORT).show();
	}

	public static void ToastMessage(Context cont, String msg, int time) {
        if(cont == null || msg == null) {
            return;
        }
		Toast.makeText(cont, msg, time).show();
	}

    public static void showHome(Activity context){
        Intent intent = new Intent(context, HomePageActivity.class);
        context.startActivity(intent);
    }
}
