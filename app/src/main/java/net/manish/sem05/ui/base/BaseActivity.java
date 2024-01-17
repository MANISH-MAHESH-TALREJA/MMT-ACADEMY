package com.pixelnx.eacademy.ui.base;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.ui.login.ActivityLogin;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomeTextRegularBold;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseActivity extends AppCompatActivity {

    private RelativeLayout rlMain;
    Context mContext;
    ModelLogin modelLogin;
    SharedPref sharedPref;
    protected DrawerLayout drawerLayout;
    private ArrayList<String> navItems;
    AdapterNavItems adapterNavItems;
    CustomeTextRegularBold tvName;
    CircleImageView profile;
    ListView drawerListView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContext = BaseActivity.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        initBase();
    }

    void initBase(){
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        tvName = findViewById(R.id.tvName);
        tvName.setText(""+modelLogin.getStudentData().getFullName());
        rlMain = findViewById(R.id.rlMain);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerListView = findViewById(R.id.drawerListView);
        drawerLayout.requestLayout();
        profile = (CircleImageView) findViewById(R.id.profile);
        if (!modelLogin.getStudentData().getImage().isEmpty()) {
            Picasso.get().load(modelLogin.getStudentData().getImage()).placeholder(R.drawable.ic_profile).into(profile);
        }
        navItems = new ArrayList<>();
        navItems.add(mContext.getResources().getString(R.string.home));
        navItems.add(mContext.getResources().getString(R.string.my_batches));
        navItems.add(mContext.getResources().getString(R.string.Edit_Profile));
        navItems.add(mContext.getResources().getString(R.string.Payment_History));
        navItems.add(mContext.getResources().getString(R.string.Apply_Leave));
        navItems.add(mContext.getResources().getString(R.string.Download_Certificate));
        navItems.add(mContext.getResources().getString(R.string.Privacy_Policy));
        navItems.add(mContext.getResources().getString(R.string.About_App));
        navItems.add(mContext.getResources().getString(R.string.Open_Source_Library));
        navItems.add(mContext.getResources().getString(R.string.Logout));

        adapterNavItems = new AdapterNavItems(mContext, navItems,modelLogin.getStudentData().getStudentId());
        drawerListView.setAdapter(adapterNavItems);
    }

    @Override
    protected void onResume() {
        super.onResume();


        if(modelLogin != null){
            if(modelLogin.getStudentData() != null){
                if(modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("arabic")){
                    //manually set Directions

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
                if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("english")) {
                    String languageToLoad = "en"; // your language
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                }

                if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("german")) {
                    String languageToLoad = "de"; // your language
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());




                }

                if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("spanish")) {
                    String languageToLoad = "es"; // your language
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());

                }

            }

            checkLogin();
        }

    }

    public void setContentBaseView(int layoutId) {

        rlMain.addView(LayoutInflater.from(BaseActivity.this).inflate(layoutId, null, false), 0);
    }
    private void checkLogin() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {

                            return;
                        }

                        if (modelLogin != null) {
                            if (modelLogin.getStudentData() != null) {

                                AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.CHECK_LOGIN)
                                        .addBodyParameter(AppConsts.STUDENT_ID, modelLogin.getStudentData().getStudentId())
                                        .addBodyParameter(AppConsts.BATCH_ID, modelLogin.getStudentData().getBatchId())
                                        .addBodyParameter(AppConsts.TOKEN, task.getResult().getToken())
                                        .setTag(AppConsts.CHECK_LOGIN)
                                        .setPriority(Priority.IMMEDIATE)
                                        .build()
                                        .getAsString(new StringRequestListener() {
                                            @Override
                                            public void onResponse(String response) {


                                                try {

                                                    JSONObject jsonObject = new JSONObject(response);

                                                    if ("false".equalsIgnoreCase(jsonObject.getString("status"))) {

                                                        sharedPref.clearAllPreferences();
                                                        Intent loginScreen = new Intent(mContext, ActivityLogin.class);
                                                        loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(loginScreen);
                                                        finish();
                                                    }


                                                } catch (Exception e) {

                                                }
                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                Toast.makeText(mContext, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            } else {

                                Intent intent = new Intent(mContext, ActivityLogin.class);
                                intent.putExtra(AppConsts.IS_SPLASH, "true");
                                startActivity(intent);
                            }
                        } else {

                            Intent intent = new Intent(mContext, ActivityLogin.class);
                            intent.putExtra(AppConsts.IS_SPLASH, "true");
                            startActivity(intent);

                        }

                    }
                });


    }

    public String getCurrentDate(String outputFormat) {
        DateFormat dateFormat = new SimpleDateFormat(outputFormat);
        Date date = new Date();
        return dateFormat.format(date);
    }

}
