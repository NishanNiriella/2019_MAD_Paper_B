package com.example.mad2019pastpaperb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mad2019pastpaperb.Database.DBHandler;

public class Main extends AppCompatActivity {
    private EditText userName, password;
    private Button btnLogin, btnRegister;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(getApplicationContext());

        userName = findViewById(R.id.etUserName);
        password = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegis);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                long status = dbHandler.registerUser(userName.getText().toString(), password.getText().toString());

                if(status > 0){
                    Toast.makeText(Main.this, "User Registered", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Main.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = dbHandler.userLogin(userName.getText().toString(), password.getText().toString());

                if(status == 1){
                    Intent intent = new Intent(Main.this, AddGame.class);
                    startActivity(intent);
                } else if(status == -1){
                    Intent intent = new Intent(Main.this, GameList.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Main.this, "Login Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}