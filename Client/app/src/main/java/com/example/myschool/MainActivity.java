package com.example.myschool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myschool.fragments.PageFragment1;
import com.example.myschool.fragments.PageFragment2;
import com.example.myschool.fragments.PageFragment3;
import com.example.myschool.models.SlidePagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String base_url = "http://10.0.2.2:8888";
    private PagerAdapter pagerAdapter;
    private ViewPager pager;

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
        this.setContentView(R.layout.activity_main);

        List<Fragment> list =new ArrayList<Fragment>();
        list.add(new PageFragment1());
        list.add(new PageFragment2());
        list.add(new PageFragment3());

        pager = findViewById(R.id.pager);
        pagerAdapter = new SlidePagerAdapter(getSupportFragmentManager(), list);
        SpringDotsIndicator springDotsIndicator = (SpringDotsIndicator) findViewById(R.id.spring_dots_indicator);
        pager.setAdapter(pagerAdapter);
        springDotsIndicator.setViewPager(pager);


        Button login = (Button) findViewById(R.id.login);
        Button reg = (Button) findViewById(R.id.reg);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    LinearLayout navigation = (LinearLayout) findViewById(R.id.navigation);
                    navigation.setVisibility(View.VISIBLE);
                }
                if(position !=2){
                    LinearLayout navigation = (LinearLayout) findViewById(R.id.navigation);
                    navigation.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        login.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
                Intent authActivity = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(authActivity);overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent authActivity = new Intent(getApplicationContext(), AuthActivity.class);
                startActivity(authActivity);overridePendingTransition(R.transition.pull_from_bottom, R.transition.push_out_from_bottom);
            }
        });

    }
}
