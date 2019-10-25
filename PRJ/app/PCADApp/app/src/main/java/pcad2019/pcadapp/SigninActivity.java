package pcad2019.pcadapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static pcad2019.pcadapp.LoginActivity.*;
/**
 * Created by giorgiodelzanno on 09/03/19.
 * aggiornado e modificato da A. Coronado e L. Astengo
 */
public class SigninActivity extends AppCompatActivity {

    AndroidClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Intent intent = getIntent();
        String ip = intent.getStringExtra("msg2");
        Toast.makeText(this, ip, Toast.LENGTH_SHORT).show();
        client = new AndroidClient(ip, 5005);
    }

    public void sendSignIn(View view) { // effettua la registrazione dell'utente
        EditText idText = (EditText) findViewById(R.id.idText);
        String id = idText.getText().toString();
        EditText nameText = (EditText) findViewById(R.id.nameText);
        String name = nameText.getText().toString();
        EditText surnameText = (EditText) findViewById(R.id.surnameText);
        String surname = surnameText.getText().toString();
        Toast.makeText(this, id+" "+name+" "+surname, Toast.LENGTH_SHORT).show();
        ConnectionWorker asyncTask = new ConnectionWorker(this,client, "signIn", id, name, surname);
        asyncTask.execute();
    }
}
