package com.folliedimomi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.folliedimomi.R;
import com.folliedimomi.model.Gallery;


public class BannerAdapter extends PagerAdapter {
    Context context;
    Gallery gallery;
    LayoutInflater layoutInflater;
    BannerListener myBannerListener;

    ImageView imgImage;


    public BannerAdapter(Context context, Gallery gallery /*Uri images[]*/, BannerListener myBannerListener) {
        this.context = context;
        this.gallery = gallery;
        this.myBannerListener = myBannerListener;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return /*images.length*/ gallery.getImages() == null ? 0 : gallery.getImages().size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.banner, container, false);

        imgImage = (ImageView) itemView.findViewById(R.id.imageView);
        //imgImage.setImageURI(images[position]);
        Glide.with(context).load(gallery.getImages().get(position)).into(imgImage);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    public interface BannerListener {
        void leftClick(int pos);

        void rightClick(int pos);
    }
}