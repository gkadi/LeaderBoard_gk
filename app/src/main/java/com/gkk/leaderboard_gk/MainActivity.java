package com.gkk.leaderboard_gk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gkk.leaderboard_gk.adapters.ViewPagerAdapter;
import com.gkk.leaderboard_gk.tabs.IQLeadersFragment;
import com.gkk.leaderboard_gk.tabs.LearningLeadersFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements ConnectivityManager.OnNetworkActiveListener {
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    private Button mButtonSubmit;

    private LearningLeadersFragment mLearningLeadersFragment;
    private IQLeadersFragment mSkillIQLeadersFragment;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.viewpager);
        mButtonSubmit = findViewById(R.id.btnSubmit);
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SubmitActivity.class);
                startActivity(intent);
            }
        });

        initViewPager();

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        Objects.requireNonNull(connectivityManager).addDefaultNetworkActiveListener(this);
    }

    private void initViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        mLearningLeadersFragment = new LearningLeadersFragment(this, getString(R.string.learning_leaders_fragment));
        mSkillIQLeadersFragment = new IQLeadersFragment(this, getString(R.string.iq_leaders_fragment));

        fragments.add(mLearningLeadersFragment);
        fragments.add(mSkillIQLeadersFragment);
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onNetworkActive() {
        Toast.makeText(this, "Active", Toast.LENGTH_SHORT).show();
    }
}