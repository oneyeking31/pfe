package com.example.exam_gestion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class admin_addaccount_fragment extends Fragment {

    @BindView(R.id.name_feild_singup)
    EditText nameFeildSingup;
    @BindView(R.id.email_feild_singup)
    EditText emailFeildSingup;
    @BindView(R.id.password_field_singup)
    EditText passwordFieldSingup;
    @BindView(R.id.phone_field_singup)
    EditText phoneFieldSingup;
    @BindView(R.id.radiobutton_Adminstrator)
    RadioButton radiobuttonAdminstrator;
    @BindView(R.id.radibutton_professor)
    RadioButton radibuttonProfessor;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.responsabale)
    RadioButton responsabale;
    @BindView(R.id.no_responsable)
    RadioButton noResponsable;
    @BindView(R.id.responsablecheck)
    RadioGroup responsablecheck;
    @BindView(R.id.button)
    Button button;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_addacount_fragment, container, false);
        ButterKnife.bind(this, view);
        view.getContentDescription();
        radibuttonProfessor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responsablecheck.setVisibility(View.VISIBLE);
            }
        });
        radiobuttonAdminstrator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                responsablecheck.setVisibility(View.GONE);
            }
        });
        return view;

    }

    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

   button.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           final String name = nameFeildSingup.getText().toString();
           final String email = emailFeildSingup.getText().toString();
           final String password = passwordFieldSingup.getText().toString();
           final String phone = phoneFieldSingup.getText().toString();
           int checkedId = radiogroup.getCheckedRadioButtonId();
           int checktype = responsablecheck.getCheckedRadioButtonId();
           RadioButton selected_gender = radiogroup.findViewById(checkedId);
           RadioButton selected_type = responsablecheck.findViewById(checktype);
           if (selected_gender == null||selected_type==null) {
               Toast.makeText(getActivity(), "Select account type please", Toast.LENGTH_SHORT).show();
           } else {
               final String REStype = selected_type.getText().toString();
               final String type = selected_gender.getText().toString();
               if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                       TextUtils.isEmpty(phone)) {
                   Toast.makeText(getActivity(), "All fields are required", Toast.LENGTH_SHORT).show();
               }
               else{
                   register(name, email, password, phone, type,REStype);
               }
           }

       }
   });
    }






    private void register(final String name, final String email, final String password, final String mobile, final String type,final String restype) {
        String URL_REGISTER = "http://192.168.1.11/api.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("You are registered successfully")) {
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
                param.put("name", name);
                param.put("email", email);
                param.put("psw", password);
                param.put("mobile", mobile);
                param.put("gender", type);
                param.put("restype", restype);
                return param;

            }


        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getActivity()).addToRequestQueue(request);
    }


}
