package com.example.walkinthepark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private String s;

    private RecyclerViewListener listener;
    private ArrayList<HashMap<String, String>> mNotes;
    private FirebaseDatabase db;
    private DatabaseReference myRef;
    private String nomeF;
    private String emailF;
    private String passwordF;
    private boolean p = true;
    private Map mapUsers = new HashMap<String, User>();


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tituloTextView;
        public TextView mensagemTextView;
        public ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            tituloTextView = (TextView) itemView.findViewById(R.id.txtTitulo);
            mensagemTextView = (TextView) itemView.findViewById(R.id.txtMensagem);
            deleteButton = (ImageButton) itemView.findViewById(R.id.deleteButton);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(listener != null && getAdapterPosition() != RecyclerView.NO_POSITION){
                listener.onClick(v, getAdapterPosition());
            }
        }
    }

    public NotesAdapter(ArrayList<HashMap<String, String>> notes, RecyclerViewListener listener, String user_email){
        mNotes = notes;
        this.listener = listener;
        this.s = user_email;
    }


    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View notesView = inflater.inflate(R.layout.single_note,parent,false);
        NotesAdapter.ViewHolder viewHolder = new NotesAdapter.ViewHolder(notesView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(NotesAdapter.ViewHolder holder, int position) {
        HashMap<String, String> note = mNotes.get(position);
        TextView textViewTitulo = holder.tituloTextView;
        textViewTitulo.setText(note.get("titulo"));
        TextView textViewMensagem = holder.mensagemTextView;
        textViewMensagem.setText(note.get("mensagem"));
        ImageButton delButton = holder.deleteButton;
        db = FirebaseDatabase.getInstance("https://walk-in-the-park---cm-default-rtdb.firebaseio.com/");
        myRef = db.getReference("User");
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot ds : snapshot.getChildren()){
                            if(s.equals(ds.child("email").getValue().toString())){
                                nomeF = ds.child("nome").getValue().toString();
                                emailF = ds.child("email").getValue().toString();
                                passwordF = ds.child("password").getValue().toString();

                                ArrayList a = (ArrayList) ((Map) ds.getValue()).get("listaNotas");
                                try{
                                    a.remove(position+1);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position,getItemCount()+1);
                                }catch(IndexOutOfBoundsException e){
                                    System.out.println("a");
                                }

                                HashMap result = new HashMap<>();
                                result.put("nome", nomeF);
                                result.put("email", emailF);
                                result.put("password", passwordF);
                                result.put("paciente", true);
                                result.put("fisioID", ds.child("fisioID").getValue());
                                result.put("listaNotas", a);
                                result.put("listaLembretes", ds.child("listaLembretes").getValue());
                                result.put("listaMoods", ds.child("listaMoods").getValue());
                                result.put("listaExercicios", ds.child("listaExercicios").getValue());
                                mapUsers.put(s, result);
                            }
                        }
                        if(p) {
                            myRef.updateChildren(mapUsers);
                            p = false;
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                mNotes.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mNotes.size());

            }
        });
    }

    @Override
    public int getItemCount() {
        return mNotes.size();
    }

    public interface RecyclerViewListener {
        void onClick(View v, int position);
    }
}

