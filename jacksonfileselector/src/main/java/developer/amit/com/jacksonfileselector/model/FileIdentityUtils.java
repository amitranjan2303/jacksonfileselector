package developer.amit.com.jacksonfileselector.model;

import android.text.TextUtils;

/**
 * Created by v3mobi on 7/12/17.
 */

public class FileIdentityUtils {

    private static boolean contains(String[] types, String ext) {

        ext=verifyInput(ext);
        if (!TextUtils.isEmpty(ext)) {
            for (String string : types) {
                if (ext.equalsIgnoreCase(string)) return true;
            }
        }


        return false;
    }

    public static String verifyInput(String ext) {
        //remove spaces
        ext = ext.replaceAll(" ", "");
        //remove special charecters
        ext = ext.replaceAll("[-+.^:,@\n&!#$%*()]", "");
        //remove numbers
        ext = ext.replaceAll("[^A-Za-z]", "");

        return ext;
    }

    public static boolean isImageIFile(String path) {
        String[] types = {"jpg", "jpeg", "png"};
        return FileIdentityUtils.contains(types, path);
    }

    public static boolean isAudioFile(String path) {
        String[] types = {"mp3"};
        return FileIdentityUtils.contains(types, path);
    }

    public static boolean isVideoFile(String path) {
        String[] types = {"mp4", "mov", "wmv", "flv", "avi"};
        return FileIdentityUtils.contains(types, path);
    }

    public static boolean isExcelFile(String path) {
        String[] types = {"xls", "xlsx"};
        return FileIdentityUtils.contains(types, path);
    }

    public static boolean isDocFile(String path) {
        String[] types = {"doc", "docx", "dot", "dotx"};
        return FileIdentityUtils.contains(types, path);
    }

    public static boolean isPPTFile(String path) {
        String[] types = {"ppt", "pptx"};
        return FileIdentityUtils.contains(types, path);
    }

    public static boolean isPDFFile(String path) {
        String[] types = {"pdf"};
        return FileIdentityUtils.contains(types, path);
    }

    public static boolean isTxtFile(String path) {
        String[] types = {"txt"};
        return FileIdentityUtils.contains(types, path);
    }
}
