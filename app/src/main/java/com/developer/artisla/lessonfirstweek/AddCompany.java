package com.developer.artisla.lessonfirstweek;

import java.io.File;
import java.io.FileNotFoundException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.developer.artisla.lessonfirstweek.model.Company;


public class AddCompany extends AppCompatActivity implements View.OnClickListener {


    private Button addLogoBtn, addCompanyBtn;
    private ImageView logoImv;
    private String realPath;
    private EditText titleEdt, descriptionEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_company);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        titleEdt = (EditText) findViewById(R.id.titleEdt);
        descriptionEdt = (EditText) findViewById(R.id.descriptionEdt);
        addLogoBtn = (Button) findViewById(R.id.addLogoBtn);
        addCompanyBtn = (Button) findViewById(R.id.addCompanyBtn);
        logoImv = (ImageView) findViewById(R.id.logoImv);

        addLogoBtn.setOnClickListener(this);
        addCompanyBtn.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent data) {
        if (resCode == Activity.RESULT_OK && data != null) {

            // SDK < API11
            if (Build.VERSION.SDK_INT < 11)
                realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

                // SDK >= 11 && SDK < 19
            else if (Build.VERSION.SDK_INT < 19)
                realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

                // SDK > 19 (Android 4.4)
            else
                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());


            setTextViews(Build.VERSION.SDK_INT, data.getData().getPath(), realPath);
        }
    }

    private void setTextViews(int sdk, String uriPath, String realPath) {


        Uri uriFromPath = Uri.fromFile(new File(realPath));
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uriFromPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        logoImv.setImageBitmap(bitmap);

        Log.d("HMKCODE", "Build.VERSION.SDK_INT:" + sdk);
        Log.d("HMKCODE", "URI Path:" + uriPath);
        Log.d("HMKCODE", "Real Path: " + realPath);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.addLogoBtn:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
                break;
            case R.id.addCompanyBtn:
                addCompany();
                break;
        }
    }

    private void addCompany() {
        String title, description;

        title = titleEdt.getText().toString();
        description = descriptionEdt.getText().toString();

        Company company = new Company(title, description, realPath);
        CompanyReaderDbHelper db = new CompanyReaderDbHelper(this);

        if (db.addCompany(company) != 0) {
            Toast.makeText(AddCompany.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(AddCompany.this, "Not saved!", Toast.LENGTH_SHORT).show();
        }

    }
}
