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
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

import csc296.finalproject.Database.UserDbCrud;
import csc296.finalproject.Model.FeedItem;
import csc296.finalproject.Model.User;
import csc296.finalproject.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WriteCommentDialog extends DialogFragment {

    private static final String KEY_ID = "ID";
    private EditText mEditText;
    private User mUser;
    private UserDbCrud mUserDbCrud;

    public WriteCommentDialog() {
        // Required empty public constructor
    }

    public static WriteCommentDialog newInstance(FeedItem feedItem) {
        Bundle args = new Bundle();
        args.putString(KEY_ID, feedItem.getId().toString());
        WriteCommentDialog dialog = new WriteCommentDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_write_comment_dialog, null);
        AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        mEditText=(EditText)view.findViewById(R.id.editText_comment);
        final Bundle args = getArguments();
        if (args != null) {
            builder.setTitle("Comment");
            builder.setView(view);
            builder.setPositiveButton("POST", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    mUser = new User(UUID.fromString(args.getString(KEY_ID)));
                    mUser.setComment(mEditText.getText().toString());
                    mUserDbCrud = UserDbCrud.get(getActivity().getApplicationContext());
                    mUserDbCrud.updateComments(mUser);
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





