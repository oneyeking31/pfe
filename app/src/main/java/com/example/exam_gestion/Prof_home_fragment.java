package com.example.exam_gestion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Prof_home_fragment extends Fragment {


    @BindView(R.id.timerbutton)
    Button timerbutton;
    @BindView(R.id.checkbutton)
    Button checkbutton;
    String level;
    String section;
    String group;
    String module;
    String date;
    String time;
    String room;
    String duree;
    String guard1;
    String guard2;
    String ready3;
    int id;
    @BindView(R.id.level)
    TextView levels;
    @BindView(R.id.module)
    TextView modules;
    @BindView(R.id.section)
    TextView sections;
    @BindView(R.id.room)
    TextView rooms;
    @BindView(R.id.time)
    TextView times;
    @BindView(R.id.groups)
    TextView groups;
    @BindView(R.id.date)
    TextView dates;
    @BindView(R.id.duration)
    TextView duration;
    int i = 0;
    @BindView(R.id.duree)
    Button dureebutton;
    @BindView(R.id.examtext)
    TextView examtext;
    @BindView(R.id.itemslayout)
    LinearLayout itemslayout;
    @BindView(R.id.checktext)
    TextView checktext;
    @BindView(R.id.buttonlayout)
    LinearLayout buttonlayout;
    @BindView(R.id.mainlayout)
    LinearLayout mainlayout;
    int groupeid ;
    int counter;
    int check = 0 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prof_home_fragment, container, false);
        ButterKnife.bind(this, view);
        view.getContentDescription();
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            getexam();
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 2000); //execute in every 10 minutes
        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            getexam();
                        } catch (Exception e) {
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 2000); //execute in every 10 minutes
        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Cheking_presence.class);
                intent.putExtra("groupeid",groupeid);
                intent.putExtra("examid",id);
                startActivity(intent);

            }
        });
        timerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                Intent intent = getActivity().getIntent();
                String name = intent.getStringExtra("name");

                if (name.equals(guard1)) {
                    ready("ready1");
                } else if (name.equals(guard2)) {
                    ready("ready2");
                }
            }
        });


        dureebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long  time = Long.valueOf(duree)*1000;
                counter =0;
                if (ready3.equals("1")) {
                    if (check==0)
                    new CountDownTimer(200000,1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            counter++;
                            if (counter==10){
                                Uri alarmSound =
                                        RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION );
                                MediaPlayer mp = MediaPlayer. create (getActivity().getBaseContext(), alarmSound);
                                mp.start();
                                checkbutton.setEnabled(true);
                                getgroupeid();
                                check=1;
                            }
                        }
                        @Override
                        public void onFinish() {
                            Uri alarmSound =
                                    RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION );
                            MediaPlayer mp = MediaPlayer. create (getActivity().getBaseContext(), alarmSound);
                            mp.start();
                        }
                    }.start();
                    Intent totimer = new Intent(getActivity(), com.example.exam_gestion.Timer.class);
                    totimer.putExtra("time",duree);
                    startActivity(totimer);


                } else {

                    new AlertDialog.Builder(getActivity())
                            .setTitle("warning")
                            .setMessage("the Responasble professor didnt lunch the exam")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })

                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }

            }
        });

    }

    public void getexam() {
        String getexam_url = "http://192.168.1.11/getexam.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getexam_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {

                    JSONArray exams = new JSONArray(response);
                    JSONObject exambject = exams.getJSONObject(0);
                    id = exambject.getInt("id");
                    level = exambject.getString("level");
                    section = exambject.getString("section");
                    group = exambject.getString("groupe");
                    module = exambject.getString("module");
                    date = exambject.getString("date");
                    room = exambject.getString("room");
                    time = exambject.getString("time");
                    duree = exambject.getString("duration");
                    guard1 = exambject.getString("guard1");
                    guard2 = exambject.getString("guard2");
                    ready3 = exambject.getString("ready3");

                    levels.setText(level);
                    sections.setText(section);
                    modules.setText(module);
                    rooms.setText(room);
                    dates.setText(date);
                    times.setText(time);
                    groups.setText(group);
                    if (duree.equals("0")) {
                        duration.setText("not set yet");
                        duration.setTextColor(Color.RED);
                    } else {
                        duration.setText(duree + "min");
                        duration.setTextColor(Color.parseColor("#03DAC5"));
                    }

                } catch (JSONException e) {
                    buttonlayout.setVisibility(View.GONE);
                    itemslayout.setVisibility(View.GONE);
                    mainlayout.setBackgroundResource(R.drawable.simple_border);
                    examtext.setText("No exam for you  please contact an adminstrator  if you have any problems ");
                    examtext.setTextSize(25);

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
                param.put("guard", name);
                return param;

            }

        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }

    public void ready(final String ready) {

        String URL_insertexam = "http://192.168.1.11/profready.php";
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
                param.put("id", String.valueOf(id));
                param.put("ready", ready);
                return param;
            }


        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getActivity()).addToRequestQueue(request);
    }


    public void getgroupeid (){
        final String groupeidurl = "http://192.168.1.11/getgroupeid.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, groupeidurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray sectionsarray = new JSONArray(response);
                        JSONObject sectionobject = sectionsarray.getJSONObject(0);

                    groupeid = sectionobject.getInt("id");


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
                param.put("levelname", level);
                param.put("section", section);
                param.put("groupe", group);

                return param;
            }

        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);

    }


}
