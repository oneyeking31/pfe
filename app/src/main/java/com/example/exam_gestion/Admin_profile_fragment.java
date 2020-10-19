package com.example.exam_gestion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Admin_profile_fragment extends Fragment {
    SharedPreferences sharedPreferences;
    SharedPreferences accountinfo;
    @BindView(R.id.singout)
    TextView singout;
    @BindView(R.id.typespace)
    TextView typespace;
    @BindView(R.id.namespace)
    TextView namespace;
    @BindView(R.id.account_image)
    ImageView accountImage;
    @BindView(R.id.editpassword)
    TextView editpassword;
    @BindView(R.id.oldpass)
    EditText oldpass;
    @BindView(R.id.newpass)
    EditText newpass;
    @BindView(R.id.confpass)
    EditText confpass;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.editpasslayout)
    LinearLayout editpasslayout;
    @BindView(R.id.contactus)
    TextView contactus;
    @BindView(R.id.salah)
    TextView salah;
    @BindView(R.id.amina)
    TextView amina;
    @BindView(R.id.contact)
    LinearLayout contact;
    String pass;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_profile_fragment, container, false);
        ButterKnife.bind(this, view);
        view.getContentDescription();
        SharedPreferences prefs = getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String status = prefs.getString("Loginstate", "mknch");
        Toast.makeText(getActivity(), status, Toast.LENGTH_SHORT).show();
        accountinfo = this.getActivity().getSharedPreferences("accountinfo", Context.MODE_PRIVATE);
        if (status.equals("loggedout")) {
            Intent intent = getActivity().getIntent();
            String name = intent.getStringExtra("name");
            String type = intent.getStringExtra("type");
            String restype = intent.getStringExtra("restype");
            SharedPreferences.Editor edit = accountinfo.edit();
            edit.putString("accountname", name);
            edit.putString("accounresttype", restype);
            edit.putString("accounttype", type);
            edit.apply();
            namespace.setText(name);
            if (type.equals("Adminstrator")) {
                typespace.setText(type);

            } else {
                typespace.setText(restype);

            }
        } else {
            String N = accountinfo.getString("accountname", "null");
            String T = accountinfo.getString("accounttype", "null");
            String R = accountinfo.getString("accounresttype", "null");
            namespace.setText(N);
            if (T.equals("Adminstrator")) {
                typespace.setText(T);

            } else {
                typespace.setText(R);

            }


        }
        sharedPreferences = this.getActivity().getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        singout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getResources().getString(R.string.prefStatus), "logout");
                editor.apply();
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);

            }
        });
        return view;


    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        editpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editpasslayout.getVisibility() == view.VISIBLE) {
                    editpasslayout.setVisibility(view.GONE);
                } else {
                    editpasslayout.setVisibility(view.VISIBLE);
                }

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getpassword();
                if (!oldpass.getText().toString().equals(pass)) {
                    Toast.makeText(getActivity(), "wrong old password", Toast.LENGTH_SHORT).show();
                } else if (newpass.getText().toString().length() < 8) {
                    Toast.makeText(getActivity(), "short password ", Toast.LENGTH_SHORT).show();
                } else if (!newpass.getText().toString().equals(confpass.getText().toString())) {
                    Toast.makeText(getActivity(), "passowrds dosent match", Toast.LENGTH_SHORT).show();
                } else {
                    setpassword(confpass.getText().toString());
                }


            }
        });
        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contact.getVisibility() == view.VISIBLE) {
                    contact.setVisibility(view.GONE);
                } else {
                    contact.setVisibility(view.VISIBLE);
                }

            }
        });
        amina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = amina.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+ mail));
                startActivity(intent);
            }
        });


        salah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = salah.getText().toString();
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"+ mail));
                startActivity(intent);
            }
        });

    }



    public void getpassword() {

        final String GETEXAM_URL = "http://192.168.1.11/getpass.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GETEXAM_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray sectionsarray = new JSONArray(response);
                    JSONObject sectionobject = sectionsarray.getJSONObject(0);
                    pass = sectionobject.getString("pass");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                Intent intent = getActivity().getIntent();
                String name = intent.getStringExtra("name");
                param.put("name", name);
                return param;
            }


        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }


    public void setpassword(final String password) {

        String URL_insertexam = "http://192.168.1.11/setpassword.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL_insertexam, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("Data Submit Successfully")) {
                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                Intent intent = getActivity().getIntent();
                String name = intent.getStringExtra("name");
                param.put("name", name);
                param.put("pass", password);
                return param;
            }


        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getActivity()).addToRequestQueue(request);


    }
}