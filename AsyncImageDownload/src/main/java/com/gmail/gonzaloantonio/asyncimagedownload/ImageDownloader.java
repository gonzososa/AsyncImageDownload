package com.gmail.gonzaloantonio.asyncimagedownload;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class ImageDownloader {
    private static final String LOG_TAG = "ImageDownloader";
    public enum Mode {NO_ASYNC_TASK, NO_DOWNLOADED_DRAWABLE, CORRECT}
    private Mode mode = Mode.NO_ASYNC_TASK;

    public void download (String url, ImageView imageView) {
        resetPurgeTimer ();
        Bitmap bitmap = getBitmapFromCache (url);

        if (bitmap == null) {
            forceDownload (url, imageView);
        } else {
            cancelPotentialDownload (url, imageView);
            imageView.setImageBitmap (bitmap);
        }
    }

    private void forceDownload (String url, ImageView imageView) {
        if (url == null) {
            imageView.setImageBitmap (null);
            return;
        }

        if (cancelPotentialDownload (url, imageView)) {
            switch (mode) {
                case NO_ASYNC_TASK:
                    Bitmap bitmap = donwloadBitmap (url);
                    addBitmapToCache (url, bitmap);
                    imageView.setImageBitmap (bitmap);
                    break;
                case NO_DOWNLOADED_DRAWABLE:
                    imageView.setMinimumHeight (156);
                    BitmapDownloaderTask task = new BitmapDownloaderTask (imageView);
                    task.execute (url);
                    break;
                case CORRECT:
                    task = new BitmapDownloaderTask (imageView);
                    DownloadedDrawable downloadedDrawable = new DownloadedDrawable (task);
                    imageView.setImageDrawable (downloadedDrawable);
                    imageView.setMinimumHeight (156);
                    task.execute (url);
                    break;
            }
        }
    }

    private static boolean cancelPotentialDownload (String url, ImageView imageView) {
        BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask (imageView);

        if (bitmapDownloaderTask != null) {
            String bitmapUrl = bitmapDownloaderTask.url;
            if ((bitmapUrl == null) || (bitmapUrl.equals (url))) {
                bitmapDownloaderTask.cancel (true);
            } else {
                return false;
            }
        }

        return true;
    }

    private static BitmapDownloaderTask getBitmapDownloaderTask (ImageView imageView) {
        if (imageView != null) {
            Drawable drawable = imageView.getDrawable ();
            if (drawable instanceof DownloadedDrawable) {
                DownloadedDrawable downloadDrawable = (DownloadedDrawable) drawable;
                return downloadDrawable.getBitmapDownloaderTask ();
            }
        }
        return null;
    }

    class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private String url;
        private final WeakReference<ImageView> imageViewWeakReference;

        public BitmapDownloaderTask (ImageView imageView) {
            imageViewWeakReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground (String...params) {
            url = params [0];
            return downloadBitmap (url);
        }

        @Override
        protected void onPostExecute (Bitmap bitmap) {
            if (isCancelled ()) {
                bitmap = null;
            }

            addBitmapToCache (url, bitmap);

            if (imageViewWeakReference != null) {
                ImageView imageView = imageViewWeakReference.get ();
                BitmapDownloaderTask bitmapDownloaderTask = getBitmapDownloaderTask (imageView);
                if ((this == bitmapDownloaderTask) || (mode != Mode.CORRECT)) {
                    imageView.setImageBitmap (bitmap);
                }
            }
        }
    }

    static class DownloadedDrawable extends ColorDrawable {
        private final WeakReference<BitmapDownloaderTask> bitmapDownladerTaskWeakReference;

        public DownloadedDrawable (BitmapDownloaderTask bitmapDownladerTask) {
            super (Color.BLACK);
            bitmapDownladerTaskWeakReference = new WeakReference<BitmapDownloaderTask>(bitmapDownladerTask);
        }

        public BitmapDownloaderTask getBitmapDownloaderTask () {
            return bitmapDownladerTaskWeakReference.get ();
        }
    }

}
