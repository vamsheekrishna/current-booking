package com.currentbooking.ticketbookinghistory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.currentbooking.R;
import com.currentbooking.interfaces.CallBackInterface;
import com.currentbooking.ticketbookinghistory.adapters.PassengerDetailsAdapter;
import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.ticketbookinghistory.models.PassengerDetailsModel;
import com.currentbooking.utilits.Constants;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.UpdateTicketStatus;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.currentbooking.ticketbookinghistory.GenerateQRCode.ETIM_PRE_FIX;
import static com.currentbooking.utilits.Constants.KEY_APPROVED;

public class ViewTicketFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_TICKET_DETAILS = "TicketDetails";
    private MyTicketInfo busTicketDetails;

    // TODO: Rename and change types of parameters
    private OnTicketBookingHistoryListener mListener;
    private LinearLayout qrBaseView;
    private TextView tvBookingStatusField;

    public ViewTicketFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingHistoryListener)context;
    }

    public static ViewTicketFragment newInstance(MyTicketInfo ticketDetails) {
        ViewTicketFragment fragment = new ViewTicketFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TICKET_DETAILS, ticketDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            busTicketDetails = (MyTicketInfo) getArguments().getSerializable(ARG_TICKET_DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_ticket, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.view_qr_code).setOnClickListener(this);
        view.findViewById(R.id.scan_qr_code).setOnClickListener(this);
        qrBaseView = view.findViewById(R.id.qr_base_view);
        loadUIComponents(view);
    }

    private void loadUIComponents(View view) {
        RecyclerView passengerListRecyclerField = view.findViewById(R.id.passenger_list_recycler_field);
        passengerListRecyclerField.setHasFixedSize(false);

        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(requireActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()),
                R.drawable.recycler_decoration_divider)));
        passengerListRecyclerField.addItemDecoration(divider);
        TextView tvBusRouteNameField = view.findViewById(R.id.tv_route_name_field);
        TextView tvBusRouteField = view.findViewById(R.id.tv_bus_route_field);
        TextView tvJourneyStartTimeField = view.findViewById(R.id.tv_bus_journey_start_time_field);
        TextView tvJourneyEndTimeField = view.findViewById(R.id.tv_bus_journey_end_time_field);
        TextView tvJourneyHrsField = view.findViewById(R.id.tv_bus_journey_hours_field);

        String busRouteName = String.format("%s %s", busTicketDetails.getOperator_name().toUpperCase(), busTicketDetails.getBus_service_no());
        String ticketNo = String.format("%s %s", getString(R.string.ticket_number), busTicketDetails.getOrder_id());
        String busRouteAndTicketNo = String.format("%s\n%s", busRouteName, ticketNo);
        tvBusRouteNameField.setText(busRouteAndTicketNo);

        String busRoute = String.format("%s to %s", busTicketDetails.getFrom_stop(), busTicketDetails.getTo_stop());
        tvBusRouteField.setText(busRoute);
        tvJourneyStartTimeField.setText(busTicketDetails.getStart_time());
        tvJourneyEndTimeField.setText(busTicketDetails.getDrop_time());
        String journeyHrs = String.format("%s Hrs", busTicketDetails.getHours());
        tvJourneyHrsField.setText(journeyHrs);

        List<PassengerDetailsModel> passengerDetailsList = busTicketDetails.getPassengerDetailsList();

        PassengerDetailsAdapter passengerDetailsAdapter = new PassengerDetailsAdapter(requireActivity(), passengerDetailsList);
        passengerListRecyclerField.setAdapter(passengerDetailsAdapter);

        TextView tvTotalPersonsFareField = view.findViewById(R.id.tv_total_persons_bus_fare_price_field);


        TextView tvServiceChargeOrGstField = view.findViewById(R.id.tv_total_persons_service_charge_or_gst_field);
        TextView tvTotalUpdatedFareField = view.findViewById(R.id.tv_total_persons_total_fare_field);
        tvTotalUpdatedFareField.setText(busTicketDetails.getTotal());

        double totalFare = Utils.getDoubleValueFromString(busTicketDetails.getTotal());
        double serviceCharge = Utils.getDoubleValueFromString(busTicketDetails.getServiceCharge());
        double actualFare = totalFare - serviceCharge;
        tvServiceChargeOrGstField.setText(busTicketDetails.getServiceCharge());
        tvTotalPersonsFareField.setText(String.format(Locale.getDefault(), "%.2f", actualFare));

        tvBookingStatusField = view.findViewById(R.id.tv_booking_status_field);
        String ticketStatus = busTicketDetails.getTicket_status();
        updateTicketStatus(ticketStatus);
    }

    private void updateTicketStatus(String ticketStatus) {
        if(ticketStatus.equalsIgnoreCase(Constants.KEY_APPROVED)) {
            tvBookingStatusField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green));
            qrBaseView.setVisibility(View.GONE);
        } else if(ticketStatus.equalsIgnoreCase(Constants.KEY_REJECTED)) {
            tvBookingStatusField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.red_two));
        } else if(ticketStatus.equalsIgnoreCase(Constants.KEY_PENDING)) {
            tvBookingStatusField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.orange_color));
        } else {
            tvBookingStatusField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.pink_color));
            qrBaseView.setVisibility(View.GONE);
        }
        tvBookingStatusField.setText(ticketStatus);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.scan_qr_code) {
            IntentIntegrator.forSupportFragment(ViewTicketFragment.this).initiateScan();
        } else {
            mListener.generateQRCode(busTicketDetails);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();

                if(result.getContents().contains(ETIM_PRE_FIX)) {
                    String[] temp = result.getContents().split(ETIM_PRE_FIX);

                    Toast.makeText(getActivity(), "Scanned: " + temp[1], Toast.LENGTH_LONG).show();
                    String wayBillNo = "11058";
                    String depot_name = "M";
                    String depot_code = "depot code 101";
                    String trip_no = "trip no 2233";
                    String route_no = "route no 1122";
                    String bus_type = "Sleeper";
                    String bus_time = busTicketDetails.getStart_time();
                    String conductor_id = "101";
                    String conductor_name = "Vijay";
                    String status = KEY_APPROVED;
                    String ticket_number = busTicketDetails.getTicket_no();
                    String operator = busTicketDetails.getOperator_name();
                    String etim_no = "1122334455";
                    String bus_no = "bus no 1";
                    String machine_no = "machine no 3";
                    String bus_service_id = busTicketDetails.getBus_service_no();
                    RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class).updateTicketStatus(wayBillNo,
                            depot_name, depot_code, trip_no, route_no, bus_type, bus_time, conductor_id, conductor_name, status, ticket_number, operator, etim_no,
                            bus_no, machine_no, bus_service_id).enqueue(new Callback<UpdateTicketStatus>() {
                        @Override
                        public void onResponse(@NotNull Call<UpdateTicketStatus> call, @NotNull Response<UpdateTicketStatus> response) {
                            MyProfile.getInstance().updateLiveTickets(progressDialog);
                            UpdateTicketStatus body = response.body();
                            if(response.isSuccessful() && body != null && body.getStatus().equalsIgnoreCase("success")) {
                                showDialog(getString(R.string.ticket_status), getString(R.string.status_approved), new CallBackInterface() {
                                    @Override
                                    public void callBackReceived(Object pObject) {
                                        updateTicketStatus(Constants.KEY_APPROVED);
                                    }
                                });
                                qrBaseView.setVisibility(View.GONE);
                            } else {
                                showDialog("", Objects.requireNonNull(body).getMsg());
                                if (Objects.requireNonNull(body).getMsg().contains(Constants.KEY_REJECTED)) {
                                    updateTicketStatus(Constants.KEY_REJECTED);
                                }
                            }
                        }

                        @Override
                        public void onFailure(@NotNull Call<UpdateTicketStatus> call, @NotNull Throwable t) {

                        }
                    });
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}