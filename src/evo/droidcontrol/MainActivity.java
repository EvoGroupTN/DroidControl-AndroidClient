package evo.droidcontrol;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener {

	Button btnOK;
	Button btnCancel;
	EditText txtIP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnCancel = (Button)findViewById(R.id.button2);
        btnOK = (Button)findViewById(R.id.button1);
        txtIP = (EditText)findViewById(R.id.editText1);
        btnCancel.setOnClickListener(this);
        btnOK.setOnClickListener(this);
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public void onClick(View v) {
		if(v==btnCancel){
			finish();
		}
		if (v==btnOK) {
			if ((txtIP.getText().toString()!=null)&&(txtIP.getText().toString().compareTo("")!=0)) {
				Intent i = new Intent(this,Commands.class);
				i.putExtra("ip", txtIP.getText().toString());
				startActivity(i);
				finish();
			}else{
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setTitle("Error");
				dialog.setMessage("You have to select a server IP");
				dialog.setPositiveButton("OK", null);
				dialog.show();
			}
			
		}
	}
    
}
