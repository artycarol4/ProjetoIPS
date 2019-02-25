package nadlertec.com.br.ips.database;

import java.io.File;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

class DataBaseAdapter extends SQLiteOpenHelper{
	public String DB_NAME = "";
	public String DB_PATH = "";
	public boolean AtivaLog = false;
	public int DB_VERSAO = 0;
	public String LogName = "";
	public String[] ScriptBanco;
	private SQLiteDatabase myDataBase;
	private Context myContext = null;

	static SQLiteDatabase sqliteDataBase;
	public Context context;

	public String DescricaoErro = "";
	
	private boolean blnOpenDatabase = false;
	
	private static DataBaseAdapter mDBConnection;
	
	/** 
	* Inicializa o framework do banco de dados 
	* 
	* @param  //Propriedade Context - context da aplica��o;
	* @param  //Propriedade Database - nome do banco de dados
	* @param  //Propriedade Versao - vers�o do banco de dados
	* @param  //Propriedade LogAtivo - ativa os log's de monitoramento do framework
	* @param  //Propriedade LogName - nome do arquivo de log
	* 
	* @param  //Observação: Os scripts de cria��o do banco de dados devem estar no strings.xml.
	* @param              //Ex.: <string-array name="CreateTable">
	* @param                      //<item>CREATE TABLE IF NOT EXISTS tbcidade( CidadeId int, Cidade LONGTEXT);</item>
	* @param                   </string-array>
	* 
	* @return nenhum
	* @throws //Propriedade DescricaoErro
	*/
    //TODO: 
	public DataBaseAdapter(Context context, String Database, int Versao, boolean LogAtivo, String LogName, String[] ComandoSQL) {       
	    super(context, Database, null ,Versao);
	    this.context = context;
	    File fTemp = context.getDatabasePath(Database);
	    this.DB_PATH = fTemp.getPath();
	    this.DB_NAME = Database;
	    this.DB_VERSAO = Versao;
	    this.LogName = LogName;
	    this.AtivaLog = LogAtivo;
	    this.ScriptBanco = ComandoSQL;
	    
	    try {
			createDataBase(ComandoSQL);
		} catch (IOException e) {
			DescricaoErro = "Erro na inicializa��o do DataBaseAdapter." + e.getMessage();
			e.printStackTrace();
			GravaLog("Erro na cria��o do banco de dados. IOException. " + e.getMessage());
		}
	    catch(Exception ex){
	    	DescricaoErro = "Erro na inicializa��o do DataBaseAdapter." + ex.getMessage();
			ex.printStackTrace();
			GravaLog("Erro na cria��o do banco de dados. " + ex.getMessage());
	    }
	}
	
	public DataBaseAdapter(Context context, String Database, int Versao, boolean LogAtivo, String LogName) {       
	    super(context, Database,null,Versao);
	    this.context = context;
	    File fTemp = context.getDatabasePath(Database);
	    this.DB_PATH = fTemp.getPath();
	    this.DB_NAME = Database;
	    this.DB_VERSAO = Versao;
	    this.LogName = LogName;
	    try {
			createDataBase(ScriptBanco);
		} catch (IOException e) {
			DescricaoErro = e.getMessage();
			e.printStackTrace();
			GravaLog("Erro na cria��o do banco de dados. IOException. " + e.getMessage());
		}
	    catch(Exception ex){
	    	DescricaoErro = "Erro na inicializa��o do DataBaseAdapter." + ex.getMessage();
			ex.printStackTrace();
			GravaLog("Erro na cria��o do banco de dados. " + ex.getMessage());
	    }
	}
	
	public DataBaseAdapter(Context context, String DbName, int Versao){
		super(context, DbName, null, Versao);
		this.myContext = context;
		DB_NAME = DbName;
		DB_PATH = "/data/data/"
				+ context.getApplicationContext().getPackageName()
				+ "/databases/";
		GravaLog("Path: " + DB_PATH);
	}
	
