package com.coolio.tcptest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.net.*;
import java.io.*;



public class ServerActivity extends Activity {

    Runnable r;
    TextView updateText;
    int port;
    ServerSocket serverSocket;
    String var = "x";
    AsyncTask<Void,Void,Void> asyncTask1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        port = 6066;
        updateText = (TextView) findViewById(R.id.update);
        updateText.setText(var);


        //new server().execute();
        @SuppressLint("HandlerLeak")
        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = msg.getData();
                var = bundle.getString("rec");
                updateText.setText(var);
            }
        };

        try {
            Thread t = new GreetingServer(port , handler);
            t.start();
        }catch(Exception e){

        }
       /* try {
            r = new ServerRun(handler);
            handler.post(r);
        }catch(Exception e){

        }*/
    }

    class GreetingServer extends Thread {
        private ServerSocket serverSocket;
        Handler handler;

        public GreetingServer(int port, Handler handler) throws IOException {
            serverSocket = new ServerSocket(port);
            this.handler = handler;
        }

        public void run() {
            //TextView updateText = (TextView) findViewById(R.id.update);
            while (true) {
                try {

                    //TextView recievedText = (TextView) findViewById(R.id.recieved);
                    // updateText.setText("Waiting for client on port " +
                    //serverSocket.getLocalPort() + "...");
                    Socket server = serverSocket.accept();

                    //updateText.setText("Just connected to " + server.getRemoteSocketAddress());
                    DataInputStream in = new DataInputStream(server.getInputStream());

                    //recievedText.setText(in.readUTF());
                    String recieved = in.readUTF();
                    Message msg = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("rec", recieved);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
                    out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                            + "\nGoodbye!");
                    server.close();

                } catch (SocketTimeoutException s) {
                    // updateText.setText("Socket timed out!");
                    // break;
                } catch (IOException e) {
                    e.printStackTrace();
                    // break;
                }
            }
        }
    }



















    /*public class server extends AsyncTask<Void,Void,Void> {
        String recieved;

        protected Void doInBackground(Void... Params) {
            try {
                ServerSocket serverSocket;
                serverSocket = new ServerSocket(port);
                Socket server = serverSocket.accept();

                DataInputStream in = new DataInputStream(server.getInputStream());

                recieved=in.readUTF();
                DataOutputStream out = new DataOutputStream(server.getOutputStream());
                out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                        + "\nGoodbye!");


                server.close();

            }catch(IOException e){

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

    public class ServerRun implements Runnable{

        Handler handler;
        public ServerRun(Handler handler) throws IOException {
            this.handler = handler;
        }

        @Override
        public void run(){

            while(true){
                try {
                    ServerSocket serverSocket;
                    serverSocket = new ServerSocket(port);
                    Socket server = serverSocket.accept();

                    DataInputStream in = new DataInputStream(server.getInputStream());

                    String recieved = in.readUTF();

                    Message msg = handler.obtainMessage();
                    Bundle bundle = new Bundle();
                    bundle.putString("rec", recieved);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                    DataOutputStream out = new DataOutputStream(server.getOutputStream());
                    out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                            + "\nGoodbye!");
                    server.close();
                }catch(Exception e){

                }
            }

        }
    }*/
    /*Handler mainHandler = new Handler(Looper.getMainLooper());

        try {
            r = new Thread(new GreetingServer(port));
            mainHandler.post(r);
        }catch (Exception e){

        }*/


        /*Thread t = new Thread(new Runnable() {
            public void run() {
                while(true){
                    if(asyncTask1.getStatus()==AsyncTask.Status.FINISHED){
                        asyncTask1 = new server().execute();
                }
            }
        }});*/



        /*Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    serverSocket = new ServerSocket(port);
                }catch(IOException e){

                }
                while (true) {
                    try {

                        TextView recievedText = (TextView) findViewById(R.id.recieved);
                        updateText.setText("Waiting for client on port " +
                        serverSocket.getLocalPort() + "...");
                        Socket server = serverSocket.accept();

                       // updateText.setText("Just connected to " + server.getRemoteSocketAddress());
                        DataInputStream in = new DataInputStream(server.getInputStream());

                        //updateText.setText(in.readUTF());
                        DataOutputStream out = new DataOutputStream(server.getOutputStream());
                        out.writeUTF("Thank you for connecting to " + server.getLocalSocketAddress()
                                + "\nGoodbye!");
                        server.close();

                    } catch (SocketTimeoutException s) {
                        //updateText.setText("Socket timed out!");
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                        break;
                    }
                }

            }
        });
        t.start();*/





}

