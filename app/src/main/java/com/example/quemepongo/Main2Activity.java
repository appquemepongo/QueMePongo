package com.example.quemepongo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quemepongo.data.Channel;
import com.example.quemepongo.data.Item;
import com.example.quemepongo.service.WeatherServiceCallBack;
import com.example.quemepongo.service.YahooWeatherService;

public class Main2Activity extends AppCompatActivity implements WeatherServiceCallBack {

    private ImageView imgClima;
    private TextView txtTemperatura;
    private TextView txtLocalidad;
    private TextView txtViento;

    private YahooWeatherService servicio;
    private ProgressDialog dialogProgreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ImageButton quemepongo  = (ImageButton) findViewById(R.id.btn_quemepongo);

        imgClima = (ImageView)findViewById(R.id.imgClima);
        txtLocalidad = (TextView)findViewById(R.id.txtCiudad);
        txtTemperatura = (TextView)findViewById(R.id.txtTemperatura);
        txtViento = (TextView)findViewById(R.id.txtViento);

        servicio = new YahooWeatherService(this);

        dialogProgreso = new ProgressDialog(this);
        dialogProgreso.setMessage("Cargando...");
        dialogProgreso.show();

        servicio.refreshWeather("Austin, TX");

        Bundle extras = getIntent().getExtras();

        final String xgenero = extras.getString("gender");
        String xciudad = extras.getString("city");

        quemepongo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //aca le paso el dato al chino del genero
                Intent intent = new Intent(getApplicationContext(), outfitActivity.class);
                intent.putExtra("gender", xgenero);
                startActivity(intent);
            }
        });
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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

    @Override
    public void ServiceSuccess(Channel chanell) {
        dialogProgreso.hide();

        Item items = chanell.getItems();
        int resourceId = getResources().getIdentifier("drawable/icon_" + items.getCondition().getCode(), null, getPackageName());

        @SuppressWarnings("deprecation")
        Drawable weatherIconDrawble = getResources().getDrawable(resourceId);

        imgClima.setImageDrawable(weatherIconDrawble);

        txtTemperatura.setText(items.getCondition().getTemperature() + "\u00B0" + chanell.getUnits().getTemperature());
        txtViento.setText(items.getCondition().getDescription());
        txtLocalidad.setText(servicio.getLocation());

    }

    @Override
    public void ServiceFailture(Exception e) {
        dialogProgreso.hide();
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
