package net.manish.sem05.ui.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;

import android.os.Bundle;
import android.text.TextUtils;

import android.util.Patterns;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import net.manish.sem05.R;

import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.batch.ModelBatchDetailsOld;
import net.manish.sem05.ui.paymentGateway.ActivityPaymentGateway;
import net.manish.sem05.ui.paymentGateway.paypal;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomEditText;
import net.manish.sem05.utils.widgets.CustomTextSemiBold;

import org.json.JSONObject;


public class ActivitySignUp extends AppCompatActivity implements View.OnClickListener {

    Context context;
    CustomEditText etUserName, etUserMobile, etUserEmail;
    RelativeLayout btnSignup;
    ModelLogin modelLogin;
    SharedPref sharedPref;
    String versionCode;
    CustomTextSemiBold tvMessage;
    String amount, batchId, paymentType;
    static ModelBatchDetailsOld.batchData batchData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        context = ActivitySignUp.this;
        sharedPref = SharedPref.getInstance(context);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);

        init();

    }


    void init() {
        etUserName = findViewById(R.id.etUserName);
        tvMessage = findViewById(R.id.tvMessage);
        etUserMobile = findViewById(R.id.etUserMobile);
        etUserEmail = findViewById(R.id.etUserEmail);
        btnSignup = findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(this);
        if (getIntent().hasExtra("amount")) {
            amount = getIntent().getStringExtra("amount");
            batchId = getIntent().getStringExtra("BatchId");
            paymentType = getIntent().getStringExtra("paymentType");
        }
        if (getIntent().hasExtra("data")) {
            batchData = (ModelBatchDetailsOld.batchData) getIntent().getSerializableExtra("data");

        }


    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    void apiSignUp(String token) {
        ProjectUtils.showProgressDialog(context, false, getResources().getString(R.string.Loading___));

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_CHECK_BATCH)
                .addBodyParameter(AppConsts.EMAIL, ""+etUserEmail.getText().toString()).build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        ProjectUtils.pauseProgressDialog();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (!jsonObject.getString(AppConsts.ISEMAILEXIST).equalsIgnoreCase(AppConsts.TRUE))
                            {
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
                            }else{
                                Toast.makeText(context,getResources().getString(R.string.EmailExist),Toast.LENGTH_SHORT).show();
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
        if (view.getId() == R.id.btnSignup) {
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

                                                    apiSignUp(task.getResult().getToken());


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
        }

    }
}