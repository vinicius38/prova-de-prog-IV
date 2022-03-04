package com.example.provadeprogiv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.provadeprogiv.DAO.PacientesDAO;
import com.example.provadeprogiv.Model.PacientesModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //variáveis de CAST/PARSE
    ListView listView;
    Button btnCadastrar;
    PacientesDAO pacientesDAO;
    ArrayList<PacientesModel> listaPacientes;
    ArrayAdapter adapter;
    PacientesModel paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listaPacientes);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(MainActivity.this,Pacientes.class);
                startActivity(intent);
            }
        });
        registerForContextMenu(listView);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                paciente = (PacientesModel) parent.getItemAtPosition(position);
                return false;
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem menuItem = menu.add("Excluir Paciente");
        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                pacientesDAO = new PacientesDAO(MainActivity.this);
                pacientesDAO.excluiPaciente(paciente);
                pacientesDAO.close();
                carregarListaPacientes();
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListaPacientes();
    }
    //Buscar dados no banco de dados
    public void carregarListaPacientes(){
        pacientesDAO = new PacientesDAO(MainActivity.this);
        listaPacientes = pacientesDAO.ListaPacientes();
        pacientesDAO.close();
        if(listaPacientes != null){
            adapter = new ArrayAdapter<PacientesModel>(MainActivity.this,android.R.layout.simple_list_item_1,listaPacientes);
            listView.setAdapter(adapter);
        }
    }
}