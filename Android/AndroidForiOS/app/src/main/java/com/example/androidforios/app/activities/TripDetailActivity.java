package com.example.androidforios.app.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MenuItem;

import com.example.androidforios.app.R;
import com.example.androidforios.app.data.model.Trip;
import com.example.androidforios.app.fragments.TripDetailFragment;

/**
 * Created by stephen.barnes on 3/23/14.
 */
public class TripDetailActivity extends FragmentActivity {

    public static Intent getTripDetailActivityIntent(Context context, Trip trip) {
        Intent intent = new Intent(context, TripDetailActivity.class);
        intent.putExtra(TripDetailActivityState.KEY_ACTIVITY_TRIP_OBJECT, trip);
        return intent;
    }

    public static final class TripDetailActivityState {
        public static final String KEY_ACTIVITY_TRIP_OBJECT = "KEY_ACTIVITY_TRIP_OBJECT";
    }

    Trip mTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_trip_detail);

        mTrip = getIntent().getParcelableExtra(TripDetailActivityState.KEY_ACTIVITY_TRIP_OBJECT);
        setTitle(mTrip.destination);

        if (savedInstanceState == null) {
            TripDetailFragment tripDetailFragment = TripDetailFragment.newInstance(mTrip);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_trip_detail_container, tripDetailFragment)
                    .commit();
        }
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
