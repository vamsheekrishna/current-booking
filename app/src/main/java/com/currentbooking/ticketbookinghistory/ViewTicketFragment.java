package com.currentbooking.ticketbookinghistory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.currentbooking.utilits.DateUtilities;
import com.currentbooking.utilits.LoggerInfo;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.NetworkUtility;
import com.currentbooking.utilits.Utils;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.TodayTickets;
import com.currentbooking.utilits.cb_api.responses.UpdateTicketStatus;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.currentbooking.ticketbookinghistory.GenerateQRCode.ETIM_PRE_FIX;
import static com.currentbooking.utilits.Constants.KEY_APPROVED;
import static com.currentbooking.utilits.DateUtilities.CALENDAR_DATE_FORMAT_THREE;

public class ViewTicketFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_TICKET_DETAILS = "TicketDetails";
    private MyTicketInfo busTicketDetails;

    // TODO: Rename and change types of parameters
    private OnTicketBookingHistoryListener mListener;
    private LinearLayout qrBaseView;
    private TextView tvBookingStatusField;
    private TextView tvBusRouteNameField;
    private TextView tv_bus_type_field;
    private TextView tvBusRouteField;
    private TextView tvJourneyStartTimeField;
    private TextView tvJourneyEndTimeField;
    private TextView tvJourneyHrsField;
    private TextView tvTotalPersonsFareField;
    private TextView tvServiceChargeOrGstField;
    private TextView tvTotalUpdatedFareField;
    private TextView tv_ticket_time_field;
    private TextView tv_ticket_date_field;
    private TextView transction_id;

    private RecyclerView passengerListRecyclerField;

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
        passengerListRecyclerField = view.findViewById(R.id.passenger_list_recycler_field);
        passengerListRecyclerField.setHasFixedSize(false);

        ImageView ivRefreshIconField = view.findViewById(R.id.iv_refresh_icon_field);
        ivRefreshIconField.setOnClickListener(this);

        DividerItemDecoration divider = new DividerItemDecoration(Objects.requireNonNull(requireActivity()), DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(Objects.requireNonNull(requireActivity()),
                R.drawable.recycler_decoration_divider)));
        passengerListRecyclerField.addItemDecoration(divider);
        tvBusRouteNameField = view.findViewById(R.id.tv_route_name_field);
        tv_bus_type_field = view.findViewById(R.id.tv_bus_type_field);
        tv_ticket_date_field = view.findViewById(R.id.tv_ticket_date_field);
        transction_id = view.findViewById(R.id.transction_id);


        tv_ticket_time_field = view.findViewById(R.id.tv_ticket_time_field);
        tvBusRouteField = view.findViewById(R.id.tv_bus_route_field);
        tvJourneyStartTimeField = view.findViewById(R.id.tv_bus_journey_start_time_field);
        tvJourneyEndTimeField = view.findViewById(R.id.tv_bus_journey_end_time_field);
        tvJourneyHrsField = view.findViewById(R.id.tv_bus_journey_hours_field);

        tvTotalPersonsFareField = view.findViewById(R.id.tv_total_persons_bus_fare_price_field);

       // tvServiceChargeOrGstField = view.findViewById(R.id.tv_total_persons_service_charge_or_gst_field);
        tvTotalUpdatedFareField = view.findViewById(R.id.tv_total_persons_total_fare_field);
        tvBookingStatusField = view.findViewById(R.id.tv_booking_status_field);

        updateViewTicketsUI();
    }

    private void updateViewTicketsUI() {
        String busRouteName = String.format("%s %s", busTicketDetails.getOperator_name().toUpperCase(), busTicketDetails.getBus_service_no());
        String ticketNo = String.format("%s %s", getString(R.string.ticket_number), busTicketDetails.getTicket_no());
        String busRouteAndTicketNo = String.format("%s\n%s", busRouteName, ticketNo);
        tvBusRouteNameField.setText(busRouteAndTicketNo);

        String busRoute = String.format("%s to %s", busTicketDetails.getFrom_stop(), busTicketDetails.getTo_stop());
        tvBusRouteField.setText(busRoute);
        tvJourneyEndTimeField.setText(busTicketDetails.getDrop_time());
        tvJourneyStartTimeField.setText(busTicketDetails.getStart_time());
        tv_bus_type_field.setText(busTicketDetails.getBus_type_name());
        tv_ticket_time_field.setText("  Time: "+busTicketDetails.getTicket_time());
        tv_ticket_date_field.setText("Date: "+busTicketDetails.getTicket_date());
        String journeyHrs = String.format("%s Hrs", busTicketDetails.getHours());
        tvJourneyHrsField.setText(journeyHrs);

        List<PassengerDetailsModel> passengerDetailsList = busTicketDetails.getPassengerDetailsList();

        PassengerDetailsAdapter passengerDetailsAdapter = new PassengerDetailsAdapter(requireActivity(), passengerDetailsList);
        passengerListRecyclerField.setAdapter(passengerDetailsAdapter);

        tvTotalUpdatedFareField.setText(busTicketDetails.getTotal());

        double totalFare = Utils.getDoubleValueFromString(busTicketDetails.getTotal());
        double serviceCharge = Utils.getDoubleValueFromString(busTicketDetails.getServiceCharge());
        double actualFare = totalFare - serviceCharge;
      //  tvServiceChargeOrGstField.setText(busTicketDetails.getServiceCharge());
        tvTotalPersonsFareField.setText(String.format(Locale.getDefault(), "%.2f", actualFare));
        transction_id.setText(busTicketDetails.getBosID());
        String ticketStatus = busTicketDetails.getTicket_status();
        updateTicketStatus(ticketStatus);
    }

    private void updateTicketStatus(String ticketStatus) {
        if(ticketStatus.equalsIgnoreCase(Constants.KEY_APPROVED)) {
            ticketStatus = "Approved and validated successfully";
            tvBookingStatusField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green));
            qrBaseView.setVisibility(View.GONE);
        } else if(ticketStatus.equalsIgnoreCase(Constants.KEY_REJECTED)) {
            tvBookingStatusField.setTextColor(ContextCompat.getColor(requireActivity(), R.color.red_two));
        } else if(ticketStatus.equalsIgnoreCase(Constants.KEY_PENDING)) {
            ticketStatus = getString(R.string.un_used);
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
        } else if(view.getId() == R.id.iv_refresh_icon_field) {
            refreshTicketSelected();
        } else {
            mListener.generateQRCode(busTicketDetails);
        }
    }

    private void refreshTicketSelected() {
        if(NetworkUtility.isNetworkConnected(requireActivity())) {
            String date = DateUtilities.getTodayDateString(CALENDAR_DATE_FORMAT_THREE);
            String id = MyProfile.getInstance().getUserId();
            TicketBookingServices service = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);
            progressDialog.show();
            service.getCurrentBookingTicket(date, id, busTicketDetails.getTicket_no()).enqueue(new Callback<TodayTickets>() {
                @Override
                public void onResponse(@NotNull Call<TodayTickets> call, @NotNull Response<TodayTickets> response) {
                    TodayTickets todayTickets = response.body();
                    if (todayTickets != null) {
                        if (todayTickets.getStatus().equalsIgnoreCase("success")) {
                            ArrayList<MyTicketInfo> data = todayTickets.getAvailableTickets();
                            if (null != data && data.size() > 0) {
                                busTicketDetails = data.get(0);
                                updateViewTicketsUI();
                            }
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<TodayTickets> call, @NotNull Throwable t) {
                    // showDialog("onFailure", "" + t.getMessage());
                    LoggerInfo.errorLog("getAvailableLiveTickets OnFailure", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        } else {
            showDialog(getString(R.string.internet_fail));
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
                                if (Objects.requireNonNull(body).getMsg().contains(Constants.KEY_FAILED)) {
                                    updateTicketStatus(Constants.KEY_FAILED);
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