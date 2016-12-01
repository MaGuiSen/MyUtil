package module.qrcode;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.ma.myutil.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class QRcodeActivity extends FragmentActivity implements QRCodeReaderView.OnQRCodeReadListener {

    @Bind(R.id.qrdecoderview)
    QRCodeReaderView mydecoderview;
    @Bind(R.id.resultTextView)
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);

        mydecoderview.setOnQRCodeReadListener(this);

        // Use this function to enable/disable decoding
        mydecoderview.setQRDecodingEnabled(true);

        // Use this function to change the autofocus interval (default is 5 secs)
        mydecoderview.setAutofocusInterval(2000L);

        // Use this function to enable/disable Torch
        mydecoderview.setTorchEnabled(true);

        // Use this function to set front camera preview
        mydecoderview.setFrontCamera();

        // Use this function to set back camera preview
        mydecoderview.setBackCamera();
    }

    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed in View
    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        resultTextView.setText(text);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.stopCamera();
    }
}