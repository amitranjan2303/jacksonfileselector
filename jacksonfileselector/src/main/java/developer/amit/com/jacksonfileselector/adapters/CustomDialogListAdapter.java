package developer.amit.com.jacksonfileselector.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import developer.amit.com.jacksonfileselector.R;

/**
 * Created by v3mobi on 3/12/17.
 */

public class CustomDialogListAdapter extends RecyclerView.Adapter<CustomDialogListAdapter.CustomDialogViewHolder> {

    private ArrayList<String> items;
    protected int selectedPosition = -1;
    private CustomDialogSelectionCallBack rvDataCallBack;

    public interface CustomDialogSelectionCallBack {
        void dialogSelectedOption(String type);
    }

    public CustomDialogListAdapter(CustomDialogSelectionCallBack context, ArrayList<String> items) {

        this.items = items;
        this.rvDataCallBack = context;
    }

    @Override
    public CustomDialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dialog_item, parent, false);
        CustomDialogViewHolder myViewHolder = new CustomDialogViewHolder(view, this);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomDialogViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class CustomDialogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textView;
        public CheckBox checkBox;
        private RelativeLayout container;
        private CustomDialogListAdapter adapter;

        public CustomDialogViewHolder(View itemView, CustomDialogListAdapter adapter) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.dialog_tv);
            checkBox = (CheckBox) itemView.findViewById(R.id.dialog_cb);
            container = (RelativeLayout) itemView.findViewById(R.id.container_view);
            container.setOnClickListener(this);
            checkBox.setOnClickListener(this);
            this.adapter = adapter;
        }

        public void bind(int pos) {
            checkBox.setChecked(adapter.selectedPosition == getAdapterPosition());
            textView.setText(items.get(pos));
        }

        @Override
        public void onClick(View view) {
            rvDataCallBack.dialogSelectedOption(items.get(getAdapterPosition()));
            adapter.selectedPosition = getAdapterPosition();

            adapter.notifyDataSetChanged();
        }
    }
}
