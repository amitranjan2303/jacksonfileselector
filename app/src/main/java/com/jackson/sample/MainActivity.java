package com.jackson.sample;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import developer.amit.com.jacksonfileselector.activity.FileSelectorActivity;
import developer.amit.com.jacksonfileselector.utils.Constants;

import static developer.amit.com.jacksonfileselector.activity.FileSelectorActivity.FILE_KEY;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private int CHOOSE_FILE_REQUESTCODE = 111;
    public static final int RequestPermissionCode = 7;
    Button btnUpload;
    TextView txtOne;

    private ArrayList<String> finalFilePath = new ArrayList<String>();

    private ArrayList<String> fileType = new ArrayList<String>();
    private String[] permission = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
            //  Manifest.permission.MEDIA_CONTENT_CONTROL
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //get component id
        btnUpload = findViewById(R.id.btn_select);
        txtOne = findViewById(R.id.txt_mgs);

        fileType.add(".jpg");
        fileType.add(".doc");
        //Constants.setMultipleSelection(true);
        //set click listener
        btnUpload.setOnClickListener(this);
    }

    private void selectFile(Context context, int reqcode) {
        if (isPermitted(permission)) {
            FileSelectorActivity.newInstant(context, reqcode, fileType);
        }
        // If permission is not enabled then else condition will execute.
        else {
            //Calling method to enable permission.
            requestPermissions(RequestPermissionCode, permission, "accept permission for file operation");

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111 && resultCode == RESULT_OK) {
            if (data != null) {
                ArrayList<String> list = data.getStringArrayListExtra(FILE_KEY);
                txtOne.setText("File Selected Count " + list.size());
                finalFilePath.addAll(list);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        boolean isPermitted = isPermitted(permissions);
        if (isPermitted) {
            FileSelectorActivity.newInstant(getApplicationContext(), CHOOSE_FILE_REQUESTCODE, fileType);
        } else {
            Toast.makeText(getApplicationContext(), "permission denied", Toast.LENGTH_LONG).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public boolean isPermitted(String[] permissions) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
                for (String permission : permissions) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                        return false;
                    }
                }
            }
            return true;
        } catch (Exception e) {
            Log.d("JacksonSelector ", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void requestPermissions(final int requestCode, final String[] permissions, String msg) {
        showMessageOKCancel(msg, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_POSITIVE) {
                    ActivityCompat.requestPermissions(MainActivity.this, permissions, requestCode);
                } else if (which == DialogInterface.BUTTON_NEGATIVE) {
                    int[] grantResults = new int[permissions.length];
                    Arrays.fill(grantResults, PackageManager.PERMISSION_DENIED);
                    onRequestPermissionsResult(requestCode, permissions, grantResults);
                }
            }
        });
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("Ok", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_select) {
            selectFile(view.getContext(), 111);
        }
    }
}
