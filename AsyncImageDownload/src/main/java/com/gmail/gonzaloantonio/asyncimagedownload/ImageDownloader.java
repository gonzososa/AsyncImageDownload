package com.gmail.gonzaloantonio.asyncimagedownload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ConcurrentHashMap;

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
                    Bitmap bitmap = downloadBitmap(url);
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

    Bitmap downloadBitmap (String url) {
        final int IO_BUFFER_SIZE = 1024 * 4;
        final HttpClient client  = (mode == Mode.NO_ASYNC_TASK ? new DefaultHttpClient () : AndroidHttpClient.newInstance ("Android"));
        final HttpGet getRequest  = new HttpGet (url);

        try {
            HttpResponse response = client.execute (getRequest);
            final int statusCode = response.getStatusLine().getStatusCode ();

            if (statusCode != HttpStatus.SC_OK) {
                Log.w ("ImageDownloader", "Error " + statusCode + " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity ();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent ();
                    return scaleBitmap (200, 200, BitmapFactory.decodeStream (new FlushedInputStream (inputStream)));
                } finally {
                    if (inputStream != null) {
                        inputStream.close ();
                    }
                    entity.consumeContent ();
                }
            }
        } catch (IOException e) {
            getRequest.abort ();
            Log.w (LOG_TAG, "I/O error while retrieving bitmap from " + url, e);
        } catch (IllegalStateException e) {
            getRequest.abort ();
            Log.w (LOG_TAG, "Incorrect URL: " + url);
        } catch (Exception e) {
            getRequest.abort ();
            Log.w (LOG_TAG, "Error while retrieving bitmap from " + url, e);
        } finally {
            if (client instanceof AndroidHttpClient) {
                ((AndroidHttpClient) client).close ();
            }
        }

        return null;
    }

    private Bitmap scaleBitmap (int dWidth, int dHeight, Bitmap src) {
        int originalWidth = src.getWidth ();
        int originalHeight = src.getHeight ();

        if (originalWidth > originalHeight) {
            dHeight = (int) (((double) originalHeight / (double) originalWidth) * dHeight);
        } else if (originalHeight > originalWidth) {
            dWidth = (int) (((double) originalWidth /(double) originalHeight) * dWidth);
        }

        return Bitmap.createScaledBitmap (src, dWidth, dHeight, true);
    }

    static class FlushedInputStream extends FilterInputStream {
        public FlushedInputStream (InputStream inputStream) {
            super (inputStream);
        }

        @Override
        public long skip (long n) throws IOException {
            long totalBytesSkipped = 0L;

            while (totalBytesSkipped < n) {
                long bytesSkipped = in.skip (n - totalBytesSkipped);
                if (bytesSkipped == 0L) {
                    int b = read ();
                    if (b < 0) {
                        break;
                    } else {
                        bytesSkipped = 1;
                    }
                }
                totalBytesSkipped += bytesSkipped;
            }

            return totalBytesSkipped;
        }
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

    public void setMode (Mode mode) {
        this.mode = mode;
        clearCache ();
    }

    //Cache stuff

    private static final int HARD_CACHE_CAPACITY = 10;
    private static final int DELAY_BEFORE_PURGE = 10 * 1000;

    private final HashMap<String, Bitmap> sHardBitmapCache =
        new LinkedHashMap<String, Bitmap>(HARD_CACHE_CAPACITY / 2, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry (LinkedHashMap.Entry<String, Bitmap> eldest) {
                if (size () > HARD_CACHE_CAPACITY) {
                    sSoftBitmapCache.put (eldest.getKey (), new SoftReference<Bitmap>(eldest.getValue ()));
                    return true;
                } else {
                    return false;
                }
            }
        };

    private final ConcurrentHashMap<String, SoftReference<Bitmap>> sSoftBitmapCache =
            new ConcurrentHashMap<String, SoftReference<Bitmap>>(HARD_CACHE_CAPACITY / 2);

    private final Handler purgeHandler = new Handler ();

    private final Runnable purger = new Runnable() {
        @Override
        public void run() {
            clearCache ();
        }
    };

    private void addBitmapToCache (String url, Bitmap bitmap) {
        if (bitmap != null) {
            synchronized (sHardBitmapCache) {
                sHardBitmapCache.put (url, bitmap);
            }
        }
    }

    private Bitmap getBitmapFromCache (String url) {
        synchronized (sHardBitmapCache) {
            final Bitmap bitmap = sHardBitmapCache.get (url);
            if (bitmap != null) {
                sHardBitmapCache.remove (url);
                sHardBitmapCache.put (url, bitmap);
                return bitmap;
            }
        }

        SoftReference<Bitmap> bitmapSoftReference = sSoftBitmapCache.get (url);
        if (bitmapSoftReference != null) {
            final Bitmap bitmap = bitmapSoftReference.get ();
            if (bitmap != null) {
                return bitmap;
            } else {
                sSoftBitmapCache.remove (url);
            }
        }

        return null;
    }

    public void clearCache () {
        sHardBitmapCache.clear ();
        sSoftBitmapCache.clear ();
    }

    private void resetPurgeTimer () {
        purgeHandler.removeCallbacks (purger);
        purgeHandler.postDelayed (purger, DELAY_BEFORE_PURGE);
    }
}
