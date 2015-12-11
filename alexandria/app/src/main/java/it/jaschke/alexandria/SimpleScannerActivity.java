package it.jaschke.alexandria;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import it.jaschke.alexandria.utils.Constants;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

/* CLASS FROM: https://github.com/dm77/barcodescanner */
public class SimpleScannerActivity extends Activity implements ZXingScannerView.ResultHandler {
    private final String TAG = "SimpleScanner";
    private ZXingScannerView mScannerView;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)

        Intent i = getIntent();
        String msg = i.getStringExtra(Constants.EXTRA_REQUEST);
        if (msg.contentEquals(Constants.EXTRA_SCAN)) {
            i.putExtra(Constants.EXTRA_SCAN_CODE, rawResult.getText());
            setResult(RESULT_OK, i);
            finish();
        }
    }
}