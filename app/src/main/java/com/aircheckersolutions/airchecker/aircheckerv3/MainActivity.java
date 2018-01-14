package com.aircheckersolutions.airchecker.aircheckerv3;

import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.*;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    TextView result;
    private ViewPager mViewPager;
    ListView listview;

    public Pollutant[] pollutants;
    public Pollen[] pollen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        pollutants = new Pollutant[6];
        pollutants[0] = new Pollutant("PM25",0.0f,true, R.drawable.bgitemtest);
        pollutants[1] = new Pollutant("PM10",0.0f,true, R.drawable.bgitemtest);
        pollutants[2] = new Pollutant("Ozone",0.0f,true, R.drawable.bgitemtest);
        pollutants[3] = new Pollutant("Nitrogene Dioxide",0.0f,true, R.drawable.bgitemtest);
        pollutants[4] = new Pollutant("Sulfur Dioxide",0.0f,true, R.drawable.bgitemtest);
        pollutants[5] = new Pollutant("Carbone Monooxide",0.0f,true, R.drawable.bgitemtest);

        pollen = new Pollen[8];
        pollen[0] = new Pollen("Ambrosia","",true, R.drawable.bgitemtest);
        pollen[1] = new Pollen("Beifuß","",true, R.drawable.bgitemtest);
        pollen[2] = new Pollen("Birke","",true, R.drawable.bgitemtest);
        pollen[3] = new Pollen("Erle","",true, R.drawable.bgitemtest);
        pollen[4] = new Pollen("Esche","",true, R.drawable.bgitemtest);
        pollen[5] = new Pollen("Gräser","",true, R.drawable.bgpollengrass);
        pollen[6] = new Pollen("Haselnuss","",true, R.drawable.bgitemtest);
        pollen[7] = new Pollen("Roggen","",true, R.drawable.bgpollenrye);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        ImageButton fab = (ImageButton) findViewById(R.id.btn_settings);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String httpCode = GetWebsite("https://api.waqi.info/feed/here/?token=16be779fb1a58ee9de6a3f1848a345ada4f4bfc6");
                try {
                    JSONObject obj = new JSONObject(httpCode);
                    pollutants[0].currentValue = Float.parseFloat(obj.getJSONObject("data").getJSONObject("iaqi").getJSONObject("pm25").getString("v"));
                    pollutants[1].currentValue = Float.parseFloat(obj.getJSONObject("data").getJSONObject("iaqi").getJSONObject("pm10").getString("v"));
                    pollutants[2].currentValue = Float.parseFloat(obj.getJSONObject("data").getJSONObject("iaqi").getJSONObject("o3").getString("v"));
                    pollutants[3].currentValue = Float.parseFloat(obj.getJSONObject("data").getJSONObject("iaqi").getJSONObject("no2").getString("v"));
                    pollutants[4].currentValue = Float.parseFloat(obj.getJSONObject("data").getJSONObject("iaqi").getJSONObject("so2").getString("v"));
                    pollutants[5].currentValue = Float.parseFloat(obj.getJSONObject("data").getJSONObject("iaqi").getJSONObject("co").getString("v"));

                    for(int i = 0; i < 6; i++){
                        pollutants[i].percentValue = pollutants[i].currentValue / pollutants[i].max;
                        if(pollutants[i].percentValue < 0.2f){
                            pollutants[i].status = 1;
                        }
                        if(pollutants[i].percentValue < 0.5f && pollutants[i].percentValue >= 0.2f){
                            pollutants[i].status  = 2;
                        }
                        if(pollutants[i].percentValue < 0.8f && pollutants[i].percentValue >= 0.5f){
                            pollutants[i].status  = 3;
                        }
                        if(pollutants[i].percentValue <= 1.0f && pollutants[i].percentValue >= 0.8f){
                            pollutants[i].status  = 4;
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }

                GetWebsiteJS();

                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

                mViewPager = (ViewPager) findViewById(R.id.container);
                mViewPager.setAdapter(mSectionsPagerAdapter);
                mSectionsPagerAdapter.GetData();

            }
        });

    }

    public Pollutant[] GetPollutants(){
        return pollutants;
    }
    public Pollen[] GetPollen(){
        return pollen;
    }

    private void GetWebsiteJS() {

        final StringBuilder builder = new StringBuilder();
        final String[] pollenValues = new String[8];
        try {
            Document doc = Jsoup.connect("http://www.wetter.com/gesundheit/pollenflug/").get();
            String title = doc.title();
            Elements links = doc.select("a[href]");
            Elements myin = doc.getElementsByClass("text--small portable-hide");
            int counter = 0;
            for (Element item : myin) {
                System.out.println(item.childNode(0).toString());
                pollenValues[counter] = item.childNode(0).toString();
                counter++;
            }

        } catch (IOException e) {
            builder.append("Error : ").append(e.getMessage()).append("\n");
        }

        for(int i = 0; i<8;i++){
            pollen[i].currentValue = pollenValues[i];
            if(pollenValues[i].equals("Keine Belastung"));
                pollen[i].status = 0;
            if(pollenValues[i].equals("Geringe Belastung"))
                pollen[i].status = 30;
            if(pollenValues[i].equals("Hohe Belastung"))
                pollen[i].status = 60;
        }
    }

    public String GetWebsite(String website){
        String resString = "";
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(website);
        try{
            HttpResponse response;
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            InputStream is = entity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"windows-1251"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) !=  null){
                sb.append(line + "\n");
            }
            resString = sb.toString();
            is.close();
        }
        catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "ERROR!", Toast.LENGTH_SHORT).show();
        }
        return  resString;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        Simple simpFrag;
        Detailed detlFrag;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    simpFrag = new Simple();
                    return  simpFrag;
                case 1:
                    detlFrag = new Detailed();
                    return detlFrag;
            }
            return  null;
        }

        public void GetData(){
            MainActivity activity = MainActivity.this;
            ImageView ivPollutant = (ImageView)findViewById(R.id.iv_pollutants);
            ImageView ivPollen = (ImageView)findViewById(R.id.iv_pollen);

            Pollutant[] arrPollutant = activity.GetPollutants();
            Pollen[] arrPollen = activity.GetPollen();
            int statusMaxPollutant = 1;
            int statusMaxPollen = 1;

            for (int i = 0; i < arrPollutant.length; i++){
                if(arrPollutant[i].status > statusMaxPollutant)
                    statusMaxPollutant = arrPollutant[i].status;
            }
            for (int i = 0; i < arrPollen.length; i++){
                if(arrPollen[i].status > statusMaxPollen)
                    statusMaxPollen = arrPollen[i].status;
            }

            if(statusMaxPollutant == 1)
                ivPollutant.setImageResource(R.drawable.smiley_happy);
            if(statusMaxPollutant == 2)
                ivPollutant.setImageResource(R.drawable.smiley_neutral);
            if(statusMaxPollutant == 3)
                ivPollutant.setImageResource(R.drawable.smiley_sad);
            if(statusMaxPollutant == 4)
                ivPollutant.setImageResource(R.drawable.smiley_hazard);

            if(statusMaxPollen == 0)
                ivPollen.setImageResource(R.drawable.smiley_happy);
            if(statusMaxPollen == 30)
                ivPollen.setImageResource(R.drawable.smiley_neutral);
            if(statusMaxPollen == 60)
                ivPollen.setImageResource(R.drawable.smiley_sad);
            if(statusMaxPollen == 90)
                ivPollen.setImageResource(R.drawable.smiley_hazard);

            //Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
    }
}
