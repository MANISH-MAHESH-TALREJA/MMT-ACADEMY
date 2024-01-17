package com.pixelnx.eacademy.ui.mcq.generateresult;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.ui.base.BaseActivity;
import com.pixelnx.eacademy.ui.mcq.ActivityMCQDashboard;
import com.pixelnx.eacademy.ui.mcq.resultquestionlist.ActivityPaperResultList;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.widgets.CustomButton;
import com.pixelnx.eacademy.utils.widgets.CustomTextExtraBold;
import com.pixelnx.eacademy.utils.widgets.CustomTextNormalBold;
import com.pixelnx.eacademy.utils.widgets.CustomTextSemiBold;


public class ActivityGenerateResult extends BaseActivity {

    Context mContext;
    CustomTextNormalBold tvResultTitle;
    ImageView ivBack;
    CustomButton btViewSheet;
    CustomTextExtraBold tvHeader;
    int timeUsed = 0;
    String showResult = "";
    String examType = "";
    String resultId = "";
    String studentId = "";
    String examDate = "";
    String img = "";
    String examName = "";
    CustomTextSemiBold tvTotalQues;
    CustomTextSemiBold tvTotalAttempt;
    CustomTextSemiBold tvCorrectAns;
    CustomTextSemiBold tvWrongAns;
    CustomTextSemiBold tvMinusMark;
    CustomTextSemiBold tvTotalScore;
    CustomTextSemiBold tvPercent;
    CustomTextSemiBold tvStartTime;
    CustomTextSemiBold tvSubmitTime;
    CustomTextSemiBold tvDate;
    CustomTextSemiBold tvTimeTaken;
    CustomTextSemiBold tvTimeUsed;
    CustomTextSemiBold tvNegative,tvResult;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_generate_result);

        mContext = ActivityGenerateResult.this;

        examType = getIntent().getStringExtra(AppConsts.EXAM_TYPE);
        resultId = getIntent().getStringExtra(AppConsts.RESULT_ID);
        showResult = getIntent().getStringExtra(AppConsts.SHOW_RESULT);
        studentId = getIntent().getStringExtra(AppConsts.STUDENT_ID);
        examDate = getIntent().getStringExtra(AppConsts.DATE);
        examName = getIntent().getStringExtra(AppConsts.EXAM_NAME);
        img = getIntent().getStringExtra(AppConsts.IMAGE_GIF_URL);
        timeUsed = getIntent().getIntExtra(AppConsts.TIME_USED, 0);
        tvResult = findViewById(R.id.tvResult);

        init();

        if (getIntent().hasExtra(AppConsts.SHOW_RESULT)) {
            if (!("1".equals(showResult))) {
                btViewSheet.setVisibility(View.GONE);
            } else {
                btViewSheet.setVisibility(View.VISIBLE);
            }
        }


        tvTotalQues.setText(getIntent().getStringExtra(AppConsts.TOTAL_QUESTIONS));
        tvTotalAttempt.setText(getIntent().getStringExtra(AppConsts.TOTAL_ATTEMPT));
        tvCorrectAns.setText(getIntent().getStringExtra(AppConsts.CORRECT_ANSWERS));
        tvWrongAns.setText(getIntent().getStringExtra(AppConsts.WRONG_ANSWERS));
        tvMinusMark.setText(getIntent().getStringExtra(AppConsts.MINUS_MARKS));
        tvTotalScore.setText(getIntent().getStringExtra(AppConsts.TOTAL_SCORE));
        tvNegative.setText(getIntent().getStringExtra(AppConsts.negetive));
        if(Double.parseDouble(getIntent().getStringExtra(AppConsts.PERCENTAGE)) < 30){
           tvResult.setText("Fail");
        }else{
            tvResult.setText("Pass");}
        try {
            int sec = timeUsed / (1000);
            if(Double.parseDouble(getIntent().getStringExtra(AppConsts.PERCENTAGE)) < 0){
            tvPercent.setText("0"+ "%");}else{
                tvPercent.setText("" + getIntent().getStringExtra(AppConsts.PERCENTAGE) + "%");
            }

            tvTimeTaken.setText("" + getIntent().getStringExtra(AppConsts.TIME_TAKEN));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        tvStartTime.setText(getIntent().getStringExtra(AppConsts.START_TIME));
        tvSubmitTime.setText(getIntent().getStringExtra(AppConsts.SUBMIT_TIME));


        tvSubmitTime.setText(getIntent().getStringExtra(AppConsts.SUBMIT_TIME));


        tvDate.setText(getIntent().getStringExtra(AppConsts.DATE));


        try {
            int sec = (Integer.parseInt(getIntent().getStringExtra(AppConsts.TIME_TAKEN)));
            int second = sec % 60;
            int minute = sec / 60;
            if (minute >= 60) {
                int hour = minute / 60;
                minute %= 60;
                tvTimeTaken.setText(hour + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second));
            } else
                tvTimeTaken.setText("00:" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second));


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        tvResultTitle.setText(getIntent().getStringExtra(AppConsts.EXAM_NAME));

    }


    private void init() {
        btViewSheet = (CustomButton) findViewById(R.id.btViewSheet);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvTotalQues = (CustomTextSemiBold) findViewById(R.id.tvTotalQues);
        tvNegative = (CustomTextSemiBold) findViewById(R.id.tvNegative);
        tvResultTitle = (CustomTextNormalBold) findViewById(R.id.tvResultTitle);
        tvTotalAttempt = (CustomTextSemiBold) findViewById(R.id.tvTotalAttempt);
        tvCorrectAns = (CustomTextSemiBold) findViewById(R.id.tvCorrectAns);
        tvWrongAns = (CustomTextSemiBold) findViewById(R.id.tvWrongAns);
        tvMinusMark = (CustomTextSemiBold) findViewById(R.id.tvMinusMark);
        tvTotalScore = (CustomTextSemiBold) findViewById(R.id.tvTotalScore);
        tvPercent = (CustomTextSemiBold) findViewById(R.id.tvPercent);
        tvTimeUsed = (CustomTextSemiBold) findViewById(R.id.tvTimeUsed);
        tvStartTime = (CustomTextSemiBold) findViewById(R.id.tvStartTime);
        tvSubmitTime = (CustomTextSemiBold) findViewById(R.id.tvSubmitTime);
        tvDate = (CustomTextSemiBold) findViewById(R.id.tvDate);
        tvTimeTaken = (CustomTextSemiBold) findViewById(R.id.tvTimeTaken);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        tvResultTitle.setText("");
        tvHeader.setText(getResources().getString(R.string.Result));

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });





        btViewSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityPaperResultList.class);
                intent.putExtra(AppConsts.EXAM_TYPE, examType);
                intent.putExtra(AppConsts.RESULT_ID, resultId);
                intent.putExtra(AppConsts.STUDENT_ID, studentId);
                intent.putExtra(AppConsts.EXAM_DATE, examDate);
                intent.putExtra(AppConsts.EXAM_NAME, examName);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(mContext, ActivityMCQDashboard.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

    }


}