	public boolean ExecutaComandoSQL(String ComandoSQL) throws SQLiteException{
		
		try{
			openDataBase();
			sqliteDataBase.execSQL(ComandoSQL);
			closeDataBase();
			return true;
			
		}
		catch(Exception ex){
			DescricaoErro = "Erro na excu��o do comando sql. " + ComandoSQL + " Erro: " + ex.getMessage();
			GravaLog("Erro na excu��o do comando sql. " + ComandoSQL + " Erro: " + ex.getMessage());
			return false;
		}
		
	}

	public boolean ExecutaComandoSQLReturn(String ComandoSQL) throws SQLiteException{
		try{
			openDataBase();
			sqliteDataBase.execSQL(ComandoSQL);
			closeDataBase();
			return true;

		}
		catch(Exception ex){
			DescricaoErro = "Erro na excu��o do comando sql. " + ComandoSQL + " Erro: " + ex.getMessage();
			GravaLog("Erro na excu��o do comando sql. " + ComandoSQL + " Erro: " + ex.getMessage());
			return false;
		}

	}

	public boolean ExecutaScripCriacaoBanco() throws SQLiteException{
		try{
			if(ScriptBanco != null){
			    if(ScriptBanco.length > 0){
			    	openDataBase();
			    	
			    	for(int i = 0; i < ScriptBanco.length; i++){
			    		sqliteDataBase.execSQL(ScriptBanco[i]);
			    		GravaLog("Executando Script N�: " + (i + 1) + " (" + ScriptBanco[i] + ")");
			    	}
			    	closeDataBase();
			    }
			    else{
			    	GravaLog("N�o h� tabelas para serem criadas.");
			    }
		    }
			
			return true;
			
		}
		catch(Exception ex){
			GravaLog("Erro na excu��o dos Script de cria��o do banco de dados. Erro: " + ex.getMessage());
			return false;
		}
		
	}

	private void createDataBase(String[] ComandoSQL) throws IOException{
		File fDatabase = context.getDatabasePath(DB_NAME);
	    
		try{
		    if(!fDatabase.exists()){
		    	GravaLog("Criando o banco de dados.");
		    	
		    	this.getWritableDatabase();
		    	
		    	GravaLog("Banco de dados criado com sucesso.");
		    	
		    }
		    else{
		    	GravaLog("O banco de dados j� esta criado.");
		    }
		    
		    if(!ExecutaScripCriacaoBanco())
		    	return;
		}
		catch(SQLiteException e){
			DescricaoErro = "Erro na cria��o do banco de dados. " + e.getMessage();
			GravaLog("Erro na cria��o do banco de dados. " + e.getMessage());
		}
	}
	
	private void GravaLog(String Mensagem){
		if(AtivaLog)
			Log.w(LogName,Mensagem);
	}
	
	public void openDataBase() throws SQLException{
		String strTemp = "";
		strTemp = DB_PATH;
		if(strTemp.indexOf(DB_NAME) == -1)
			strTemp = DB_PATH + DB_NAME;
		
		try{
			sqliteDataBase.close();
		}
		catch(Exception ex){
			//ex.printStackTrace();
		}
		
    	sqliteDataBase = SQLiteDatabase.openDatabase(strTemp, null, SQLiteDatabase.OPEN_READWRITE);
    	blnOpenDatabase = true;

     }
	
	public void closeDataBase(){
		sqliteDataBase.close();
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
 
	}

	private boolean checkDataBase() {
		SQLiteDatabase checkDB = null;
		try {
			String myPath = DB_PATH + DB_NAME;
			checkDB = SQLiteDatabase.openDatabase(myPath, null,
					SQLiteDatabase.OPEN_READONLY);

		} catch (SQLiteException e) {
			DescricaoErro = "Erro na verifica��o da base de dados: " + e.getMessage();
			GravaLog("Erro na verifica��o da base de dados: " + e.getMessage());
		}
		if (checkDB != null) {
			checkDB.close();
		}
		return checkDB != null ? true : false;
	}

