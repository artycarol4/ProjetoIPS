package nadlertec.com.br.ips.database;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import nadlertec.com.br.ips.util.Preferencia;

public class DataBase {
	public static String DB_NAME = "";
	public static int VERSAO = 0;
	public static String LOGNAME = "";
	public static String DescricaoErro = "";
	public boolean AtivaLog = false;
	private static String PrimaryKey = "IPSPROJ085";
	private static String[][] vParametro;
	private Context myContext = null;
	private DataBaseAdapter db;
	
	public DataBase(Context context){
		try{
			myContext = context;
			vParametro = new String[5][2];
			vParametro[0][0] = "DB_NAME";
			vParametro[1][0] = "VERSAO";
			vParametro[2][0] = "LOGNAME";
					
			Preferencia.LeBase(context, vParametro, PrimaryKey);
			
			if(vParametro[0][1].length() > 0)
				DB_NAME = vParametro[0][1];
			
			if(!vParametro[1][1].equals("VERSAO"))
				VERSAO = Integer.parseInt(vParametro[1][1]);
			
			if(vParametro[2][1].length() > 0)
				LOGNAME = vParametro[2][1];
			
			if(DB_NAME.equals("DB_NAME") && VERSAO == 0)
				return;
			
			IniciaLog();
			
			db = new DataBaseAdapter(myContext,DB_NAME,VERSAO);
			
		}catch(Exception ex){
			DescricaoErro = "Erro na leitura das informações da base de dados." + ex.getMessage();
			ex.printStackTrace();
		}
		
	}
	
	public DataBase(){
				
	}
	
	public boolean Inicializa(Context context, String DatabaseName, int Versao, String LogName, String[] Command){
		try{
			vParametro = new String[5][2];
			vParametro[0][0] = "DB_NAME";
			vParametro[0][1] = DatabaseName;
			vParametro[1][0] = "VERSAO";
			vParametro[1][1] = Versao + "";
			vParametro[2][0] = "LOGNAME";
			vParametro[2][1] = LogName;
						
			Preferencia.GravaBase(context, vParametro, PrimaryKey);
			
			DB_NAME = DatabaseName;
			VERSAO = Versao;
			LOGNAME = LogName;
			
			IniciaLog();
			
			db = new DataBaseAdapter(context,DB_NAME,VERSAO,AtivaLog,LOGNAME,Command);
	        
			if(db.DescricaoErro.length() > 0)
	        	throw new Exception(db.DescricaoErro);
			
			db.close();
			
			return true;
									
		}catch(Exception ex){
			ex.printStackTrace();
			DescricaoErro = "Erro na gravação das informações da base de dados." + ex.getMessage();
		}
		return false;
	}

	private void IniciaLog(){
		DescricaoErro = "";
		if(db != null){
			db.LogName = LOGNAME;
			db.AtivaLog = AtivaLog;
		}
	}
	
	public boolean ExecutaSQL(String SQL)
	{
		try{
			IniciaLog();
			db.openDataBase();
			db.ExecutaComandoSQL(SQL);
			db.close();
			if(db.DescricaoErro.length() > 0)
	        	throw new Exception(db.DescricaoErro);			
			
			return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
			DescricaoErro = ex.getMessage();
		}
		
		return false;
	}

	public long ExecutaComandoSQLReturn(String SQL, String Tabela, String Campo)
	{
		Cursor cur;
		String strSQL = "";
		try{
			IniciaLog();
			db.openDataBase();
			db.ExecutaComandoSQL(SQL);
			if(db.DescricaoErro.length() > 0)
				throw new Exception(db.DescricaoErro);
			db.close();

			db.openDataBase();
			strSQL = "SELECT MAX(" + Campo + ") FROM " + Tabela;
			cur = db.PesquisaComandoSQL(SQL);
			if(db.DescricaoErro.length() > 0)
				throw new Exception(db.DescricaoErro);
			db.close();

			cur.moveToFirst();
			while (cur.isAfterLast() == false){
				return cur.getLong(cur.getColumnIndex(Campo));
			}

		}
		catch(Exception ex){
			ex.printStackTrace();
			DescricaoErro = ex.getMessage();
		}

		return 0;
	}

	public Cursor ListAllCursor(String Tabela, String[] Campos, String OrderBy){
		Cursor cur;
		try{
			IniciaLog();
			db.openDataBase();
			cur = db.Pesquisa(Tabela, Campos, null, null, null, null, OrderBy);
			db.close();
			
			if(db.DescricaoErro.length() > 0)
	        	throw new Exception(db.DescricaoErro);
			
			return cur;
		}
		catch(Exception ex){
			ex.printStackTrace();
			DescricaoErro = ex.getMessage();
		}
		
		return null;
	}
	
	public String[][] ListAllArray(String Tabela, String[] Campos, String OrderBy){
		String[][] aReturn;
		try{
			IniciaLog();
			db.openDataBase();
			aReturn = db.PesquisaArray(Tabela, Campos, null, null, null, null,  (OrderBy.length() == 0?null:OrderBy));
			db.close();
			
			if(db.DescricaoErro.length() > 0)
	        	throw new Exception(db.DescricaoErro);
			
			return aReturn;
		}
		catch(Exception ex){
			ex.printStackTrace();
			DescricaoErro = ex.getMessage();
		}
		
		return null;
	}

