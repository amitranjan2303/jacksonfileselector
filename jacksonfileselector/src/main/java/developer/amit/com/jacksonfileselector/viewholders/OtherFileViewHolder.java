package developer.amit.com.jacksonfileselector.viewholders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import developer.amit.com.jacksonfileselector.R;
import developer.amit.com.jacksonfileselector.adapters.DataListAdapter;
import developer.amit.com.jacksonfileselector.model.DocumentModel;
import developer.amit.com.jacksonfileselector.utils.Constants;

/**
 * Created by v3mobi on 9/12/17.
 */

public class OtherFileViewHolder extends BaseViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private CheckBox checkBox;
    private ImageView imageView;
    private TextView textView;
    private LinearLayout container;
    private DataListAdapter adapter;
    private DocumentModel documentModel;
    private ArrayList<DocumentModel> model = new ArrayList<DocumentModel>();

    public OtherFileViewHolder(View itemView, DataListAdapter adapter) {
        super(itemView);
        checkBox = itemView.findViewById(R.id.cb_box);
        textView = itemView.findViewById(R.id.tv_text);
        imageView = itemView.findViewById(R.id.iv_image);
        container = itemView.findViewById(R.id.container);

        container.setOnClickListener(this);
        textView.setOnClickListener(this);
        imageView.setOnClickListener(this);
        // checkBox.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(this);
        this.adapter = adapter;
    }

    @Override
    public void bind(Object object, int postion) {
        if (!Constants.isMultipleSelection()) {
            checkBox.setChecked(adapter.selectedPosition == getAdapterPosition());
        }
        DocumentModel model = (DocumentModel) object;
        textView.setText(model.getFileName());
        imageView.setImageResource(R.drawable.ic_file);
        documentModel = model;
    }


    @Override
    public void onClick(View view) {

        if (!Constants.isMultipleSelection()) {
            model.add(documentModel);
            adapter.selectedPosition = getAdapterPosition();
            adapter.notifyDataSetChanged();
            adapter.getCallBack().getSingleSelectionResult(model);
        } else {
            if (checkBox.isChecked()) {
                checkBox.setChecked(false);
            } else {
                checkBox.setChecked(true);
            }
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (Constants.isMultipleSelection()) {
            if (b) {
                model.add(documentModel);
            } else {
                model.remove(documentModel);
            }
            adapter.getCallBack().getMultipleSelectionResult(b, documentModel);
        }
    }
}
