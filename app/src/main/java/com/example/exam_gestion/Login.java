package com.example.exam_gestion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login extends AppCompatActivity {

    @BindView(R.id.email_feild)
    EditText emailFeild;
    @BindView(R.id.password_field)
    EditText passwordField;
    @BindView(R.id.button)
    Button login;

    SharedPreferences sharedPreferences;
    @BindView(R.id.checklogin)
    CheckBox checklogin;
    String type ;
    final static String cehck_URL = "http://192.168.1.11/checktype.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String loginStatus = sharedPreferences.getString(getResources().getString(R.string.prefStatus), "");
        if (loginStatus.equals("loggedprof")) {
            Intent in =new Intent(Login.this,Profspace.class);
            startActivity(in);
            finish();
        }
        else if (loginStatus.equals("loggedadmin")) {
            Intent in =new Intent(Login.this,Adminspace.class);
            startActivity(in);
            finish();
        }



    }

    public void singin(View view) {


        String email = emailFeild.getText().toString();
        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(Login.this, "All Fields Required", Toast.LENGTH_SHORT).show();
        } else {
            login(email,password);
        }
    }




 public void login(final String email, final String password) {
        String URL_REGISTER = "http://192.168.1.11/login2.php";

        StringRequest request = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String name="";
                String typee="";
                String restypee="";
                try {
                    JSONArray profarray= new JSONArray(response);
                    for (int i = 0; i < profarray.length(); i++) {
                        JSONObject profobject = profarray.getJSONObject(i);
                         name= profobject.getString("name");
                        typee= profobject.getString("type");
                        restypee= profobject.getString("restype");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                if (typee.equals("Proffesor") && restypee.equals("Non responsable")) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (checklogin.isChecked()){
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedprof");
                    }
                    else{
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedout");
                    }
                    Intent intent = new Intent(Login.this, Profspace.class);
                    intent.putExtra("name",name);
                    intent.putExtra("type",typee);
                    intent.putExtra("restype",restypee);
                    startActivity(intent);
                    editor.apply();
                    finish();
                }
                else if (typee.equals("Adminstrator")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (checklogin.isChecked()){
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedadmin");
                    }
                    else{
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedout");
                    }

                    Intent intent = new Intent(Login.this, Adminspace.class);
                    intent.putExtra("name",name);
                    intent.putExtra("type",typee);
                    intent.putExtra("restype",restypee);
                    startActivity(intent);
                    editor.apply();
                    finish();

                }
                else if (typee.equals("Proffesor") && restypee.equals("Responsable")){
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    if (checklogin.isChecked()){
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedrespo");
                    }
                    else{
                        editor.putString(getResources().getString(R.string.prefStatus),"loggedout");
                    }
                    Intent intent = new Intent(Login.this, Resposapce.class);
                    intent.putExtra("name",name);
                    intent.putExtra("type",typee);
                    intent.putExtra("restype",restypee);
                    startActivity(intent);
                    editor.apply();
                    finish();

                }

                else {
                    Toast.makeText(Login.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("email", email);
                param.put("psw", password);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Login.this).addToRequestQueue(request);

    }


    public void  checktype ( ){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, cehck_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray groupssarray = new JSONArray(response);
                    JSONObject groupobject = groupssarray.getJSONObject(0);
                  type = groupobject.getString("type");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("email",emailFeild.getText().toString());
                return param;
            }


        };

        Volley.newRequestQueue(Login.this).add(stringRequest);



    }




}
