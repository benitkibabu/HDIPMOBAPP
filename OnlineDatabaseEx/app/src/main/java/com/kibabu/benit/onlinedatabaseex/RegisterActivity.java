package com.kibabu.benit.onlinedatabaseex;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.kibabu.benit.onlinedatabaseex.app.AppController;
import com.kibabu.benit.onlinedatabaseex.helper.AppPreference;
import com.kibabu.benit.onlinedatabaseex.helper.DbHelper;
import com.kibabu.benit.onlinedatabaseex.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String APIULR = "http://virtualkitchen.gear.host/";
    Button login, register;
    EditText name, username, email, password;


    //User Model
    User u;

    DbHelper db;

    AppPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        login = (Button) findViewById(R.id.login_button);
        register = (Button ) findViewById(R.id.register_button);

        email = (EditText) findViewById(R.id.email);
        username = (EditText) findViewById(R.id.username);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);

        db = new DbHelper(this);
        pref  = new AppPreference(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u = new User(name.getText().toString(),username.getText().toString(),
                        email.getText().toString(), password.getText().toString());

                try {
                    RegisterUser(u);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    void RegisterUser(final User user) throws JSONException {
        final String TAG = "register";

        StringRequest request = new StringRequest(Request.Method.POST,APIULR,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.w("RESPONSE", response.toString());
                try {
                    JSONObject obj = new JSONObject(response);
                    boolean error = obj.getBoolean("error");
                    if(error == false){
                        JSONObject data = obj.getJSONObject("data");
                        User u = new User(data.getInt("id"),
                                data.getString("name"),
                                data.getString("username"),
                                data.getString("email"),
                                data.getString("password"));

                        long id = db.setUser(u);
                        if(id != 0){
                            pref.setKeyIsLogin("isLogin", true);
                            Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> param = new HashMap<>();
                param.put("tag", TAG);
                param.put("name",user.getName());
                param.put("username",user.getUsername() );
                param.put("email",user.getEmail() );
                param.put("password", user.getPassword());


                return param;
            }
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                return header;
            }
        };

        AppController.getInstance().addToRequestQueue(request, TAG);
    }
}
