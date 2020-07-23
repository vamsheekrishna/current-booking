package com.currentbooking.ticketbooking;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.currentbooking.R;
import com.currentbooking.interfaces.CallBackInterface;
import com.currentbooking.ticketbooking.adapters.CustomSpinnerAdapter;
import com.currentbooking.utilits.DialogUtility;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.cb_api.responses.BusOperator;
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
    private List<Concession> concessionsTypeList = new ArrayList<>();
    private Concession selectedConcessionDetails;
    private CallBackInterface callBackInterface;
    private String selectedPersonType;
    private TextView tvConcessionTypeField;
    private  NumberPicker numberPickerField;
    private EditText etNameField;
    private BusOperator busOperatorDetails;


    public static AddPassengersDialogView getInstance(BusOperator busOperatorDetails, List<Concession> concessionList) {
        AddPassengersDialogView addPassengersDialog = new AddPassengersDialogView();
        Bundle extras = new Bundle();
        extras.putSerializable("ConcessionsList", (Serializable) concessionList);
        extras.putSerializable("BusOperatorDetails", busOperatorDetails);
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
            busOperatorDetails = (BusOperator) extras.getSerializable("BusOperatorDetails");
            concessionsTypeList = (List<Concession>) extras.getSerializable("ConcessionsList");
        }

        loadUIComponents(view);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        etNameField = view.findViewById(R.id.et_name_field);
        numberPickerField = view.findViewById(R.id.number_picker);
        numberPickerField.setMinValue(13);
        numberPickerField.setMaxValue(59);
        Spinner personTypesSpinnerField = view.findViewById(R.id.person_type_spinner_field);
        tvConcessionTypeField = view.findViewById(R.id.tv_concession_type_field);

        String[] personTypesItems = getResources().getStringArray(R.array.person_types_items);
        personsTypeList.addAll(Arrays.asList(personTypesItems));

        CustomSpinnerAdapter personsTypeAdapter = new CustomSpinnerAdapter(requireActivity(), personsTypeList);
        personTypesSpinnerField.setAdapter(personsTypeAdapter);

        personTypesSpinnerField.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object lSelectedItem = personTypesSpinnerField.getSelectedItem();
                if (lSelectedItem instanceof String) {
                    selectedPersonType = (String) lSelectedItem;
                    selectedConcessionDetails = null;
                    tvConcessionTypeField.setText(getString(R.string.concession_type));
                    setAgeLimit(null);
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
        tvConcessionTypeField.setOnClickListener(v -> {
            addConcessionScreen();
        });
    }

    private void setAgeLimit(Concession concessionDetails) {
        if (concessionDetails != null) {
            numberPickerField.setValue(Utils.getIntegerValueFromString(concessionDetails.getMinAgeLimit()));
            numberPickerField.setMinValue(Utils.getIntegerValueFromString(concessionDetails.getMinAgeLimit()));
            numberPickerField.setMaxValue(Utils.getIntegerValueFromString(concessionDetails.getMaxAgeLimit()));
        } else {
            if (selectedPersonType.equalsIgnoreCase(getString(R.string.adult))) {
                numberPickerField.setMinValue(Utils.getIntegerValueFromString(busOperatorDetails.getAdultMinAge()));
                numberPickerField.setMaxValue(Utils.getIntegerValueFromString(busOperatorDetails.getAdultMaxAge()));
            } else {
                numberPickerField.setMinValue(Utils.getIntegerValueFromString(busOperatorDetails.getChildMinAge()));
                numberPickerField.setMaxValue(Utils.getIntegerValueFromString(busOperatorDetails.getChildMaxAge()));
            }
        }
    }

    private void addConcessionScreen() {
        ConcessionTypeSelectionDialog concessionTypeSelectionDialog = ConcessionTypeSelectionDialog.getInstance(selectedPersonType, concessionsTypeList);
        concessionTypeSelectionDialog.setInterfaceClickListener(pObject -> {
            if (pObject instanceof Concession) {
                selectedConcessionDetails = (Concession) pObject;
                selectedConcessionDetails.setConcessionDetailsAdded(true);
                tvConcessionTypeField.setText(selectedConcessionDetails.getConcessionNM());
                setAgeLimit(selectedConcessionDetails);
            }
        });
        if (!requireActivity().isFinishing()) {
            concessionTypeSelectionDialog.show(requireActivity().getSupportFragmentManager(), ConcessionTypeSelectionDialog.class.getName());
        }
    }

    public void setInterfaceClickListener(CallBackInterface callBackInterface) {
        this.callBackInterface = callBackInterface;
    }

    private void addPassengerSelected() {
        String name = etNameField.getText().toString().trim();
        if(TextUtils.isEmpty(name)) {
            etNameField.setError(getString(R.string.name_cannot_be_empty));
            return;
        }
        if(selectedConcessionDetails == null) {
            selectedConcessionDetails = new Concession();
            selectedConcessionDetails.setConcessionDetailsAdded(false);
        }
        selectedConcessionDetails.setName(name);
        selectedConcessionDetails.setAge(numberPickerField.getValue());
        selectedConcessionDetails.setPersonType(selectedPersonType);
        callBackInterface.callBackReceived(selectedConcessionDetails);
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
