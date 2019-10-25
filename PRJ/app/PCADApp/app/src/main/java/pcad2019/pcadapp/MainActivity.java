package pcad2019.pcadapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static pcad2019.pcadapp.LoginActivity.*;
import static pcad2019.pcadapp.LoginActivity.EXTRA_MESSAGE_IP;
/**
 * Created by giorgiodelzanno on 09/03/19.
 * aggiornado e modificato da A. Coronado e L. Astengo
 */
public class MainActivity extends AppCompatActivity {

    AndroidClient client;
    protected volatile boolean logoutDone ; // true se è stato già effettuato logout, utilizzata nel metodo onStop
    protected volatile String ID ;

    EditText area;
    EditText data;
    TextView logger;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            Intent intent = getIntent();
            String id = intent.getStringExtra("msg1");
            ID=id;
            String ip = intent.getStringExtra("msg2");
            Toast.makeText(this, id+" "+ip, Toast.LENGTH_SHORT).show();
            client = new AndroidClient(ip, 5005 , id);
            area = findViewById(R.id.areaTxt);
            data = findViewById(R.id.txtTop);
            logger = findViewById(R.id.logger);
        }


        @Override
        public void onBackPressed(){
            AlertDialog.Builder builder = new AlertDialog.Builder(pcad2019.pcadapp.MainActivity.this);
            builder.setMessage("Sei sicuro di voler uscire? \nVerrà effettuato LogOut")
                    .setPositiveButton("CONFERMA",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int id) {
                            pcad2019.pcadapp.MainActivity.super.onBackPressed(); // Chiudiamo veramente
                            //Il logout viene effettuato dal metodo onStop
                        }
                    }).setNegativeButton("ANNULLA",new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int id) {
                    // Non succede nulla
                }
            });
            builder.create().show();

        }

        public void log(String s){
            logger.setText(" "+ s);
        }

        @Override
        protected void onStop(){
            super.onStop();
            if (!logoutDone) { // effetuo logout solo se non è già stato fatto dal RandomWalkWorker
                ConnectionWorker worker = new ConnectionWorker(this, client, "logOut", null, null, null);
                worker.execute();

            }

        }

        public void doSend(View view) {
            ConnectionWorker asyncTask = new ConnectionWorker(this,client, "send", ID, area.getText().toString(), data.getText().toString());
            asyncTask.execute();
        }

        public void doTop(View view) {
            Toast.makeText(this, area.getText().toString(),Toast.LENGTH_SHORT).show();
            String starea = area.getText().toString();
            ConnectionWorker asyncTask = new ConnectionWorker(this,client, "top", ID, starea, null);
            asyncTask.execute();

        }


}
