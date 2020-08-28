package com.currentbooking.utilits.views;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.currentbooking.R;
import com.currentbooking.interfaces.CallBackInterface;
import com.currentbooking.utilits.Constants;
import com.currentbooking.utilits.LoggerInfo;

import java.util.Objects;

import static com.currentbooking.utilits.Utils.internetConnectionAvailable;

public class BaseFragment extends Fragment {

    protected Dialog progressDialog;
    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //progressDialog = new ProgressDialog(getActivity(), R.style.mySpinnerTheme);
        progressDialog = CustomLoadingDialog.getInstance(getActivity());
        //progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        //progressDialog.setCancelable(false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(!internetConnectionAvailable()) {
            try {
                AlertDialog.Builder builder =new AlertDialog.Builder(requireActivity());
                builder.setTitle("No internet Connection");
                builder.setMessage("Please turn on internet connection to continue");
                builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Objects.requireNonNull(getActivity()).onBackPressed();
                        // System.exit(0);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            } catch (Exception e) {
                Log.d("Exception", "Exception: " + e.getMessage());
            }
        }
    }


    public void showDialog(String msg) {
        try {
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(
                    requireActivity(),
                    R.style.AlertDialog
            );
            builder.setTitle(requireActivity().getString(R.string.message));
            builder.setCancelable(false);
            builder.setMessage(msg);
            builder.setNegativeButton(requireActivity().getString(R.string.close), null);
            AlertDialog alertDialog = builder.create();
            alertDialog.setOnShowListener(arg0 -> alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireActivity(), R.color.button_bg)));
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {
            LoggerInfo.errorLog("show dialog exception", e.getMessage());
        }
    }

    protected void showDialog(String title, String msg) {
        try {
            AlertDialog.Builder builder = new
                    AlertDialog.Builder(
                    requireActivity(),
                    R.style.AlertDialog
            );
            if (TextUtils.isEmpty(title)) {
                title = requireActivity().getString(R.string.message);
            }
            builder.setTitle(title);
            builder.setCancelable(false);
            builder.setMessage(msg);
            builder.setNegativeButton(requireActivity().getString(R.string.close), null);
            AlertDialog alertDialog = builder.create();
            alertDialog.setOnShowListener(arg0 -> alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireActivity(), R.color.button_bg)));
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {
            LoggerInfo.errorLog("show dialog exception", e.getMessage());
        }
    }

    protected void showDialog(String title, String msg, CallBackInterface callBackInterface) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity(), R.style.AlertDialog);
            builder.setTitle(title);
            builder.setCancelable(false);
            builder.setMessage(msg);
            builder.setNegativeButton("close", (dialogInterface, i) -> {
                callBackInterface.callBackReceived(Constants.TAG_SUCCESS);
                dialogInterface.dismiss();
            });
            AlertDialog alertDialog = builder.create();
            if (!requireActivity().isFinishing()) {
                alertDialog.show();
            }
        } catch (Exception e) {

        }
    }
}
