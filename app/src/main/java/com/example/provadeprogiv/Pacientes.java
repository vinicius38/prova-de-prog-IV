package com.example.provadeprogiv;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.provadeprogiv.DAO.PacientesDAO;
import com.example.provadeprogiv.Model.PacientesModel;

public class Pacientes extends AppCompatActivity {
    EditText txtNomePaciente;
    EditText txtIdade;
    EditText txtTelefone;
    EditText txtendereco;
    EditText txtNumero;
    Button btnCadastrar;
    PacientesModel editarPacientes;
    PacientesModel paciente;
    PacientesDAO pacientesDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);

        paciente = new PacientesModel();
        pacientesDAO = new PacientesDAO(Pacientes.this);

        Intent intent= getIntent();
        editarPacientes = (PacientesModel) intent.getSerializableExtra("selectPaciente");

        txtNomePaciente = (EditText) findViewById(R.id.nomePaciente);
        txtIdade = (EditText) findViewById(R.id.idadePaciente);
        txtTelefone = (EditText) findViewById(R.id.telefonePaciente);
        txtendereco = (EditText) findViewById(R.id.enderecoPaciente);
        txtNumero = (EditText) findViewById(R.id.numeroPaciente);
        btnCadastrar = (Button) findViewById(R.id.btnAlterar);

        if(editarPacientes != null){
            btnCadastrar.setText("Alterar");
            txtNomePaciente.setText(editarPacientes.getNome());
            txtIdade.setText(String.valueOf(editarPacientes.getData()));
            txtTelefone.setText(String.valueOf(editarPacientes.getTelefone()));
            txtendereco.setText(editarPacientes.getEndereco());
            txtNumero.setText(String.valueOf(editarPacientes.getNumero()));
            paciente.setId(editarPacientes.getId());
        }else{
            btnCadastrar.setText("Cadastrar");
        }

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 paciente.setNome(txtNomePaciente.getText().toString());
                 paciente.setData(Integer.parseInt(txtIdade.getText().toString()));
                 paciente.setTelefone(Integer.parseInt(txtTelefone.getText().toString()));
                 paciente.setEndereco(txtendereco.getText().toString());
                 paciente.setNumero(Integer.parseInt(txtNumero.getText().toString()));
                 if(btnCadastrar
                         .getText()
                         .toString()
                         .equals("Cadastrar")
                 ){
                     pacientesDAO.cadPaciente(paciente);
                 } else{
                     pacientesDAO.alteraPaciente(paciente);
                 }
                 pacientesDAO.close();
                 finish();
            }
        });
    }
}
