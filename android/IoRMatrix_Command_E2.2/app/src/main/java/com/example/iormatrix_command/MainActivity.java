package com.example.iormatrix_command;


import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private PrintStream ps;
    private Socket client;
    private  Boolean connected = false;
    final ExecutorService executorService = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clk_haut(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                ps.println("h");
            }
        });
    }

    public void clk_bas(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                ps.println("b");
            }
        });
    }

    public void clk_droite(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                ps.println("d");
            }
        });
    }

    public void clk_gauche(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                ps.println("g");
            }
        });
    }

    public void clk_raz(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                ps.println("init");
            }
        });
    }

    public void exit(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                if(connected){
                    ps.println("exit");
                    ps.close();
                    try {
                        client.close();
                        connected = false;
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void client_connect(View v){
        Connect c = new Connect();
        c.execute();
    }




    public class Connect extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... param){
            try{
                if(!connected) {
                    client = new Socket("10.0.2.2", 3000);
                    ps = new PrintStream(client.getOutputStream());

                    ps.println("afficher");
                    connected = true;
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }

    /*
    * Episode 3 partie 4
    * */
    private Boolean value = true;
    public void on_off(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    client = new Socket("192.168.1.20", 8080);
                    ps = new PrintStream(client.getOutputStream());
                    if(value){
                        ps.print("a");
                        value = !value;
                    } else{
                        ps.print("b");
                        value = !value;
                    }
                } catch (Exception ignored) {}
            }
        });
    }
}
