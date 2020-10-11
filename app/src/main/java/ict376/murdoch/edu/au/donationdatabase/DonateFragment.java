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
public  class DonateFragment extends Fragment {

    private DBHelper mydb ;

    View mLayoutView;

    TextView amount ;
    TextView date;

    int id_To_Update = 0;

    // the buttons
    Button mBackButton;
    Button mSubmitButton;

    /**
     * Create a new instance of DetailsFragment, initialized to
     * show the text at 'index'.
     */
    public static DonateFragment newInstance(int index) {

        DonateFragment f = new DonateFragment();

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

        amount   = (TextView) mLayoutView.findViewById(R.id.editTextAmount);
        date  = (TextView) mLayoutView.findViewById(R.id.editTextDate);

        mydb = new DBHelper(getActivity());

        mSubmitButton = (Button)mLayoutView.findViewById(R.id.button_submit);
        mBackButton = (Button)mLayoutView.findViewById(R.id.button_back);

        //  Bundle extras = getIntent().getExtras();

        int Value = getShownIndex();

         Toast.makeText(getActivity(), Integer.toString(Value), Toast.LENGTH_SHORT).show();

        if(Value>0){
            //means this is in view mode - not the add donator part.

             Toast.makeText(getActivity(), Integer.toString(Value), Toast.LENGTH_SHORT).show();

            Cursor rs = mydb.getData(Value);
            id_To_Update = Value;
            rs.moveToFirst();

            String amoun   = rs.getString(rs.getColumnIndex(DBHelper.AMOUNTSDONATED_COLUMN_AMOUNT_DONATED));
            String dat  = rs.getString(rs.getColumnIndex(DBHelper.AMOUNTSDONATED_COLUMN_DATE));

            if (!rs.isClosed()) {
                rs.close();
            }

             Toast.makeText(getActivity(), amoun, Toast.LENGTH_SHORT).show();
//            setButtonsToViewMode();

            amount.setText((CharSequence)amoun);

            date.setText((CharSequence)dat);
        }

        // setting the listeners for the buttons
        mSubmitButton   = (Button) getActivity().findViewById(R.id.button_submit);
        mBackButton = (Button) getActivity().findViewById(R.id.button_back);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                run();
                onSubmitButtonCLick();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackButtonClick();
            }
        });
    }

    public  void onSubmitButtonCLick() {
        Toast.makeText(getActivity().getApplicationContext(), "Back to home", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }

    public void onBackButtonClick(){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(R.string.deleteDonator)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mydb.deleteDonator(id_To_Update);
                        Toast.makeText(getActivity().getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                        startActivity(intent);  // back to main activity
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        AlertDialog d = builder.create();
        d.setTitle("Are you sure?");
        d.show();

    }

    public void run()     {

        int Value = getShownIndex();
        if(Value>0){

            // Updating a donator
            if(mydb.updateDonatedAmount(id_To_Update, amount.getText().toString(), date.getText().toString())){
                Toast.makeText(getActivity().getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();
                // Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
                //startActivity(intent);
            }
            else{
                Toast.makeText(getActivity().getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            // inserting a new donator
            if(mydb.insertDonatedAmount(amount.getText().toString(), date.getText().toString())){
                Toast.makeText(getActivity().getApplicationContext(), "Donated amount inserted!", Toast.LENGTH_SHORT).show();

                // disable inserting  the same donator again


            }else{
                Toast.makeText(getActivity().getApplicationContext(), "Donated amount NOT inserted. ", Toast.LENGTH_SHORT).show();
            }

            // I don't want to start the same activity in which the fragment is running
            // I want just to refresh the display of a fragment


            //Intent intent = new Intent(getActivity().getApplicationContext(),MainActivity.class);
            //startActivity(intent);
        }

        // Refresh the fragment in which the list of data is re-displayed
//        DonatorListFragment donatorListFragment = (DonatorListFragment)getFragmentManager().findFragmentById(R.id.donatorlist_fragment_container);
//        if (donatorListFragment!=null){
//            donatorListFragment.refresh();
//        }


    }

}
