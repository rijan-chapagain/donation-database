package ict376.murdoch.edu.au.donationdatabase;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;

public class DisplayDonateActivity extends Activity {

    DonateFragment donateFragment;

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