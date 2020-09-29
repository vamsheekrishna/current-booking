package com.currentbooking.ticketbooking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;

import com.currentbooking.R;
import com.currentbooking.home.HomeActivity;
import com.currentbooking.ticketbooking.viewmodels.TicketBookingViewModel;
import com.currentbooking.utilits.MyProfile;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.LoginService;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.BusObject;
import com.currentbooking.utilits.cb_api.responses.BusOperator;
import com.currentbooking.utilits.cb_api.responses.CCAvenueResponse;
import com.currentbooking.utilits.cb_api.responses.ForgotPasswordResponse;
import com.currentbooking.utilits.cb_api.responses.GetFareResponse;
import com.currentbooking.utilits.cb_api.responses.RSAKeyData;
import com.currentbooking.utilits.cb_api.responses.RSAKeyResponse;
import com.currentbooking.utilits.ccavenue.AvenuesParams;
import com.currentbooking.utilits.ccavenue.RSAUtility;
import com.currentbooking.utilits.ccavenue.ServiceUtility;
import com.currentbooking.utilits.encrypt.Encryption;
import com.currentbooking.utilits.encrypt.EncryptionHelper;
import com.currentbooking.utilits.views.BaseFragment;
import com.currentbooking.wallet.AddMoneyCCAvenueResponse;
import com.currentbooking.wallet.OnWalletListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.currentbooking.utilits.Utils.internetConnectionAvailable;

public class WalletFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_PASSENGER_DETAILS = "user_id";
    private static final String ARG_FARE_DETAILS = "Fare Details";
    private static final String ARG_AMOUNT_DETAILS = "amount";

    private String amount;
    private GetFareResponse.FareDetails mFareDetails;
    // JsonObject jsonObject;
    OnWalletListener mListener;
    private RSAKeyData rsaKeyObject;
    AddMoneyCCAvenueResponse ccAvenueResponse;
    WebView webview;

    public WalletFragment() {
        // Required empty public constructor
    }

    public static WalletFragment newInstance(String amount) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_AMOUNT_DETAILS, amount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (OnWalletListener) context;
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
            //passengerDetails = getArguments().getString(ARG_PASSENGER_DETAILS);
            amount = getArguments().getString(ARG_AMOUNT_DETAILS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the select_bus_points for this fragment
        return inflater.inflate(R.layout.fragment_add_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // view.findViewById(R.id.conform).setOnClickListener(this);
        webview = view.findViewById(R.id.web_view);

        webview.getSettings().setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT"); // javaScriptInterface
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(webview, url);
                if(null != progressDialog && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError er) {
                handler.proceed();
                // Ignore SSL certificate errors
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                // Toast.makeText(getContext(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                showDialog("", description+" "+ failingUrl);
                if(null != progressDialog && progressDialog.isShowing()) {
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

        progressDialog.show();
        LoginService loginService = RetrofitClientInstance.getRetrofitInstance().create(LoginService.class);
        loginService.addMoney(MyProfile.getInstance().getUserId(),amount).enqueue(new Callback<RSAKeyResponse>() {
            @Override
            public void onResponse(@NotNull Call<RSAKeyResponse> call, @NotNull Response<RSAKeyResponse> response) {
                RSAKeyResponse data = response.body();
                if(response.isSuccessful()) {
                    assert data != null;
                    if(data.getStatus().equalsIgnoreCase("success")) {
                        assert response.body() != null;
                        rsaKeyObject = response.body().getData();
                        progressDialog.show();

                        loadWebView();
                    } else {
                        showDialog("", data.getMsg());
                        progressDialog.dismiss();
                        requireActivity().finish();
                    }
                } else {
                    if(data != null){
                        showdailog(data.getMsg());

                    }else{
                        showdailog("You have exceed amount limit. You can add money into wallet up to Rs.10000.00 ");

                    }


                    progressDialog.dismiss();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(@NotNull Call<RSAKeyResponse> call, @NotNull Throwable t) {
                showDialog("", t.getMessage());
                progressDialog.dismiss();
                requireActivity().finish();
            }
        });



    }

    private void loadWebView() {
        try {
            /* An instance of this class will be registered as a JavaScript interface */
            progressDialog.show();
            StringBuffer params = new StringBuffer();
            String access_code, merchant_id, redirect_url, cancel_url, billing_country, merchant_param1, merchant_param2,
            billing_name , billing_email, billing_tel, encVal = "";
            Encryption AESencryption;
            EncryptionHelper objEncryptionHelper = new EncryptionHelper();

            access_code = rsaKeyObject.getAccess_code(); //getString(R.string.access_code);
            merchant_id = rsaKeyObject.getMerchant_id(); //getString(R.string.merchant_id);
            redirect_url = rsaKeyObject.getRedirect_url(); // getString(R.string.redirect_url);
            cancel_url = rsaKeyObject.getCancel_url(); // getString(R.string.cancel_url);
            billing_country = getString(R.string.country);
            // merchant_param1 = merchantParams.ticket_id;
            billing_name  = MyProfile.getInstance().getFirstName();//"shweta";
            billing_email = MyProfile.getInstance().getEmail(); // "shwetadalvi9@gmail.com";
            billing_tel   = MyProfile.getInstance().getMobileNumber(); // "8446399429";
            StringBuffer vEncVal = new StringBuffer("");
            try {
                //String amount = String.valueOf(mFareDetails.getTotal());
                String currency = getString(R.string.currency);
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.AMOUNT, amount));
                vEncVal.append(ServiceUtility.addToPostParams(AvenuesParams.CURRENCY, currency));
                encVal = RSAUtility.encrypt(vEncVal.substring(0, vEncVal.length() - 1), rsaKeyObject.getRsa_key().replaceAll("[\n]", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }

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

            try {
                String vTransUrl = ("https://test.ccavenue.com/transaction/initTrans");
                webview.postUrl(vTransUrl, vPostParams.getBytes("UTF-8"));// EncodingUtils.getBytes(vPostParams, "UTF-8"));
                progressDialog.dismiss();

            } catch (Exception e) {
                progressDialog.dismiss();

                showDialog("", e.getMessage());

                e.printStackTrace();
                requireActivity().finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showDialog("", e.getMessage());
            requireActivity().finish();
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
            ccAvenueResponse = g.fromJson(html, AddMoneyCCAvenueResponse.class);
            if (ccAvenueResponse.getStatus().equalsIgnoreCase("success")) {
                Log.d("JsonObject", "html: " + html);
                showdailog("Your Wallet balance updated successfully");
            } else {
                showdailog("Transaction Failed");
            }


        }
        @JavascriptInterface
        public void gotMsg(String msg) {
            showDialog("", msg);
            // Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public void showdailog(String msg){

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