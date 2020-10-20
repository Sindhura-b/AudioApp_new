package com.example.myapp2.ui;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapp2.util.MainActivityFragmentManager;

import com.example.myapp2.R;


public class MainActivity extends AppCompatActivity implements IMainActivity
{

    private static final String TAG = "MainActivity";

    //UI Components

    // Vars

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loadFragment(HomeFragment.newInstance());
        Log.d(TAG, "load fragment called:");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.search_icon){
            final SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint("Search Here!");

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.d(TAG, "text submit");
                    displaySearchResults(query, searchView);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    //arrayAdapter.getFilter().filter(s);
                    Log.d(TAG, "text changed and arrayadapter filtered");
                    return false;
                }
            });
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private void loadFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        String tag ="";
        if(fragment instanceof HomeFragment){
            tag = getString(R.string.fragment_home);
        }
        else if(fragment instanceof CategoryFragment){
            tag = getString(R.string.fragment_category);
            transaction.addToBackStack(tag);
        }
        //else if(fragment instanceof PlaylistFragment){
        //    tag = getString(R.string.fragment_home);
        //    transaction.addToBackStack(tag);
        //}

        transaction.add(R.id.main_container,fragment,tag);
        transaction.commit();
        Log.d(TAG, "loaded fragment");

        //MainActivityFragmentManager.getInstance().addFragment(fragment);

        //showFragment(fragment);
    }

    private void showFragment(Fragment fragment){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.show(fragment);
        transaction.commit();

        for(Fragment f:MainActivityFragmentManager.getInstance().getFragments()){
            if(f!=null){
                if(!f.getTag().equals(fragment.getTag())){
                    FragmentTransaction t= getSupportFragmentManager().beginTransaction();
                    t.hide(f);
                    t.commit();
                }
            }
        }

        Log.d(TAG, "showFragment: num fragments: " + MainActivityFragmentManager.getInstance().getFragments().size());
    }

    /*@Override
    public void onTextChange(MenuItem menuItem) {
        loadFragment(CategoryFragment.newInstance(menuItem));
    }*/

    private void displaySearchResults(String query, @NonNull final View searchView) {
        // hide the keyboard
        searchView.clearFocus();

        // open SearchVideoGridFragment and display the results
        CategoryFragment categoryFragment = new CategoryFragment();
        Bundle bundle = new Bundle();
        bundle.putString(categoryFragment.QUERY, query);
        categoryFragment.setArguments(bundle);
        loadFragment(categoryFragment);

        Log.d(TAG, "MainActivity: Display search results called" );
    }


}


















