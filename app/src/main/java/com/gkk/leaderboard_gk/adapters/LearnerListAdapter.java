package com.gkk.leaderboard_gk.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gkk.leaderboard_gk.R;
import com.gkk.leaderboard_gk.models.Learner;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class LearnerListAdapter extends RecyclerView.Adapter<LearnerListAdapter.ViewHolder>{
    protected ArrayList<Learner> mLearners;
    protected Context mContext;

    public LearnerListAdapter(Context context, ArrayList<Learner> learners) {
        mContext = context;
        mLearners = learners;
    }

    public void setLearners(ArrayList<Learner> learners) {
        mLearners = learners;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_leader, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.showData(mLearners.get(position));
    }

    @Override
    public int getItemCount() {
        return mLearners.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        protected ImageView mIVIcon;
        protected TextView mTVName;
        protected TextView mTVCriteriaCountry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mIVIcon = itemView.findViewById(R.id.icon);
            mTVName = itemView.findViewById(R.id.tv_name);
            mTVCriteriaCountry = itemView.findViewById(R.id.tv_criteria_country);
        }

        public void showData(Learner learner){
            mTVName.setText(learner.getName());
        }
    }
}
