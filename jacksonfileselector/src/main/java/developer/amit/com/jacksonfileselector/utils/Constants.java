package developer.amit.com.jacksonfileselector.utils;

/**
 * Created by v3mobi on 31/12/17.
 */

public class Constants {
    private static boolean MULTIPLE_SELECTION=false;

    public static boolean isMultipleSelection() {
        return MULTIPLE_SELECTION;
    }

    public static void setMultipleSelection(boolean multipleSelection) {
        MULTIPLE_SELECTION = multipleSelection;
    }
}
