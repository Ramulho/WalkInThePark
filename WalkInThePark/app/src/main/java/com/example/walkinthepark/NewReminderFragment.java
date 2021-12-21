package com.example.walkinthepark;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class NewReminderFragment extends Fragment {
    Button bDate;
    Button bTime;
    Button bAdd;
    TextView teste;
    EditText te;
    String time ="";
    String message="";
    String date ="";


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private TextView hora;
    private TextView data;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private List<String> listTitulo;
    private boolean a;
    private Map mapReminders = new HashMap<String,Reminder>();

    public NewReminderFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_reminder, container, false);
        bDate = (Button) view.findViewById(R.id.buttonDate);
        bTime = (Button) view.findViewById(R.id.buttonTime);
        bAdd = (Button) view.findViewById(R.id.buttonAdd);
        data = (TextView) view.findViewById(R.id.textData);
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("Reminder");

        bDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectDate();
            }
        });

        hora = (TextView) view.findViewById(R.id.textHora);

        bTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectTime();
            }
        });

        te = (EditText) view.findViewById(R.id.message);

        bAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataS = data.getText().toString();
                String horaS = hora.getText().toString();
                String text = te.getText().toString();

                if(dataS.equals("Data") || horaS.equals("Hora") || text.equals("")){
                    Toast toast = Toast.makeText(getContext(), "Escolhe uma Data Hora e Lembrete!", Toast.LENGTH_SHORT);
                    toast.show();

                }else {
                    Reminder rem = new Reminder(horaS, dataS, text);
                    //((ReminderActivity) getActivity()).adicionarLembrete(rem);


                    Map reminderValues = rem.toMap();

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (DataSnapshot ds: snapshot.getChildren()){
                                String s = ds.child("titulo").getValue().toString();
                                listTitulo.add(s);
                            }
                            //mapNotes.put(titulo, noteValues);
                            //Toast.makeText(getContext(), "Nota adicionada!", Toast.LENGTH_SHORT).show();
                            //myRef.updateChildren(mapNotes);
                            if(listTitulo.contains(te) && a){
                                Toast.makeText(getContext(), "Ja existe um reminder com este titulo!", Toast.LENGTH_SHORT).show();
                                te.setText("");
                                data.setText("");
                                hora.setText("");


                            }else {

                                if(a) {
                                    //myRef.child("User").child(email);
                                    mapReminders.put(text, reminderValues);
                                    Toast.makeText(getContext(), "Nota adicionada!", Toast.LENGTH_SHORT).show();
                                    myRef.updateChildren(mapReminders);
                                    goToMain(view);
                                    a = false;

                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    Toast toast = Toast.makeText(getContext(), "Lembrete Adicionado!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        return view;
    }

    private void goToMain(View view) {
        Intent i = new Intent(getActivity(), UserHomeActivity.class);
        startActivity(i);
    }


    private void selectTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                time = hour + ":" + minute;
                hora.setText(time+"h");
            }
        },hour,minute,true);
        timePickerDialog.show();
    }

    private void selectDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = day+"-"+(month+1)+"-"+year;
                data.setText(date);
            }
        },year,month,day);
        datePickerDialog.show();
    }

}