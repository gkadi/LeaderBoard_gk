package com.gkk.leaderboard_gk;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gkk.leaderboard_gk.adapters.LearnerListAdapter;
import com.gkk.leaderboard_gk.adapters.SkillIQLeadersListAdapter;
import com.gkk.leaderboard_gk.models.Learner;
import com.gkk.leaderboard_gk.utils.ApiUtil;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public abstract class AbstractFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener {
    protected String leaders_path;
    protected String title;
    protected Comparator<Learner> mComparator;

    protected ProgressBar mProgressBar;
    protected RecyclerView mRecyclerView;
    protected SwipeRefreshLayout mSwipeRefreshLayout;

    protected LearnerListAdapter mListAdapter;
    protected ArrayList<Learner> mLearners = new ArrayList<>();

    public AbstractFragment() {
    }

    public AbstractFragment(String title) {
        this.title = title;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leaders, container, false);

        mProgressBar = view.findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);

        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = view.findViewById(R.id.learners_list);
        LinearLayoutManager lm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(lm);

        mRecyclerView.setAdapter(mListAdapter);

        loadData();

        return view;
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @SuppressLint("StaticFieldLeak")
    public void loadData() {
        if(!internetAvailable(getContext())) {
            Toast.makeText(getContext(), "Internet connection is required", Toast.LENGTH_SHORT).show();
            return;
        }
        LearnersQueryTask task = new LearnersQueryTask();
        task.execute(ApiUtil.buildURL(leaders_path));
    }
    public static boolean internetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo!=null && networkInfo.isConnected();
    }

    public class LearnersQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String result = null;
            try {
                result = ApiUtil.getJSon(urls[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mProgressBar.setVisibility(View.INVISIBLE);
            mSwipeRefreshLayout.setRefreshing(false);

            if (s!=null){
                mLearners = ApiUtil.getLearnersFromJson(s);
                Collections.sort(mLearners, mComparator);

                mListAdapter.setLearners(mLearners);
            }else{
                Toast.makeText(getContext(), "No result found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(!mSwipeRefreshLayout.isRefreshing())
                mProgressBar.setVisibility(View.VISIBLE);
        }
    }
}
