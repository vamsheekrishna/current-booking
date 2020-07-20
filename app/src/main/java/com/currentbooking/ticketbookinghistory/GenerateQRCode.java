package com.currentbooking.ticketbookinghistory;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.currentbooking.R;
import com.currentbooking.ticketbookinghistory.models.MyTicketInfo;
import com.currentbooking.utilits.cb_api.responses.Concession;
import com.currentbooking.utilits.views.BaseFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.lang.reflect.Type;
import java.util.List;

public class GenerateQRCode extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ETIM_PRE_FIX = "ETIM_";

    private MyTicketInfo myTicketInfo;
    private String mParam2;
    private ImageView imageView;
    private String ticketJson= "{}";

    public GenerateQRCode() {
        //
    }
    public static GenerateQRCode newInstance(MyTicketInfo param1, String param2) {
        GenerateQRCode fragment = new GenerateQRCode();
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
            myTicketInfo = (MyTicketInfo) getArguments().getSerializable(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_generate_virtual_card, container, false);

    }
    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Show QRCode");
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.qr_code);

        Type ticketType = new TypeToken<MyTicketInfo>() {}.getType();
        Gson gson = new Gson();
        ticketJson = gson.toJson(myTicketInfo, ticketType);

        String text= ETIM_PRE_FIX +ticketJson;
        imageView.setImageBitmap(encodeAsBitmap(text, 1020,1020));
    }

    public static Bitmap encodeAsBitmap(String source, int width, int height) {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(source, BarcodeFormat.QR_CODE, width, height, null);
        } catch (IllegalArgumentException | WriterException e) {
            // Unsupported format
            return null;
        }

        final int w = result.getWidth();
        final int h = result.getHeight();
        final int[] pixels = new int[w * h];

        for (int y = 0; y < h; y++) {
            final int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? Color.BLACK : Color.WHITE;
            }
        }

        final Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);

        return bitmap;
    }
}
