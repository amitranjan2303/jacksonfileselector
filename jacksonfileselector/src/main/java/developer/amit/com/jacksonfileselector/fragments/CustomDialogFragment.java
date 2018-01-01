package developer.amit.com.jacksonfileselector.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import java.util.ArrayList;

import developer.amit.com.jacksonfileselector.R;
import developer.amit.com.jacksonfileselector.adapters.CustomDialogListAdapter;

public class CustomDialogFragment extends DialogFragment implements View.OnClickListener, CustomDialogListAdapter.CustomDialogSelectionCallBack {

    private static final String EXTENSION_KEY = "extensionKey";
    private ArrayList<String> extensionList;
    private String selectedExtension;
    private DialogFinalResultCallBack callBack;

    //fragmet UI component reffrence
    Button ok;
    Button cancel;
    //call back interface
    public interface DialogFinalResultCallBack {
        void getDialogFinalResult(String type);
    }

    public CustomDialogFragment() {
        // Required empty public constructor
    }

    public static CustomDialogFragment newInstance(ArrayList<String> extension) {
        CustomDialogFragment fragment = new CustomDialogFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(EXTENSION_KEY, extension);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            extensionList = getArguments().getStringArrayList(EXTENSION_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_custom_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.getDialog().setCancelable(false);

        //get UI component refrence
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.dialog_rv);
        ok = (Button) view.findViewById(R.id.choose);
        cancel = (Button) view.findViewById(R.id.cancel);

        //set click listener
        cancel.setOnClickListener(this);

        //setup adapter for show extension list by which user can choose and select extension
        CustomDialogListAdapter adapter = new CustomDialogListAdapter(this, extensionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void dialogSelectedOption(String type) {
        ok.setTextColor(getResources().getColor(R.color.colorPrimary));
        //set click listener when value is selected
        ok.setOnClickListener(this);
        selectedExtension = type;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.choose) {
            if (!TextUtils.isEmpty(selectedExtension)) {
                callBack.getDialogFinalResult(selectedExtension);
                this.getDialog().dismiss();
            }
        } else {
            //this is return to launcher activity
            getActivity().setResult(Activity.RESULT_CANCELED);
            getActivity().finish();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBack = (DialogFinalResultCallBack) context;
    }
}
