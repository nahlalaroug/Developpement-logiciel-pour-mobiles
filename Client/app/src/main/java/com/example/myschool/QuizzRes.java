package com.example.myschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschool.models.loginResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class QuizzRes extends AppCompatActivity {
    private float score;
    private boolean isDropped;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String base_url = "http://10.0.2.2:8888";


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
        this.setContentView(R.layout.activity_quizz_res);

        Intent intent = getIntent();

        isDropped = false;

        final String q1 = intent.getStringExtra("q1");
        String res1 = intent.getStringExtra("res1");
        final String reponse1 = intent.getStringExtra("reponse1");

        final String q2 = intent.getStringExtra("q2");
        String res2 = intent.getStringExtra("res2");
        final String reponse2 = intent.getStringExtra("reponse2");

        final String email = intent.getStringExtra("email");
        final String UE = intent.getStringExtra("ue");

        final TextView t1 = new TextView(QuizzRes.this);
        final TextView t2 = new TextView(QuizzRes.this);
        final TextView t3 = new TextView(QuizzRes.this);


        if( res1.equals("ok") ){
            score+=0.5;
            t2.setTextColor(getResources().getColor(R.color.colorPrimary, getResources().newTheme()));
        }else{
            t2.setTextColor(getResources().getColor(R.color.colorAccent, getResources().newTheme()));

        }
        if( res2.equals("ok") ){
            score+=0.5;
            t3.setTextColor(getResources().getColor(R.color.colorPrimary, getResources().newTheme()));

        }else{
            t3.setTextColor(getResources().getColor(R.color.colorAccent, getResources().newTheme()));
        }

        score*=100;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        HashMap<String, String> map = new HashMap<>();
        map.put("email", email);
        map.put("date", dtf.format(now));
        map.put("type", "quizz de "+ UE );
        map.put("marks", String.valueOf(score));

        Call<String> call = retrofitInterface.executeAddLog(map);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 200){
                    ;
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(QuizzRes.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        TextView Ch1 = (TextView) findViewById(R.id.Ch1);
        Ch1.setText("Votre score est de " + score + "%");

        TextView remark = (TextView) findViewById(R.id.remark);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        ImageView img = (ImageView) findViewById(R.id.img);

        if (score < 50.0 ) {
            imageView.setImageResource(R.drawable.ic_bad);
            img.setImageResource(R.drawable.ic_head);
            remark.setText("Votre score est en dessous de la moyenne. Vous devriez revoir votre cours.");
        }else{
            imageView.setImageResource(R.drawable.ic_good);
            img.setImageResource(R.drawable.ic_party);
            remark.setText("Votre score est au dessous de la moyenne. Félicitation !");
        }


        final LinearLayout l3 = (LinearLayout) findViewById(R.id.linearLayout3);

        TextView dropdown = (TextView) findViewById(R.id.dropdown);
        dropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isDropped) {
                    t1.setText("Les réponses étaient ");
                    t1.setPadding(0, 5, 0, 0);
                    l3.addView(t1);

                    t2.setText("Q: " + q1 + "R: " + reponse1);
                    t2.setPadding(0, 5, 0, 0);
                    l3.addView(t2);

                    t3.setText("Q: " + q2 + "R: " + reponse2);
                    t3.setPadding(0, 5, 0, 0);
                    l3.addView(t3);
                    isDropped=true;
                }else{
                    l3.removeView(t1);
                    l3.removeView(t2);
                    l3.removeView(t3);
                    isDropped=false;
                }


            }
        });

        Button ret = (Button) findViewById(R.id.ret);
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginResult result = new loginResult();
                result.setEmail(email);
               Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                dashboard.putExtra("current_user", result);
                startActivity(dashboard);
                overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
            }
        });
    }
}
