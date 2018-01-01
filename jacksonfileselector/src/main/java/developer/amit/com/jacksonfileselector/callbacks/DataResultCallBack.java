package developer.amit.com.jacksonfileselector.callbacks;

import java.util.ArrayList;

import developer.amit.com.jacksonfileselector.model.DocumentModel;

public interface DataResultCallBack {

    void getMultipleSelectionResult(boolean status,DocumentModel model);
    void getSingleSelectionResult(ArrayList<DocumentModel> model);
}
