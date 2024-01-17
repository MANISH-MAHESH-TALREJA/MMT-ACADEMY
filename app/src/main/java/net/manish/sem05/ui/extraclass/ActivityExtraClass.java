package com.pixelnx.eacademy.ui.extraclass;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modelextraClass.ModelExtraClass;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.ui.home.ActivityHome;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomTextExtraBold;

import java.util.ArrayList;

public class ActivityExtraClass extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rvExtraClass;
    ProgressBar progressBar;
    ImageView ivNoData;
    FloatingActionButton btPrevious;
    FloatingActionButton btNext;
    FloatingActionMenu fabMenu;
    Context mContext;
    StaggeredGridLayoutManager linearLayoutManager;
    int pastVisibleItems;
    boolean isLoading = false;
    boolean tag = true;
    AdapterExtraClass adapterExtraClass;
    CustomTextExtraBold tvHeader;
    ArrayList<ModelExtraClass.ExtraClass> extraClasses;
    ImageView ivBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_extra_class);
        mContext = ActivityExtraClass.this;
        init();
    }

    private void init() {

        ivBack = findViewById(R.id.ivBack);
        rvExtraClass = findViewById(R.id.rvExtraClass);
        progressBar = findViewById(R.id.progressBar);
        ivNoData = findViewById(R.id.ivNoData);
        tvHeader = findViewById(R.id.tvHeader);
        fabMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        btPrevious = (FloatingActionButton) findViewById(R.id.btPrevious);
        btNext = (FloatingActionButton) findViewById(R.id.btNext);
        btNext.setVisibility(View.GONE);
        btNext.setOnClickListener(this);
        btPrevious.setOnClickListener(this);
        ivBack.setOnClickListener(this);

        if (ProjectUtils.checkConnection(mContext))
            getExtraClasses("upcomming");
        else {
            Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();

            progressBar.setVisibility(View.GONE);
        }

        tvHeader.setText(getResources().getString(R.string.ExtraClass));


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btNext:
                btPrevious.setVisibility(View.VISIBLE);
                btNext.setVisibility(View.GONE);
                tvHeader.setText(""+getResources().getString(R.string.UpcomingClasses));
                fabMenu.close(true);
                getExtraClasses("upcomming");

                break;

            case R.id.ivBack:

                if ("push".equalsIgnoreCase(getIntent().getStringExtra(AppConsts.IS_PUSH))) {

                    startActivity(new Intent(ActivityExtraClass.this, ActivityHome.class));
                    finish();
                } else {

                    if (tag) {
                        finish();
                    } else {
                        fabMenu.close(true);
                        tvHeader.setText(""+getResources().getString(R.string.ExtraClass));
                        getExtraClasses("upcomming");

                        tag = true;
                    }
                }

                break;


            case R.id.btPrevious:
                tag = false;
                tvHeader.setText(""+getResources().getString(R.string.PreviousClasses));
                btNext.setVisibility(View.VISIBLE);
                btPrevious.setVisibility(View.GONE);
                getExtraClasses("previous");
                fabMenu.close(true);
                break;

        }
    }


    private void getExtraClasses(final String str) {
        progressBar.setVisibility(View.VISIBLE);
        linearLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        rvExtraClass.setAdapter(null);
        SharedPref pref = SharedPref.getInstance(mContext);
        ModelLogin login = pref.getUser(AppConsts.STUDENT_DATA);

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.EXTRA_CLASS)
                .addBodyParameter(AppConsts.IS_ADMIN, login.getStudentData().getAdminId())
                .addBodyParameter(AppConsts.BATCH_ID, login.getStudentData().getBatchId())
                .addBodyParameter(AppConsts.STUDENT_ID, login.getStudentData().getStudentId())
                .addBodyParameter(AppConsts.TYPE, str)
                .addBodyParameter(AppConsts.PAGE_NO, "0")
                .setTag(AppConsts.EXTRA_CLASS)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(ModelExtraClass.class, new ParsedRequestListener<ModelExtraClass>() {
                    @Override
                    public void onResponse(ModelExtraClass response) {
                        progressBar.setVisibility(View.GONE);
                        if ("true".equalsIgnoreCase(response.getStatus())) {
                            isLoading = false;
                            ivNoData.setVisibility(View.GONE);
                            extraClasses = new ArrayList<>();
                            extraClasses = response.getExtraClass();
                            rvExtraClass.setLayoutManager(linearLayoutManager);
                            adapterExtraClass = new AdapterExtraClass(mContext, extraClasses);
                            rvExtraClass.setAdapter(adapterExtraClass);
                        } else {
                            ivNoData.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            isLoading = false;

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        progressBar.setVisibility(View.GONE);
                        ivNoData.setVisibility(View.VISIBLE);
                        isLoading = false;

                    }
                });


        rvExtraClass.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int[] firstVisibleItems = null;
                firstVisibleItems = linearLayoutManager.findFirstVisibleItemPositions(firstVisibleItems);
                if (firstVisibleItems != null && firstVisibleItems.length > 0) {
                    pastVisibleItems = firstVisibleItems[0];
                }

                if (dy > 0) {
                    if (!isLoading) {
                        isLoading = false;

                    }
                }
            }
        });


    }


    @Override
    public void onBackPressed() {

        if ("push".equalsIgnoreCase(getIntent().getStringExtra(AppConsts.IS_PUSH))) {

            startActivity(new Intent(ActivityExtraClass.this, ActivityHome.class));
            finish();
        } else {

            if (tag) {

                finish();
            } else {
                fabMenu.close(true);

                tvHeader.setText(""+getResources().getString(R.string.ExtraClass));
                getExtraClasses("upcoming");
                tag = true;
            }
        }
    }
}
