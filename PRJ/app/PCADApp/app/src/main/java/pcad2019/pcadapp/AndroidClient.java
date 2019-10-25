package pcad2019.pcadapp;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by giorgiodelzanno on 09/03/19.
 *
 * Modificato ed adattato da Andres Coronado e Lorenzo Astengo
 * come progetto per il corso di PCACD 2018-2019
 */

class AndroidClient {
    String ip;
    private int port;
    private String currentId; // ID dell'utente attualmente loggato

    public String top;

    AndroidClient(String ip, int port) {
        this.ip = ip;
        this.port = port;
        this.top="";
    }

    AndroidClient(String ip, int port , String currentId) {
        this.ip = ip;
        this.port = port;
        this.currentId = currentId;
        this.top="";
    }


    //REGISTRAZIONE UTENTE
    boolean signIn(String id, String name, String surname) {

        if (id.equals("") || name.equals("") || surname.equals("")) return false;
        Socket serverSocket = null;
        PrintWriter output ;
        BufferedReader input ;

        try {

            serverSocket = new Socket(ip, port);

            output =  new PrintWriter(serverSocket.getOutputStream(), true);

            input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            output.println("signin");
            String result = input.readLine();

            if(result.equals("FAIL")) return false;

            output.println(id);
            output.println(name);
            output.println(surname);

            result = input.readLine();

            if(result.equals("FAIL")) return false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //CANCELLAZIONE REGISTRAZIONE UTENTE
    boolean removeUser() {
        Socket serverSocket = null;
        PrintWriter output;
        BufferedReader input;
        try {
            serverSocket = new Socket(ip, port);
            output =  new PrintWriter(serverSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            output.println("remove");
            String result = input.readLine();
            if(result.equals("FAIL")) return false;
            output.println(currentId);
            result = input.readLine();
            if(result.equals("FAIL")) return false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }  finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //LOGOUT
    boolean logOut() {
        Socket serverSocket = null;
        PrintWriter output ;
        BufferedReader input ;
        try {
            serverSocket = new Socket(ip, port);
            output =  new PrintWriter(serverSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            output.println("logout");
            String result = input.readLine();
            if(result.equals("FAIL")) return false;
            output.println(currentId);
            result = input.readLine();
            if(result.equals("FAIL")) return false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }  finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //LOGIN
    boolean logIn(String id) {
        Socket serverSocket = null;
        PrintWriter output ;
        BufferedReader input ;
        try {
            serverSocket = new Socket(ip, port);
            output =  new PrintWriter(serverSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
            output.println("login");

            String result = input.readLine();
            if(result.equals("FAIL")) return false;

            output.println(id);
            result = input.readLine();
            if(result.equals("FAIL")) return false;

        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }  finally {
            try {
                if(serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(Exception ee) {
                ee.printStackTrace();
            }
        }
        currentId = id;
        return true;
    }

    //send message
    boolean sndmsg(String id,String area, String data) {
        Socket serverSocket = null;
        PrintWriter output ;
        BufferedReader input ;
        try {
            serverSocket = new Socket(ip, port);
            output =  new PrintWriter(serverSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            output.println("send");
            String result = input.readLine();
            if(result.equals("FAIL")) return false;

            output.println(id);
            result = input.readLine();
            if(result.equals("FAIL")) return false;
            output.println(area);
            result = input.readLine();
            if(result.equals("FAIL")) return false;
            output.println(data);
            result = input.readLine();
            if(result.equals("FAIL")) return false;
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }  finally {
            try {
                if(serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch(Exception ee) {
                ee.printStackTrace();
            }
        }
        return true;
    }

    //top 3
    boolean reqTop(String id,String area) {
        Socket serverSocket = null;
        PrintWriter output;
        BufferedReader input;
        try {

            serverSocket = new Socket(ip, port);
            output = new PrintWriter(serverSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));

            output.println("top");
            String result = input.readLine();
            if (result.equals("FAIL")) return false;

            output.println(id);

            result = input.readLine();
            if (result.equals("FAIL")) return false;

            output.println(area);

            //getting top answers
            this.top = input.readLine();
            this.top=this.top.replaceAll("-", "\n");


        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }
        return true;
    }

    public String getTop() {
        return top;
    }
/*
    public static void main(String[] args){
        AndroidClient client = new AndroidClient("127.0.0.1", 5005);
        String test = "pup333u";
        String test2= "pupu2";
        client.signIn(test, test, test);
        client.logIn(test);
        client.sndmsg(test, test, test2);
        client.asktop(test, test);
        client.logOut();
        client.removeUser();
        System.out.println(client.top);
        //client.asktop(test, test);

    }
}
*/

}