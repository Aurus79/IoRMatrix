package com.example.iormatrix_matrix;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/*import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.client.Emitter;*/

public class MainActivity extends AppCompatActivity {

    private Boolean connected = false;
    Socket client;
    PrintStream ps;
    MyMatrix matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matrix = findViewById(R.id.matrix);
        matrix.init();
        matrix.afficher();
    }

    public void client_connect(View v){
        Connect c = new Connect();
        c.execute();
    }




    public class Connect extends AsyncTask<String,String,String> {
        @SuppressLint("WrongThread")
        @Override
        protected String doInBackground(String... param){
            try {
                boolean ready = true;
                while(ready) {
                    System.out.println("IoRMatrix CLIENT Start ...");
                    Socket readFromNoeud = new Socket("192.168.43.147", 3001);
                    System.out.println("Client connecte ...");

                    matrix.init();

                    BufferedReader br = new BufferedReader(new InputStreamReader(readFromNoeud.getInputStream()));
                    String s ;
                    while(!(s = br.readLine()).equals("exit")) {
                        if(s.equals("d")) { System.out.println("DROITE"); matrix.incX(); System.out.print("IoRMatrix_S > "); }
                        if(s.equals("g")) { System.out.println("GAUCHE"); matrix.decX(); System.out.print("IoRMatrix_S > "); }
                        if(s.equals("b")) { System.out.println("BAS"); matrix.incY(); System.out.print("IoRMatrix_S > "); }
                        if(s.equals("h")) { System.out.println("HAUT"); matrix.decY(); System.out.print("IoRMatrix_S > "); }
                        if(s.equals("init")) { System.out.println("INIT"); matrix.init(); System.out.print("IoRMatrix_S > ");}
                        if(s.equals("afficher")) { System.out.println("INIT"); matrix.afficher(); System.out.print("IoRMatrix_S > "); }
                    }
                    matrix.cacher();
                    System.out.println("Bye");
                    readFromNoeud.close();
                }


            }

            catch(Exception e) {}
            return "";
        }
    }
}
