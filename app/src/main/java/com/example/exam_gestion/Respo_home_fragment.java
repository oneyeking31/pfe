package com.example.exam_gestion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Respo_home_fragment extends Fragment {
    String level;
    String section;
    String group;
    String module;
    String date;
    String time;
    String room;
    long duree;
    String guard1;
    String guard2;
    String ready1;
    String ready2;
    int id;
    @BindView(R.id.level)
    TextView levels;
    @BindView(R.id.module)
    TextView modules;
    @BindView(R.id.section)
    TextView sections;
    @BindView(R.id.groups)
    TextView groups;
    @BindView(R.id.room)
    TextView rooms;
    @BindView(R.id.date)
    TextView dates;
    @BindView(R.id.time)
    TextView times;
    @BindView(R.id.duration)
    TextView duration;
    @BindView(R.id.settime)
    Button settime;
    @BindView(R.id.startexam)
    Button startexam;
    @BindView(R.id.checkbutton)
    Button checkbutton;
    @BindView(R.id.itemslayout)
    LinearLayout itemslayout;
    @BindView(R.id.examtext)
    TextView examtext;
    @BindView(R.id.checktext)
    TextView checktext;
    @BindView(R.id.mainlayout)
    LinearLayout mainlayout;
    @BindView(R.id.buttonlayout)
    LinearLayout buttonlayout;
    int counter;
    private long START_TIME_IN_MILLIS = 600000;
    int test =0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.respo_home_activity, container, false);
        view.getContentDescription();
        ButterKnife.bind(this, view);
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



        checkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent info = new Intent(getActivity(),Admin_presence.class);
                info.putExtra("ID",id);
                startActivity(info);
            }
        });

        settime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("set duration ");
                final EditText input = new EditText(getActivity());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setRawInputType(Configuration.KEYBOARD_12KEY);
                alert.setView(input);
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        int duree = Integer.valueOf(input.getText().toString());
                        setTime(String.valueOf(duree));
                        dialog.dismiss();
                        getexam();


                    }
                });
               final AlertDialog dialog = alert.create();
                dialog.show();
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                    input.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            if (TextUtils.isEmpty(s)) {

                                ((AlertDialog) dialog).getButton(
                                        AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                            } else {
                                ((AlertDialog) dialog).getButton(
                                        AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                            }
                        }
                    });


            }
        });
        startexam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter = 0;
                if (!guard1.equals("") && !guard2.equals("")) {
                    if (ready1.equals("1") && ready2.equals("1")) {
                        if (test==0)
                        new CountDownTimer(duree*60000,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                counter++;
                                if (counter==10){
                                    Uri alarmSound =
                                            RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION );
                                    MediaPlayer mp = MediaPlayer. create (getActivity().getBaseContext(), alarmSound);
                                    mp.start();
                                  checkbutton.setEnabled(true);
                                  test=1;

                                }
                            }
                            @Override
                            public void onFinish() {

                                Uri alarmSound =
                                        RingtoneManager. getDefaultUri (RingtoneManager. TYPE_NOTIFICATION );
                                MediaPlayer mp = MediaPlayer. create (getActivity().getBaseContext(), alarmSound);
                                mp.start();
                                endexam();

                            }
                        }.start();
                        checkready();



                    } else if (!ready1.equals("1") && ready2.equals("1")) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("warning")
                                .setMessage("guard 1 is not ready do you want start exam anyway ? ")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        checkready();

                                    }
                                })

                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();

                    } else if (!ready2.equals("1") && ready1.equals("1")) {

                        new AlertDialog.Builder(getActivity())
                                .setTitle("warning")
                                .setMessage("guard 2 is not ready do you want start exam anyway ? ")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        checkready();
                                        if(duree==0){

                                        }
                                    }
                                })

                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();


                    } else if (ready2.equals("0") && ready1.equals("0")) {
                        new AlertDialog.Builder(getActivity())
                                .setTitle("warning")
                                .setMessage("guards are not ready  ")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })

                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }


            }
        });

    }

    public void getexam() {
        String getexam_url = "http://192.168.1.11/getexamforrespo.php";

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
                    duree = exambject.getLong("duration");
                    guard1 = exambject.getString("guard1");
                    guard2 = exambject.getString("guard2");
                    ready1 = exambject.getString("ready1");
                    ready2 = exambject.getString("ready2");
                    levels.setText(level);
                    sections.setText(section);
                    modules.setText(module);
                    rooms.setText(room);
                    dates.setText(date);
                    times.setText(time);
                    groups.setText(group);
                    if (duree==0) {
                        duration.setText("not set yet");
                        duration.setTextColor(Color.RED);
                    } else {
                        duration.setText(String.valueOf(duree )+ "min");
                        duration.setTextColor(Color.parseColor("#03DAC5"));
                    }


                } catch (JSONException e) {

                    itemslayout.setVisibility(View.GONE);
                    buttonlayout.setVisibility(View.GONE);
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

    public void setTime(final String duree) {
        String URL_insertexam = "http://192.168.1.11/settime.php";
        Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_SHORT).show();
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
                param.put("name", String.valueOf(id));
                param.put("duree", duree);
                return param;

            }


        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getActivity()).addToRequestQueue(request);


    }

    public void endexam() {
        String URL_insertexam = "http://192.168.1.11/end.php";
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
                return param;
            }


        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getActivity()).addToRequestQueue(request);


    }
    public void checkready() {
        String URL_insertexam = "http://192.168.1.11/respoready.php";
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
                return param;
            }


        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(getActivity()).addToRequestQueue(request);


    }

}

