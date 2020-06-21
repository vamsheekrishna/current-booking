package com.currentbooking.ticketbooking;

import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.interfaces.CallBackInterface;
import com.currentbooking.ticketbooking.adapters.ConcessionsTypeAdapter;
import com.currentbooking.utilits.DialogUtility;
import com.currentbooking.utilits.RecyclerTouchListener;
import com.currentbooking.utilits.cb_api.responses.Concession;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Created by Satya Seshu on 21/06/20.
 */
public class ConcessionTypeSelectionDialog extends DialogFragment {

    private List<Concession> concessionsTypeList;
    private Concession selectedConcessionDetails;
    private CallBackInterface callBackInterface;
    private ConcessionsTypeAdapter concessionsTypeAdapter;

    public static ConcessionTypeSelectionDialog getInstance(List<Concession> concessionList) {
        ConcessionTypeSelectionDialog concessionTypeSelectionDialog = new ConcessionTypeSelectionDialog();
        Bundle extras = new Bundle();
        extras.putSerializable("ConcessionsList", (Serializable) concessionList);
        concessionTypeSelectionDialog.setArguments(extras);
        return concessionTypeSelectionDialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Dialog dialog = new Dialog(requireActivity(), R.style.Theme_AppCompat_Light_DarkActionBar);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(requireActivity(), R.layout.concession_type_selection_view, null);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        dialog.setOnKeyListener((dialog1, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                closeDialog();
            }
            return false;
        });

        Bundle extras = getArguments();
        if (extras != null) {
            concessionsTypeList = (List<Concession>) extras.getSerializable("ConcessionsList");
        }

        loadUIComponents(view);

        return dialog;
    }

    private void loadUIComponents(View view) {
        TextView tvTitleField = view.findViewById(R.id.tv_title_field);
        tvTitleField.setText(getString(R.string.select_concession).toUpperCase());
        view.findViewById(R.id.iv_back_arrow_field).setOnClickListener(v -> {
            closeDialog();
        });
        RecyclerView concessionsListField = view.findViewById(R.id.concession_list_field);
        concessionsListField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(requireActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()),
                R.drawable.recycler_decoration_divider)));
        concessionsListField.addItemDecoration(divider);

        concessionsListField.addOnItemTouchListener(new RecyclerTouchListener(requireActivity(), concessionsListField, new RecyclerTouchListener.ClickListener() {

            @Override
            public void onClick(View view, int position) {
                selectedConcessionDetails = concessionsTypeList.get(position);
                callBackInterface.callBackReceived(selectedConcessionDetails, "delete");
                closeDialog();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        if (concessionsTypeList != null && !concessionsTypeList.isEmpty()) {
            concessionsTypeAdapter = new ConcessionsTypeAdapter(requireActivity(), concessionsTypeList);
            concessionsListField.setAdapter(concessionsTypeAdapter);
        }

        EditText searchView = view.findViewById(R.id.search_concession_field);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString().trim();
                if (newText.length() > 0) {
                    concessionsTypeAdapter.getFilter().filter(newText);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            closeDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showErrorMessage(String message) {
        DialogUtility.getOKDialog(requireActivity(), getString(R.string.message), message);
    }

    private void closeDialog() {
        Dialog dialog = getDialog();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public void setInterfaceClickListener(CallBackInterface interfaceClickListener) {
        this.callBackInterface = interfaceClickListener;
    }
}
