package com.hfad.treegarden;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.List;

public class GardenListActivity extends AppCompatActivity {
    public static final String EXTRA_TREE = "";
    private ListView listViewTree;
    private String userId = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garden_list);
        wireWidgets();


        registerForContextMenu(listViewTree);
        populateListView();



    }

//    private void login() {
//        String login = "braden.k.wong@gmail.com";
//        String password = "minecraft";
//        Backendless.UserService.login(login, password, new AsyncCallback<BackendlessUser>() {
//            @Override
//            public void handleResponse(BackendlessUser response) {
//                //Start the new activity here bc
//                //this method is called when login is complete successful
//                Toast.makeText(GardenListActivity.this, Backendless.UserService.CurrentUser()+"", Toast.LENGTH_SHORT).show();
//                userId = response.getObjectId();
//                populateListView();
//
//            }
//
//            @Override
//            public void handleFault(BackendlessFault fault) {
//                Toast.makeText(GardenListActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//    }

    private void populateListView() {
        userId = Backendless.UserService.CurrentUser().getObjectId();
        String whereClause = "objectId = '" + userId + "'";
        DataQueryBuilder queryBuilder = DataQueryBuilder.create();
        queryBuilder.setWhereClause( whereClause );



        Backendless.Data.of( Tree.class).find(queryBuilder, new AsyncCallback<List<Tree>>(){
            @Override
            public void handleResponse(final List<Tree> gardenList )
            {
                Log.d("GARDENS: ", "handleResponse: " + gardenList.toString());
                // all Restaurant instances have been found
                GardenAdapter adapter = new GardenAdapter(
                        GardenListActivity.this,
                        R.layout.item_gardenlist,
                        gardenList);
                listViewTree.setAdapter(adapter);
//                set the onItemClickListener to open the Garden Activity
                listViewTree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent treeDetailIntent = new Intent(GardenListActivity.this, GardenActivity.class);
                        treeDetailIntent.putExtra(EXTRA_TREE, (Parcelable) gardenList.get(position));
                        startActivity(treeDetailIntent);
                        // finish();
                    }
                });
                //take the clicked object and include it in the Intent
                //in the RestaurantActivity's onCreate, check it there is a Parcelable extra
                //if there is, then get the Restaurant object and populate the fields
                Toast.makeText(GardenListActivity.this, "Populated List View", Toast.LENGTH_SHORT).show();
            }



            @Override
            public void handleFault( BackendlessFault fault )
            {
                Toast.makeText(GardenListActivity.this, "failed to populate"+fault.getMessage(), Toast.LENGTH_SHORT).show();

                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        });
    }


    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.regmenu, menu);
    }
    public boolean onContextItemSelected(MenuItem item) {
        //find out which menu_delete item was pressed
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.option1:
                Tree treee = (Tree) listViewTree.getItemAtPosition(info.position);
                deleteRestaurant(treee);
                return true;
            default:
                return false;
        }
    }


    private void deleteRestaurant(Tree tree) {
        Backendless.Persistence.of(Tree.class ).remove(tree, new AsyncCallback<Long>()
        {
            public void handleResponse( Long response )
            {
                // Contact has been deleted. The response is the
                // time in milliseconds when the object was deleted
                populateListView();
            }
            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be
                // retrieved with fault.getCode()
                Toast.makeText(GardenListActivity.this, fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        } );
    }

    //method to execute when option one is chosen
    private void doOptionOne() {
        Toast.makeText(this, "Option One Chosen...", Toast.LENGTH_LONG).show();
    }

    private void wireWidgets() {
        listViewTree = findViewById(R.id.listview_gardenlist);
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateListView();
    }
}
