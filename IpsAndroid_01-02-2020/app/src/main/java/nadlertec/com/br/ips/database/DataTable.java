package nadlertec.com.br.ips.database;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import nadlertec.com.br.ips.R;


public class DataTable {
    private DataBase db;
    private String strTable = "";
    private String strLogCat = "";
    //private String strPrefKey = "";
    private boolean blnLogCat = false;
    private String strWhere= "";
    private String strSQL = "";

    public String DescricaoErro = "";

    public DataTable(Context context, String pTable){
        db = new DataBase(context);
        this.strTable = pTable;
        strLogCat = context.getString(R.string.LogCat);
    }

    public Cursor PesquisaCursorSQL(String pSQL){
        DescricaoErro = "";
        Cursor cReturn = null;

        try{
            cReturn = db.ListSQLCursor(pSQL);

            if(DataBase.DescricaoErro != "")
                throw new Exception("Erro na pesquisa SQL. Erro: " + DataBase.DescricaoErro);
        }
        catch(Exception ex){
            ex.printStackTrace();
            DescricaoErro = ex.getMessage();
            GravaLogCat(DescricaoErro);
        }
        return cReturn;
    }

    public Long ExecuteSQL(String pSQL){
        try{
            db.ExecutaSQL(pSQL);
            if(db.DescricaoErro.length() > 0)
                throw new Exception("Erro ao executar o comando. Erro : " + db.DescricaoErro);
            return 0l;
        }catch(Exception ex){
            ex.printStackTrace();
            DescricaoErro = ex.getMessage();
        }
        return -1l;
    }

    public Long ExecutaComandoSQLReturn(String pSQL, String pTABELA, String pCAMPO){
        long lngReturn = 0;
        try{
            lngReturn = db.ExecutaComandoSQLReturn(pSQL, pTABELA, pCAMPO);
            if(db.DescricaoErro.length() > 0)
                throw new Exception("Erro ao executar o comando. Erro : " + db.DescricaoErro);
            return lngReturn;
        }catch(Exception ex){
            ex.printStackTrace();
            DescricaoErro = ex.getMessage();
        }
        return -1l;
    }

    private void GravaLogCat(String Mensagem){
        if(blnLogCat){
            Log.w(strLogCat, Mensagem);
        }
    }
}
