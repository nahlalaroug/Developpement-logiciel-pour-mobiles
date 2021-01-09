package com.example.myschool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschool.models.loginResult;
import com.facebook.login.LoginResult;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    protected TextInputEditText email;
    protected EditText pass;
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
        this.setContentView(R.layout.activity_login);

       Button logButton = (Button) findViewById(R.id.btnLogin);
        email = (TextInputEditText) findViewById(R.id.inputEmail);
        pass = (EditText) findViewById(R.id.inputPassword);

        logButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().isEmpty() && !pass.getText().toString().isEmpty()){
                    HashMap<String, String> map = new HashMap<>();
                    map.put("email", email.getText().toString());
                    map.put("password", pass.getText().toString());

                   // Toast.makeText(LoginActivity.this, "Click with : " + email.getText().toString() + " : " + pass.getText().toString(), Toast.LENGTH_LONG).show();

                    Call<loginResult> call = retrofitInterface.executeLogin(map);

                    call.enqueue(new Callback<loginResult>() {
                        @Override
                        public void onResponse(Call<loginResult> call, Response<loginResult> response) {
                            if (response.code() == 200) {
                                loginResult result = response.body();
                              //  Toast.makeText(LoginActivity.this, result.getEmail() + ": " + result.getName(), Toast.LENGTH_LONG).show();
                                if (result.getType() == 2) {
                                    Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
                                    dashboard.putExtra("current_user", result);
                                    startActivity(dashboard);
                                    overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
                                } else {
                                    Intent dashboard = new Intent(getApplicationContext(), ParentDashboardActivity.class);
                                    startActivity(dashboard);
                                    overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
                                }
                            } else if (response.code() == 400) {
                               Toast.makeText(LoginActivity.this, "user not found", Toast.LENGTH_LONG).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<loginResult> call, Throwable t) {
                          //  Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Veuillez remplir tout les champs", Toast.LENGTH_LONG).show();

                }
            }
            });

        TextView forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty() ) {
                    Toast.makeText(LoginActivity.this,"Veuillez entrer votre email", Toast.LENGTH_LONG).show();

                }else {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("email", email.getText().toString());

                    Call<String> call = retrofitInterface.executeFP(map);

                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if ( response.code() == 200) {
                                Toast.makeText(LoginActivity.this, response.body(), Toast.LENGTH_LONG).show();
                            }else if (response.code() == 400 ){
                                Toast.makeText(LoginActivity.this, "User not found.", Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }
        }));

        TextView auth = (TextView) findViewById(R.id.gotoRegister);
        auth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent authActivity = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(authActivity);
                overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
            }
        });

    }
}
