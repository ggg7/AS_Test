package com.example.as_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText ucid, password, fname, lname, email;
    Button register, show;
    TextView show_result;
    RequestQueue requestQueue;
    String insertUrl = "https://web.njit.edu/~ggg7/AS_Demo/AS_InsertInto.php";
    String showUrl = "https://web.njit.edu/~ggg7/AS_Demo/AS_ShowStudents.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ucid = (EditText) findViewById(R.id.UCID);
        password = (EditText) findViewById(R.id.password);
        fname = (EditText) findViewById(R.id.fname);
        lname = (EditText) findViewById(R.id.lname);
        email = (EditText) findViewById(R.id.email);
        register = (Button) findViewById(R.id.register);
        show = (Button) findViewById(R.id.show);
        show_result = (TextView) findViewById(R.id.show_result);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                        showUrl, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONArray students = response.getJSONArray("students");
                            for(int i = 0; i < students.length(); i++)
                            {
                                JSONObject student = students.getJSONObject(i);

                                String ucid = student.getString("ucid");
                                String password = student.getString("password");
                                String fname = student.getString("fname");
                                String lname = student.getString("lname");
                                String email = student.getString("email");

                                show_result.append(ucid+" "+password+" "+fname+" "+lname+" "+email+"\n");
                            }
                            show_result.append("===\n");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
                requestQueue.add(jsonObjectRequest);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest request = new StringRequest(Request.Method.POST, insertUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> parameters = new HashMap<String, String>();
                        parameters.put("ucid", ucid.getText().toString());
                        parameters.put("password", password.getText().toString());
                        parameters.put("fname", fname.getText().toString());
                        parameters.put("lname", lname.getText().toString());
                        parameters.put("email", email.getText().toString());

                        return parameters;
                    }
                };

                requestQueue.add(request);
            }
        });
    }
}
