package com.example.myschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschool.models.UE;
import com.example.myschool.models.quizzModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CurrentQuizz extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String base_url = "http://10.0.2.2:8888";
    private ArrayList<UE> listUE;
    private ArrayList<String> reponses = new ArrayList<String>();

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
        this.setContentView(R.layout.activity_current_quizz);

        Intent intent = getIntent();
        String grade = intent.getStringExtra("grade");
        final String UE = intent.getStringExtra("ue");
        final String email = intent.getStringExtra("email");

        final TextView textView = (TextView) findViewById(R.id.textView);
        final TextView textView2 = (TextView) findViewById(R.id.textView2);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        final TextView Ch1 = (TextView) findViewById(R.id.Ch1);
        final TextView Ch2 = (TextView) findViewById(R.id.Ch2);

        final RadioButton choix1 = (RadioButton) findViewById(R.id.Choix1);
        final RadioButton choix2 = (RadioButton) findViewById(R.id.Choix2);
        final RadioButton choix3 = (RadioButton) findViewById(R.id.Choix3);

        final RadioButton choix21 = (RadioButton) findViewById(R.id.Choix21);
        final RadioButton choix22 = (RadioButton) findViewById(R.id.Choix22);
        final RadioButton choix23 = (RadioButton) findViewById(R.id.Choix23);

        textView.setText("MySchool quizz de " + UE);
        textView2.setText(grade);

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

        Call<ArrayList<quizzModel>> call = retrofitInterface.executeGetQuizz(map);

        call.enqueue(new Callback<ArrayList<quizzModel>>() {
            @Override
            public void onResponse(Call<ArrayList<quizzModel>> call, Response<ArrayList<quizzModel>> response) {
                if( response.code() == 200) {
                    for (int i = 0; i < response.body().size(); i++) {
                        if ( i == 0){
                            Ch1.setText(response.body().get(i).getQ());
                           reponses.add(response.body().get(i).getR());
                            choix1.setText(response.body().get(i).getC().get(0).getChoix());
                            choix2.setText(response.body().get(i).getC().get(1).getChoix());
                            choix3.setText(response.body().get(i).getC().get(2).getChoix());

                        }
                        if (i == 1){
                            Ch2.setText(response.body().get(i).getQ());
                            reponses.add(response.body().get(i).getR());
                            choix21.setText(response.body().get(i).getC().get(0).getChoix());
                            choix22.setText(response.body().get(i).getC().get(1).getChoix());
                            choix23.setText(response.body().get(i).getC().get(2).getChoix());

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<quizzModel>> call, Throwable t) {

            }
        });

        final RadioGroup type = (RadioGroup) findViewById(R.id.type);
        final RadioGroup type2 = (RadioGroup) findViewById(R.id.type2);

        Button btnCheck = (Button) findViewById(R.id.btnCheck);

        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent res = new Intent(getApplicationContext(), QuizzRes.class);
                int selectedId = type.getCheckedRadioButtonId();
                RadioButton temp = (RadioButton) findViewById(selectedId);
                String checkedValue = (String) temp.getText();
                if(checkedValue.equals(reponses.get(0))){
                    res.putExtra("q1", Ch1.getText());
                    res.putExtra("res1", "ok");
                    res.putExtra("reponse1", checkedValue);

                }else{
                    res.putExtra("q1", Ch1.getText());
                    res.putExtra("res1", "notok");
                    res.putExtra("reponse1", reponses.get(0));
                }

                int selectedId2 = type2.getCheckedRadioButtonId();
                RadioButton temp2 = (RadioButton) findViewById(selectedId2);
                String checkedValue2 = (String) temp2.getText();
                if(checkedValue2.equals(reponses.get(1))){

                    res.putExtra("q2", Ch2.getText());
                    res.putExtra("res2", "ok");
                    res.putExtra("reponse2", checkedValue);
                }else{

                    res.putExtra("q2", Ch2.getText());
                    res.putExtra("res2", "notok");
                    res.putExtra("reponse2", reponses.get(1));
                }
                res.putExtra("email", email);
                res.putExtra("ue", UE);
                startActivity(res);
                overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
            }
        });
    }
}
