package com.panda.solar.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.panda.solar.activities.R;

/**
 * Created by macosx on 28/01/2019.
 */

public class GlideImageHandler implements RequestListener<Drawable> {

    @Override
    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
        return false;
    }

    @Override
    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
        return false;
    }

    public interface GlideLoaderCallback{
        void onSuccess();
        void onFailure();
    }

    private int width;
    private int height;

    private Uri uri;
    private ImageView imageView;
    private Context context;
    private GlideLoaderCallback glideLoaderCallback;

    public GlideImageHandler(Context context, Uri uri, ImageView imageView){

        this.imageView = imageView;
        this.uri = uri;
        this.context = context;
    }

    public void load(int width, int height, GlideLoaderCallback glideLoaderCallback){
        this.width = width;
        this.height = height;
        this.glideLoaderCallback = glideLoaderCallback;
        glideLoadImageViewUri(uri);


    }

    private void glideLoadImageViewUri(Uri uri){

        if(context != null){
            GlideApp.with(context).load(uri)
                    .transforms(new CenterCrop(), new RoundedCorners(8))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(new RequestOptions().
                            diskCacheStrategy(DiskCacheStrategy.NONE).
                            override(width, height).skipMemoryCache(true)
                    )
                    .into(target);
        }

    }

    private BaseTarget<Drawable> target = new BaseTarget<Drawable>() {
        @Override
        public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
            imageView.setImageDrawable(drawable);
            glideLoaderCallback.onSuccess();

        }

        @Override
        public void getSize(@NonNull SizeReadyCallback cb) {
            cb.onSizeReady(width, height);
        }

        @Override
        public void onLoadFailed(@Nullable Drawable errorDrawable) {
            super.onLoadFailed(errorDrawable);
            glideLoaderCallback.onFailure();
        }

        @Override
        public void removeCallback(@NonNull SizeReadyCallback cb) {
        }

        @Override
        public void onStop() {
            super.onStop();

        }
    };

}
