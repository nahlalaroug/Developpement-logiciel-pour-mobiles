package com.example.myschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschool.models.UE;
import com.example.myschool.models.loginResult;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentCourse extends AppCompatActivity {

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
        this.setContentView(R.layout.activity_current_course);

        Intent intent = getIntent();
        String grade = intent.getStringExtra("grade");
        String UE = intent.getStringExtra("ue");

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.textView2);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        textView.setText("MySchool cours de " + UE);
        textView2.setText( grade );

        switch (UE) {
            case "Mathématique":
                imageView.setImageResource(R.drawable.ic_math);
                break;
            case "Physique":
                imageView.setImageResource(R.drawable.ic_phy);
                break;
            case "Chimie":
                imageView.setImageResource(R.drawable.ic_chi);
                break;
            case "Français":
                imageView.setImageResource(R.drawable.ic_fr);
                break;
            case "Histoire":
                imageView.setImageResource(R.drawable.ic_hi);
                break;
        }

        HashMap<String, String> map = new HashMap<>();
        map.put("grade", grade);
        map.put("UE", UE);

        Call<UE> call = retrofitInterface.executeGetUE(map);

        call.enqueue(new Callback<com.example.myschool.models.UE>() {
            @Override
            public void onResponse(Call<com.example.myschool.models.UE> call, Response<com.example.myschool.models.UE> response) {
                if (response.code() == 200) {
                    System.out.println("UE : " + response.body().getLabel());
                    System.out.println("SIZE : " + response.body().getChapitres().size());
                    for (int j = 0; j < response.body().getChapitres().size(); j++) {
                        System.out.println("AAAAA" + response.body().getChapitres().get(j).getText());
                        if (j == 0) {
                            TextView textCh1 = (TextView) findViewById(R.id.textCh1);
                            textCh1.setText(response.body().getChapitres().get(j).getText());
                        } else if (j == 1) {
                            TextView textCh2 = (TextView) findViewById(R.id.textCh2);
                            textCh2.setText(response.body().getChapitres().get(j).getText());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<com.example.myschool.models.UE> call, Throwable t) {
                Toast.makeText(CurrentCourse.this, t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }
}
