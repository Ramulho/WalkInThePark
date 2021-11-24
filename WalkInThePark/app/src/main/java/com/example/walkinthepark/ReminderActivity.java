package com.example.walkinthepark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ReminderActivity extends AppCompatActivity {
    private static ReminderFragment reminderFragment;
    private static NewReminderFragment newReminderFragment;
    Button bAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        if(newReminderFragment == null){
            newReminderFragment = new NewReminderFragment();
        }

        if(reminderFragment == null){
            reminderFragment = new ReminderFragment();
        }
        replaceFragment(reminderFragment);

        bAdd = findViewById(R.id.button_add);
        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bAdd.getText().equals("Adicionar")) {
                    bAdd.setText("Ver Lembretes");
                    newReminderFragment = new NewReminderFragment();
                    replaceFragment(newReminderFragment);
                } else if (bAdd.getText().equals("Ver Lembretes")) {
                    bAdd.setText("Adicionar");
                    replaceFragment(reminderFragment);
                }
            }
        });

    }


    private void replaceFragment(Fragment reminderFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.all_reminders,reminderFragment);
        fragmentTransaction.commit();
    }
}