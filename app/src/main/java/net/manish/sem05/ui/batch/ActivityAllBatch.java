package com.pixelnx.eacademy.ui.batch;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.pixelnx.eacademy.R;
import com.pixelnx.eacademy.utils.AppConsts;
import com.pixelnx.eacademy.utils.ProjectUtils;
import com.pixelnx.eacademy.utils.sharedpref.SharedPref;
import com.pixelnx.eacademy.utils.widgets.CustomSmallText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityAllBatch extends AppCompatActivity implements  SwipeRefreshLayout.OnRefreshListener{
   ImageView backIv;
   RecyclerView recyclerView;
   Context context;
    SharedPref sharedPref;
    static String checkLanguage = "";
    CustomSmallText subCatName;
    int pageStart = 0, pageEnd = 6;
    String searchTag="";
    boolean isLoading = false;
    static String subcatId;
    ImageView noResultIv;
    EditText searchBarEditText;
    String stuId="";
    private SwipeRefreshLayout swipeRefreshLayout;
    ArrayList<ModelCatSubCat.batchData.SubCategory.BatchData> listBatch;
    AdapterAllBatch adapterCatSubCat;
    boolean isFirst=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        languageDynamic();
        setContentView(R.layout.activity_all_batch);
        context=ActivityAllBatch.this;
        sharedPref = SharedPref.getInstance(context);
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
    void init(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE, Color.GREEN);
        swipeRefreshLayout.setOnRefreshListener(this);



        searchBarEditText = findViewById(R.id.searchBarEditText);
        searchBarEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 2) {
                    searchTag =s.toString();
                    apiCall();
                }
                if(!isFirst){
                if(s.length() <= 0){
                    pageStart=0;
                    pageEnd=6;
                    searchTag="";
                    apiCall();

                }}else{

                    isFirst=false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        recyclerView=findViewById(R.id.recyclerView);
        subCatName=findViewById(R.id.subCatName);
        noResultIv=findViewById(R.id.noResultIv);
        if(getIntent().hasExtra("subcatname")){

        subCatName.setText(getIntent().getStringExtra("subcatname"));
            subcatId=getIntent().getStringExtra("subcatId");
            stuId=getIntent().getStringExtra("stuId");


        }
        backIv=findViewById(R.id.backIv);
        backIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
            }
        });
        ProjectUtils.showProgressDialog(context, false, getResources().getString(R.string.Loading___));
        apiCall();

    }

    void apiCall(){

        if(!searchTag.isEmpty()){
            pageStart=0;
            pageEnd=1000;
        }
     if(subcatId.equalsIgnoreCase("0")){
    subcatId="other";
       }

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_get_all_data)
                .addBodyParameter(AppConsts.START, "" + pageStart)
                .addBodyParameter(AppConsts.LENGTH, "" + pageEnd)
                .addBodyParameter(AppConsts.SEARCH, searchTag)
                .addBodyParameter("subcat", ""+subcatId)
                .addBodyParameter(AppConsts.STUDENT_ID, "" +stuId)
                .build().getAsObject(ModelCatSubCat.class, new ParsedRequestListener<ModelCatSubCat>() {
            @Override
            public void onResponse(ModelCatSubCat response) {

                swipeRefreshLayout.setRefreshing(false);
                ProjectUtils.pauseProgressDialog();
try{
                if (response.getStatus().equalsIgnoreCase("true")) {

                    noResultIv.setVisibility(View.GONE);



                 for(int i=0;i< response.getBatchData().size();i++){
                    if(response.getBatchData().get(i).getSubcategory().size() > 0){

                        if(pageStart==0){
                            isLoading=false;
                            listBatch=new ArrayList<>();
                            listBatch=response.getBatchData().get(i).getSubcategory().get(0).getBatchData();
                            initAdapter();
                            initScrollListener();
                        }else{
                            isLoading=false;
                            listBatch.addAll(response.getBatchData().get(i).getSubcategory().get(0).getBatchData());
                            if(response.getBatchData().get(i).getSubcategory().get(0).getBatchData().size() < 1)
                            {
                                Toast.makeText(context, getResources().getString(R.string.NoMoreCoursesfound), Toast.LENGTH_SHORT).show();

                            }
                            adapterCatSubCat.notifyDataSetChanged();

                        }


                    }
    }

    if(listBatch.size() < 1){
        recyclerView.setVisibility(View.GONE);
        noResultIv.setVisibility(View.VISIBLE);
      //  searchBarEditText.clearFocus();
       //hideKeyboard(ActivityAllBatch.this);
    }
                } else {
                    recyclerView.setVisibility(View.GONE);
                    noResultIv.setVisibility(View.VISIBLE);
                }
}catch (Exception e){

}
            }

            @Override
            public void onError(ANError anError) {
                ProjectUtils.pauseProgressDialog();
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(context, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();

            }
        });




    }


    private void initAdapter() {

        recyclerView.setLayoutManager(new GridLayoutManager(context,2));
        adapterCatSubCat = new AdapterAllBatch(listBatch, getApplicationContext(),""+stuId);
        recyclerView.setAdapter(adapterCatSubCat);

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
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == listBatch.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });


    }

    private void loadMore() {
        listBatch.add(null);
        adapterCatSubCat.notifyItemInserted(listBatch.size() - 1);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                listBatch.remove(listBatch.size() - 1);
                int scrollPosition = listBatch.size();
                adapterCatSubCat.notifyItemRemoved(scrollPosition);
                int currentSize = scrollPosition;
                int nextLimit = currentSize + 4;
                pageStart = currentSize;
                pageEnd = nextLimit;
                apiCall();
                while (currentSize - 1 < nextLimit) {
                    //
                    currentSize++;
                }



            }
        }, 2000);


    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onRefresh() {
        if (ProjectUtils.checkConnection(context)) {
            pageStart=0;
            pageEnd=6;
            searchTag="";
            apiCall();
        } else {
            Toast.makeText(context, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
    }
}