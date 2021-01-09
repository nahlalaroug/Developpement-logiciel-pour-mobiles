package com.example.myschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.myschool.models.loginResult;
import com.example.myschool.models.userModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashboardActivity extends AppCompatActivity {

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
        this.setContentView(R.layout.activity_dashboard);

        CardView courses = (CardView) findViewById(R.id.courses);
        CardView quizz = (CardView) findViewById(R.id.quizz);
        CardView logs = (CardView) findViewById(R.id.logs);
        CardView advice = (CardView) findViewById(R.id.advice);

        final loginResult loginRes = (loginResult) getIntent().getSerializableExtra("current_user");
        final userModel[] result = {new userModel()};
        HashMap<String, String> map = new HashMap<>();
        map.put("email", loginRes.getEmail());
        Call<userModel> call = retrofitInterface.executeGetMember(map);

        call.enqueue(new Callback<userModel>() {
                         @Override
                         public void onResponse(Call<userModel> call, Response<userModel> response) {
                             if (response.code() == 200) {
                                 result[0] = response.body();
                                 Toast.makeText(DashboardActivity.this, "success", Toast.LENGTH_LONG).show();
                             }
                         }
                         @Override
                         public void onFailure(Call<userModel> call, Throwable t) {
                             Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                         }
                     });

                courses.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myCourses = new Intent(getApplicationContext(), MyCourses.class);
                        myCourses.putExtra("current_user", result[0]);
                        startActivity(myCourses);
                        overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
                    }
                });

                 quizz.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             Intent myCourses = new Intent(getApplicationContext(), Quizz.class);
                             myCourses.putExtra("current_user", result[0]);
                             startActivity(myCourses);
                             overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
                         }
                 });

                 logs.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         Intent logActivity = new Intent(getApplicationContext(), LogsActivity.class);
                         logActivity.putExtra("current_user", result[0]);
                         startActivity(logActivity);
                         overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);

                     }
                 });

                 advice.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View v) {
                         DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                         LocalDateTime now = LocalDateTime.now();

                         HashMap<String, String> map = new HashMap<>();
                         map.put("email", result[0].getEmail());
                         map.put("date", dtf.format(now));
                         map.put("type", "advices");
                         map.put("marks", "none");

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
                                 Toast.makeText(DashboardActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                             }
                         });

                         Intent adviceActivity = new Intent(getApplicationContext(), AdviceActivity.class);
                         adviceActivity.putExtra("current_user", result[0]);
                         startActivity(adviceActivity);
                         overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);

                     }
                 });
    }
}
