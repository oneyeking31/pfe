package com.example.exam_gestion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Admin_consultation_fragment extends Fragment {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Exam> mExams = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_consultation_fragment, container, false);
        view.getContentDescription();
        ButterKnife.bind(this, view);
        getexams();
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new Exam_adapter(mExams);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        return view;

    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {



    }


    public void getexams(){
        final String exam = "http://192.168.1.11/getexamforcons.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET,exam, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray exam = new JSONArray(response);

                    for (int i = 0; i < exam.length(); i++) {

                        JSONObject examobject =exam.getJSONObject(i);
                        int id= examobject.getInt("id");
                        String level= examobject.getString("level");
                        String section= examobject.getString("section");
                        int group= examobject.getInt("groupe");
                        String module= examobject.getString("module");
                        String date= examobject.getString("date");
                        String time= examobject.getString("time");
                        int room= examobject.getInt("room");
                       int duree= examobject.getInt("duration");
                       Exam metud = new Exam(id,level,section,group,module,room,date,time,duree);
                        mExams.add(metud);

                    }
                    mAdapter = new Exam_adapter(mExams);
                    mRecyclerView.setAdapter(mAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

}


