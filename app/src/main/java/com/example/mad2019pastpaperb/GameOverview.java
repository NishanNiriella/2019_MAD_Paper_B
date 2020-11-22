package com.example.mad2019pastpaperb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mad2019pastpaperb.Database.DBHandler;

import java.util.ArrayList;

public class GameOverview extends AppCompatActivity {
    private TextView gameName, totalRating;
    private Button btnComment;
    private ListView commentList;
    private EditText comment;
    private ArrayAdapter commentsAdapter;
    private ArrayList arrComments;
    private DBHandler dbHandler;
    private SeekBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_overview);

        String name = getIntent().getStringExtra("GAME_NAME");

        dbHandler = new DBHandler(getApplicationContext());
        gameName = findViewById(R.id.tvgameName);
        comment = findViewById(R.id.etComment);
        totalRating = findViewById(R.id.totRating);
        btnComment = findViewById(R.id.btnSubmit);
        ratingBar = findViewById(R.id.seekBar2);
        commentList = findViewById(R.id.commentList);

        gameName.setText(name);

        double totalRate = dbHandler.getTotalRating(name);

        totalRating.setText(String.valueOf(totalRate));

        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ratingGiven = ratingBar.getProgress();
                long status = dbHandler.insertComments(comment.getText().toString(), gameName.getText().toString(), ratingGiven);

                if(status > 0){
                    Toast.makeText(GameOverview.this, "Comment Added!", Toast.LENGTH_SHORT).show();
                    comment.setText(null);
                } else {
                    Toast.makeText(GameOverview.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        arrComments = dbHandler.viewComments(name);
        commentsAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrComments);
        commentList.setAdapter(commentsAdapter);

    }
}