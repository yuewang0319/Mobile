package csc296.finalproject.RecyclerView;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import csc296.finalproject.Database.UserDbCrud;
import csc296.finalproject.Database.UserDbSchema;
import csc296.finalproject.Dialog.AlertDialogFragment;
import csc296.finalproject.Dialog.WriteCommentDialog;
import csc296.finalproject.Fragtivity;
import csc296.finalproject.MainActivity;
import csc296.finalproject.Model.FeedItem;
import csc296.finalproject.Model.User;
import csc296.finalproject.ProfileActivity;
import csc296.finalproject.R;
import csc296.finalproject.SignInActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedRecyclerFragment extends Fragment {

    public interface DetailItemClickListener {
        public void OnPhotoItemClick(CharSequence message);

        public void OnVideoPlayClick(CharSequence video, CharSequence photo);
    }


    private final static String TAG="FeedRecyclerFragment";
    private UserDbCrud mUserDbCrud;
    private DetailItemClickListener mListener;
    private User mUser;
    private RecyclerView mRecyclerView;
    private List<FeedItem> mFeedItems;
    private MediaPlayer mMediaPlayer;
    private FeedAdapter mAdapter;
    private Button mSearchButton;
    private EditText mSearchInput;




    public FeedRecyclerFragment() {
        // Required empty public constructor
    }

    public static FeedRecyclerFragment newInstance(){
        return new FeedRecyclerFragment();
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

        mAdapter=new FeedAdapter(mFeedItems);
        mRecyclerView.setAdapter(mAdapter);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFeedItems = mUserDbCrud.readFeedItems(UserDbSchema.FeedItemTable.Cols.SONG_NAME + "=?", new String[]{mSearchInput.getText().toString()});
                mAdapter.notifyDataSetChanged();
            }
        });


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
        View view=inflater.inflate(R.layout.fragment_feed, container, false);

        mUser = new User();
        Fragtivity activity = (Fragtivity) getActivity();
        mUser.setUserName(activity.getUsername());

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_feed);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        mUserDbCrud = UserDbCrud.get(getActivity().getApplicationContext());
        mFeedItems=mUserDbCrud.readFeedItems(null, null);

        mSearchButton = (Button)view.findViewById(R.id.button_search);
        mSearchInput =(EditText)view.findViewById(R.id.editText_search);




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

        switch(item.getItemId()) {

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

    private class FeedAdapter extends RecyclerView.Adapter<FeedHolder> {


        public FeedAdapter(List<FeedItem> feedItems) {
            mFeedItems = feedItems;
        }


        @Override
        public FeedHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.view_feed_record, parent, false);

            FeedHolder holder = new FeedHolder(view);

            return holder;
        }

        @Override
        public void onBindViewHolder(FeedHolder holder, int position) {
            holder.bind(mFeedItems.get(position));
        }

        @Override
        public int getItemCount() {
            return mFeedItems.size();
        }


    }

    private class FeedHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "FeedHolder";
        private TextView mSongName;
        private TextView mComment;
        private ImageView mPhoto;
        private ImageView mShareIcon;
        private Button mVideoPlay;
        private FeedItem mFeedItem;



        public FeedHolder(final View itemView) {
            super(itemView);
            mSongName = (TextView) itemView.findViewById(R.id.textView_personal_song_name);
            mComment = (TextView) itemView.findViewById(R.id.textView_personal_comment);
            mPhoto = (ImageView) itemView.findViewById(R.id.imageView_personal_photo);
            mShareIcon = (ImageView) itemView.findViewById(R.id.imageView_personal_share);
            mVideoPlay = (Button) itemView.findViewById(R.id.button_video_play);



        }

        public void bind(FeedItem feedItem) {
            mFeedItem = feedItem;
            mSongName.setText(mFeedItem.getSongName());
            try {
                ProfileActivity.displayImage(mPhoto, mFeedItem.getPhoto(), getActivity());
            } catch (Exception e) {
                Log.e(TAG, e.toString());


            }

            mPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnPhotoItemClick(mFeedItem.getPhoto());
                    Log.d(TAG, mFeedItem.getPhoto());
                }
            });

            mVideoPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.OnVideoPlayClick(mFeedItem.getVideo(), mFeedItem.getPhoto());
                    Log.d(TAG, mFeedItem.getVideo());
                }
            });

            mComment.setText("Posted by " + mFeedItem.getUserName());






            mShareIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager manager = getFragmentManager();
                    WriteCommentDialog dialog = WriteCommentDialog.newInstance(mFeedItem);
                    dialog.show(manager,"WriteCommentDialog");
                }
            });


        }
    }


}
