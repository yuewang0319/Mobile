package csc296.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import csc296.finalproject.Database.UserCursorWrapper;
import csc296.finalproject.Database.UserDbCrud;
import csc296.finalproject.Database.UserDbSchema;
import csc296.finalproject.Model.User;

public class SignUpActivity extends Activity {


    private User mUser;
    private UserDbCrud mUserDbCrud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mUser = new User();
        mUserDbCrud = UserDbCrud.get(getApplicationContext());


    }


    public void sign_up(View view) {

        EditText username = (EditText) findViewById(R.id.editText_Signup_userName);
        mUser.setUserName(username.getText().toString());
        EditText password = (EditText) findViewById(R.id.editText_signUp_passWord);
        mUser.setPassword(password.getText().toString());

        UserCursorWrapper wrapper = mUserDbCrud.queryUsers(UserDbSchema.UserTable.Cols.USERNAME + "=?", new String[]{mUser.getUserName().toString()});

        if (wrapper.getCount() > 0) {
            Toast.makeText(getApplicationContext(), "User Account Already Exists!", Toast.LENGTH_LONG).show();

        } else {
            int rowInserted = mUserDbCrud.createUsers(mUser);
            if (rowInserted > 0) {
                Toast.makeText(getApplicationContext(), "User Account Create Successfully!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);

            }

        }

    }

    public void log_out(View view){
        Intent intent=new Intent(SignUpActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
