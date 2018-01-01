package developer.amit.com.jacksonfileselector.model;

import android.graphics.Bitmap;

public class DocumentModel {
    private String fileType;// this variable is only use for view type
    private String fileName;
    private String filePath;
    private Bitmap curThumb;

    private DocumentModel() {
    }

    public static DocumentModel getInstance() {
        return new DocumentModel();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Bitmap getCurThumb() {
        return curThumb;
    }

    public void setCurThumb(Bitmap curThumb) {
        this.curThumb = curThumb;
    }
}
