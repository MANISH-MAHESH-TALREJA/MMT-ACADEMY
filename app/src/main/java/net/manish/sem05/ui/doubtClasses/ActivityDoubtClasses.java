package net.manish.sem05.ui.doubtClasses;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import net.manish.sem05.R;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityDoubtClasses extends BaseActivity implements View.OnClickListener {
    CustomTextExtraBold tvHeader;
    ImageView ivBack, cancel,noResult;
    RelativeLayout btApplyForClass, btSave;
    LinearLayout llListOfClassHistory, llApplyClass;
    RecyclerView rvList;
    Context context;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    String batch_id;
    Spinner SubjectSpinner, ChapterSpinner;
    CustomeTextRegular tvTeacher;
    CustomEditText edtDesc;
    String teacherId, subjectId, topicId;
    NestedScrollView scrollView;
    CustomeTextRegular date,teachersReply, ChapterName, subjectName;
    LinearLayout headerList;
    CustomTextSemiBold teacherHeading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_classes);
        context = ActivityDoubtClasses.this;
        sharedPref = SharedPref.getInstance(context);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        batch_id = modelLogin.getStudentData().getBatchId();
        ivBack = findViewById(R.id.ivBack);
        tvTeacher = findViewById(R.id.tvTeacher);
        cancel = findViewById(R.id.cancel);
        noResult = findViewById(R.id.noResult);
        headerList = findViewById(R.id.headerList);
        cancel.setOnClickListener(this);
        date = findViewById(R.id.date);
        teachersReply = findViewById(R.id.teachersReply);
        ivBack.setOnClickListener(this);
        apiGetSubject();
        ini();
    }


    void ini() {
        tvHeader = findViewById(R.id.tvHeader);
        ChapterName = findViewById(R.id.ChapterName);
        subjectName = findViewById(R.id.subjectName);
        scrollView = findViewById(R.id.scrollView);
        edtDesc = findViewById(R.id.edtDesc);
        llApplyClass = findViewById(R.id.llApplyClass);
        btSave = findViewById(R.id.btSave);
        SubjectSpinner = findViewById(R.id.SubjectSpinner);
        ChapterSpinner = findViewById(R.id.ChapterSpinner);
        teacherHeading = findViewById(R.id.teacherHeading);


        btSave.setOnClickListener(this);
        llListOfClassHistory = findViewById(R.id.llListOfClassHistory);
        btApplyForClass = findViewById(R.id.btApplyForClass);
        btApplyForClass.setOnClickListener(this);
        tvHeader.setText(getResources().getString(R.string.DoubtClasses));
        rvList = findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(context));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
                @Override
                public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    if (scrollY == 0) {
                        if (llApplyClass.getVisibility() == View.GONE) {
                            btApplyForClass.setVisibility(View.VISIBLE);
                        }
                    } else {
                        btApplyForClass.setVisibility(View.GONE);
                    }
                }
            });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        apiGetListOfDoubtClasses();

    }

    void apiGetListOfDoubtClasses() {
        ProjectUtils.showProgressDialog(context, true, getResources().getString(R.string.Loading___));

        AndroidNetworking.post(AppConsts.BASE_URL+AppConsts.API_GET_DOUBTS_ASK)
                .addBodyParameter(AppConsts.STUDENT_ID, "" + modelLogin.getStudentData().getStudentId())
                .addBodyParameter(AppConsts.BATCH_ID, "" + modelLogin.getStudentData().getBatchId())
                .build()
                .getAsObject(ModelDoubtClassHistory.class, new ParsedRequestListener<ModelDoubtClassHistory>() {
                    @Override
                    public void onResponse(ModelDoubtClassHistory response) {
                        ProjectUtils.pauseProgressDialog();
                        if (response.getStatus().equalsIgnoreCase("true")) {
                            noResult.setVisibility(View.GONE);
                            headerList.setVisibility(View.VISIBLE);
                            ArrayList<ModelDoubtClassHistory.doubtsData> dataArrayList;
                            dataArrayList = response.getDoubtsData();
                            Adapter adapter = new Adapter(dataArrayList);
                            rvList.setAdapter(adapter);

                        } else {
                            noResult.setVisibility(View.VISIBLE);
                            headerList.setVisibility(View.GONE);


                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ProjectUtils.pauseProgressDialog();
                        Toast.makeText(context, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                    }
                });


    }

    void apiGetSubject() {
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_VIEW_SUBJECT_LIST)
                .addBodyParameter(AppConsts.BATCH_ID, batch_id).build()
                .getAsObject(ModelSubject.class, new ParsedRequestListener<ModelSubject>() {
                    @Override
                    public void onResponse(ModelSubject response) {
                        if (response.getStatus().equalsIgnoreCase("True")) {
                            ArrayList<String> listSub = new ArrayList<>();
                            for (int i = 0; i < response.getSubjectData().size(); i++) {
                                listSub.add(response.getSubjectData().get(i).getSubjectName());

                            }
                            ArrayAdapter<String> arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, listSub);
                            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            SubjectSpinner.setAdapter(arrayAdapter);

                            SubjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    for (int x = 0; x < response.getSubjectData().size(); x++) {
                                        if (response.getSubjectData().get(x).getSubjectName().equalsIgnoreCase("" + SubjectSpinner.getSelectedItem())) {
                                            apiGetTeachers("" + response.getSubjectData().get(x).getSubjectId());
                                        }

                                    }

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });


    }

    void apiGetTeachers(String id) {
        teacherId = "";
        subjectId = "" + id;
        topicId = "";
        ProjectUtils.showProgressDialog(context, true, getResources().getString(R.string.Loading___));
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_VIEW_SUBJECT_LIST)
                .addBodyParameter(AppConsts.BATCH_ID, batch_id)
                .addBodyParameter(AppConsts.SUBJECT_ID, id).build()
                .getAsObject(ModelTeacherTopicList.class, new ParsedRequestListener<ModelTeacherTopicList>() {
                    @Override
                    public void onResponse(ModelTeacherTopicList response) {
                        ProjectUtils.pauseProgressDialog();
                        if (response.getStatus().equalsIgnoreCase("True")) {
                            if (response.getSubjectData().get(0).getTeacherData() != null) {
                                ArrayList<String> listTopic = new ArrayList<>();
                                for (int i = 0; i < response.getSubjectData().get(0).getChapterData().size(); i++) {
                                    listTopic.add(response.getSubjectData().get(0).getChapterData().get(i).getChapter_name());

                                }
                                tvTeacher.setText("" + response.getSubjectData().get(0).getTeacherData().get(0).getName());
                                teacherId = "" + response.getSubjectData().get(0).getTeacherData().get(0).getId();
                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, listTopic);
                                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                ChapterSpinner.setAdapter(arrayAdapter);
                                ChapterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        for (int x = 0; x < response.getSubjectData().get(0).getChapterData().size(); x++) {

                                            if (response.getSubjectData().get(0).getChapterData().get(x).getChapter_name().equalsIgnoreCase("" + ChapterSpinner.getSelectedItem())) {

                                                topicId = response.getSubjectData().get(0).getChapterData().get(x).getId();
                                            }

                                        }

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {

                                    }
                                });
                            }

                        } else {
                            Toast.makeText(context, ""+getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();


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
        if (id == R.id.ivBack) {
            onBackPressed();
        } else if (id == R.id.btSave) {
            if (teacherId.isEmpty()) {
                Toast.makeText(context, "Select Teacher !", Toast.LENGTH_SHORT).show();
            } else {

                if (subjectId.isEmpty()) {
                    Toast.makeText(context, "Select subject !", Toast.LENGTH_SHORT).show();
                } else {
                    if (topicId.isEmpty()) {
                        Toast.makeText(context, "Select topic !", Toast.LENGTH_SHORT).show();
                    } else {
                        dataSaveApi();
                    }
                }
            }
        } else if (id == R.id.cancel) {
            btApplyForClass.setVisibility(View.VISIBLE);
            SubjectSpinner.setVisibility(View.VISIBLE);
            ChapterSpinner.setVisibility(View.VISIBLE);
            btSave.setVisibility(View.VISIBLE);
            ChapterName.setVisibility(View.GONE);
            subjectName.setVisibility(View.GONE);
            date.setText("");
            edtDesc.setText("");
            llApplyClass.setVisibility(View.GONE);
            llListOfClassHistory.setVisibility(View.VISIBLE);
            teachersReply.setVisibility(View.GONE);
            teacherHeading.setVisibility(View.GONE);
            btApplyForClass.setVisibility(View.VISIBLE);
            scrollView.smoothScrollTo(0, 0);
            scrollView.fullScroll(View.FOCUS_UP);
        } else if (id == R.id.btApplyForClass) {
            llListOfClassHistory.setVisibility(View.GONE);
            llApplyClass.setVisibility(View.VISIBLE);
            btApplyForClass.setVisibility(View.GONE);
        }

    }

    void dataSaveApi() {

        ProjectUtils.showProgressDialog(context, true, getResources().getString(R.string.Loading___));

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_DOUBTS_CLASS_ASK)
                .addBodyParameter(AppConsts.TEACHER_ID, "" + teacherId)
                .addBodyParameter(AppConsts.STUDENT_ID, "" + modelLogin.getStudentData().getStudentId())
                .addBodyParameter(AppConsts.BATCH_ID, "" + modelLogin.getStudentData().getBatchId())
                .addBodyParameter(AppConsts.SUBJECT_ID, "" + subjectId)
                .addBodyParameter(AppConsts.CHAPTER_ID, "" + topicId)
                .addBodyParameter(AppConsts.DESCRIPTION, "" + edtDesc.getText().toString())
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {


                try {
                    ProjectUtils.pauseProgressDialog();
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("True")) {
                        Toast.makeText(context, getResources().getString(R.string.Applied_successfully_), Toast.LENGTH_SHORT).show();
                        llApplyClass.setVisibility(View.GONE);
                        llListOfClassHistory.setVisibility(View.VISIBLE);
                        btApplyForClass.setVisibility(View.VISIBLE);
                        edtDesc.setText("");
                        apiGetListOfDoubtClasses();


                    } else {
                        Toast.makeText(context, "" + jsonObject.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    ProjectUtils.pauseProgressDialog();
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(ANError anError) {
                ProjectUtils.pauseProgressDialog();
                Toast.makeText(context, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        if(getIntent().hasExtra(AppConsts.IS_PUSH)){
            startActivity(new Intent(getApplicationContext(), ActivityHome.class));
            finish();

        }else{
            super.onBackPressed();
        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ActivityHolderView> {
        View view;
        ArrayList<ModelDoubtClassHistory.doubtsData> list;

        Adapter(ArrayList<ModelDoubtClassHistory.doubtsData> list) {
            this.list = list;

        }

        @NonNull
        @Override
        public ActivityHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            view = LayoutInflater.from(context).inflate(R.layout.list_doubtclass, parent, false);
            return new ActivityHolderView(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ActivityHolderView holder, int position) {
            holder.subjectTv.setText("" + list.get(position).getSubjectName());

            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                final Date dateStartObj = sdf.parse(list.get(position).getCreateAt());

                holder.chapterNameTv.setText(new SimpleDateFormat("dd-MM-yyyy").format(dateStartObj)
                );
            }catch (Exception e){

            }

            holder.teacherNameTv.setText("" + list.get(position).getTeacherName());
            if (list.get(position).getStatus().equalsIgnoreCase("0")) {
                holder.statusTv.setText(getResources().getString(R.string.Pending));
            }
            if (list.get(position).getStatus().equalsIgnoreCase("1")) {
                holder.statusTv.setText(getResources().getString(R.string.Approved));
                holder.statusTv.setTextColor(getResources().getColor(R.color.green_dark));


            }
            if (list.get(position).getStatus().equalsIgnoreCase("2")) {
                holder.statusTv.setText(getResources().getString(R.string.Decline));
                holder.statusTv.setTextColor(getResources().getColor(R.color.app_red));
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (list.get(position).getStatus().equalsIgnoreCase("1")) {
                        llListOfClassHistory.setVisibility(View.GONE);
                        llApplyClass.setVisibility(View.VISIBLE);
                        btApplyForClass.setVisibility(View.GONE);
                        btSave.setVisibility(View.GONE);
                        edtDesc.setText("" + list.get(position).getUsersDescription());
                        tvTeacher.setText("" + list.get(position).getTeacherName());
                        SubjectSpinner.setVisibility(View.GONE);
                        ChapterSpinner.setVisibility(View.GONE);

                        ChapterName.setVisibility(View.VISIBLE);
                        teachersReply.setVisibility(View.VISIBLE);
                        teacherHeading.setVisibility(View.VISIBLE);

                        subjectName.setVisibility(View.VISIBLE);
                        ChapterName.setText("" + list.get(position).getChapterName());
                        subjectName.setText("" + list.get(position).getSubjectName());


                        date.setText(getResources().getString(R.string.Date) + " - " + list.get(position).getAppointmentDate() + "\n"+getResources().getString(R.string.Time) +"- "+ list.get(position).getAppointmentTime()
                                );
                        teachersReply.setText(" "+list.get(position).getTeacherDescription());

                    } else {
                        Toast.makeText(context, getResources().getString(R.string.NotApproved), Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class ActivityHolderView extends RecyclerView.ViewHolder {
            CustomeTextRegular subjectTv, chapterNameTv, teacherNameTv, statusTv;

            public ActivityHolderView(@NonNull View itemView) {
                super(itemView);
                subjectTv = itemView.findViewById(R.id.subjectTv);
                chapterNameTv = itemView.findViewById(R.id.chapterNameTv);
                teacherNameTv = itemView.findViewById(R.id.teacherNameTv);
                statusTv = itemView.findViewById(R.id.statusTv);


            }
        }

    }

}


