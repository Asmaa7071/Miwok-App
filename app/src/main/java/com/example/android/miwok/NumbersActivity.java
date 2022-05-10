package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity implements ItemAdapter.OnItemClickListener {
    private  MediaPlayer mMediaPlayer;
    private ArrayList<ItemModel>numbersArrayList;
    private AudioManager mAudioManager;
    private RecyclerView recyclerView;

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
        setContentView(R.layout.activity_numbers);
        initViews();

        numbersArrayList = new ArrayList<ItemModel>();
        numbersArrayList.add(new ItemModel("lutti","One",R.drawable.number_one,R.raw.number_one));
        numbersArrayList.add(new ItemModel("otiiko","Two",R.drawable.number_two,R.raw.number_two));
        numbersArrayList.add(new ItemModel("tolookosu","Three",R.drawable.number_three,R.raw.number_three));
        numbersArrayList.add(new ItemModel("oyyisa","Four",R.drawable.number_four,R.raw.number_four));
        numbersArrayList.add(new ItemModel("massokka","Five",R.drawable.number_five,R.raw.number_five));
        numbersArrayList.add(new ItemModel("temmokka","Six",R.drawable.number_six,R.raw.number_six));
        numbersArrayList.add(new ItemModel("kenekaku","Seven",R.drawable.number_seven,R.raw.number_seven));
        numbersArrayList.add(new ItemModel("kawinta","Eight",R.drawable.number_eight,R.raw.number_eight));
        numbersArrayList.add(new ItemModel("wo’e","Nine",R.drawable.number_nine,R.raw.number_nine));
        numbersArrayList.add(new ItemModel("na’aacha","Ten",R.drawable.number_ten,R.raw.number_ten));


        ItemAdapter itemAdapter2 = new ItemAdapter(NumbersActivity.this,numbersArrayList,R.color.category_numbers,this);
        recyclerView.setAdapter(itemAdapter2);

        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(NumbersActivity.this,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager2);

    }//End on crete method

    /**
     * Initial values of each view
     */

    private void initViews() {
        recyclerView = findViewById(R.id.numbersRecyclerView);
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * on each item click open media player of this item
     */

    @Override
    public void onItemClick(int itemNo) {
        releaseMediaPlayer();
        ItemModel itemModel = numbersArrayList.get(itemNo);

        int res = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

              if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                  //We have focus now.......

                  // Setup a listener on the media player, so that we can stop and release the
                  // media player once the sound has finished playing.

                  mMediaPlayer = MediaPlayer.create(NumbersActivity.this, itemModel.getMediaPlayerResID());
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
}//End class