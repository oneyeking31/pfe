package com.example.exam_gestion;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Admin_home_fragment extends Fragment {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.select_section)
    LinearLayout selectSection;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.group)
    LinearLayout group;
    @BindView(R.id.module)
    LinearLayout module;
    @BindView(R.id.room)
    LinearLayout room;
    @BindView(R.id.date)
    LinearLayout date;
    @BindView(R.id.hour)
    LinearLayout hour;
    String select;
    @BindView(R.id.select_level)
    LinearLayout selectLevel;
int duree ;
    public int mYear, mMonth, mDay, mHour, mMinute;
    String dateselected="" ;
    String timeselected="";
    //section//
    private static final String SECTIONS = "http://192.168.1.11/sections.php";
    ArrayList<String> sections = new ArrayList<String>();
    ArrayAdapter sectionadapter;
//********//
    //level//
private static final String LEVELS = "http://192.168.1.11/level.php";
    ArrayList<String> levels = new ArrayList<String>();
    ArrayAdapter leveladapter;
    String levelselected="" ;
    //********//
    //groups//
    private static final String GROUPES = "http://192.168.1.11/groups.php";
    ArrayList<String> groupes = new ArrayList<String>();
    ArrayAdapter groupadapter;
    String sectionselected ="";
    String groupselected="";
//********//
//modules//
private static final String MODULES = "http://192.168.1.11/modules.php";
    ArrayList<String> modules= new ArrayList<String>();
    ArrayAdapter moduleadapter;
    String moduleselected="";
//********//
//modules//
private static final String ROOMS = "http://192.168.1.11/room.php";
    ArrayList<String> rooms= new ArrayList<String>();
    ArrayAdapter roomadapter;
    String roomselected="";
