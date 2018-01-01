package developer.amit.com.jacksonfileselector.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import developer.amit.com.jacksonfileselector.FilesScanner;
import developer.amit.com.jacksonfileselector.R;
import developer.amit.com.jacksonfileselector.adapters.DataListAdapter;
import developer.amit.com.jacksonfileselector.callbacks.DataResultCallBack;
import developer.amit.com.jacksonfileselector.model.DocumentModel;
import developer.amit.com.jacksonfileselector.model.FileIdentityUtils;

public class DataListFrament extends Fragment implements FilesScanner.ServiceResultCallBack, DataResultCallBack {

    private static final String EXTENSION = "fileExt";
    private String extension;
    private RecyclerView recyclerView;
    private View view;
    private FinalResultCallBack callBack;
    private ArrayList<DocumentModel> multipleSelectionList = new ArrayList<DocumentModel>();

    public interface FinalResultCallBack {
        void getFinalSelectionResult(ArrayList<DocumentModel> pathList);
    }

    public DataListFrament() {
        // Required empty public constructor
    }

    public static DataListFrament newInstance(String extension) {
        DataListFrament fragment = new DataListFrament();
        Bundle args = new Bundle();
        args.putString(EXTENSION, extension);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            extension = getArguments().getString(EXTENSION);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_data_list_frament, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_data);
        ProgressDialog progressDoalog = new ProgressDialog(view.getContext());
        FilesScanner scanner = new FilesScanner(view.getContext(), this, progressDoalog);
        scanner.execute(extension);
        return view;
    }

    @Override
    public void serviceResult(ArrayList<DocumentModel> pathList) {

        showDoumentsList(pathList);
    }

    private void showDoumentsList(ArrayList<DocumentModel> pathList) {

        DataListAdapter adapter = new DataListAdapter(this, pathList);
        LinearLayoutManager layoutManager = null;
        if (FileIdentityUtils.isImageIFile(extension)) {
            layoutManager = new GridLayoutManager(getContext(), 3);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            layoutManager = new LinearLayoutManager(view.getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void getMultipleSelectionResult(boolean status, DocumentModel model) {
        if (status) {
            multipleSelectionList.add(model);
        } else {
            multipleSelectionList.remove(model);
        }
        callBack.getFinalSelectionResult(multipleSelectionList);
    }

    @Override
    public void getSingleSelectionResult(ArrayList<DocumentModel> model) {
        callBack.getFinalSelectionResult(model);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBack = (FinalResultCallBack) context;
    }
}
