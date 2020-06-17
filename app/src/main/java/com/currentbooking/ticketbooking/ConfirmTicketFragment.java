package com.currentbooking.ticketbooking;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.views.BaseFragment;

import java.util.Objects;

public class ConfirmTicketFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private TextView tvConcessionCodeField;

    public ConfirmTicketFragment() {
        // Required empty public constructor
    }

    OnTicketBookingListener mListener;

    public static ConfirmTicketFragment newInstance(String param1, String param2) {
        ConfirmTicketFragment fragment = new ConfirmTicketFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingListener) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("Confirm Ticket");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        return inflater.inflate(R.layout.fragment_confirm_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView)view.findViewById(R.id.first_name)).setText(MyProfile.getInstance().getFirstName());
        ((TextView)view.findViewById(R.id.last_name)).setText(MyProfile.getInstance().getLastName());
        ((TextView)view.findViewById(R.id.mobile_no)).setText(MyProfile.getInstance().getMobileNumber());
        ((TextView) view.findViewById(R.id.email)).setText(MyProfile.getInstance().getEmail());
        AppCompatTextView dob = view.findViewById(R.id.dob);
        dob.setText(MyProfile.getInstance().getDob());
        ((TextView) view.findViewById(R.id.address1)).setText(MyProfile.getInstance().getAddress1());
        ((TextView) view.findViewById(R.id.address2)).setText(MyProfile.getInstance().getAddress2());
        ((TextView) view.findViewById(R.id.state)).setText(MyProfile.getInstance().getState());
        ((TextView) view.findViewById(R.id.pin_code)).setText(MyProfile.getInstance().getPinCode());
        view.findViewById(R.id.edit_profile).setVisibility(View.GONE);
        view.findViewById(R.id.conform).setOnClickListener(this);

        ((TextView) view.findViewById(R.id.tv_route_name_field)).setText("");
        ((TextView) view.findViewById(R.id.tv_bus_type_field)).setText("");
        ((TextView) view.findViewById(R.id.tv_bus_route_field)).setText("");
        ((TextView) view.findViewById(R.id.tv_bus_journey_start_time_field)).setText("");
        ((TextView) view.findViewById(R.id.tv_bus_journey_hours_field)).setText("");
        ((TextView) view.findViewById(R.id.tv_bus_journey_end_time_field)).setText("");
        ((TextView) view.findViewById(R.id.tv_bus_fare_price_field)).setText("");
        view.findViewById(R.id.btn_book_now_field).setVisibility(View.GONE);

        view.findViewById(R.id.confirm).setOnClickListener(this);

        Button btnConcessionCodeField = view.findViewById(R.id.btn_concession_code_field);
        LinearLayout concessionCodeLayoutField = view.findViewById(R.id.concession_code_apply_layout_field);
        ImageView ivDeleteConcessionCodeField = view.findViewById(R.id.delete_concession_code_btn_field);
        tvConcessionCodeField = view.findViewById(R.id.tv_concession_code_field);
        btnConcessionCodeField.setOnClickListener(v -> {
            btnConcessionCodeField.setVisibility(View.GONE);
            concessionCodeLayoutField.setVisibility(View.VISIBLE);
            gotoConcessionScreen();
        });
        ivDeleteConcessionCodeField.setOnClickListener(v -> {
            tvConcessionCodeField.setText("");
            ivDeleteConcessionCodeField.setVisibility(View.GONE);
            concessionCodeLayoutField.setVisibility(View.GONE);
            btnConcessionCodeField.setVisibility(View.VISIBLE);
        });
    }

    private void gotoConcessionScreen() {
        Dialog dialog = new Dialog(Objects.requireNonNull(getActivity()), R.style.Theme_AppCompat_Dialog);
        View view = View.inflate(getActivity(), R.layout.concession_list_view, null);
        dialog.setContentView(view);
        RecyclerView recyclerView = view.findViewById(R.id.concession_list_field);


        dialog.show();
    }

    @Override
    public void onClick(View v) {
        mListener.goToPayment();
    }
}