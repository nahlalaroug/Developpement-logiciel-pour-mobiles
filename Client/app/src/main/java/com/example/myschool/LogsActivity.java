package com.example.myschool;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.myschool.models.logModel;
import com.example.myschool.models.userModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogsActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String base_url = "http://10.0.2.2:8888";
    private ArrayList<String> listLogs = new ArrayList<String>();

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
        this.setContentView(R.layout.activity_logs);

        final userModel currentUser = (userModel) getIntent().getSerializableExtra("current_user");

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("email", currentUser.getEmail());

        Call<ArrayList<logModel>> call = retrofitInterface.executeGetLogs(map);
        call.enqueue(new Callback<ArrayList<logModel>>() {
            @Override
            public void onResponse(Call<ArrayList<logModel>> call, Response<ArrayList<logModel>> response) {
                if(response.code() == 200){
                    for (int i = 0; i < response.body().size(); i++) {
                        String tmp = response.body().get(i).getDate() + " " + response.body().get(i).getType() + " " +
                                response.body().get(i).getMarks();
                        listLogs.add(tmp);
                    }

                    ListView my_listview = (ListView)findViewById(R.id.listView1);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(LogsActivity.this, android.R.layout.simple_list_item_1, listLogs);
                    my_listview.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<logModel>> call, Throwable t) {

            }
        });
    }
}
