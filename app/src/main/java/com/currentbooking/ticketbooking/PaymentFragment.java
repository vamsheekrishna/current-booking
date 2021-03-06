package com.currentbooking.ticketbooking;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.NetworkUtility;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.cb_api.responses.BusOperator;
import com.currentbooking.utilits.cb_api.responses.CCAvenueResponse;
import com.currentbooking.utilits.cb_api.responses.GetBalance;
import com.currentbooking.utilits.cb_api.responses.GetFareResponse;
import com.currentbooking.utilits.cb_api.responses.RSAKeyData;
import com.currentbooking.utilits.cb_api.responses.RSAKeyResponse;
import com.currentbooking.utilits.ccavenue.AvenuesParams;
import com.currentbooking.utilits.ccavenue.RSAUtility;
import com.currentbooking.utilits.ccavenue.ServiceUtility;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_PASSENGER_DETAILS = "Passenger Details";
    private static final String ARG_FARE_DETAILS = "Fare Details";

    private String passengerDetails;
    private GetFareResponse.FareDetails mFareDetails;
    // JsonObject jsonObject;
    OnTicketBookingListener mListener;
    private RSAKeyData rsaKeyObject;
    CCAvenueResponse ccAvenueResponse;

    WebView webview;

    public PaymentFragment() {
        // Required empty public constructor
    }

    public static PaymentFragment newInstance(String passengerDetails, GetFareResponse.FareDetails fareDetails) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PASSENGER_DETAILS, passengerDetails);
        args.putSerializable(ARG_FARE_DETAILS, fareDetails);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnTicketBookingListener) context;

    }

    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(getActivity()).setTitle("Payment");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            passengerDetails = getArguments().getString(ARG_PASSENGER_DETAILS);
            mFareDetails = (GetFareResponse.FareDetails) getArguments().getSerializable(ARG_FARE_DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // view.findViewById(R.id.conform).setOnClickListener(this);
        webview = view.findViewById(R.id.web_view);

        webview.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= 19) {
            webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT"); // javaScriptInterface
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(webview, url);
                if (null != progressDialog && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError er) {
                handler.proceed();
                // Ignore SSL certificate errors
            }

            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
                // Redirect to deprecated method, so you can use it in all SDK versions
                showDialog("", rerr.getDescription().toString());
                if (null != progressDialog && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        });
        /*webview.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                WebView webView = (WebView) v;
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                }
            }

            return false;
        });*/

        /*if (NetworkUtility.isNetworkConnected(requireActivity())) {
            progressDialog.show();
            TicketBookingServices ticketBookingService = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);

            TicketBookingViewModel ticketBookingViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(TicketBookingViewModel.class);

            Gson gson = new Gson();
            Type listType = new TypeToken<BusOperator>() {
            }.getType();
            String busOperator = gson.toJson(ticketBookingViewModel.getSelectedBusOperator().getValue(), listType);
            listType = new TypeToken<BusObject>() {
            }.getType();
            String selectedBus = gson.toJson(ticketBookingViewModel.getSelectedBusObject().getValue(), listType);
            listType = new TypeToken<GetFareResponse.FareDetails>() {
            }.getType();
            // String fareDetails = gson.toJson(mFareDetails, listType);
            ticketBookingService.getRSAKey(busOperator, selectedBus, mFareDetails.getPassengerDetails(), mFareDetails.getBreakup(), MyProfile.getInstance().getUserId()).enqueue(new Callback<RSAKeyResponse>() {
                @Override
                public void onResponse(@NotNull Call<RSAKeyResponse> call, @NotNull Response<RSAKeyResponse> response) {
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        RSAKeyResponse data = response.body();
                        if (data != null) {
                            if (data.getStatus().equalsIgnoreCase("success")) {
                                rsaKeyObject = data.getData();
                                loadWebView();
                            } else {
                                showErrorDialog(data.getMsg());
                            }
                        } else {
                            showErrorDialog(getString(R.string.no_information_available));
                        }
                    } else {
                        showErrorDialog(getString(R.string.no_information_available));
                    }
                }

                @Override
                public void onFailure(@NotNull Call<RSAKeyResponse> call, @NotNull Throwable t) {
                    showDialog("", t.getMessage());
                    progressDialog.dismiss();
                }
            });
        } else {
            showErrorDialog(getString(R.string.internet_fail));
        }*/
        ViewDialog alert = new ViewDialog();
        alert.showDialogg(requireActivity());
    }

    private void showErrorDialog(String message) {
        showDialog(getString(R.string.message), message, pObject -> requireActivity().getSupportFragmentManager().popBackStack());
    }

    private void loadWebView() {
        try {
            /* An instance of this class will be registered as a JavaScript interface */
            progressDialog.show();
            StringBuilder params = new StringBuilder();
            String access_code, merchant_id, redirect_url, cancel_url, billing_country, merchant_param1, merchant_param2,
                    billing_name, billing_email, billing_tel, encVal = "";

            access_code = rsaKeyObject.getAccess_code(); //getString(R.string.access_code);
            merchant_id = rsaKeyObject.getMerchant_id(); //getString(R.string.merchant_id);
            redirect_url = rsaKeyObject.getRedirect_url(); // getString(R.string.redirect_url);
            cancel_url = rsaKeyObject.getCancel_url(); // getString(R.string.cancel_url);
            billing_country = getString(R.string.country);
            // merchant_param1 = merchantParams.ticket_id;
            billing_name = MyProfile.getInstance().getFirstName();//"shweta";
            billing_email = MyProfile.getInstance().getEmail(); // "shwetadalvi9@gmail.com";
            billing_tel = MyProfile.getInstance().getMobileNumber(); // "8446399429";
            StringBuilder vEncVal = new StringBuilder();

            String amount = String.valueOf(mFareDetails.getTotal());
            String currency = getString(R.string.currency);
            vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, amount));
            vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, currency));
            encVal = RSAUtility.encrypt(vEncVal.substring(0, vEncVal.length() - 1), rsaKeyObject.getRsa_key().replaceAll("[\n]", ""));

            params.append(ServiceUtility.addToPostParams(AvenuesParams.ACCESS_CODE, access_code));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_ID, merchant_id));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ORDER_ID, rsaKeyObject.getOrder_id()));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.REDIRECT_URL, redirect_url));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.CANCEL_URL, cancel_url));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.ENC_VAL, URLEncoder.encode(encVal, "UTF-8")));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_NAME, billing_name));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_EMAIL, billing_email));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_TEL, billing_tel));
            params.append(ServiceUtility.addToPostParams(AvenuesParams.BILLING_COUNTRY, billing_country));
            //params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_PARAM1, merchant_param1));//ticket id
            params.append(ServiceUtility.addToPostParams(AvenuesParams.MERCHANT_PARAM2, "android"));//platform

            String vPostParams = params.substring(0, params.length() - 1);

            String vTransUrl = ("https://test.ccavenue.com/transaction/initTrans");
            webview.postUrl(vTransUrl, vPostParams.getBytes(StandardCharsets.UTF_8));// EncodingUtils.getBytes(vPostParams, "UTF-8"));
             progressDialog.dismiss();
        } catch (Exception e) {
            showErrorDialog(e.getMessage());
            progressDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        //mListener.gotoTicketStatus();
    }

    class MyJavaScriptInterface {
        @JavascriptInterface
        public void processHTML(String html) {
            // jsonObject = new JsonParser().parse(html).getAsJsonObject();
            Gson g = new Gson();
            ccAvenueResponse = g.fromJson(html, CCAvenueResponse.class);
            Log.d("JsonObject", "html: " + html);
            if (ccAvenueResponse.getStatus().equalsIgnoreCase("success")) {
                Log.d("JsonObject", "html: " + html);
                mListener.gotoTicketStatus(passengerDetails, ccAvenueResponse);
            }else{
                showdailog_back("Transaction Failed");

            }

        }

        @JavascriptInterface
        public void gotMsg(String msg) {
            showDialog("", msg);
            // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public class ViewDialog {

        public void showDialogg(Activity activity) {
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.payment_option_dailog);

            Button wallet = (Button) dialog.findViewById(R.id.using_wallet);
            Button ccavenue = (Button) dialog.findViewById(R.id.using_netbanking);
            ccavenue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (NetworkUtility.isNetworkConnected(requireActivity())) {
                        progressDialog.show();
                        TicketBookingServices ticketBookingService = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);

                        TicketBookingViewModel ticketBookingViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(TicketBookingViewModel.class);

                        Gson gson = new Gson();
                        Type listType = new TypeToken<BusOperator>() {
                        }.getType();
                        String busOperator = gson.toJson(ticketBookingViewModel.getSelectedBusOperator().getValue(), listType);
                        listType = new TypeToken<BusObject>() {
                        }.getType();
                        String selectedBus = gson.toJson(ticketBookingViewModel.getSelectedBusObject().getValue(), listType);
                        listType = new TypeToken<GetFareResponse.FareDetails>() {
                        }.getType();
                        // String fareDetails = gson.toJson(mFareDetails, listType);
                        ticketBookingService.getRSAKey(busOperator, selectedBus, mFareDetails.getPassengerDetails(), mFareDetails.getBreakup(), MyProfile.getInstance().getUserId(),mFareDetails.getFare()).enqueue(new Callback<RSAKeyResponse>() {
                            @Override
                            public void onResponse(@NotNull Call<RSAKeyResponse> call, @NotNull Response<RSAKeyResponse> response) {
                                progressDialog.dismiss();
                                if (response.isSuccessful()) {
                                    RSAKeyResponse data = response.body();
                                    if (data != null) {
                                        if (data.getStatus().equalsIgnoreCase("success")) {
                                            rsaKeyObject = data.getData();
                                            progressDialog.show();
                                            loadWebView();
                                        } else {
                                            showdailog_back(data.getMsg());
                                        }
                                    } else {
                                        showdailog_back(getString(R.string.payment_failed_description));


                                    }
                                } else {
                                    showdailog_back(getString(R.string.payment_failed_description));

                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<RSAKeyResponse> call, @NotNull Throwable t) {
                                //showDialog("", t.getMessage());
                                progressDialog.dismiss();
                                showdailog_back(getString(R.string.server_connection_failed));

                            }
                        });
                    } else {
                        showErrorDialog(getString(R.string.internet_fail));
                    }
                }
            });
            wallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    if (NetworkUtility.isNetworkConnected(requireActivity())) {


                        progressDialog.show();
                        TicketBookingServices ticketBookingService = RetrofitClientInstance.getRetrofitInstance().create(TicketBookingServices.class);

                        TicketBookingViewModel ticketBookingViewModel = new ViewModelProvider(Objects.requireNonNull(getActivity())).get(TicketBookingViewModel.class);

                        Gson gson = new Gson();
                        Type listType = new TypeToken<BusOperator>() {
                        }.getType();
                        String busOperator = gson.toJson(ticketBookingViewModel.getSelectedBusOperator().getValue(), listType);
                        listType = new TypeToken<BusObject>() {
                        }.getType();
                        String selectedBus = gson.toJson(ticketBookingViewModel.getSelectedBusObject().getValue(), listType);
                        listType = new TypeToken<GetFareResponse.FareDetails>() {
                        }.getType();
                        // String fareDetails = gson.toJson(mFareDetails, listType);
                        ticketBookingService.getRSAKeyWallet(busOperator, selectedBus, mFareDetails.getPassengerDetails(), mFareDetails.getBreakup(), MyProfile.getInstance().getUserId(),mFareDetails.getFare()).enqueue(new Callback<GetBalance>() {
                            @Override
                            public void onResponse(@NotNull Call<GetBalance> call, @NotNull Response<GetBalance> response) {
                                progressDialog.dismiss();
                                GetBalance data = response.body();
                                if(response.isSuccessful()) {
                                    if (data != null) {
                                        if (data.getStatus().equalsIgnoreCase("success")||data.getStatus().equalsIgnoreCase("")) {
                                            ccAvenueResponse = response.body().getResult();
                                            mListener.gotoTicketStatus(passengerDetails, ccAvenueResponse);
                                        }else{
                                            progressDialog.dismiss();
                                            showdailog_back(data.getMsg());

                                        }

                                    }else{
                                        progressDialog.dismiss();
                                        showdailog_back(getString(R.string.payment_failed_description));

                                    }
                                }
                                else {
                                    if(data!=null){
                                        progressDialog.dismiss();
                                        showdailog_back(ccAvenueResponse.getMsg());

                                    }else{
                                        showdailog_back(getString(R.string.server_connection_failed));

                                    }
                                    progressDialog.dismiss();

                                }
                            }

                            @Override
                            public void onFailure(@NotNull Call<GetBalance> call, @NotNull Throwable t) {
                                //showDialog("", t.getMessage());
                                progressDialog.dismiss();
                                showdailog_back(getString(R.string.server_connection_failed));

                            }
                        });
                    } else {
                        showErrorDialog(getString(R.string.internet_fail));
                    }
                }
            });

            dialog.show();

        }
    }
    public void showdailog_back(String msg){

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
            builder.setTitle("Message");
            builder.setMessage(msg);
            builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(requireActivity(), TicketBookingActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    requireActivity().startActivity(intent);
                    requireActivity().finish();
                }


            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        catch (Exception e){
            e.printStackTrace();}


    }

}
