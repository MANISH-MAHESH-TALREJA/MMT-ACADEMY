package net.manish.sem05.ui.generatepapers;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import net.manish.sem05.R;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.base.BaseActivity;
import net.manish.sem05.ui.mcq.generateresult.ActivityGenerateResult;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.HomeWatcher;
import net.manish.sem05.utils.OnHomePressedListener;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomButton;
import net.manish.sem05.utils.widgets.CustomTextBold;
import net.manish.sem05.utils.widgets.CustomTextExtraBold;
import net.manish.sem05.utils.widgets.CustomTextSemiBold;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class ActivityGetPapers extends BaseActivity implements AppConsts {

    static CustomTextExtraBold tvTime;
    static HorizontalScrollView horizonScrollView;
    static CustomTextSemiBold tvRemainingQuestion;
    static RecyclerView rvGeneratePaper;
    RecyclerView rvCount;
    ArrayList<ModelGetPaper.TotalExamData.QuestionDetails> paperList;
    Context mContext;
    ImageView ivBack;
    static LinearLayout linearCount;
    AdapterGetPaper adapterGeneratePaper;
    String examId = "";
    String examName = "";
    String formattedTime;
    String startTime = "";
    String startTimeSecond;
    TextView allQuestions;
    String currentServerTime = "";
    int idCount = 1;
    boolean isFirst = true;
    static ArrayList<View> dynamicCountList;
    String showResult = "";
    String currentDeviceTime = "";
    String currentDeviceDate = "";
    String examType = "";
    String durations;
    String paperTime = "";
    String totalQues = "";
    public HashMap<String, String> holdGenerateAnswer = new HashMap<>();
    JSONArray jsonQuesIds;
    JSONArray jsonAns;
    JSONArray jsonTimeArray = new JSONArray();
    SharedPref sharedPref;
    ModelLogin modelLogin;
    ProgressBar progressBar;
    HomeWatcher mHomeWatcher;
    boolean shouldSubmitPaper = false;
    CustomButton submit;
    CountDownTimer countDownTimer;
    private long timeCountInMilliSeconds = 60000;
    String forTest = "";
    Dialog dialog;
    ImageView noResult;
    String down = "";
    boolean hasRes=false;

    private enum TimerStatus {
        STARTED,
        STOPPED
    }

    IntentFilter inf;
    private TimerStatus timerStatus = TimerStatus.STOPPED;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        inf = new IntentFilter();
        inf.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_papers);
        mContext = ActivityGetPapers.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        if (modelLogin != null) {
            if (modelLogin.getStudentData() != null) {
                if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("arabic")) {
                    //manually set Directions

                    getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
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

                if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("french")) {
                    String languageToLoad = "fr"; // your language
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());


                }
                if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("english")) {
                    String languageToLoad = "en"; // your language
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                }

            }


        }
        examId = getIntent().getStringExtra("id");
        showResult = getIntent().getStringExtra(AppConsts.SHOW_RESULT);
        paperTime = getIntent().getStringExtra(AppConsts.PAPER_TIME);
        examName = getIntent().getStringExtra("nm");
        totalQues = getIntent().getStringExtra(AppConsts.TOTAL_QUESTION);

        try {
            mHomeWatcher = new HomeWatcher(this);

            mHomeWatcher.setOnHomePressedListener(new OnHomePressedListener() {
                @Override
                public void onHomePressed() {

                    shouldSubmitPaper = true;
                    submitResult();
                }


            });


            formattedTime = ConvertMinutesTimeToHHMMString(Integer.parseInt(getIntent().getStringExtra(AppConsts.PAPER_TIME)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        noResult = findViewById(R.id.noResult);
        dynamicCountList = new ArrayList<>();
        init();

        if (ProjectUtils.checkConnection(mContext)) {

            generatePaperApi(examId);
        } else {
            Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
    }

    private void setProgressBarValues() {
        progressBar.setMax((int) timeCountInMilliSeconds / 1000);
        progressBar.setProgress((int) timeCountInMilliSeconds / 1000);
    }

    private void submitResult() {


        submitResult("");


    }


    public static String ConvertMinutesTimeToHHMMString(int minutesTime) {
        TimeZone timeZone = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(timeZone);
        String time = df.format(new Date(minutesTime * 60 * 1000L));
        return time;
    }

    @Override
    public void setIdsOfAllSelectedQuestions(HashMap<String, String> hasIdsAns, HashMap<String, String> questionTimeHash) {

        jsonTimeArray = new JSONArray();
        jsonQuesIds = new JSONArray();
        jsonAns = new JSONArray();

        Iterator iterateIdsAndAns = hasIdsAns.keySet().iterator();

        while (iterateIdsAndAns.hasNext()) {

            String questionId = (String) iterateIdsAndAns.next();
            String answer = (String) hasIdsAns.get(questionId);

            jsonQuesIds.put(questionId);
            jsonAns.put(answer);

        }

        Iterator iterateIdsQuestionTime = questionTimeHash.keySet().iterator();

        while (iterateIdsQuestionTime.hasNext()) {

            String questionId = (String) iterateIdsQuestionTime.next();
            String time = (String) questionTimeHash.get(questionId);

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("qid", questionId);
                jsonObject.put("time", time);

                jsonTimeArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        DateFormat timeInstance = SimpleDateFormat.getTimeInstance();
        currentDeviceTime = timeInstance.format(Calendar.getInstance().getTime());
        startTime = currentDeviceTime;
        currentDeviceDate = getCurrentDate("dd-MM-yy");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        down = "down";
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {

        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onPause() {
        if (examType.equalsIgnoreCase("mock_test")) {

            submitResult("back");

            super.onPause();
        } else {
            finish();
            super.onPause();
        }


        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    /*    if (!down.equalsIgnoreCase("down")) {
            if (examType.equalsIgnoreCase("mock_test")) {

                submitResult("back");

                super.onPause();
            } else {
                finish();
                super.onPause();
            }


            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
        } else {
            super.onPause();
        }*/


    }

    private void init() {
        tvTime = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        allQuestions = findViewById(R.id.allQuestions);
        tvTime.setText("" + examName);
        rvGeneratePaper = (RecyclerView) findViewById(R.id.rvGeneratePaper);


        SnapHelper helper = new PagerSnapHelper();
        helper.attachToRecyclerView(rvGeneratePaper);
        rvCount = (RecyclerView) findViewById(R.id.rvCount);
        ivBack = (ImageView) findViewById(R.id.ivBack);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        horizonScrollView = (HorizontalScrollView) findViewById(R.id.horizonScrollView);
        linearCount = (LinearLayout) findViewById(R.id.linearCount);

        submit = findViewById(R.id.submit);
        tvRemainingQuestion = findViewById(R.id.tvRemainingQuestion);
        tvRemainingQuestion.setTextSize(17f);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holdGenerateAnswer.size() != 0) {
                    submitResult("");
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.Please_Attempt_Any_Question____), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        allQuestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    dialog = new Dialog(mContext, android.R.style.Theme_Material_Light_Dialog_Alert);
                } else {
                    dialog = new Dialog(mContext);
                }
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.allquestions);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                CustomTextBold dialogYesButton = dialog.findViewById(R.id.cancel);
                CustomTextBold ttlAns = dialog.findViewById(R.id.ttlAns);
                dialogYesButton.setText(getResources().getString(R.string.TotalQuestions) + " " + paperList.size());
                ttlAns.setText(getResources().getString(R.string.TotalAnswered) + " " + holdGenerateAnswer.size());
                RecyclerView rvAttemptedQues = dialog.findViewById(R.id.rvAttemptedQues);
                if (paperList.size() >= 16) {
                    rvAttemptedQues.setLayoutManager(new GridLayoutManager(mContext, 4));
                } else {
                    rvAttemptedQues.setLayoutManager(new GridLayoutManager(mContext, 3));
                }


                AdapterQuestionList adapterQuestionList = new AdapterQuestionList(paperList);
                rvAttemptedQues.setAdapter(adapterQuestionList);
                dialogYesButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


                dialog.show();

            }
        });


    }

    @Override
    public void onBackPressed() {
        forTest = "back";
        if (examType.equalsIgnoreCase("mock_test")) {
            exitAppDialog();
        } else {
            finish();

            super.onBackPressed();
        }
    }

    private void exitAppDialog() {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(mContext, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(mContext);
        }

        builder.setMessage(getResources().getString(R.string.Are_you_sure_you_want_to_cance_this_test_Once_you_cancel_Yourtestissubmittedautomatically_))
                .setCancelable(false)
                .setTitle("" + getResources().getString(R.string.app_name))
                .setPositiveButton("" + getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        submitResult("back");
                        finish();
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

    private void generateDynamicCountLayouts() {

        final LinearLayout l1 = new LinearLayout(mContext);
        LinearLayout.LayoutParams l1Params = new LinearLayout.LayoutParams(88, 88);
        l1.setId(idCount);
        l1Params.setMargins(18, 18, 18, 18);
        l1.setBackgroundResource(R.drawable.circle_back_grad);
        l1Params.gravity = Gravity.CENTER_VERTICAL;
        l1.setGravity(Gravity.CENTER);
        l1.setLayoutParams(l1Params);
        CustomTextSemiBold tvCount = new CustomTextSemiBold(mContext);
        LinearLayout.LayoutParams tvCountParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        tvCountParams.gravity = Gravity.CENTER;
        tvCount.setLayoutParams(tvCountParams);
        tvCount.setId(idCount);
        tvCount.setTextColor(ContextCompat.getColor(mContext, R.color.white));
        tvCount.setText(idCount + "");
        l1.addView(tvCount);
        linearCount.addView(l1);
        dynamicCountList.add(l1);


        l1.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {
                for (int i = 0; i < dynamicCountList.size(); i++) {
                    if (l1.getId() == dynamicCountList.get(i).getId()) {

                        rvGeneratePaper.scrollToPosition((l1.getId()) - 1);
                    }
                }
            }
        });
        idCount++;
    }

    private void submitResult(String from) {
        if (from.equalsIgnoreCase("back")) {
            Log.v("DebugCheckSaloni","saloni   ------------------back");
            JSONObject jsonObject = new JSONObject();
            for (Map.Entry<String, String> entry : holdGenerateAnswer.entrySet()) {
                try {
                    jsonObject.put("" + entry.getKey(), "" + entry.getValue());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
            ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));
            if(!hasRes) {
                getResultApi(jsonObject + "", startTimeSecond, from);
            }


        } else {
            Log.v("DebugCheckSaloni","saloni   ------------------without back");
            if (holdGenerateAnswer.size() != 0) {
                JSONObject jsonObject = new JSONObject();
                for (Map.Entry<String, String> entry : holdGenerateAnswer.entrySet()) {
                    try {
                        jsonObject.put("" + entry.getKey(), "" + entry.getValue());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                ProjectUtils.showProgressDialog(mContext, false, "" + getResources().getString(R.string.Please_wait___));
                if(!hasRes){
                getResultApi(jsonObject + "", startTimeSecond, from);}


            } else {

                Toast.makeText(mContext, getResources().getString(R.string.Please_Attempt_Any_Question____), Toast.LENGTH_SHORT).show();

            }

        }
    }


    private void getResultApi(String answerformat, String startTime, final String from) {
Log.v("DebugCheckSaloni","saloni   ------------------"+examType+",,,,,,,,,,,,,,\n"+answerformat);

        forTest = "back";
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_GET_RESULT)
                .addBodyParameter(AppConsts.EXAM_TYPE, "" + examType)
                .addBodyParameter(AppConsts.IS_ADMIN, "" + modelLogin.getStudentData().getAdminId())
                .addBodyParameter(AppConsts.STUDENT_ID, "" + modelLogin.getStudentData().getStudentId())
                .addBodyParameter("question_answer", "" + answerformat)
                .addBodyParameter(AppConsts.PAPER_ID, "" + examId)
                .addBodyParameter(AppConsts.PAPER_NAME, "" + examName)
                .addBodyParameter(AppConsts.TOTAL_QUESTION, "" + totalQues)
                .addBodyParameter(AppConsts.START_TIME, "" + startTime)
                .addBodyParameter("time_duration", "" + durations)
                .setTag(AppConsts.API_GET_RESULT)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
hasRes=true;
                        Log.v("DebugCheckSaloni","response=== " +response);
                        ProjectUtils.pauseProgressDialog();
                        try {
                            JSONObject mainJson = new JSONObject(response);


                            if (AppConsts.TRUE.equals(mainJson.getString(AppConsts.STATUS))) {
                                Toast.makeText(mContext, getResources().getString(R.string.Submit_successfully), Toast.LENGTH_SHORT).show();

                                if (from.equalsIgnoreCase("")) {
                                    setIntentResultValue(response);
                                } else {
                                    setIntentResultValue(response);
                                    finish();
                                }


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtils.pauseProgressDialog();
                        Toast.makeText(mContext, anError.getErrorDetail(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setIntentResultValue(String response) {


        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);

            Intent intent = new Intent(mContext, ActivityGenerateResult.class);
            if ("exam".equalsIgnoreCase(examType)) {
                intent.putExtra(AppConsts.DATE, jsonObject.getString("examDate") + "");
                intent.putExtra(AppConsts.TOTAL_QUESTIONS, jsonObject.getString("totalQuestion") + "");
                intent.putExtra(AppConsts.TOTAL_ATTEMPT, jsonObject.getString("totalAttemptQuestion") + "");
            } else {
                intent.putExtra(AppConsts.EXAM_NAME, examName + "");
                intent.putExtra(AppConsts.EXAM_TYPE, examType + "");
                intent.putExtra(AppConsts.DATE, jsonObject.getString("date") + "");
                intent.putExtra(AppConsts.TOTAL_QUESTIONS, jsonObject.getString("totalQuestion") + "");
                intent.putExtra(AppConsts.TOTAL_ATTEMPT, jsonObject.getString("totalAttempt") + "");
                intent.putExtra(AppConsts.CORRECT_ANSWERS, jsonObject.getString("correctAns") + "");
                intent.putExtra(AppConsts.WRONG_ANSWERS, jsonObject.getString("wrongAns") + "");
                intent.putExtra(AppConsts.TOTAL_SCORE, jsonObject.getString("paperTotalMarks") + "");
                intent.putExtra(AppConsts.negetive, jsonObject.getString("paperNegetiveMarks") + "");
                intent.putExtra(AppConsts.PERCENTAGE, jsonObject.getString("percentage") + "");
                intent.putExtra(AppConsts.START_TIME, jsonObject.getString("startTime") + "");
                intent.putExtra(AppConsts.SUBMIT_TIME, jsonObject.getString("submitTime") + "");
                intent.putExtra(AppConsts.TIME_TAKEN, jsonObject.getString("timeTaken") + "");
            }


            startActivity(intent);
            finish();
            stopCountDownTimer();


        } catch (Exception e) {
            Log.v("TESTTT","TESTTT   "+e);
            e.printStackTrace();
        }
    }

    public static void nextPre(int pos, String btnName) {
        if (btnName.equalsIgnoreCase("next")) {
            rvGeneratePaper.scrollToPosition(pos + 1);
        } else {
            rvGeneratePaper.scrollToPosition(pos - 1);
        }
    }

    public static void updateCountBack(int position, boolean check) {
        if (check)
            dynamicCountList.get(position).setBackgroundResource(R.drawable.ic_checked);
        else
            dynamicCountList.get(position).setBackgroundResource(R.drawable.reset_back_grad);
    }


    void generatePaperApi(String examId) {

        ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_GENERATE_PRACTICE_PAPER)
                .addBodyParameter(AppConsts.PAPER_ID, examId)
                .addBodyParameter(AppConsts.IS_ADMIN, modelLogin.getStudentData().getAdminId())
                .setTag(AppConsts.API_GENERATE_PRACTICE_PAPER)
                .build()
                .getAsObject(ModelGetPaper.class, new ParsedRequestListener<ModelGetPaper>() {
                    @Override
                    public void onResponse(ModelGetPaper response) {
                        try {
                            if ("true".equalsIgnoreCase(response.getStatus())) {

                                if (response.getTotalExamData().get(0).getType().equals("2")) {
                                    examType = "practice";
                                } else {
                                    examType = "mock_test";
                                }
                                if (response.getTotalExamData().get(0).getTotalQuestion().equalsIgnoreCase("0")) {
                                    ProjectUtils.pauseProgressDialog();
                                    Toast.makeText(mContext, "" + getResources().getString(R.string.No_Questions_available), Toast.LENGTH_SHORT).show();


                                } else {
                                    paperList = response.getTotalExamData().get(0).getQuestionDetails();
                                    currentServerTime = response.getCurrentTime();
                                    startTimeSecond = response.getCurrentTime();
                                    durations = response.getTotalExamData().get(0).getTimeDuration();
                                    ProjectUtils.pauseProgressDialog();
                                    for (int i = 0; i < paperList.size(); i++) {
                                        if (paperList.get(i).getId().isEmpty()) {
                                            paperList.remove(i);
                                        } else {
                                            generateDynamicCountLayouts();
                                        }
                                    }
                                    totalQues = "" + response.getTotalExamData().get(0).getTotalQuestion();
                                    tvRemainingQuestion.setText("Q : " + totalQues + "/" + totalQues);
                                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                                    linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                                    adapterGeneratePaper = new AdapterGetPaper(mContext, paperList, holdGenerateAnswer, rvGeneratePaper, (AppConsts) mContext);
                                    rvGeneratePaper.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                                    rvGeneratePaper.setAdapter(adapterGeneratePaper);

                                    startStop();
                                }
                            } else {
                                ProjectUtils.pauseProgressDialog();
                                noResult.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                                allQuestions.setVisibility(View.GONE);
                                submit.setVisibility(View.GONE);
                                Toast.makeText(mContext, "" + getResources().getString(R.string.Paper_is_not_available_this_time_please_try_again), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception E) {
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtils.pauseProgressDialog();

                    }
                });
    }


    public String getCurrentDate(String outputFormat) {
        DateFormat dateFormat = new SimpleDateFormat(outputFormat, Locale.ENGLISH);
        Date date = new Date();
        return dateFormat.format(date);
    }


    private void startStop() {

        if (timerStatus == TimerStatus.STOPPED) {

            setTimerValues();

            setProgressBarValues();

            timerStatus = TimerStatus.STARTED;

            startCountDownTimer();

        } else {

            timerStatus = TimerStatus.STOPPED;

            stopCountDownTimer();
        }
    }

    private void setTimerValues() {

        String[] temp = formattedTime.split(":");

        int timeInMinutes = 0;

        timeInMinutes = timeInMinutes + (Integer.parseInt(temp[0]) * 60) + Integer.parseInt(temp[1]);

        int time = timeInMinutes;


        timeCountInMilliSeconds = time * 60 * 1000;
    }


    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(timeCountInMilliSeconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                tvTime.setText("" + hmsTimeFormatter(millisUntilFinished));
                progressBar.setProgress((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {

                ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));

                if (isFirst) {
                    isFirst = false;
                    submitResult("back");
                    tvTime.setText("Over : 00");
                }


                timerStatus = TimerStatus.STOPPED;

            }

        }.start();
        countDownTimer.start();
    }

    private String hmsTimeFormatter(long milliSeconds) {
        String hms = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(milliSeconds),
                TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(milliSeconds)),
                TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliSeconds)));
        return hms;
    }


    private void stopCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopCountDownTimer();
        finish();

    }


    /* -----------------------------------------------CountDownTimer Methods------------------------------------------------------------ */

    public class AdapterQuestionList extends RecyclerView.Adapter<HolderAdapterQuestionList> {

        ArrayList<ModelGetPaper.TotalExamData.QuestionDetails> paperList;

        View view;

        public AdapterQuestionList(ArrayList<ModelGetPaper.TotalExamData.QuestionDetails> paperList) {
            this.paperList = paperList;


        }

        @NonNull
        @Override
        public HolderAdapterQuestionList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            view = LayoutInflater.from(mContext).inflate(R.layout.questionattempted, viewGroup, false);

            return new HolderAdapterQuestionList(view);

        }

        @Override
        public void onBindViewHolder(@NonNull HolderAdapterQuestionList holder, @SuppressLint("RecyclerView") int i) {
            int s = i;
            s++;
            for (int x = 0; x < holdGenerateAnswer.size(); x++) {
                if (holdGenerateAnswer.containsKey("" + paperList.get(i).getId())) {
                    holder.questionCount.setBackgroundResource(R.drawable.back_strockgreen);
                    holder.questionCount.setTextColor(getResources().getColor(R.color.black));
                } else {
                    holder.questionCount.setBackgroundResource(R.drawable.back_strock);
                    holder.questionCount.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                }
            }
            holder.questionCount.setText("" + s);
            holder.questionCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.questionCount.setTextColor(getResources().getColor(R.color.light_green));
                    rvGeneratePaper.scrollToPosition(i);
                    dialog.dismiss();

                }
            });


        }


        @Override
        public int getItemCount() {
            return paperList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    public class HolderAdapterQuestionList extends RecyclerView.ViewHolder {

        CustomTextExtraBold questionCount;

        public HolderAdapterQuestionList(@NonNull View itemView) {
            super(itemView);
            questionCount = itemView.findViewById(R.id.tvQuestCount);

        }
    }


}

