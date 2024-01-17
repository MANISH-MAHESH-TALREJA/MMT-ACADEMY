package com.pixelnx.eacademy.ui.paymentGateway;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.ModelSettingRecord;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Locale;


public class paypal extends AppCompatActivity {
    private static final String TAG = "paymentExample";
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX; //  set live by   PayPalConfiguration.ENVIRONMENT_PRODUCTION
    static String CONFIG_CLIENT_ID = "";
    private static final int REQUEST_CODE_PAYMENT = 1;
    private static PayPalConfiguration config;
    static String currencyCode = "", amount = "";
    SharedPref sharedPref;
    ModelLogin modelLogin;
    ModelSettingRecord modelSettingRecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal);

        try {
            modelSettingRecord = sharedPref.getSettingInfo(AppConsts.APP_INFO);
            if (modelSettingRecord.getData().getLanguageName().equalsIgnoreCase("arabic")) {
                //for rtl
                Configuration configuration = getResources().getConfiguration();
                configuration.setLayoutDirection(new Locale("fa"));
                getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
                String languageToLoad = "ar"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());
                recreate();


            }
            if (modelSettingRecord.getData().getLanguageName().equalsIgnoreCase("french")) {
                String languageToLoad = "fr"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());


            }
            if (modelSettingRecord.getData().getLanguageName().equalsIgnoreCase("english")) {
                String languageToLoad = "en"; // your language
                Locale locale = new Locale(languageToLoad);
                Locale.setDefault(locale);
                Configuration config = new Configuration();
                config.locale = locale;
                getBaseContext().getResources().updateConfiguration(config,
                        getBaseContext().getResources().getDisplayMetrics());



            }
        } catch (Exception e) {

        }
        if (getIntent().hasExtra("clientId")) {
            CONFIG_CLIENT_ID = getIntent().getStringExtra("clientId");
            currencyCode = getIntent().getStringExtra("code");
            amount = getIntent().getStringExtra("amount");
        }

        config = new PayPalConfiguration()
                .environment(CONFIG_ENVIRONMENT)
                .clientId(CONFIG_CLIENT_ID)
                .merchantName("Example Merchant")
                .merchantUserAgreementUri(Uri.parse("https://www.example.com/legal"));

        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
        onBuyPressed();

    }

    public void onBuyPressed() {

        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(paypal.this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
        startActivityForResult(intent, REQUEST_CODE_PAYMENT);
    }

    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal("" + amount), "" + currencyCode, "sample item",
                paymentIntent);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(confirm.toJSONObject().toString(4));
                        JSONObject jsonObject1 = new JSONObject("" + jsonObject.getString("response"));
                        Intent returnIntent = new Intent();
                        returnIntent.putExtra("paymentdata", "" + jsonObject1.getString("id"));
                        returnIntent.putExtra("amount", "" + amount);
                        setResult(Activity.RESULT_OK,returnIntent);
                        finish();
                        ((TextView) findViewById(R.id.txtResult)).setText(getResources().getString(R.string.PaymentCompleted)+" ");
                    } catch (JSONException e) {

                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this,getResources().getString(R.string.Cancel),Toast.LENGTH_SHORT).show();
              onBackPressed();

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
               finish();
                Toast.makeText(this,getResources().getString(R.string.An_invalid_Payment_or_PayPalConfiguration_was_submitted),Toast.LENGTH_SHORT).show();
            }
        }
    }




    @Override
    public void onDestroy() {
        // Stop service when done saloni
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
