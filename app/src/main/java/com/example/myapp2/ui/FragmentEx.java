package com.example.myapp2.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class FragmentEx extends Fragment{

    protected AppCompatActivity getAppCompatActivity() {
        return (AppCompatActivity) getActivity();
    }

    /**
     * @return Instance of {@link ActionBar}.
     */
    protected ActionBar getSupportActionBar() {
        // The Fragment might not always get completely destroyed after Activity.finish(), hence
        // this code might get called after the hosting activity is destroyed.  Therefore we need
        // to handle getActivity() properly.  Refer to:  http://stackoverflow.com/a/21886594/3132935
        AppCompatActivity activity = getAppCompatActivity();
        return (activity != null ? activity.getSupportActionBar() : null);
    }

    /**
     * Set a {@link Toolbar} to act as the {@link ActionBar}.
     *
     * @param toolbar	Toolbar to set as the Activity's action bar, or null to clear it.
     */
    protected void setSupportActionBar(Toolbar toolbar) {
        AppCompatActivity activity = getAppCompatActivity();

        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }
    }
}
