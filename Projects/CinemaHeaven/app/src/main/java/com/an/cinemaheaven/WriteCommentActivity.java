package com.an.cinemaheaven;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.an.cinemaheaven.Api.Api;
import com.an.cinemaheaven.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

public class WriteCommentActivity extends AppCompatActivity {
    TextView tv_title;
    TextView saveButton, cancelButton;

    ImageView iv_grade;

    EditText et_rating;
    EditText et_comment;

    RatingBar ratingBar;

    int id;
    int imageRes;

    String url = Api.createCommentUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_comment);

        Intent intent = getIntent();
        id  = intent.getExtras().getInt("id");
        String title = intent.getExtras().getString("title").toString();
        int grade = intent.getExtras().getInt("grade");

        iv_grade = (ImageView) findViewById(R.id.iv_grade);
        tv_title = (TextView) findViewById(R.id.tv_movieTitle_in_writeCommentActivity);
        et_rating = (EditText) findViewById(R.id.et_rating);
        et_comment = (EditText) findViewById(R.id.et_comment);
        ratingBar = (RatingBar) findViewById(R.id.rbar_in_write_comment) ;
        saveButton = (TextView) findViewById(R.id.tv_save_button);
        cancelButton = (TextView) findViewById(R.id.tv_cancel_button);

        tv_title.setText(title);
        setGradeImage(grade);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contents = et_comment.getText().toString();
                float rating = ratingBar.getRating();

                sendRequest(contents, rating);
                finish();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void sendRequest(String contents, float rating){
        url += ("id=" + id + "&writer="+ Api.writer+"&rating="+rating+"&contents="+contents);
        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
    }

    public void setGradeImage(int g){
        switch(g){
            case 12:
                imageRes = R.drawable.ic_12;
                break;
            case 15:
                imageRes = R.drawable.ic_15;
                break;
            case 19:
                imageRes = R.drawable.ic_19;
                break;
            case 0:
                imageRes = R.drawable.ic_all;
                break;
        }

        iv_grade.setImageResource(imageRes);
    }

}
