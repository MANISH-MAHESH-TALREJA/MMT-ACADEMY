package net.manish.sem05.ui.multibatch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import net.manish.sem05.R;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.base.BaseActivity;
import net.manish.sem05.ui.batch.ModelBachDetails;
import net.manish.sem05.ui.home.ActivityHome;
import net.manish.sem05.ui.settingdashboard.ActivitySettingDashboard;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.ProjectUtils;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomSmallText;
import net.manish.sem05.utils.widgets.CustomTextExtraBold;
import net.manish.sem05.utils.widgets.CustomeTextRegularBold;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class ActivityMyBatch extends BaseActivity {
    RecyclerView rlBatchBuyed;
    Context context;
    ArrayList<ModelBachDetails.batchData> buyBatchList;
    ImageView ivBack, noResult;
    CustomTextExtraBold tvHeader;
    ModelLogin modelLogin;
    SharedPref sharedPref;


    AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = ActivityMyBatch.this;
        sharedPref = SharedPref.getInstance(context);
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        if (!ProjectUtils.checkConnection(context)) {
            Toast.makeText(context, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
        }
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
                    String languageToLoad = "en"; // you     gbr language
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                }

                if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("german")) {
                    String languageToLoad = "de"; // you     gbr language
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());
                }



            }
        }
       // setContentView(R.layout.activity_my_batch);
        setContentBaseView(R.layout.activity_my_batch);
        checkAppUpdate();
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            int verCode = pInfo.versionCode;
         //    checkVersion(verCode);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        initial();


    }

    private void checkVersion(int verCode) {
        AndroidNetworking.get(AppConsts.BASE_URL + AppConsts.API_WELCOME)
                .setTag(AppConsts.API_WELCOME)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject mainJson = new JSONObject(response);
                            if (mainJson.getString("status").equalsIgnoreCase("true")) {
                                if (verCode != Integer.parseInt(mainJson.getString("version").trim())) {
                                //    openAppUpdateDialog();
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

    private void openAppUpdateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setMessage(getResources().getString(R.string.Please_update_your_app_you_are_using_older_version))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final String appPackageName = getPackageName(); // package name of the app
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                        } catch (android.content.ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                        }
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();

    }


    void checkAppUpdate() {
        Log.v("update_pop","---0");
        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.registerListener(listener);
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            Log.v("update_pop","---1"+appUpdateInfo.packageName());
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                Log.v("update_pop","---2");
                requestUpdate(appUpdateInfo);
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                Log.v("update_pop","---3");
                resume();
            }else{
                Log.v("update_pop","---4--"+appUpdateInfo.availableVersionCode());
                Log.v("update_pop","---4-- updateAvailability "+appUpdateInfo.updateAvailability());
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE){
                    Log.v("update_pop","---4-- go "+appUpdateInfo.availableVersionCode());
                    requestUpdate(appUpdateInfo);

                }
            }
        });
    }

    private static final int MY_REQUEST_UPDATE_CODE = 17326;

    private void requestUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            Log.v("update_pop","update---------------------  "+appUpdateInfo.updatePriority());
            appUpdateManager.startUpdateFlowForResult(appUpdateInfo,  AppUpdateType.IMMEDIATE, ActivityMyBatch.this, MY_REQUEST_UPDATE_CODE);

        } catch (IntentSender.SendIntentException e) {
            Log.v("update_pop","update---------------------  "+e);
            e.printStackTrace();
        }
    }

    private void resume() {
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                Log.v("update_pop","saloni   --");
                notifyUser();
            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_UPDATE_CODE) {
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Update canceled by user!  " , Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_OK) {
                resume();
            } else {
                Toast.makeText(getApplicationContext(), "Update Failed!  " , Toast.LENGTH_LONG).show();
               // checkUpdate();
            }
        }
    }


    InstallStateUpdatedListener listener = installState -> {
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            notifyUser();
        }
    };

    private void notifyUser() {
        Snackbar snackbar =
                Snackbar.make(findViewById(R.id.swipeRefreshLayout),
                        "An update has just been downloaded.",
                        Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("RESTART", view -> appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snackbar.show();

    }

    private void initial() {
        rlBatchBuyed = findViewById(R.id.rlBatchBuyed);
        ivBack = (ImageView) findViewById(R.id.ivBack);
        noResult = (ImageView) findViewById(R.id.noResult);
        ivBack.setImageDrawable(getResources().getDrawable(R.drawable.menu));
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });

        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        tvHeader.setText(getResources().getString(R.string.my_batches));
        ProjectUtils.showProgressDialog(context, false, getResources().getString(R.string.Loading___));
        getBatches();


    }
    private void exitAppDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getResources().getString(R.string.Are_you_sure_you_want_to_close_this_app))
                .setCancelable(false)
                .setTitle(getResources().getString(R.string.app_name))
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


        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                if (modelLogin.getStudentData().getLanguageName().equalsIgnoreCase("arabic")) {
                    alertDialog.findViewById(android.R.id.message).setTextDirection(View.TEXT_DIRECTION_RTL);
                }

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            }
        });
        alertDialog.show();


    }

    @Override
    public void onBackPressed() {
       exitAppDialog();
    }

    void getBatches() {


        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_GET_BATCH_FEE)
                .addBodyParameter(AppConsts.STUDENT_ID, "" + modelLogin.getStudentData().getStudentId())
                .build(
                ).getAsObject(ModelBachDetails.class, new ParsedRequestListener<ModelBachDetails>() {
            @Override
            public void onResponse(ModelBachDetails response) {
                ProjectUtils.pauseProgressDialog();
                buyBatchList = new ArrayList<>();
                if (response.getStatus().equalsIgnoreCase("true")) {
                    if (response.getYourBatch() != null) {
                        buyBatchList = response.getYourBatch();
                        if(buyBatchList.size() > 0){
                        noResult.setVisibility(View.GONE);
                        }else{
                            noResult.setVisibility(View.VISIBLE);
                        }
                    } else {
                        noResult.setVisibility(View.VISIBLE);
                    }
                    rlBatchBuyed.setLayoutManager(new LinearLayoutManager(context));
                    AdapterCustom adapterCustom = new AdapterCustom(context, buyBatchList, "yourbatch");
                    rlBatchBuyed.setAdapter(adapterCustom);




                } else {
                    buyBatchList = new ArrayList<>();
                    noResult.setVisibility(View.VISIBLE);
                    rlBatchBuyed.setLayoutManager(new GridLayoutManager(context, 2));
                    AdapterCustom adapterCustom = new AdapterCustom(context, buyBatchList, "yourbatch");
                    rlBatchBuyed.setAdapter(adapterCustom);
                }
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(context, getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                ProjectUtils.pauseProgressDialog();

            }
        });


    }

    class AdapterCustom extends RecyclerView.Adapter<AdapterCustom.Myholder> {
        ArrayList<ModelBachDetails.batchData> listBatch;
        String tag = "";
        View view;

        AdapterCustom(Context context, ArrayList<ModelBachDetails.batchData> list, String tag) {
            this.listBatch = list;
            this.tag = tag;
        }

        @NonNull
        @Override
        public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            view = LayoutInflater.from(context).inflate(R.layout.multibatch_list, parent, false);
            return new Myholder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull Myholder holder, @SuppressLint("RecyclerView") int position) {



                holder.layoutBatch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ProjectUtils.checkConnection(context)) {
                            if(listBatch.get(position).getStatus().equalsIgnoreCase("1")){
                            changeBatch("" + listBatch.get(position).getId());}else{
                                Toast.makeText(context,"Your batch is inactive!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, getResources().getString(R.string.NoInternetConnection), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


                holder.batchName.setText("" + listBatch.get(position).getBatchName());

if(listBatch.get(position).getStatus().equalsIgnoreCase("1")){
 holder.inactive.setVisibility(View.GONE);
 holder.ivArrow.setVisibility(View.VISIBLE);

}else{

    holder.inactive.setVisibility(View.VISIBLE);
    holder.ivArrow.setVisibility(View.GONE);
}
        }

        @Override
        public int getItemCount() {
            return listBatch.size();
        }

        class Myholder extends RecyclerView.ViewHolder {
            CustomSmallText batchName;
            RelativeLayout layoutBatch;
            TextView inactive;
            ImageView ivArrow;

            public Myholder(@NonNull View itemView) {
                super(itemView);

                batchName = itemView.findViewById(R.id.batchName);
                layoutBatch = itemView.findViewById(R.id.layoutBatch);
                inactive = itemView.findViewById(R.id.inactive);
                ivArrow = itemView.findViewById(R.id.ivArrow);

            }
        }
    }

    void changeBatch(String s) {

        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_CHANGE_BATCH)
                .addBodyParameter(AppConsts.STUDENT_ID, "" + modelLogin.getStudentData().getStudentId())
                .addBodyParameter(AppConsts.BATCH_ID, "" + s)
                .build().getAsObject(ModelLogin.class, new ParsedRequestListener<ModelLogin>() {
            @Override
            public void onResponse(ModelLogin response) {
                if (AppConsts.TRUE.equals(response.getStatus())) {
                    sharedPref.setUser(AppConsts.STUDENT_DATA, response);
                    startActivity(new Intent(context, ActivityHome.class));

                } else {
                    Toast.makeText(context, "" + response.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {

                Toast.makeText(context, "" + context.getResources().getString(R.string.Try_again_server_issue), Toast.LENGTH_SHORT).show();

            }
        });


    }
}