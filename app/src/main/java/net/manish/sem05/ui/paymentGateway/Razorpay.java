package com.pixelnx.eacademy.ui.paymentGateway;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pixelnx.eacademy.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Razorpay extends AppCompatActivity implements PaymentResultListener {
static  String rZPKey="",amountForPayment="",currencyCode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay);
        Checkout.preload(getApplicationContext());
        rZPKey=getIntent().getStringExtra("rZPKey");
        amountForPayment=getIntent().getStringExtra("amount");
        currencyCode=getIntent().getStringExtra("code");
        Log.v("saloni123","saloni    "+rZPKey+","+amountForPayment);
        startPayment(""+amountForPayment);
    }
    public void startPayment(String payments) {

        Checkout checkout = new Checkout();
        checkout.setKeyID(rZPKey);
        final Activity activity = this;


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "" + getResources().getString(R.string.app_name));
            jsonObject.put("description", "Pay Fee");
            jsonObject.put("currency", "" + currencyCode);
            jsonObject.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            jsonObject.put("payment_capture", true);
            String payment = "" + payments;
            double totalPay = Double.parseDouble(payment);
            totalPay = totalPay * 100;
            jsonObject.put("amount", "" + totalPay);
            checkout.open(activity, jsonObject);

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Error_in_payment), Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }


    }
    @Override
    public void onPaymentSuccess(String s) {
        if (!amountForPayment.equalsIgnoreCase("free")) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.Payment_Done), Toast.LENGTH_SHORT).show();
            Intent returnIntent = new Intent();
            returnIntent.putExtra("paymentdata", "" + s);
            returnIntent.putExtra("amount", "" + amountForPayment);
            setResult(Activity.RESULT_OK,returnIntent);
            onBackPressed();
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.v("saloni123","saloni    "+s+i);
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.Payment_Cancel), Toast.LENGTH_SHORT).show();
        finish();


    }
}