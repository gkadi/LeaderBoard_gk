package com.gkk.leaderboard_gk.tabs;

import android.content.Context;
import android.os.Bundle;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.gkk.leaderboard_gk.AbstractFragment;
import com.gkk.leaderboard_gk.adapters.SkillIQLeadersListAdapter;
import com.gkk.leaderboard_gk.models.Learner;
import com.gkk.leaderboard_gk.utils.ApiUtil;

public class IQLeadersFragment extends AbstractFragment {

    public IQLeadersFragment() {
        super();
    }

    public IQLeadersFragment(Context context, String title) {
        super(title);
        mListAdapter = new SkillIQLeadersListAdapter(context, mLearners);
        mComparator = new Learner.LearnersScoreComparator();
        leaders_path = ApiUtil.SKILL_IQ_LEADERS_PATH;
    }
}