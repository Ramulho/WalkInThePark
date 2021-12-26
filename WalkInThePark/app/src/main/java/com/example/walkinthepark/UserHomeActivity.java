package com.example.walkinthepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UserHomeActivity extends AppCompatActivity {

    private DatabaseReference refNotas;
    private DatabaseReference refReminders;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    String user_email;


    // Fragmentos
    static UserHomeFragment userHomeFragment;
    static NotesFragment notesFragment;
    static RemindersFragment remindersFragment;
    static ExerciseFragment exerciseFragment;
    static MoodsFragment moodsFragment;
    static CalibrationFragment calibrationFragment;
    static SettingsFragment settingsFragment;
    static AboutFragment aboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        user_email = getIntent().getStringExtra("user_email");
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        refNotas = db.getReference("Note");
        refReminders = db.getReference("Reminder");
        myRef = db.getReference("User");
        Map m = new HashMap<String,Map>();

        // Inicializar fragmentos
        if(userHomeFragment == null) {
            userHomeFragment = new UserHomeFragment();
        }
        if(notesFragment == null) {
            notesFragment = new NotesFragment();
        }
        if(remindersFragment == null) {
            remindersFragment = new RemindersFragment();
        }
        if(exerciseFragment == null) {
            exerciseFragment = new ExerciseFragment();
        }
        if(moodsFragment == null) {
            moodsFragment = new MoodsFragment();
        }
        if(calibrationFragment == null) {
            calibrationFragment = new CalibrationFragment();
        }
        if(settingsFragment == null) {
            settingsFragment = new SettingsFragment();
        }
        if(aboutFragment == null) {
            aboutFragment = new AboutFragment();
        }

        replaceFragment(userHomeFragment);

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationMenuUser);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Bundle bundle;
                switch (item.getItemId()) {
                    case R.id.menuAc:
                        replaceFragment(userHomeFragment);
                    case R.id.notasAc:
                        bundle = new Bundle();
                        bundle.putString("fragment", "fragNT");
                        notesFragment.setArguments(bundle);
                        replaceFragment(notesFragment);
                        break;
                    case R.id.lembretesAc:
                        bundle = new Bundle();
                        bundle.putString("fragment", "frag1");
                        remindersFragment.setArguments(bundle);
                        replaceFragment(remindersFragment);
                        break;
                    case R.id.videosAc:
                        replaceFragment(exerciseFragment);
                        break;
                    case R.id.humorAc:
                        bundle = new Bundle();
                        bundle.putString("fragment", "fragM");
                        moodsFragment.setArguments(bundle);
                        replaceFragment(moodsFragment);
                        break;
                    case R.id.calibracaoAc:
                        replaceFragment(calibrationFragment);
                        break;
                    case R.id.definicoesAc:
                        replaceFragment(settingsFragment);
                        break;
                    case R.id.ajudaAc:
                        replaceFragment(aboutFragment);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_user, fragment);
        fragmentTransaction.commit();
    }

    public String getCurrentUserEmail(){
        return this.user_email;
    }


}