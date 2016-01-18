package csc296.finalproject.Video;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;

import csc296.finalproject.Fragtivity;
import csc296.finalproject.RecyclerView.PhotoDetailActivity;

public class MediaPlayerActivity extends Fragtivity {
    public static final String VIDEO_MESSAGE = "VIDEO_MESSAGE";


    public static Intent newIntent(Context c,CharSequence video, CharSequence photo){
        Intent intent=new Intent(c,MediaPlayerActivity.class);
        intent.putExtra(VIDEO_MESSAGE, video);
        intent.putExtra(PhotoDetailActivity.PHOTO_MESSAGE,photo);

        return intent;
    }
    @Override
    public Fragment createFragment() {
        return MediaPlayerFragment.newInstance();
    }
}
