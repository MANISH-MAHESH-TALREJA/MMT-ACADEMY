package com.pixelnx.eacademy.ui.batch;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.model.ModelSettingRecord;
import com.pixelnx.eacademy.ui.login.ActivityLogin;
import com.pixelnx.eacademy.ui.signup.ActivitySignUp;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomSmallText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivityBatch extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener{
    RecyclerView recyclerView;
    Context context;
    static String checkLanguage = "";
    ImageView noResultIv;
    CustomSmallText refreshTextView;
    SharedPref sharedPref;
    LinearLayout btnSignup, loginTv;
    ArrayList<ModelCatSubCat.batchData> catSubList = new ArrayList<>();
    EditText searchBarEditText;
    boolean isLoading = false;
    AdapterCatPage adapterCat;
    int pageStart = 0, pageEnd = 4;
    String searchTag="";
    SwipeRefreshLayout swipeRefreshLayout;
    boolean onRefreshCall=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageDynamic();
        setContentView(R.layout.activitybatch);

        context = ActivityBatch.this;
        sharedPref = SharedPref.getInstance(context);
        if (!ProjectUtils.checkConnection(context)) {
            Toast.makeText(context, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, getResources().getString(R.string.Please_allow_permissions), Toast.LENGTH_SHORT).show();
            requestPermission();
        }

        siteSettings();
        init();
    }

    void languageDynamic() {


        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_CHECKLANGUAGE)
                .build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ("true".equalsIgnoreCase(jsonObject.getString("status"))) {
                        if (jsonObject.getString("languageName").equalsIgnoreCase("arabic")) {
                            //for rtl
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

                            if (!checkLanguage.equals("ar")) {
                                recreate();
                            }
                            checkLanguage = "ar";

                        }
                        if (jsonObject.getString("languageName").equalsIgnoreCase("french")) {
                            String languageToLoad = "fr"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("fr")) {
                                recreate();
                            }
                            checkLanguage = "fr";

                        }
                        if (jsonObject.getString("languageName").equalsIgnoreCase("english")) {
                            String languageToLoad = "en"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("en")) {
                                recreate();
                            }
                            checkLanguage = "en";


                        }
                        if (jsonObject.getString("languageName").equalsIgnoreCase("hindi")) {
                            String languageToLoad = "hi"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("hi")) {
                                recreate();
                            }
                            checkLanguage = "hi";


                        }

                        if (jsonObject.getString("languageName").equalsIgnoreCase("german")) {
                            String languageToLoad = "de"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("de")) {
                                recreate();
                            }
                            checkLanguage = "de";


                        }

                        if (jsonObject.getString("languageName").equalsIgnoreCase("spanish")) {
                            String languageToLoad = "es"; // your language
                            Locale locale = new Locale(languageToLoad);
                            Locale.setDefault(locale);
                            Configuration config = new Configuration();
                            config.locale = locale;
                            getBaseContext().getResources().updateConfiguration(config,
                                    getBaseContext().getResources().getDisplayMetrics());
                            if (!checkLanguage.equals("es")) {
                                recreate();
                            }
                            checkLanguage = "es";


                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(ANError anError) {

            }
        });


    }

    private void requestPermission() {

        Dexter.withActivity((Activity) context)
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                        ,Manifest.permission.BLUETOOTH
                    )
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }

                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(context, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

    }

    void getBatchDetails() {
        if(!searchTag.isEmpty()){
            pageStart=0;
            pageEnd=1000;
        }


        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_GET_BATCH_FEE)
                .addBodyParameter(AppConsts.START, "" + pageStart)
                .addBodyParameter(AppConsts.LENGTH, "" + pageEnd)
                .addBodyParameter(AppConsts.LIMIT, "3")
                .addBodyParameter(AppConsts.SEARCH, searchTag)
                .build().getAsObject(ModelCatSubCat.class, new ParsedRequestListener<ModelCatSubCat>() {
            @Override
            public void onResponse(ModelCatSubCat response) {

                onRefreshCall=false;
                ProjectUtils.pauseProgressDialog();
                swipeRefreshLayout.setRefreshing(false);
                refreshTextView.setVisibility(View.GONE);
                if (response.getStatus().equalsIgnoreCase("true")) {
                    noResultIv.setVisibility(View.GONE);


                    if (pageStart == 0) {
                        catSubList = response.batchData;
                        if(catSubList.size() < 1){
                            noResultIv.setVisibility(View.VISIBLE);
                        }
                        initAdapter();
                        initScrollListener();
                    } else {
                        if(searchTag.isEmpty()){
                        if(response.batchData.size() < 1){
                            Toast.makeText(context, context.getResources().getString(R.string.NoMoreCoursesfound), Toast.LENGTH_SHORT).show();
                        }
                        catSubList.addAll(response.batchData);
                        adapterCat.notifyDataSetChanged();
                        isLoading = false;
                        }else{
                            catSubList = response.batchData;
                            initAdapter();
                            initScrollListener();
                        }
                    }

                } else {


                    noResultIv.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onError(ANError anError) {
                onRefreshCall=false;
                ProjectUtils.pauseProgressDialog();
                refreshTextView.setVisibility(View.VISIBLE);


            }
        });


    }

    void siteSettings() {
        AndroidNetworking.get(AppConsts.BASE_URL + AppConsts.API_HOMEGENERAL_SETTING).build()
                .getAsObject(ModelSettingRecord.class, new ParsedRequestListener<ModelSettingRecord>() {
                    @Override
                    public void onResponse(ModelSettingRecord response) {
                        if (response.getStatus().equalsIgnoreCase("true")) {
                            sharedPref.setSettingInfo(AppConsts.APP_INFO, response);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {


                    }
                });
    }

    @Override
    protected void onPause() {
        ProjectUtils.pauseProgressDialog();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        exitAppDialog();
    }

    private void exitAppDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getResources().getString(R.string.Are_you_sure_you_want_to_close_this_app))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
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

    private void openSettingsDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("" + getResources().getString(R.string.Please_allow_permissions));
        builder.setMessage(getResources().getString(R.string.This_app_needs_permission));
        builder.setPositiveButton(getResources().getString(R.string.GOTO_SETTINGS), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    void init() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
       // swipeRefreshLayout.setRefreshing(true);



        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = findViewById(R.id.recyclerView);
        if(pageStart == 0) {
         ProjectUtils.showProgressDialog(context, false, getResources().getString(R.string.Loading___));
            Log.v("saloni123","normal "+pageStart);
            getBatchDetails();
        }


        searchBarEditText = findViewById(R.id.searchBarEditText);
        searchBarEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.length() > 2) {
                    searchTag =s.toString();
                    catSubList=new ArrayList<>();
                    getBatchDetails();
                }
                if(s.length() <= 0){
                    pageStart=0;
                    pageEnd=4;
                    searchTag="";
                    isLoading = false;
                    getBatchDetails();

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSignup = findViewById(R.id.btnSignup);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ActivitySignUp.class).putExtra("login", "Withoutbatch"));
            }
        });
        noResultIv = findViewById(R.id.noResultIv);
        //  recyclerView.addItemDecoration(new CirclePagerIndicatorDecoration());
        //  SnapHelper helper = new PagerSnapHelper();
        //  helper.attachToRecyclerView(recyclerView);
        loginTv = findViewById(R.id.loginTv);
        refreshTextView = findViewById(R.id.refreshTextView);
        refreshTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshTextView.setVisibility(View.GONE);
                getBatchDetails();
            }
        });

        loginTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, ActivityLogin.class));
            }
        });


    }


    private void initAdapter() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapterCat = new AdapterCatPage(catSubList, getApplicationContext(),"",checkLanguage);
        recyclerView.setAdapter(adapterCat);

    }

    private void initScrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == catSubList.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        catSubList.add(null);
       adapterCat.notifyItemInserted(catSubList.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(catSubList.size() > 0){
                catSubList.remove(catSubList.size() - 1);}
                int scrollPosition = catSubList.size();
                adapterCat.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = pageEnd + 4;
                pageStart = pageEnd;
                pageEnd = nextLimit;
                getBatchDetails();
                while (currentSize - 1 < nextLimit) {
                    //
                    currentSize++;
                }
              /*  catSubList.remove(catSubList.size() - 1);
                int scrollPosition = catSubList.size();
                adapterCat.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 3;
                pageStart = currentSize;
                pageEnd = nextLimit;
                getBatchDetails();
                while (currentSize - 1 < nextLimit) {
                    //
                    currentSize++;
                }*/



            }
        }, 2000);


    }


    @Override
    public void onRefresh() {
Log.v("saloni123","refresh "+pageStart);
        swipeRefreshLayout.setRefreshing(true);
        if (ProjectUtils.checkConnection(context)) {
            pageStart=0;
            pageEnd=4;
            searchTag="";

            if(!onRefreshCall){
                onRefreshCall=true;
            getBatchDetails();}
        } else {
            Toast.makeText(context, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
    }
}