	public void VerificaDataBase() throws IOException {
		GravaLog("Verificando se a base de dados existe...");
		boolean dbExist = checkDataBase();
		if (!dbExist){
			try {
				this.getReadableDatabase();
				GravaLog("Base de dados criada...");
				
			}catch (Exception e) {
				DescricaoErro = "Erro na cria��o da base de dados: " + e.getMessage();
				GravaLog("Erro na cria��o da base de dados: " + e.getMessage());
			}
		}
		
		try {
			openDataBase();
		
			GravaLog("Criando as Tabelas...");
			//myDataBase.execSQL("CREATE TABLE IF NOT EXISTS tbtime (Id INTEGER, Time01 long, Time02 long, Data long, Enviado int, Parado int);");
			
			GravaLog("Tabelas criadas...");
			
		}catch (Exception e) {
			DescricaoErro = "Erro na cria��o da base de dados: " + e.getMessage();
			GravaLog("Erro na cria��o da base de dados: " + e.getMessage());
		}
		
	}

	public static synchronized DataBaseAdapter getDBAdapterInstance(Context context, String DbName, int Versao) {
		if (mDBConnection == null) {
			mDBConnection = new DataBaseAdapter(context, DbName, Versao);
		}
		return mDBConnection;
	}
	 /**
	 * Seleciona os registros do banco
	 * @param tableName
	 * @param tableColumns
	 * @param whereClase
	 * @param whereArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return Cursor com o retorno da consulta.
	 */
	public Cursor Pesquisa(String tableName, String[] tableColumns,
			String whereClase, String whereArgs[], String groupBy,
			String having, String orderBy) throws SQLiteException{
			Cursor cursor;
			try{
				cursor = sqliteDataBase.query(tableName, tableColumns, whereClase, whereArgs,
						groupBy, having, orderBy);
								
				return cursor;
			}
			catch(Exception ex){
				DescricaoErro = "Erro no m�todo Pesquisa. " + ex.getMessage();
				GravaLog("Erro no m�todo Pesquisa. " + ex.getMessage());
				return null;
			}
		
	}
	
	 /**
	 * Seleciona os registros do banco de dados e retorna um array de string
	 * @param tableName
	 * @param tableColumns
	 * @param whereClase
	 * @param whereArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return Array de string com o resultado da pesquisa
	 */
	public String [][] PesquisaArray(String tableName, String[] tableColumns,
			String whereClase, String whereArgs[], String groupBy,
			String having, String orderBy) throws SQLiteException{
			Cursor cursor;
			int intCount= 0;
			String[][] vRetorno = new String[0][0];
			
			try{
				cursor = sqliteDataBase.query(tableName, tableColumns, whereClase, whereArgs,
						groupBy, having, orderBy);
				
				return vRetorno = ConverteCursorString(cursor);
			}
			catch(Exception ex){
				DescricaoErro = "Erro no m�todo PesquisaArray. " + ex.getMessage();
				GravaLog("Erro no m�todo PesquisaArray. " + ex.getMessage());
				return null;
			}
		
	}

	/**
	 * Convert Cursor em um array de String
	 * 
	 * @param cursor cursor que ser� convertido
	 * 
	 * @return Array de String com as informa��es do cursor.
	 */
	public String[][] ConverteCursorString(Cursor cursor) throws SQLiteException{
		int intCount= 0;
		String[][] vRetorno = new String[0][0];
		
		try{
			if(cursor.getCount() > 0){
				vRetorno = new String[cursor.getCount()][cursor.getColumnCount()];
				cursor.moveToFirst();
				
				while (cursor.isAfterLast() == false) {
					for(int i = 0; i < cursor.getColumnCount();i++){
						vRetorno[intCount][i] = cursor.getString(i);
					}
					
			        intCount++;
			        cursor.moveToNext();
			    }
			}
			cursor.close();
							
			return vRetorno;
		}
		catch(Exception ex){
			DescricaoErro = "Erro no m�todo ConverteCursorString. " + ex.getMessage();
			GravaLog("Erro no m�todo ConverteCursorString. " + ex.getMessage());
			return null;
		}
	}

	/**
	 * Seleciona os registros do banco de dados executando uma string de consulta
	 * 
	 * @param ComandoSQL string com o comando de consulta
	 * 
	 * @return Cursor com o retorno da consulta.
	 */
	public Cursor PesquisaComandoSQL(String ComandoSQL) throws SQLiteException{
	    Cursor cursor;
		try{
				cursor = sqliteDataBase.rawQuery(ComandoSQL, null);
				return cursor;
		}
		catch(Exception ex){
			DescricaoErro = "Erro no m�todo PesquisaComandoSQL." + ex.getMessage();
			GravaLog("Erro no m�todo PesquisaComandoSQL." + ex.getMessage());
			return null;
		}
	    
	}

