package csc296.finalproject.Video;


import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;

import csc296.finalproject.Fragtivity;
import csc296.finalproject.ProfileActivity;
import csc296.finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaPlayerFragment extends Fragment {

    private static final String TAG = "MediaPlayerFragTag";
    private MediaPlayer mPlayer;
    private ImageButton mPause;
    private ImageButton mPlay;
    private ImageButton mStop;
    private ImageView mImageView;



    public MediaPlayerFragment() {
        // Required empty public constructor
    }

    public static MediaPlayerFragment newInstance() {
        return new MediaPlayerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);  // audio playback continuity
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);



    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_media_player, container, false);

        Fragtivity activity=(Fragtivity)getActivity();
        mImageView=(ImageView)view.findViewById(R.id.imageView_media_player);
        try {
            ProfileActivity.displayImage(mImageView, activity.getPhoto(), getActivity());
        }
        catch (Exception e){
            Log.e(TAG, e.toString());
        }



        mPause = (ImageButton)view.findViewById(R.id.button_pause);

        mPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragtivity activity=(Fragtivity)getActivity();

                if(mPlayer.isPlaying()) {
                    mPlayer.pause();
                    mPause.setImageResource(R.mipmap.icon_play);
                }
                else {
                    play(Uri.parse(activity.getVideo()));
                    mPlayer.start();
                    mPause.setImageResource(R.mipmap.icon_pause);


                }
            }
        });

        return view;
    }

    private void play(Uri uri) {
        try {

            if(mPlayer.isPlaying()) {
                Log.i(TAG, "Media player is playing; stopping.");
                mPlayer.stop();
            }
            mPlayer.reset();
            mPlayer.setDataSource(getActivity().getApplicationContext(), uri);
            mPlayer.prepare();
        }
        catch(IOException ioe) {
            Log.e(TAG, "Failed to play music: ");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayer.release();
        mPlayer = null;
    }

}
