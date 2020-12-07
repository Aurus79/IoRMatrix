package com.example.iormatrix_matrix;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.client.Emitter;*/

public class MainActivity extends AppCompatActivity implements MqttCallback {

    private Boolean connected = false;
    Socket client;
    PrintStream ps;
    MyMatrix matrix;

    final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /* MQTT */
    private int qos = 0;
    private MqttAndroidClient mqttClient;
    private IMqttToken token;
    private String mqttBroker = "tcp://mqtt.flespi.io:1883";
    private String mqttTopic = "commande";
    private String mqttUsername = "5MU9zXUYY0fy7tmz13BejE2zxy7xzUzuxhrU49IRZOfW5rkXiLZsdRPAfNYEvnWQ";
    private String mqttMdp = "";
    private String mqttClientId = MqttClient.generateClientId();
    MqttMessage message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        matrix = findViewById(R.id.matrix);
        matrix.init();
        matrix.afficher();
    }

    public void client_connect(View v){
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("IoRMatrix CLIENT Start ...");
                MqttConnectOptions connectOptions = new MqttConnectOptions();
                connectOptions.setCleanSession(true);
                connectOptions.setUserName(mqttUsername);
                connectOptions.setPassword(mqttMdp.toCharArray());
                mqttClient = new MqttAndroidClient(MainActivity.this,mqttBroker,mqttClientId);
                try {
                    token = mqttClient.connect(connectOptions);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                subscribe();
                connected = true;
                System.out.println("Client connecte ...");
            }
        });
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        if (topic.equals(mqttTopic)) {
            String value = message.toString();

            // ICI tu fais un truc avec value (genre ton switch sur le mouvement)
            move(value);
        }
    }

    public void move(String value){
        if(value.equals("d")) { System.out.println("DROITE"); matrix.incX(); System.out.print("IoRMatrix_S > "); }
        if(value.equals("g")) { System.out.println("GAUCHE"); matrix.decX(); System.out.print("IoRMatrix_S > "); }
        if(value.equals("b")) { System.out.println("BAS"); matrix.incY(); System.out.print("IoRMatrix_S > "); }
        if(value.equals("h")) { System.out.println("HAUT"); matrix.decY(); System.out.print("IoRMatrix_S > "); }
        if(value.equals("init")) { System.out.println("INIT"); matrix.init(); System.out.print("IoRMatrix_S > ");}
        if(value.equals("afficher")) { System.out.println("INIT"); matrix.afficher(); System.out.print("IoRMatrix_S > "); }
        //if(value.equals("exit")) { System.out.println("INIT"); matrix.cacher(); System.out.print("IoRMatrix_S > "); }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    // MQTT
    private void subscribe(){
        token.setActionCallback(new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                mqttClient.setCallback((MqttCallback) MainActivity.this);
                try {
                    mqttClient.subscribe(mqttTopic, qos);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

            }
        });
    }
}
