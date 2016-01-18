package csc296.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.util.UUID;

import csc296.finalproject.Database.UserDbCrud;
import csc296.finalproject.Model.User;
import csc296.finalproject.Photo.PhotoUtils;
import csc296.finalproject.RecyclerView.FeedRecyclerActivity;
import csc296.finalproject.RecyclerView.PersonalRecyclerActivity;

public class ProfileActivity extends Activity {

    private static final String TAG="ProfileActivity";
    private User mUser;
    private UserDbCrud mUserDbCrud;
    private String mUsername;
    private ImageView mImage;
    private VideoView mVideoView;
    private File mPhotoFile;
    private File mVideoFile;
    private static final int TAKE_PICTURE = 1;
    private static final int RECORD_VIDEO = 2;
    private static final String DEFAULT="NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mUser = new User();
        mUsername=getIntent().getStringExtra(SignInActivity.KEY_USERNAME);
        mUser.setUserName(mUsername);
        mUserDbCrud = UserDbCrud.get(getApplicationContext());
        setDefault(mUser);

        TextView hello=(TextView)findViewById(R.id.textView_profile_hello);
        hello.setText("Hello, " + mUsername);

        TextView photo=(TextView)findViewById(R.id.textView_profile_photo);
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

                String filename = "IMG_" + UUID.randomUUID().toString() + ".jpg";
                File picturesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                mPhotoFile = new File(picturesDir, filename);
                Uri photoUri = Uri.fromFile(mPhotoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(intent, TAKE_PICTURE);


            }
        });

        TextView video=(TextView)findViewById(R.id.textView_profile_video);
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);

                String filename = "VIDEO_" + UUID.randomUUID().toString() + ".3gp";
                File videosDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
                mVideoFile = new File(videosDir, filename);
                Uri videoUri = Uri.fromFile(mVideoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                startActivityForResult(intent, RECORD_VIDEO);

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                handled = true;
                break;

            default:
                handled = super.onOptionsItemSelected(item);
        }

        return handled;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK){
            Log.d(TAG, "onActivityResult()");
            try {
                mImage = (ImageView) findViewById(R.id.imageView_profile_photo);
                displayImage(mImage, mPhotoFile.getPath(),this);
                mUser.setPhoto(mPhotoFile.getPath());
            }
            catch (Exception e){
                Log.d(TAG, e.toString());
            }
        }

        if (requestCode == RECORD_VIDEO && resultCode == RESULT_OK){
            try {
                mVideoView = (VideoView) findViewById(R.id.videoView_profile_video);
                displayVideo(mVideoView,mVideoFile.getPath(),getApplicationContext());
                mUser.setVideo(mVideoFile.getPath());
            }
            catch (Exception e){
                Log.d(TAG, e.toString());
            }
        }
    }


    public static void displayVideo(VideoView video, String path, Context c){
        video.setVideoURI(Uri.parse(path));
        video.setMediaController(new MediaController(c));
        video.requestFocus();
        video.start();
    }

    public static void displayImage(ImageView image, String path, Activity activity) {
        Bitmap bitmap = PhotoUtils.getScaledBitmap(path, activity);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        image.setImageBitmap(bitmap);
    }

    public void button_save(View view){
        EditText edittext=(EditText)findViewById(R.id.editText_profile_song_name);
        mUser.setSongName(edittext.getText().toString());

        int rowInserted = mUserDbCrud.insertRecords(mUser);
        if (rowInserted > 0)
            Toast.makeText(getApplicationContext(), "Save Successfully!", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(ProfileActivity.this,ProfileActivity.class);
        intent.putExtra(SignInActivity.KEY_USERNAME, mUser.getUserName().toString());
        startActivity(intent);

    }



    public void setDefault(User user){
        user.setSongName(DEFAULT);
        user.setPhoto(DEFAULT);
        user.setVideo(DEFAULT);
    }



    private void restartActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.putExtra(SignInActivity.KEY_USERNAME, mUser.getUserName());
        startActivity(intent);
    }

}
