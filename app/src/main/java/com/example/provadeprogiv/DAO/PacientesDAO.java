package com.example.provadeprogiv.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.provadeprogiv.Model.PacientesModel;

import java.util.ArrayList;

public class PacientesDAO extends SQLiteOpenHelper {

    private static final String DATABASE = "cadastroPaciente";
    private static final int VERSION = 1;

    public String msgErro = "";
    public PacientesDAO(Context context){
        super(context, DATABASE, null, VERSION);
        msgErro = "";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE pacientes("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"+
                "nome TEXT NOT NULL,"+
                "idade INTEGER NOT NULL,"+
                "telefone INTEGER NOT NULL,"+
                "endereco TEXT NOT NULL,"+
                "numero INTEGER NOT NULL DEFAULT 0);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion != newVersion){
            String sql = "DROP TABLE IF EXISTS pacientes";
            db.execSQL(sql);
        }
    }
    //inserir paciente
    public boolean cadPaciente(PacientesModel paciente){
        try {
        ContentValues values = new ContentValues();
        values.put("nome",paciente.getNome());
        values.put("idade",paciente.getData());
        values.put("telefone",paciente.getTelefone());
        values.put("endereco",paciente.getEndereco());
        values.put("numero",paciente.getNumero());
        getWritableDatabase().insert("pacientes",null,values);
        return true;
        }catch (Exception ex){
            msgErro = ex.getMessage();
            return false;
        }

    }
    //altera cadastro paciente
    public boolean alteraPaciente(PacientesModel paciente){
        try {
            ContentValues values = new ContentValues();
            values.put("nome",paciente.getNome());
            values.put("idade",paciente.getData());
            values.put("telefone",paciente.getTelefone());
            values.put("endereco",paciente.getEndereco());
            values.put("numero",paciente.getNumero());

            String[] args = {paciente.getId().toString()};
            getWritableDatabase().update("pacientes",values, "id=?",args);
            return true;
        }catch (Exception ex) {
            msgErro = ex.getMessage();
            return false;
        }
    }
    //Exclui paciente
    public boolean excluiPaciente(PacientesModel paciente){
        try {
            String[] args = {paciente.getId().toString()};
            getWritableDatabase().delete("pacientes","id=?",args);
            return true;
        }catch (Exception ex) {
            msgErro = ex.getMessage();
            return false;
        }
    }
    //lista de pacientes
    public ArrayList<PacientesModel>ListaPacientes(){
        ArrayList<PacientesModel>listPacientes = new ArrayList<>();
        String[] coluns={"id","nome","idade","telefone","endereco","numero"};
        Cursor cursor = getWritableDatabase().query("paciente", coluns,null,null,null,null,null);
        while(cursor.moveToNext()){
            PacientesModel pacientesModel = new PacientesModel();
            pacientesModel.setId(cursor.getLong(0));
            pacientesModel.setNome(cursor.getString(1));
            pacientesModel.setData(cursor.getInt(2));
            pacientesModel.setNumero(cursor.getInt(3));
            pacientesModel.setEndereco(cursor.getString(4));
            pacientesModel.setNumero(cursor.getInt(5));
        }
        return listPacientes;
    }
}
