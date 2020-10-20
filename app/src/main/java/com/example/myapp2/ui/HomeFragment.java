package com.example.myapp2.ui;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp2.R;
import com.example.myapp2.adapter.PlaylistRecyclerAdapter;
import com.example.myapp2.model.Podcast;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class HomeFragment extends Fragment
{

    private static final String TAG = "HomeFragment";


    // UI Components


    // Vars
    private ArrayAdapter<String> arrayAdapter;
    private IMainActivity mIMainActivity;
    //private CollectionReference ref;

    //private PlaylistRecyclerAdapter mResultList;

    public static HomeFragment newInstance(){
        return new HomeFragment();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

        //FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        //ref = firestore.collection("Audio");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "home fragment oncreateview executed:");
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        ImageView radioChecked = view.findViewById(R.id.radio_button_checked);
        /*List<String> mylist = new ArrayList<>();
        mylist.add("Eraser");mylist.add("book");mylist.add("Apple");
        mylist.add("pencil");mylist.add("Android");mylist.add("Amazon");

        arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,mylist);
        listView.setAdapter(arrayAdapter);*/
        Log.d(TAG, "arrayadapter created");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //firebaseUserSearch(view, "paris");
        Log.d(TAG, "HomeFragment: onViewCreated and firebaseusersearch called ");
    }

/*    @Override
    public Void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        ListView listView = view.findViewById(R.id.my_list);
        List<String> mylist = new ArrayList<>();
        mylist.add("Eraser");mylist.add("book");mylist.add("Apple");
        mylist.add("pencil");mylist.add("Android");mylist.add("Amazon");

        arrayAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,mylist);
        listView.setAdapter(arrayAdapter);
        Log.d(TAG, "arrayadapter created");
        return view;
    }*/
   @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mIMainActivity = (IMainActivity) getActivity();
    }

    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.my_menu, menu);
    }*/

/*    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        int id = menuItem.getItemId();
        if(id == R.id.search_icon){
            SearchView searchView = (SearchView) menuItem.getActionView();
            searchView.setQueryHint("Search Here!");
            mIMainActivity.onTextChange(menuItem);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    Log.d(TAG, "text submit");
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String s) {
                    //arrayAdapter.getFilter().filter(s);
                    mIMainActivity.onTextChange(s);
                    Log.d(TAG, "text changed and arrayadapter filtered");
                    return true;
                }
            });
        }
        return super.onOptionsItemSelected(menuItem);
    }*/




}















