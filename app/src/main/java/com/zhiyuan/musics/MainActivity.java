package com.zhiyuan.musics;

import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.zhiyuan.musics.model.MusicBean;
import com.zhiyuan.musics.util.JsoupHtml;
import com.zhiyuan.musics.util.OkHttpUtils;
import com.zhiyuan.musics.util.StatusBarUtils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;

import me.wcy.lrcview.LrcView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LrcView lrcView;
    private SeekBar seekBar;
    private Button btnPlayPause;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private Handler handler = new Handler();
    EditText editText;
    SearchView button;
    private List<MusicBean.DataBean.SongListBean> songList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtils.setWindowStatusBarColor(this, Color.BLUE);
        editText = (EditText) findViewById(R.id.editMusic);
        button = (SearchView) findViewById(R.id.findMusic);
        button.setOnClickListener(this);
        lrcView = (LrcView) findViewById(R.id.lrc_view);
        seekBar = (SeekBar) findViewById(R.id.progress_bar);
        btnPlayPause = (Button) findViewById(R.id.btn_play_pause);
        try {
            mediaPlayer.reset();
            AssetFileDescriptor fileDescriptor = getAssets().openFd("jiangnan.mp3");
            mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    seekBar.setMax(mediaPlayer.getDuration());
                    seekBar.setProgress(0);
                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    lrcView.updateTime(0);
                    seekBar.setProgress(0);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        lrcView.loadLrc(getLrcText("jiangnan.lrc"));

        lrcView.setOnPlayClickListener(new LrcView.OnPlayClickListener() {
            @Override
            public boolean onPlayClick(long time) {
                mediaPlayer.seekTo((int) time);
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    handler.post(runnable);
                }
                return true;
            }
        });

        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    handler.post(runnable);
                } else {
                    mediaPlayer.pause();
                    handler.removeCallbacks(runnable);
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                lrcView.updateTime(seekBar.getProgress());
            }
        });
    }

    private String getLrcText(String fileName) {
        String lrcText = null;
        try {
            InputStream is = getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            lrcText = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lrcText;
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mediaPlayer.isPlaying()) {
                long time = mediaPlayer.getCurrentPosition();
                lrcView.updateTime(time);
                seekBar.setProgress((int) time);
            }
            handler.postDelayed(this, 300);
        }
    };

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(runnable);
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.findMusic:{
                final String re = editText.getText().toString();
                if("".equals(re) || TextUtils.isEmpty(re)){
                    Toast.makeText(this,"请输入歌曲",Toast.LENGTH_LONG).show();
                }else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            JsoupHtml jsoupHtml = new JsoupHtml();
                            try {
                                String id = jsoupHtml.getUrl("http://musicmini.baidu.com/app/search/searchList.php?qword=" + re + "&ie=utf-8&page=" + 1);
                                if("".equals(id) || TextUtils.isEmpty(id)){
                                    Log.d("xuezhiyuan","null");
                                }else {
                                    OkHttpUtils okHttpUtils = new OkHttpUtils();
                                    String httpGet = okHttpUtils.okHttpGet(id);
                                    Gson gson = new Gson();
                                    MusicBean musicBean = new MusicBean();
                                    MusicBean fromJson = gson.fromJson(httpGet, (Type) musicBean);
                                    Log.d("xuezhiyuan",httpGet);
                                    songList = fromJson.getData().getSongList();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
            break;
        }
    }
}