//********//
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view = inflater.inflate(R.layout.admin_home_fragment, container, false);
        ButterKnife.bind(this, view);
        view.getContentDescription();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {

       getlevel();
       getroom();

       button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (levelselected.equals("")||sectionselected.equals("")||groupselected.equals("")||moduleselected.equals("")||dateselected.equals("")||timeselected.equals("")||roomselected.equals("")){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("warning")
                            .setMessage("check that you enter all requierd info ")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }

else {
                    Intent i = new Intent(getActivity(), Prof_list.class);
                    i.putExtra("level", levelselected);
                    i.putExtra("section", sectionselected);
                    i.putExtra("group", groupselected);
                    i.putExtra("module", moduleselected);
                    i.putExtra("date", dateselected);
                    i.putExtra("time", timeselected);
                    i.putExtra("room", roomselected);
                    startActivity(i);
                }

            } });



        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        monthOfYear+=1;
                        mYear = year;
                        mMonth = monthOfYear;
                        mDay =dayOfMonth;
                        dateselected = year+"-"+monthOfYear+"-"+dayOfMonth;

                        Toast.makeText(getActivity(),   dateselected, Toast.LENGTH_LONG).show();
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }    });

        hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final  Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                    mHour = hourOfDay;
                                  mMinute = minute;
                                 timeselected =hourOfDay+":"+minute;
                                Toast.makeText(getActivity(),   timeselected, Toast.LENGTH_LONG).show();

                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();





            }
        });
        selectLevel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               CharSequence[] cs = levels.toArray(new CharSequence[levels.size()]);
               AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
               mBuilder.setTitle("select level");

               mBuilder.setSingleChoiceItems(leveladapter, -1, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int i) {
                       String selectedItem = levels.get(i);
                       levelselected = selectedItem;
                       Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_LONG).show();
                   }
               });

               mBuilder.setPositiveButton("VALID", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Toast.makeText(getActivity(), "done", Toast.LENGTH_LONG).show();
                   }
               });

               mBuilder.create();
               mBuilder.show();
           }


       });
        selectSection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(levelselected.equals("")){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("warning")
                            .setMessage("you should select a level first ")

                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    getsections();
                    sections.clear();
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    mBuilder.setTitle("select section ");

                    mBuilder.setSingleChoiceItems(sectionadapter, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            String selectedItem = sections.get(i);
                            sectionselected = selectedItem;
                            Toast.makeText(getActivity(), selectedItem, Toast.LENGTH_LONG).show();

                        }
                    });
                    mBuilder.setPositiveButton("VALID", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    mBuilder.create();
                    mBuilder.show();
                }
            }


        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(levelselected.equals("")|| sectionselected.equals("")){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("warning")
                            .setMessage("you should select a level and section first ")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    getgroupes();
                    groupes.clear();

                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    mBuilder.setTitle("select group ");

                    mBuilder.setSingleChoiceItems(groupadapter, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            String selectedItem = groupes.get(i);
                            groupselected = selectedItem;


                        }
                    });

                    mBuilder.setPositiveButton("VALID", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    mBuilder.create();
                    mBuilder.show();
                }
            }


        });

        module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(levelselected.equals("")){
                    new AlertDialog.Builder(getActivity())
                            .setTitle("warning")
                            .setMessage("you should select a level first ")

                            // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // Continue with delete operation
                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
                else {
                    getmodule();
                    modules.clear();
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                    mBuilder.setTitle("select module ");

                    mBuilder.setSingleChoiceItems(moduleadapter, -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            String selectedItem = modules.get(i);
                            moduleselected = selectedItem;

                        }
                    });

                    mBuilder.setPositiveButton("VALID", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    mBuilder.create();
                    mBuilder.show();
                }
            }


        });
        room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                mBuilder.setTitle("select room");

                mBuilder.setSingleChoiceItems(roomadapter, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        String selectedItem = rooms.get(i);
                        roomselected = selectedItem;


                    }
                });

                mBuilder.setPositiveButton("VALID", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "done", Toast.LENGTH_LONG).show();

                    }
                });

                mBuilder.create();
                mBuilder.show();
            }


        });





        super.onViewCreated(view, savedInstanceState);
    }

    public void getsections() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, SECTIONS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("tagconvertstr", "["+response+"]");
                    JSONArray sectionsarray = new JSONArray(response);
                    for (int i = 0; i < sectionsarray.length(); i++) {
                        JSONObject sectionobject = sectionsarray.getJSONObject(i);
                        String num = sectionobject.getString("num");
                       sections.add(num);
                    }

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
            String level =levelselected;


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("levelname", level);
                return param;
            }

        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
        sectionadapter = new ArrayAdapter<String>(
                getActivity(), // Context
                android.R.layout.simple_list_item_single_choice, // Layout
                sections // List
        );
    }

    public void getlevel(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LEVELS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray levelarray= new JSONArray(response);
                    for (int i = 0; i < levelarray.length(); i++) {
                        JSONObject levelobject = levelarray.getJSONObject(i);
                        String name = levelobject.getString("name");
                        levels.add(name);
                    }

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
       leveladapter = new ArrayAdapter<String>(
                getActivity(), // Context
                android.R.layout.select_dialog_singlechoice, // Layout
               levels // List
        );
    }

    public void getgroupes() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GROUPES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray groupssarray = new JSONArray(response);
                    for (int i = 0; i <groupssarray.length(); i++) {
                        JSONObject groupobject = groupssarray.getJSONObject(i);
                        String num = groupobject.getString("groupnum");
                        groupes.add(num);
                        Toast.makeText(getActivity(), groupes.get(i), Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }){
            String sectionname =sectionselected;
            String levelname =levelselected;

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("sectionname", sectionname);
                param.put("levelname", levelselected);
                return param;
            }

        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
       groupadapter = new ArrayAdapter<String>(
                getActivity(), // Context
                android.R.layout.simple_list_item_single_choice, // Layout
                groupes // List
        );
    }
    public void getmodule() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, MODULES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray modulesarray = new JSONArray(response);
                    for (int i = 0; i < modulesarray.length(); i++) {
                        JSONObject moduleobject = modulesarray.getJSONObject(i);
                        String name = moduleobject.getString("name");
                        modules.add(name);
                    }

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
            String level =levelselected;
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("levelname", level);
                return param;
            }

        };
        Volley.newRequestQueue(getActivity()).add(stringRequest);
       moduleadapter = new ArrayAdapter<String>(
                getActivity(), // Context
                android.R.layout.simple_list_item_single_choice, // Layout
              modules // List
        );
    }
    public void getroom(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ROOMS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray roomsarray= new JSONArray(response);
                    for (int i = 0; i < roomsarray.length(); i++) {
                        JSONObject roomobject = roomsarray.getJSONObject(i);
                        String id = roomobject.getString("id");
                      rooms.add(id);
                    }

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
        roomadapter = new ArrayAdapter<String>(
                getActivity(), // Context
                android.R.layout.select_dialog_singlechoice, // Layout
                rooms// List
        );
    }


}



