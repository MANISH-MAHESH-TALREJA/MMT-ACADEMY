package net.manish.sem05.ui.signup;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import net.manish.sem05.R;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.batch.ActivityBatch;
import net.manish.sem05.ui.batch.ModelCatSubCat;
import net.manish.sem05.ui.login.ActivityLogin;
import net.manish.sem05.ui.paymentGateway.ActivityPaymentGateway;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomEditText;
import net.manish.sem05.utils.widgets.CustomTextSemiBold;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import static net.manish.sem05.utils.AppConsts.IS_REGISTER;


public class ActivitySignUp extends AppCompatActivity implements View.OnClickListener {

    Context context;
    CustomEditText etUserName, etUserMobile, etUserEmail;
    RelativeLayout btnSignup;
    ModelLogin modelLogin;
    SharedPref sharedPref;
    String versionCode;
    CustomTextSemiBold tvMessage;
    String amount, batchId, paymentType;
    static ModelCatSubCat.batchData.SubCategory.BatchData batchData;
    LinearLayout loginTv, tvMove;
    static String checkLanguage = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        context = ActivitySignUp.this;
        sharedPref = SharedPref.getInstance(context);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);

        init();

    }

    void languageDynamic() {


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

            }
        });


    }
    void init() {
        etUserName = findViewById(R.id.etUserName);
        loginTv = findViewById(R.id.loginTv);
        loginTv.setOnClickListener(this);
        tvMove = findViewById(R.id.tvMove);
        tvMove.setOnClickListener(this);
        tvMessage = findViewById(R.id.tvMessage);
        etUserMobile = findViewById(R.id.etUserMobile);
        etUserEmail = findViewById(R.id.etUserEmail);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(this);
        languageDynamic();
        if (getIntent().hasExtra("amount")) {
            amount = getIntent().getStringExtra("amount");
            batchId = getIntent().getStringExtra("BatchId");
            paymentType = getIntent().getStringExtra("paymentType");
        }
        if (getIntent().hasExtra("data")) {
            batchData = (ModelCatSubCat.batchData.SubCategory.BatchData) getIntent().getSerializableExtra("data");
        }


    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    void apiSignUp(String token) {
        ProjectUtils.showProgressDialog(context, false, getResources().getString(R.string.Loading___));

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_CHECK_BATCH)
                .addBodyParameter(AppConsts.EMAIL, "" + etUserEmail.getText().toString()).build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ProjectUtils.pauseProgressDialog();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getString(AppConsts.ISEMAILEXIST).equalsIgnoreCase(AppConsts.TRUE)) {

                                PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                                versionCode = String.valueOf(pInfo.versionCode);
                                Intent intent = new Intent(context, ActivityPaymentGateway.class);
                                intent.putExtra("versionCode", "" + versionCode);
                                intent.putExtra("paymentType", "" + paymentType);
                                intent.putExtra("token", "" + token);
                                intent.putExtra("amount", "" + amount);
                                intent.putExtra("BatchId", "" + batchId);
                                intent.putExtra("name", "" + etUserName.getText().toString());
                                intent.putExtra("email", "" + etUserEmail.getText().toString());
                                intent.putExtra("mobile", "" + etUserMobile.getText().toString());
                                intent.putExtra("data", batchData);

                                startActivity(intent);
                            } else {
                                Toast.makeText(context, getResources().getString(R.string.EmailExist), Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtils.pauseProgressDialog();
                        Toast.makeText(context, getResources().getString(R.string.Try_again_server_issue), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.tvMove:
                Intent intent = new Intent(context, ActivityBatch.class);
                startActivity(intent);



            break;
            case R.id.loginTv:
                startActivity(new Intent(context, ActivityLogin.class));
                break;
            case R.id.btnSignup:
                if (ProjectUtils.checkConnection(context)) {


                    if (etUserName.getText().toString().isEmpty()) {
                        etUserName.setError(getResources().getString(R.string.Please_Enter_name));
                        etUserName.requestFocus();
                    } else {
                        if (etUserEmail.getText().toString().isEmpty()) {
                            etUserEmail.setError("" + getResources().getString(R.string.EnterEmail));
                            etUserEmail.requestFocus();

                        } else {
                            if (etUserMobile.getText().toString().isEmpty()) {
                                etUserMobile.setError("" + getResources().getString(R.string.EnterMobile));
                                etUserMobile.requestFocus();


                            } else {
                                if (isValidEmail(etUserEmail.getText().toString())) {
                                    if (etUserMobile.getText().toString().length() > 6) {
                                        FirebaseInstanceId.getInstance().getInstanceId()
                                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                    @Override
                                                    public void onComplete(Task<InstanceIdResult> task) {
                                                        if (!task.isSuccessful()) {

                                                            return;

                                                        }

                                                        if (getIntent().hasExtra("login")) {
                                                        if (getIntent().getStringExtra("login").equalsIgnoreCase("Withoutbatch")) {


                                                            ProjectUtils.showProgressDialog(context, true, getResources().getString(R.string.Loading___));
                                                        /* Log.v("saloni123","  "+etUserName.getText().toString()+" , "+etUserEmail.getText().toString()+" ," +
                                                                 " "+ etUserMobile.getText().toString()+"  , "+ task.getResult().getToken()+" "+versionCode );*/

                                                            AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_STUDENT_REGISTRATION)
                                                                    .addBodyParameter(AppConsts.NAME, "" + etUserName.getText().toString())
                                                                    .addBodyParameter(AppConsts.EMAIL, "" + etUserEmail.getText().toString())
                                                                    .addBodyParameter(AppConsts.MOBILE, "" + etUserMobile.getText().toString())
                                                                    .addBodyParameter(AppConsts.TOKEN, "" + task.getResult().getToken())
                                                                    .addBodyParameter(AppConsts.VERSION_CODE, "" + versionCode)
                                                                    .build()
                                                                    .getAsObject(ModelLogin.class, new ParsedRequestListener<ModelLogin>() {
                                                                        @Override
                                                                        public void onResponse(ModelLogin response) {
                                                                            if (response.getStatus().equalsIgnoreCase("true")) {

                                                                                sharedPref.setUser(AppConsts.STUDENT_DATA, response);
                                                                                sharedPref.setBooleanValue(IS_REGISTER, true);
                                                                                modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
                                                                                Intent intent = new Intent(context, ActivityPaymentGateway.class).putExtra("login", "Withoutbatch");
                                                                                intent.putExtra("name", "" + etUserName.getText().toString());
                                                                                intent.putExtra("email", "" + etUserEmail.getText().toString());
                                                                                intent.putExtra("mobile", "" + etUserMobile.getText().toString());
                                                                                intent.putExtra("token", "" + task.getResult().getToken());
                                                                                intent.putExtra("versionCode", "" + versionCode);
                                                                                startActivity(intent);
                                                                                ProjectUtils.pauseProgressDialog();

                                                                            } else {
                                                                                ProjectUtils.pauseProgressDialog();
                                                                                Toast.makeText(context, "" + response.getMsg(), Toast.LENGTH_SHORT).show();
                                                                            }

                                                                        }

                                                                        @Override
                                                                        public void onError(ANError anError) {
                                                                            ProjectUtils.pauseProgressDialog();

                                                                        }
                                                                    });


                                                        }
                                                        } else {

                                                            apiSignUp(task.getResult().getToken());
                                                        }


                                                    }
                                                });


                                    } else {
                                        Toast.makeText(context, "" + getResources().getString(R.string.Entervalidnumber), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    etUserEmail.setError("" + getResources().getString(R.string.InvalidEmail));
                                    etUserEmail.requestFocus();
                                }


                            }
                        }
                    }

                } else {

                    Toast.makeText(context, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }
}