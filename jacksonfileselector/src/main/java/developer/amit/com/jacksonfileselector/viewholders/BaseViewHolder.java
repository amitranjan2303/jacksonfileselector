package developer.amit.com.jacksonfileselector.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by v3mobi on 9/12/17.
 */

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bind(Object object, int position);
}
