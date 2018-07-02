package com.example.eduard.mindummy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public class Details extends AppCompatActivity {

    private EditText name;
    private EditText val1;
    private EditText val2;
    private EditText lat;
    private EditText lng;
    private RadioGroup options1;
    private RadioGroup options2;
    private DataFacade dataFacade;

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        dataFacade = DataFacade.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //setting toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarDetails);
        myToolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.editText_name);
        val1 = findViewById(R.id.editText_val1);
        val2 = findViewById(R.id.editText_val2);
        options1 = findViewById(R.id.options1);
        options2 = findViewById(R.id.options2);
        lat = findViewById(R.id.editText_lat);
        lng = findViewById(R.id.editText_lng);

        Button button = findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val3;
                String val4;
                switch (options1.getCheckedRadioButtonId()) {
                    case R.id.opt1:
                        val3 = "opt1";
                        break;
                    case R.id.opt2:
                        val3 = "opt2";
                        break;
                    case R.id.opt3:
                        val3 = "opt3";
                        break;
                    default:
                        val3 = "";
                }
                switch (options2.getCheckedRadioButtonId()) {
                    case R.id.opt4:
                        val4 = "opt4";
                        break;
                    case R.id.opt5:
                        val4 = "opt5";
                        break;
                    default:
                        val4 = "";
                }
                float dist = distance(view);
                if (!val3.equals("") && !val4.equals("") && !lat.getText().toString().equals("") && !lng.getText().toString().equals("") && dist != -1) {
                    if (getIntent().hasExtra("id"))
                        dataFacade.delete(getIntent().getIntExtra("id", 0));
                    dataFacade.addThing(name.getText().toString(), val1.getText().toString(), val2.getText().toString(), val3,val4, Double.parseDouble(lat.getText().toString()), Double.parseDouble(lng.getText().toString()), dist);
                    ((Activity)view.getContext()).finish();
                }else{
                    Toast.makeText(view.getContext(),"Select the options dummy",(int)5).show();
                }
            }
        });
        //mirem si estem obrint un antic
        Intent intent = getIntent();
        if(intent.hasExtra("id"))
            fillFields(intent.getIntExtra("id",0));

    }

    private float distance(View view) {
        LocationManager locationManager = (LocationManager) view.getContext().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (ActivityCompat.shouldShowRequestPermissionRationale((Details)view.getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(Details.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            }
        }
        final float[] dist = new float[1];
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Location location1 = new Location("");
                        location1.setLatitude(Double.parseDouble(lat.getText().toString()));
                        location1.setLongitude(Double.parseDouble(lng.getText().toString()));
                        dist[0] = location.distanceTo(location1);
                        if (location != null) {
                            dist[0] = -1;
                        }
                    }
                });
        return dist[0];
    }

    private void fillFields(int id) {
        Thing thing = dataFacade.getThing(id);
        name.setText(thing.getName());
        val1.setText(thing.getVal1());
        val2.setText(thing.getVal2());
        lat.setText(Double.toString(thing.getLat()));
        lng.setText(Double.toString(thing.getLng()));
        switch (thing.getVal3()){
            case "opt1":
                options1.check(R.id.opt1);
                break;
            case "opt2":
                options1.check(R.id.opt2);
                break;
            default:
                options1.check(R.id.opt3);
        }
        switch (thing.getVal4()){
            case "opt4":
                options2.check(R.id.opt4);
                break;
            default:
                options2.check(R.id.opt5);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                this.finish();
                return true;
        }
    }
}
