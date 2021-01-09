package com.example.myschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myschool.models.AuthToSend;
import com.example.myschool.models.UE;
import com.example.myschool.models.gradesResult;
import com.example.myschool.models.loginResult;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String base_url = "http://10.0.2.2:8888";
    private ArrayList<String> grades = new ArrayList<String>();
    private ArrayList<ArrayList<String>> listTopics = new ArrayList<ArrayList<String>>();
    private ArrayList<String> topics = new ArrayList<String>();
    private int step;
    private int formule;
    private boolean formuleEnd;
    private ArrayList<AuthToSend> toSend = new ArrayList<AuthToSend>();
    private AuthToSend mainUser;
    private int cptChild;
    private int nbChild;
    private boolean nbChildSelected;
    private int formula1;
    private  boolean formulaEnd;

    public void nextStep(){
        LinearLayout firstStep = (LinearLayout) findViewById(R.id.firstStep);
        RadioGroup type = (RadioGroup) findViewById(R.id.type);
        ConstraintLayout secondStep = (ConstraintLayout) findViewById(R.id.secondStep);

        ConstraintLayout thirdStep = (ConstraintLayout)findViewById(R.id.thirdStep);
        Spinner mSpinner = (Spinner) findViewById(R.id.spinner);

        ConstraintLayout fourthStep = (ConstraintLayout)findViewById(R.id.fourthStep);
        Spinner UE1 = (Spinner) findViewById(R.id.UE1);
        Spinner UE2 = (Spinner) findViewById(R.id.UE2);

        ConstraintLayout fifthStep = (ConstraintLayout)findViewById(R.id.fifthStep);

        if(step == 1){
            firstStep.setVisibility(View.INVISIBLE);
            int selectedId = type.getCheckedRadioButtonId();
            mainUser.setType(selectedId);
            secondStep.setVisibility(View.VISIBLE);
            step =2;
        }
        else if(step == 2){
                    step=3;
            EditText fname =(EditText)findViewById(R.id.inputFNAME);
            mainUser.setFirstname(fname.getText().toString());
            EditText lname =(EditText) findViewById(R.id.inputLNAME);
            mainUser.setLastname(fname.getText().toString());
            EditText pass =(EditText) findViewById(R.id.inputPassword);
            mainUser.setPass((pass.getText().toString()));
            secondStep.setVisibility(View.INVISIBLE);
            thirdStep.setVisibility(View.VISIBLE);
            EditText email = (EditText) findViewById(R.id.inputEmail);
            mainUser.setEmail(email.getText().toString());
            TextView inputNBChild = (TextView) findViewById(R.id.inputNBChild);
            nbChild = Integer.parseInt(inputNBChild.getText().toString());
            nbChildSelected=true;
            TextView textChild = (TextView) findViewById(R.id.textChild);
            textChild.setText("Information pour l'enfant "+(cptChild+1));

            final NestedScrollView mSCrollView = (NestedScrollView)findViewById(R.id.cl);

            mSCrollView.setScrollY(0);


        }
        else if(step == 3) {
            final NestedScrollView mSCrollView = (NestedScrollView)findViewById(R.id.cl);

            mSCrollView.setScrollY(0);

            step = 4;

                thirdStep.setVisibility(View.INVISIBLE);
                fourthStep.setVisibility(View.VISIBLE);
            }else if(step == 4){
                AuthToSend child = new AuthToSend();
                EditText fnameChild = (EditText) findViewById(R.id.inputFNAMEChild);
                child.setFirstname(fnameChild.getText().toString());
                EditText lnameChild = (EditText) findViewById(R.id.inputLNAMEChild);
                child.setLastname(lnameChild.getText().toString());
                EditText emailChild = (EditText) findViewById(R.id.inputEmailChild);
                child.setEmail(emailChild.getText().toString());
                EditText passChild = (EditText) findViewById(R.id.inputPassChild);
                child.setPass(passChild.getText().toString());
                child.setGrade(mSpinner.getSelectedItem().toString());
                System.out.println("Selected grade = " + mSpinner.getSelectedItem());
                HashMap temp = new HashMap<String, String>();
                if(formule == 1) {
                    temp.put("UE", UE1.getSelectedItem());
                }else if(formule == 2) {
                    temp.put("UE", UE1.getSelectedItem());
                    temp.put("UE", UE2.getSelectedItem());
                }else{
                    temp.put("UE", "All");
                }
                child.getFormula1().add(temp);
                mainUser.getStudents().add(child.getEmail());
                child.debug();
                mainUser.debug();
                cptChild++;
                System.out.println(cptChild+ "/"+nbChild);
                 if(cptChild < nbChild) {
                    System.out.println(cptChild+ "/"+nbChild);
                    step = 3;
                     thirdStep.setVisibility(View.VISIBLE);
                     fourthStep.setVisibility(View.INVISIBLE);
                     TextView textChild = (TextView) findViewById(R.id.textChild);
                     textChild.setText("Information pour l'enfant "+(cptChild+1));
                     //vider les champs ici si tout les eleves sont pas entré de l'étape 3
                    //vider les champs à la fin de l'étape 4
                     toSend.add(child);
                }else{
                    step =5;
                    fourthStep.setVisibility(View.INVISIBLE);
                    fifthStep.setVisibility(View.VISIBLE);
                }


            }else if (step == 5){
                if(formula1 == 1){
                    mainUser.setFormule("progression");
                }else{
                    mainUser.setFormule("accompagnement");
                }
                toSend.add(mainUser);
                step = 6;

             }

        }
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
        this.setContentView(R.layout.activity_auth);

        mainUser= new AuthToSend();
        step = 1;
        cptChild = 0;
        formule =1;
        formuleEnd = false;
        nbChildSelected= false;
        formula1 =1;
        formulaEnd = false;

        final Button next = (Button) findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextStep();
                if(step == 6){
                    for (int i = 0; i < toSend.size(); i++) {

                        Call<String> call = retrofitInterface.executeRegister(toSend.get(i));
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                if(response.code() == 200) {
                                    Toast.makeText(AuthActivity.this, response.body(), Toast.LENGTH_LONG).show();
                                }

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(AuthActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                    }
                }
        });

        final TextView datepicker = (TextView) findViewById(R.id.inputBD);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Enter your birth date");
        final MaterialDatePicker materialDatePicker =  builder.build();

        datepicker.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                datepicker.setText(materialDatePicker.getHeaderText());
            }
        });

        final Spinner mSpinner = (Spinner) findViewById(R.id.spinner);
        Call<ArrayList<gradesResult>> call = retrofitInterface.executeGrades();

        call.enqueue(new Callback<ArrayList<gradesResult>>() {
            @Override
            public void onResponse(Call<ArrayList<gradesResult>> call, Response<ArrayList<gradesResult>> response) {
                System.out.println("HERE HERE HERE HERE : " + response.body().get(0).getGrade());
                for (int i = 0; i < response.body().size(); i ++ ) {
                    response.body().get(i).printGradeResult();
                    String tmp = response.body().get(i).getGrade();
                    listTopics.add(response.body().get(i).getStringifyUE());
                    grades.add(tmp);
                }

                grades.add("Année scolaire");
                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(AuthActivity.this,android.R.layout.simple_spinner_dropdown_item,grades){
                    @Override
                    public int getCount() {
                        return super.getCount()-1;
                    }
                };
                mSpinner.setAdapter(adapter);
                mSpinner.setSelection(adapter.getCount());

            }

            @Override
            public void onFailure(Call<ArrayList<gradesResult>> call, Throwable t) {

            }
        });

        final ImageView formuleImage = (ImageView) findViewById(R.id.airplane);
        final TextView formuleText = (TextView) findViewById(R.id.textView9);

        final Button formuleValidation = (Button) findViewById(R.id.formuleValidation);
        formuleValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView choseUE = (TextView) findViewById(R.id.choseUE);
                Spinner UE1 = (Spinner) findViewById(R.id.UE1);
                Spinner UE2 = (Spinner) findViewById(R.id.UE2);

                ArrayList<String> UES = listTopics.get(mSpinner.getSelectedItemPosition());
                UES.add("Choix de l'UE");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AuthActivity.this,android.R.layout.simple_spinner_dropdown_item,UES){
                    @Override
                    public int getCount() {
                        return super.getCount()-1;
                    }
                };
                UE1.setAdapter(adapter);
                UE1.setSelection(adapter.getCount());
                UE2.setAdapter(adapter);
                UE2.setSelection(adapter.getCount());

                if(!formuleEnd) {
                    formuleEnd = true;
                    formuleValidation.setText("Cancel");
                    if(formule == 1 ){
                        choseUE.setVisibility(View.VISIBLE);
                        UE1.setVisibility(View.VISIBLE);
                    }else if(formule == 2){
                        choseUE.setVisibility(View.VISIBLE);
                        UE1.setVisibility(View.VISIBLE);
                        UE2.setVisibility(View.VISIBLE);
                    }else{
                        choseUE.setText("Vous avez choisis toutes les matières");
                        choseUE.setVisibility(View.VISIBLE);
                    }
                }else{
                    formuleEnd = false;
                    formuleValidation.setText("J'en profite !");
                    UE1.setVisibility(View.INVISIBLE);
                    UE2.setVisibility(View.INVISIBLE);
                    choseUE.setVisibility(View.INVISIBLE);
                    UES = new ArrayList<String>();
                }
            }

        });


        ImageView nextFormule = (ImageView) findViewById(R.id.formuleNext);
        nextFormule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!formuleEnd) {
                    if (formule == 1) {
                        formuleImage.setImageResource(R.drawable.ic_dollar);
                        formuleText.setText("Formule deux cours - 35€");
                        formule = 2;
                    } else if (formule == 2) {
                        formuleImage.setImageResource(R.drawable.ic_money);
                        formuleText.setText("Formule tout les cours - 100€");
                        formule = 3;
                    } else if (formule == 3) {
                        ;
                    }
                }
            }
        });

        ImageView previousFormule = (ImageView) findViewById(R.id.formulePrevious);
        previousFormule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!formuleEnd) {
                    if (formule == 1) {
                        ;
                    } else if (formule == 2) {
                        formuleImage.setImageResource(R.drawable.ic_coin);
                        formuleText.setText("Formule un cours - 20€");
                        formule = 1;
                    } else if (formule == 3) {
                        formuleImage.setImageResource(R.drawable.ic_dollar);
                        formuleText.setText("Formule deux cours - 35€");
                        formule = 2;
                    } else if (formule == 4) {
                        ;
                    }
                }
            }
        });

        final ImageView formula1Next = (ImageView) findViewById(R.id.formula1Next);
        final ImageView formulaPic = (ImageView) findViewById(R.id.formulaPic);
        final TextView  formulaText = (TextView) findViewById(R.id.formula1Text);

        formula1Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!formulaEnd) {
                    if (formula1 == 1) {
                        formula1 = 2;
                        formulaPic.setImageResource(R.drawable.ic_videoconference);
                        formulaText.setText("Formule accompagnement");

                    }
                }
            }
        });

        ImageView formulaPrevious = (ImageView) findViewById(R.id.formula1Previous);
        formulaPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!formulaEnd) {
                    if (formula1 == 2) {
                        formula1 = 1;
                        formulaPic.setImageResource(R.drawable.ic_pdf);
                        formulaText.setText("Formule progression");

                    }
                }
            }
        });

        Button formula1Validation = (Button) findViewById(R.id.formula1Validation);
        formula1Validation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(!formulaEnd){formulaEnd = true;}else{formulaEnd = false;}
            }
        });

    }
}
