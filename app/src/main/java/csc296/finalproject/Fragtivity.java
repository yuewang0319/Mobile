package csc296.finalproject;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import csc296.finalproject.Model.User;
import csc296.finalproject.RecyclerView.PhotoDetailActivity;
import csc296.finalproject.Video.MediaPlayerActivity;


public abstract class Fragtivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragtivity);



        FragmentManager manager = getFragmentManager();
        if(manager.findFragmentById(R.id.frame_layout_fragment_container) == null) {
            manager.beginTransaction()
                    .add(R.id.frame_layout_fragment_container, createFragment())
                    .commit();
        }
    }

    public abstract Fragment createFragment();


    public String getUsername(){
        return getIntent().getStringExtra(SignInActivity.KEY_USERNAME);

    }
    public String getPhoto(){
        return getIntent().getStringExtra(PhotoDetailActivity.PHOTO_MESSAGE);
    }

    public String getVideo(){
        return getIntent().getStringExtra(MediaPlayerActivity.VIDEO_MESSAGE);
    }
}

