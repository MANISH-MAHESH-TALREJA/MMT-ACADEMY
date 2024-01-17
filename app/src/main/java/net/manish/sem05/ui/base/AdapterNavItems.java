package net.manish.sem05.ui.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.StringRequestListener;
import net.manish.sem05.R;
import net.manish.sem05.ui.aboutapp.ActivityAboutApp;
import net.manish.sem05.ui.aboutapp.ActivityOpenSourceLibrary;
import net.manish.sem05.ui.applyleave.ActivityApplyLeave;
import net.manish.sem05.ui.certificate.ActivityCertificateAssigned;
import net.manish.sem05.ui.editProfile.ActivityProfile;
import net.manish.sem05.ui.home.ActivityPrivacyPolicy;
import net.manish.sem05.ui.login.ActivityLogin;
import net.manish.sem05.ui.multibatch.ActivityMultiBatchHome;
import net.manish.sem05.ui.multibatch.ActivityMyBatch;
import net.manish.sem05.ui.payment.ActivityPaymentHistory;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomTextSemiBold;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class AdapterNavItems extends BaseAdapter {

    Context mContext;
    List<String> navList;
   SharedPref sharedPref;
    String id="";


    public AdapterNavItems(Context mContext, List<String> navList, String id) {
        this.mContext = mContext;
        this.navList = navList;
        this.id=id;

    }

    @Override
    public int getCount() {
        return navList.size();
    }

    @Override
    public Object getItem(int position) {
        return navList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        sharedPref = SharedPref.getInstance(mContext);
        LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = vi.inflate(R.layout.nav_items, parent, false);




        final CustomTextSemiBold title = (CustomTextSemiBold) convertView.findViewById(R.id.title);

        ImageView ivSide=convertView.findViewById(R.id.ivSide);
        ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.download));
        title.setText(navList.get(position));


        if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.home))){
            ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.homeicons));
        }
        if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.my_batches))){
            ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.batchesmy));
        }
          if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Edit_Profile))){
              ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.editprofile));
             }
        if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Download))){
            ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.editprofile));
        }
        if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Payment_History))){
            ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.payment));
        }
        if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Apply_Leave))){
            ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.leave));
        }
        if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Download_Certificate))){
            ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.download));
        }
        if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Logout))){
            ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.editprofile));
        }
        if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Privacy_Policy))){
            ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.privacypoli));
        }
        if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.About_App))){
            ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.aboutapp));
        }
        if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Open_Source_Library))){
            ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.opensource));
        }
        if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Logout))){
            ivSide.setImageDrawable(mContext.getResources().getDrawable(R.drawable.logout));
        }

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Edit_Profile))){
                    Intent intent = new Intent(mContext, ActivityProfile.class);
                    mContext.startActivity(intent);
                }

                if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Payment_History))){
                    mContext.startActivity(new Intent(mContext, ActivityPaymentHistory.class));
                }
                if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Apply_Leave))){
                    Intent intentApplyLeave = new Intent(mContext, ActivityApplyLeave.class);
                    mContext.startActivity(intentApplyLeave);
                }
                if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Download_Certificate))){
                    Intent intentCertificate = new Intent(mContext, ActivityCertificateAssigned.class);
                    mContext.startActivity(intentCertificate);
                }

                if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Privacy_Policy))){
                    Intent intentP = new Intent(mContext, ActivityPrivacyPolicy.class);
                    intentP.putExtra("WEB_URL", AppConsts.URL_PRIVACY_POLICY);
                    intentP.putExtra("HEADER", "Privacy Policy");
                    mContext.startActivity(intentP);
                }
                if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.About_App))){
                    Intent intentAboutApp = new Intent(mContext, ActivityAboutApp.class);
                    mContext.startActivity(intentAboutApp);
                }
                if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Open_Source_Library))){
                    Intent intentOpenSourceLibrary = new Intent(mContext, ActivityOpenSourceLibrary.class);
                    mContext.startActivity(intentOpenSourceLibrary);
                }
                if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.home))){
                    Intent intentHome = new Intent(mContext, ActivityMultiBatchHome.class);
                    mContext.startActivity(intentHome);
                }
                if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.my_batches))){
                    Intent intentMyBatch = new Intent(mContext, ActivityMyBatch.class);
                    mContext.startActivity(intentMyBatch);
                }
                if(navList.get(position).equalsIgnoreCase( mContext.getResources().getString(R.string.Logout))){
                    logoutAppDialog();
                }


            }
        });





        return convertView;
    }

    private void logoutAppDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(mContext.getResources().getString(R.string.Are_you_sure_you_want_to_logout))
                .setCancelable(false)

                .setTitle(mContext.getResources().getString(R.string.app_name))
                .setPositiveButton(mContext.getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutApi();
                    }
                })
                .setNegativeButton(mContext.getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });


        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));

            }
        });
        alertDialog.show();
    }

    private void logoutApi() {
        AndroidNetworking.post(AppConsts.BASE_URL + AppConsts.API_LOGOUT)
                .addBodyParameter(AppConsts.STUDENT_ID, id)
                .setTag(AppConsts.API_LOGOUT)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject mainJson = new JSONObject(response);
                            if (AppConsts.TRUE.equals(mainJson.getString(AppConsts.STATUS))) {
                                sharedPref.clearAllPreferences();
                                Intent loginScreen = new Intent(mContext, ActivityLogin.class);
                                loginScreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(loginScreen);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, mContext.getResources().getString(R.string.Something_went_wrong), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(mContext, mContext.getResources().getString(R.string.Try_again), Toast.LENGTH_SHORT).show();
                    }

                });
    }



}
