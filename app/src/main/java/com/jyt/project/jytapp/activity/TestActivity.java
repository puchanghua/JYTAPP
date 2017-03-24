package com.jyt.project.jytapp.activity;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import com.jyt.project.jytapp.bean.ADInfo;
import com.jyt.project.jytapp.util.CommonConstants;
import com.jyt.project.jytapp.view.ImageCycleView;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by pch on 2017/3/21.
 */

public class TestActivity extends BaseActivity {
    private ImageCycleView ad_view;
    private int imagesLegth;
    private Vector<String> images;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ad_view = (ImageCycleView)findViewById(R.id.ad_view);
        imagesLegth = 3;
        images = new Vector<String>();
        images.add(CommonConstants.IMAGE_PATH+"1.jpg");
        images.add(CommonConstants.IMAGE_PATH+"2.jpg");
        images.add(CommonConstants.IMAGE_PATH+"3.jpg");
        playImages(images,imagesLegth);
    }



    //循环播放图片
    private void playImages(Vector<String> images, int imagesLegth){
        ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
        for (int i = 0; i < imagesLegth; i++) {
            ADInfo info = new ADInfo();
            info.setId("" + i);
            info.setUrl("file://" + images.get(i));
            infos.add(info);
        }
        ad_view.setImageResources(infos, mAdCycleViewListener);
        ad_view.setVisibility(View.VISIBLE);
    }

    private ImageCycleView.ImageCycleViewListener mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener() {

        @Override
        public void onImageClick(ADInfo info, int position, View imageView) {

        }

        @Override
        public void displayImage(String imageURL, ImageView imageView, int position) {
            ImageLoader.getInstance().displayImage(imageURL, imageView);// 使用ImageLoader对图片进行加装！
        }

        @Override
        public void onPlayFinish() {
            //  handlePlay();
         //   cycleTimes++;
        }
    };
}
