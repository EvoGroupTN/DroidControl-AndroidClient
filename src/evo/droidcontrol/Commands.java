package evo.droidcontrol;
import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Sample code that invokes the speech recognition intent API.
 */
public class Commands extends Activity implements OnClickListener {
    
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    
    //private ListView mList;
    /*private LivresBDD livreBdd=null;
    private String TABLE_LIVRES;
	private int VERSION_BDD;
	private String COL_ID;
	private int NUM_COL_ID;
	private String COL_ISBN;
	private int NUM_COL_ISBN;
	private String COL_TITRE;
	private int NUM_COL_TITRE;
	private String NOM_BDD_CR;
	public int indexTab;
	
	Menu mnu;*/
    public EditText txtres;
    /**
     * Called with the activity is first created.
     */
    public static LivresBDD livreBdd;
    public static String ipAdress;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.commands);
        livreBdd = new LivresBDD(this,"commands.db","commands",1,"idCMD",0,"NameCMD",1,"ValCMD",2);
        // Get display items for later interaction
        Button speakButton = (Button) findViewById(R.id.btn_speak);
        Intent intent = getIntent();
        if(intent.getStringExtra("ip")!=null)
        	ipAdress = intent.getStringExtra("ip").toString();
        else
        	ipAdress = "0";
        //ipAdress="192.168.1.5";
        //mList = (ListView) findViewById(R.id.list);
        txtres = (EditText)findViewById(R.id.editText1);
        txtres.setText("log:");
        // Check to see if a recognition activity is present
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(
                new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0) {
            speakButton.setOnClickListener(this);
            //livreBdd = new LivresBDD(this,NOM_BDD_CR,TABLE_LIVRES,VERSION_BDD,COL_ID,NUM_COL_ID,COL_ISBN,NUM_COL_ISBN,COL_TITRE,NUM_COL_TITRE);
        } else {
            speakButton.setEnabled(false);
            speakButton.setText("Recognizer not present");
        }
    }

    /**
     * Handle the click on the start recognition button.
     */
    public void onClick(View v) {
        if (v.getId() == R.id.btn_speak) {
            startVoiceRecognitionActivity();
        }
    }
    
    public void saveToLog(String txt){
    	 txtres.setText(txtres.getText().toString()+"\n"+txt);
    }

    /**
     * Fire an intent to start the speech recognition activity.
     */
    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    /**
     * Handle the results from the recognition activity.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            final ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            //mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,matches));
            final Handler mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    String text = (String)msg.obj;
                    saveToLog(text);
                }
        };
            new Thread(new Runnable() {
                public void run() {
                	try {
                		PrintWriter printwriter;
                		Socket client;
                		livreBdd.open();
                		if(livreBdd.getcommand(matches.get(0))!=null){
                	     client = new Socket(ipAdress, 4444);  //connect to server
                	     //while(true){
                	     printwriter = new PrintWriter(client.getOutputStream(),true);
                	     
                	     printwriter.write(livreBdd.getcommand(matches.get(0))[1]);  //write the message to output stream
                	     printwriter.flush();
                	     printwriter.close();
                	     String message = "Executing \""+matches.get(0)+"\"...\n"+livreBdd.getcommand(matches.get(0))[1];
                	     Message msg = new Message();
                         msg.obj = message;
                         mHandler.sendMessage(msg);
                	     
                	    /* InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
                	     BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                	     String message = bufferedReader.readLine();
                	     inputStreamReader.close();
                	     textField.setText(message);*/
                	     client.close();   //closing the connection
                	    // }
                		}
                		livreBdd.close();
                	    } catch (UnknownHostException e) {
                	     e.printStackTrace();
                	    } catch (IOException e) {
                	     e.printStackTrace();
                	    }
                }
            }).start();
           
            /*new Thread(new Runnable() {
                public void run() {
                	try {
                		PrintWriter printwriter;
                		Socket client;
                		String message;
                		InputStreamReader inputStreamReader;
                	    BufferedReader bufferedReader;
                	     client = new Socket(ipAdress, 4444);  //connect to server
                	     while(true){
                	     inputStreamReader = new InputStreamReader(client.getInputStream());
                         bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                         message = bufferedReader.readLine();
                         Message msg = new Message();
                         msg.obj = message;
                         mHandler.sendMessage(msg);
                	     InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
                	     BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //get the client message
                	     String message = bufferedReader.readLine();
                	     inputStreamReader.close();
                	     textField.setText(message);
                	     //client.close();   //closing the connection
                	     }
                	    } catch (UnknownHostException e) {
                	     e.printStackTrace();
                	    } catch (IOException e) {
                	     e.printStackTrace();
                	    }
                }
            }).start();*/
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,1,Menu.NONE,"Add Command");
		menu.add(0,2,Menu.NONE,"Commands");
		menu.add(0,3,Menu.NONE,"DroidMouse");
		menu.add(0,4,Menu.NONE,"Exit");
	return true;
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	if(item.getItemId()==1){
    		Intent i1 = new Intent(this,Add.class);
    		startActivity(i1);
    	}
    	if (item.getItemId()==2) {
    		Intent i2 = new Intent(this,ListCmd.class);
    		startActivity(i2);
		}
    	if (item.getItemId()==3) {
    		Intent i3 = new Intent(this,DroidTouch.class);
    		startActivity(i3);
		}
    	if (item.getItemId()==4) {
    		finish();
		}
    	
    	return true;
    }
}