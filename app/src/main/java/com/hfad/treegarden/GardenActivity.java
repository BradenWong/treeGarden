package com.hfad.treegarden;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class GardenActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private TextView textViewName;
    private TextView textViewCauseOfDeath;
    private TextView textViewDeath;
    private ImageView imageViewTree;
    private Tree sendingTree;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Backendless.initApp( getApplicationContext(),
                credentials.APPID,
                credentials.API_KEY );
        setContentView(R.layout.activity_garden);

        wireWidgets();
        prefillFields();
        setOnClickListener();
    }

    private void prefillFields() {
        Intent restaurantIntent = getIntent();
        sendingTree = restaurantIntent.getParcelableExtra(GardenListActivity.EXTRA_TREE);
        if (sendingTree != null) {
            textViewName.setText(sendingTree.getTree());
            textViewCauseOfDeath.setText(sendingTree.getCauseOfDeath());
            textViewDeath.setText(String.format("%s", sendingTree.isDead()));


        }
    }

    private void setOnClickListener() {
       button.setOnClickListener(this);
    }


    private void wireWidgets() {
        textViewName = findViewById(R.id.textView_garden_name);
        textViewDeath = findViewById(R.id.textView_garden_dead);
        textViewCauseOfDeath = findViewById(R.id.textView_garden_causeOfDeath);
        imageViewTree = findViewById(R.id.imageView_garden);
        button = findViewById(R.id.button_gardenActivity_back);
    }

    private void SaveOnBackendless() {


        String tree = textViewName.getText().toString();
        String causeOfDeath = textViewCauseOfDeath.getText().toString();
        boolean dead = true;
        if (sendingTree == null) {
            sendingTree = new Tree();
        }
        sendingTree.setTree(tree);
        sendingTree.setCauseOfDeath(causeOfDeath);
        sendingTree.setDead(dead);



        Backendless.Persistence.save(sendingTree, new AsyncCallback<Tree>() {
            @Override
            public void handleResponse(Tree response) {
                Toast.makeText(GardenActivity.this, "I saved on backendless.", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(GardenActivity.this, "Failed: Backendless" + fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_gardenActivity_back:
                SaveOnBackendless();
                Intent closeGardenIntent = new Intent (GardenActivity.this,
                        GardenListActivity.class);
                startActivity(closeGardenIntent);


        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}