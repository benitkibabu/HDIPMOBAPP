package com.example.benitkibabu.retrofitexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.benitkibabu.retrofitexample.models.Data;
import com.example.benitkibabu.retrofitexample.models.Label;
import com.example.benitkibabu.retrofitexample.models.SearchResult;
import com.example.benitkibabu.retrofitexample.services.APIService;
import com.example.benitkibabu.retrofitexample.services.RetrofitClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView title, descritpion;
    ImageView imageView;

    APIService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        title = (TextView) findViewById(R.id.title);
        descritpion = (TextView) findViewById(R.id.description);
        imageView = (ImageView) findViewById(R.id.image);

        service = RetrofitClient.getClient("http://api.brewerydb.com/v2/").create(APIService.class);

        getRandomBeer();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomBeer();
            }
        });
    }

    void getRandomBeer(){
        service.getRandomBeer(getString(R.string.api_key), "Y")
                .enqueue(new Callback<SearchResult>() {
                    @Override
                    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                        if(response.isSuccessful()){
                            Data data = response.body().getData();
                            Label labels = data.getLabel();

                            title.setText(data.getName());

                            if(data.getDescription() == null || data.getDescription().isEmpty())
                                descritpion.setText("No Description");
                            else
                                descritpion.setText(data.getDescription());

                            Picasso.with(MainActivity.this)
                                    .load(labels.getLarge())
                                    .placeholder(R.drawable.progress_animation)
                                    .resize(1024, 1024)
                                    .into(imageView);

                        }else{
                            Snackbar.make(title, "API Call was un-succeful", Snackbar.LENGTH_LONG)
                                    .show();
                        }

                    }

                    @Override
                    public void onFailure(Call<SearchResult> call, Throwable t) {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRandomBeer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
