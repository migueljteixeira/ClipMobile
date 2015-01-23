package com.migueljteixeira.clipmobile.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.migueljteixeira.clipmobile.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class InfoMapFragment extends BaseFragment {

    @InjectView(R.id.map) ImageView mImageView;
    private PhotoViewAttacher mAttacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.inject(this, view);

        showProgressSpinnerOnly(true);
        
        Picasso.with(getActivity())
                .load(R.drawable.map_campus)
                .into(mImageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        showProgressSpinnerOnly(false);
                    }

                    @Override
                    public void onError() {
                        //
                    }
                });

        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setScaleType(ImageView.ScaleType.FIT_CENTER);
        
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        
        mAttacher.cleanup();
    }
}