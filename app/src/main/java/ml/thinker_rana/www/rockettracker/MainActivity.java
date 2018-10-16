package ml.thinker_rana.www.rockettracker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mTaggle;
    private NavigationView mNavigationView;
    Fragment myFragment;



    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        mTaggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mTaggle);
        mTaggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mTaggle.onOptionsItemSelected(item)){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        android.support.v4.app.Fragment fragment = getVisibleFragment();
        if (fragment instanceof ScheduleFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            mNavigationView.setCheckedItem(R.id.schedule);
        }

        else if (fragment instanceof LiveFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        else if (fragment instanceof LatestFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        else if (fragment instanceof SettingFragment){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }

        else if (fragment instanceof ExitFragment){
            ExitConfirmation();
        }

        else if (fragment instanceof HomeFragment){
            ExitConfirmation();
        }
        else {
            super.onBackPressed();
        }
    }

    public android.support.v4.app.Fragment getVisibleFragment(){
        android.support.v4.app.FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<android.support.v4.app.Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(android.support.v4.app.Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScheduleFragment()).addToBackStack(null).commit();
                mNavigationView.setCheckedItem(R.id.schedule);
                break;
            case R.id.live:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LiveFragment()).addToBackStack(null).commit();
                mNavigationView.setCheckedItem(R.id.live);
                break;
            case R.id.latest_launch:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LatestFragment()).addToBackStack(null).commit();
                break;
            case R.id.setting:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingFragment()).addToBackStack(null).commit();
                break;
            case R.id.exit:
                ExitConfirmation();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }

    private void ExitConfirmation(){
        final AlertDialog.Builder builder =  new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Are you sure want to close this?");
        builder.setCancelable(true);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
