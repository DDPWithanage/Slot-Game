package com.example.slotgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {
    EditText username, password;
    Button loginBtn;
    Database DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.userName1);
        password = findViewById(R.id.password1);
        loginBtn = findViewById(R.id.btnSignIn1);
        DB = new Database(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("") || pass.equals(""))
                    Toast.makeText(login.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    Boolean check = DB.CheckUsernamePassword(user, pass);
                    if(check == true) {
                        Toast.makeText(login.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), FrontPage.class);
                        startActivity(intent);

                       //Pass data to the front page
                        Intent newIntent = new Intent(getBaseContext(), FrontPage.class);
                        newIntent.putExtra("value", user);
                        //newIntent.putExtra("value", pass);
                        startActivity(newIntent);

                    }else{
                        Toast.makeText(login.this, "Invalid username or password", Toast.LENGTH_SHORT).show();

                    }
                    }
                }

        });


    }

    public void back(View view) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }
}