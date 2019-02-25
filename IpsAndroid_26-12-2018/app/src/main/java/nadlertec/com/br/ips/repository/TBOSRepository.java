package nadlertec.com.br.ips.repository;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import nadlertec.com.br.ips.database.DataTable;
import nadlertec.com.br.ips.model.TBACESSORIO;
import nadlertec.com.br.ips.model.TBOS;
import nadlertec.com.br.ips.util.Utilitario;

public class TBOSRepository {
    Context cContext;
    DataTable datatable;
    public String DescricaoErro = "";

    public TBOSRepository(Context pContext){
        this.cContext = pContext;
        datatable = new DataTable(pContext,"tbpedido");
    }

    public void SyncOS(List<TBOS> pLSTPEDIDO){
        String strSQL = "";

        try{
            strSQL = "DELETE FROM tbpedido";
            datatable.ExecuteSQL(strSQL);

            for (TBOS pedido: pLSTPEDIDO) {
                strSQL = "DELETE FROM tbpedido WHERE Id_int = " + pedido.Id_int;
                datatable.ExecuteSQL(strSQL);
                if (datatable.DescricaoErro.length() > 0)
                    throw new Exception("Erro: " + datatable.DescricaoErro);

                strSQL = "DELETE FROM tbacessorio WHERE Id_int = " + pedido.Id_int;
                datatable.ExecuteSQL(strSQL);
                if (datatable.DescricaoErro.length() > 0)
                    throw new Exception("Erro: " + datatable.DescricaoErro);

                strSQL = "INSERT INTO tbpedido (Idinspetor_int_fk, Idresponsaveltecnico_int_fk, Idcliente_int_fk, Cliente_str, ";
                strSQL += "Responsaveltecnico_str, Inspetor_str, NumeroPedido_str, NumeroArt_str, Finalizado_bool, Id_int, ";
                strSQL += "Id_int_fk, Id_int_fk_2, Idnumeropedido_int, Quantidade_int, Exibir_bool) VALUES (";
                strSQL += pedido.Idinspetor_int_fk + ", ";
                strSQL += pedido.Idresponsaveltecnico_int_fk + ", ";
                strSQL += pedido.Idcliente_int_fk + ", ";
                strSQL += Utilitario.VlrString(pedido.Cliente_str) + ", ";
                strSQL += Utilitario.VlrString(pedido.Responsaveltecnico_str) + ", ";
                strSQL += Utilitario.VlrString(pedido.Inspetor_str) + ", ";
                strSQL += Utilitario.VlrString(pedido.NumeroPedido_str) + ", ";
                strSQL += Utilitario.VlrString(pedido.NumeroArt_str) + ", ";
                strSQL += pedido.Finalizado_bool ? "1" : "0" + ", ";
                strSQL += pedido.Id_int + ", ";
                strSQL += pedido.Id_int_fk + ", ";
                strSQL += pedido.Id_int_fk_2 + ", ";
                strSQL += pedido.Idnumeropedido_int + ", ";
                strSQL += pedido.Quantidade_int + ", ";
                strSQL += pedido.Exibir_bool ? "1" : "0" + ") ";
                datatable.ExecuteSQL(strSQL);
                if (datatable.DescricaoErro.length() > 0)
                    throw new Exception("Erro: " + datatable.DescricaoErro);

                for (TBACESSORIO acessorio: pedido.AcessorioList) {
                    strSQL = "INSERT INTO tbacessorio (Id_int, Idmontaacessorios_int, Acessorio_str) VALUES ( ";
                    strSQL += pedido.Id_int + ",";
                    strSQL += acessorio.Idmontaacessorios_int + ",";
                    strSQL += Utilitario.VlrString(acessorio.Acessorio_str) + ")";

                    datatable.ExecuteSQL(strSQL);
                    if (datatable.DescricaoErro.length() > 0)
                        throw new Exception("Erro: " + datatable.DescricaoErro);
                }
            }
        }catch(Exception ex){
            ex.printStackTrace();
            DescricaoErro = ex.getMessage();
        }
    }

