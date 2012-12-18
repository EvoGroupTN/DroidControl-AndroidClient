package evo.droidcontrol;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LivresBDD {

	int VERSION_BDD ;
	String NOM_BDD ;

	String TABLE_LIVRES;
	String COL_ID ;
	int NUM_COL_ID;
	String COL_ISBN;
	int NUM_COL_ISBN;
	String COL_TITRE ;
	int NUM_COL_TITRE;

	private SQLiteDatabase bdd;

	private MaBaseSQLite maBaseSQLite;



	Context context;
	public LivresBDD(Context context, String nOM_BDD2,
			String tABLE_LIVRES2, int vERSION_BDD2, String cOL_ID2,
			int nUM_COL_ID2, String cOL_ISBN2, int nUM_COL_ISBN2,
			String cOL_TITRE2, int nUM_COL_TITRE2) {

		this.context =context;
		NOM_BDD=nOM_BDD2;
		TABLE_LIVRES=tABLE_LIVRES2;
		VERSION_BDD=vERSION_BDD2;
		COL_ID=cOL_ID2;
		COL_ISBN=cOL_ISBN2;
		COL_TITRE=cOL_TITRE2;

		NUM_COL_ID=nUM_COL_ID2;
		NUM_COL_ISBN=nUM_COL_ISBN2;
		NUM_COL_TITRE=nUM_COL_TITRE2;

		maBaseSQLite = new MaBaseSQLite(context, NOM_BDD, null, VERSION_BDD,TABLE_LIVRES,COL_ID,COL_ISBN,COL_TITRE);

	}



	public void open(){
		//on ouvre la BDD en �criture
		bdd = maBaseSQLite.getWritableDatabase();
	}


	boolean isOpened(){
		if(bdd.isOpen())return true;
		else return false;
	}
	
	public long inserer(String ch1,String ch2){
		Log.d("tbl", TABLE_LIVRES);
		ContentValues values = new ContentValues();
		values.put(COL_ISBN, ch1);
		values.put(COL_TITRE, ch2);
		open();
		//Log.d("tbl", TABLE_LIVRES);
		return bdd.insert(TABLE_LIVRES, null, values);
		
	}
	
	public void supprimer(int id){
		open();
		bdd.delete(TABLE_LIVRES, COL_ID+"="+id, null);
		close();
	}
	
	public void updateContact(int id, String name, String title) 
	   {
	      ContentValues editCon = new ContentValues();
	      editCon.put("name", name);
	      editCon.put("cap", title);

	      open();
	      bdd.update(TABLE_LIVRES, editCon, COL_ID+"="+id, null);
	      close();
	   }
	
	public void close(){
		//on ferme l'acc�s � la BDD
		bdd.close();
	}

	public String[] getcommand(String name){
		Cursor c = bdd.rawQuery("SELECT * FROM "+TABLE_LIVRES+" WHERE "+COL_ISBN+" = '"+name+"'", null);
		c.moveToFirst();
		if(c.getCount()==1){
			String[] s={c.getString(NUM_COL_ISBN),c.getString(NUM_COL_TITRE)}; 
			return(s);
		}else
			return null;
	}

	public ArrayList<Command> getAllCommand() {
		ArrayList<Command> list = new ArrayList<Command>();
		Cursor c = bdd.query(TABLE_LIVRES, new String[]{COL_ID,COL_ISBN,COL_TITRE},null, null, null, null, null);
		c.moveToFirst();
		while (!c.isAfterLast()) {
			int id=c.getInt(0);
			String isbn =c.getString(1);
			String titre = c.getString(2);
			Command l = new Command(id,isbn,titre);
			list.add(l);
			c.moveToNext();
		}
		c.close();
		return list;
	}
}