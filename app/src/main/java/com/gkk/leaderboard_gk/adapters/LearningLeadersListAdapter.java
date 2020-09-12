package com.gkk.leaderboard_gk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gkk.leaderboard_gk.R;
import com.gkk.leaderboard_gk.models.Learner;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LearningLeadersListAdapter extends LearnerListAdapter{
    public LearningLeadersListAdapter(Context context, ArrayList<Learner> learners) {
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
            Glide.with(mContext)
                    .load(mContext.getDrawable(R.drawable.top_learner))
                    .into(mIVIcon);
        }

        @Override
        public void showData(Learner learner) {
            super.showData(learner);
            mTVCriteriaCountry.setText(learner.getHours()+" learning hours, "+learner.getCountry());
        }
    }
}
