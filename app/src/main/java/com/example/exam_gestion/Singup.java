package com.example.exam_gestion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class Singup extends AppCompatActivity {

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
    @BindView(R.id.button)
    Button button;

    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.responsabale)
    RadioButton responsabale;
    @BindView(R.id.no_responsable)
    RadioButton noResponsable;
    @BindView(R.id.responsablecheck)
    RadioGroup responsablecheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singup_activity);
        ButterKnife.bind(this);
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
    }

    public void singed(View view) {
        final String name = nameFeildSingup.getText().toString();
        final String email = emailFeildSingup.getText().toString();
        final String password = passwordFieldSingup.getText().toString();
        final String phone = phoneFieldSingup.getText().toString();
        int checkedId = radiogroup.getCheckedRadioButtonId();
        int checktype = responsablecheck.getCheckedRadioButtonId();
        RadioButton selected_gender = radiogroup.findViewById(checkedId);
        RadioButton selected_type = responsablecheck.findViewById(checktype);
        if (selected_gender == null||selected_type==null) {
            Toast.makeText(Singup.this, "Select account type please", Toast.LENGTH_SHORT).show();
        } else {
            final String REStype = selected_type.getText().toString();
            final String type = selected_gender.getText().toString();
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password) ||
                    TextUtils.isEmpty(phone)) {
                Toast.makeText(Singup.this, "All fields are required", Toast.LENGTH_SHORT).show();
            }
            else{
                register(name, email, password, phone, type,REStype);
                }
            }

        }



    public void tosingin(View view) {
        Intent intent = new Intent(Singup.this, Login.class);
        startActivity(intent);

    }

    private void register(final String name, final String email, final String password, final String mobile, final String type,final String restype) {
        String URL_REGISTER = "http://192.168.1.11/api.php";
        StringRequest request = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (response.equals("You are registered successfully")) {
                    Toast.makeText(Singup.this, response, Toast.LENGTH_SHORT).show();
                    if (restype.equals("Responsable")){
                    Intent intent = new Intent(Singup.this, Module_list.class);
                    intent.putExtra("message", name);
                    startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(Singup.this, Login.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(Singup.this, response, Toast.LENGTH_SHORT).show();

                }

            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Singup.this, error.toString(), Toast.LENGTH_SHORT).show();

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
        MySingleton.getmInstance(Singup.this).addToRequestQueue(request);
    }
}
