package com.example.androidforios.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.example.androidforios.app.R;
import com.example.androidforios.app.data.model.Trip;
import com.example.androidforios.app.data.model.TripList;
import com.example.androidforios.app.fragments.TripListFragment;
import com.example.androidforios.app.fragments.TripListFragment.TripListFragmentInteractionListener;

/**
 * Created by stephen.barnes on 3/23/14.
 */
public class TripListActivity extends FragmentActivity implements TripListFragmentInteractionListener {

    /**
     *
     * @param context
     * @param lineType
     * @return
     */
    public static Intent getTripListActivityIntent(Context context, TripList.LineType lineType) {
        Intent intent = new Intent(context, TripListActivity.class);
        intent.putExtra(TripListActivityState.KEY_ACTIVITY_TRIP_LIST_LINE_TYPE, lineType.getLineName());
        return intent;
    }

    public static final class TripListActivityState {
        public static final String KEY_ACTIVITY_TRIP_LIST_LINE_TYPE = "KEY_ACTIVITY_TRIP_LIST_LINE_TYPE";
    }

    TripList.LineType mLineType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_trip_list);

        mLineType = TripList.LineType.getLineType(getIntent().getStringExtra(TripListActivityState.KEY_ACTIVITY_TRIP_LIST_LINE_TYPE));
        setTitle(mLineType.getLineName());

        if (savedInstanceState == null) {
            TripListFragment tripListFragment = TripListFragment.newInstance(mLineType);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_trip_list_container, tripListFragment)
                    .commit();
        }
    }

    @Override
    public void onTripSelected(Trip trip) {
        Intent tripDetailIntent = TripDetailActivity.getTripDetailActivityIntent(this, trip);
        startActivity(tripDetailIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_CANCELED);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
