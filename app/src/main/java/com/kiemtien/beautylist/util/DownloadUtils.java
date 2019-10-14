package com.kiemtien.beautylist.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class DownloadUtils {

    public interface DownloadingCallback {
        void onFinished();

        void onFailed();
    }

    private static DownloadingCallback mCallback;
    private static DownloadBitmapCallback mDownloadBitmapCallback;

    public static void download(String url, String name, String storageDest, DownloadingCallback callback) {
        mCallback = callback;
        String[] urls = new String[3];
        urls[0] = url;
        urls[1] = name;
        urls[2] = storageDest;
        new DownloadTask().execute(urls);
    }

    private static boolean checkFolderAndMakingIfNotExist(String path) {
        File folder = new File(path);
        boolean success = true;
        if (!folder.exists()) {
            success = folder.mkdirs();
        }
        return success;
    }

    private static String getFormatImageFromUrl(String url) {
        if (url == null || url.equals("")) {
            return "png";
        }
        StringBuilder format = new StringBuilder();
        for (int i = url.length() - 1; i >= 0; i--) {
            if (!String.valueOf(url.charAt(i)).equals(".")) {
                format.insert(0, url.charAt(i));
            } else {
                break;
            }
        }
        return format.toString();
    }

    private static String checkName(String name) {
        if (name.length() > 40) {
            return name.substring(0, 40);
        }
        return name;
    }

    private static class DownloadTask extends AsyncTask<String, Integer, Void> {
        private boolean success = false;

        protected Void doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                InputStream input = url.openStream();
                try {
                    File storagePath = Environment.getExternalStorageDirectory();
                    String fileName = String.format("%s.%s", checkName(params[1]), getFormatImageFromUrl(params[0]));
                    if (checkFolderAndMakingIfNotExist(storagePath + File.separator + params[2])) {
                        OutputStream output = new FileOutputStream(storagePath + File.separator + params[2]
                                + File.separator + fileName);
                        try {
                            byte[] buffer = new byte[2048];
                            int bytesRead = 0;
                            while ((bytesRead = input.read(buffer, 0, buffer.length)) >= 0) {
                                output.write(buffer, 0, bytesRead);
                            }
                            success = true;
                        } finally {
                            output.close();
                        }
                    }
                } finally {
                    input.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... progress) {
            //Yet to code
        }

        @Override
        protected void onPostExecute(Void result) {
            if (mCallback != null) {
                if (success) {
                    mCallback.onFinished();
                } else {
                    mCallback.onFailed();
                }
            }
        }
    }

    public static void downloadImageToBitmap(String url, DownloadBitmapCallback callback) {
        mDownloadBitmapCallback = callback;
        new DownloadImgTask().execute(url);
    }

    private static class DownloadImgTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bm = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bm = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bm;
        }

        protected void onPostExecute(Bitmap result) {
            if (mDownloadBitmapCallback != null) {
                if (result != null)
                    mDownloadBitmapCallback.onSuccess(result);
                else
                    mDownloadBitmapCallback.onFailed();
            }
        }
    }

    public interface DownloadBitmapCallback {
        void onSuccess(Bitmap bitmap);

        void onFailed();
    }
}
