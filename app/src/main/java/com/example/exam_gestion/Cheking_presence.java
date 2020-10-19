package com.example.exam_gestion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class Cheking_presence extends AppCompatActivity {
    private static final String EXAMGEST_URL = "http://192.168.1.11/getstudents.php";
    @BindView(R.id.recyclerView)
    RecyclerView recycleview;
    Etudaint_adapter mEtudaint_adapter;
    ArrayList<etudaint> mEtudaintList;
    Etudaint_adapter.Etudaint_viewholder holder;
    SharedPreferences sharedPreferences;
    @BindView(R.id.check)
    Button check;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cheking_presence_activity);
        ButterKnife.bind(this);
        mEtudaintList = new ArrayList<>();
        recycleview.setLayoutManager(new LinearLayoutManager(this));
        recycleview.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        Bundle extras = getIntent().getExtras();
        final int id = extras.getInt("examid",0);
        final int groupeid = extras.getInt("groupeid",0);
        Toast.makeText(this,String.valueOf(groupeid),Toast.LENGTH_SHORT).show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, EXAMGEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray groupssarray = new JSONArray(response);
                    for (int i = 0; i <groupssarray.length(); i++) {
                        JSONObject groupobject = groupssarray.getJSONObject(i);
                       String name =  groupobject.getString("name");
                        etudaint metud = new etudaint(name);
                        mEtudaintList.add(metud);

                    }

                    mEtudaint_adapter = new Etudaint_adapter(Cheking_presence.this, mEtudaintList);
                    recycleview.setAdapter(mEtudaint_adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cheking_presence.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("groupid",String.valueOf(groupeid));
                return param;
            }

        };
        Volley.newRequestQueue(this).add(stringRequest);

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
                if (mEtudaint_adapter.getSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < mEtudaint_adapter.getSelected().size(); i++) {
                        stringBuilder.append(mEtudaint_adapter.getSelected().get(i).getName());
                         final String s = mEtudaint_adapter.getSelected().get(i).getName();
                        stringBuilder.append("\n");
                        String URL_insertexam = "http://192.168.1.11/insertpresence.php";
                        StringRequest request = new StringRequest(Request.Method.POST, URL_insertexam, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("Data Submit Successfully")) {
                                    Toast.makeText(Cheking_presence.this, response, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Cheking_presence.this, response, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Cheking_presence.this, error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("name", s);
                                param.put("present","1");
                                param.put("examid",String.valueOf(id));

                                return param;
                            }


                        };

                        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        MySingleton.getmInstance(Cheking_presence.this).addToRequestQueue(request);



                    }


                    showToast(stringBuilder.toString().trim());
                }
                if (mEtudaint_adapter.getnotSelected().size() > 0) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < mEtudaint_adapter.getnotSelected().size(); i++) {
                        stringBuilder.append(mEtudaint_adapter.getnotSelected().get(i).getName());
                        final String s = mEtudaint_adapter.getnotSelected().get(i).getName();
                        stringBuilder.append("\n");
                        String URL_insertexam = "http://192.168.1.11/insertpresence.php";
                        StringRequest request = new StringRequest(Request.Method.POST, URL_insertexam, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("Data Submit Successfully")) {
                                    Toast.makeText(Cheking_presence.this, response, Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Cheking_presence.this, response, Toast.LENGTH_SHORT).show();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Cheking_presence.this, error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String, String> param = new HashMap<>();
                                param.put("name", s);
                                param.put("present","0");
                                param.put("examid",String.valueOf(id));

                                return param;
                            }


                        };

                        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                        MySingleton.getmInstance(Cheking_presence.this).addToRequestQueue(request);


                    }
                    showToast(stringBuilder.toString().trim());
                }
                else {
                    showToast("No Selection");
                }


                moveTaskToBack(false);



            }
        });



    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        for (int i = 0 ; i<mEtudaintList.size();i++) {
            if (mEtudaintList.get(i).isChecked())
            savedInstanceState.putBoolean(String.valueOf(i), true);
        }
    }


    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        for (int i = 0 ; i<mEtudaintList.size();i++) {
            if(savedInstanceState.getBoolean(String.valueOf(i), true)){
                mEtudaint_adapter.onBindViewHolder(holder,i);
            }

        }
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void Loadetudaints() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, EXAMGEST_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("tagconvertstr", "["+response+"]");
                try {
                    JSONArray etudaiant = new JSONArray(response);

                    for (int i = 0; i < etudaiant.length(); i++) {
                        JSONObject etudaintobject = etudaiant.getJSONObject(i);
                        String name = etudaintobject.getString("name");
                        int groupe = etudaintobject.getInt("groupeid");
                        etudaint metud = new etudaint(name);
                        mEtudaintList.add(metud);

                    }
                    mEtudaint_adapter = new Etudaint_adapter(Cheking_presence.this, mEtudaintList);

                    recycleview.setAdapter(mEtudaint_adapter);




                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cheking_presence.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                return param;
            }

        };

        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void  Loadprofs() {
         final String presence = "http://192.168.1.11/adminpresence.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, presence, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray sectionsarray = new JSONArray(response);
                    for (int i = 0; i < sectionsarray.length(); i++) {
                        JSONObject sectionobject = sectionsarray.getJSONObject(i);
                        int present = sectionobject.getInt("present");
                        mEtudaintList.get(i).setPresence(present);
                        if ( mEtudaintList.get(i).isPresence()==1){
                            holder.name.setTextColor(Color.GREEN);
                        }
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cheking_presence.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                HashMap<String, String> param = new HashMap<>();
                Bundle extras = getIntent().getExtras();
                final int id = extras.getInt("examid",0);
                param.put("id",String.valueOf(id));
                return param;
            }

        };
        Volley.newRequestQueue(Cheking_presence.this).add(stringRequest);
    }
    public void delete() {

        String URL_insertexam = "http://192.168.1.11/deletepresence.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL_insertexam, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Data Submit Successfully")) {
                    Toast.makeText(Cheking_presence.this, response, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Cheking_presence.this, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Cheking_presence.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                Bundle extras = getIntent().getExtras();
                final int id = extras.getInt("examid",0);
                param.put("id", String.valueOf(id));
                return param;
            }


        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(Cheking_presence.this).addToRequestQueue(request);
    }


}