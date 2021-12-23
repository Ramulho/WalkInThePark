package com.example.walkinthepark;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User {
    private String nome;
    private String password;
    private boolean paciente;
    private String fisioID;
    private String email;
    private List<Note> notas = new ArrayList<Note>();;
    private List<Reminder> lembretes = new ArrayList<Reminder>();;
    private List<User> listaPacientes;


    public User(String nome, String email, String password, String fisioID, boolean fisioSN) {
        this.nome = nome;
        this.password = password;
        this.email = email;
        this.paciente = fisioSN;
        this.listaPacientes = new ArrayList<User>();
        if(!paciente)
            listaPacientes.add(new User("", "", "", "", true));
        if(!fisioID.equals("")){
            this.fisioID = fisioID;
        }
        if(this.notas.isEmpty()){
            this.notas = new ArrayList<Note>();
        }
        this.notas.add(new Note("",""));

        if(this.lembretes.isEmpty()){
            this.lembretes = new ArrayList<Reminder>();
        }
        this.lembretes.add(new Reminder("","",""));
    }

    public String getNome() {
        return this.nome;
    }

    public String getPassword(){
        return this.password;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean isFisioterapeuta() {
        return paciente;
    }

    public String getFisioID(){
        return this.fisioID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFisioterapeuta(boolean fisioterapeuta) {
        this.paciente = fisioterapeuta;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addPaciente(User user){listaPacientes.add(user);}

    public Map toMap() {
        HashMap result = new HashMap<>();
        result.put("nome", nome);
        result.put("email", email);
        result.put("password", password);
        result.put("paciente", paciente);
        result.put("fisioID", fisioID);
        result.put("listaPacientes", listaPacientes);
        result.put("listaNotas", this.notas);
        result.put("listaLembretes", this.lembretes);
        return result;
    }

    public User fromMap(){
        return null;
    }

}