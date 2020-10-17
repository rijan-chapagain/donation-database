package ict376.murdoch.edu.au.donationdatabase;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;

public class DisplayNewDonatorActivity extends Activity {

    DonatorDetailsFragment donatorDetailsFragment;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.details_activity_layout);

        Bundle extras = getIntent().getExtras();
        int ix = -1;
        if(extras !=null)
            ix = extras.getInt("id", -1);

        if (savedInstanceState == null) {
            donatorDetailsFragment = DonatorDetailsFragment.newInstance(ix);
            getFragmentManager().beginTransaction().add(R.id.donatordetails_fragment_container, donatorDetailsFragment).commit();
        }else{
            donatorDetailsFragment = (DonatorDetailsFragment) getFragmentManager().findFragmentById(R.id.donatordetails_fragment_container);
        }
    }
}