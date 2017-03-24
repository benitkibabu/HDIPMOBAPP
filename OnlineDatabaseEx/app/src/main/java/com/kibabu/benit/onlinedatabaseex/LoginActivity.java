package com.kibabu.benit.onlinedatabaseex;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private static final String APIULR = "http://virtualkitchen.gear.host/?tag=login";

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


        login("benitkibabu@gmail.com", "123456");
    }

    void login(final String email, final String password){

        final String tag = "login";

        Uri url = Uri.parse(APIULR)
                .buildUpon()
                .appendQueryParameter("tag", tag)
				.appendQueryParameter("email", email)
				.appendQueryParameter("password", password)
                .build();

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                Log.w("Login Activity", response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Login Activity", error.getMessage());
                    }
                }
        ){
           /* @Override
            protected Map<String, String> getParams() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();
                params.put("tag", tag);
                params.put("email", email);
                params.put("password", password);

                return params;
            }*/

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError{
                Map<String, String> params = new HashMap<>();

                return params;
            }
        };


        AppController.getInstance().addToRequestQueue(request, tag);

    }

}
