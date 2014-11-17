package com.example.androidforios.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.androidforios.app.R;
import com.example.androidforios.app.adapters.TripArrayAdapter;
import com.example.androidforios.app.data.managers.DataManager;
import com.example.androidforios.app.data.model.Trip;
import com.example.androidforios.app.data.model.TripList;
import com.smb.loadingbananapeel.LoadingBananaPeelView;

import java.util.Random;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.androidforios.app.fragments.TripListFragment.TripListFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TripListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TripListFragment extends Fragment implements LoadingBananaPeelView.BananaPeelActionListener {

    /**
     * The configuration flags for the Trip List Fragment.
     */
    public static final class TripListFragmentState {
        public static final String KEY_FRAGMENT_TRIP_LIST_LINE_TYPE = "KEY_FRAGMENT_TRIP_LIST_LINE_TYPE";
        public static final String KEY_FRAGMENT_TRIP_LIST_DATA = "KEY_FRAGMENT_TRIP_LIST_DATA";
    }

    protected TripList.LineType mLineType;
    protected TripArrayAdapter mTripArrayAdapter;
    protected TripListFragmentInteractionListener mListener;
    protected TripListViewHolder mViewHolder;

    protected Loader<TripList> mLoader;
    private Random mRandom = new Random();

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param lineType the subway line to show trips for.
     * @return A new instance of fragment TripListFragment.
     */
    public static TripListFragment newInstance(TripList.LineType lineType) {
        TripListFragment fragment = new TripListFragment();
        Bundle args = new Bundle();
        args.putString(TripListFragmentState.KEY_FRAGMENT_TRIP_LIST_LINE_TYPE, lineType.getLineName());
        fragment.setArguments(args);
        return fragment;
    }

    public TripListFragment() { }

    protected TripList mTripList;
    protected void setTripList(TripList tripList) {
        Bundle arguments = this.getArguments();
        arguments.putParcelable(TripListFragmentState.KEY_FRAGMENT_TRIP_LIST_DATA, tripList);
        mTripList = tripList;
        if (mTripArrayAdapter != null) {
            mTripArrayAdapter.clear();
            mTripArrayAdapter.addAll(mTripList.trips);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLineType = TripList.LineType.getLineType(getArguments().getString(TripListFragmentState.KEY_FRAGMENT_TRIP_LIST_LINE_TYPE));
            mTripList = getArguments().getParcelable(TripListFragmentState.KEY_FRAGMENT_TRIP_LIST_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewHolder = new TripListViewHolder();
        return inflater.inflate(mViewHolder.getLayoutResourceID(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewHolder.populate(view);

        mTripArrayAdapter = new TripArrayAdapter(getActivity());
        mViewHolder.tripListView.setAdapter(mTripArrayAdapter);
        mViewHolder.tripListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    mListener.onTripSelected(mTripArrayAdapter.getItem(position));
                }
            }
        });
        mViewHolder.loadingBananaPeelView.setBananaPeelListener(this);

        mLoader = getLoaderManager().initLoader(0, savedInstanceState, new LoaderManager.LoaderCallbacks<TripList>() {
            @Override
            public Loader<TripList> onCreateLoader(int id, Bundle args) {
                return new DataManager.SubwayLineLoader(getActivity(), mLineType);
            }

            @Override
            public void onLoadFinished(Loader<TripList> loader, TripList data) {
                setTripList(data);

                if (mViewHolder != null) {
//                    if (mRandom .nextInt() % 2 == 0) {
                        mViewHolder.loadingBananaPeelView.showContent();
//                    } else {
//                        mViewHolder.loadingBananaPeelView.showBananaPeel();
//                    }
                }
            }

            @Override
            public void onLoaderReset(Loader<TripList> loader) { }
        });

        this.loadData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (TripListFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement SubwayLineFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void loadData() {
        if (mLoader != null) {
            if (mViewHolder != null && mViewHolder.loadingBananaPeelView != null) {
                mViewHolder.loadingBananaPeelView.showLoading();
            }
            mLoader.forceLoad();
        }
    }

    /**
     * This interface is for acting on when the banapeel's empty stat is clicked.
     */
    @Override
    public void onBananaPeelClick() {
        this.loadData();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface TripListFragmentInteractionListener {
        public void onTripSelected(Trip trip);
    }

    private static class TripListViewHolder {
        private static final int LAYOUT_RESOURCE_ID = R.layout.fragment_subway_trip_list;

        ListView tripListView;
        LoadingBananaPeelView loadingBananaPeelView;

        public void populate(View view) {
            loadingBananaPeelView = (LoadingBananaPeelView) view.findViewById(R.id.fragment_content_loading_view);
            tripListView = (ListView) loadingBananaPeelView.getContentView();
        }

        public int getLayoutResourceID() {
            return LAYOUT_RESOURCE_ID;
        }
    }
}
