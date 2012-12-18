package evo.droidcontrol;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Add extends Activity implements OnClickListener {

	EditText txtName;
	EditText txtVal;
	Button btnOK;
	Button btnCancel;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add);
		txtName = (EditText)findViewById(R.id.editText1);
		txtVal = (EditText)findViewById(R.id.editText2);
		btnOK = (Button)findViewById(R.id.button1);
		btnCancel = (Button)findViewById(R.id.button2);
		btnOK.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_add, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		Commands.livreBdd.open();
		Commands.livreBdd.inserer(txtName.getText().toString(), txtVal.getText().toString());
		Commands.livreBdd.close();
		finish();
	}

}
