package com.example.exam_gestion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
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

public class Admin_presence extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private Prsence_Adapter mAdapter;
    ArrayList<Presence> mPresences = new ArrayList<>();
    private static final String presence = "http://192.168.1.11/adminpresence.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_presence_activity);
        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);
        mAdapter = new Prsence_Adapter(this, mPresences);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        Loadprofs();

    }



    private void Loadprofs() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, presence, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONArray sectionsarray = new JSONArray(response);
                    for (int i = 0; i < sectionsarray.length(); i++) {
                        JSONObject sectionobject = sectionsarray.getJSONObject(i);
                        String name = sectionobject.getString("name");
                        int present = sectionobject.getInt("present");
                       Presence presence= new Presence(name,present);
                       mPresences.add(presence);
                    }
                    recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Admin_presence.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Intent info = getIntent();
                int ids =info.getIntExtra("ID",0);
                String id = String.valueOf(ids);
                HashMap<String, String> param = new HashMap<>();
                param.put("id",id);
                return param;
            }

        };
        Volley.newRequestQueue(Admin_presence.this).add(stringRequest);
    }




}