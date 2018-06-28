package com.example.eduard.mindummy;

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
