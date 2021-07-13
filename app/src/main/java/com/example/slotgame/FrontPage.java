package com.example.slotgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class FrontPage extends AppCompatActivity implements Event {
    ImageView btn_up, btn_down;
    CasinoGame image1, image2, image3;
    TextView text_score;
    int count_done = 0;
    Database DB;
    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        btn_down = findViewById(R.id.btn_down);
        btn_up = findViewById(R.id.btn_up);

        image1 = (CasinoGame)findViewById(R.id.image1);
        image2 = (CasinoGame)findViewById(R.id.image2);
        image3 = (CasinoGame)findViewById(R.id.image3);

        text_score = findViewById(R.id.text_score);
        DB = new Database(this);

        //Intent intent = getIntent();
        value = getIntent().getStringExtra("value");

        image1.setEvent(FrontPage.this);
        image2.setEvent(FrontPage.this);
        image3.setEvent(FrontPage.this);

     /*  if (values.equals(username) && values.equals(password)) {
            eventEnd(0,0);
        }*/

        btn_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Common.SCORE >= 50) //50 is min price to scroll
                {
                  btn_up.setVisibility(View.GONE);
                  btn_down.setVisibility(View.VISIBLE);

                    image1.setRandomImage(new Random().nextInt(5), //Random from 11 images that we have
                          new Random().nextInt((15 - 5) +1 ) + 5); //Random from range 5 to 15 range for rotate count

                    image2.setRandomImage(new Random().nextInt(5), //Random from 11 images that we have

                            new Random().nextInt((15 - 5) +1 ) + 5); //Random from range 5 to 15 range for rotate count

                    image3.setRandomImage(new Random().nextInt(5), //Random from 11 images that we have
                            new Random().nextInt((15 - 5) +1 ) + 5); //Random from range 5 to 15 range for rotate count


                    Common.SCORE -= 10;
                    String score = String.valueOf(Common.SCORE);
                    text_score.setText(score);

                    DB.checkScore(value,score);
                    DB.addScore(score);
                   // DB.getScore(score);



                }
                else{
                    Toast.makeText(FrontPage.this, "You have not enough money", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void eventEnd(int result, int count) {
        if(count_done < 2 ) //If still slot is rolling
            count_done++;
        else{

            btn_down.setVisibility(View.GONE);
            btn_up.setVisibility(View.VISIBLE);
            count_done = 0; //reset

            SharedPreferences settings = getSharedPreferences("Slot Game", Context.MODE_PRIVATE);
            int totalScore = settings.getInt("totalScore", 0);

                //Result
                if (image1.getValue() == image2.getValue() && image2.getValue() == image3.getValue()) {
                    Toast.makeText(this, "You win a big prize", Toast.LENGTH_SHORT).show();
                    Common.SCORE += 500;

                    totalScore = settings.getInt("totalScore", 0);
                    totalScore += Common.SCORE;
                    String score = String.valueOf(totalScore);
                    text_score.setText(score);
                    DB.addScore(score);

                } else if (image1.getValue() == image2.getValue() && image2.getValue() == image3.getValue() && equals(Util.SEVEN)) {
                    Toast.makeText(this, "You win a jackpot price", Toast.LENGTH_SHORT).show();
                    Common.SCORE *= 100;

                    totalScore = settings.getInt("totalScore", 0);
                    totalScore += Common.SCORE;
                    String score = String.valueOf(totalScore);
                    text_score.setText(score);
                    DB.addScore(score);

                } else {
                    Toast.makeText(this, "You lose", Toast.LENGTH_SHORT).show();

                }

                SharedPreferences.Editor editor = settings.edit();
                editor.putInt("totalScore", totalScore);
                editor.commit();
        }


    }
    public void back(View view) {
        Intent intent = new Intent(getApplicationContext(), login.class);
        startActivity(intent);
    }


    public void logout(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}