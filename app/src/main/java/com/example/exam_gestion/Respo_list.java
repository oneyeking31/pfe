package com.example.exam_gestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Respo_list extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnGetSelected)
    Button btnGetSelected;
    private ArrayList<Responsable> mResponsables = new ArrayList<>();
    private Respo_adapter adapter;
    private static final String MODULE = "http://192.168.1.11/Respo.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.respo_list_activity);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new Respo_adapter(this, mResponsables);
        recyclerView.setAdapter(adapter);
        Loadrespo();
        final Intent info = getIntent();
        final int count = info.getIntExtra("count",0);
        final String level = info.getStringExtra("level");
        final String section =info.getStringExtra("section");
        final String group =info.getStringExtra("group");
        final String module=info.getStringExtra("module");
        final   String room =info.getStringExtra("room");
        final String date = info.getStringExtra("date");
        final  String time =info.getStringExtra("time");
        final ArrayList<String>  slectedguards = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String gaurd =info.getStringExtra(String.valueOf(i));
            slectedguards.add(gaurd);
        }

        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            String respo;

            @Override
            public void onClick(View view) {
                if (adapter.getSelected() != null) {
                    showToast(adapter.getSelected().getName());
                     respo =adapter.getSelected().getName();
                } else {
                    showToast("No Selection");
                }
                String URL_insertexam = "http://192.168.1.11/insertexam.php";
                StringRequest request = new StringRequest(Request.Method.POST, URL_insertexam, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("Data Submit Successfully")) {
                            Toast.makeText(Respo_list.this, response, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Respo_list.this, response, Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Respo_list.this, error.toString(), Toast.LENGTH_SHORT).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> param = new HashMap<>();
                        param.put("level", level);
                        param.put("section", section);
                        param.put("group", group);
                        param.put("module", module);
                        param.put("room", room);
                        param.put("date", date);
                        param.put("time", time);
                        param.put("chefguard", respo);
                        for (int i =0 ; i<slectedguards.size();i++){
                            int j =i+1;
                            String guard = "guard";
                            param.put(guard+j, slectedguards.get(i));
                        }

                        return param;
                    }


                };

                request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                MySingleton.getmInstance(Respo_list.this).addToRequestQueue(request);
                Intent fin = new Intent(Respo_list.this,Adminspace.class);
                startActivity(fin);
                finish();
            }


        });

    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void Loadrespo() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MODULE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray Respoarray = new JSONArray(response);

                    for (int i = 0; i < Respoarray.length(); i++) {

                        JSONObject respoObject = Respoarray.getJSONObject(i);
                        String name = respoObject.getString("name");
                        Responsable responsable = new Responsable(name);
                        mResponsables.add(responsable);

                    }
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Respo_list.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(Respo_list.this).add(stringRequest);
    }
}