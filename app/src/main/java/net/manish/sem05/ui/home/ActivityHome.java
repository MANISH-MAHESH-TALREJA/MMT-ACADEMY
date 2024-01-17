package com.pixelnx.eacademy.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.ModelDynamicNotices;
import com.pixelnx.eacademy.model.ModelNewChanges;
import com.pixelnx.eacademy.model.modelliveclassdata.ModelLiveClassData;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.model.modeltopscore.ModelTopScorer;
import com.pixelnx.eacademy.ui.UpcomingExams.ActivityVacancyOrUpcomingExam;
import com.pixelnx.eacademy.ui.assignment.ActivityAssignment;
import com.pixelnx.eacademy.ui.base.BaseActivity;
import com.pixelnx.eacademy.ui.extraclass.ActivityExtraClass;
import com.pixelnx.eacademy.ui.galary.galleryvideos.ActivityVimeoVideo;
import com.pixelnx.eacademy.ui.galary.galleryvideos.ExoplayerVideos;
import com.pixelnx.eacademy.ui.home.adapter.HomeAdapter;
import com.pixelnx.eacademy.ui.library.ActivityPdf;

import com.pixelnx.eacademy.ui.live.ActivityLive;
import com.pixelnx.eacademy.ui.live.JitsiActivity;
import com.pixelnx.eacademy.ui.login.ActivityLogin;
import com.pixelnx.eacademy.ui.mcq.practicepaper.ActivityPracticePaper;
import com.pixelnx.eacademy.ui.mcq.practiceresult.ActivityPracticeResult;
import com.pixelnx.eacademy.ui.multibatch.ActivityMultiBatchHome;
import com.pixelnx.eacademy.ui.multibatch.ActivityMyBatch;
import com.pixelnx.eacademy.ui.noticeAnnouncement.ActivityForFragments;
import com.pixelnx.eacademy.ui.settingdashboard.ActivitySettingDashboard;
import com.pixelnx.eacademy.ui.video.ActivityYoutubeVideo;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomSmallText;
import com.pixelnx.eacademy.utils.widgets.CustomTextBold;
import com.pixelnx.eacademy.utils.widgets.CustomTextExtraBold;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityHome extends BaseActivity {
    Context mContext;
    ArrayList<String> listData;
    RecyclerView rvHome;
    RecyclerView rvNotice;
    RecyclerView rvRank;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    String numberMeeting = "";
    String passwordMeeting = "";
    String sdkKey = "";
    String secretKey = "";
    CustomTextExtraBold tvHeader, batchName;
    CustomSmallText extraCount;
    CustomSmallText mockCount;
    CustomSmallText vacancyCount;
    CustomSmallText extra;
    CustomSmallText mockPapers;
    CustomSmallText vacancy;
    CustomSmallText welcomeHead;
    CustomSmallText tvTodayDate;
    CustomSmallText tvTop;
    CustomSmallText tvLiveClasses;
    CustomSmallText tvLiveClasses2;
    CustomSmallText tvTopic;
    CustomSmallText tvTopic2;
    CustomSmallText tvYourScore;
    SwipeRefreshLayout swipe;
    LinearLayout llLive,llLive2, llExams, llMockPapers, llExtraClass,llLiveclasses2;
    CustomSmallText nameTv;
    CustomSmallText nameTv2;
    ImageView liveClassImage,liveClassImage2, imageView,imageView1,ivBack;
    TextView countNotice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentBaseView(R.layout.activity_newhome);
        mContext = ActivityHome.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        vacancy = findViewById(R.id.vacancy);
        tvLiveClasses = findViewById(R.id.tvLiveclasses);
        tvLiveClasses2 = findViewById(R.id.tvLiveclasses2);
        extraCount = findViewById(R.id.extraCount);
        mockCount = findViewById(R.id.mockCount);
        vacancyCount = findViewById(R.id.vacancyCount);
        nameTv = findViewById(R.id.nameTv);
        imageView = findViewById(R.id.imageView);
        imageView1 = findViewById(R.id.imageView1);
        nameTv2 = findViewById(R.id.nameTv2);
        llLiveclasses2 = findViewById(R.id.llLiveclasses2);
        llLive = findViewById(R.id.llLive);
        llLive2 = findViewById(R.id.llLive2);
        liveClassImage = findViewById(R.id.liveClassImage);
        liveClassImage2 = findViewById(R.id.liveClassImage2);
        tvTop = findViewById(R.id.tvTop);
        tvTopic = findViewById(R.id.tvTopic);
        tvTopic2 = findViewById(R.id.tvTopic2);
        tvYourScore = findViewById(R.id.tvYourScore);
        tvTodayDate = findViewById(R.id.tvTodayDate);
        welcomeHead = findViewById(R.id.welcomhead);
        swipe = findViewById(R.id.swipe);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setImageDrawable(getResources().getDrawable(R.drawable.menu));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (ProjectUtils.checkConnection(mContext)) {
                    apiMeetingData();
                    newUpdates();
                    topScorer();
                    checkLogin();
                    addDataToList();
                    swipe.setRefreshing(false);
                } else {
                    swipe.setRefreshing(false);
                    Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                }
            }
        });
        rvNotice = findViewById(R.id.rvNotis);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvNotice.setLayoutManager(linearLayoutManager);
        extra = findViewById(R.id.extra);
        mockPapers = findViewById(R.id.mockPapers);

        init();


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

    void newUpdates() {


        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_HOME_DB_NEW_CHANGES)
                .addBodyParameter(AppConsts.STUDENT_ID, "" + modelLogin.getStudentData().getStudentId())
                .addBodyParameter(AppConsts.BATCH_ID, "" + modelLogin.getStudentData().getBatchId())
                .addBodyParameter(AppConsts.IS_ADMIN, "" + modelLogin.getStudentData().getAdminId())
                .build()
                .getAsObject(ModelNewChanges.class, new ParsedRequestListener<ModelNewChanges>() {
                    @Override
                    public void onResponse(ModelNewChanges response) {


                        tvTodayDate = findViewById(R.id.tvTodayDate);
                        welcomeHead = findViewById(R.id.welcomhead);
                        if (response.getStatus().equalsIgnoreCase("true")) {

                            tvTodayDate.setVisibility(View.VISIBLE);
                            welcomeHead.setVisibility(View.VISIBLE);
                            vacancyCount.setText("" + response.getVacancy().size());
                            extraCount.setText("" + response.getExtraClass().size());
                            mockCount.setText("" + response.getMockPaper().size());

                            vacancy.setText(getResources().getString(R.string.Exams));
                            extra.setText(getResources().getString(R.string.ExtraClass));
                            mockPapers.setText(getResources().getString(R.string.MockPaper));

                            ArrayList<ModelDynamicNotices> arrayList = new ArrayList<>();
                            if (response.getAddNewBook().size() > 0) {
                                arrayList.add(new ModelDynamicNotices(getResources().getString(R.string.NewBooks) + " (" + response.getAddNewBook().size() + ")", "" + response.getAddNewBook().get(0).getTitle(), "" + response.getAddNewBook().get(0).getSubject() + "\n" + response.getAddNewBook().get(0).getTopic(), "", "", ""));
                            }
                            if (response.getAddNewNotes().size() > 0) {
                                arrayList.add(new ModelDynamicNotices(getResources().getString(R.string.NewNotes)+ " (" + response.getAddNewNotes().size() + ")", "" + response.getAddNewNotes().get(0).getTitle() + "\n" + response.getAddNewNotes().get(0).getSubject(), "" + response.getAddNewNotes().get(0).getTopic(), "" + response.getAddNewNotes().get(0).getTopic(), "", ""));
                            }
                            if (response.getAddOldPaper().size() > 0) {
                                arrayList.add(new ModelDynamicNotices(getResources().getString(R.string.OldPapersAdded) + " (" + response.getAddOldPaper().size() + ")", "" + response.getAddOldPaper().get(0).getTitle(), "" + response.getAddOldPaper().get(0).getSubject() + "\n" + response.getAddOldPaper().get(0).getTopic(), "" + response.getAddOldPaper().get(0).getTopic(), "", ""));
                            }
                            String gender = "";
                            if (response.getExtraClass().size() > 0) {
                                if (response.getExtraClass().get(0).getTeachGender().equalsIgnoreCase("male")) {
                                    gender = " " + getResources().getString(R.string.Sir);
                                } else {
                                    gender = " " + getResources().getString(R.string.Madam);
                                }
                                arrayList.add(new ModelDynamicNotices(getResources().getString(R.string.ExtraClass) + " ( " + response.getExtraClass().size() + " )",
                                        "" + response.getExtraClass().get(0).getDescription(),
                                        getResources().getString(R.string.By) + " : " + response.getExtraClass().get(0).getName() + gender + "\n" + getResources().getString(R.string.Date) + " : " +
                                                " " + response.getExtraClass().get(0).getDate(), "", "", ""));
                            }
                            if (response.getHomeWork().size() > 0) {


                                arrayList.add(new ModelDynamicNotices(getResources().getString(R.string.Assignment) + " ( " + response.getHomeWork().size() + " )", "" +
                                        response.getHomeWork().get(0).getDescription(),
                                        getResources().getString(R.string.subject) + " : " + response.getHomeWork().get(0).getSubjectName() + "\n" + getResources().getString(R.string.By) + " : " + response.getHomeWork().get(0).
                                                getName() + gender + "\n" + getResources().getString(R.string.Date) + " :" + response.getHomeWork().get(0).getDate(), "", "", ""));
                            }

                            if (response.getVideoLecture().size() > 0) {

                                arrayList.add(new ModelDynamicNotices(getResources().getString(R.string.VideoLectures) + " ( " + response.getVideoLecture().size() + " )",
                                        getResources().getString(R.string.subject) + " : " + response.getVideoLecture().get(0).getSubject() + "\n" + getResources().getString(R.string.Chapter) + " : " + response.getVideoLecture().get(0).getTopic(),
                                        getResources().getString(R.string.Title_of_video) + " : " + response.getVideoLecture().get(0).getTitle(), "" + response.getVideoLecture().get(0).getUrl(), "" + response.getVideoLecture().get(0).getVideoId(), response.getVideoLecture().get(0).getVideoType()));

                            }
                            if (response.getVacancy().size() > 0) {
                                arrayList.add(new ModelDynamicNotices(getResources().getString(R.string.Upcoming_Exams) + " ( " + response.getVacancy().size() + " )",
                                        getResources().getString(R.string.Exams) + "  : " + response.getVacancy().get(0).getTitle() + "\n" + response.getVacancy().get(0).getDescription(),
                                        "\n" + getResources().getString(R.string.Mode_of_exam) + " :" +
                                                " " + response.getVacancy().get(0).getMode() + "\n" + getResources().getString(R.string.Date) + " " + response.getVacancy().get(0).getStartDate(), "", "", ""));
                            }
                            if (response.getNotices().size() > 0) {
                                arrayList.add(new ModelDynamicNotices(getResources().getString(R.string.Notices) + " ( " + response.getNotices().size() + " )", "" + response.getNotices().get(0).getTitle(),
                                        "" + response.getNotices().get(0).getDescription(), "", "", ""));
                            }
                            if (response.getPracticePaper().size() > 0) {
                                arrayList.add(new ModelDynamicNotices(getResources().getString(R.string.Practice_Paper) + " ( " + response.getPracticePaper().size() + " )", "" + response.getPracticePaper().get(0).getName(),
                                        getResources().getString(R.string.TotalQuestions) + " : " + response.getPracticePaper().get(0).getTotalQuestion() + "" +
                                                "\n" + getResources().getString(R.string.Duration) + " : " + response.getPracticePaper().get(0).getTimeDuration(), "", "", ""));
                            }
                            if (response.getMockPaper().size() > 0) {
                                arrayList.add(new ModelDynamicNotices(getResources().getString(R.string.MockPaper) + "  ( " + response.getMockPaper().size() + " )", "" + response.getMockPaper().get(0).getName(), getResources().getString(R.string.TotalQuestions) + "" +
                                        " : " + response.getMockPaper().get(0).getTotalQuestion() + "\n" + getResources().getString(R.string.Duration) +
                                        "\n : " + response.getMockPaper().get(0).getTimeDuration() + "\n" + getResources().getString(R.string.Date) + "/" + getResources().getString(R.string.Time) + "  " + response.getMockPaper().get(0).getMockSheduledDate() + " " + response.getMockPaper().get(0).getMockSheduledTime(), "", "", ""));
                            }
                            AdapterList adapterList = new AdapterList(arrayList);
                            rvNotice.setAdapter(adapterList);

                            if (arrayList.size() == 0) {
                                if (tvTodayDate != null && welcomeHead != null) {
                                    welcomeHead.setVisibility(View.GONE);
                                    tvTodayDate.setVisibility(View.GONE);
                                }

                            } else {
                                if (tvTodayDate != null && welcomeHead != null) {
                                    welcomeHead.setVisibility(View.VISIBLE);
                                    tvTodayDate.setVisibility(View.VISIBLE);
                                }
                            }

                        } else {
                            if (tvTodayDate != null && welcomeHead != null) {
                                welcomeHead.setVisibility(View.GONE);
                                tvTodayDate.setVisibility(View.GONE);
                            }

                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(mContext, "" + getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                    }
                });

    }







    public String getCurrentDate(String outputFormat) {
        DateFormat dateFormat = new SimpleDateFormat(outputFormat);
        Date date = new Date();
        return dateFormat.format(date);
    }


    private void init() {

        welcomeHead = findViewById(R.id.welcomhead);
        rvRank = findViewById(R.id.rvRank);
        tvTodayDate = findViewById(R.id.tvTodayDate);
        llExams = findViewById(R.id.llExams);
        llMockPapers = findViewById(R.id.llMockPapers);
        llExtraClass = findViewById(R.id.llExtraClass);

        llExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ActivityVacancyOrUpcomingExam.class));
            }
        });
        llMockPapers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ActivityPracticePaper.class).putExtra(AppConsts.EXAM_TYPE, "mock_test"));

            }
        });
        llExtraClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mContext.startActivity(new Intent(mContext, ActivityExtraClass.class));
            }
        });
        newUpdates();
        Calendar cal = Calendar.getInstance();
        String format = new SimpleDateFormat("E, MMM d, yyyy").format(cal.getTime());

        tvTodayDate.setText("" + format);
        rvHome = (RecyclerView) findViewById(R.id.rvHome);

        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        batchName = (CustomTextExtraBold) findViewById(R.id.batchName);
        rvHome.setLayoutManager(new GridLayoutManager(mContext, 2));
        rvRank.setLayoutManager(new GridLayoutManager(mContext, 3));


        try {


            batchName.setText("" + modelLogin.getStudentData().getBatchName());

        } catch (Exception e) {
        }


        addDataToList();


        topScorer();
    }


    @Override
    protected void onResume() {
        super.onResume();

        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);

        apiMeetingData();
        apiLiveClass();

        try {
            tvHeader.setTextSize(18f);
            tvHeader.setText("" + modelLogin.getStudentData().getFullName());

        } catch (Exception e) {
        }

        newUpdates();

        if (rvHome != null) {
            addDataToList();
        }

    }


    void topScorer() {


        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_GET_TOP_SCORER)
                .addBodyParameter(AppConsts.BATCH_ID, modelLogin.getStudentData().getBatchId())
                .build()
                .getAsObject(ModelTopScorer.class, new ParsedRequestListener<ModelTopScorer>() {
                    @Override
                    public void onResponse(ModelTopScorer response) {

                        if (AppConsts.TRUE.equals(response.getStatus())) {
                            tvTop.setVisibility(View.VISIBLE);
                            tvYourScore.setVisibility(View.VISIBLE);
                            AdapterListTopScorer adapterList = new AdapterListTopScorer(response.getTopThree(), response.getFilesUrl());
                            rvRank.setAdapter(adapterList);

                            tvYourScore.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(mContext, ActivityPracticeResult.class);
                                    intent.putExtra(AppConsts.EXAM_TYPE, "mock_test");
                                    startActivity(intent);
                                }
                            });
                        } else {
                            tvTop.setVisibility(View.GONE);
                            tvYourScore.setVisibility(View.GONE);
                            ArrayList<ModelTopScorer.TopThree> strings = new ArrayList<>();
                            AdapterListTopScorer adapterList = new AdapterListTopScorer(strings, "");
                            rvRank.setAdapter(adapterList);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    void apiMeetingData() {

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_LIVE_CLASS_DATA)
                .addBodyParameter(AppConsts.BATCH_ID, modelLogin.getStudentData().getBatchId())
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (AppConsts.TRUE.equals("" + jsonObject.getString("status"))) {
                                JSONArray jsonArray = new JSONArray("" + jsonObject.getString("data"));
                                JSONObject jsonObject1 = new JSONObject("" + jsonArray.get(0));
                                if (jsonObject1.has("meetingNumber")) {
                                    numberMeeting = "" + jsonObject1.getString("meetingNumber");
                                    passwordMeeting = "" + jsonObject1.getString("password");
                                    sdkKey = "" + jsonObject1.getString("sdkKey");
                                    secretKey = "" + jsonObject1.getString("sdkSecret");

                                }
                                apiLiveClass();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {


                    }
                });
    }



    void apiLiveClass() {

        Log.v("TEST","TEST  "+modelLogin.getStudentData().getBatchId());
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.checkActiveLiveClassJetsi)
                .addBodyParameter(AppConsts.BATCH_ID, modelLogin.getStudentData().getBatchId())
                .build()
                .getAsObject(ModelLiveClassData.class, new ParsedRequestListener<ModelLiveClassData>() {
                    @Override
                    public void onResponse(ModelLiveClassData response) {

                        if (AppConsts.TRUE.equals(response.getStatus())) {
                            Glide.with(mContext)
                                    .load(R.drawable.red_circle)
                                    .into(imageView);
                            llLive2.setVisibility(View.VISIBLE);
                            tvLiveClasses2.setVisibility(View.VISIBLE);

                            if (response.getLiveClass() != null) {
                                Log.v("saloni","0"+ response.getLiveClass().getName());
                                nameTv2.setText("" + response.getLiveClass().getName());
                                tvTopic2.setText("" + response.getLiveClass().getSubjectName() + " (" + response.getLiveClass().getChapterName() + ")");
                                Picasso.get().load(response.getFilesUrl() + response.getLiveClass().getTeachImage()).placeholder(R.drawable.noimage).into(liveClassImage2);
                                llLive2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {



                                            mContext.startActivity(new Intent(mContext, JitsiActivity.class).
                                                    putExtra("meetroom", "" + response.getMeeting_number())
                                            );

                                    }
                                });
                            }

                        } else {
                            llLive2.setVisibility(View.GONE);
                            llLiveclasses2.setVisibility(View.GONE);

                            tvLiveClasses2.setVisibility(View.GONE);
                          //  Toast.makeText(mContext, mContext.getResources().getString(R.string.NoClassAvailable), Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                      //  Toast.makeText(mContext, mContext.getResources().getString(R.string.NoClassAvailable), Toast.LENGTH_SHORT).show();
                    }
                });
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_CHECK_ACTIVE_LIVE_CLASS)
                .addBodyParameter(AppConsts.BATCH_ID, modelLogin.getStudentData().getBatchId())
                .build()
                .getAsObject(ModelLiveClassData.class, new ParsedRequestListener<ModelLiveClassData>() {
                    @Override
                    public void onResponse(ModelLiveClassData response) {

                        if (AppConsts.TRUE.equals(response.getStatus())) {
                            llLive.setVisibility(View.VISIBLE);
                            tvLiveClasses.setVisibility(View.VISIBLE);
                            Glide.with(mContext)
                                    .load(R.drawable.red_circle)
                                    .into(imageView1);
                            if (response.getLiveClass() != null) {
                                nameTv.setText("" + response.getLiveClass().getName());
                                tvTopic.setText("" + response.getLiveClass().getSubjectName() + " (" + response.getLiveClass().getChapterName() + ")");
                                Picasso.get().load(response.getFilesUrl() + response.getLiveClass().getTeachImage()).placeholder(R.drawable.noimage).into(liveClassImage);
                                llLive.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {


                                        if (!numberMeeting.isEmpty()) {
                                            mContext.startActivity(new Intent(mContext, ActivityLive.class).
                                                    putExtra("meetingId", "" + numberMeeting).putExtra("meetingPassword", "" + passwordMeeting).putExtra("sdkKey", "" + sdkKey)
                                                    .putExtra("sdkSecret", "" + secretKey));
                                        }else{
                                            Toast.makeText(mContext, mContext.getResources().getString(R.string.NoClassAvailable), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }

                        } else {
                            llLive.setVisibility(View.GONE);
                            tvLiveClasses.setVisibility(View.GONE);
                        }


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }


    private void addDataToList() {
        listData = new ArrayList<>();

        listData.add( getResources().getString(R.string.Announcements));
        listData.add( getResources().getString(R.string.jitsi_class));
        listData.add( getResources().getString(R.string.Live_class));
        listData.add(getResources().getString(R.string.mcq));
        listData.add(getResources().getString(R.string.Upcoming_Exams));
        listData.add(getResources().getString(R.string.VideoLectures));
        listData.add(getResources().getString(R.string.Assignment));
        listData.add(getResources().getString(R.string.ExtraClass));
        listData.add(getResources().getString(R.string.DoubtClasses));
        listData.add(getResources().getString(R.string.Attendance));
        listData.add(getResources().getString(R.string.Library));
        listData.add(getResources().getString(R.string.Syllabus));


        HomeAdapter homeAdapter = new HomeAdapter(mContext, listData);

        rvHome.setAdapter(homeAdapter);

    }

    private void exitAppDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(getResources().getString(R.string.Are_you_sure_you_want_to_close_this_app))
                .setCancelable(false)
                .setTitle(getResources().getString(R.string.app_name))
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

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(mContext, ActivityMyBatch.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }








    /* -----------------------------------------------CountDownTimer Methods------------------------------------------------------------ */

    public class AdapterList extends RecyclerView.Adapter<HolderAdapterQuestionList> {

        ArrayList<ModelDynamicNotices> list;
        View view;

        public AdapterList(ArrayList<ModelDynamicNotices> list) {
            this.list = list;


        }


        @Override
        public HolderAdapterQuestionList onCreateViewHolder(ViewGroup viewGroup, int i) {
            view = LayoutInflater.from(mContext).inflate(R.layout.dynamic, viewGroup, false);

            return new HolderAdapterQuestionList(view);

        }

        @Override
        public void onBindViewHolder(HolderAdapterQuestionList holder, @SuppressLint("RecyclerView") int i) {

            holder.title.setText("" + list.get(i).getName());
            holder.description.setText("" + list.get(i).getDescp());
            holder.nameOther.setText("" + list.get(i).getOther());


            holder.rlDynamic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list.get(i).getName().contains(getResources().getString(R.string.ExtraClass))) {
                        mContext.startActivity(new Intent(mContext, ActivityExtraClass.class).putExtra("idNotice", "" + list.get(i).getId()));
                    }
                    if (list.get(i).getName().contains(getResources().getString(R.string.Assignment))) {
                        mContext.startActivity(new Intent(mContext, ActivityAssignment.class).putExtra("date", "" + holder.nameOther.getText()).putExtra("idNotice", "" + list.get(i).getId()));
                    }
                    if (list.get(i).getName().contains(getResources().getString(R.string.VideoLectures))) {

                        if (list.get(i).getVideoType().equalsIgnoreCase("youtube")) {
                            mContext.startActivity(new Intent(mContext, ActivityYoutubeVideo.class).putExtra("firebase/notice", "" + list.get(i).getOther()).putExtra("vId", "" + list.get(i).getVideoId()));
                        } else {
                            if (list.get(i).getVideoType().equalsIgnoreCase("video")) {
                                mContext.startActivity(new Intent(mContext, ExoplayerVideos.class).putExtra("WEB_URL", "" + list.get(i).getId()));
                            } else {
                                mContext.startActivity(new Intent(mContext, ActivityVimeoVideo.class).putExtra("firebase/notice", "" + list.get(i).getOther()).putExtra("WEB_URL", "" + list.get(i).getId()).putExtra("type", list.get(i).getVideoType()));
                            }
                        }
                    }
                    if (list.get(i).getName().contains(getResources().getString(R.string.Upcoming_Exams))) {
                        mContext.startActivity(new Intent(mContext, ActivityVacancyOrUpcomingExam.class).putExtra("idNotice", "" + list.get(i).getId()));
                    }
                    if (list.get(i).getName().contains(getResources().getString(R.string.Notices))) {
                        mContext.startActivity(new Intent(mContext, ActivityForFragments.class).putExtra("idNotice", "" + list.get(i).getId()));
                    }
                    if (list.get(i).getName().contains("" + getResources().getString(R.string.Practice_Paper))) {
                        mContext.startActivity(new Intent(mContext, ActivityPracticePaper.class).putExtra(AppConsts.EXAM_TYPE, "practice").putExtra("idNotice", "" + list.get(i).getId()));
                    }
                    if (list.get(i).getName().contains(getResources().getString(R.string.MockPaper))) {
                        mContext.startActivity(new Intent(mContext, ActivityPracticePaper.class).putExtra(AppConsts.EXAM_TYPE, "mock_test").putExtra("idNotice", "" + list.get(i).getId()));
                    }
                    if (list.get(i).getName().contains(getResources().getString(R.string.NewBooks))) {
                        startActivity(new Intent(mContext, ActivityPdf.class).putExtra("from", "books"));
                    }
                    if (list.get(i).getName().contains(getResources().getString(R.string.NewNotes))) {
                        startActivity(new Intent(mContext, ActivityPdf.class).putExtra("from", "notes"));
                    }
                    if (list.get(i).getName().contains(getResources().getString(R.string.OldPapersAdded))) {
                        startActivity(new Intent(mContext, ActivityPdf.class).putExtra("from", "oldpaper"));
                    }

                }
            });
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    public class HolderAdapterQuestionList extends RecyclerView.ViewHolder {

        CustomSmallText description;
        CustomSmallText nameOther;
        CustomTextBold title;
        TextView seeAllTv;
        RelativeLayout rlDynamic;

        public HolderAdapterQuestionList(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            welcomeHead = itemView.findViewById(R.id.welcomhead);
            seeAllTv = itemView.findViewById(R.id.seeAllTv);
            description = itemView.findViewById(R.id.descrip);
            nameOther = itemView.findViewById(R.id.nameOther);
            rlDynamic = itemView.findViewById(R.id.rlDynamic);

        }
    }


    public class AdapterListTopScorer extends RecyclerView.Adapter<HolderAdapterTopScoreList> {

        ArrayList<ModelTopScorer.TopThree> listTopScore;
        View view;
        String url;

        public AdapterListTopScorer(ArrayList<ModelTopScorer.TopThree> list, String url) {
            this.listTopScore = list;
            this.url = url;


        }


        @Override
        public HolderAdapterTopScoreList onCreateViewHolder(ViewGroup viewGroup, int i) {
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_top_scorer, viewGroup, false);
            return new HolderAdapterTopScoreList(view);

        }

        @Override
        public void onBindViewHolder(HolderAdapterTopScoreList holder, int i) {
            if (!url.isEmpty()) {
                try {
                    holder.tvName.setText("" + listTopScore.get(i).getName());
                    holder.tvPaperName.setText("" + listTopScore.get(i).getPaperName());
                    double percent = Double.parseDouble(listTopScore.get(i).getPercentage());


                    holder.tvPercent.setText("" + new DecimalFormat("##.##").format(percent) + " %");
                    int countRank = i + 1;
                    if (countRank == 1) {
                        holder.rankText.setText("" + countRank + "st");
                    }
                    if (countRank == 2) {
                        holder.rankText.setText("2nd");
                    }
                    if (countRank == 3) {
                        holder.rankText.setText("3rd");
                    }
                    Picasso.get().load(url + listTopScore.get(i).getImage()).placeholder(R.drawable.ic_profile).into(holder.profileTopScore);
                } catch (Exception e) {

                }

            }
        }


        @Override
        public int getItemCount() {
            return listTopScore.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    public class HolderAdapterTopScoreList extends RecyclerView.ViewHolder {
        CustomSmallText tvName;
        CustomSmallText tvPaperName;
        CustomSmallText rankText;
        CustomSmallText tvPercent;
        CircleImageView profileTopScore;

        public HolderAdapterTopScoreList(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPaperName = itemView.findViewById(R.id.tvPaperName);
            tvPercent = itemView.findViewById(R.id.tvPercent);
            rankText = itemView.findViewById(R.id.rankText);
            profileTopScore = itemView.findViewById(R.id.profileTopScore);


        }
    }


}
