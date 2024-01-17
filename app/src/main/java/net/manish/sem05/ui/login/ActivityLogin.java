package com.pixelnx.eacademy.ui.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.ui.batch.ActivityBatch;
import com.pixelnx.eacademy.ui.forgotpassword.ActivityForgotPassword;
import com.pixelnx.eacademy.ui.multibatch.ActivityMultiBatchHome;
import com.pixelnx.eacademy.ui.signup.ActivitySignUp;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomEditText;
import com.pixelnx.eacademy.utils.widgets.CustomSmallText;
import com.pixelnx.eacademy.utils.widgets.CustomTextSemiBold;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.pixelnx.eacademy.utils.AppConsts.IS_REGISTER;

public class ActivityLogin extends AppCompatActivity implements View.OnClickListener {


    RelativeLayout btLogin;
    Context mContext;
    ModelLogin modelLogin;
    LinearLayout llLogin;
    SharedPref sharedPref;
    CustomEditText etUserName;
    CustomEditText etPassword;

    CustomTextSemiBold tvContactAdmin;
    String versionCode = "";
    CustomSmallText tvOrLoginWith, forgotPass;
    SwipeRefreshLayout swipeRefreshLayout;
    static String checkLanguage = "";
    LinearLayout btnSignIn,tvMove;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mContext = ActivityLogin.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);



        initial();
    }


    private void initial() {

        btLogin = findViewById(R.id.btLogin);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
        forgotPass = findViewById(R.id.forgotPass);
        forgotPass.setOnClickListener(this);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ProjectUtils.checkConnection(mContext)) {

                    languageDynamic();


                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvOrLoginWith = findViewById(R.id.tvOrLoginWith);
        if (modelLogin != null) {
            if (modelLogin.getStudentData() != null) {
                tvOrLoginWith.setVisibility(View.VISIBLE);
                tvOrLoginWith.setText(getResources().getString(R.string.orLoginWith) + " " + modelLogin.getStudentData().getFullName());
            }
        } else {
            tvOrLoginWith.setVisibility(View.GONE);
        }

        etUserName = (CustomEditText) findViewById(R.id.etUserName);

        etPassword = (CustomEditText) findViewById(R.id.etPassword);
        tvMove = findViewById(R.id.tvMove);
        tvContactAdmin = (CustomTextSemiBold) findViewById(R.id.tvContactAdmin);
        llLogin = (LinearLayout) findViewById(R.id.llLogin);
        btLogin.setOnClickListener(this);
        tvOrLoginWith.setOnClickListener(this);
        tvMove.setOnClickListener(this);


        try {
            PackageInfo pInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            versionCode = String.valueOf(pInfo.versionCode);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        languageDynamic();

    }


    void languageDynamic() {
        swipeRefreshLayout.setRefreshing(false);

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_CHECKLANGUAGE)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("true".equalsIgnoreCase(jsonObject.getString("status"))) {
                        if (jsonObject.getString("languageName").equalsIgnoreCase("arabic")) {
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

                            if (!checkLanguage.equals("ar")) {
                                recreate();
                            }
                            checkLanguage = "ar";

                        }
                        if (jsonObject.getString("languageName").equalsIgnoreCase("french")) {
                            String languageToLoad = "fr"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("fr")) {
                                recreate();
                            }
                            checkLanguage = "fr";

                        }
                        if (jsonObject.getString("languageName").equalsIgnoreCase("english")) {
                            String languageToLoad = "en"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("en")) {
                                recreate();
                            }
                            checkLanguage = "en";


                        }
                        if (jsonObject.getString("languageName").equalsIgnoreCase("hindi")) {
                            String languageToLoad = "hi"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("hi")) {
                                recreate();
                            }
                            checkLanguage = "hi";


                        }
                        if (jsonObject.getString("languageName").equalsIgnoreCase("german")) {
                            String languageToLoad = "de"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("de")) {
                                recreate();
                            }
                            checkLanguage = "de";


                        }

                        if (jsonObject.getString("languageName").equalsIgnoreCase("spanish")) {
                            String languageToLoad = "es"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("es")) {
                                recreate();
                            }
                            checkLanguage = "es";


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });


    }

    private void loginApi(String token) {

        ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));
        Log.v("saloni","saloni  "+etUserName.getText().toString());
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_LOGIN)
                .addBodyParameter(AppConsts.USERNAME, etUserName.getText().toString())
                .addBodyParameter(AppConsts.PASSWORD, etPassword.getText().toString())
                .addBodyParameter(AppConsts.TOKEN, token)
                .addBodyParameter(AppConsts.VERSION_CODE, "" + versionCode)
                .setTag(AppConsts.API_LOGIN)
                .build()
                .getAsObject(ModelLogin.class, new ParsedRequestListener<ModelLogin>() {
                    @Override
                    public void onResponse(ModelLogin response) {
Log.v("TESTT","saloni  "+response);
                        ProjectUtils.pauseProgressDialog();
                        if (AppConsts.TRUE.equals(response.getStatus())) {

                            Toast.makeText(mContext, response.getMsg(), Toast.LENGTH_SHORT).show();

                            tvContactAdmin.setVisibility(View.GONE);
                            sharedPref.setUser(AppConsts.STUDENT_DATA, response);
                            sharedPref.setBooleanValue(IS_REGISTER, true);
                            etUserName.setText("");
                            etPassword.setText("");
                            Calendar cal = Calendar.getInstance();
                            String format = new SimpleDateFormat("E, MMM d, yyyy").format(cal.getTime());
                            sharedPref.setDate("date", format);
                            startActivity(new Intent(mContext, ActivityMultiBatchHome.class).putExtra(AppConsts.IS_SPLASH, "true"));
                            infoUpdate();

                        } else {

                            tvContactAdmin.setVisibility(View.VISIBLE);
                            String string = "";
                            string = " " + response.getMsg();
                            Spanned sp = Html.fromHtml(string);
                            tvContactAdmin.setText(sp);

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.v("TESTT","saloni  "+anError);
                        tvContactAdmin.setVisibility(View.VISIBLE);
                        String string = "" + getResources().getString(R.string.Try_again_server_issue);
                        Spanned sp = Html.fromHtml(string);
                        tvContactAdmin.setText(sp);
                        ProjectUtils.pauseProgressDialog();

                    }
                });

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btLogin) {
            if (!etUserName.getText().toString().isEmpty()) {
                if (!etPassword.getText().toString().isEmpty()) {
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(Task<InstanceIdResult> task) {
                                    if (!task.isSuccessful()) {

                                        return;

                                    }
                                    if (ProjectUtils.checkConnection(mContext)) {

                                        loginApi(task.getResult().getToken());


                                    } else {

                                        Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                                    }


                                }
                            });

                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.Please_Enter_Password), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(mContext, getResources().getString(R.string.Please_Enter_EnrollmentId), Toast.LENGTH_SHORT).show();
            }
        } else {
            if (v.getId() == R.id.tvMove) {
                Intent intent = new Intent(mContext, ActivityBatch.class);
                startActivity(intent);


            } else {
                if (v.getId() == R.id.tvOrLoginWith) {
                    if (modelLogin != null) {
                        if (modelLogin.getStudentData().getBatchId().isEmpty()) {
                            Intent intent = new Intent(mContext, ActivityBatch.class);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(mContext, ActivityBatch.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        }
        if (v.getId() == R.id.forgotPass) {
            startActivity(new Intent(mContext, ActivityForgotPassword.class));

        }
        if(v.getId() == R.id.btnSignIn) {
            startActivity(new Intent(mContext, ActivitySignUp.class).putExtra("login","Withoutbatch"));
        }}

    private void exitAppDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(getResources().getString(R.string.Are_you_sure_you_want_to_close_this_app))

                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }

    @Override
    public void onBackPressed() {


        exitAppDialog();
    }


    void infoUpdate() {

        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_HOME_LAST_LOGIN_TIME)
                .addBodyParameter(AppConsts.STUDENT_ID, "" + modelLogin.getStudentData().getStudentId())
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").contains("true")) {
                                Calendar cal = Calendar.getInstance();
                                String format = new SimpleDateFormat("E, MMM d, yyyy").format(cal.getTime());
                                sharedPref.setDate("date", "" + format);


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(mContext, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();

                    }
                });
    }


}
