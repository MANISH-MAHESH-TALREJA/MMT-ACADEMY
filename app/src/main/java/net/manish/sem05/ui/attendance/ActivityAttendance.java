package net.manish.sem05.ui.attendance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import net.manish.sem05.R;
import net.manish.sem05.model.attendanceModel.ModelAttendance;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.base.BaseActivity;
import net.manish.sem05.ui.home.ActivityHome;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomSmallText;
import net.manish.sem05.utils.widgets.CustomTextExtraBold;

import java.util.ArrayList;
import java.util.Calendar;

public class ActivityAttendance extends BaseActivity implements View.OnClickListener {
    Context mContext;
    ArrayList<String> monthList;
    ArrayList<String> yearList;
    CustomTextExtraBold tvHeader;
    ImageView ivBack;
    ImageView noResult;
    LinearLayout llDipResult;
    String month = "";
    String year = "";
    Spinner spMonth;
    Spinner spYear;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    LinearLayout llTableHeading;
    RecyclerView rvAttendance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        mContext = ActivityAttendance.this;
        initial();
    }

    void initial() {
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        monthList = new ArrayList<>();
        yearList = new ArrayList<>();
        llDipResult = (LinearLayout) findViewById(R.id.lldipResult);
        spMonth = (Spinner) findViewById(R.id.spMonth);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        rvAttendance = findViewById(R.id.rvAttendance);
        spYear = (Spinner) findViewById(R.id.spYear);
        llTableHeading = findViewById(R.id.llTableHeading);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        noResult = findViewById(R.id.noResult);
        tvHeader.setText(getResources().getString(R.string.Attendance));
        Calendar c = Calendar.getInstance();
        year = "" + c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH) + 1;
        month = "" + m;
        if (month.length() == 1) {
            month = "0" + month;

        }
        monthList.add(0, "" + month);
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

        ArrayAdapter monthAdapter = new ArrayAdapter(mContext, com.razorpay.R.layout.support_simple_spinner_dropdown_item, monthList);
        spMonth.setAdapter(monthAdapter);


        yearList.add("" + year);

        yearList.add("2019");
        yearList.add("2020");
        yearList.add("2021");
        yearList.add("2022");
        yearList.add("2023");
        yearList.add("2024");
        yearList.add("2025");
        yearList.add("2026");




        ArrayAdapter yearAdapter = new ArrayAdapter(mContext, com.razorpay.R.layout.support_simple_spinner_dropdown_item, yearList);
        spYear.setAdapter(yearAdapter);

        llDipResult.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        apiAttendance();
    }

    void apiAttendance() {
        ProjectUtils.showProgressDialog(mContext, true, getResources().getString(R.string.Loading___));
        month = spMonth.getSelectedItem().toString();
        year = spYear.getSelectedItem().toString();
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_GET_ATTENDANCE)
                .addBodyParameter(AppConsts.STUDENT_ID, modelLogin.getStudentData().getStudentId())
                .addBodyParameter(AppConsts.MONTH, "" + month)
                .addBodyParameter(AppConsts.YEAR, "" + year)
                .setTag(AppConsts.API_PRACTICE_TEST_RESULT)
                .build()
                .getAsObject(ModelAttendance.class, new ParsedRequestListener<ModelAttendance>() {
                    @Override
                    public void onResponse(ModelAttendance response) {

                        ProjectUtils.pauseProgressDialog();
                        if (AppConsts.TRUE.equals(response.getStatus())) {
                            ProjectUtils.pauseProgressDialog();
                            noResult.setVisibility(View.GONE);
                            llTableHeading.setVisibility(View.VISIBLE);
                            rvAttendance.setLayoutManager(new GridLayoutManager(mContext, 1));
                            AdapterList adapterList = new AdapterList(response.getAttendance());
                            rvAttendance.setAdapter(adapterList);
                        } else {
                            ProjectUtils.pauseProgressDialog();
                            noResult.setVisibility(View.VISIBLE);
                            llTableHeading.setVisibility(View.GONE);
                            rvAttendance.setLayoutManager(new GridLayoutManager(mContext, 4));
                            ArrayList<ModelAttendance.Attendance> attendances = new ArrayList<>();
                            AdapterList adapterList = new AdapterList(attendances);
                            rvAttendance.setAdapter(adapterList);

                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtils.pauseProgressDialog();
                    }
                });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.lldipResult) {
            if (!spMonth.getSelectedItem().toString().equals("Month")) {
                if (!spYear.getSelectedItem().toString().equals("Year")) {

                    if (ProjectUtils.checkConnection(mContext)) {
                        apiAttendance();
                    } else {
                        Toast.makeText(mContext, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.PleaseSelectMonthAndYear), Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(mContext, getResources().getString(R.string.PleaseSelectMonthAndYear), Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.ivBack) {
            finish();
            ProjectUtils.pauseProgressDialog();

        }
    }


    public class AdapterList extends RecyclerView.Adapter<HolderAdapterTopScoreList> {

        ArrayList<ModelAttendance.Attendance> listTopScore;

        View view;

        public AdapterList(ArrayList<ModelAttendance.Attendance> list) {
            this.listTopScore = list;


        }

        @NonNull
        @Override
        public HolderAdapterTopScoreList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            view = LayoutInflater.from(mContext).inflate(R.layout.attendance, viewGroup, false);
            return new HolderAdapterTopScoreList(view);

        }

        @Override
        public void onBindViewHolder(@NonNull HolderAdapterTopScoreList holder, int i) {
            holder.tvDateAttendance.setText(listTopScore.get(i).getDate());
            holder.tvTime.setText(listTopScore.get(i).getTime());
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

    @Override
    public void onBackPressed() {
        if ("push".equalsIgnoreCase(getIntent().getStringExtra(AppConsts.IS_PUSH))) {

            startActivity(new Intent(ActivityAttendance.this, ActivityHome.class));
            finish();
        } else {
            super.onBackPressed();
            finish();
        }
    }

    public class HolderAdapterTopScoreList extends RecyclerView.ViewHolder {
        CustomSmallText tvDateAttendance;
        CustomSmallText tvTime;

        public HolderAdapterTopScoreList(@NonNull View itemView) {
            super(itemView);
            tvDateAttendance = itemView.findViewById(R.id.tvDateAttendance);
            tvTime = itemView.findViewById(R.id.tvTime);

        }
    }
}