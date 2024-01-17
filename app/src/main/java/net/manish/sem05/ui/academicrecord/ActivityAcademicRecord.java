package net.manish.sem05.ui.academicrecord;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import net.manish.sem05.R;
import net.manish.sem05.model.academicrecord.ModelAcademicRecord;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.base.BaseActivity;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomTextExtraBold;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ActivityAcademicRecord extends BaseActivity {
    CustomTextExtraBold tvHeader;
    ImageView ivBack;
    Context mContext;
    ArrayList<String> monthList;
    ArrayList<String> yearList;
    String month = "";
    String year = "";
    Spinner spMonth;
    Spinner spYear;
    BarChart barChart;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    ImageView noResult;
    LinearLayout llDipResult;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_academic_record);
        mContext = ActivityAcademicRecord.this;
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("arabic")) {
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
        initial();
    }

    void initial() {
        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        barChart = findViewById(R.id.barchart);
        tvHeader.setText(getResources().getString(R.string.Academic_Record));
        monthList = new ArrayList<>();
        yearList = new ArrayList<>();
        noResult = findViewById(R.id.noResult);
        spMonth = (Spinner) findViewById(R.id.spMonth);
        spYear = (Spinner) findViewById(R.id.spYear);
        llDipResult = (LinearLayout) findViewById(R.id.lldipResult);
        llDipResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!spMonth.getSelectedItem().toString().equals(getResources().getString(R.string.Month))) {
                    if (!spYear.getSelectedItem().toString().equals(getResources().getString(R.string.Year))) {

                        if (ProjectUtils.checkConnection(mContext)) {
                            apiAcademicRecord();
                        } else {
                            Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, getResources().getString(R.string.PleaseSelectMonthAndYear), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.PleaseSelectMonthAndYear), Toast.LENGTH_LONG).show();
                }
            }
        });
        ivBack.setOnClickListener(view -> onBackPressed());
        Calendar c = Calendar.getInstance();
        year = "" + c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        month = "" + m;
        if (month.length() == 1) {
            month = "0" + month;

        }
        monthList.add(0, "month");
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

        ArrayAdapter monthAdapter = new ArrayAdapter(mContext, com.razorpay.R.layout.support_simple_spinner_dropdown_item, monthList) {
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


        // Set popupWindow height to 500px


        yearList.add("year");

        yearList.add("2020");
        yearList.add("2021");
        yearList.add("2022");
        yearList.add("2023");
        yearList.add("2024");
        yearList.add("2025");
        yearList.add("2026");


        ArrayAdapter yearAdapter = new ArrayAdapter(mContext, com.razorpay.R.layout.support_simple_spinner_dropdown_item, yearList);
        spYear.setAdapter(yearAdapter);


        apiAcademicRecord();
    }

    void apiAcademicRecord() {
        ProjectUtils.showProgressDialog(mContext, false, getResources().getString(R.string.Loading___));
        if (!spMonth.getSelectedItem().toString().equals("Month")) {
            month = spMonth.getSelectedItem().toString();
        }

        if (!spYear.getSelectedItem().toString().equalsIgnoreCase("Year")) {
            year = spYear.getSelectedItem().toString();
        } else {
            Calendar c = Calendar.getInstance();
            year = "" + c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH) + 1;
            month = "" + m;
            if (month.length() == 1) {
                month = "0" + month;

            }

        }


        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_GET_ACADEMIC_RECORD)
                .addBodyParameter(AppConsts.STUDENT_ID, modelLogin.getStudentData().getStudentId())
                .addBodyParameter(AppConsts.BATCH_ID, modelLogin.getStudentData().getBatchId())
                .addBodyParameter(AppConsts.MONTH, "" + month)
                .addBodyParameter(AppConsts.YEAR, "" + year)
                .build()
                .getAsObject(ModelAcademicRecord.class, new ParsedRequestListener<ModelAcademicRecord>() {
                    @Override
                    public void onResponse(ModelAcademicRecord response) {

                        ProjectUtils.pauseProgressDialog();
                        if (AppConsts.TRUE.equals(response.getStatus())) {


                            ArrayList<BarEntry> entries = new ArrayList<>();
                            entries.add(new BarEntry(1, Float.parseFloat(response.getAcademicRecord().getExtraClass())));
                            ArrayList<BarEntry> entries2 = new ArrayList<>();
                            entries2.add(new BarEntry(3, Float.parseFloat(response.getAcademicRecord().getPracticeResult())));
                            ArrayList<BarEntry> entries3 = new ArrayList<>();
                            entries3.add(new BarEntry(5, Float.parseFloat(response.getAcademicRecord().getMockResult())));

                            List<IBarDataSet> bars = new ArrayList<>();
                            BarDataSet dataSet;
                            if (getResources().getString(R.string.ExtraClass).length() > 16) {
                                dataSet = new BarDataSet(entries, response.getAcademicRecord().getTotalExtraClass() + " " + getResources().getString(R.string.ExtraClass).substring(0, 13) + "..");
                            } else {
                                dataSet = new BarDataSet(entries, response.getAcademicRecord().getTotalExtraClass() + " " + getResources().getString(R.string.ExtraClass));
                            }
                            dataSet.setColor(ContextCompat.getColor(mContext, R.color.colorPrimary));

                            bars.add(dataSet);
                            BarDataSet dataSet2;
                            if (getResources().getString(R.string.Practice_Paper).length() > 16) {

                                dataSet2 = new BarDataSet(entries2, response.getAcademicRecord().getTotalPracticeTest() + "  " + getResources().getString(R.string.Practice_Paper)
                                        .substring(0, 13) + "..");
                            } else {
                                dataSet2 = new BarDataSet(entries2, response.getAcademicRecord().getTotalPracticeTest() + "  " + getResources().getString(R.string.Practice_Paper)
                                );
                            }

                            dataSet2.setColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDarkTwo));
                            bars.add(dataSet2);

                            BarDataSet dataSet3 = new BarDataSet(entries3, response.getAcademicRecord().getTotalMockTest() + " " + getResources().getString(R.string.MockPaper));
                            dataSet3.setColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
                            bars.add(dataSet3);

                            BarData data = new BarData(bars);
                            data.setValueFormatter(new MyValueFormatter());
                            data.setValueTextSize(14f);
                            Legend l = barChart.getLegend();
                            l.setTextSize(15f);

                            l.setTextColor(Color.BLACK);
                            barChart.setData(data);
                            barChart.getXAxis().setDrawLabels(false);
                            barChart.getDescription().setEnabled(false);
                            barChart.animateY(5000);
                            noResult.setVisibility(View.GONE);
                            barChart.setVisibility(View.VISIBLE);

                            if (response.getAcademicRecord().getTotalExtraClass().equalsIgnoreCase("0") && response.getAcademicRecord().getTotalPracticeTest().equalsIgnoreCase("0") && response
                                    .getAcademicRecord().getTotalMockTest().equalsIgnoreCase("0")) {

                                noResult.setVisibility(View.VISIBLE);
                          barChart.setVisibility(View.GONE);

                            }
                        } else {
                            noResult.setVisibility(View.VISIBLE);

                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(mContext, getResources().getString(R.string.Something_went_wrong), Toast.LENGTH_SHORT).show();
                        ProjectUtils.pauseProgressDialog();
                    }
                });

    }

    public class MyValueFormatter extends ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("#");
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value);
        }
    }
}