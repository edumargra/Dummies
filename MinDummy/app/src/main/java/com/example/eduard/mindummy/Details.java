package com.example.eduard.mindummy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Details extends AppCompatActivity {

    private EditText name;
    private EditText val1;
    private EditText val2;
    private RadioGroup options1;
    private RadioGroup options2;
    private DataFacade dataFacade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        dataFacade = DataFacade.getInstance();

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

        Button button = findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String val3;
                String val4;
                switch (options1.getCheckedRadioButtonId()){
                    case R.id.opt1:
                        val3 = "opt1";
                        break;
                    case  R.id.opt2:
                        val3 = "opt2";
                        break;
                    case R.id.opt3:
                        val3 = "opt3";
                        break;
                    default:
                        val3 = "";
                }
                switch (options2.getCheckedRadioButtonId()){
                    case R.id.opt4:
                        val4 = "opt4";
                        break;
                    case R.id.opt5:
                        val4 = "opt5";
                        break;
                    default:
                        val4 = "";
                }
                if (!val3.equals("") && !val4.equals("")) {
                    if(getIntent().hasExtra("id"))
                        dataFacade.delete(getIntent().getIntExtra("id",0));
                    dataFacade.addThing(name.getText().toString(), val1.getText().toString(), val2.getText().toString(), val3,val4);
                    ((Activity)view.getContext()).finish();
                }else{
                    Toast.makeText(view.getContext(),"Select the options dummy",(int)5);
                }
            }
        });
        //mirem si estem obrint un antic
        Intent intent = getIntent();
        if(intent.hasExtra("id"))
            fillFields(intent.getIntExtra("id",0));

    }

    private void fillFields(int id) {
        Thing thing = dataFacade.getThing(id);
        name.setText(thing.getName());
        val1.setText(thing.getVal1());
        val2.setText(thing.getVal2());
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
