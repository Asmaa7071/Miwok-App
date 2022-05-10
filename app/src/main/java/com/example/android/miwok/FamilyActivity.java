package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class FamilyActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener{
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private ArrayList<ItemModel>familyArrayList;

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
        setContentView(R.layout.activity_family);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        RecyclerView recyclerView = findViewById(R.id.familyRecyclerView);
        familyArrayList =new ArrayList<>();

        familyArrayList.add(new ItemModel("әpә","father",R.drawable.family_father,R.raw.family_father));
        familyArrayList.add(new ItemModel("әṭa","mother",R.drawable.family_mother,R.raw.family_mother));
        familyArrayList.add(new ItemModel("angsi","son",R.drawable.family_son,R.raw.family_son));
        familyArrayList.add(new ItemModel("tune","daughter",R.drawable.family_daughter,R.raw.family_daughter));
        familyArrayList.add(new ItemModel("taachi","older brother",R.drawable.family_older_brother,R.raw.family_older_brother));
        familyArrayList.add(new ItemModel("chalitti","younger brother",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        familyArrayList.add(new ItemModel("teṭe","older sister",R.drawable.family_older_sister,R.raw.family_older_sister));
        familyArrayList.add(new ItemModel("kolliti","younger sister",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        familyArrayList.add(new ItemModel("ama","grandmother",R.drawable.family_grandmother,R.raw.family_grandmother));
        familyArrayList.add(new ItemModel("paapa","grandfather",R.drawable.family_grandfather,R.raw.family_grandfather));


        ItemAdapter itemAdapter = new ItemAdapter(FamilyActivity.this,familyArrayList,R.color.category_family,this);
        recyclerView.setAdapter(itemAdapter);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(FamilyActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

    }

    /**
     * on each item click open media player of this item
     */

    @Override
    public void onItemClick(int itemNo) {
        releaseMediaPlayer();
        ItemModel itemModel = familyArrayList.get(itemNo);

        int res = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //We have focus now.......

            // Setup a listener on the media player, so that we can stop and release the
            // media player once the sound has finished playing.
            mMediaPlayer = MediaPlayer.create(FamilyActivity.this, itemModel.getMediaPlayerResID());
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