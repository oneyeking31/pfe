package com.example.exam_gestion;

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

public class Module_list extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnGetSelected)
    Button btnGetSelected;
    private ArrayList<Module> modules = new ArrayList<>();
    private Module_adapter adapter;
    private static final String MODULE = "http://192.168.1.11/getmodulelist.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.module_list_activity);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new Module_adapter(this, modules);
        recyclerView.setAdapter(adapter);
        Loadetudaints();
        Bundle bundle = getIntent().getExtras();
        final String message = bundle.getString("message");

        btnGetSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (adapter.getSelected() != null) {
                    showToast(adapter.getSelected().getName());
                } else {
                    showToast("No Selection");
                }


            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }









    private void Loadetudaints() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MODULE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray modulerray = new JSONArray(response);

                    for (int i = 0; i < modulerray.length(); i++) {

                        JSONObject modulebject = modulerray.getJSONObject(i);
                        String name = modulebject.getString("name");
                        Module MODULE = new Module(name,"");
                      modules.add(MODULE);

                    }
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Module_list.this, error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(Module_list.this).add(stringRequest);
    }

}