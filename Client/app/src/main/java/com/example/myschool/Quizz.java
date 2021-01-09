package com.example.myschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschool.models.UE;
import com.example.myschool.models.userModel;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Quizz extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String base_url = "http://10.0.2.2:8888";
    private ArrayList<UE> listUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        retrofit = new Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).build();

        retrofitInterface = retrofit.create(RetrofitInterface.class);

        //Cette directive enlève la barre de titre
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Cette directive permet d'enlever la barre de notifications pour afficher l'application en plein écran
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //On définit le contenu de la vue APRES les instructions précédentes pour éviter un crash
        this.setContentView(R.layout.activity_my_courses);

        final userModel currentUser = (userModel) getIntent().getSerializableExtra("current_user");

        Toast.makeText(Quizz.this, currentUser.getGrade(), Toast.LENGTH_LONG).show();
        CardView c1 = (CardView) findViewById(R.id.UE1);
        CardView c2 = (CardView) findViewById(R.id.UE2);
        CardView c3 = (CardView) findViewById(R.id.UE3);

        final TextView UE1Title = (TextView) findViewById(R.id.U1Title);
        final TextView UE2Title = (TextView) findViewById(R.id.U2Title);
        final TextView UE3Title = (TextView) findViewById(R.id.U3Title);

        for (int i = 0; i < currentUser.getListUE().size(); i++) {
            if (i == 0) {
                c1.setVisibility(View.VISIBLE);
                UE1Title.setText(currentUser.getListUE().get(i).getUe());

                ImageView UE1IMG = (ImageView) findViewById(R.id.UE1Iimg);
                switch (currentUser.getListUE().get(i).getUe()) {
                    case "Mathématique":
                        UE1IMG.setImageResource(R.drawable.ic_math);
                        break;
                    case "Physique":
                        UE1IMG.setImageResource(R.drawable.ic_phy);
                        break;
                    case "Chimie":
                        UE1IMG.setImageResource(R.drawable.ic_chi);
                        break;
                    case "Français":
                        UE1IMG.setImageResource(R.drawable.ic_fr);
                        break;
                    case "Histoire":
                        UE1IMG.setImageResource(R.drawable.ic_hi);
                        break;
                }

            } else if (i == 1) {
                c2.setVisibility(View.VISIBLE);
                UE2Title.setText(currentUser.getListUE().get(i).getUe());

                ImageView UE2IMG = (ImageView) findViewById(R.id.UE2Iimg);

                switch (currentUser.getListUE().get(i).getUe()) {
                    case "Mathématique":
                        UE2IMG.setImageResource(R.drawable.ic_math);
                        break;
                    case "Physique":
                        UE2IMG.setImageResource(R.drawable.ic_phy);
                        break;
                    case "Chimie":
                        UE2IMG.setImageResource(R.drawable.ic_chi);
                        break;
                    case "Français":
                        UE2IMG.setImageResource(R.drawable.ic_fr);
                        break;
                    case "Histoire":
                        UE2IMG.setImageResource(R.drawable.ic_hi);
                        break;
                }

            } else if (i == 2) {
                c3.setVisibility(View.VISIBLE);

                UE3Title.setText(currentUser.getListUE().get(i).getUe());

                ImageView UE3IMG = (ImageView) findViewById(R.id.UE3Iimg);

                switch (currentUser.getListUE().get(i).getUe()) {
                    case "Mathématique":
                        UE3IMG.setImageResource(R.drawable.ic_math);
                        break;
                    case "Physique":
                        UE3IMG.setImageResource(R.drawable.ic_phy);
                        break;
                    case "Chimie":
                        UE3IMG.setImageResource(R.drawable.ic_chi);
                        break;
                    case "Français":
                        UE3IMG.setImageResource(R.drawable.ic_fr);
                        break;
                    case "Histoire":
                        UE3IMG.setImageResource(R.drawable.ic_hi);
                        break;
                }

            }
        }

        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent currentQuizz = new Intent(getApplicationContext(), CurrentQuizz.class);
                currentQuizz.putExtra("grade", currentUser.getGrade());
                currentQuizz.putExtra("ue", UE1Title.getText().toString());
                currentQuizz.putExtra("email", currentUser.getEmail());
                startActivity(currentQuizz);
                overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent currentQuizz = new Intent(getApplicationContext(), CurrentQuizz.class);
                currentQuizz.putExtra("grade", currentUser.getGrade());
                currentQuizz.putExtra("ue", UE2Title.getText().toString());
                currentQuizz.putExtra("email", currentUser.getEmail());
                startActivity(currentQuizz);
                overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent currentQuizz = new Intent(getApplicationContext(), CurrentQuizz.class);
                currentQuizz.putExtra("grade", currentUser.getGrade());
                currentQuizz.putExtra("ue", UE3Title.getText().toString());
                currentQuizz.putExtra("email", currentUser.getEmail());
                startActivity(currentQuizz);
                overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
            }
        });
    }
}