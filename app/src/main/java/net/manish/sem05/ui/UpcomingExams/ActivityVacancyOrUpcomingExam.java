package com.pixelnx.eacademy.ui.UpcomingExams;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.model.modelvacancies.ModelVacancies;
import com.pixelnx.eacademy.ui.UpcomingExams.adapter.AdapterVacancy;
import com.pixelnx.eacademy.ui.base.BaseActivity;
import com.pixelnx.eacademy.ui.home.ActivityHome;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomTextExtraBold;

public class ActivityVacancyOrUpcomingExam extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    Context mContext;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView rvVacancies;
    CustomTextExtraBold tvHeader;
    ImageView ivUser;
    ImageView ivBack;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    ImageView noRecordFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_vacancies);

        mContext = ActivityVacancyOrUpcomingExam.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        if(modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("arabic")){
            //manually set Directions
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        if (!ProjectUtils.checkConnection(mContext)) {
            Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
        init();
    }

    private void init() {
        rvVacancies = (RecyclerView) findViewById(R.id.rvVacancies);
        noRecordFound = findViewById(R.id.no_record_found);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        ivUser = (ImageView) findViewById(R.id.ivUser);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivUser.setVisibility(View.GONE);
        tvHeader.setText(""+getResources().getString(R.string.Upcoming_Exams));
        ivBack.setOnClickListener(this);
        rvVacancies.setLayoutManager(new GridLayoutManager(mContext, 1));

        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (ProjectUtils.checkConnection(mContext)) {
                                            getAllVacancy();
                                        } else {
                                            swipeRefreshLayout.setRefreshing(false);
                                            Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
        );
    }

    private void getAllVacancy() {

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_VACANCIES)
                .addBodyParameter(AppConsts.IS_ADMIN, modelLogin.getStudentData().getAdminId())
                .addBodyParameter(AppConsts.STUDENT_ID, modelLogin.getStudentData().getStudentId())
                .setPriority(Priority.IMMEDIATE)
                .setTag(AppConsts.API_VACANCIES)
                .build()
                .getAsObject(ModelVacancies.class, new ParsedRequestListener<ModelVacancies>() {
                    @Override
                    public void onResponse(ModelVacancies response) {

                        if (AppConsts.TRUE.equals(response.getStatus())) {
                            AdapterVacancy bookAdapter = new AdapterVacancy(mContext, response.getVacancy(), response.getFilesUrl());
                            rvVacancies.setAdapter(bookAdapter);
                            swipeRefreshLayout.setRefreshing(false);
                            noRecordFound.setVisibility(View.GONE);
                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            noRecordFound.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
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
        if (ProjectUtils.checkConnection(mContext)) {
            getAllVacancy();
        } else {
            swipeRefreshLayout.setRefreshing(false);
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
