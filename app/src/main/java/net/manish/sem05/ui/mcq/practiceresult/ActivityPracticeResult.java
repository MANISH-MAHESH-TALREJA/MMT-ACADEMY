package com.pixelnx.eacademy.ui.mcq.practiceresult;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.model.modelpractiesresult.ModelPractiesResult;
import com.pixelnx.eacademy.ui.base.BaseActivity;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomTextExtraBold;
import com.pixelnx.eacademy.utils.widgets.CustomTextSemiBold;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityPracticeResult extends BaseActivity implements View.OnClickListener {
    Context mContext;
    ArrayList<String> monthList;
    ArrayList<String> yearList;
    ArrayList<ModelPractiesResult.PracticeResult> testResultList;
    Spinner spMonth;
    Spinner spYear;
    String examType = "";
    String studentId = "";
    SharedPref sharedPref;
    ImageView ivBack;
    ImageView ivNoData;
    ModelLogin modelLogin;
    RecyclerView rvPracticeResult;
    LinearLayout llDipResult;
    String month = "";
    String year = "";
    float sum = 0;
    LineChart linechart;
    CustomTextExtraBold tvAverage;
    LinearLayout selectMonthTextView;
    CustomTextSemiBold btViewChart;
    LinearLayout filterHeader;
    SwipeRefreshLayout swipeRefreshLayout;
    CustomTextExtraBold tvHeader;
    HorizontalScrollView horizonScrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_practice_result);
        mContext = ActivityPracticeResult.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        monthList = new ArrayList<>();
        yearList = new ArrayList<>();
        testResultList = new ArrayList<>();
        examType = getIntent().getStringExtra(AppConsts.EXAM_TYPE);
        studentId = modelLogin.getStudentData().getStudentId();


        init();
    }

    private void init() {
        spMonth = (Spinner) findViewById(R.id.spMonth);
        tvAverage = (CustomTextExtraBold) findViewById(R.id.tvAverage);
        spYear = (Spinner) findViewById(R.id.spYear);
        filterHeader = (LinearLayout) findViewById(R.id.filterHeader);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        ivNoData = (ImageView) findViewById(R.id.ivNoData);
        selectMonthTextView = findViewById(R.id.selectMonthTextView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        btViewChart = findViewById(R.id.btViewChart);
        horizonScrollView = findViewById(R.id.horizonScrollView);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        llDipResult = (LinearLayout) findViewById(R.id.lldipResult);
        rvPracticeResult = (RecyclerView) findViewById(R.id.rvPracticeResult);
        linechart = (LineChart) findViewById(R.id.linechart);
        swipeRefreshLayout.setColorSchemeColors(Color.RED);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ProjectUtils.checkConnection(mContext)) {

                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                }
            }
        });

        if ("practice".equals(examType)) {
            tvHeader.setText(getResources().getString(R.string.Practice_Result));
        } else if ("exam".equals(examType)) {
            tvHeader.setText("Test Result");
        } else {
            tvHeader.setText(getResources().getString(R.string.Mock_Test_Result));
        }
        Calendar c = Calendar.getInstance();
        year = "" + c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        month = "" + m;
        if (month.length() == 1) {
            month = "0" + month;

        }

        monthList.add("Month");
        monthList.add("01");
        monthList.add("02");
        monthList.add("03");
        monthList.add("04");
        monthList.add("05");
        monthList.add("06");
        monthList.add("07");
        monthList.add("08");
        monthList.add("09");
        monthList.add("10");
        monthList.add("11");
        monthList.add("12");


        ArrayAdapter monthAdapter = new ArrayAdapter(mContext, com.razorpay.R.layout.support_simple_spinner_dropdown_item, monthList){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                convertView = super.getDropDownView(position, convertView,
                        parent);

                convertView.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams p = convertView.getLayoutParams();
                p.height = 90; // set the height
                convertView.setLayoutParams(p);
                return convertView;
            }
        };

        spMonth.setAdapter(monthAdapter);


        yearList.add("Year");
        yearList.add("2020");
        yearList.add("2021");
        yearList.add("2022");
        yearList.add("2023");
        yearList.add("2024");
        yearList.add("2025");
        yearList.add("2026");
        yearList.add("2027");

        ArrayAdapter yearAdapter = new ArrayAdapter(mContext, com.razorpay.R.layout.support_simple_spinner_dropdown_item, yearList);
        spYear.setAdapter(yearAdapter);
        practiceResultApi();
        llDipResult.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        btViewChart.setOnClickListener(this);
    }

    private void practiceResultApi() {

        if (!spMonth.getSelectedItem().toString().equals("Month")) {
            month = spMonth.getSelectedItem().toString();
        }

        if (!spYear.getSelectedItem().toString().equalsIgnoreCase("Year")) {
            year = spYear.getSelectedItem().toString();
        }else{
            Calendar c = Calendar.getInstance();
            year = "" + c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH) + 1;
            month = "" + m;
            if (month.length() == 1) {
                month = "0" + month;

            }

        }


        ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));
