package developer.amit.com.jacksonfileselector.utils;

public class Constants {
    private static boolean MULTIPLE_SELECTION=false;

    public static boolean isMultipleSelection() {
        return MULTIPLE_SELECTION;
    }

    public static void setMultipleSelection(boolean multipleSelection) {
        MULTIPLE_SELECTION = multipleSelection;
    }
}
