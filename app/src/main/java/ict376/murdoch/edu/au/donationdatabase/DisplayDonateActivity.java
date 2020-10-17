package ict376.murdoch.edu.au.donationdatabase;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;

public class DisplayDonateActivity extends Activity {

    AmountDetailsFragment donateFragment;
    private static int id_index;

    public static DisplayDonateActivity newInstance(int index){

        DisplayDonateActivity f = new DisplayDonateActivity();
        Bundle args = new Bundle();
        args.putInt("index", index);
        id_index = index;

        return f;
    }

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
            donateFragment = AmountDetailsFragment.newInstance(ix, id_index);
            getFragmentManager().beginTransaction().add(R.id.donatordetails_fragment_container, donateFragment).commit();
        }else{
            donateFragment = (AmountDetailsFragment) getFragmentManager().findFragmentById(R.id.donatordetails_fragment_container);
        }
    }
}