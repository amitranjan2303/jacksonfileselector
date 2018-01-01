package developer.amit.com.jacksonfileselector.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import developer.amit.com.jacksonfileselector.fragments.CustomDialogFragment;
import developer.amit.com.jacksonfileselector.R;
import developer.amit.com.jacksonfileselector.fragments.DataListFrament;
import developer.amit.com.jacksonfileselector.model.DocumentModel;

public class FileSelectorActivity extends AppCompatActivity implements CustomDialogFragment.DialogFinalResultCallBack, DataListFrament.FinalResultCallBack, View.OnClickListener {

    public static final String EXT_KEY = "extKey";
    public static final String FILE_KEY = "file";

    private Button okButton;
    private ArrayList<String> pathList;

    public static void newInstant(Context mContext, int reqCode, ArrayList<String> list) {

        if (list != null) {
            if (!list.isEmpty()) {
                Intent intent = new Intent(mContext, FileSelectorActivity.class);
                intent.putStringArrayListExtra(EXT_KEY, list);
                ((Activity) mContext).startActivityForResult(intent, reqCode);
            } else {
                Toast.makeText(mContext, "Extension List Can't be empty", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(mContext, "Extension List Can't be Null", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_selector);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        okButton = (Button) findViewById(R.id.ok);
        okButton.setOnClickListener(this);
        if (getIntent().getStringArrayListExtra(EXT_KEY) != null) {
            ArrayList<String> extList = getIntent().getStringArrayListExtra(EXT_KEY);
            if (!extList.isEmpty()) {
                if (extList.size() == 1) {
                    //do here for only one extension type
                    showDataList(extList.get(0));
                } else {
                    CustomDialogFragment fragment = CustomDialogFragment.newInstance(extList);
                    FragmentManager fm = getSupportFragmentManager();
                    fragment.setCancelable(false);
                    fragment.show(fm, "DialogFragment");
                }
            } else {
                Toast.makeText(this, "Extension List Can't be null", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void getDialogFinalResult(String type) {
        showDataList(type);
    }

    private void showDataList(String type) {
        DataListFrament fragment = DataListFrament.newInstance(type);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.frame_container, fragment);
        transaction.commit();
    }

    @Override
    public void getFinalSelectionResult(ArrayList<DocumentModel> pathList) {
        if (pathList != null && !pathList.isEmpty()) {
            this.pathList = new ArrayList<String>();
            for (DocumentModel model : pathList) {
                this.pathList.add(model.getFilePath());
                okButton.setVisibility(View.VISIBLE);
            }
        } else {
            okButton.setVisibility(View.GONE);
        }
    }

    private void gotoHome() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onBackPressed() {
        gotoHome();
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        if (id == R.id.ok) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra(FILE_KEY, pathList);
            setResult(RESULT_OK, intent);
            finish();
        } else if (id == android.R.id.home) {
            gotoHome();
        }

    }
}
