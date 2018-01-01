package developer.amit.com.jacksonfileselector.viewholders;

import android.view.View;
import android.widget.TextView;

import developer.amit.com.jacksonfileselector.R;
import developer.amit.com.jacksonfileselector.model.DocumentModel;

/**
 * Created by v3mobi on 16/12/17.
 */

public class EmptyViewHolder extends BaseViewHolder {
    private TextView textView;

    public EmptyViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.title);
    }

    @Override
    public void bind(Object object, int position) {
        textView.setText(((DocumentModel) object).getFilePath());
    }
}
