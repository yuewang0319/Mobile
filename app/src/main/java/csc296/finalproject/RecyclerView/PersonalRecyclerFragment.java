package csc296.finalproject.RecyclerView;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;
import java.util.List;

import csc296.finalproject.Database.UserCursorWrapper;
import csc296.finalproject.Database.UserDbCrud;
import csc296.finalproject.Database.UserDbSchema;
import csc296.finalproject.Dialog.AlertDialogFragment;
import csc296.finalproject.Fragtivity;
import csc296.finalproject.MainActivity;
import csc296.finalproject.Model.User;
import csc296.finalproject.ProfileActivity;
import csc296.finalproject.R;
import csc296.finalproject.SignInActivity;

import csc296.finalproject.Database.UserDbSchema.UserTable;
import csc296.finalproject.Database.UserDbSchema.FeedItemTable;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalRecyclerFragment extends Fragment {

    public interface DetailItemClickListener {
        public void OnPhotoItemClick(CharSequence message);

        public void OnVideoPlayClick(CharSequence video, CharSequence photo);
    }

    private final static String TAG = "PRecyclerFragment";
    private User mUser;
    private UserDbCrud mUserDbCrud;
    private DetailItemClickListener mListener;
    private RecyclerView mRecyclerView;
    private List<User> mUsers;
    private MediaPlayer mMediaPlayer;

    public PersonalRecyclerFragment() {
        // Required empty public constructor
    }


    public static PersonalRecyclerFragment newInstance() {
        return new PersonalRecyclerFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mListener = (DetailItemClickListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (DetailItemClickListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();


        mRecyclerView.setAdapter(new PersonalAdapter(mUsers));


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal_recycler, container, false);


        mUser = new User();
        Fragtivity activity = (Fragtivity) getActivity();
        mUser.setUserName(activity.getUsername());
        Log.d(TAG, mUser.getUserName().toString());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_persons);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        mUserDbCrud = UserDbCrud.get(getActivity().getApplicationContext());
        mUsers = mUserDbCrud.readUsers(UserTable.Cols.USERNAME + "=? AND " + UserTable.Cols.PASSWORD + " IS NULL", new String[]{mUser.getUserName()});
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        boolean handled;

        switch (item.getItemId()) {

            case R.id.menu_home:
                restartActivity(FeedRecyclerActivity.class);
                handled = true;
                break;
            case R.id.menu_create_record:
                restartActivity(ProfileActivity.class);
                handled = true;
                break;
            case R.id.menu_record_history:
                restartActivity(PersonalRecyclerActivity.class);
                handled = true;
                break;
            case R.id.menu_log_out:
                Intent intent1 = new Intent(getActivity(), MainActivity.class);
                startActivity(intent1);
                handled = true;
                break;

            default:
                handled = super.onOptionsItemSelected(item);
        }

        return handled;
    }

    private void restartActivity(Class activityClass) {
        Intent intent = new Intent(getActivity(), activityClass);
        intent.putExtra(SignInActivity.KEY_USERNAME, mUser.getUserName());
        startActivity(intent);
    }


    private class PersonalAdapter extends RecyclerView.Adapter<PersonalHolder> {
        private List<User> mUsers;

        public PersonalAdapter(List<User> users) {
            mUsers = users;
        }


        @Override
        public PersonalHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.view_personal_record, parent, false);

            PersonalHolder holder = new PersonalHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(PersonalHolder holder, int position) {
            holder.bind(mUsers.get(position));
        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }


    }

    private class PersonalHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "PersonalHolder";
        private TextView mSongName;
        private TextView mComment;
        private ImageView mPhoto;
        private ImageView mShareIcon;
        private Button mVideoPlay;
        private User mUser;


        public PersonalHolder(final View itemView) {
            super(itemView);
            mSongName = (TextView) itemView.findViewById(R.id.textView_personal_song_name);
            mComment = (TextView) itemView.findViewById(R.id.textView_personal_comment);
            mPhoto = (ImageView) itemView.findViewById(R.id.imageView_personal_photo);
            mShareIcon = (ImageView) itemView.findViewById(R.id.imageView_personal_share);
            mVideoPlay = (Button) itemView.findViewById(R.id.button_video_play);
            mComment = (TextView)itemView.findViewById(R.id.textView_personal_comment);

        }

        public void bind(User user) {
            mUser = user;
            mSongName.setText(mUser.getSongName());
            try {
                ProfileActivity.displayImage(mPhoto, mUser.getPhoto(), getActivity());
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

            mPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnPhotoItemClick(mUser.getPhoto());
                }
            });

            mVideoPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnVideoPlayClick(mUser.getVideo(), mUser.getPhoto());
                }
            });

            mShareIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getFragmentManager();
                    AlertDialogFragment dialog = AlertDialogFragment.newInstance(mUser);
                    dialog.show(manager, "AlertDialog");
                }
            });
            if(mUser.getComment()!=null) {
                mComment.setText(mUser.getComment());
            }


        }
    }
}
