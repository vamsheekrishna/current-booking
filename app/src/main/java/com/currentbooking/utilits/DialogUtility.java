package com.currentbooking.utilits;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.currentbooking.R;

/**
 * Created by Satya Seshu on 20/06/20.
 */
public class DialogUtility {

    public static void getOKDialog(final Activity pContext, String titleMessage, final String pMessage) {
        if (pContext == null) return;
        pContext.runOnUiThread(() -> {
            if (!pContext.isFinishing()) {
                androidx.appcompat.app.AlertDialog.Builder builder = new
                        androidx.appcompat.app.AlertDialog.Builder(
                        pContext,
                        R.style.AlertDialog
                );
                String title = titleMessage;
                if (TextUtils.isEmpty(titleMessage)) {
                    title = pContext.getString(R.string.message);
                }
                builder.setTitle(title);
                builder.setCancelable(false);
                builder.setMessage(pMessage);
                builder.setNegativeButton(pContext.getString(R.string.close), null);
                androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                alertDialog.setOnShowListener(arg0 -> alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(pContext, R.color.button_bg)));
                if (!pContext.isFinishing()) {
                    alertDialog.show();
                }
            } else {
                Toast.makeText(pContext, pMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
