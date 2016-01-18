package csc296.finalproject.RecyclerView;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import csc296.finalproject.Fragtivity;
import csc296.finalproject.ProfileActivity;
import csc296.finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotoDetailFragment extends Fragment {


    private final static String TAG="PhotoDetailFragment";
    private ImageView mImageView;

    public PhotoDetailFragment() {
        // Required empty public constructor
    }

    public static PhotoDetailFragment newInstance(){
        return new PhotoDetailFragment();
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view=inflater.inflate(R.layout.fragment_photo_detail, container, false);
        mImageView=(ImageView)view.findViewById(R.id.imageView_photo_full_screen);
        Fragtivity activity=(Fragtivity)getActivity();

        try {
            ProfileActivity.displayImage(mImageView, activity.getPhoto(), getActivity());
        }
        catch (Exception e){
            Log.e(TAG, e.toString());
        }

        return view;
    }

}
