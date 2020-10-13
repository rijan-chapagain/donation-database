package ict376.murdoch.edu.au.donationdatabase;


import android.app.FragmentTransaction;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import 	android.util.Pair;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class DonatorListFragment extends Fragment {

    public final static String EXTRA_MESSAGE = "MESSAGE";
    public final static int REQUEST_CODE_NEW_DONATOR = 1;


    // Views
    boolean mDualPane;
    View    mLayoutView;
    Button  mNewButton;
    private ListView obj;

    // Database
    DBHelper mydb = null;
    ArrayList mArrayList;  // the list of all donators

    public static DonatorListFragment newInstance(){

        DonatorListFragment f = new DonatorListFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mLayoutView = inflater.inflate(R.layout.donator_list_layout, null);
        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        // initialise the DB
        refresh();

        // At the click on an item, start a new activity that will display the content of the database
        obj.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

                // Here either start a new activity or display it on the same window

                // The index of the Donator that will be shown in the new activity DislayActivity.java
                Pair<Integer, String> p = (Pair<Integer, String>)mArrayList.get(position);
                int id_To_Search = p.first; // position + 1;

                // Check whether the details frame is visible
                View detailsFrame = getActivity().findViewById(R.id.donatordetails_fragment_container);
                mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

                if (mDualPane) {

                    // landscape mode
                    // display on the same Activity
                    DonatorDetailsFragment details = DonatorDetailsFragment.newInstance(id_To_Search);

                    // Execute a transaction, replacing any existing fragment
                    // with this one inside the frame.
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.donatordetails_fragment_container, details);

                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();

                }else {
                    // portrait mode
                    Bundle dataBundle = new Bundle();

                    dataBundle.putInt("id", id_To_Search);
                    Intent intent = new Intent(getActivity().getApplicationContext(), DisplayDonatorActivity.class);
                    intent.putExtras(dataBundle);
                    startActivityForResult(intent, REQUEST_CODE_NEW_DONATOR);
                }
            }
        });


        // set the onclick listener for the button
        mNewButton = (Button) getActivity().findViewById(R.id.new_button);

        mNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View detailsFrame = getActivity().findViewById(R.id.donatordetails_fragment_container);
                mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

                if (mDualPane) {
                    // landscape mode
                    // display on the same Activity
                    DonatorDetailsFragment details = DonatorDetailsFragment.newInstance(0);

                    // Execute a transaction, replacing any existing fragment
                    // with this one inside the frame.
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.donatordetails_fragment_container, details);

                    ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                    ft.commit();

                }else {
                // portrait mode
                    Bundle dataBundle = new Bundle();
                    dataBundle.putInt("id", 0);

                    Intent intent = new Intent(getActivity().getApplicationContext(), DisplayDonatorActivity.class);
                    intent.putExtras(dataBundle);   //

                    startActivity(intent);          //
                }

            }
        });



    }

    @Override
    public void onResume() {
        // Log.e("DEBUG", "onResume of LoginFragment");
        super.onResume();

        refresh();
    }

    public void refresh(){

        if (mydb == null)
            mydb = new DBHelper(getActivity());

        // Get all the donators from the database
        mArrayList = mydb.getAllDonators();

        ArrayList<String> array_list = new  ArrayList<String>();

        for (int i=0; i<mArrayList.size(); i++){
            Pair<Integer, String> p = (Pair<Integer, String>)mArrayList.get(i);
            array_list.add(p.second);
        }
        // Put all the donators in an array
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, array_list);

        // Display the donators in the ListView object
        obj = (ListView)mLayoutView.findViewById(R.id.listView1);
        obj.setAdapter(arrayAdapter);

        // Check the orientation of the display
        //mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

        View detailsFrame = getActivity().findViewById(R.id.donatordetails_fragment_container);
        mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

    }



}