package com.pixelnx.eacademy.ui.payment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.ui.base.BaseActivity;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomSmallText;
import com.pixelnx.eacademy.utils.widgets.CustomTextExtraBold;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ActivityPaymentHistory extends BaseActivity implements View.OnClickListener {
    CustomTextExtraBold tvHeader;
    ImageView ivBack, noResultIv;
    Context context;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    LinearLayout dynamicLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        context = ActivityPaymentHistory.this;
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(this);
        sharedPref = SharedPref.getInstance(context);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        init();

    }

    void init() {
        tvHeader = findViewById(R.id.tvHeader);
        noResultIv = findViewById(R.id.noResultIv);
        dynamicLayout = findViewById(R.id.dynamicLayout);
        tvHeader.setText(getResources().getString(R.string.Payment_History));
        api();
    }

    void api() {
        ProjectUtils.showProgressDialog(context, true, getResources().getString(R.string.Loading___));

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_GET_PAYMENT_HISTORY)
                .addBodyParameter(AppConsts.STUDENT_ID, "" + modelLogin.getStudentData().getStudentId())
                .build().getAsObject(ModelHistory.class, new ParsedRequestListener<ModelHistory>() {
            @Override
            public void onResponse(ModelHistory response) {
                ProjectUtils.pauseProgressDialog();
                if (response.getStatus().equalsIgnoreCase("true")) {
                    noResultIv.setVisibility(View.GONE);
                    dynamicLayout.setVisibility(View.VISIBLE);


                    for (int i = 0; i < response.getPaymentData().size(); i++) {

                        LinearLayout parentview = new LinearLayout(context);
                        parentview.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        parentview.setOrientation(LinearLayout.HORIZONTAL);
                        TextView textName = new CustomSmallText(context);
                        textName.setText(response.getPaymentData().get(i).getBatchName() + " ");
                        textName.setTextSize(14f);

                        textName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        textName.setLayoutParams(new TableLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                        TextView textAmount = new CustomSmallText(context);
                        textAmount.setTextSize(14f);
                        textAmount.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                        textAmount.setText(" " + response.getPaymentData().get(i).getCurrencyDecimalCode() + " " + response.getPaymentData().get(i).getAmount() + " ");
                        textAmount.setLayoutParams(new TableLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                        TextView textDate = new CustomSmallText(context);
                        textDate.setTextSize(14f);
                        textDate.setGravity(Gravity.CENTER);


                        View view = new View(context);
                        view.setPadding(1, 1, 1, 1);
                        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
                        view.setBackgroundColor(Color.BLACK);

                        try {
                            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            final Date dateStartObj = sdf.parse("" + response.getPaymentData().get(i).getCreateAt());
                            textDate.setText(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(dateStartObj));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        textDate.setLayoutParams(new TableLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f));
                        parentview.addView(textName);
                        parentview.addView(textAmount);
                        parentview.addView(textDate);
                        dynamicLayout.addView(view);
                        dynamicLayout.addView(parentview);


                    }


                } else {
                    dynamicLayout.setVisibility(View.GONE);
                    noResultIv.setVisibility(View.VISIBLE);


                }
            }

            @Override
            public void onError(ANError anError) {
                ProjectUtils.pauseProgressDialog();
                Toast.makeText(context, getResources().getString(R.string.Try_again_server_issue), Toast.LENGTH_SHORT);
            }
        });


    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivBack) {
            onBackPressed();
        }

    }
}