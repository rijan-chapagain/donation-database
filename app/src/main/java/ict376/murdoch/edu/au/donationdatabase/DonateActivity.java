package ict376.murdoch.edu.au.donationdatabase;

/**
 * Created by Hamid on 5/10/2017.
 */

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;

public class DonateActivity extends Activity {

    DonatorDetailsFragment donatorDetailsFragment;

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.donate_activity_layout);

    }

    /*
    public void onDeleteContactClick(View v){

        donatorDetailsFragment.onDeleteContactClick(v);


    }

    public void onEditContactClick(View v){

        donatorDetailsFragment.onEditContactClick(v);

    }

    public void run(View view)    {

        donatorDetailsFragment.run(view);

        //Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        //startActivity(intent);

    }
    */
}