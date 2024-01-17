package net.manish.sem05.ui.noticeAnnouncement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import net.manish.sem05.R;
import net.manish.sem05.model.modellogin.ModelLogin;
import net.manish.sem05.ui.home.ActivityHome;
import net.manish.sem05.utils.AppConsts;
import net.manish.sem05.utils.sharedpref.SharedPref;
import net.manish.sem05.utils.widgets.CustomTextExtraBold;

public class ActivityForFragments extends AppCompatActivity {

    ViewPager simpleViewPager;
    TabLayout tabLayout;
    ModelLogin modelLogin;
    SharedPref sharedPref;
    String pCount = "";
    String aCount = "";
    String kCount = "";
    CustomTextExtraBold tvHeader;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_for_notify_fragments);
        simpleViewPager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.simpleTabLayout);
        ivBack = findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sharedPref = SharedPref.getInstance(ActivityForFragments.this);

        modelLogin = sharedPref.getUser(AppConsts.STUDENT_DATA);
        tvHeader = (CustomTextExtraBold) findViewById(R.id.tvHeader);
        tvHeader.setText(""+getResources().getString(R.string.Announcements));


        if (getIntent().hasExtra("p_count")) {
            pCount = getIntent().getStringExtra("p_count");
            aCount = getIntent().getStringExtra("a_count");
            kCount = getIntent().getStringExtra("k_count");

        }

        simpleViewPager.setOffscreenPageLimit(5);

        TabLayout.Tab firstTab = tabLayout.newTab();
        firstTab.setText(getResources().getString(R.string.Personal_Notice));

        tabLayout.addTab(firstTab);


        TabLayout.Tab secondTab = tabLayout.newTab();
        secondTab.setText(getResources().getString(R.string.Common_Notice));

        tabLayout.addTab(secondTab); // add  the tab  in the TabLayout


        PagerAdopter adapter = new PagerAdopter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        simpleViewPager.setAdapter(adapter);

        simpleViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                simpleViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        if (getIntent().hasExtra("notice")) {
            if (getIntent().getStringExtra("notice").contains("notice_common")) {
                simpleViewPager.setCurrentItem(1);
            } else {
                simpleViewPager.setCurrentItem(0);
            }
        }
    }

    @Override
    public void onBackPressed() {


        startActivity(new Intent(ActivityForFragments.this, ActivityHome.class));
        finish();

    }
}
//saloni shrivastava