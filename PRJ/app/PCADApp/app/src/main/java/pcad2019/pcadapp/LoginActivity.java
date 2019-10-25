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
import android.widget.Toast;
/**
 * Created by giorgiodelzanno on 09/03/19.
 * aggiornado e modificato da A. Coronado e L. Astengo
 */
public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_ID = "msg1";
    public static final String EXTRA_MESSAGE_IP = "msg2";
    private String IPstring ;

    private boolean checkIp(){ // controlla se è stato inserito un indirizzo ip
        if ( IPstring == null ){
            Toast.makeText(this, "Indirizzo IP mancante", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void doSignIn(View view) { // avvia activity per la registrazione
        if (!checkIp()) return;
        Intent intent = new Intent(this, SigninActivity.class);
        intent.putExtra("msg2", IPstring);
        startActivity(intent);
    }

    public void doLogIn(View view) { // effettuta il login e se va a buon fine avvia la loginActivity
        if (!checkIp()) return;
        EditText idText = (EditText) findViewById(R.id.loginText);
        String id = idText.getText().toString();
        AndroidClient client = new AndroidClient(IPstring , 5005);
        ConnectionWorker worker = new ConnectionWorker(this,client,"logIn",id,null,null);
        worker.execute();
    }

    public void insertIp(View view) { // inserisce indirizzo IP sul quale contattare il server
        EditText ipText = (EditText) findViewById(R.id.IpText);
        IPstring = ipText.getText().toString();
        Toast.makeText(this, IPstring, Toast.LENGTH_SHORT).show();
    }
}
