package evo.droidcontrol;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.widget.Toast;

public class MaBaseSQLite extends SQLiteOpenHelper {

	public   String TABLE_LIVRES;
	public   String COL_ID ;
	public   String COL_ISBN ;
	public   String COL_TITRE ;
	public   String CREATE_BDD;
	
	SQLiteDatabase db;
	
	public MaBaseSQLite(Context context, String name, CursorFactory factory, int version,String table,String a1,String a2,String a3) {
		super(context, name, factory, version);

		TABLE_LIVRES=table;
		COL_ID=a1;
		COL_ISBN=a2;
		COL_TITRE=a3;


		CREATE_BDD = "CREATE TABLE " + TABLE_LIVRES + " ("
		+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ISBN + " TEXT NOT NULL, "
		+ COL_TITRE + " TEXT NOT NULL);";
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		//on cr�� la table � partir de la requ�te �crite dans la variable CREATE_BDD
		db.execSQL(CREATE_BDD);
		
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//On peut faire ce qu'on veut ici moi j'ai d�cid� de supprimer la table et de la recr�er
		//comme �a lorsque je change la version les id repartent de 0
		db.execSQL("DROP TABLE " + TABLE_LIVRES + ";");
		onCreate(db);
	}


}