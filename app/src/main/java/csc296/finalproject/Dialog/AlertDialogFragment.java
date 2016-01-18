package csc296.finalproject.Dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

import csc296.finalproject.Database.UserDbCrud;
import csc296.finalproject.Model.User;
import csc296.finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlertDialogFragment extends DialogFragment {

    private static final String KEY_ID="ID";
    private static final String KEY_USER_NAME="USERNAME";
    private static final String KEY_SONG_NAME="SONGNAME";
    private static final String KEY_PHOTO="PHOTO";
    private static final String KEY_VIDEO="VIDEO";
    private User mUser;
    private UserDbCrud mUserDbCrud;



    public AlertDialogFragment(){

    }



    public static AlertDialogFragment newInstance(User user){
        Bundle args = new Bundle();
        args.putString(KEY_ID, user.getId().toString());
        args.putString(KEY_USER_NAME, user.getUserName());
        args.putString(KEY_SONG_NAME,user.getSongName());
        args.putString(KEY_PHOTO,user.getPhoto());
        args.putString(KEY_VIDEO,user.getVideo());
        AlertDialogFragment dialog=new AlertDialogFragment();
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final Bundle args = getArguments();
        if (args != null) {
            builder.setMessage("Please confirm if you want to post this record");
            builder.setView(view);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mUser=new User(UUID.fromString(args.getString(KEY_ID)));
                    mUser.setUserName(args.getString(KEY_USER_NAME));
                    mUser.setSongName(args.getString(KEY_SONG_NAME));
                    mUser.setPhoto(args.getString(KEY_PHOTO));
                    mUser.setVideo(args.getString(KEY_VIDEO));
                    mUserDbCrud = UserDbCrud.get(getActivity().getApplicationContext());
                    int rowInserted = mUserDbCrud.createFeedItems(mUser);
                    if (rowInserted > 0)
                        Toast.makeText(getActivity().getApplicationContext(), "Post Successfully!", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

        }
        return builder.create();
    }

}
