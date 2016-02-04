package com.developer.artisla.lessonfirstweek;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.developer.artisla.lessonfirstweek.model.Company;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        showCompanyList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent addCompany = new Intent(MainActivity.this, AddCompany.class);
                startActivity(addCompany);

            }
        });
    }

    private void showCompanyList() {

        CompanyReaderDbHelper db = new CompanyReaderDbHelper(this);
        List<Company> companies = db.getAllCompanies();

        mAdapter = new CustomAdapter(companies);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();
        showCompanyList();
    }

    private void sortByDate() {


        CompanyReaderDbHelper db = new CompanyReaderDbHelper(this);
        List<Company> companies = db.getAllCompaniesByCreated_At();
        mAdapter = new CustomAdapter(companies);
        mRecyclerView.setAdapter(mAdapter);


    }

    private void sortByTitle() {
        CompanyReaderDbHelper db = new CompanyReaderDbHelper(this);
        List<Company> companies = db.getAllCompaniesByTitle();
        mAdapter = new CustomAdapter(companies);
        mRecyclerView.setAdapter(mAdapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case R.id.sort_by_date:
                sortByDate();
                break;
            case R.id.sort_by_title:
                sortByTitle();
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
