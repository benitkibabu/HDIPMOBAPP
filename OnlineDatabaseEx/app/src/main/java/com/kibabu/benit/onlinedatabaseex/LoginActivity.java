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
import com.android.volley.toolbox.StringRequest;
import com.kibabu.benit.onlinedatabaseex.app.AppController;
import com.kibabu.benit.onlinedatabaseex.helper.AppPreference;
import com.kibabu.benit.onlinedatabaseex.helper.DbHelper;
import com.kibabu.benit.onlinedatabaseex.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String APIULR = "http://virtualkitchen.gear.host/";

    Button login, register;
    EditText email, password;


    //User Model
    User u;

    DbHelper db;

    AppPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.login_button);
        register = (Button ) findViewById(R.id.register_button);

        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);

        db = new DbHelper(this);
        pref  = new AppPreference(this);

        if(pref.getIsLogin() == true){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = email.getText().toString();
                String p = password.getText().toString();
                login(e, p);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    void login(final String email, final String password){

        final String tag = "login";

        Uri url = Uri.parse(APIULR)
                .buildUpon()
                .appendQueryParameter("tag", tag)
				.appendQueryParameter("email", email)
				.appendQueryParameter("password", password)
                .build();

        Log.e("NEW_APIURL", url.toString());

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                Log.w("Login Activity", response);

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
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Login Activity", error.getMessage());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(request, tag);

    }

}
