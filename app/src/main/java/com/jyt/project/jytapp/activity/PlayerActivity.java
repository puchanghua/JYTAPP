package com.jyt.project.jytapp.activity;

import android.Manifest;

import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import com.jyt.project.jytapp.bean.ADInfo;
import com.jyt.project.jytapp.util.CommonConstants;
import com.jyt.project.jytapp.view.ImageCycleView;
import com.jyt.project.jytapp.view.CustomVideoView;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;


/**
 * Created by pch on 2017/3/20.
 */

public class PlayerActivity extends BaseActivity implements OnCompletionListener,OnErrorListener{
    private CustomVideoView videoView;
    private WebView webView;
    private ImageCycleView ad_view;
    private File ad;
    private int imagesLength;
    private Vector<String> images;
    private ProgressBar pgb;
    private int currentVideoIndex = 0;//当前视频下标
    private int currentImagesIndex = 0;//当前图片下标
    private int cycleTimes;//图片播放的次数
    private int current;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_ad_drawer);
        initFindViewById();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{ Manifest.permission. WRITE_EXTERNAL_STORAGE }, 1);
        } else {
            initData();
        }
        initEvent();
       // playImages(images,imagesLegth);
       playVideo(ad.getPath());
    }

    private void initFindViewById(){
        videoView = (CustomVideoView) findViewById(R.id.pv_video);
        webView = (WebView) findViewById(R.id.wv_ttc);
        ad_view=(ImageCycleView) findViewById(R.id.ad_view);
        pgb = (ProgressBar)findViewById(R.id.pro_bar);
    }

    private void initEvent(){
        videoView.setOnCompletionListener(this);
        videoView.setOnErrorListener(this);

    }
    private void initData(){
        ad = new File(Environment.getExternalStorageDirectory(),"1.mp4");
        imagesLength = 3;
        images = new Vector<String>();
        images.add(CommonConstants.IMAGE_PATH+"1.jpg");
        images.add(CommonConstants.IMAGE_PATH+"2.jpg");
        images.add(CommonConstants.IMAGE_PATH+"3.jpg");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.ttc2000.com");
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if(newProgress==100){
                    pgb.setVisibility(View.GONE);//加载完网页进度条消失
                }
                else{
                    pgb.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    pgb.setProgress(newProgress);//设置进度值
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initData();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    private void handlePlay(){

    }

    //播放视频
    private void playVideo(String path){
        ad_view.setVisibility(View.INVISIBLE);
        videoView.setVideoPath(path);
        videoView.setVisibility(View.VISIBLE);
        videoView.start();
    }

    //循环播放图片
    private void playImages(Vector<String> images, int imagesLength){
        ArrayList<ADInfo> infos = new ArrayList<ADInfo>();
        for (int i = 0; i < imagesLength; i++) {
            ADInfo info = new ADInfo();
            info.setId("" + i);
            info.setUrl("file://" + images.get(i));
            infos.add(info);
        }
        ad_view.setImageResources(infos, mAdCycleViewListener);
        ad_view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
 /*       currentVideoIndex++;
        ad = new File(Environment.getExternalStorageDirectory(),"1.mp4");
        playVideo(ad.getPath());*/

        playImages(images,imagesLength);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }



    @Override
    public void onPause() {
        super.onPause();
        current=videoView.getCurrentPosition();
        videoView.suspend();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoView.resume();
        videoView.seekTo(current);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView!=null)videoView.stopPlayback();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) ) {
            if( webView.canGoBack()){
                webView.goBack(); //返回WebView的上一页面
                return true;
            }
        }
        return true;

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
            cycleTimes++;
            if (cycleTimes == 3) {
                cycleTimes = 0;
                playVideo(ad.getPath());
            }
        }
    };
}
