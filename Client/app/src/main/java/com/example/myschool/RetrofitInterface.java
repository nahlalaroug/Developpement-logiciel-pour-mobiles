package com.example.myschool;

import com.example.myschool.models.AuthToSend;
import com.example.myschool.models.UE;
import com.example.myschool.models.adviceModel;
import com.example.myschool.models.gradesResult;
import com.example.myschool.models.logModel;
import com.example.myschool.models.loginResult;
import com.example.myschool.models.quizzModel;
import com.example.myschool.models.userModel;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitInterface {

    @GET("/test")
    Call<String> executeTest();

    @POST("/login")
    Call<loginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/forgotpass")
    Call<String> executeFP(@Body HashMap<String, String> map);

    @GET("/grades")
    Call<ArrayList<gradesResult>> executeGrades();

    @POST("/register")
    Call<String> executeRegister(@Body AuthToSend toSend);

    @POST("/getMemberByEmail")
    Call<userModel> executeGetMember(@Body HashMap<String, String> map);

    @POST("/getCourseByGradeAndUE")
    Call<UE> executeGetUE(@Body HashMap<String, String> map);

    @POST("/getQuizzByGradeAndUE")
    Call<ArrayList<quizzModel>> executeGetQuizz(@Body HashMap<String, String> map);

    @POST("/addLog")
    Call<String> executeAddLog(@Body HashMap<String, String> map);

    @POST("/getLogs")
    Call<ArrayList<logModel>> executeGetLogs(@Body HashMap<String, String> map);

    @POST("/getAdvices")
    Call<ArrayList<adviceModel>> executeGetAdvices(@Body HashMap<String, String> map);

}
