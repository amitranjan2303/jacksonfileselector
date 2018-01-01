package developer.amit.com.jacksonfileselector;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.ProgressBar;

import java.io.File;
import java.util.ArrayList;

import developer.amit.com.jacksonfileselector.model.DocumentModel;
import developer.amit.com.jacksonfileselector.model.FileIdentityUtils;
import developer.amit.com.jacksonfileselector.utils.AppConstans;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.MediaColumns.DATA;
import static developer.amit.com.jacksonfileselector.model.FileIdentityUtils.verifyInput;


/**
 * Created by v3mobi on 7/12/17.
 */

public class FilesScanner extends AsyncTask<String, Void, ArrayList<DocumentModel>> {

    private ArrayList<String> pathList;
    private Context context;
    private ServiceResultCallBack callBack;
    private String fileType;
    private ProgressDialog progressDoalog;

    public interface ServiceResultCallBack {
        void serviceResult(ArrayList<DocumentModel> pathList);
    }

    public FilesScanner(Context context, Object Object, ProgressDialog progressDoalog) {
        this.context = context;
        callBack = (ServiceResultCallBack) Object;
        this.progressDoalog = progressDoalog;
        pathList = new ArrayList<String>();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDoalog.setMessage("Please Wait...");
        progressDoalog.show();
    }

    @Override
    protected ArrayList<DocumentModel> doInBackground(String... strings) {
        fileType = verifyInput(strings[0]);
        ArrayList<DocumentModel> documentList = new ArrayList<DocumentModel>();
        if (FileIdentityUtils.isImageIFile(fileType)) {
            getAllImageFile(documentList);

        } else if (FileIdentityUtils.isVideoFile(fileType)) {
            getAllVideoFile(documentList);
        } else if (FileIdentityUtils.isAudioFile(fileType)) {
            getAllAudioFile(documentList);
        } else {
            getAllOtherFile(documentList);
        }
        return documentList;
    }

    @Override
    protected void onPostExecute(ArrayList<DocumentModel> documentModels) {
        progressDoalog.dismiss();
        if (documentModels.isEmpty()) {
            DocumentModel model = DocumentModel.getInstance();
            model.setFileName("Empty File");
            model.setFileType(AppConstans.FILE_TYPE_EMPTY);
            model.setFilePath("Sorry no file found");
            documentModels.add(model);
        }
        callBack.serviceResult(documentModels);
    }

    private void getAllVideoFile(ArrayList<DocumentModel> documentList) {

        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        String[] projection = new String[]{
                MediaStore.Video.Media.BUCKET_ID,
                MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Video.Media.DATA
        };


        String[] selectionArgs = null; // there is no ? in selection so null here

        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;

        String sortOrder = MediaStore.Video.Media.DATE_ADDED + " DESC";

        Cursor cursor = cr.query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int videoId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(DATA));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 1;
                Bitmap curThumb = MediaStore.Video.Thumbnails.getThumbnail(cr, videoId, MediaStore.Video.Thumbnails.MICRO_KIND, options);
                DocumentModel model = DocumentModel.getInstance();
                model.setFileName(title);
                model.setFilePath(path);
                model.setFileType(AppConstans.FILE_TYPE_VIDEO);
                model.setCurThumb(curThumb);
                documentList.add(model);
                callBack.serviceResult(documentList);
            }
        } else {
        }

    }

    private void getAllAudioFile(ArrayList<DocumentModel> documentList) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;


        String[] projection = new String[]{
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.Media.DATA,
        };


        String[] selectionArgs = null; // there is no ? in selection so null here

        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO;

        String sortOrder = MediaStore.Audio.Media.DATE_ADDED + " DESC";

        Cursor cursor = cr.query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int audioId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(DATA));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                DocumentModel model = DocumentModel.getInstance();
                model.setFileName(title);
                model.setFilePath(path);
                model.setFileType(AppConstans.FILE_TYPE_AUDIO);
                documentList.add(model);
                callBack.serviceResult(documentList);
            }
        } else {
        }

    }

    private void getAllImageFile(ArrayList<DocumentModel> documentList) {
        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = new String[]{
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA
        };

        String[] selectionArgs = null; // there is no ? in selection so null here

        String selection = null;
        /*MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;*/

        String sortOrder = MediaStore.Images.Media.DATE_ADDED + " DESC";

        Cursor cursor = cr.query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int imageId = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(DATA));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME));
                File image = new File(path);
                Bitmap bitmap = null;
                if (image.exists())
                    bitmap = BitmapFactory.decodeFile(image.getAbsolutePath());
                DocumentModel model = DocumentModel.getInstance();
                model.setFileName(title);
                model.setFilePath(path);
                model.setFileType(AppConstans.FILE_TYPE_IMAGE);
                model.setCurThumb(bitmap);
                documentList.add(model);
            }
            cursor.close();
        }
    }

    private void getAllOtherFile(ArrayList<DocumentModel> documentList) {

        ContentResolver cr = context.getContentResolver();
        Uri uri = MediaStore.Files.getContentUri("external");

        // every column, although that is huge waste, you probably need
        // BaseColumns.DATA (the path) only.
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.SIZE,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Files.FileColumns.TITLE
        };

        String[] selectionArgs = null; // there is no ? in selection so null here
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "!="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE + " AND "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "!="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO;
        String sortOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC";

        Cursor cursor = cr.query(uri, projection, selection, selectionArgs, sortOrder);

        if (cursor != null) {
            while (cursor.moveToNext()) {

                int fileID = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
                String path = cursor.getString(cursor.getColumnIndexOrThrow(DATA));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE));
                if (path.endsWith("." + fileType)) {

                    DocumentModel model = DocumentModel.getInstance();
                    model.setFileName(title);
                    model.setFilePath(path);
                    model.setFileType(AppConstans.FILE_TYPE_OTHER);
                    documentList.add(model);
                }
            }
        } else {
        }
    }
}
