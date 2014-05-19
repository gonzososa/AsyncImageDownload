package com.gmail.gonzaloantonio.asyncimagedownload;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private static String [] URLS = {
        "https://pbs.twimg.com/media/BcMu4KhIMAAJSqC.jpg",
        "https://pbs.twimg.com/media/Bb9qAagIgAAfSvg.jpg",
        "https://pbs.twimg.com/media/BjBZ9RLIIAEmDJ1.jpg",
        "https://pbs.twimg.com/media/BkovgPLIAAA58ZL.jpg",
        "https://pbs.twimg.com/media/BklXhMBIIAAAlvx.jpg",
        "https://pbs.twimg.com/media/Bkjq2N4IEAE335P.jpg",
        "https://pbs.twimg.com/media/Bka0wlkIIAAn1k8.jpg",
        "https://pbs.twimg.com/media/BkaETdSCQAAfb7G.jpg",
        "https://pbs.twimg.com/media/BkQFryrIcAAiodL.jpg",
        "https://pbs.twimg.com/media/BkOpckpCcAA_70R.jpg",
        "https://pbs.twimg.com/media/BkOcfdJCcAEH0MG.jpg",
        "https://pbs.twimg.com/media/BkEoEADIEAA3obl.jpg",
        "https://pbs.twimg.com/media/Bj_tyNaIIAAqOp7.jpg",
        "https://pbs.twimg.com/media/Bj1dPJqCAAA8m2Y.jpg",
        "https://pbs.twimg.com/media/Bjwes9ICYAA7u-H.png",
        "https://pbs.twimg.com/media/BjwUBCICAAAFS0m.jpg",
        "https://pbs.twimg.com/media/BjrLItoCIAA3r7D.jpg",
        "https://pbs.twimg.com/media/BjqQsrTIEAAUiR7.jpg",
        "https://pbs.twimg.com/media/BjnaKhYIgAA3VXi.jpg",
        "https://pbs.twimg.com/media/BjbHdI-IYAAVE0s.jpg",
        "https://pbs.twimg.com/media/BjH47HoCMAAH6qU.jpg",
        "https://pbs.twimg.com/media/BnPrWI3CEAAIzSP.jpg",
        "https://pbs.twimg.com/media/BnO6BriIcAEkCy5.jpg",
        "https://pbs.twimg.com/media/BnEJAzoCIAAT4ZD.jpg",
        "https://pbs.twimg.com/media/BnCzJNgIUAAIsiJ.jpg",
        "https://pbs.twimg.com/media/Bm4ll80IQAAp9qP.jpg",
        "https://pbs.twimg.com/media/Bm4N-biIAAA1o57.jpg",
        "https://pbs.twimg.com/media/BmXRC6FIIAAXTQG.jpg",
        "https://pbs.twimg.com/media/BmQx40GIIAAF2Dt.jpg",
        "https://pbs.twimg.com/media/BmITsr6IMAA92OS.jpg",
        "https://pbs.twimg.com/media/BmGQZujIAAAdHEi.jpg",
        "https://pbs.twimg.com/media/Bl7VnpLIYAEGVeR.jpg",
        "https://pbs.twimg.com/media/Bltkx8cIgAE9WBq.jpg",
        "https://pbs.twimg.com/media/BlNHyNUIQAASMjF.jpg",
        "https://pbs.twimg.com/media/Bk8ycCLIQAAQ798.jpg",
        "https://pbs.twimg.com/media/Bk5Hq2uCYAAkq5l.jpg",
        "https://pbs.twimg.com/media/BizjLsIIQAA3q5E.jpg",
        "https://pbs.twimg.com/media/Bie1fMFCUAAk6z_.jpg",
        "https://pbs.twimg.com/media/BidYGudIcAAKs9U.jpg",
        "https://pbs.twimg.com/media/BieqLjVIYAE3SsG.jpg",
        "https://pbs.twimg.com/media/BiZKfovCUAA_fTP.jpg",
        "https://pbs.twimg.com/media/BiVgETQIcAAlVKZ.jpg",
        "https://pbs.twimg.com/media/BiTgFHKCQAAY2Yn.jpg",
        "https://pbs.twimg.com/media/BiQ3DaGIMAAWyDg.jpg",
        "https://pbs.twimg.com/media/Bhn06w1IYAAMt0s.jpg",
        "https://pbs.twimg.com/media/Bhh8kV6IIAAiF10.jpg",
        "https://pbs.twimg.com/media/BhdTlm7IEAEQTGD.jpg",
        "https://pbs.twimg.com/media/Bhb7OBUIQAAPyzx.jpg",
        "https://pbs.twimg.com/media/Bha-DtSIgAE7FBu.jpg",
        "https://pbs.twimg.com/media/Bha9GUoIcAEQVN8.jpg",
        "https://pbs.twimg.com/media/BhWzYgsIUAAMe3k.jpg",
        "https://pbs.twimg.com/media/BhQ5VtsIgAAXtOe.jpg",
        "https://pbs.twimg.com/media/BhQs5yGIAAALsw1.jpg",
        "https://pbs.twimg.com/media/BhQgVUNIQAAsfg3.jpg",
        "https://pbs.twimg.com/media/BhNz37HIAAAxIh3.jpg",
        "https://pbs.twimg.com/media/BhNhmgsIEAAdDDG.jpg",
        "https://pbs.twimg.com/media/Bg3PGadCMAAd6pC.jpg",
        "https://pbs.twimg.com/media/BgzYT05IUAAcd14.jpg",
        "https://pbs.twimg.com/media/BgZ0mfzIIAAc4QJ.jpg",
        "https://pbs.twimg.com/media/BgSa1BtIQAE06dw.jpg",
        "https://pbs.twimg.com/media/BgPiAXVIIAAd26L.jpg",
        "https://pbs.twimg.com/media/BgKLYWYIcAAkGy2.jpg",
        "https://pbs.twimg.com/media/Bf4L0APIIAAufb6.jpg",
        "https://pbs.twimg.com/media/Bf1qTHGIgAACFRr.jpg",
        "https://pbs.twimg.com/media/Bf0FZ6kIYAA0tZO.jpg",
        "https://pbs.twimg.com/media/Bfz3sr4IEAAKe74.jpg",
        "https://pbs.twimg.com/media/BfwzcupCIAAIHKt.jpg",
        "https://pbs.twimg.com/media/Bfqo9HLIIAA1xf-.jpg",
        "https://pbs.twimg.com/media/Bfj0MnPIIAELSyA.jpg",
        "https://pbs.twimg.com/media/BfKeuN7CcAAFhl0.jpg",
        "https://pbs.twimg.com/media/BfAjiWBIcAA8TW0.jpg",
        "https://pbs.twimg.com/media/Be8GPggIYAAfv2q.jpg",
        "https://pbs.twimg.com/media/Be1hk6RIMAAPzbP.jpg",
        "https://pbs.twimg.com/media/BetEXxBIUAAjWhG.jpg",
        "https://pbs.twimg.com/media/BehmMOZIUAAexJ2.jpg",
        "https://pbs.twimg.com/media/Beehm99IMAAlPZZ.jpg",
        "https://pbs.twimg.com/media/BeeMw_OCEAAdnLl.jpg",
        "https://pbs.twimg.com/media/BediG8kIYAA2jS8.jpg",
        "https://pbs.twimg.com/media/BeTQExrIQAAkDhb.jpg",
        "https://pbs.twimg.com/media/BeIxny4IMAAXwEl.jpg",
        "https://pbs.twimg.com/media/Bdt4qL5IgAEFNcI.jpg",
        "https://pbs.twimg.com/media/BdRMvasIAAAozmY.jpg",
        "https://pbs.twimg.com/media/BdHcPYRIcAAhrWJ.jpg",
        "https://pbs.twimg.com/media/BdEG0fcIUAAa3h0.png",
        "https://pbs.twimg.com/media/BbdZ6Q7IEAACd9i.jpg",
        "https://pbs.twimg.com/media/BaiS5fuIgAAudPC.jpg",
        "https://pbs.twimg.com/media/BaBeO7LCYAELODZ.jpg",
        "https://pbs.twimg.com/media/BZkAepoCcAA0KxU.jpg",
        "https://pbs.twimg.com/media/BZjqbINCQAAQLHI.jpg",
        "https://pbs.twimg.com/media/BZRdL6YCUAA6K1-.jpg",
        "https://pbs.twimg.com/media/BYzoVkWIYAA1djc.jpg"
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

        imageDownloader.download (URLS [position], (ImageView) view);
        return view;
    }

    public ImageDownloader getImageDownloader () {
        return imageDownloader;
    }

}
