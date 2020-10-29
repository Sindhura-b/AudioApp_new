package com.example.myapp2.ui;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp2.R;
import com.example.myapp2.adapter.PlaylistRecyclerAdapter;
import com.example.myapp2.model.Podcast;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class CategoryFragment extends Fragment
{

    private static final String TAG = "CategoryFragment";

    /** User's search query string. */
    private String  searchQuery = "";
    /** Edit searched query through long press on search query**/
    private SearchView editSearchView;
    private IMainActivity mIMainActivity;

    public static final String QUERY = "CategoryFragment.Query";

    private CollectionReference ref;
    private PlaylistRecyclerAdapter mResultList;


    public static CategoryFragment newInstance(){

        Log.d(TAG, "Category fragment new instance");
        return new CategoryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        ref = firestore.collection("Audio");
        Log.d(TAG, "Category fragment on create " + ref);

        // set the user's search query
        searchQuery = getArguments().getString(QUERY);

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.layout_category_list_item, container, false);
       //View view = super.onCreateView(R.layout.layout_category_list_item, container, savedInstanceState);

       // setup the toolbar/actionbar
       //Toolbar toolbar = view.findViewById(R.id.toolbar);
       //setSupportActionBar(toolbar);

        //makes searched query editable on long press
        /*toolbar.setOnLongClickListener(new View.OnLongClickListener() {
            editSearchView.setIconified(false);
            editSearchView.setQuery(searchQuery,false);
            return false;
            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        });*/
        //the app will call onCreateOptionsMenu() for when the user wants to search
        setHasOptionsMenu(true);

        Log.d(TAG, "Category fragment on create view");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        firebaseUserSearch(view, searchQuery);
        Log.d(TAG, "CategoryFragment: onViewCreated and firebaseusersearch called ");
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem searchMenuItem = menu.findItem(R.id.search_icon);
        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
        editSearchView = searchView;
        // will be called when user clicks on actionbar's search icon
        searchMenuItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                // if the user has searched previously, then copy the query into the search view
                if(searchQuery != null && !searchQuery.isEmpty()){
                    searchView.onActionViewExpanded();
                    searchView.setQuery(searchQuery, false);
                }
                // now expand the search view
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                return true;
            }
        });
    }

    private void firebaseUserSearch(View view, String searchText) {


        Query firestoreSearchQuery = ref.orderBy("location_id").startAt(searchText).endAt(searchText + "\uf8ff");

        FirestoreRecyclerOptions<Podcast> options = new FirestoreRecyclerOptions.Builder<Podcast>().setQuery(firestoreSearchQuery, Podcast.class).build();

        mResultList = new PlaylistRecyclerAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mResultList);

        Log.d(TAG, "CategoryFragmenrt: firebasesearch finished " + mResultList);
    }

    @Override
    public void onStart() {
        super.onStart();
        mResultList.startListening();
    }
    @Override
    public void onStop() {
        super.onStop();
        mResultList.stopListening();
    }


}















