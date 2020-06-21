package com.currentbooking.ticketbooking;

import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.currentbooking.R;
import com.currentbooking.interfaces.CallBackInterface;
import com.currentbooking.models.ConcessionTypeModel;
import com.currentbooking.ticketbooking.adapters.CustomSpinnerAdapter;
import com.currentbooking.utilits.DialogUtility;
import com.currentbooking.utilits.cb_api.responses.Concession;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Satya Seshu on 20/06/20.
 */
public class AddPassengersDialogView extends DialogFragment {

    private List<Object> personsTypeList = new ArrayList<>();
    private List<Object> concessionsTypeList = new ArrayList<>();
    private Concession selectedConcessionDetails;
    private CallBackInterface callBackInterface;
    private String selectedPersonType;

    public static AddPassengersDialogView getInstance(List<Concession> concessionList) {
        AddPassengersDialogView addPassengersDialog = new AddPassengersDialogView();
        Bundle extras = new Bundle();
        extras.putSerializable("ConcessionsList", (Serializable) concessionList);
        addPassengersDialog.setArguments(extras);
        return addPassengersDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        Dialog dialog = new Dialog(requireActivity(), R.style.Theme_AppCompat_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(requireActivity(), R.layout.add_passengers_view, null);
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
            concessionsTypeList = (List<Object>) extras.getSerializable("ConcessionsList");
        }

        loadUIComponents(view);
        return dialog;
    }

    private void loadUIComponents(View view) {
        Spinner personTypesSpinnerField = view.findViewById(R.id.person_type_spinner_field);
        Spinner concessionTypesSpinnerField = view.findViewById(R.id.concession_type_spinner_field);

        String[] personTypesItems = getResources().getStringArray(R.array.person_types_items);
        personsTypeList.addAll(Arrays.asList(personTypesItems));

        concessionsTypeList.add(0, getString(R.string.concession_type));

        CustomSpinnerAdapter personsTypeAdapter = new CustomSpinnerAdapter(requireActivity(), personsTypeList);
        personTypesSpinnerField.setAdapter(personsTypeAdapter);

        personTypesSpinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object lSelectedItem = personTypesSpinnerField.getSelectedItem();
                if (lSelectedItem instanceof String) {
                    selectedPersonType = (String) lSelectedItem;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        CustomSpinnerAdapter concessionsTypeAdapter = new CustomSpinnerAdapter(requireActivity(), concessionsTypeList);
        concessionTypesSpinnerField.setAdapter(concessionsTypeAdapter);

        concessionTypesSpinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object lSelectedItem = concessionTypesSpinnerField.getSelectedItem();
                if (lSelectedItem instanceof Concession) {
                    selectedConcessionDetails = null;
                    selectedConcessionDetails = (Concession) lSelectedItem;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        view.findViewById(R.id.btn_cancel_field).setOnClickListener(v -> {
            closeDialog();
        });
        view.findViewById(R.id.btn_add_field).setOnClickListener(v -> {
            addPassengerSelected();
        });
    }

    public void setInterfaceClickListener(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    private void addPassengerSelected() {
        if (selectedConcessionDetails == null) {
            showErrorMessage(getString(R.string.please_select_concession));
            return;
        }

        ConcessionTypeModel concessionTypeModel = new ConcessionTypeModel();
        concessionTypeModel.setPersonType(selectedPersonType);
        concessionTypeModel.setConcessionCode(selectedConcessionDetails.getConcessionNM());
        concessionTypeModel.setConcessionType(selectedConcessionDetails.getConcessionID());
        callBackInterface.callBackReceived(concessionTypeModel);
        closeDialog();
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
}
