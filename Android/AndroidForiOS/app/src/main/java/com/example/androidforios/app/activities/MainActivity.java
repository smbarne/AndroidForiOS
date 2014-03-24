package com.example.androidforios.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.androidforios.app.R;
import com.example.androidforios.app.data.model.TripList;
import com.example.androidforios.app.fragments.SubwayListFragment;
import com.example.androidforios.app.fragments.SubwayListFragment.SubwayLineFragmentInteractionListener;

public class MainActivity extends FragmentActivity implements SubwayLineFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            Fragment subwayListFragment = SubwayListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_main_container, subwayListFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_about) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onLineTypeSelected(TripList.LineType lineType)
    {
        Intent intent = TripListActivity.getTripListActivityIntent(getApplicationContext(), lineType);
        startActivity(intent);
    }
}
