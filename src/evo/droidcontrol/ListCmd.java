package evo.droidcontrol;

import java.util.ArrayList;

import evo.droidcontrol.Command;
import evo.droidcontrol.MonListAdapter;

import evo.droidcontrol.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;


public class ListCmd extends Activity {
	
	ListView lv;
	//ListView mList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_cmd);
		try{
		
		lv=(ListView)findViewById(R.id.listView1);
		//mList = (ListView) findViewById(R.id.list);
		Log.d("test", "test");
		Commands.livreBdd.open();
		ArrayList<Command> list = Commands.livreBdd.getAllCommand();
		Commands.livreBdd.close();
		if(list!=null){
		MonListAdapter adapt = new MonListAdapter(list, this,Commands.livreBdd);
		lv.setAdapter(adapt);
		}
		}catch(Exception e){
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Error");
			dialog.setMessage(e.toString());
			dialog.setPositiveButton("OK", null);
			dialog.show();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_list_cmd, menu);
		return true;
	}

}
