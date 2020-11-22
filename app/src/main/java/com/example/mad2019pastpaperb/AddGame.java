package com.example.mad2019pastpaperb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mad2019pastpaperb.Database.DBHandler;

public class AddGame extends AppCompatActivity {
    private EditText etGame, etYear;
    private Button btnAdd;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        dbHandler = new DBHandler(getApplicationContext());
        etGame = findViewById(R.id.etGameName);
        etYear = findViewById(R.id.etGameYear);
        btnAdd = findViewById(R.id.btnAddGame);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean status = dbHandler.addGame(etGame.getText().toString(), Integer.parseInt(etYear.getText().toString()));
                
                if(status){
                    Toast.makeText(AddGame.this, "Game Added!", Toast.LENGTH_SHORT).show();
                    etGame.setText(null);
                    etYear.setText(null);
                } else {
                    Toast.makeText(AddGame.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}