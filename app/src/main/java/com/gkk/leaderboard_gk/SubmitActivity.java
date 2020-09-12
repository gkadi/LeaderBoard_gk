package com.gkk.leaderboard_gk;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gkk.leaderboard_gk.models.Post;
import com.gkk.leaderboard_gk.utils.UtilPostingApi;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SubmitActivity extends AppCompatActivity {

    public static final String FORM_URL = "https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse";

    private Toolbar mToolbar;

    private Dialog mDialog;
    private Button mBtnSubmit;
    private Button mBtnConfirm;
    private ImageButton mBtnDismissConfirmDialog;
    private View mViewSubmissionData;
    private EditText mEdTFirstName;
    private EditText mEdTLasName;
    private EditText mEdTEmail;
    private EditText mEdTGithub;
    private UtilPostingApi mUtilRetrofit;

    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);

        mViewSubmissionData = findViewById(R.id.layout_submission_data);

        mEdTFirstName = findViewById(R.id.firstName);
        mEdTLasName = findViewById(R.id.lastName);
        mEdTEmail = findViewById(R.id.email);
        mEdTGithub = findViewById(R.id.githubLink);


        mDialog = new Dialog(this);
        mBtnSubmit = findViewById(R.id.buttonSubmit);
        mBtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showConfirmDialog();
            }
        });

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                Objects.requireNonNull(mViewSubmissionData).setAlpha(1);
            }
        });
        mDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Objects.requireNonNull(mViewSubmissionData).setAlpha(0);
            }
        });
        init();
    }

    private void init() {
        mEdTFirstName.setText("Gloire");
        mEdTLasName.setText("KADIMA");
        mEdTEmail.setText("gloirekadima@gmail.com");
        mEdTGithub.setText("https://github.com/gkadi/LeaderBoard_gk");

        // Initializing Queue for Volley
        queue = Volley.newRequestQueue(getApplicationContext());
    }

    private void showConfirmDialog() {
        mDialog.setContentView(R.layout.confirmation_dialog_layout);

        mBtnConfirm = mDialog.findViewById(R.id.btnConfirm);
        mBtnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        mBtnDismissConfirmDialog = mDialog.findViewById(R.id.btnClose);
        mBtnDismissConfirmDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        mDialog.show();
    }

    private void submit() {
        final Post post = new Post(mEdTFirstName.getText().toString(), mEdTLasName.getText().toString(),
                mEdTEmail.getText().toString(), mEdTGithub.getText().toString());

        StringRequest request = new StringRequest(
                Request.Method.POST,
                FORM_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("TAG", "Response: " + response);
                        if (response.length() > 0) {
                            successfulPost();
                        } else {
                            notSuccessfulPost();
                            Toast.makeText(SubmitActivity.this, "Failed, try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        notSuccessfulPost();
                        Toast.makeText(SubmitActivity.this, "Error while Posting Data", Toast.LENGTH_SHORT).show();
                    }
                 })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put(UtilPostingApi.email, post.getEmail());
                        params.put(UtilPostingApi.firstName, post.getFirstName());
                        params.put(UtilPostingApi.lastName, post.getLastName());
                        params.put(UtilPostingApi.link, post.getGithublink());

                        return params;
                    }
                };
        request.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(request);
    }

    private void successfulPost(){
        mDialog.setContentView(R.layout.succeful_dialog_layout);
        mDialog.show();
    }

    private void notSuccessfulPost(){
        mDialog.setContentView(R.layout.unsucceful_dialog_layout);
        mDialog.show();
    }
}