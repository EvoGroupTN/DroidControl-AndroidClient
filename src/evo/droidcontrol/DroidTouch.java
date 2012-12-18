package evo.droidcontrol;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class DroidTouch extends Activity{

	TextView txtTouch;
	TextView lblcoord;
	String msg;
	float x=0,y=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_droid_touch);
		lblcoord = (TextView)findViewById(R.id.textViewlbl);
		
		msg="mouse";
		new Thread(new Runnable() {
            public void run() {
            	try {
            		PrintWriter printwriter;
            		Socket client;
            		
            	     client = new Socket(Commands.ipAdress, 4444);  //connect to server
            	     while(true){
            	     printwriter = new PrintWriter(client.getOutputStream(),true);
            	     printwriter.write("test"); 
            	     
            	    	 printwriter.write(lblcoord.getText().toString());  //write the message to output stream
            	    	 printwriter.flush();
            	     }
            	     /*String message = "Executing \""+matches.get(0)+"\"...\n"+livreBdd.getcommand(matches.get(0))[1];
            	     Message msg = new Message();
                     msg.obj = message;
                     mHandler.sendMessage(msg);
            	     printwriter.flush();
            	     printwriter.close();
            	    /* InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
            	     BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //get the client message
            	     String message = bufferedReader.readLine();
            	     inputStreamReader.close();
            	     textField.setText(message);*/
            	     //client.close();   //closing the connection
            	    // }
            		
            	    } catch (UnknownHostException e) {
            	     e.printStackTrace();
            	    } catch (IOException e) {
            	     e.printStackTrace();
            	    }
            }
        }).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_droid_touch, menu);
		return true;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	    
	    x=event.getX();
		y=event.getY();
		lblcoord.setText("mouse:"+x+":"+y);
		
	return false;
	}

	

}
