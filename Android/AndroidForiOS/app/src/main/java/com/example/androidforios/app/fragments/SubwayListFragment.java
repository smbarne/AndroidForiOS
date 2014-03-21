package com.example.androidforios.app.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.androidforios.app.R;
import com.example.androidforios.app.adapters.LineTypeArrayAdapter;
import com.example.androidforios.app.data.model.TripList;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubwayListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubwayListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SubwayListFragment extends Fragment {

    protected OnFragmentInteractionListener mListener;
    protected SubwayListFragmentViewHolder viewHolder;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SubwayListFragment.
     */
    public static SubwayListFragment newInstance() {
        return new SubwayListFragment();
    }

    public SubwayListFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Initialize the convienence viewholder class and inflate the layout for
        // this fragment
        viewHolder = new SubwayListFragmentViewHolder();
        return inflater.inflate(viewHolder.getLayoutResourceID(), container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewHolder.populate(view);

        LineTypeArrayAdapter listDataAdapter = new LineTypeArrayAdapter(getActivity(), TripList.LineType.values());
        viewHolder.subwayListView.setAdapter(listDataAdapter);

        viewHolder.subwayListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mListener != null) {
                    // TODO: Go to next activity with lineType
                }
            }
        });

        viewHolder.helloWorldButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.application_description, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    // TODO: Rename
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onLineTypeSelected(TripList.LineType lineType);
    }

    private static class SubwayListFragmentViewHolder {
        private static final int LAYOUT_RESOURCE_ID = R.layout.fragment_subway_list;

        ListView subwayListView;
        Button helloWorldButton;

        public void populate(View view) {
            subwayListView = (ListView) view.findViewById(R.id.fragment_subway_list_listview);
            helloWorldButton= (Button) view.findViewById(R.id.fragment_subway_list_Button);
        }

        public int getLayoutResourceID() {
            return LAYOUT_RESOURCE_ID;
        }
    }
}
