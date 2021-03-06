package com.example.walkinthepark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class ProfHomeActivity extends AppCompatActivity {

    private DatabaseReference myRef;
    private FirebaseDatabase db;
    private  ArrayList<Map> pacientes;

    String prof_email;
    String prof_name;
    // Fragmentos
    static ProfHomeFragment profHomeFragment;
    static CalibrationFragment calibrationFragment;
    static SettingsFragment settingsFragment;
    static AboutFragment aboutFragment;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof_home);

        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        Map m = new HashMap<String,Map>();
        List<HashMap<String, User>> list = new ArrayList<>();

        prof_email = getIntent().getStringExtra("user_email");
        prof_name = getIntent().getStringExtra("user_name");

        // Inicializar fragmentos
        if(profHomeFragment == null) {
            profHomeFragment = new ProfHomeFragment();
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

        DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);
        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationMenuFisio);

        View headerView = navigationView.getHeaderView(0);
        TextView nomeText = headerView.findViewById(R.id.username);
        nomeText.setText(prof_name.toUpperCase());
        TextView fisioText = headerView.findViewById(R.id.usertype);
        fisioText.setText("Fisioterapeuta");

        navigationView.getMenu().findItem(R.id.logoutAc).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(getApplicationContext(), "Fez logout com sucesso", Toast.LENGTH_SHORT).show();
                Intent l = new Intent(ProfHomeActivity.this, LoginActivity.class);
                l.putExtra("logout", "true");
                startActivity(l);
                finish();
                return true;
            }
        });

        navController = Navigation.findNavController(this, R.id.navProfFragment);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    public String getCurrentProfEmail(){
        return this.prof_email;
    }
}