package com.pixelnx.eacademy.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.pixelnx.eacademy.ui.UpcomingExams.ActivityVacancyOrUpcomingExam;
import com.pixelnx.eacademy.ui.applyleave.ActivityApplyLeave;
import com.pixelnx.eacademy.ui.assignment.ActivityAssignment;
import com.pixelnx.eacademy.ui.batch.ActivityBatch;
import com.pixelnx.eacademy.ui.certificate.ActivityCertificate;
import com.pixelnx.eacademy.ui.doubtClasses.ActivityDoubtClasses;
import com.pixelnx.eacademy.ui.extraclass.ActivityExtraClass;
import com.pixelnx.eacademy.ui.galary.galleryvideos.ActivityVimeoVideo;
import com.pixelnx.eacademy.ui.galary.galleryvideos.ExoplayerVideos;
import com.pixelnx.eacademy.ui.library.ActivityPdf;
import com.pixelnx.eacademy.ui.mcq.practicepaper.ActivityPracticePaper;
import com.pixelnx.eacademy.ui.multibatch.ActivityMultiBatchHome;
import com.pixelnx.eacademy.ui.multibatch.ActivityMyBatch;
import com.pixelnx.eacademy.ui.noticeAnnouncement.ActivityForFragments;
import com.pixelnx.eacademy.ui.video.ActivityYoutubeVideo;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.Firebase.MyFirebaseMessagingService;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 1000;
    Context mContext;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    Handler handler = new Handler();
    ProgressBar progressBar;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        setContentView(R.layout.activity_splash);
        mContext = SplashActivity.this;


        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);


        progressBar = findViewById(R.id.progressBar);
        swipe = findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkLogin();
            }
        });


        if (!ProjectUtils.checkConnection(mContext)) {
            Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }
        handler.postDelayed(mTask, SPLASH_TIME_OUT);
        languageDynamic();

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

                        }
                        if (jsonObject.getString("languageName").equalsIgnoreCase("french")) {
                            String languageToLoad = "fr"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());


                        }
                        if (jsonObject.getString("languageName").equalsIgnoreCase("hindi")) {
                            String languageToLoad = "hi"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                        }

                        if (jsonObject.getString("languageName").equalsIgnoreCase("german")) {
                            String languageToLoad = "de"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                        }

                        if (jsonObject.getString("languageName").equalsIgnoreCase("spanish")) {
                            String languageToLoad = "es"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
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

    Runnable mTask = new Runnable() {
        @Override
        public void run() {

            checkLogin();

        }
    };


    private void checkLogin() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

                    @Override
                    public void onComplete(Task<InstanceIdResult> task) {
                        Log.v("SALONI123","saloni     checkkk ");
                        if (!task.isSuccessful()) {
                            Log.v("SALONI123","saloni     checkkk1 ");
                            return;
                        }
                        Log.v("SALONI123","saloni     checkkk2 ");
                        if (modelLogin != null) {
                            Log.v("SALONI123","saloni     checkkk 3");
                            if (modelLogin.getStudentData() != null) {
                                Log.v("SALONI123","saloni     checkkk4 "+task.getResult().getToken());

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
                                                        Intent loginScreen = new Intent(mContext, ActivityBatch.class);
                                                        loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        startActivity(loginScreen);
                                                        finish();
                                                    } else {

                                                        checkStatus();
                                                    }


                                                } catch (Exception e) {
                                                    Toast.makeText(mContext, getResources().getString(R.string.Try_again_server_issue), Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                                Toast.makeText(mContext, getResources().getString(R.string.Try_again_server_issue), Toast.LENGTH_SHORT).show();
                                            }
                                        });


                            } else {

                                Intent intent = new Intent(mContext, ActivityBatch.class);
                                intent.putExtra(AppConsts.IS_SPLASH, "true");
                                startActivity(intent);
                                finish();
                            }
                        } else {

                            Intent intent = new Intent(mContext, ActivityBatch.class);
                            intent.putExtra(AppConsts.IS_SPLASH, "true");
                            startActivity(intent);
                            finish();

                        }

                    }

                });


    }

    private void checkStatus() {


        if (sharedPref.getBooleanValue(AppConsts.IS_REGISTER)) {

            if (modelLogin != null) {
                if (MyFirebaseMessagingService.WHERE != null) {
                    moveToActivity(MyFirebaseMessagingService.WHERE,MyFirebaseMessagingService.BatchName,MyFirebaseMessagingService.BatchID);
                } else {
                    Intent intent = new Intent(mContext, ActivityMultiBatchHome.class);
                    intent.putExtra(AppConsts.IS_SPLASH, "true");
                    startActivity(intent);
                    finish();
                }


            } else {

                Intent intent = new Intent(mContext, ActivityBatch.class);
                intent.putExtra(AppConsts.IS_SPLASH, "true");
                startActivity(intent);
                finish();
            }
        } else {

            Intent intent = new Intent(mContext, ActivityBatch.class);
            intent.putExtra(AppConsts.IS_SPLASH, "true");
            startActivity(intent);
            finish();
        }
    }


    private void moveToActivity( String tag, String name,String idBatch) {
if(idBatch != null){
    if(idBatch.equalsIgnoreCase(""+modelLogin.getStudentData().getBatchId())){
        if ("homework".equalsIgnoreCase(tag)) {
            MyFirebaseMessagingService.WHERE = "";
            Intent intent = new Intent(mContext, ActivityAssignment.class);
            intent.putExtra(AppConsts.IS_PUSH, "push");
            startActivity(intent);
        } else if ("exam".equalsIgnoreCase(tag)) {
            MyFirebaseMessagingService.WHERE = "";

            Intent intent = new Intent(mContext, ActivityVacancyOrUpcomingExam.class);
            intent.putExtra(AppConsts.IS_PUSH, "push");
            startActivity(intent);
        } else if ("extraclasses".equalsIgnoreCase(tag)) {
            MyFirebaseMessagingService.WHERE = "";

            Intent intent = new Intent(mContext, ActivityExtraClass.class);
            intent.putExtra(AppConsts.IS_PUSH, "push");
            startActivity(intent);
        } else if ("practice".equalsIgnoreCase(tag)) {
            MyFirebaseMessagingService.WHERE = "";

            Intent intent = new Intent(mContext, ActivityPracticePaper.class);
            intent.putExtra(AppConsts.EXAM_TYPE, "practice");
            intent.putExtra(AppConsts.IS_PUSH, "push");
            mContext.startActivity(intent);
        } else if ("mock_test".equalsIgnoreCase(tag)) {
            MyFirebaseMessagingService.WHERE = "";
            Intent intent = new Intent(mContext, ActivityPracticePaper.class);
            intent.putExtra(AppConsts.EXAM_TYPE, "mock_test");
            intent.putExtra(AppConsts.IS_PUSH, "push");
            mContext.startActivity(intent);
        } else if ("Leave".equalsIgnoreCase(tag)) {
            MyFirebaseMessagingService.WHERE = "";
            Intent intent = new Intent(mContext, ActivityApplyLeave.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra(AppConsts.IS_PUSH, "push");
            mContext.startActivity(intent);
        } else if ("notice_personal".equalsIgnoreCase(tag) || "notice_common".equalsIgnoreCase(tag)) {
            MyFirebaseMessagingService.WHERE = "";
            Toast.makeText(mContext, "Notice !", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, ActivityForFragments.class);
            intent.putExtra(AppConsts.IS_PUSH, "push");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            if (tag.contains("notice_common")) {
                intent.putExtra("notice", "notice_common");
            } else {
                intent.putExtra("notice", "notice_personal");

            }
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mContext.startActivity(intent);
        } else {
            if ("videoLecture".equalsIgnoreCase(tag)) {

                if (MyFirebaseMessagingService.videoType.equalsIgnoreCase("youtube")) {
                    mContext.startActivity(new Intent(mContext, ActivityYoutubeVideo.class)
                            .putExtra("firebase/notice", "" + MyFirebaseMessagingService.topicVideo).putExtra("vId",
                                    "" + MyFirebaseMessagingService.vId).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).
                                    putExtra("WEB_URL", "" + MyFirebaseMessagingService.videoUrl).putExtra("type", MyFirebaseMessagingService.videoType));
                } else {
                    if (MyFirebaseMessagingService.videoType.equalsIgnoreCase("video")) {
                        mContext.startActivity(new Intent(mContext, ExoplayerVideos.class)    .putExtra("firebase/notice", "" + MyFirebaseMessagingService.topicVideo).putExtra("WEB_URL", "" + MyFirebaseMessagingService.videoUrl));
                    }else{
                        mContext.startActivity(new Intent(mContext, ActivityVimeoVideo.class)
                                .putExtra("firebase/notice", "" + MyFirebaseMessagingService.topicVideo).putExtra("vId",
                                        "" + MyFirebaseMessagingService.vId).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).
                                        putExtra("WEB_URL", "" + MyFirebaseMessagingService.videoUrl).putExtra("type", MyFirebaseMessagingService.videoType));
                    }
                }

            } else {
                if ("certificate".equalsIgnoreCase(tag)) {

                    mContext.startActivity(new Intent(mContext, ActivityCertificate.class).putExtra(AppConsts.IS_PUSH, AppConsts.IS_PUSH));

                } else {
                    if ("doubtsClass".equalsIgnoreCase(tag)) {
                        mContext.startActivity(new Intent(mContext, ActivityDoubtClasses.class).putExtra(AppConsts.IS_PUSH, AppConsts.IS_PUSH));
                    } else {
                        if ("addOldPaper".equalsIgnoreCase(tag) || "addNewBook".equalsIgnoreCase(tag) || "addNewNotes".equalsIgnoreCase(tag)) {
                            if ("addNewBook".equalsIgnoreCase(tag)){
                                startActivity(new Intent(mContext, ActivityPdf.class).putExtra("from","books").putExtra("firebase/notice",""));}
                            if ("addNewNotes".equalsIgnoreCase(tag)){
                                startActivity(new Intent(mContext, ActivityPdf.class).putExtra("from","notes")  .putExtra("firebase/notice",""));}

                            if ("addOldPaper".equalsIgnoreCase(tag)){
                                startActivity(new Intent(mContext, ActivityPdf.class).putExtra("from","oldpaper").putExtra("firebase/notice",""));}
                        }else{
                            MyFirebaseMessagingService.WHERE = "";
                            Intent intent = new Intent(mContext, ActivityMyBatch.class);
                            intent.putExtra(AppConsts.IS_PUSH, "push");
                            startActivity(intent);
                            finish();}
                    }

                }
            }

        }
    }else{
        MyFirebaseMessagingService.WHERE = "";
        Intent intent = new Intent(mContext, ActivityMyBatch.class);
        intent.putExtra(AppConsts.IS_PUSH, "push");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}

    }

}
