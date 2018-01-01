package developer.amit.com.jacksonfileselector.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import developer.amit.com.jacksonfileselector.R;
import developer.amit.com.jacksonfileselector.callbacks.DataResultCallBack;
import developer.amit.com.jacksonfileselector.model.DocumentModel;
import developer.amit.com.jacksonfileselector.utils.AppConstans;
import developer.amit.com.jacksonfileselector.viewholders.BaseViewHolder;
import developer.amit.com.jacksonfileselector.viewholders.EmptyViewHolder;
import developer.amit.com.jacksonfileselector.viewholders.ImageViewHolder;
import developer.amit.com.jacksonfileselector.viewholders.OtherFileViewHolder;

public class DataListAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private ArrayList<DocumentModel> pathList;
    public int selectedPosition = -1;
    private DataResultCallBack callBack;

    public DataListAdapter(Object object, ArrayList<DocumentModel> pathList) {
        this.pathList = pathList;
        this.callBack = (DataResultCallBack) object;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        BaseViewHolder holder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(viewType, parent, false);
        if (viewType == R.layout.item_image_data) {
            // create a new viewholder for view
            holder = new ImageViewHolder(v, this);
        } else if (viewType == R.layout.item_other_file_data) {
            holder = new OtherFileViewHolder(v, this);
        } else {
            holder = new EmptyViewHolder(v);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {

        if (holder instanceof ImageViewHolder) {
            ((ImageViewHolder) holder).bind(pathList.get(position), position);
        } else if (holder instanceof OtherFileViewHolder) {
            ((OtherFileViewHolder) holder).bind(pathList.get(position), position);
        } else {
            ((EmptyViewHolder) holder).bind(pathList.get(position), position);
        }

    }

    @Override
    public int getItemCount() {
        return pathList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (pathList.get(position).getFileType().equalsIgnoreCase(AppConstans.FILE_TYPE_IMAGE)) {
            return R.layout.item_image_data;
        } else if (pathList.get(position).getFileType().equalsIgnoreCase(AppConstans.FILE_TYPE_AUDIO)) {
            return R.layout.item_other_file_data;
        } else if (pathList.get(position).getFileType().equalsIgnoreCase(AppConstans.FILE_TYPE_VIDEO)) {
            return R.layout.item_other_file_data;
        } else if (pathList.get(position).getFileType().equalsIgnoreCase(AppConstans.FILE_TYPE_OTHER)) {
            return R.layout.item_other_file_data;
        } else {
            return R.layout.item_empty;
        }

    }

    public DataResultCallBack getCallBack() {
        return callBack;
    }
}
