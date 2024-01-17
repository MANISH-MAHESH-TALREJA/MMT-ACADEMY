package com.pixelnx.eacademy.ui.mcq.resultquestionlist;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.model.modelpractiesresult.ModelPractiesResult;
import com.pixelnx.eacademy.model.modelviewresult.ModelViewResult;
import com.pixelnx.eacademy.ui.base.BaseActivity;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomButton;
import com.pixelnx.eacademy.utils.widgets.CustomTextExtraBold;
import com.pixelnx.eacademy.utils.widgets.CustomTextSemiBold;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ActivityPaperResultList extends BaseActivity {
    RecyclerView rvPracticeList;
    Context mContext;
    CustomTextExtraBold tvHeader;
    ImageView ivBack;
    WebView web;
    RelativeLayout hideHeader;
    CustomButton btViewSheet;
    CustomButton btViewSheetResult;
    LinearLayout llWebLayout;
    LinearLayout llRightWrong;
    ModelViewResult.AllData paperResultList;
    int timeUsed = 0;
    ArrayList<ModelViewResult.AllData.AllQuestion> subjectWiseResultList;
    AdapterPaperResultList adapterPaperResultList;
    String examType = "";
    String paperId = "";
    String resultId = "";
    String studentId = "";
    String examDate = "";
    String examName = "";
    String showResult = "no";
    String percent = "";
    String timeTaken = "";
    String fromFrag = "no";
    ModelPractiesResult.PracticeResult data;
    ModelLogin modelLogin;
    SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_paper_result_list);
        mContext = ActivityPaperResultList.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        if(modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("arabic")){
            //manually set Directions
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }



        initial();
        examType = getIntent().getStringExtra(AppConsts.EXAM_TYPE);
        resultId = getIntent().getStringExtra(AppConsts.RESULT_ID);
        studentId = getIntent().getStringExtra(AppConsts.STUDENT_ID);
        examDate = getIntent().getStringExtra(AppConsts.EXAM_DATE);
        examName = getIntent().getStringExtra(AppConsts.EXAM_NAME);
        showResult = getIntent().getStringExtra(AppConsts.SHOW_RESULT);
        percent = getIntent().getStringExtra(AppConsts.PERCENTAGE);
        timeTaken = getIntent().getStringExtra(AppConsts.TIME_TAKEN);
        fromFrag = getIntent().getStringExtra(AppConsts.FROM_FRAG);
        paperId = getIntent().getStringExtra(AppConsts.PAPER_ID);
        data = (ModelPractiesResult.PracticeResult) getIntent().getSerializableExtra(AppConsts.PAPER_DATA);

        if (examName != null && (!examName.equals("")))
            tvHeader.setText(examName);

        if (showResult != null && (!showResult.equals("")))
            btViewSheetResult.setVisibility(View.VISIBLE);


        if (ProjectUtils.checkConnection(mContext)) {
            viewPaperDetailApi(examType, resultId, paperId);
        } else {
            Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
    }

    private void viewPaperDetailApi(String examType, String resultId, String paperId) {
        ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_VIEW_PRACTICE_ANS_SHEET)
                .addBodyParameter("exam_type", examType)
                .addBodyParameter(AppConsts.RESULT_ID, resultId)
                .addBodyParameter(AppConsts.PAPER_ID, paperId)
                .setTag(AppConsts.API_VIEW_PRACTICE_ANS_SHEET)
                .build()
                .getAsObject(ModelViewResult.class, new ParsedRequestListener<ModelViewResult>() {
                    @Override
                    public void onResponse(ModelViewResult response) {
                        ProjectUtils.pauseProgressDialog();
                        if (AppConsts.TRUE.equals(response.getStatus())) {
                            subjectWiseResultList = new ArrayList<>();
                            paperResultList = response.getResultData();
                            subjectWiseResultList = response.getResultData().getAllQuestions();
                            renderWebView(paperResultList, subjectWiseResultList);
                            adapterPaperResultList = new AdapterPaperResultList(mContext, subjectWiseResultList, examType);
                            rvPracticeList.setLayoutManager(new LinearLayoutManager(mContext));
                            ((SimpleItemAnimator) rvPracticeList.getItemAnimator()).setSupportsChangeAnimations(false);
                            rvPracticeList.setAdapter(adapterPaperResultList);
                            try {
                                makeDataForDialog(response);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(mContext, "" + response.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(mContext, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                        ProjectUtils.pauseProgressDialog();
                    }
                });
    }

    private void makeDataForDialog(ModelViewResult response) {
        data = new ModelPractiesResult.PracticeResult();
        data.setTotalQuestion(response.getResultData().getTotalQuestion());

        data.setStartTime(response.getResultData().getStartTime());
        data.setSubmitTime(response.getResultData().getSubmitTime());


        data.setDate("" + response.getResultData().getDate());
        data.setAttemptedQuestion("" + response.getResultData().getTotalQuestion());


        Date startDate = null;// Set start date
        Date endDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ssaa");
        try {
            startDate = sdf.parse(response.getResultData().getStartTime());
            endDate = sdf.parse(response.getResultData().getSubmitTime());
            assert startDate != null;
            assert endDate != null;
            long duration = endDate.getTime() - startDate.getTime();
            data.setTimeTaken("" + duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initial() {
        rvPracticeList = (RecyclerView) findViewById(R.id.rvPracticeList);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        web = (WebView) findViewById(R.id.web);
        llWebLayout = (LinearLayout) findViewById(R.id.llWebLayout);
        llRightWrong = (LinearLayout) findViewById(R.id.llRightWrong);
        hideHeader = (RelativeLayout) findViewById(R.id.hideHeader);
        btViewSheet = (CustomButton) findViewById(R.id.btViewSheet);
        btViewSheetResult = (CustomButton) findViewById(R.id.btViewSheetResult);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.setVerticalScrollBarEnabled(true);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btViewSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btViewSheet.setVisibility(View.GONE);
                hideHeader.setVisibility(View.GONE);
                rvPracticeList.setVisibility(View.GONE);
                llRightWrong.setVisibility(View.GONE);
                llWebLayout.setVisibility(View.VISIBLE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });

        btViewSheetResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(data);
            }
        });
    }


    private void openDialog(ModelPractiesResult.PracticeResult data) {
        final Dialog dialog = new Dialog(mContext, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setContentView(R.layout.activity_generate_result);
        dialog.show();
        CustomButton btViewSheet = (CustomButton) dialog.findViewById(R.id.btViewSheet);
        ImageView iBack = (ImageView) dialog.findViewById(R.id.ivBack);
        CustomTextSemiBold tvTotalQues = (CustomTextSemiBold) dialog.findViewById(R.id.tvTotalQues);
        CustomTextSemiBold tvTotalAttempt = (CustomTextSemiBold) dialog.findViewById(R.id.tvTotalAttempt);
        CustomTextSemiBold tvCorrectAns = (CustomTextSemiBold) dialog.findViewById(R.id.tvCorrectAns);
        CustomTextSemiBold tvWrongAns = (CustomTextSemiBold) dialog.findViewById(R.id.tvWrongAns);
        CustomTextSemiBold tvTotalScore = (CustomTextSemiBold) dialog.findViewById(R.id.tvTotalScore);
        CustomTextSemiBold tvPercent = (CustomTextSemiBold) dialog.findViewById(R.id.tvPercent);
        CustomTextSemiBold tvStartTime = (CustomTextSemiBold) dialog.findViewById(R.id.tvStartTime);
        CustomTextSemiBold tvSubmitTime = (CustomTextSemiBold) dialog.findViewById(R.id.tvSubmitTime);
        CustomTextSemiBold tvDate = (CustomTextSemiBold) dialog.findViewById(R.id.tvDate);
        CustomTextSemiBold tvTimeTaken = (CustomTextSemiBold) dialog.findViewById(R.id.tvTimeTaken);
        CustomTextSemiBold tvTimeUsed = (CustomTextSemiBold) dialog.findViewById(R.id.tvTimeUsed);


        int len = 0;


        int totalAtt = Integer.parseInt(data.getAttemptedQuestion());
        int totalQuestion = Integer.parseInt(data.getTotalQuestion());

        if (totalAtt > totalQuestion) {
            totalAtt = totalQuestion;
        }

        double timeTake = (Integer.parseInt(data.getTimeTaken()));




        tvTotalQues.setText("" + data.getTotalQuestion());
        tvTotalAttempt.setText("" + totalAtt);
        tvCorrectAns.setText("" + data.getRightAns());
        tvWrongAns.setText("" + len);
        tvTotalScore.setText("" + data.getTotalScore());
        tvStartTime.setText("" + data.getStartTime());
        tvSubmitTime.setText("" + data.getSubmitTime());
        tvDate.setText("" + data.getDate());

        tvTimeTaken.setText("" + hmsTimeFormatter(Long.parseLong(data.getTimeTaken())));
        try {
            convertTime(timeUsed, tvTimeUsed);

            double min = len * .25;
            double marksObtain = (Double.parseDouble(data.getRightAns()) - (min));
            double totalQ = Integer.parseInt(data.getTotalQuestion());
            double per = ((marksObtain * 100) / totalQ);

            tvPercent.setText("" + new DecimalFormat("##.##").format(per) + "%");
        } catch (Exception e) {

        }
        btViewSheet.setVisibility(View.GONE);

        iBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void convertTime(int time, CustomTextSemiBold tvTimeTaken) {
        int sec = time;
        int second = sec % 60;
        int minute = sec / 60;
        if (minute >= 60) {
            int hour = minute / 60;
            minute %= 60;
            tvTimeTaken.setText(hour + ":" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second));
        } else
            tvTimeTaken.setText("00:" + (minute < 10 ? "0" + minute : minute) + ":" + (second < 10 ? "0" + second : second));
    }



    private void renderWebView(ModelViewResult.AllData subject, ArrayList<ModelViewResult.AllData.AllQuestion> allQuestions) {
        String firstHtml = "<html>\n" +
                "<head> <meta name=\"viewport\" content=\"width=device-width\">\n" +
                "<style>\n" +
                "table, th, td {\n" +
                "  border: 1px solid black;\n" +
                "  border-collapse: collapse;\n" +
                "  font-size:15px;\n" +
                "}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body style=\"text-align:center;\">\n" +
                "\n" +
                "<table style=\"text-align:center;margin:0 auto;\" cellpadding=\"5\">\n" +
                "  <tr>\n" +
                "    <td>S.No</td>\n" +
                "    <td>Ques.</td>\n" +
                "    <td>Total Ques</td>\n" +
                "    <td>Total Attempts</td> \n" +
                "    <td>Correct</td>\n" +
                "    <td>Incorrect</td>\n" +
                "    <td>Time Used</td>\n" +
                "  </tr>";

        String lastHtml = "</table>\n" +
                "\n" +
                "</body>\n" +
                "</html>";

        String middleHtml = "";

        for (int i = 0; i < allQuestions.size(); i++) {
            middleHtml = middleHtml + "  <tr><td> " + (i + 1) + "</td>" +
                    "<td> " + allQuestions.get(i).getQuestion() + "</td>" +
                    "<td> " + subject.getTotalQuestion() + "</td>" +
                    "<td> " + subject.getId() + "</td>" +
                    "<td> " + subject.getId() + "</td>" +
                    "<td> " + subject.getId() + "</td>" +
                    "<td> " + subject.getId() + "</td>" +
                    "</tr>";
        }

        web.loadData(firstHtml + middleHtml + lastHtml, "text/html; charset=utf-8", "UTF-8");
        web.loadData(firstHtml + middleHtml + lastHtml, "text/html; charset=utf-8", "UTF-8");
        web.loadData(firstHtml + middleHtml + lastHtml, "text/html; charset=utf-8", "UTF-8");
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            btViewSheet.setVisibility(View.VISIBLE);
            hideHeader.setVisibility(View.VISIBLE);
            rvPracticeList.setVisibility(View.VISIBLE);
            llWebLayout.setVisibility(View.GONE);
            llRightWrong.setVisibility(View.VISIBLE);
        } else {
            finish();
        }
    }


    private String hmsTimeFormatter(long milliSeconds) {
        String second = String.format("%02d: %02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return second.replaceAll("-", "");
    }

}
