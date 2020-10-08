package ict376.murdoch.edu.au.donationdatabase;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
// import android.support.v7.app.AppCompatActivity;

public class MainActivity extends Activity{//AppCompatActivity {

    DonatorListFragment donatorListFragment;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        if (savedInstanceState == null) {
            donatorListFragment = DonatorListFragment.newInstance();
            getFragmentManager().beginTransaction().add(R.id.donatorlist_fragment_container, donatorListFragment).commit();
        }else{
            donatorListFragment = (DonatorListFragment)getFragmentManager().findFragmentById(R.id.donatorlist_fragment_container);
        }
    }

}

