package com.pixelnx.eacademy.ui.noticeAnnouncement.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.modellogin.ModelLogin;
import com.pixelnx.eacademy.model.modelnotify.ModelNotify;
import com.pixelnx.eacademy.ui.noticeAnnouncement.AdapterNotifications;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;

import java.util.ArrayList;


public class FragmentsAll extends Fragment {
    View view;
    SharedPref sharedPref;
    ModelLogin modelLogin;
    private Context mContext;
    RecyclerView rvNotify;
    AdapterNotifications adapterNotifications;
    ArrayList<ModelNotify.Data> notifyList;
    ImageView noRecordFound;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_personal, container, false);
        mContext = getActivity();
        sharedPref = SharedPref.getInstance(mContext);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);

        init(view);

        return view;
    }


    private void init(View v) {
        rvNotify = (RecyclerView) v.findViewById(R.id.rvNotify);
        noRecordFound = v.findViewById(R.id.no_record_found);



        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_NOTIFICATION_KBC)
                .addBodyParameter(AppConsts.IS_ADMIN, modelLogin.getStudentData().getAdminId())
                .addBodyParameter(AppConsts.STUDENT_ID, "all")
                .addBodyParameter("uid", ""+modelLogin.getStudentData().getStudentId())
                .setTag(AppConsts.API_NOTIFICATION_MERGED)
                .build()
                .getAsObject(ModelNotify.class, new ParsedRequestListener<ModelNotify>() {
                    @Override
                    public void onResponse(ModelNotify response) {


                        notifyList = new ArrayList<>();

                        if (AppConsts.TRUE.equals(response.getStatus())) {
                            noRecordFound.setVisibility(View.GONE);
                            notifyList = response.getAllNotice();
                            adapterNotifications = new AdapterNotifications(mContext, notifyList, response.getBaseUrl());
                            rvNotify.setLayoutManager(new LinearLayoutManager(mContext));
                            rvNotify.setAdapter(adapterNotifications);
                        } else {
                            noRecordFound.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {

                        Toast.makeText(mContext, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
