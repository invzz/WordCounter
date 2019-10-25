package pcad2019.pcadapp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

/**
 * Created by giorgiodelzanno on 09/03/19.
 * aggiornado e modificato da A. Coronado e L. Astengo
 */

public class ConnectionWorker extends AsyncTask<String,String,Boolean> {

    private Activity activity;
    private AndroidClient client;
    private String command;
    private String id;
    private String nameArea;
    private String snameData;


    public ConnectionWorker(Activity activity, AndroidClient client, String command, String id, String nameArea, String snameData) {
        this.activity = activity;
        this.client = client;
        this.command = command;
        this.id = id;
        this.nameArea = nameArea;
        this.snameData = snameData;
    }

    // in base al comando specificato  nel costruttore il thread esegue una delle operazioni
    @Override
    protected Boolean doInBackground(String... strings) {
        switch (command) {
            case "signIn": return client.signIn(id, nameArea, snameData);
            case "remove": return client.removeUser();
            case "logIn":  return client.logIn(id);
            case "logOut": return client.logOut();
            case "top": return client.reqTop(id,nameArea);
            case "send": return client.sndmsg(id,nameArea,snameData);
            default: throw new AssertionError();
        }
    }

    @Override
    protected void onPostExecute(Boolean result){
        switch(command) {
            case "signIn": {
                if (result) {
                    Toast.makeText(activity, "Registrazione effettuata", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                else {
                    Toast.makeText(activity, "Impossibile eseguire registrazione", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case "remove": {
                if (result) {
                    Toast.makeText(activity, "Utente cancellato", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                else { Toast.makeText(activity, "Impossibile cancellare utente", Toast.LENGTH_SHORT).show();}
                break;
            }
            case "logIn": {
                if (!result) {
                    Toast.makeText(activity, "Impossibile eseguire LogIn", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent = new Intent(activity, MainActivity.class);
                    intent.putExtra("msg1", id);
                    intent.putExtra("msg2" ,client.ip);
                    activity.startActivity(intent);
                }
                break;
            }
            case "logOut": {
                if (result) {
                    Toast.makeText(activity, "LogOut effettuato", Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                else { Toast.makeText(activity, "Impossibile eseguire LogOut", Toast.LENGTH_SHORT).show();}
                break;
            }
            case "top":if (result) {
                Toast.makeText(activity,"top3 in selected area:\n"+client.top.toString(), Toast.LENGTH_SHORT).show();
            }
            else { Toast.makeText(activity, "OOps.. something went wrong", Toast.LENGTH_SHORT).show();}
                break;


            case "send": {
                if (result) {
                    Toast.makeText(activity, "data sent", Toast.LENGTH_SHORT).show();
                }
                else { Toast.makeText(activity, "cant't send data", Toast.LENGTH_SHORT).show();}
                break;
            }

        }
    }
}