package com.coolio.tcptest;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.*;
import java.io.*;

public class ClientActivity extends AppCompatActivity {

    String serverName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

    }


    public class client extends AsyncTask<Void,Void,Void> {
        String recieved;
        protected Void doInBackground(Void... Params) {
            //String serverName = "10.7.48.108";

            int port = 6066;
            try {
                //updateText.setText("Connecting to " + serverName + " on port " + port);
                Socket client = new Socket(serverName, port);

               // updateText.setText("Just connected to " + client.getRemoteSocketAddress());
                OutputStream outToServer = client.getOutputStream();
                DataOutputStream out = new DataOutputStream(outToServer);

                out.writeUTF("Hello from " + client.getLocalSocketAddress());
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);

                recieved = in.readUTF();
                client.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void aVoid) {

            TextView updateText = (TextView) findViewById(R.id.update);
            updateText.setText("in post");
            TextView recievedText = (TextView) findViewById(R.id.recieved);
            recievedText.setText("Server says: " + recieved);
        }
    }

    public void onSendClicked(View view) {
        EditText messageView= (EditText) findViewById(R.id.ip);
        serverName=messageView.getText().toString();
        new client().execute();
    }
}


