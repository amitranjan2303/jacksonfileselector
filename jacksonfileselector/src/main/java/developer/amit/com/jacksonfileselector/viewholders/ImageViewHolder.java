package developer.amit.com.jacksonfileselector.viewholders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.ArrayList;

import developer.amit.com.jacksonfileselector.R;
import developer.amit.com.jacksonfileselector.adapters.DataListAdapter;
import developer.amit.com.jacksonfileselector.callbacks.DataResultCallBack;
import developer.amit.com.jacksonfileselector.model.DocumentModel;
import developer.amit.com.jacksonfileselector.utils.Constants;

/**
 * Created by v3mobi on 9/12/17.
 */

public class ImageViewHolder extends BaseViewHolder implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private DataListAdapter adapter;
    private CheckBox checkBox;
    private ImageView imageView;
    private FrameLayout layout;
    private RelativeLayout container;
    private DocumentModel documentModel;
    private ArrayList<DocumentModel> model = new ArrayList<DocumentModel>();

    public ImageViewHolder(View itemView, DataListAdapter adapter) {
        super(itemView);
        this.adapter = adapter;
        container = itemView.findViewById(R.id.container_both);
        checkBox = itemView.findViewById(R.id.cb_image);
        imageView = itemView.findViewById(R.id.iv_image);
        layout = itemView.findViewById(R.id.view_container);

        container.setOnClickListener(this);
        //checkBox.setOnClickListener(this);
        checkBox.setOnCheckedChangeListener(this);
        imageView.setOnClickListener(this);
        layout.setOnClickListener(this);

        this.adapter = adapter;
    }

    @Override
    public void bind(Object object, int position) {
        documentModel = (DocumentModel) object;
        if (!Constants.isMultipleSelection()) {
            checkBox.setChecked(adapter.selectedPosition == getAdapterPosition());
        }

        if (documentModel.getCurThumb() != null) {
            imageView.setImageBitmap(documentModel.getCurThumb());
            container.setVisibility(View.VISIBLE);
        } else {
            container.setVisibility(View.GONE);
        }

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
            adapter.getCallBack().getMultipleSelectionResult(b, documentModel);
        }
    }
}
