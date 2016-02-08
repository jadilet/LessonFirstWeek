package com.developer.artisla.lessonfirstweek;

import android.app.Activity;
import android.content.ContentValues;
import android.widget.ImageView;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.developer.artisla.lessonfirstweek.model.Company;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;


public class UpdateCompany extends AppCompatActivity implements View.OnClickListener {

    private EditText titleEdt;
    private EditText descriptionEdt;
    private Button addLogoBtn,updateCompanyBtn;
    private ImageView logoImv;
    private String realPath;
    private Company company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_company);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        company = (Company) getIntent().getSerializableExtra("COMPANY");

        updateCompanyBtn = (Button) findViewById(R.id.updateCompanyBtn);
        titleEdt = (EditText) findViewById(R.id.titleEdt);
        descriptionEdt = (EditText) findViewById(R.id.descriptionEdt);
        addLogoBtn = (Button) findViewById(R.id.addLogoBtn);
        logoImv = (ImageView) findViewById(R.id.logoImv);



        updateCompanyBtn.setOnClickListener(this);
        addLogoBtn.setOnClickListener(this);

        logoImv.setImageURI(Uri.parse(company.getImagePath()));
        titleEdt.setText(company.getTitle());
        descriptionEdt.setText(company.getDescription());



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
        company.setImagePath(realPath);

        Log.d("HMKCODE", "Build.VERSION.SDK_INT:" + sdk);
        Log.d("HMKCODE", "URI Path:" + uriPath);
        Log.d("HMKCODE", "Real Path: " + realPath);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addLogoBtn:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 0);
                break;
            case R.id.updateCompanyBtn:
                updateCompany();


        }


    }

    public void updateCompany(){
        Company company = new Company(this.company.getId(),titleEdt.getText().toString(),descriptionEdt.getText().toString(),this.company.getImagePath());

        CompanyReaderDbHelper db = new CompanyReaderDbHelper(this);
        db.updateCompanyById(company);
        finish();
    }

}
