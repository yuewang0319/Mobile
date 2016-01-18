package csc296.finalproject.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;

import csc296.finalproject.Fragtivity;
import csc296.finalproject.SignInActivity;
import csc296.finalproject.Video.MediaPlayerActivity;

public class PersonalRecyclerActivity extends Fragtivity implements PersonalRecyclerFragment.DetailItemClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }


    @Override
    public Fragment createFragment(){
        return PersonalRecyclerFragment.newInstance();
    }

    @Override
    public void OnPhotoItemClick(CharSequence name){
        Intent intent=PhotoDetailActivity.newIntent(this,name);
        startActivity(intent);
    }

    @Override
    public void OnVideoPlayClick(CharSequence video,CharSequence photo){
        Intent intent= MediaPlayerActivity.newIntent(this, video, photo);
        startActivity(intent);
    }
}
