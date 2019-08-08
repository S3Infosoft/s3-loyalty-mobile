package in.walkover.sendotpsample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.msg91.sendotpandroid.library.PhoneNumberFormattingTextWatcher;
import com.msg91.sendotpandroid.library.PhoneNumberUtils;
import com.msg91.sendotpandroid.library.SendOtpVerification;
import com.msg91.sendotpandroid.library.Verification;
import com.msg91.sendotpandroid.library.VerificationListener;
import com.msg91.sendotpandroid.library.internal.Iso2Phone;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements VerificationListener {

    public String TAG="---- Verify";
    private Verification verification;
    private EditText mPhoneNumber,mOtpnumber;
    private Button mSmsButton,mOtpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mOtpnumber=  (EditText) findViewById(R.id.OTPNumber);
        mPhoneNumber = (EditText) findViewById(R.id.phoneNumber);
        mSmsButton = (Button) findViewById(R.id.smsVerificationButton);
        mOtpButton=(Button)findViewById(R.id.otpVerificationButton);



    }


//288118Afm2e9qcxQ5d47e920 API KEy


    public void onButtonClicked(View view) {
        verification = SendOtpVerification.createSmsVerification
                (SendOtpVerification
                        .config("+91" + mPhoneNumber.getText().toString().trim())
                        .context(this)
                        .autoVerification(false)
                        .httpsConnection(false)//connection to be use in network calls
                        .expiry("5")//value in minutes
                        .senderId("SMSIND") //where XXXX is any string
//                            .otp("1234")// Default Otp code if want to add yours
                        .otplength("4") //length of your otp
                        .message("##OTP## is Your verification digits.")
                        .build(), this);
        verification.initiate();
    }


    public void onOTPClicked(View view) {

        verification.verify(mOtpnumber.getText().toString());
    }

    @Override
    public void onInitiated(String response) {
        Log.d(TAG, "---Initialized!" + response);
    }

    @Override
    public void onInitiationFailed(Exception exception) {
        Log.e(TAG, "---Verification initialization failed: " + exception.getMessage());
        Toast.makeText(this, "Verification initialization failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerified(String response) {
        Log.d(TAG, "---Verified!\n" + response);
        Toast.makeText(this, "Verification Successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onVerificationFailed(Exception exception) {
        Log.e(TAG, "----Verification failed: " + exception.getMessage());
        Toast.makeText(this, "Verification failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();

    }

}
