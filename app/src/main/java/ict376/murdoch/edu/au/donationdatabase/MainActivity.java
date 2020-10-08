package ict376.murdoch.edu.au.donationdatabase;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
// import android.support.v7.app.AppCompatActivity;

public class MainActivity extends Activity{//AppCompatActivity {

    ContactListFragment contactListFragment;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout);

        if (savedInstanceState == null) {
            contactListFragment = ContactListFragment.newInstance();
            getFragmentManager().beginTransaction().add(R.id.contactlist_fragment_container, contactListFragment).commit();
        }else{
            contactListFragment = (ContactListFragment)getFragmentManager().findFragmentById(R.id.contactlist_fragment_container);
        }
    }

}

