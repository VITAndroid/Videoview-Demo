package com.example.videoview;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class Videoview extends Activity {
    ProgressBar mProgressBar;
    VideoView mVideoView;
    TextView textView;
    RatingBar ratingbar;
    Button button;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mVideoView = findViewById(R.id.video_view);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        mVideoView.setVideoURI(uri);
        MediaController mediaController = new MediaController(this);
        mVideoView.setMediaController(mediaController);
        mediaController.setAnchorView(mVideoView);

        mProgressBar = (ProgressBar) findViewById(R.id.Progressbar);
        mProgressBar.setProgress(0);
        mProgressBar.setMax(100);
        new MyAsync().execute();
        textView = (TextView) findViewById(R.id.duration);

        ratingbar=(RatingBar)findViewById(R.id.ratingBar);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(view -> {
            String rating=String.valueOf(ratingbar.getRating());
            Toast.makeText(getApplicationContext(), "Your rating: "+rating+"\n    Submitted.", Toast.LENGTH_LONG).show();
        });
    }
    private class MyAsync extends AsyncTask<Void, Integer, Void>
    {
        int duration = 0;
        int current = 0;
        @Override
        protected Void doInBackground(Void... params) {

            mVideoView.start();
            mVideoView.setOnPreparedListener(mp -> duration = mVideoView.getDuration());

            do {
                current = mVideoView.getCurrentPosition();
                handler.post(new Runnable() {
                    public void run() {
                        textView.setText(mProgressBar.getProgress()+"/"+mProgressBar.getMax());
                    }
                });
                try {
                    publishProgress((int) (current * 100 / duration));
                    if(mProgressBar.getProgress() >= 100){
                        break;
                    }
                } catch (Exception e) {
                }
            } while (mProgressBar.getProgress() <= 100);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgressBar.setProgress(values[0]);
        }
    }
}