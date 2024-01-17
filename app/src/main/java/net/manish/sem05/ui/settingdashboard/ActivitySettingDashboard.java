package com.pixelnx.eacademy.ui.settingdashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.ui.aboutapp.ActivityAboutApp;
import com.pixelnx.eacademy.ui.aboutapp.ActivityOpenSourceLibrary;
import com.pixelnx.eacademy.ui.applyleave.ActivityApplyLeave;
import com.pixelnx.eacademy.ui.base.BaseActivity;
import com.pixelnx.eacademy.ui.certificate.ActivityCertificateAssigned;
import com.pixelnx.eacademy.ui.editProfile.ActivityProfile;
import com.pixelnx.eacademy.ui.home.ActivityPrivacyPolicy;
import com.pixelnx.eacademy.ui.login.ActivityLogin;
import com.pixelnx.eacademy.ui.multibatch.ActivityMultiBatchHome;
import com.pixelnx.eacademy.ui.multibatch.ActivityMyBatch;
import com.pixelnx.eacademy.ui.payment.ActivityPaymentHistory;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class ActivitySettingDashboard extends BaseActivity implements View.OnClickListener {
    LinearLayout llEditProfile;
    LinearLayout llApplyLeave;
    LinearLayout llCertificate;
    LinearLayout btnPrivacyPolicy;
    LinearLayout llLogout;
    LinearLayout btnAboutApp,llPayment;
    LinearLayout btnOpenSourceLibrary;
    Context mContext;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    TextView myBatch, home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = ActivitySettingDashboard.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);


        if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("arabic")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
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
        }
        if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("french")) {
            String languageToLoad = "fr"; // your language
            Locale locale = new Locale(languageToLoad);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());


        }
        setContentView(R.layout.activity_setting_dashboard);

        llApplyLeave = findViewById(R.id.llApplyLeave);
        btnAboutApp = findViewById(R.id.btnAboutApp);
        llCertificate = findViewById(R.id.llCertificate);
        llEditProfile = findViewById(R.id.llEditProfile);
        btnPrivacyPolicy = findViewById(R.id.btnPrivacypolicy);

        btnOpenSourceLibrary = findViewById(R.id.btnOpenSourceLibrary);
        llLogout = findViewById(R.id.llLogout);
        llPayment = findViewById(R.id.llPayment);
        llPayment.setOnClickListener(this);
        llLogout.setOnClickListener(this);
        btnAboutApp.setOnClickListener(this);
        llEditProfile.setOnClickListener(this);
        llApplyLeave.setOnClickListener(this);
        llCertificate.setOnClickListener(this);
        btnPrivacyPolicy.setOnClickListener(this);
        btnOpenSourceLibrary.setOnClickListener(this);
        home = findViewById(R.id.home);
        myBatch = findViewById(R.id.myBatch);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityMultiBatchHome.class);
                startActivity(intent);
            }
        });
        myBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityMyBatch.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llEditProfile:
                Intent intent = new Intent(mContext, ActivityProfile.class);
                startActivity(intent);
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.llCertificate:
                Intent intentCertificate = new Intent(mContext, ActivityCertificateAssigned.class);
                startActivity(intentCertificate);
                break;
            case R.id.llApplyLeave:
                Intent intentApplyLeave = new Intent(mContext, ActivityApplyLeave.class);
                startActivity(intentApplyLeave);
                break;
            case R.id.btnPrivacypolicy:
                Intent intentP = new Intent(mContext, ActivityPrivacyPolicy.class);
                intentP.putExtra("WEB_URL", AppConsts.URL_PRIVACY_POLICY);
                intentP.putExtra("HEADER", "Privacy Policy");
                startActivity(intentP);
                break;
            case R.id.llLogout:
                logoutAppDialog();
                break;
            case R.id.btnAboutApp:
                Intent intentAboutApp = new Intent(mContext, ActivityAboutApp.class);
                startActivity(intentAboutApp);
                break;
            case R.id.btnOpenSourceLibrary:
                Intent intentOpenSourceLibrary = new Intent(mContext, ActivityOpenSourceLibrary.class);
                startActivity(intentOpenSourceLibrary);
                break;
            case R.id.llPayment:
               startActivity(new Intent(mContext, ActivityPaymentHistory.class));
                break;
        }
    }

    private void logoutAppDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(getResources().getString(R.string.Are_you_sure_you_want_to_logout))
                .setCancelable(false)

                .setTitle(getResources().getString(R.string.app_name))
                .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutApi();
                    }
                })
                .setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("arabic")) {
                    alertDialog.findViewById(android.R.id.message).setTextDirection(View.TEXT_DIRECTION_RTL);
                }
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            }
        });
        alertDialog.show();
    }

    private void logoutApi() {
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_LOGOUT)
                .addBodyParameter(AppConsts.STUDENT_ID, modelLogin.getStudentData().getStudentId())
                .setTag(AppConsts.API_LOGOUT)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainJson = new JSONObject(response);
                            if (AppConsts.TRUE.equals(mainJson.getString(AppConsts.STATUS))) {
                                sharedPref.clearAllPreferences();
                                Intent loginScreen = new Intent(mContext, ActivityLogin.class);
                                loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(loginScreen);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, getResources().getString(R.string.Something_went_wrong), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(mContext, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                    }

                });
    }
}