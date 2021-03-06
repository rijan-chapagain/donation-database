package ict376.murdoch.edu.au.donationdatabase;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public  class DonatorDetailsFragment extends Fragment {

    private DBHelper mydb ;

    View mLayoutView;

    TextView name ;
    TextView phone;
    TextView email;
    TextView street;
    TextView place;

    int id_To_Update = 0;

    // the buttons
    Button mEditButton;
    Button mSaveButton;
    Button mDeleteButton;
    Button mDonateButton;
    Button mBackButton;

    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static DonatorDetailsFragment newInstance(int index) {

        DonatorDetailsFragment f = new DonatorDetailsFragment();

        // Supply index input as an argument.
        // Google recommends using bundles to pass in arguments
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    // Retrieve the index of the donator that will be displayed
    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        mLayoutView = inflater.inflate(R.layout.donator_details_layout, null);

        return mLayoutView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (mLayoutView == null)
            return;

        name   = (TextView) mLayoutView.findViewById(R.id.editTextName);
        phone  = (TextView) mLayoutView.findViewById(R.id.editTextPhone);
        street = (TextView) mLayoutView.findViewById(R.id.editTextStreet);
        email  = (TextView) mLayoutView.findViewById(R.id.editTextEmail);
        place  = (TextView) mLayoutView.findViewById(R.id.editTextCity);

        mydb = new DBHelper(getActivity());

        mSaveButton = (Button)mLayoutView.findViewById(R.id.button1);
//        mEditButton.setVisibility(View.INVISIBLE);

//        mDeleteButton = (Button)mLayoutView.findViewById(R.id.button_delete);
//        mDeleteButton.setVisibility(View.INVISIBLE);

        //  Bundle extras = getIntent().getExtras();

        int Value = getShownIndex();



        // Toast.makeText(getActivity(), Integer.toString(Value), Toast.LENGTH_SHORT).show();

        if(Value>0){

            Cursor rs = mydb.getData(Value);

            id_To_Update = Value;
            rs.moveToFirst();

            String nam   = rs.getString(rs.getColumnIndex(DBHelper.DONATORS_COLUMN_NAME));
            String phon  = rs.getString(rs.getColumnIndex(DBHelper.DONATORS_COLUMN_PHONE));
            String emai  = rs.getString(rs.getColumnIndex(DBHelper.DONATORS_COLUMN_EMAIL));
            String stree = rs.getString(rs.getColumnIndex(DBHelper.DONATORS_COLUMN_STREET));
            String plac  = rs.getString(rs.getColumnIndex(DBHelper.DONATORS_COLUMN_CITY));

            if (!rs.isClosed()) {
                rs.close();
            }

            // Toast.makeText(getActivity(), nam, Toast.LENGTH_SHORT).show();
            setButtonsToViewMode();

            name.setText((CharSequence)nam);
            name.setFocusable(false);
            name.setClickable(false);
            name.setFocusableInTouchMode(false);
            name.setEnabled(false);

            phone.setText((CharSequence)phon);
            phone.setFocusable(false);
            phone.setClickable(false);
            phone.setFocusableInTouchMode(false);
            phone.setEnabled(false);

            email.setText((CharSequence)emai);
            email.setFocusable(false);
            email.setClickable(false);
            email.setFocusableInTouchMode(false);
            email.setEnabled(false);

            street.setText((CharSequence)stree);
            street.setFocusable(false);
            street.setClickable(false);
            street.setFocusableInTouchMode(false);
            street.setEnabled(false);

            place.setText((CharSequence)plac);
            place.setFocusable(false);
            place.setClickable(false);
            place.setFocusableInTouchMode(false);
            place.setEnabled(false);

        }

        // setting the listeners for the buttons
        mSaveButton   = (Button) getActivity().findViewById(R.id.button1);
//        mDeleteButton = (Button) getActivity().findViewById(R.id.button_delete);
        mDonateButton = (Button) getActivity().findViewById(R.id.button_donate);
//        mBackButton = (Button) getActivity().findViewById(R.id.button_back);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run();
                setButtonsToViewMode();

                Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setButtonsToViewMode(){
        mSaveButton.setVisibility(View.INVISIBLE);
//        mEditButton.setVisibility(View.VISIBLE);
//        mDeleteButton.setVisibility(View.VISIBLE);
    }

    public void run()     {

        int Value = getShownIndex();

        if(Value>0){

            // Updating a donator
            if(mydb.updateDonator(id_To_Update,name.getText().toString(), phone.getText().toString(), email.getText().toString(), street.getText().toString(), place.getText().toString())){
                Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                 Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getActivity().getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            // inserting a new donator
            if(mydb.insertDonator(name.getText().toString(), phone.getText().toString(), email.getText().toString(), street.getText().toString(), place.getText().toString())){
                Toast.makeText(getActivity().getApplicationContext(), "Donator inserted!", Toast.LENGTH_SHORT).show();

                // disable inserting  the same donator again


            }else{
                Toast.makeText(getActivity().getApplicationContext(), "Donator NOT inserted. ", Toast.LENGTH_SHORT).show();
            }

            // I don't want to start the same activity in which the fragment is running
            // I want just to refresh the display of a fragment


            //Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
            //startActivity(intent);
        }

        // Refresh the fragment in which the list of data is re-displayed
        DonatorListFragment donatorListFragment = (DonatorListFragment)getFragmentManager().findFragmentById(R.id.donatorlist_fragment_container);
        if (donatorListFragment!=null){
            donatorListFragment.refresh();
        }


    }

}
