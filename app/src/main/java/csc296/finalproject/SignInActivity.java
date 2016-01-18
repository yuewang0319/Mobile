package csc296.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import csc296.finalproject.Database.UserCursorWrapper;
import csc296.finalproject.Database.UserDbCrud;
import csc296.finalproject.Database.UserDbSchema;
import csc296.finalproject.Model.User;
import csc296.finalproject.RecyclerView.FeedRecyclerActivity;

public class SignInActivity extends Activity {

    private User mUser;
    private UserDbCrud mUserDbCrud;
    public static final String KEY_USERNAME="project02.csc292.project02.Username";
    private static final String TAG="SignIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mUser = new User();
        mUserDbCrud = UserDbCrud.get(getApplicationContext());

    }

    public void sign_in(View view) {

        EditText username = (EditText) findViewById(R.id.editText_SignIn_userName);
        mUser.setUserName(username.getText().toString());
        EditText password = (EditText) findViewById(R.id.editText_signIn_passWord);
        mUser.setPassword(password.getText().toString());

        UserCursorWrapper wrapper=mUserDbCrud.queryUsers(UserDbSchema.UserTable.Cols.USERNAME + "=? AND " +UserDbSchema.UserTable.Cols.PASSWORD + "=?", new String[]{mUser.getUserName().toString(),mUser.getPassword().toString()});

        if (wrapper.getCount()>0) {
            Toast.makeText(getApplicationContext(), "Log In Successfully!", Toast.LENGTH_LONG).show();
            Intent intent=new Intent(SignInActivity.this,FeedRecyclerActivity.class);
            intent.putExtra(KEY_USERNAME, mUser.getUserName().toString());
            Log.d(TAG, mUser.getUserName().toString());
            startActivity(intent);

        } else {
            Toast.makeText(getApplicationContext(), "Password or Username Incorrect!", Toast.LENGTH_LONG).show();
        }

        }

    public void log_out(View view){
        Intent intent=new Intent(SignInActivity.this,MainActivity.class);
        startActivity(intent);
    }

    }





