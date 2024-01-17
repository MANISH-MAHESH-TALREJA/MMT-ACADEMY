package net.manish.sem05.ui.applyleave;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import net.manish.sem05.R;
import net.manish.sem05.model.modelLeave.ModelLeaveHistory;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.base.BaseActivity;
import net.manish.sem05.ui.home.ActivityHome;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomEditText;
import net.manish.sem05.utils.widgets.CustomTextExtraBold;
import net.manish.sem05.utils.widgets.CustomTextSemiBold;
import net.manish.sem05.utils.widgets.CustomeTextRegular;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class ActivityApplyLeave extends BaseActivity {
    ImageView ivBack;
    ImageView  cancel;
    CustomTextExtraBold tvHeader;
    CustomEditText fromDate;
    CustomEditText  toDate;
    CustomEditText  messageBody;
    CustomEditText   edtSubject;
    RelativeLayout btApply;
    RelativeLayout  btApplyForLeave;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    Context context;
    LinearLayout llApplyLeave;
    LinearLayout llLeaveHistory;
    RecyclerView rvListLeaveData;
    CustomTextSemiBold otherDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_leave);
        context = ActivityApplyLeave.this;

        initial();
    }

    void initial() {
        sharedPref = SharedPref.getInstance(context);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        fromDate = findViewById(R.id.fromDate);
        rvListLeaveData = findViewById(R.id.rvListLeaveData);
        toDate = findViewById(R.id.toDate);
        btApply = findViewById(R.id.btApply);
        llLeaveHistory = findViewById(R.id.llLeaveHistory);
        cancel = findViewById(R.id.cancel);
        llApplyLeave = findViewById(R.id.llApplyLeave);
        btApplyForLeave = findViewById(R.id.btApplyForLeave);
        edtSubject = findViewById(R.id.edtSubject);
        messageBody = findViewById(R.id.messageBody);
        otherDetails = findViewById(R.id.otherDetails);
        btApplyForLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llApplyLeave.setVisibility(View.VISIBLE);
                btApplyForLeave.setVisibility(View.GONE);
                llLeaveHistory.setVisibility(View.GONE);
                fromDate.setText("");
                toDate.setText("");
                edtSubject.setText("");
                messageBody.setText("");
                btApply.setVisibility(View.VISIBLE);
                otherDetails.setVisibility(View.GONE);
                messageBody.setFocusable(true);
                edtSubject.setEnabled(true);
                messageBody.setFocusableInTouchMode(true);
                edtSubject.setFocusableInTouchMode(true);
                fromDate.setEnabled(true);
                toDate.setEnabled(true);

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                llApplyLeave.setVisibility(View.GONE);
                btApplyForLeave.setVisibility(View.VISIBLE);
                llLeaveHistory.setVisibility(View.VISIBLE);
                leaveHistory();
            }
        });
        btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!fromDate.getText().toString().isEmpty()) {
                    if (!toDate.getText().toString().isEmpty()) {
                        if (!edtSubject.getText().toString().isEmpty()) {
                            if (!messageBody.getText().toString().isEmpty()) {

                                DateFormat format = new SimpleDateFormat("dd-mm-yyyy");
                                Date date = null;
                                Date date2 = null;
                                try {
                                    date = format.parse(fromDate.getText().toString());
                                    date2 = format.parse(toDate.getText().toString());




                                        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_APPLY_LEAVE)
                                                .addBodyParameter(AppConsts.UID, "" + modelLogin.getStudentData().getStudentId())
                                                .addBodyParameter(AppConsts.FROM_DATE, "" + fromDate.getText().toString())
                                                .addBodyParameter(AppConsts.TO_DATE, "" + toDate.getText().toString())
                                                .addBodyParameter(AppConsts.SUBJECT, "" + edtSubject.getText().toString())
                                                .addBodyParameter(AppConsts.LEAVE_MSG, "" + messageBody.getText().toString())
                                                .setTag(AppConsts.API_PRACTICE_TEST_RESULT)
                                                .build()
                                                .getAsString(new StringRequestListener() {
                                                    @Override
                                                    public void onResponse(String response) {

                                                        try {
                                                            JSONObject jsonObject = new JSONObject(response);
                                                            if (jsonObject.getString("status").equals("true")) {
                                                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.Applied_successfully_), Toast.LENGTH_SHORT).show();
                                                                llApplyLeave.setVisibility(View.GONE);
                                                                btApplyForLeave.setVisibility(View.VISIBLE);
                                                                llLeaveHistory.setVisibility(View.VISIBLE);
                                                                leaveHistory();
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                                                            }

                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }

                                                    @Override
                                                    public void onError(ANError anError) {

                                                    }
                                                });


                                } catch (ParseException e) {
                                    e.printStackTrace();

                                }


                            } else {
                                messageBody.setError(getResources().getString(R.string.Required_Field));

                            }

                        } else {
                            edtSubject.setError(getResources().getString(R.string.Subject_required));
                        }

                    } else {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.Select_Date), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.Select_Date), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivBack = (ImageView) findViewById(R.id.ivBack);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        tvHeader.setText(getResources().getString(R.string.Apply_Leave));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment(fromDate);
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new SelectDateFragment(toDate);
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });
        leaveHistory();
    }

    void leaveHistory() {
        ProjectUtils.showProgressDialog(ActivityApplyLeave.this,true,""+getResources().getString(R.string.Loading___));
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_VIEW_LEAVE)
                .addBodyParameter(AppConsts.UID, "" + modelLogin.getStudentData().getStudentId())
                .build()
                .getAsObject(ModelLeaveHistory.class, new ParsedRequestListener<ModelLeaveHistory>() {
                    @Override
                    public void onResponse(ModelLeaveHistory response) {
                        ProjectUtils.pauseProgressDialog();
                        if (AppConsts.TRUE.equals(response.getStatus())) {
                            ArrayList<ModelLeaveHistory.LeaveData> list = response.getLeaveData();
                            AdapterList adapterList = new AdapterList(list);
                            rvListLeaveData.setLayoutManager(new LinearLayoutManager(context));
                            rvListLeaveData.setAdapter(adapterList);

                        } else {

                            llLeaveHistory.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtils.pauseProgressDialog();


                    }
                });


    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        private EditText edit;

        public SelectDateFragment(EditText edit) {
            this.edit = edit;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar calendar = Calendar.getInstance();
            int yy = calendar.get(Calendar.YEAR);
            int mm = calendar.get(Calendar.MONTH);
            int dd = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, yy, mm, dd);
            datePicker.getDatePicker().setMinDate(System.currentTimeMillis());
            return datePicker;
        }

        public void onDateSet(DatePicker view, int yy, int mm, int dd) {
            populateSetDate(edit, yy, mm + 1, dd);
        }
    }

    public static void populateSetDate(EditText edit, int year, int month, int day) {
        edit.setText(day + "-" + month + "-" + year);
    }

    public class AdapterList extends RecyclerView.Adapter<HolderAdapterTopScoreList> {

        ArrayList<ModelLeaveHistory.LeaveData> list;

        View view;

        public AdapterList(ArrayList<ModelLeaveHistory.LeaveData> list) {
            this.list = list;


        }

        @NonNull
        @Override
        public HolderAdapterTopScoreList onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            view = LayoutInflater.from(context).inflate(R.layout.leave_list, viewGroup, false);

            return new HolderAdapterTopScoreList(view);

        }

        @Override
        public void onBindViewHolder(@NonNull HolderAdapterTopScoreList holder, int i) {
            try {
                String startDate = ""+list.get(i).getAddedAt();
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                Date date = (Date)formatter.parse(startDate);
                SimpleDateFormat newFormat = new SimpleDateFormat("dd-MMM-yy");
                String finalString = newFormat.format(date);
                holder.dateTv.setText("" + finalString);

                if (list.get(i).getStatus().equalsIgnoreCase("0")) {
                    holder.statusTv.setText(getResources().getString(R.string.Pending));
                } else {
                    if (list.get(i).getStatus().equalsIgnoreCase("1")) {
                        holder.statusTv.setText(""+getResources().getString(R.string.Approved));
                    } else {
                        holder.statusTv.setText(""+getResources().getString(R.string.Decline));
                    }
                }

                holder.subjectTv.setText("" + list.get(i).getSubject());
                holder.seeTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        holder.seeTv.setVisibility(View.VISIBLE);
                        btApplyForLeave.setVisibility(View.GONE);
                        llLeaveHistory.setVisibility(View.GONE);
                        llApplyLeave.setVisibility(View.VISIBLE);
                        fromDate.setText("" + list.get(i).getFromDate());
                        toDate.setText("" + list.get(i).getToDate());
                        edtSubject.setText("" + list.get(i).getSubject());
                        messageBody.setText("" + list.get(i).getLeaveMsg());
                        messageBody.setFocusable(false);
                        messageBody.setFocusableInTouchMode(false);
                        edtSubject.setEnabled(false);
                        fromDate.setEnabled(false);
                        toDate.setEnabled(false);
                        btApply.setVisibility(View.GONE);
                        otherDetails.setVisibility(View.VISIBLE);
                        if(list.get(i).getStatus().equalsIgnoreCase("0")){
                        otherDetails.setText(getResources().getString(R.string.Totaldays)+":  "+list.get(i).getTotalDays()+" \n"+getResources().getString(R.string.Status)+":    "+getResources().getString(R.string.Pending));
                        }else{
                            if (list.get(i).getStatus().equalsIgnoreCase("1")) {
                                otherDetails.setText(getResources().getString(R.string.Totaldays)+":  "+list.get(i).getTotalDays()+" \n"+getResources().getString(R.string.Status)+":    "+getResources().getString(R.string.Approved));
                            } else {
                                otherDetails.setText(getResources().getString(R.string.Totaldays)+":  "+list.get(i).getTotalDays()+" \n"+getResources().getString(R.string.Status)+":    "+getResources().getString(R.string.Decline));
                            }

                        }
                    }
                });
            } catch (Exception e) {

            }

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if ("push".equalsIgnoreCase(getIntent().getStringExtra(AppConsts.IS_PUSH))) {
            startActivity(new Intent(ActivityApplyLeave.this, ActivityHome.class));
            finish();
        } else finish();
    }

    public class HolderAdapterTopScoreList extends RecyclerView.ViewHolder {
        CustomeTextRegular seeTv;
        CustomeTextRegular  statusTv;
        CustomeTextRegular   dateTv;
        CustomeTextRegular   subjectTv;

        public HolderAdapterTopScoreList(@NonNull View itemView) {
            super(itemView);
            seeTv = itemView.findViewById(R.id.seeTv);
            statusTv = itemView.findViewById(R.id.statusTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            subjectTv = itemView.findViewById(R.id.subjectTv);

        }
    }

}