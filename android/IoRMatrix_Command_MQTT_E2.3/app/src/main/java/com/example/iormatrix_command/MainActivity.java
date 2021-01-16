package com.example.iormatrix_command;


import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    //private PrintStream ps;
    //private Socket client;
    private  Boolean connected = false;
    final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /* MQTT */

    private int qos = 0;
    private MqttAndroidClient mqttClient;
    //private IMqttToken token;
    private String mqttBroker = "tcp://mqtt.flespi.io:1883";
    private String mqttTopic = "commande";
    private String mqttUsername = "5MU9zXUYY0fy7tmz13BejE2zxy7xzUzuxhrU49IRZOfW5rkXiLZsdRPAfNYEvnWQ";
    //private String mqttMdp = "";
    private String mqttClientId = MqttClient.generateClientId();
    MqttMessage message;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clk_haut(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                //ps.println("h");
                Log.i("test","test3 <---------------------------");
                message.setQos(qos);
                message.setPayload("h".getBytes());
                Log.i("test","test4 <---------------------------");
                try {
                    mqttClient.publish(mqttTopic,message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                Log.i("test","test5 <---------------------------");
            }
        });
    }

    public void clk_bas(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                //ps.println("b");
                message.setQos(qos);
                message.setPayload("b".getBytes());
                try {
                    mqttClient.publish(mqttTopic,message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void clk_droite(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                //ps.println("d");
                message.setQos(qos);
                message.setPayload("d".getBytes());
                try {
                    mqttClient.publish(mqttTopic,message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void clk_gauche(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                //ps.println("g");
                message.setQos(qos);
                message.setPayload("g".getBytes());
                try {
                    mqttClient.publish(mqttTopic,message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void clk_raz(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                //ps.println("init");
                message.setQos(qos);
                message.setPayload("init".getBytes());
                try {
                    mqttClient.publish(mqttTopic,message);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void exit(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                if(connected){
                    //ps.println("exit");
                    //ps.close();
                    message.setQos(qos);
                    message.setPayload("exit".getBytes());
                    try {
                        mqttClient.publish(mqttTopic,message);
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }

                    try {
                        mqttClient.close();
                        mqttClient.disconnect();
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

/*    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.i("test",token.toString());
    }*/


    public class Connect extends AsyncTask<String,String,String>{
        @Override
        protected String doInBackground(String... param){
            try{
                if(!connected) {
                   /* client = new Socket("192.168.43.147", 3000);
                    ps = new PrintStream(client.getOutputStream());

                    ps.println("afficher");*/
                    connected = true;

                    //MqttConnectOptions connectOptions = new MqttConnectOptions();
                    //connectOptions.setCleanSession(true);
                    //connectOptions.setUserName(mqttUsername);
                    //connectOptions.setPassword(mqttMdp.toCharArray());

                    Log.i("test","test1 <---------------------------");
                    mqttClient = new MqttAndroidClient(MainActivity.this,mqttBroker,mqttClientId);
                    mqttClient.connect();
                    Log.i("test","test2 <---------------------------");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }
    }
}