    public List<TBOS> List(){
        Cursor cCursor = null;
        DescricaoErro = "";
        List<TBOS> lstReturn = new ArrayList<>();

        try{
            String strSQL = "SELECT * FROM tbpedido ORDER BY NumeroPedido_str";
            cCursor = datatable.PesquisaCursorSQL(strSQL);
            if(datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);
            else
            {
                cCursor.moveToFirst();
                while (cCursor.isAfterLast() == false) {
                    TBOS pedido = new TBOS();
                    pedido.Idinspetor_int_fk = cCursor.getInt(cCursor.getColumnIndex("Idinspetor_int_fk"));
                    pedido.Idresponsaveltecnico_int_fk = cCursor.getInt(cCursor.getColumnIndex("Idresponsaveltecnico_int_fk"));
                    pedido.Idcliente_int_fk = cCursor.getInt(cCursor.getColumnIndex("Idcliente_int_fk"));
                    pedido.Cliente_str = cCursor.getString(cCursor.getColumnIndex("Cliente_str"));
                    pedido.Responsaveltecnico_str = cCursor.getString(cCursor.getColumnIndex("Responsaveltecnico_str"));
                    pedido.Inspetor_str = cCursor.getString(cCursor.getColumnIndex("Inspetor_str"));
                    pedido.NumeroPedido_str = cCursor.getString(cCursor.getColumnIndex("NumeroPedido_str"));
                    pedido.NumeroArt_str = cCursor.getString(cCursor.getColumnIndex("NumeroArt_str"));
                    pedido.Finalizado_bool = cCursor.getInt(cCursor.getColumnIndex("Finalizado_bool")) == 0 ? false : true;;
                    pedido.Id_int = cCursor.getInt(cCursor.getColumnIndex("Id_int"));
                    pedido.Id_int_fk = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk"));
                    pedido.Id_int_fk_2 = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk_2"));
                    pedido.Idnumeropedido_int = cCursor.getInt(cCursor.getColumnIndex("Idnumeropedido_int"));
                    pedido.Quantidade_int = cCursor.getInt(cCursor.getColumnIndex("Quantidade_int"));
                    pedido.Exibir_bool = cCursor.getInt(cCursor.getColumnIndex("Exibir_bool")) == 0 ? false : true;

                    pedido.AcessorioList = ListAcessorio(pedido.Id_int);

                    lstReturn.add(pedido);
                    cCursor.moveToNext();
                }
                cCursor.close();

                return lstReturn;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            DescricaoErro = ex.getMessage();
        }

        return null;
    }

    private List<TBACESSORIO> ListAcessorio(int pId_int){
        Cursor cCursor = null;
        DescricaoErro = "";
        List<TBACESSORIO> lstReturn = new ArrayList<>();

        try{
            String strSQL = "SELECT * FROM tbacessorio WHERE Id_int = " + pId_int + " ORDER BY Acessorio_str";
            cCursor = datatable.PesquisaCursorSQL(strSQL);
            if(datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);
            else
            {
                cCursor.moveToFirst();
                while (cCursor.isAfterLast() == false) {
                    TBACESSORIO model = new TBACESSORIO();
                    model.Idmontaacessorios_int = cCursor.getInt(cCursor.getColumnIndex("Idmontaacessorios_int"));
                    model.Acessorio_str = cCursor.getString(cCursor.getColumnIndex("Acessorio_str"));

                    lstReturn.add(model);
                    cCursor.moveToNext();
                }
                cCursor.close();

                return lstReturn;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            DescricaoErro = ex.getMessage();
        }

        return null;
    }

    public List<TBOS> Search(TBOS pOS){
        Cursor cCursor = null;
        DescricaoErro = "";
        List<TBOS> lstReturn = new ArrayList<>();
        String strWHERE = "";

        try{
            String strSQL = "SELECT * FROM tbpedido ";
            if(pOS.NumeroPedido_str.length() > 0)
                strWHERE += " WHERE NumeroPedido_str LIKE '%" + pOS.NumeroPedido_str + "%'";
            if(pOS.Cliente_str.length() > 0)
                strWHERE += (strWHERE == "" ? " WHERE " : " AND") + " Cliente_str LIKE '%" + pOS.Cliente_str + "%'";
            if(pOS.Inspetor_str.length() > 0)
                strWHERE += (strWHERE == "" ? " WHERE " : " AND") + " Inspetor_str LIKE '%" + pOS.Inspetor_str + "%'";
            strSQL += strWHERE + " ORDER BY NumeroPedido_str";
            cCursor = datatable.PesquisaCursorSQL(strSQL);
            if(datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);
            else
            {
                cCursor.moveToFirst();
                while (cCursor.isAfterLast() == false) {
                    TBOS pedido = new TBOS();
                    pedido.Idinspetor_int_fk = cCursor.getInt(cCursor.getColumnIndex("Idinspetor_int_fk"));
                    pedido.Idresponsaveltecnico_int_fk = cCursor.getInt(cCursor.getColumnIndex("Idresponsaveltecnico_int_fk"));
                    pedido.Idcliente_int_fk = cCursor.getInt(cCursor.getColumnIndex("Idcliente_int_fk"));
                    pedido.Cliente_str = cCursor.getString(cCursor.getColumnIndex("Cliente_str"));
                    pedido.Responsaveltecnico_str = cCursor.getString(cCursor.getColumnIndex("Responsaveltecnico_str"));
                    pedido.Inspetor_str = cCursor.getString(cCursor.getColumnIndex("Inspetor_str"));
                    pedido.NumeroPedido_str = cCursor.getString(cCursor.getColumnIndex("NumeroPedido_str"));
                    pedido.NumeroArt_str = cCursor.getString(cCursor.getColumnIndex("NumeroArt_str"));
                    pedido.Finalizado_bool = cCursor.getInt(cCursor.getColumnIndex("Finalizado_bool")) == 0 ? false : true;;
                    pedido.Id_int = cCursor.getInt(cCursor.getColumnIndex("Id_int"));
                    pedido.Id_int_fk = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk"));
                    pedido.Id_int_fk_2 = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk_2"));
                    pedido.Idnumeropedido_int = cCursor.getInt(cCursor.getColumnIndex("Idnumeropedido_int"));
                    pedido.Quantidade_int = cCursor.getInt(cCursor.getColumnIndex("Quantidade_int"));
                    pedido.Exibir_bool = cCursor.getInt(cCursor.getColumnIndex("Exibir_bool")) == 0 ? false : true;

                    pedido.AcessorioList = ListAcessorio(pedido.Id_int);

                    lstReturn.add(pedido);
                    cCursor.moveToNext();
                }
                cCursor.close();

                return lstReturn;
            }
        }catch(Exception ex){
            ex.printStackTrace();
            DescricaoErro = ex.getMessage();
        }

        return null;
    }
}
