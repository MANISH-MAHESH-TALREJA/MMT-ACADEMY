package net.manish.sem05.ui.assignment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import net.manish.sem05.R;
import net.manish.sem05.model.modelhomework.ModelHomeWork;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.base.BaseActivity;
import net.manish.sem05.ui.home.ActivityHome;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomEditText;
import net.manish.sem05.utils.widgets.CustomTextExtraBold;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class ActivityAssignment extends BaseActivity implements View.OnClickListener {
    private Context mContext;
    private RecyclerView rvHomeWork;
    ImageView ivNoData;
    ImageView ivBack;
    CustomTextExtraBold tvHeader;
    HorizontalCalendarView calendarView;
    LinearLayout openDatePick;
    DatePickerDialog.OnDateSetListener datePick;
    Calendar myCalendar;
    CustomEditText etSearch;
    String d = "";
    Calendar startDate;
    Calendar  endDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment);
        mContext = ActivityAssignment.this;
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() {


        rvHomeWork = findViewById(R.id.rvHomeWork);
        ivNoData = findViewById(R.id.ivNoData);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvHeader = findViewById(R.id.tvHeader);
        tvHeader.setText(getResources().getString(R.string.Assignment));
        rvHomeWork.setLayoutManager(new LinearLayoutManager(mContext));

        if (getIntent().hasExtra("date")) {

            try {
                String[] stringList = getIntent().getStringExtra("date").split("Date :");
                String dateString = "" + stringList[1];
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                Date dateObject = format.parse(dateString);
                SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                String formatted = format1.format(dateObject.getTime());
                Calendar tCalendar = Calendar.getInstance();
                tCalendar.setTime(dateObject);
                startDate=tCalendar;
                endDate=tCalendar;
                getHomeWork(formatted);
                d = formatted;
            } catch (Exception e) {

            }


        } else {


            Calendar date = Calendar.getInstance();
            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
            String formatted = format1.format(date.getTime());
            getHomeWork(formatted);
            d = formatted;


        }


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

            try {
                if (!getIntent().hasExtra("date")) {
                startDate = Calendar.getInstance();
                endDate = Calendar.getInstance();}


                startDate.add(Calendar.MONTH, -1);
                endDate.add(Calendar.MONTH, 1);

                HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                        .range(startDate, endDate)
                        .datesNumberOnScreen(3)
                        .build();


                horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
                    @Override
                    public void onDateSelected(Calendar date, int position) {


                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {


                            try {
                                String newDate=""+date.getTime();

                                DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy" , Locale.ENGLISH);
                                Date datenew =formatter.parse(newDate);

                                DateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                                String formattedDate = targetFormat.format(datenew);

                                rvHomeWork.setAdapter(null);
                                getHomeWork(formattedDate);
                            } catch (ParseException e) {
                                e.printStackTrace();

                            }

                        } else {
                            SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                            String formatted = format1.format(date.getTime());
                            rvHomeWork.setAdapter(null);
                            getHomeWork(formatted);

                        }

                    }
                });
            } catch (Exception e) {

                Toast.makeText(mContext, ""+getResources().getString(R.string.Something_went_wrong), Toast.LENGTH_SHORT).show();
            }
        } else {
            calendarView = findViewById(R.id.calendarView);
            calendarView.setVisibility(View.GONE);
            openDatePick = findViewById(R.id.opendatepick);
            openDatePick.setVisibility(View.VISIBLE);
            openDatePick.setOnClickListener(this);
            etSearch = findViewById(R.id.etSearch);
            etSearch.setText("" + d);
            etSearch.setOnClickListener(this);
            myCalendar = Calendar.getInstance();

            datePick = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    String myFormat = "dd-MM-yyyy";
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                    etSearch.setText("" + sdf.format(myCalendar.getTime()));
                    getHomeWork(sdf.format(myCalendar.getTime()));


                }

            };

        }
    }

    private void getHomeWork(String date) {
        SharedPref sharedPref = SharedPref.getInstance(mContext);
        ModelLogin modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.HOME_WORK)
                .addBodyParameter(AppConsts.BATCH_ID, modelLogin.getStudentData().getBatchId())
                .addBodyParameter(AppConsts.IS_ADMIN, modelLogin.getStudentData().getAdminId())
                .addBodyParameter(AppConsts.STUDENT_ID, modelLogin.getStudentData().getStudentId())
                .addBodyParameter(AppConsts.HOME_WORK_DATE, "" + date)
                .setPriority(Priority.IMMEDIATE)
                .setTag(AppConsts.HOME_WORK)
                .build()
                .getAsObject(ModelHomeWork.class, new ParsedRequestListener<ModelHomeWork>() {
                    @Override
                    public void onResponse(ModelHomeWork response) {


                        if ("true".equals(response.getStatus())) {
                            ivNoData.setVisibility(View.GONE);

                            if (response.getHomeWork().size() > 0) {
                                AdapterAssignment adapterHomeWork = new AdapterAssignment(mContext, response.getHomeWork());
                                rvHomeWork.setAdapter(adapterHomeWork);
                            }

                        } else {

                            ivNoData.setVisibility(View.VISIBLE);
                            ArrayList<ModelHomeWork.Homework> homework = new ArrayList<>();
                            AdapterAssignment adapterHomeWork = new AdapterAssignment(mContext, homework);
                            rvHomeWork.setAdapter(adapterHomeWork);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(mContext, getResources().getString(R.string.Try_again), Toast.LENGTH_LONG).show();
                    }
                });


    }

    @Override
    public void onBackPressed() {
        if ("push".equalsIgnoreCase(getIntent().getStringExtra(AppConsts.IS_PUSH))) {

            startActivity(new Intent(ActivityAssignment.this, ActivityHome.class));
            finish();
        } else {
            super.onBackPressed();
            finish();
        }

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.etSearch || id == R.id.opendatepick) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, datePick, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        }

    }
}