	 /**
	 * Seleciona os registros do banco de dados executando uma string de consulta e retornando um array de string com o resultado
	 * 
	 * @param //tableName
	 *
	 * @return Array de string com o resultado da pesquisa
	 */
	public String [][] PesquisaComandoSQLArray(String ComandoSQL) throws SQLiteException{
			int intCount= 0;
			String[][] vRetorno = null;
			
			try{
				Cursor cursor = sqliteDataBase.rawQuery(ComandoSQL, null);
				
				if(cursor.getCount() > 0){
					vRetorno = new String[cursor.getCount()][cursor.getColumnCount()];
					cursor.moveToFirst();
					
					while (cursor.isAfterLast() == false) {
						for(int i = 0; i < cursor.getColumnCount();i++){
							vRetorno[intCount][i] = cursor.getString(i);
						}
						
				        intCount++;
				        cursor.moveToNext();
				    }
				}
				cursor.close();
								
				return vRetorno;
			}
			catch(Exception ex){
				DescricaoErro = "Erro no m�todo PesquisaComandoSQLArray. " + ex.getMessage();
				GravaLog("Erro no m�todo PesquisaComandoSQLArray. " + ex.getMessage());
				return null;
			}
		
	}
	
	/**
	 * This function used to insert the Record in DB. 
	 * @param tableName
	 * @param nullColumnHack
	 * @param initialValues
	 * @return the row ID of the newly inserted row, or -1 if an error occurred
	 */
	public long Insert(String tableName, String nullColumnHack,ContentValues initialValues) throws SQLiteException{
		long lngReturn = 0;
		try{
			lngReturn = sqliteDataBase.insert(tableName, nullColumnHack, initialValues);
			return lngReturn;
		}
		catch(Exception ex){
			DescricaoErro = "Erro no m�todo Insert. " + ex.getMessage();
			GravaLog("Erro no m�todo Insert. " + ex.getMessage());
		}
		
		return 0;
	}
	
	/**
	 * Atualiza informa��es do banco do dados.
	 * @param tableName
	 * @param initialValues
	 * @param whereClause
	 * @param whereArgs
	 * @return True se n�o houver erro
	 */
	public boolean Update(String tableName, ContentValues initialValues, String whereClause, String whereArgs[]) throws SQLiteException{
		try{
			return sqliteDataBase.update(tableName, initialValues, whereClause,	whereArgs) > 0;
		}
		catch(Exception ex){
			DescricaoErro = "Erro no m�todo Update. " + ex.getMessage();
			GravaLog("Erro no m�todo Update. " + ex.getMessage());
			return false;
		}
	}
	
	/**
	 * This function used to delete the Record in DB.
	 * @param tableName
	 * @param whereClause
	 * @param whereArgs
	 * @return 0 in case of failure otherwise return no of row(s) are deleted.
	 */
	public int Delete(String tableName, String whereClause,String[] whereArgs) throws SQLiteException{
		try{
			return sqliteDataBase.delete(tableName, whereClause, whereArgs);
		}
		catch(Exception ex){
			DescricaoErro = "Erro no m�todo Delete. " + ex.getMessage();
			GravaLog("Erro no m�todo Delete. " + ex.getMessage());
			return 0;
		}
	}

	public SQLiteStatement CompilaStatement(String SQL){
		try{
			return sqliteDataBase.compileStatement(SQL);
		}
		catch(Exception ex){
			DescricaoErro = ex.getMessage();
			ex.printStackTrace();
		}
		return null;
	}
	
	public void BeginTransaction(){
		sqliteDataBase.beginTransaction();
	}
	
	public void CommitTransaction(){
		sqliteDataBase.setTransactionSuccessful();
		sqliteDataBase.endTransaction();
	}
	
	public void RoolbackTransaction(){
		sqliteDataBase.endTransaction();
	}
}
