package com.gmail.gonzaloantonio.asyncimagedownload;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private static String [] URLS = {
        "https://pbs.twimg.com/profile_images/462981550003740672/Jb-UpOux.jpeg",
        "https://pbs.twimg.com/media/BoHECEXIgAEwrDi.jpg:large",
        "https://pbs.twimg.com/media/BnemgXxCIAA-aif.jpg:large",
        "https://pbs.twimg.com/media/BnXGVKbCAAA4LoV.jpg:large",
        "https://pbs.twimg.com/media/BcMu4KhIMAAJSqC.jpg:large",
        "https://pbs.twimg.com/media/Bb9qAagIgAAfSvg.jpg:large",
        "https://pbs.twimg.com/media/BoU_qgLCMAAnAai.jpg:large",
        "https://pbs.twimg.com/media/Bo0_K-2CcAArf_4.jpg:large",
        "https://pbs.twimg.com/media/BjBZ9RLIIAEmDJ1.jpg:large",
        "https://pbs.twimg.com/media/BkovgPLIAAA58ZL.jpg:large",
        "https://pbs.twimg.com/media/BklXhMBIIAAAlvx.jpg:large",
        "https://pbs.twimg.com/media/Bkjq2N4IEAE335P.jpg:large",
        /*"https://pbs.twimg.com/media/Bka0wlkIIAAn1k8.jpg:large",
        "https://pbs.twimg.com/media/BkaETdSCQAAfb7G.jpg:large",
        "https://pbs.twimg.com/media/BkQFryrIcAAiodL.jpg:large",
        "https://pbs.twimg.com/media/BkOpckpCcAA_70R.jpg:large",
        "https://pbs.twimg.com/media/BkOcfdJCcAEH0MG.jpg:large",
        "https://pbs.twimg.com/media/BkEoEADIEAA3obl.jpg:large",
        "https://pbs.twimg.com/media/Bj_tyNaIIAAqOp7.jpg:large",
        "https://pbs.twimg.com/media/Bj1dPJqCAAA8m2Y.jpg:large",
        "https://pbs.twimg.com/media/Bjwes9ICYAA7u-H.png:large",
        "https://pbs.twimg.com/media/BjwUBCICAAAFS0m.jpg:large",
        "https://pbs.twimg.com/media/BjrLItoCIAA3r7D.jpg:large",
        "https://pbs.twimg.com/media/BjqQsrTIEAAUiR7.jpg:large",
        "https://pbs.twimg.com/media/BjnaKhYIgAA3VXi.jpg:large",
        "https://pbs.twimg.com/media/BjbHdI-IYAAVE0s.jpg:large",
        "https://pbs.twimg.com/media/BjH47HoCMAAH6qU.jpg:large",
        "https://pbs.twimg.com/media/BnPrWI3CEAAIzSP.jpg:large",
        "https://pbs.twimg.com/media/BnO6BriIcAEkCy5.jpg:large",
        "https://pbs.twimg.com/media/BnEJAzoCIAAT4ZD.jpg:large",
        "https://pbs.twimg.com/media/BnCzJNgIUAAIsiJ.jpg:large",
        "https://pbs.twimg.com/media/Bm4ll80IQAAp9qP.jpg:large",
        "https://pbs.twimg.com/media/Bm4N-biIAAA1o57.jpg:large",
        "https://pbs.twimg.com/media/BmXRC6FIIAAXTQG.jpg:large",
        "https://pbs.twimg.com/media/BmQx40GIIAAF2Dt.jpg:large",
        "https://pbs.twimg.com/media/BmITsr6IMAA92OS.jpg:large",
        "https://pbs.twimg.com/media/BmGQZujIAAAdHEi.jpg:large",
        "https://pbs.twimg.com/media/Bl7VnpLIYAEGVeR.jpg:large",
        "https://pbs.twimg.com/media/Bltkx8cIgAE9WBq.jpg:large",
        "https://pbs.twimg.com/media/BlNHyNUIQAASMjF.jpg:large",
        "https://pbs.twimg.com/media/Bk8ycCLIQAAQ798.jpg:large",
        "https://pbs.twimg.com/media/Bk5Hq2uCYAAkq5l.jpg:large",
        "https://pbs.twimg.com/media/BizjLsIIQAA3q5E.jpg:large",
        "https://pbs.twimg.com/media/Bie1fMFCUAAk6z_.jpg:large",
        "https://pbs.twimg.com/media/BidYGudIcAAKs9U.jpg:large",
        "https://pbs.twimg.com/media/BieqLjVIYAE3SsG.jpg:large",
        "https://pbs.twimg.com/media/BiZKfovCUAA_fTP.jpg:large",
        "https://pbs.twimg.com/media/BiVgETQIcAAlVKZ.jpg:large",
        "https://pbs.twimg.com/media/BiTgFHKCQAAY2Yn.jpg:large",
        "https://pbs.twimg.com/media/BiQ3DaGIMAAWyDg.jpg:large",
        "https://pbs.twimg.com/media/Bhn06w1IYAAMt0s.jpg:large",
        "https://pbs.twimg.com/media/Bhh8kV6IIAAiF10.jpg:large",
        "https://pbs.twimg.com/media/BhdTlm7IEAEQTGD.jpg:large",
        "https://pbs.twimg.com/media/Bhb7OBUIQAAPyzx.jpg:large",
        "https://pbs.twimg.com/media/Bha-DtSIgAE7FBu.jpg:large",
        "https://pbs.twimg.com/media/Bha9GUoIcAEQVN8.jpg:large",
        "https://pbs.twimg.com/media/BhWzYgsIUAAMe3k.jpg:large",
        "https://pbs.twimg.com/media/BhQ5VtsIgAAXtOe.jpg:large",
        "https://pbs.twimg.com/media/BhQs5yGIAAALsw1.jpg:large",
        "https://pbs.twimg.com/media/BhQgVUNIQAAsfg3.jpg:large",
        "https://pbs.twimg.com/media/BhNz37HIAAAxIh3.jpg:large",
        "https://pbs.twimg.com/media/BhNhmgsIEAAdDDG.jpg:large",
        "https://pbs.twimg.com/media/Bg3PGadCMAAd6pC.jpg:large",
        "https://pbs.twimg.com/media/BgzYT05IUAAcd14.jpg:large",
        "https://pbs.twimg.com/media/BgZ0mfzIIAAc4QJ.jpg:large",
        "https://pbs.twimg.com/media/BgSa1BtIQAE06dw.jpg:large",
        "https://pbs.twimg.com/media/BgPiAXVIIAAd26L.jpg:large",
        "https://pbs.twimg.com/media/BgKLYWYIcAAkGy2.jpg:large",
        "https://pbs.twimg.com/media/Bf4L0APIIAAufb6.jpg:large",
        "https://pbs.twimg.com/media/Bf1qTHGIgAACFRr.jpg:large",
        "https://pbs.twimg.com/media/Bf0FZ6kIYAA0tZO.jpg:large",
        "https://pbs.twimg.com/media/Bfz3sr4IEAAKe74.jpg:large",
        "https://pbs.twimg.com/media/BfwzcupCIAAIHKt.jpg:large",
        "https://pbs.twimg.com/media/Bfqo9HLIIAA1xf-.jpg:large",
        "https://pbs.twimg.com/media/Bfj0MnPIIAELSyA.jpg:large",
        "https://pbs.twimg.com/media/BfKeuN7CcAAFhl0.jpg:large",
        "https://pbs.twimg.com/media/BfAjiWBIcAA8TW0.jpg:large",
        "https://pbs.twimg.com/media/Be8GPggIYAAfv2q.jpg:large",
        "https://pbs.twimg.com/media/Be1hk6RIMAAPzbP.jpg:large",
        "https://pbs.twimg.com/media/BetEXxBIUAAjWhG.jpg:large",
        "https://pbs.twimg.com/media/BehmMOZIUAAexJ2.jpg:large",
        "https://pbs.twimg.com/media/Beehm99IMAAlPZZ.jpg:large",
        "https://pbs.twimg.com/media/BeeMw_OCEAAdnLl.jpg:large",
        "https://pbs.twimg.com/media/BediG8kIYAA2jS8.jpg:large",
        "https://pbs.twimg.com/media/BeTQExrIQAAkDhb.jpg:large",
        "https://pbs.twimg.com/media/BeIxny4IMAAXwEl.jpg:large",
        "https://pbs.twimg.com/media/Bdt4qL5IgAEFNcI.jpg:large",
        "https://pbs.twimg.com/media/BdRMvasIAAAozmY.jpg:large",
        "https://pbs.twimg.com/media/BdHcPYRIcAAhrWJ.jpg:large",
        "https://pbs.twimg.com/media/BdEG0fcIUAAa3h0.png:large",
        "https://pbs.twimg.com/media/BbdZ6Q7IEAACd9i.jpg:large",
        "https://pbs.twimg.com/media/BaiS5fuIgAAudPC.jpg:large",
        "https://pbs.twimg.com/media/BaBeO7LCYAELODZ.jpg:large",
        "https://pbs.twimg.com/media/BZkAepoCcAA0KxU.jpg:large",
        "https://pbs.twimg.com/media/BZjqbINCQAAQLHI.jpg:large",
        "https://pbs.twimg.com/media/BZRdL6YCUAA6K1-.jpg:large",
        "https://pbs.twimg.com/media/BYzoVkWIYAA1djc.jpg:large"*/
    };

    private final ImageDownloader imageDownloader = new ImageDownloader ();

    @Override
    public int getCount () {
        return URLS.length;
    }

    @Override
    public String getItem (int position) {
        return URLS [position];
    }

    @Override
    public long getItemId (int position) {
        return URLS[position].hashCode ();
    }

    @Override
    public View getView (int position, View view, ViewGroup parent) {
        if (view == null) {
            view = new ImageView (parent.getContext ());
            view.setPadding (7, 7, 7, 7);
        }
        Log.i ("JENSELTER", "Downloading " + URLS [position]);
        imageDownloader.download (URLS [position], (ImageView) view);
        return view;

        /*if (view == null) {
            view = new ImageView (parent.getContext());
            view.setPadding (7,7,7,7);
        }
        Log.i ("JENSELTER", "Downloading " + URLS [position]);
        ((ImageView) view).setMinimumHeight (200);
        ((ImageView) view).setImageDrawable (new ColorDrawable(Color.GRAY));

        return view;*/
    }

    public ImageDownloader getImageDownloader () {
        return imageDownloader;
    }
}