	public Cursor ListSQLCursor(String SQL){
		Cursor cur;
		try{
			IniciaLog();
			db.openDataBase();
			cur = db.PesquisaComandoSQL(SQL);
			db.close();
			
			if(db.DescricaoErro.length() > 0)
	        	throw new Exception(db.DescricaoErro);
			
			return cur;
		}
		catch(Exception ex){
			ex.printStackTrace();
			DescricaoErro = ex.getMessage();
		}
		
		return null;
	}
	
	public String[][] ListSQLArray(String SQL){
		String[][] aReturn;
		try{
			IniciaLog();
			db.openDataBase();
			aReturn = db.PesquisaComandoSQLArray(SQL);
			db.close();
			
			if(db.DescricaoErro.length() > 0)
	        	throw new Exception(db.DescricaoErro);
			
			return aReturn;
		}
		catch(Exception ex){
			ex.printStackTrace();
			DescricaoErro = ex.getMessage();
		}
		
		return null;
	}

	public boolean Update(String Tabela, ContentValues cValues, String Where, String[] WhereValues){
		try{
			IniciaLog();
			db.openDataBase();
			db.Update(Tabela, cValues, Where, WhereValues);
			db.close();
			
			if(db.DescricaoErro.length() > 0)
	        	throw new Exception(db.DescricaoErro);
			
			return true;
		}
		catch(Exception ex){
			DescricaoErro = ex.getMessage();
		}
		
		return false;
	}
	
	public long Insert(String Tabela, ContentValues cValues){
		long lngReturn = 0;
		try{
			IniciaLog();
			db.openDataBase();
			lngReturn = db.Insert(Tabela, null, cValues);
			db.close();
			
			if(db.DescricaoErro.length() > 0)
	        	throw new Exception(db.DescricaoErro);
			
		}
		catch(Exception ex){
			DescricaoErro = ex.getMessage();
		}
		
		return lngReturn;
	}
	
	public boolean Delete(String Tabela, String Where){
        try{
        	IniciaLog();
			db.openDataBase();
			db.Delete(Tabela, Where, null);
			db.close();
			
			if(db.DescricaoErro.length() > 0)
	        	throw new Exception(db.DescricaoErro);
			
			return true;
		}
		catch(Exception ex){
			DescricaoErro = ex.getMessage();
		}
		
		return false;
    }

	public Cursor ListCursor(String Tabela, String[] Campo, String Where, String[] WhereValor){
		Cursor cReturn;
		
		try{
			IniciaLog();
			db.openDataBase();
			cReturn = db.Pesquisa(Tabela, Campo, Where, WhereValor, null, null, null);
			
			return cReturn;
		}catch(Exception ex){
			ex.printStackTrace();
			DescricaoErro = ex.getMessage();
		}
		
		return null;
						
	}
	
	public String[][] ListArray(String Tabela, String[] Campo, String Where, String[] WhereValor){
		String[][] aReturn;
		
		try{
			IniciaLog();
			db.openDataBase();
			aReturn = db.PesquisaArray(Tabela, Campo, Where, WhereValor, null, null, null);
			
			return aReturn;
		}catch(Exception ex){
			ex.printStackTrace();
			DescricaoErro = ex.getMessage();
		}
		
		return null;
						
	}

	public boolean InsertStatement(String SQL, String[][] vValor){
		try{
			IniciaLog();
			db.openDataBase();
			db.BeginTransaction();
			
			SQLiteStatement stmt = db.CompilaStatement(SQL);
			for (int i = 0; i < vValor.length; i++) {
				for(int j = 0; j < vValor[i].length; j++){
					stmt.bindString(j + 1, vValor[i][j]);
				}
				stmt.execute();
			}
		    stmt.clearBindings();
		 
			db.CommitTransaction();
			return true;
			
		}
		catch(Exception ex){
			DescricaoErro = ex.getMessage();
			ex.printStackTrace();
			db.RoolbackTransaction();
		}
		return false;
	}
	
	public static void CopyDatabase(Context ctx, String nomeDB){  
	  
	   // Cria o banco vazio  
	   SQLiteDatabase db = ctx.openOrCreateDatabase(  
	     nomeDB, Context.MODE_PRIVATE, null);
	  
	   db.close();  
	        
	   try {  
	     // Abre o arquivo que deve estar na pasta assets  
	     InputStream is = ctx.getAssets().open(nomeDB);  
	     // Abre o arquivo do banco vazio ele fica em:  
	     // /data/data/nome.do.pacote.da.app/databases  
	     FileOutputStream fos = new FileOutputStream(  
	       ctx.getDatabasePath(nomeDB));  
	  
	     // Copia byte a byte o arquivo do assets para  
	     // o aparelho/emulador  
	     byte[] buffer = new byte[1024];  
	     int read;  
	     while ((read = is.read(buffer)) > 0){  
	       fos.write(buffer, 0, read);  
	     }  
	   } catch (IOException e) {  
	     e.printStackTrace();  
	   }   
	 }  

	public static void InicializaBaseCopia(String pBaseName, String pVersao, String pLogName, Context context){
		String[][] vParam = new String[1][2];
        
		vParam[0][0] = "DB_NAME";
		vParam[0][1] = "";
                
		Preferencia.LeBase(context, vParam, PrimaryKey);
		
		if(!vParam[0][1].equals(pBaseName)){
			CopyDatabase(context, pBaseName);

			vParam = new String[3][2];
			vParam[0][0] = "DB_NAME";
			vParam[0][1] = pBaseName;
			vParam[1][0] = "VERSAO";
			vParam[1][1] = pVersao;
			vParam[2][0] = "LOGNAME";
			vParam[2][1] = pLogName;
						
			Preferencia.GravaBase(context, vParam, PrimaryKey);
		}
	}
}
