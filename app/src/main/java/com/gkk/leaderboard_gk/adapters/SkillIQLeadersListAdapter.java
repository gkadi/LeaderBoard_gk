package com.gkk.leaderboard_gk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gkk.leaderboard_gk.R;
import com.gkk.leaderboard_gk.models.Learner;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class SkillIQLeadersListAdapter extends LearnerListAdapter{
    public SkillIQLeadersListAdapter(Context context, ArrayList<Learner> learners) {
        super(context, learners);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_leader, parent, false);

        return new ViewHolder(v);
    }

    public class ViewHolder extends LearnerListAdapter.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void showData(Learner learner) {
            super.showData(learner);
            mTVCriteriaCountry.setText(learner.getScore()+" skill IQ Score, "+learner.getCountry());
        }
    }
}
