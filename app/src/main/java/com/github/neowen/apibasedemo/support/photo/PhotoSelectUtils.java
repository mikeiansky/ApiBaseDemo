package com.github.neowen.apibasedemo.support.photo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * @date on 2018/9/18
 * @Author Winson
 */
public class PhotoSelectUtils {

    public static final int REQ_ALBUM = 12019;
    public static final int REQ_CAMERA = 12020;
    public static final int REQ_CROP = 12021;

    public interface OnPhotoSelectListener {

        void onPhotoSelect(File file);

    }

    private Activity activity;
    private int outputX;
    private int outputY;
    private OnPhotoSelectListener onPhotoSelectListener;

    public PhotoSelectUtils(Activity activity) {
        this(activity, 300, 300);
    }

    public PhotoSelectUtils(Activity activity, int outputX, int outputY) {
        this.activity = activity;
        float density = activity.getResources().getDisplayMetrics().density;
        outputX = (int) (outputX * density);
        outputY = (int) (outputY * density);
    }

    public void setOnPhotoSelectListener(OnPhotoSelectListener onPhotoSelectListener) {
        this.onPhotoSelectListener = onPhotoSelectListener;
    }

    public void selectFromPhotoAlbum() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(i, REQ_ALBUM);
    }

    public void selectByCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getOutputUri());
        activity.startActivityForResult(intent, REQ_CAMERA);
    }

    private void startPhotoCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", getOutputUri());
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, REQ_CROP);
    }

    private File getOutputFile() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File directory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/Android/data/cache" + activity.getPackageName());
            if (!directory.exists()) {
                directory.mkdirs();
            }
            return new File(directory.getAbsolutePath() + "/avatar.jpg");
        } else {
            return new File(activity.getCacheDir().getAbsolutePath() + File.separator + "avatar.jpg");
        }
    }

    private Uri getOutputUri() {
        return Uri.fromFile(getOutputFile());
    }

    public void deleteSelectCacheFile() {
        File file = getOutputFile();
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * This method must be called on the method [ onActivityResult() ] of activity
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_ALBUM) {
            startPhotoCrop(data.getData());
        } else if (requestCode == REQ_CAMERA) {
            startPhotoCrop(getOutputUri());
        } else if (requestCode == REQ_CROP) {
            if (onPhotoSelectListener != null) {
                onPhotoSelectListener.onPhotoSelect(getOutputFile());
            }
        }
    }


}
