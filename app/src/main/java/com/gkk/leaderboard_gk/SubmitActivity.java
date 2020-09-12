package com.gkk.leaderboard_gk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gkk.leaderboard_gk.models.Post;
import com.gkk.leaderboard_gk.utils.UtilRetrofit;

import java.util.List;
import java.util.Objects;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SubmitActivity extends AppCompatActivity {

    public static final String BASE_URL = "https://docs.google.com/forms/d/e/1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse/";
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
    private UtilRetrofit mUtilRetrofit;


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
        mEdTGithub.setText("https://ThisIsAtest.com/");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mUtilRetrofit = retrofit.create(UtilRetrofit.class);
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
        Post post = new Post(mEdTFirstName.getText().toString(), mEdTLasName.getText().toString(),
                mEdTEmail.getText().toString(), mEdTGithub.getText().toString());

        Call<Post> call = mUtilRetrofit.createPost(post.getEmail(), post.getFirstName(), post.getLastName(), post.getGithublink());
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(SubmitActivity.this, response.errorBody().toString(), Toast.LENGTH_LONG).show();
                    notSuccessfulPost();
                    return;
                }

                Post postresponse = response.body();
                Toast.makeText(SubmitActivity.this, postresponse.getEmail(), Toast.LENGTH_SHORT).show();

                successfulPost();
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                notSuccessfulPost();
                Toast.makeText(SubmitActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
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