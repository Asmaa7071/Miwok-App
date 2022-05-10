package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {
    public MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    public ArrayList<ItemModel>phrasesArrayList;

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
        setContentView(R.layout.activity_phrases);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        RecyclerView recyclerView = findViewById(R.id.phrasesRecyclerView);

        phrasesArrayList = new ArrayList<ItemModel>();
        phrasesArrayList.add(new ItemModel("minto wuksus","Where are you going?",R.raw.phrase_where_are_you_going));
        phrasesArrayList.add(new ItemModel("tinnә oyaase'nә","What is your name?",R.raw.phrase_what_is_your_name));
        phrasesArrayList.add(new ItemModel("oyaaset...", "My name is...",R.raw.phrase_my_name_is));
        phrasesArrayList.add(new ItemModel("michәksәs?","How are you feeling?",R.raw.phrase_how_are_you_feeling));
        phrasesArrayList.add(new ItemModel("kuchi achit","I’m feeling good.",R.raw.phrase_im_feeling_good));
        phrasesArrayList.add(new ItemModel("әәnәs'aa?","Are you coming?",R.raw.phrase_are_you_coming));
        phrasesArrayList.add(new ItemModel("hәә’ әәnәm","Yes, I’m coming.",R.raw.phrase_yes_im_coming));
        phrasesArrayList.add(new ItemModel("әәnәm","I’m coming.",R.raw.phrase_im_coming));
        phrasesArrayList.add(new ItemModel("yoowutis","Let’s go.",R.raw.phrase_lets_go));
        phrasesArrayList.add(new ItemModel("әnni'nem","Come here.",R.raw.phrase_come_here));


        ItemAdapter itemAdapter2 = new ItemAdapter(PhrasesActivity.this,phrasesArrayList,R.color.category_phrases,this);
        recyclerView.setAdapter(itemAdapter2);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(PhrasesActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager2);
    }

    /**
     * on each item click open media player of this item
     */

    @Override
    public void onItemClick(int itemNo) {
        releaseMediaPlayer();
        ItemModel itemModel = phrasesArrayList.get(itemNo);

        int res = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //We have focus now.......

            // Setup a listener on the media player, so that we can stop and release the
            // media player once the sound has finished playing.
            mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, itemModel.getMediaPlayerResID());
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(onCompletionListener);
        }//End If
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