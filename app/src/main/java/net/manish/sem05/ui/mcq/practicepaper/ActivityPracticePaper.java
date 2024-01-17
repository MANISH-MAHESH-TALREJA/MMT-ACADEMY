package com.pixelnx.eacademy.ui.mcq.practicepaper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.model.modelpracticepaper.ModelPracticePaper;
import com.pixelnx.eacademy.ui.base.BaseActivity;
import com.pixelnx.eacademy.ui.home.ActivityHome;
import com.pixelnx.eacademy.ui.login.ActivityLogin;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomTextExtraBold;

import org.json.JSONObject;

import java.util.ArrayList;


public class ActivityPracticePaper extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    Context mContext;
    RecyclerView rvPracticePaper;
    ArrayList<ModelPracticePaper.ExamPaper> practiceList;
    CustomTextExtraBold tvHeader;
    ImageView ivUser;
    ImageView ivBack;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    AdapterPracticePaper adapterPracticePaper;
    private SwipeRefreshLayout swipeRefreshLayout;
    private String examType = "";
    CustomTextExtraBold tvEnable;
    ImageView noRecordFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_practice_paper);

        mContext = ActivityPracticePaper.this;

        if (!ProjectUtils.checkConnection(mContext)) {
            Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
        noRecordFound = findViewById(R.id.no_record_found);
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        practiceList = new ArrayList<>();
        examType = getIntent().getStringExtra(AppConsts.EXAM_TYPE);
        init();

        if ("practice".equals(examType)) {
            tvHeader.setText(getResources().getString(R.string.Practice_Paper));
        } else if ("exam".equals(examType)) {
            tvHeader.setText("Test Paper");
        } else {
            tvHeader.setText(""+getResources().getString(R.string.Mock_Test_Paper));
        }
    }


    private void practicePaperApi() {
        Log.v("TESTT","TESTT  "+examType);
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_PRACTICE_PAPER)
                .addBodyParameter(AppConsts.IS_ADMIN, modelLogin.getStudentData().getAdminId())
                .addBodyParameter(AppConsts.EXAM_TYPE, examType)
                .addBodyParameter(AppConsts.STUDENT_ID, modelLogin.getStudentData().getStudentId())
                .addBodyParameter(AppConsts.BATCH_ID, modelLogin.getStudentData().getBatchId())
                .setTag(AppConsts.API_PRACTICE_PAPER)
                .build()
                .getAsObject(ModelPracticePaper.class, new ParsedRequestListener<ModelPracticePaper>() {
                    @Override
                    public void onResponse(ModelPracticePaper response) {

                        practiceList = new ArrayList<>();
                        ProjectUtils.pauseProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                        if (response.getStatus().equals("true")) {
                            noRecordFound.setVisibility(View.GONE);
                            ProjectUtils.pauseProgressDialog();
                            practiceList = response.getExamPaper();
                            if (practiceList == null) {
                                Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();
                            } else {
                                if (practiceList.size() == 0) {
                                    Toast.makeText(mContext, "No Data Found", Toast.LENGTH_SHORT).show();

                                    return;
                                }
                            }

                            adapterPracticePaper = new AdapterPracticePaper(mContext, practiceList);
                            rvPracticePaper.setLayoutManager(new LinearLayoutManager(mContext));
                            rvPracticePaper.setAdapter(adapterPracticePaper);
                        } else {

                            noRecordFound.setVisibility(View.VISIBLE);
                            adapterPracticePaper = new AdapterPracticePaper(mContext, practiceList);
                            rvPracticePaper.setLayoutManager(new LinearLayoutManager(mContext));
                            rvPracticePaper.setAdapter(adapterPracticePaper);

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtils.pauseProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkLogin();
        practicePaperApi();

    }

    private void checkLogin() {
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
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
                                                    Toast.makeText(mContext, "Please Restart App.", Toast.LENGTH_SHORT).show();
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

    private void init() {
        rvPracticePaper = (RecyclerView) findViewById(R.id.rvPracticePaper);
        tvEnable = (CustomTextExtraBold) findViewById(R.id.tvEnable);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        ivUser = (ImageView) findViewById(R.id.ivUser);
        ivBack = (ImageView) findViewById(R.id.ivBack);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ProjectUtils.checkConnection(mContext)) {
                                            practicePaperApi();
                                        } else {
                                            Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
        );

        ivUser.setVisibility(View.GONE);
        ivBack.setOnClickListener(this);
        tvHeader.setText(getIntent().getStringExtra(AppConsts.EXAM_TYPE));
    }

    @Override
    public void onClick(View v) {
        if ("push".equalsIgnoreCase(getIntent().getStringExtra(AppConsts.IS_PUSH))) {
            startActivity(new Intent(mContext, ActivityHome.class));
            finish();
        } else finish();
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        if (ProjectUtils.checkConnection(mContext)) {
            practicePaperApi();
        } else {
            Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if ("push".equalsIgnoreCase(getIntent().getStringExtra(AppConsts.IS_PUSH))) {
            startActivity(new Intent(mContext, ActivityHome.class));
            finish();
        } else finish();

    }
}
