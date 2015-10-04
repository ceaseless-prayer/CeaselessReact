package org.theotech.ceaselessandroid;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.theotech.ceaselessandroid.image.ImageURLServiceImpl;
import org.theotech.ceaselessandroid.person.Person;
import org.theotech.ceaselessandroid.scripture.ScriptureData;
import org.theotech.ceaselessandroid.scripture.ScriptureServiceImpl;

import java.util.ArrayList;
import java.util.List;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView verseImage = (ImageView) findViewById(R.id.verse_image);
        verseImage.setImageResource(R.drawable.icon_76);

        populatePrayForPeopleList();

        // asynchronous fetchers
        new ScriptureFetcher().execute();
        new ImageFetcher().execute();
        // Notification service code. 
        alarmMethod();
    }

    private void alarmMethod(){
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        Intent myIntent = new Intent(this , NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, PendingIntent.FLAG_NO_CREATE);
        //FLAG_NO_CREATE means this will return null if there is already a pending intent
        if (pendingIntent == null) {
            pendingIntent = PendingIntent.getService(this, 0, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Log.d(TAG, "Setting reminder notification alarm");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.SECOND, 0);
            //calendar.add(Calendar.SECOND, 3);
            calendar.set(Calendar.MINUTE, 30);
            calendar.set(Calendar.HOUR, 8);
            calendar.set(Calendar.AM_PM, Calendar.AM);
            calendar.add(Calendar.DAY_OF_MONTH, 1);

            alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), 1000 * 60 * 60 * 24, pendingIntent);
        } else {
            Log.d(TAG, "Not setting reminder notification alarm. Already set.");
            //PendingIntent.getService(this, 0, myIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        }
    }

    private void populatePrayForPeopleList() {
        List<Person> persons = new ArrayList<Person>();
        persons.add(new Person("ID1", "1NAME"));
        persons.add(new Person("ID2", "2NAME"));
        persons.add(new Person("ID3", "3NAME"));
        LinearLayout prayForPeopleList = (LinearLayout) findViewById(R.id.pray_for_people_list);
        for (int i = 0; i < persons.size(); i++) {
            View row = getLayoutInflater().inflate(R.layout.pray_for_people_list, null);
            TextView textView = (TextView) row.findViewById(R.id.pray_for_person_name);
            textView.setText(persons.get(i).toString());
            ImageView imageView = (ImageView) row.findViewById(R.id.pray_for_person_image);
            imageView.setImageResource(R.drawable.icon_76);
            prayForPeopleList.addView(row);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent homeIntent = new Intent(this, MainActivity.class);
            startActivity(homeIntent);
        } else if (id == R.id.nav_people) {
            Intent peopleIntent = new Intent(this, PeopleTabbedActivity.class);
            startActivity(peopleIntent);
        } else if (id == R.id.nav_verse) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_contact_us) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class ScriptureFetcher extends AsyncTask<String, Void, ScriptureData> {

        @Override
        protected ScriptureData doInBackground(String... params) {
            return new ScriptureServiceImpl().getScripture();
        }

        @Override
        protected void onPostExecute(ScriptureData scripture) {
            if (scripture != null) {
                Log.d(TAG, "scripture = " + scripture.getJson());

                TextView verseTitle = (TextView) findViewById(R.id.verse_title);
                verseTitle.setText(scripture.getCitation());

                TextView verseText = (TextView) findViewById(R.id.verse_text);
                verseText.setText(scripture.getText());
            } else {
                Log.e(TAG, "Could not fetch scripture!");

                TextView verseTitle = (TextView) findViewById(R.id.verse_title);
                verseTitle.setText("Matthew 21:22");

                TextView verseText = (TextView) findViewById(R.id.verse_text);
                verseText.setText("And whatever you ask in prayer, you will receive, if you have faith.\"");
            }
        }
    }

    private class ImageFetcher extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {
                return new ImageURLServiceImpl().getImageURL();
            }

            @Override
            protected void onPostExecute(String imageUrl) {
                if (imageUrl != null) {
                    Log.d(TAG, "imageUrl = " + imageUrl);

                    // TODO: Download the image and display using picasso
                } else {
                    Log.e(TAG, "Could not fetch scripture!");
                }
            }
    }
}