Log.v("DebugCheckSaloni","DebugCheckSaloni ============"+year+",,"+month+examType+","+modelLogin.getStudentData().getBatchId());

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_PRACTICE_TEST_RESULT)
                .addBodyParameter(AppConsts.STUDENT_ID, studentId)
                .addBodyParameter(AppConsts.MONTH, "" + month)
                .addBodyParameter(AppConsts.YEAR, "" + year)
                .addBodyParameter(AppConsts.EXAM_TYPE, examType)
                .addBodyParameter(AppConsts.IS_ADMIN, modelLogin.getStudentData().getAdminId())
                .addBodyParameter(AppConsts.BATCH_ID, modelLogin.getStudentData().getBatchId())
                .setTag(AppConsts.API_PRACTICE_TEST_RESULT)
                .build()
                .getAsObject(ModelPractiesResult.class, new ParsedRequestListener<ModelPractiesResult>() {
                    @Override
                    public void onResponse(ModelPractiesResult response) {
                        ProjectUtils.pauseProgressDialog();
                        swipeRefreshLayout.setRefreshing(false);
                        if (response == null) {
                            ivNoData.setVisibility(View.VISIBLE);
                            testResultList = new ArrayList<>();
                            tvAverage.setText("");
                            AdapterPracticeResult adapterPracticePaper = new AdapterPracticeResult(mContext, testResultList, examType, studentId);
                            rvPracticeResult.setLayoutManager(new LinearLayoutManager(mContext));
                            rvPracticeResult.setAdapter(adapterPracticePaper);

                        }
                        try {

                            if (AppConsts.TRUE.equals(response.getStatus())) {
                                ivNoData.setVisibility(View.GONE);

                                testResultList = response.getResultData();
                                if (response.getResultData().size() == 0) {
                                    ivNoData.setVisibility(View.VISIBLE);
                                }


                                AdapterPracticeResult adapterPracticePaper = new AdapterPracticeResult(mContext, testResultList, examType, studentId);
                                rvPracticeResult.setLayoutManager(new LinearLayoutManager(mContext));
                                rvPracticeResult.setAdapter(adapterPracticePaper);

                                final String[] months = new String[testResultList.size()];
                                ArrayList<Entry> entries = new ArrayList<>();


                                tvAverage.setText("Average Percent : " + String.format("%.2f", sum / testResultList.size()) + "%");
                                int size = testResultList.size() - 1;
                                int x = 0;
                                for (int i = size; i >= 0; i--) {

                                    try {
                                        if (testResultList.get(x).getPaperName().length() >= 6) {
                                            String s = "" + testResultList.get(x).getPaperName();
                                            months[i] = s.substring(0, 6);
                                        } else {
                                            months[i] = testResultList.get(x).getPaperName();
                                        }


                                        entries.add(new Entry(x, Float.parseFloat(testResultList.get(i).getPercentage())));

                                        x++;

                                    } catch (NumberFormatException e) {
                                        e.printStackTrace();
                                    }
                                }

                                LineDataSet dataSet = new LineDataSet(entries, "result");
                                dataSet.setColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                                dataSet.setValueTextColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                                dataSet.setValueTextSize(12f);

                                XAxis xAxis = linechart.getXAxis();
                                linechart.setTouchEnabled(true);
                                linechart.setScrollContainer(true);
                                linechart.setHorizontalScrollBarEnabled(true);
                                linechart.setScaleXEnabled(true);
                                xAxis.setGranularityEnabled(true);
                                xAxis.setAvoidFirstLastClipping(true);
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                xAxis.setLabelCount(testResultList.size() + 1);
                                xAxis.setLabelRotationAngle(270);
                                if (testResultList.size() > 18) {
                                    linechart.setMinimumWidth(2999);

                                } else {
                                    if (testResultList.size() > 8) {
                                        if (testResultList.size() <= 4) {
                                            linechart.setMinimumWidth(1900);
                                        } else {
                                            linechart.setMinimumWidth(1990);
                                        }

                                    } else {
                                        linechart.setMinimumWidth(1600);
                                    }
                                }
                                ValueFormatter valueFormatter = new ValueFormatter() {
                                    @Override
                                    public String getFormattedValue(float value) {

                                        if (testResultList.size() == 1)
                                            return months[0];
                                        else {
                                            try {
                                                return months[(int) value];
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        return months[0];

                                    }
                                };

                                xAxis.setValueFormatter(valueFormatter);
                                Legend l = linechart.getLegend();
                                l.setWordWrapEnabled(true);
                                linechart.setVisibleXRangeMaximum(5);


                                linechart.invalidate();

                                YAxis yAxisRight = linechart.getAxisRight();
                                yAxisRight.setEnabled(false);

                                YAxis yAxisLeft = linechart.getAxisLeft();
                                yAxisLeft.setGranularity(1f);

                                LineData data = new LineData(dataSet);

                                linechart.setData(data);

                                linechart.animateX(2500);

                                linechart.getDescription().setEnabled(false);
                                linechart.invalidate();


                            } else {

                                ivNoData.setVisibility(View.VISIBLE);
                                testResultList = new ArrayList<>();
                                tvAverage.setText("");
                                AdapterPracticeResult adapterPracticePaper = new AdapterPracticeResult(mContext, testResultList, examType, studentId);
                                rvPracticeResult.setLayoutManager(new LinearLayoutManager(mContext));
                                rvPracticeResult.setAdapter(adapterPracticePaper);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.v("DebugCheckSaloni","DebugCheckSaloni-- "+anError);
                        ProjectUtils.pauseProgressDialog();
                        testResultList = new ArrayList<>();
                        sum = 0;
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(mContext,""+getResources().getString(R.string.Try_again_server_issue),Toast.LENGTH_SHORT).show();
                    }

                });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lldipResult:
                if (!spMonth.getSelectedItem().toString().equals("Month")) {
                    if (!spYear.getSelectedItem().toString().equals("Year")) {
                        sum = 0;
                        if (ProjectUtils.checkConnection(mContext)) {
                            practiceResultApi();
                        } else {
                            Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, getResources().getString(R.string.PleaseSelectMonthAndYear), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.PleaseSelectMonthAndYear), Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.ivBack:
                finish();
                break;

            case R.id.btViewChart:
                linechart.setVisibility(View.VISIBLE);
                horizonScrollView.setVisibility(View.VISIBLE);
                selectMonthTextView.setVisibility(View.GONE);
                rvPracticeResult.setVisibility(View.GONE);
                filterHeader.setVisibility(View.GONE);
                tvAverage.setVisibility(View.GONE);
                btViewChart.setVisibility(View.GONE);
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linechart.setVisibility(View.GONE);
            horizonScrollView.setVisibility(View.GONE);
            selectMonthTextView.setVisibility(View.VISIBLE);
            rvPracticeResult.setVisibility(View.VISIBLE);
            filterHeader.setVisibility(View.VISIBLE);
            btViewChart.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            finish();
        }
    }

}
