package evo.droidcontrol;



import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MonListAdapter extends BaseAdapter
{
	// Une liste de contact
	private ArrayList<Command> mListP;
	LivresBDD livrebdd;
	ImageButton btnDel;
	private Context context;
	public MonListAdapter(ArrayList<Command> list,Context context, LivresBDD lbdd) {
		mListP=list;
		this.context=context;
		livrebdd = lbdd;
	}

	@Override
	public int getCount() {
		return mListP.size();
	}

	@Override
	public Object getItem(int position) {
		return mListP.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LinearLayout layoutItem;
		LayoutInflater mInflater =  LayoutInflater.from(context);
		//(1) : R�utilisation des layouts
		if (convertView == null) {
			
			//Initialisation de notre item � partir du layout XML "personne_layout.xml"
			layoutItem = (LinearLayout) mInflater.inflate(R.layout.view, parent, false);
			convertView = layoutItem;
			/*TextView tv_num = (TextView)layoutItem.findViewById(R.id.tv1);
			holder.num=(TextView)layoutItem.findViewById(R.id.tv1);
			holder.nom=(TextView)layoutItem.findViewById(R.id.tv2);
			holder.pren=(TextView)layoutItem.findViewById(R.id.tv3);
			holder.position = position;
			convertView.setTag(holder);*/
			
		} else {
			layoutItem = (LinearLayout) convertView;
		}
		//(2) : R�cup�ration des TextView de notre layout
		TextView tv_num = (TextView)layoutItem.findViewById(R.id.textView1);
		TextView tv_Nom = (TextView)layoutItem.findViewById(R.id.textView2);
		TextView tv_Prenom = (TextView)layoutItem.findViewById(R.id.textView3);
		//CheckBox chk = (CheckBox) layoutItem.findViewById(R.id.checkBox1);
		btnDel = (ImageButton)layoutItem.findViewById(R.id.imageButton1);
		//ImageButton btnEdit = (ImageButton) layoutItem.findViewById(R.id.imageButton2);
		/*btnEdit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Take List from parent view
            	
            	//Principale.this.txtISBN.setText(mListP.get(position).getIsbn());
            }
        });*/
       //btnDel.setTag(holder);
		//(3) : Renseignement des valeurs
		
		tv_num.setText(mListP.get(position).getId()+"");
		tv_Nom.setText(mListP.get(position).getIsbn());
		tv_Prenom.setText(mListP.get(position).getTitre());
		btnDel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Take List from parent view
            	
            	livrebdd.supprimer(mListP.get(position).getId());
                mListP.remove(position);
                notifyDataSetChanged();
            }
        });
		
		//holder.num.setText(mListP.get(position).toString());
	    //holder.position = position;
		//On retourne l'item cr��.
		return convertView;
	}

	public void add(Command livre) {
		mListP.add(livre);
		
	}
	

	public void update(Command livre) {
		for(int i=0;i<mListP.size();i++)
		{
			if(mListP.get(i).getId()==livre.getId())
			{
				mListP.set(i, livre);
				break;
			}
		}
	}


}