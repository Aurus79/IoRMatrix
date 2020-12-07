package com.example.iormatrix_command;


import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private PrintStream ps;
    //private Socket client;
    private  Boolean connected = false;
    final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /* MQTT */

    private MqttAndroidClient client;
    private IMqttToken token;
    private String broker = "tcp://mqtt.flespi.io:1883";
    private String clientId = MqttClient.generateClientId();



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
/*                    client = new Socket("192.168.43.147", 3000);
                    ps = new PrintStream(client.getOutputStream());

                    ps.println("afficher");
                    connected = true;*/

                    MqttConnectOptions connectOptions = new MqttConnectOptions();
                    connectOptions.setCleanSession(true);
                    connectOptions.setUserName("user");
                    connectOptions.setPassword("motdepasse".toCharArray());
                    client = new MqttAndroidClient(MainActivity.this,broker,clientId);
                    token = client.connect(connectOptions);
                    //souscrire(); TODO
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
