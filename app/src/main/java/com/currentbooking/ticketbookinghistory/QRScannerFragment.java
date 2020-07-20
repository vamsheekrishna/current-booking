package com.currentbooking.ticketbookinghistory;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.currentbooking.R;
import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.utilits.cb_api.RetrofitClientInstance;
import com.currentbooking.utilits.cb_api.interfaces.TicketBookingServices;
import com.currentbooking.utilits.cb_api.responses.UpdateTicketStatus;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.VIBRATOR_SERVICE;
import static com.currentbooking.ticketbookinghistory.GenerateQRCode.ETIM_PRE_FIX;
import static com.currentbooking.utilits.Constants.KEY_APPROVED;

public class QRScannerFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final int REQUEST_CODE_CEMERA = 101;

    // TODO: Rename and change types of parameters
    private MyTicketInfo mMyTicketInfo;
    private String mParam2;

    // private TextView textView;
    private BarcodeDetector detector;
    private CameraSource cameraSource;
    private OnTicketBookingHistoryListener mListener;
    private boolean isSuccess;
    private SurfaceView surfaceView;
    private Button buttonScan;
    private TextView textViewName;
    private TextView textViewAddress;

    private IntentIntegrator qrScan;

    public QRScannerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnTicketBookingHistoryListener) {
            mListener = (OnTicketBookingHistoryListener) context;
        }
    }

    public static QRScannerFragment newInstance(MyTicketInfo param1, String param2) {
        QRScannerFragment fragment = new QRScannerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMyTicketInfo = (MyTicketInfo) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, REQUEST_CODE_CEMERA);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q_r_scanner, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Scan QRCode");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //View objects
        /*buttonScan = view.findViewById(R.id.buttonScan);
        textViewName = view.findViewById(R.id.textViewName);
        textViewAddress =  view.findViewById(R.id.textViewAddress);
*/

        // qrScan = new IntentIntegrator(getActivity()).initiateScan();
        IntentIntegrator.forSupportFragment(QRScannerFragment.this).initiateScan();
        detector = new BarcodeDetector.Builder(getActivity())
                .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource.Builder(getActivity(), detector).setRequestedPreviewSize(600,600)
                .build();
        showPreview(view);
    }

    public void setButtonScan(Button buttonScan) {
        this.buttonScan = buttonScan;
    }

    private void showPreview(View view) {
        surfaceView = view.findViewById(R.id.surface);
        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)!=
                        PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                try {
                    cameraSource.start(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });
        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                //getBarcodeData();
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size()!=0 ) {
                    final Barcode thisCode = barcodes.valueAt(0);
                    if(thisCode.rawValue.contains(ETIM_PRE_FIX) && !isSuccess) {
                        Vibrator vibrator = (Vibrator) Objects.requireNonNull(getContext()).getSystemService(VIBRATOR_SERVICE);
                        assert vibrator != null;
                        vibrator.vibrate(new long[]{0, 200, 300,400} , -1);
                        Objects.requireNonNull(getActivity()).onBackPressed();
                    }
                }
            }
        });
    }
}
