package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener{
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private ArrayList<ItemModel>colorsArrayList;

    /**
     * When Audio Manger Change Focus
     */
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            // The AUDIO_FOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a short amount of time.
            // The AUDIO_FOCUS_LOSS_TRANSIENT_CAN_DUCK case means that our app is allowed to continue playing sound but at a lower volume.
            //  We'll treat both cases the same way because our app is playing short sound files.

            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK){
                //pause
                mMediaPlayer.pause();
                //playback start from zero position
                mMediaPlayer.seekTo(0);
            }//End if
            else if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                // The AUDIO_FOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            }//End Else if
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                // The AUDIO_FOCUS_LOSS case means we've lost audio focus permanent
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }//End Else if

        }//End OnAudioFocusChange
    };//End OnAudioFocusChangeListener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);

        RecyclerView recyclerView = findViewById(R.id.colorsRecyclerView);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        colorsArrayList =new ArrayList<>();

        colorsArrayList.add(new ItemModel("weṭeṭṭi","red",R.drawable.color_red,R.raw.color_red));
        colorsArrayList.add(new ItemModel("chokokki","green",R.drawable.color_green,R.raw.color_green));
        colorsArrayList.add(new ItemModel("ṭakaakki","brown",R.drawable.color_brown,R.raw.color_brown));
        colorsArrayList.add(new ItemModel("ṭopoppi","gray",R.drawable.color_gray,R.raw.color_gray));
        colorsArrayList.add(new ItemModel("kululli","black",R.drawable.color_black,R.raw.color_black));
        colorsArrayList.add(new ItemModel("kelelli","white",R.drawable.color_white,R.raw.color_white));
        colorsArrayList.add(new ItemModel("ṭopiisә","dusty yellow",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        colorsArrayList.add(new ItemModel("chiwiiṭә","mustard yellow",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));


        ItemAdapter itemAdapter = new ItemAdapter(ColorsActivity.this,colorsArrayList,R.color.category_colors,this);
        recyclerView.setAdapter(itemAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ColorsActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
    }

    /**
     * on each item click open media player of this item
     */

    @Override
    public void onItemClick(int itemNo) {
        releaseMediaPlayer();
        ItemModel itemModel = colorsArrayList.get(itemNo);

        int res = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //We have focus now.......

            // Setup a listener on the media player, so that we can stop and release the
            // media player once the sound has finished playing.

            mMediaPlayer = MediaPlayer.create(ColorsActivity.this, itemModel.getMediaPlayerResID());
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
        }


    }//End on Item Click

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);

        }//end If
    }//End release media player

    /**
     * After media player completion Clean up the media player by releasing its resources.
     */

    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            releaseMediaPlayer();
        }//End onCompletion method
    };//End MediaPlayer.OnCompletionListener

    /**
     * When the user stop the app Clean up the media player by releasing its resources.
     */
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }//End on stop method
}