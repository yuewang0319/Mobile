package csc296.finalproject.RecyclerView;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import csc296.finalproject.Fragtivity;

public class PhotoDetailActivity extends Fragtivity {


    public static final String PHOTO_MESSAGE = "PHOTO_MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public static Intent newIntent(Context c,CharSequence message){
        Intent intent=new Intent(c,PhotoDetailActivity.class);
        intent.putExtra(PHOTO_MESSAGE, message);

        return intent;
    }


    @Override
    public Fragment createFragment(){
        return PhotoDetailFragment.newInstance();
    }


}
