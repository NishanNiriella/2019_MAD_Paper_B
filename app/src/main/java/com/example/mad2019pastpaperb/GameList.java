package com.example.mad2019pastpaperb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mad2019pastpaperb.Database.DBHandler;

import java.util.ArrayList;

public class GameList extends AppCompatActivity {
    private ListView gameList;
    private ArrayList arrGameList;
    private ArrayAdapter gamesAdapter;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        gameList = findViewById(R.id.gameList);
        dbHandler = new DBHandler(getApplicationContext());

        arrGameList = dbHandler.viewGames();
        gamesAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrGameList);
        gameList.setAdapter(gamesAdapter);

        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected = gameList.getItemAtPosition(position).toString();
                Intent intent = new Intent(GameList.this, GameOverview.class);
                intent.putExtra("GAME_NAME", selected);
                startActivity(intent);
            }
        });

    }
}