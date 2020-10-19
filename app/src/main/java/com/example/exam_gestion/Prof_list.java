package com.example.exam_gestion;

import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Prof_list extends AppCompatActivity {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.okbutton)
    Button okbutton;
    private Prof_adapter mAdapter;
    ArrayList<prof> profsList = new ArrayList<>();
    private static final String GUARD = "http://192.168.1.11/guard.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prof_list_activity);
        ButterKnife.bind(this);
        recyclerView.setHasFixedSize(true);
        mAdapter = new Prof_adapter(this, profsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        final Intent i = new Intent(Prof_list.this, Respo_list.class);
        Intent info = getIntent();
        final String level = info.getStringExtra("level");
        final String section =info.getStringExtra("section");
        final String group =info.getStringExtra("group");
       final String module=info.getStringExtra("module");
      final   String room =info.getStringExtra("room");
       final String date = info.getStringExtra("date");
        final  String time =info.getStringExtra("time");
        Toast.makeText(this,level+section+group+module+room+date+time,Toast.LENGTH_LONG).show();
        Loadprofs();
        okbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count;
                if (mAdapter.getSelected().size() > 0 && mAdapter.getSelected().size() <= 2 ) {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (   count = 0; count < mAdapter.getSelected().size(); count++) {
                        stringBuilder.append(mAdapter.getSelected().get(count).getName());
                       i.putExtra(String.valueOf(count),mAdapter.getSelected().get(count).getName());
                    }
                    i.putExtra("count",count);
                    i.putExtra("level", level);
                    i.putExtra("section", section);
                    i.putExtra("group", group);
                    i.putExtra("module", module);
                    i.putExtra("date", date);
                    i.putExtra("time", time);
                    i.putExtra("room", room);
                    startActivity(i);



                } else {
                    showToast("No Selection or past limit");
                }


            }
        });
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void Loadprofs() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GUARD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray prof = new JSONArray(response);
                    for (int i = 0; i < prof.length(); i++) {
                        JSONObject profobject = prof.getJSONObject(i);
                        String name = profobject.getString("name");
                        prof prof1 = new prof(name);
                        profsList.add(prof1);

                    }
                    recyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Prof_list.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(Prof_list.this).add(stringRequest);
    }

}