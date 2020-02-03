package com.panda.solar.presentation.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.panda.solar.activities.R;

import java.util.List;


public class ScanEquipmentFragment extends Fragment implements DecoratedBarcodeView.TorchListener {

    private DecoratedBarcodeView barcodeView;
    private Button switchFlashlightButton;
    private View v;

    public ScanEquipmentFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_scan_equipment, container, false);

        barcodeView = (DecoratedBarcodeView)view.findViewById(R.id.barcode_view);
        //switchFlashlightButton = (Button)rootView.findViewById(R.id.switch_flashlight);

        startScanner();
        return view;
    }


    private void startScanner(){
        barcodeView.setTorchListener(this);
        barcodeView.setStatusText("Place QR code in rectangle to scan");

        //switchFlashlightButton.setOnClickListener(this);

        barcodeView.decodeContinuous(barcodeCallback);
        barcodeView.resume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }

    private BarcodeCallback barcodeCallback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {

            barcodeView.pause();
            if(result.getText() != null)
            {
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {

        }
    };
}
