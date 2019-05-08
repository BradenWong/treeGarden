package com.hfad.treegarden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoginActivity extends AppCompatActivity {

    private TextView textViewCreateAccount;
    private Button buttonLogin;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button createAccount;
    public static final String EXTRA_USERNAME = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        wireWidgets();

        //initalize backendless connection
        Backendless.initApp(this,"87E07747-F5D6-F578-FF3F-258ABE0D5900", "90720E74-763F-4D58-B654-1E6F3E253336");




        buttonLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                loginToBackendless();
            }
        });

        textViewCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAccountIntent = new Intent(LoginActivity.this,
                        CreateAccountActivity.class);

                String username = editTextUsername.getText().toString();
                if(username.length() > 0) {
                    createAccountIntent.putExtra(EXTRA_USERNAME, username);
                }
                startActivity(createAccountIntent);
            }
        });
    }

    private void loginToBackendless() {
        String login = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        Log.d("LOGIN", login);
        Log.d("PASSWORD", password);
        Backendless.UserService.login(login, password, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                //Start the new activity here bc
                //this method is called when login is complete successful
                Toast.makeText(LoginActivity.this, response.getEmail(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(LoginActivity.this, GardenListActivity.class);
                startActivity(intent);
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(LoginActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void wireWidgets() {
        textViewCreateAccount = findViewById(R.id.textview_login_createaccount);
        buttonLogin = findViewById(R.id.button_login_login);
        editTextPassword = findViewById(R.id.edittext_login_password);
        editTextUsername = findViewById(R.id.edittext_login_username);
    }
}