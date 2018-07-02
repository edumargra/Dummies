package com.example.eduard.mindummy;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Dock extends AppCompatActivity implements MyItemClickListener {

    private Adapter adapter;
    private DataFacade dataFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dock);

        //setting toolbar
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarMain);
        //myToolbar.setNavigationIcon(R.drawable.ic_back);
        setSupportActionBar(myToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);

        dataFacade = DataFacade.getInstance();
        buildRecycleView();

        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(Dock.this, Details.class));
            }
        });

        final RadioGroup radioGroup = findViewById(R.id.options0);
        Button button = findViewById(R.id.filter);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.opt01:
                        adapter.updateList(dataFacade.getThingsVal3("opt1"));
                        break;
                    case R.id.opt02:
                        adapter.updateList(dataFacade.getThingsVal3("opt2"));
                        break;
                    case R.id.opt03:
                        adapter.updateList(dataFacade.getThingsVal3("opt3"));
                        break;
                    case R.id.opt04:
                        adapter.updateList(dataFacade.getThingsVal4("opt4"));
                        break;
                    case R.id.opt05:
                        adapter.updateList(dataFacade.getThingsVal4("opt5"));
                        break;
                    default:
                        adapter.updateList(dataFacade.getThings());
                }
            }
        });
        Button save = findViewById(R.id.saveOff);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = "myfile";
                FileOutputStream outputStream;
                try {
                    outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                    ObjectOutputStream dout = new ObjectOutputStream(outputStream);
                    dout.writeObject(dataFacade.getThings());
                    dout.flush();
                    outputStream.getFD().sync();
                    outputStream.close();
                } catch (Exception e) {
                    Toast.makeText(view.getContext(),"Error while saving",(int)5).show();
                }
            }
        });
        Button load = findViewById(R.id.loadOff);
        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filename = "myfile";
                FileInputStream inputStream;
                try {
                    inputStream = openFileInput(filename);
                    ObjectInputStream din = new ObjectInputStream(inputStream);
                    dataFacade.setThings((ArrayList<Thing>)din.readObject());
                    din.close();
                    //inputStream.getFD().sync();
                    inputStream.close();
                    adapter.updateList(dataFacade.getThings());
                } catch (Exception e) {
                    Toast.makeText(view.getContext(),"Error while loading",(int)5).show();
                }
            }
        });
        Button map = findViewById(R.id.bMaps);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(),Map.class));
            }
        });
    }

    public void buildRecycleView() {
        //populating the list
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.taskDock_items);
        //layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //adapter
        adapter = new Adapter(dataFacade.getThings());
        adapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(adapter);
        //decorate
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onItemClick(int id) {
        Intent myIntent = new Intent(this, Details.class);
        myIntent.putExtra("id", id);
        startActivity(myIntent);
    }
}
