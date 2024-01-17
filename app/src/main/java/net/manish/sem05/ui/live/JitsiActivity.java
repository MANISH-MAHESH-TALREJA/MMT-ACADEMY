package net.manish.sem05.ui.live;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.facebook.react.modules.core.PermissionListener;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.sharedpref.SharedPref;

import org.jitsi.meet.sdk.JitsiMeetActivityDelegate;
import org.jitsi.meet.sdk.JitsiMeetActivityInterface;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;
import org.jitsi.meet.sdk.JitsiMeetUserInfo;
import org.jitsi.meet.sdk.JitsiMeetView;
import org.jitsi.meet.sdk.JitsiMeetViewListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

public class JitsiActivity extends FragmentActivity implements JitsiMeetActivityInterface {
    private JitsiMeetView view;
    ModelLogin modelLogin;
    SharedPref sharedPref;
    Context mContext;
    String meetroom="";

    @Override
    protected void onActivityResult(
            int requestCode,
            int resultCode,
            Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        JitsiMeetActivityDelegate.onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        JitsiMeetActivityDelegate.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPref = SharedPref.getInstance(getApplicationContext());
        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        super.onCreate(savedInstanceState);
        mContext=JitsiActivity.this;
        Log.v("saloni123","-----    actciii");
        JitsiMeetUserInfo userInfo = new JitsiMeetUserInfo();
        view = new JitsiMeetView(this);
        userInfo.setDisplayName(""+modelLogin.getStudentData().getFullName());
        userInfo.setEmail(""+modelLogin.getStudentData().getUserEmail());
        try {
            userInfo.setAvatar(new URL(modelLogin.getStudentData().getImage()));
        } catch (MalformedURLException e) {
            Log.v("TESTT","sal"+e);
            e.printStackTrace();
        }
        meetroom=getIntent().getStringExtra("meetroom");
        //https://meet.jit.si/mfgbk896uhogmobmgpo5609
        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setRoom("https://meet.jit.si/"+meetroom)
                .setUserInfo(userInfo)
                .setFeatureFlag("invite.enabled", false)
                .setFeatureFlag("android.screensharing.enabled",true)

                //  .setFeatureFlag("pip.enabled", false)
                .build();
        view.join(options);

        view.setListener(new JitsiMeetViewListener() {
            @Override
            public void onConferenceJoined(Map<String, Object> map) {

            }

            @Override
            public void onConferenceTerminated(Map<String, Object> map) {
                finish();

            }

            @Override
            public void onConferenceWillJoin(Map<String, Object> map) {

            }

        });





        setContentView(view);

     /*   Intent intent = new Intent(mContext, JitsiMeetOngoingConferenceService.class);
        intent.setAction(JitsiMeetOngoingConferenceService.Action.START.getName());

        ComponentName componentName;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            componentName = mContext.startForegroundService(intent);
        } else {
            componentName = mContext.startService(intent);
        }
        if (componentName == null) {
            JitsiMeetLogger.w("class" + "Ongoing conference service not started");
        }*/

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        view.dispose();
        view = null;

        JitsiMeetActivityDelegate.onHostDestroy(this);
        finish();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        JitsiMeetActivityDelegate.onNewIntent(intent);
    }

    @Override
    public void onRequestPermissionsResult(
            final int requestCode,
            final String[] permissions,
            final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        JitsiMeetActivityDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();

        JitsiMeetActivityDelegate.onHostResume(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        JitsiMeetActivityDelegate.onHostPause(this);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void requestPermissions(String[] permissions, int requestCode, PermissionListener listener) {

        String[] PERMISSIONS = {
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.CAMERA
        };

        this.requestPermissions(permissions, requestCode);

        if (!hasPermissions(PERMISSIONS)) {
            Toast.makeText(mContext,"Need permissions",Toast.LENGTH_LONG).show();

        }

    }

    public boolean hasPermissions(String... permissions) {

        if (permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
