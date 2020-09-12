package com.gkk.leaderboard_gk.tabs;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gkk.leaderboard_gk.AbstractFragment;
import com.gkk.leaderboard_gk.R;
import com.gkk.leaderboard_gk.adapters.LearningLeadersListAdapter;
import com.gkk.leaderboard_gk.adapters.SkillIQLeadersListAdapter;
import com.gkk.leaderboard_gk.models.Learner;
import com.gkk.leaderboard_gk.utils.ApiUtil;

public class LearningLeadersFragment extends AbstractFragment{

    public LearningLeadersFragment() {
        super();
    }

    public LearningLeadersFragment(Context context, String title) {
        super(title);
        mListAdapter = new LearningLeadersListAdapter(context, mLearners);
        mComparator = new Learner.LearnersHoursComparator();
        leaders_path = ApiUtil.LEARNING_LEADERS_PATH;
    }
}