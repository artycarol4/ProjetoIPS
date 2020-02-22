package nadlertec.com.br.ips.repository;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import nadlertec.com.br.ips.database.DataTable;
import nadlertec.com.br.ips.model.TBLAUDO;
import nadlertec.com.br.ips.model.TBOS;
import nadlertec.com.br.ips.util.Utilitario;

public class TBLAUDORepository {
    Context cContext;
    DataTable datatable;
    public String DescricaoErro = "";

    public TBLAUDORepository(Context pContext){
        this.cContext = pContext;
        datatable = new DataTable(pContext,"tblaudo");
    }

    public void ListContato(TBLAUDO pTBLAUDO){
        Cursor cCursor = null;
        DescricaoErro = "";
        String strTemp = "";

        try{
            String strSQL = "SELECT Contato_str, Telefone_str, Setor_str FROM tblaudo ";
            strSQL += " WHERE LAUDOID = (SELECT IFNULL(MAX(LAUDOID),0) FROM tblaudo ";
            strSQL += "                  WHERE Id_int = " + pTBLAUDO.Id_int + ")";

            cCursor = datatable.PesquisaCursorSQL(strSQL);
            if(datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);
            else
            {
                cCursor.moveToFirst();
                while (cCursor.isAfterLast() == false) {
                    pTBLAUDO.Contato_str = cCursor.getString(cCursor.getColumnIndex("Contato_str"));
                    pTBLAUDO.Telefone_str =  cCursor.getString(cCursor.getColumnIndex("Telefone_str"));
                    pTBLAUDO.Setor_str =  cCursor.getString(cCursor.getColumnIndex("Setor_str"));
                    cCursor.moveToNext();
                }
                cCursor.close();
            }

            if (datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);

        }catch(Exception ex){
            ex.printStackTrace();
            DescricaoErro= ex.getMessage();
        }
    }

    public void Insert(TBLAUDO pLAUDO){
        DescricaoErro = "";
        String strSQL = "";

        try{
            pLAUDO.LAUDOID = MAXID();

            strSQL = "INSERT INTO tblaudo (LAUDOID, Id_int, Id_int_fk, Id_int_fk_2, Fabricante_str, Capacidade_str, ";
            strSQL += "Contato_str, Telefone_str, Tag_str, Setor_str, Modelo_str, Dimensoes_str, Metodologiainspecao_str, ";
            strSQL += "Certificadofabricante_str, Registroinspecao_str, Acessorio_str, Registroreparo_str, Recomendacoes_str, ";
            strSQL += "Caminhoevidenciafotograficaum_str, Caminhoevidenciafotograficadois_str, ";
            strSQL += "Caminhoevidenciafotograficatres_str, Caminhoevidenciafotograficaquatro_str, ";
            strSQL += "Evidenciafotograficaum_sync, Evidenciafotograficadois_sync, ";
            strSQL += "Evidenciafotograficatres_sync, Evidenciafotograficaquatro_sync, DemaisInformacoesEmpresa_str, ";
            strSQL += "DemaisInformacoesAcessorios_str, ConclusaoTecnica_str, NumeroArt_str) VALUES (";
            strSQL += pLAUDO.LAUDOID + ", ";
            strSQL += pLAUDO.Id_int + ", ";
            strSQL += pLAUDO.Id_int_fk + ", ";
            strSQL += pLAUDO.Id_int_fk_2 + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Fabricante_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Capacidade_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Contato_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Telefone_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Tag_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Setor_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Modelo_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Dimensoes_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Metodologiainspecao_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Certificadofabricante_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Registroinspecao_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Acessorio_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Registroreparo_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Recomendacoes_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Caminhoevidenciafotograficaum_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Caminhoevidenciafotograficadois_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Caminhoevidenciafotograficatres_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.Caminhoevidenciafotograficaquatro_str) + ", ";
            strSQL += pLAUDO.Evidenciafotograficaum_sync + ", ";
            strSQL += pLAUDO.Evidenciafotograficadois_sync + ", ";
            strSQL += pLAUDO.Evidenciafotograficatres_sync + ", ";
            strSQL += pLAUDO.Evidenciafotograficaquatro_sync + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.DemaisInformacoesEmpresa_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.DemaisInformacoesAcessorios_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.ConclusaoTecnica_str) + ", ";
            strSQL += Utilitario.VlrString(pLAUDO.NumeroArt_str) + ") ";

            datatable.ExecuteSQL(strSQL);
            if (datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);

        }catch(Exception ex){
            ex.printStackTrace();
            DescricaoErro = ex.getMessage();
        }
    }

    private int MAXID(){
        Cursor cCursor = null;
        DescricaoErro = "";
        String strSQL = "SELECT IFNULL(MAX(LAUDOID),0) + 1 FROM tblaudo";
        int intRETURN = 0;

        try{
            cCursor = datatable.PesquisaCursorSQL(strSQL);
            if(datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);
            else
            {
                cCursor.moveToFirst();
                while (cCursor.isAfterLast() == false) {
                    intRETURN =  cCursor.getInt(0);
                    cCursor.moveToNext();
                }
                cCursor.close();
            }

            if (datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);

        }catch(Exception ex){
            ex.printStackTrace();
            DescricaoErro = ex.getMessage();
        }

        return intRETURN;
    }

    public List<TBLAUDO> List(long pId_int){
        Cursor cCursor = null;
        DescricaoErro = "";
        List<TBLAUDO> lstReturn = new ArrayList<>();
        try{
            String strSQL = "SELECT * FROM tblaudo WHERE Id_int = " + pId_int;

            cCursor = datatable.PesquisaCursorSQL(strSQL);
            if(datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);
            else
            {
                cCursor.moveToFirst();
                while (cCursor.isAfterLast() == false) {
                    TBLAUDO laudo = new TBLAUDO();
                    laudo.NumeroPedido_str = cCursor.getString(cCursor.getColumnIndex("NumeroPedido_str"));
                    laudo.Id_int = cCursor.getInt(cCursor.getColumnIndex("Id_int"));
                    laudo.Id_int_fk = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk"));
                    laudo.Id_int_fk_2 = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk_2"));
                    laudo.Fabricante_str = cCursor.getString(cCursor.getColumnIndex("Fabricante_str"));
                    laudo.Capacidade_str = cCursor.getString(cCursor.getColumnIndex("Capacidade_str"));
                    laudo.Contato_str = cCursor.getString(cCursor.getColumnIndex("Contato_str"));
                    laudo.ConclusaoTecnica_str = cCursor.getString(cCursor.getColumnIndex("ConclusaoTecnica_str"));
                    laudo.Telefone_str = cCursor.getString(cCursor.getColumnIndex("Telefone_str"));
                    laudo.Tag_str = cCursor.getString(cCursor.getColumnIndex("Tag_str"));
                    laudo.Setor_str = cCursor.getString(cCursor.getColumnIndex("Setor_str"));
                    laudo.Modelo_str = cCursor.getString(cCursor.getColumnIndex("Modelo_str"));
                    laudo.NumeroArt_str = cCursor.getString(cCursor.getColumnIndex("NumeroArt_str"));
                    laudo.DemaisInformacoesAcessorios_str = cCursor.getString(cCursor.getColumnIndex("DemaisInformacoesAcessorios_str"));
                    laudo.DemaisInformacoesEmpresa_str = cCursor.getString(cCursor.getColumnIndex("DemaisInformacoesEmpresa_str"));
                    laudo.Dimensoes_str = cCursor.getString(cCursor.getColumnIndex("Dimensoes_str"));
                    laudo.Metodologiainspecao_str = cCursor.getString(cCursor.getColumnIndex("Metodologiainspecao_str"));
                    laudo.Certificadofabricante_str = cCursor.getString(cCursor.getColumnIndex("Certificadofabricante_str"));
                    laudo.Registroinspecao_str = cCursor.getString(cCursor.getColumnIndex("Registroinspecao_str"));
                    laudo.Registroreparo_str = cCursor.getString(cCursor.getColumnIndex("Registroreparo_str"));
                    laudo.Recomendacoes_str = cCursor.getString(cCursor.getColumnIndex("Recomendacoes_str"));
                    laudo.Empresa_str = cCursor.getString(cCursor.getColumnIndex("Empresa_str"));
                    laudo.Caminhoevidenciafotograficaum_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficaum_str"));
                    laudo.Caminhoevidenciafotograficadois_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficadois_str"));
                    laudo.Caminhoevidenciafotograficatres_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficatres_str"));
                    laudo.Caminhoevidenciafotograficaquatro_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficaquatro_str"));
                    laudo.Evidenciafotograficaum_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficaum_sync"));
                    laudo.Evidenciafotograficadois_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficadois_sync"));
                    laudo.Evidenciafotograficatres_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficatres_sync"));
                    laudo.Evidenciafotograficaquatro_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficaquatro_sync"));
                    laudo.Cnpj_str = cCursor.getString(cCursor.getColumnIndex("Cnpj_str"));
                    laudo.Datametodologiainspecao_str = cCursor.getString(cCursor.getColumnIndex("Datametodologiainspecao_str"));
                    laudo.LaudoComSequencia_str = cCursor.getString(cCursor.getColumnIndex("LaudoComSequencia_str"));
                    laudo.Laudo_str = cCursor.getString(cCursor.getColumnIndex("Laudo_str"));
                    laudo.Nomeresponsaveltecnico_str = cCursor.getString(cCursor.getColumnIndex("Nomeresponsaveltecnico_str"));
                    laudo.NumeroPedido_str = cCursor.getString(cCursor.getColumnIndex("NumeroPedido_str"));
                    laudo.Acessorio_str = cCursor.getString(cCursor.getColumnIndex("Acessorio_str"));
                    laudo.SYNC = cCursor.getInt(cCursor.getColumnIndex("SYNC"));
                    laudo.LAUDOID = cCursor.getInt(cCursor.getColumnIndex("LAUDOID"));
                    lstReturn.add(laudo);
                    cCursor.moveToNext();
                }
                cCursor.close();

                return lstReturn;
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    public TBLAUDO ListByID(long pLAUDOID){
        Cursor cCursor = null;
        DescricaoErro = "";
        TBLAUDO laudo = new TBLAUDO();
        try{
            String strSQL = "SELECT * FROM tblaudo WHERE LAUDOID = " + pLAUDOID;

            cCursor = datatable.PesquisaCursorSQL(strSQL);
            if(datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);
            else
            {
                cCursor.moveToFirst();
                while (cCursor.isAfterLast() == false) {
                    laudo.NumeroPedido_str = cCursor.getString(cCursor.getColumnIndex("NumeroPedido_str"));
                    laudo.Id_int = cCursor.getInt(cCursor.getColumnIndex("Id_int"));
                    laudo.Id_int_fk = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk"));
                    laudo.Id_int_fk_2 = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk_2"));
                    laudo.Fabricante_str = cCursor.getString(cCursor.getColumnIndex("Fabricante_str"));
                    laudo.Capacidade_str = cCursor.getString(cCursor.getColumnIndex("Capacidade_str"));
                    laudo.Contato_str = cCursor.getString(cCursor.getColumnIndex("Contato_str"));
                    laudo.ConclusaoTecnica_str = cCursor.getString(cCursor.getColumnIndex("ConclusaoTecnica_str"));
                    laudo.Telefone_str = cCursor.getString(cCursor.getColumnIndex("Telefone_str"));
                    laudo.Tag_str = cCursor.getString(cCursor.getColumnIndex("Tag_str"));
                    laudo.Setor_str = cCursor.getString(cCursor.getColumnIndex("Setor_str"));
                    laudo.Modelo_str = cCursor.getString(cCursor.getColumnIndex("Modelo_str"));
                    laudo.NumeroArt_str = cCursor.getString(cCursor.getColumnIndex("NumeroArt_str"));
                    laudo.DemaisInformacoesAcessorios_str = cCursor.getString(cCursor.getColumnIndex("DemaisInformacoesAcessorios_str"));
                    laudo.DemaisInformacoesEmpresa_str = cCursor.getString(cCursor.getColumnIndex("DemaisInformacoesEmpresa_str"));
                    laudo.Dimensoes_str = cCursor.getString(cCursor.getColumnIndex("Dimensoes_str"));
                    laudo.Metodologiainspecao_str = cCursor.getString(cCursor.getColumnIndex("Metodologiainspecao_str"));
                    laudo.Certificadofabricante_str = cCursor.getString(cCursor.getColumnIndex("Certificadofabricante_str"));
                    laudo.Registroinspecao_str = cCursor.getString(cCursor.getColumnIndex("Registroinspecao_str"));
                    laudo.Registroreparo_str = cCursor.getString(cCursor.getColumnIndex("Registroreparo_str"));
                    laudo.Recomendacoes_str = cCursor.getString(cCursor.getColumnIndex("Recomendacoes_str"));
                    laudo.Empresa_str = cCursor.getString(cCursor.getColumnIndex("Empresa_str"));
                    laudo.Caminhoevidenciafotograficaum_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficaum_str"));
                    laudo.Caminhoevidenciafotograficadois_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficadois_str"));
                    laudo.Caminhoevidenciafotograficatres_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficatres_str"));
                    laudo.Caminhoevidenciafotograficaquatro_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficaquatro_str"));
                    laudo.Evidenciafotograficaum_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficaum_sync"));
                    laudo.Evidenciafotograficadois_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficadois_sync"));
                    laudo.Evidenciafotograficatres_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficatres_sync"));
                    laudo.Evidenciafotograficaquatro_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficaquatro_sync"));
                    laudo.Cnpj_str = cCursor.getString(cCursor.getColumnIndex("Cnpj_str"));
                    laudo.Datametodologiainspecao_str = cCursor.getString(cCursor.getColumnIndex("Datametodologiainspecao_str"));
                    laudo.LaudoComSequencia_str = cCursor.getString(cCursor.getColumnIndex("LaudoComSequencia_str"));
                    laudo.Laudo_str = cCursor.getString(cCursor.getColumnIndex("Laudo_str"));
                    laudo.Nomeresponsaveltecnico_str = cCursor.getString(cCursor.getColumnIndex("Nomeresponsaveltecnico_str"));
                    laudo.NumeroPedido_str = cCursor.getString(cCursor.getColumnIndex("NumeroPedido_str"));
                    laudo.Acessorio_str = cCursor.getString(cCursor.getColumnIndex("Acessorio_str"));
                    laudo.SYNC = cCursor.getInt(cCursor.getColumnIndex("SYNC"));
                    laudo.LAUDOID = cCursor.getInt(cCursor.getColumnIndex("LAUDOID"));
                    cCursor.moveToNext();
                }
                cCursor.close();

                return laudo;
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    public boolean ImageExist(String pIMAGE){
        Cursor cCursor = null;
        DescricaoErro = "";
        boolean blnReturn = false;

        try{
            String strSQL = "SELECT * FROM tblaudo ";
            strSQL += " WHERE Caminhoevidenciafotograficaum_str = " + Utilitario.VlrString(pIMAGE);
            strSQL += "       OR Caminhoevidenciafotograficadois_str = " + Utilitario.VlrString(pIMAGE);
            strSQL += "       OR Caminhoevidenciafotograficatres_str = " + Utilitario.VlrString(pIMAGE);
            strSQL += "       OR Caminhoevidenciafotograficaquatro_str = " + Utilitario.VlrString(pIMAGE);

            cCursor = datatable.PesquisaCursorSQL(strSQL);
            if(datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);
            else
            {
                cCursor.moveToFirst();
                while (cCursor.isAfterLast() == false) {
                    blnReturn = true;
                    cCursor.moveToNext();
                }
                cCursor.close();
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return blnReturn;
    }

    public int UpdateStatusImage(long pLAUDOID, String pIMAGEM, int pSTATUS){
        try{
            String strSQL = "UPDATE tblaudo SET ";
            strSQL += pIMAGEM + " = " + pSTATUS;
            strSQL += " WHERE LAUDOID = " + pLAUDOID;
            datatable.ExecuteSQL(strSQL);
            if (datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);

            return pSTATUS;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return 0;
    }

    public void UpdateAdd(TBLAUDO pLAUDO){
        try{
            String strSQL = "UPDATE tblaudo SET ";
            strSQL += "SYNC = " + pLAUDO.SYNC;
            strSQL += ", Cnpj_str = " + Utilitario.VlrString(pLAUDO.Cnpj_str);
            strSQL += ", Datametodologiainspecao_str = " + Utilitario.VlrString(pLAUDO.Datametodologiainspecao_str);
            strSQL += ", LaudoComSequencia_str = " + Utilitario.VlrString(pLAUDO.LaudoComSequencia_str);
            strSQL += ", Laudo_str = " + Utilitario.VlrString(pLAUDO.Laudo_str);
            strSQL += ", Empresa_str = " + Utilitario.VlrString(pLAUDO.Empresa_str);
            strSQL += ", Acessorio_str = " + Utilitario.VlrString(pLAUDO.Acessorio_str);
            strSQL += ", Nomeresponsaveltecnico_str = " + Utilitario.VlrString(pLAUDO.Nomeresponsaveltecnico_str);
            strSQL += ", NumeroPedido_str = " + Utilitario.VlrString(pLAUDO.NumeroPedido_str);
            strSQL += " WHERE LAUDOID = " + pLAUDO.LAUDOID;
            datatable.ExecuteSQL(strSQL);
            if (datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void Delete(long pLAUDOID){
        try{
            String strSQL = "DELETE FROM tblaudo ";
            strSQL += " WHERE LAUDOID = " + pLAUDOID;
            datatable.ExecuteSQL(strSQL);
            if (datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void Update(TBLAUDO pLAUDO){
        DescricaoErro = "";
        String strSQL = "";

        try{
            strSQL = "UPDATE tblaudo SET ";
            strSQL += "Fabricante_str = " + Utilitario.VlrString(pLAUDO.Fabricante_str) + ", ";
            strSQL += "Capacidade_str = " + Utilitario.VlrString(pLAUDO.Capacidade_str) + ", ";
            strSQL += "Contato_str = " + Utilitario.VlrString(pLAUDO.Contato_str) + ", ";
            strSQL += "Telefone_str = " + Utilitario.VlrString(pLAUDO.Telefone_str) + ", ";
            strSQL += "Tag_str = " + Utilitario.VlrString(pLAUDO.Tag_str) + ", ";
            strSQL += "Setor_str = " + Utilitario.VlrString(pLAUDO.Setor_str) + ", ";
            strSQL += "Modelo_str = " + Utilitario.VlrString(pLAUDO.Modelo_str) + ", ";
            strSQL += "Dimensoes_str = " + Utilitario.VlrString(pLAUDO.Dimensoes_str) + ", ";
            strSQL += "Metodologiainspecao_str = " + Utilitario.VlrString(pLAUDO.Metodologiainspecao_str) + ", ";
            strSQL += "Certificadofabricante_str = " + Utilitario.VlrString(pLAUDO.Certificadofabricante_str) + ", ";
            strSQL += "Registroinspecao_str = " + Utilitario.VlrString(pLAUDO.Registroinspecao_str) + ", ";
            strSQL += "Acessorio_str = " + Utilitario.VlrString(pLAUDO.Acessorio_str) + ", ";
            strSQL += "Registroreparo_str = " + Utilitario.VlrString(pLAUDO.Registroreparo_str) + ", ";
            strSQL += "Recomendacoes_str = " + Utilitario.VlrString(pLAUDO.Recomendacoes_str) + ", ";
            strSQL += "DemaisInformacoesEmpresa_str = " + Utilitario.VlrString(pLAUDO.DemaisInformacoesEmpresa_str) + ", ";
            strSQL += "DemaisInformacoesAcessorios_str = " + Utilitario.VlrString(pLAUDO.DemaisInformacoesAcessorios_str) + ", ";
            strSQL += "ConclusaoTecnica_str = " + Utilitario.VlrString(pLAUDO.ConclusaoTecnica_str) + ", ";
            strSQL += "NumeroArt_str = " + Utilitario.VlrString(pLAUDO.NumeroArt_str) + " ";
            strSQL += " WHERE LAUDOID = " + pLAUDO.LAUDOID;
            datatable.ExecuteSQL(strSQL);
            if (datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);

        }catch(Exception ex){
            ex.printStackTrace();
            DescricaoErro = ex.getMessage();
        }
    }

    public List<TBLAUDO> ListNoSync(){
        Cursor cCursor = null;
        DescricaoErro = "";
        List<TBLAUDO> lstReturn = new ArrayList<>();
        try{
            String strSQL = "SELECT * FROM tblaudo WHERE SYNC = 0";

            cCursor = datatable.PesquisaCursorSQL(strSQL);
            if(datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);
            else
            {
                cCursor.moveToFirst();
                while (cCursor.isAfterLast() == false) {
                    TBLAUDO laudo = new TBLAUDO();
                    laudo.NumeroPedido_str = cCursor.getString(cCursor.getColumnIndex("NumeroPedido_str"));
                    laudo.Id_int = cCursor.getInt(cCursor.getColumnIndex("Id_int"));
                    laudo.Id_int_fk = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk"));
                    laudo.Id_int_fk_2 = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk_2"));
                    laudo.Fabricante_str = cCursor.getString(cCursor.getColumnIndex("Fabricante_str"));
                    laudo.Capacidade_str = cCursor.getString(cCursor.getColumnIndex("Capacidade_str"));
                    laudo.Contato_str = cCursor.getString(cCursor.getColumnIndex("Contato_str"));
                    laudo.ConclusaoTecnica_str = cCursor.getString(cCursor.getColumnIndex("ConclusaoTecnica_str"));
                    laudo.Telefone_str = cCursor.getString(cCursor.getColumnIndex("Telefone_str"));
                    laudo.Tag_str = cCursor.getString(cCursor.getColumnIndex("Tag_str"));
                    laudo.Setor_str = cCursor.getString(cCursor.getColumnIndex("Setor_str"));
                    laudo.Modelo_str = cCursor.getString(cCursor.getColumnIndex("Modelo_str"));
                    laudo.NumeroArt_str = cCursor.getString(cCursor.getColumnIndex("NumeroArt_str"));
                    laudo.DemaisInformacoesAcessorios_str = cCursor.getString(cCursor.getColumnIndex("DemaisInformacoesAcessorios_str"));
                    laudo.DemaisInformacoesEmpresa_str = cCursor.getString(cCursor.getColumnIndex("DemaisInformacoesEmpresa_str"));
                    laudo.Dimensoes_str = cCursor.getString(cCursor.getColumnIndex("Dimensoes_str"));
                    laudo.Metodologiainspecao_str = cCursor.getString(cCursor.getColumnIndex("Metodologiainspecao_str"));
                    laudo.Certificadofabricante_str = cCursor.getString(cCursor.getColumnIndex("Certificadofabricante_str"));
                    laudo.Registroinspecao_str = cCursor.getString(cCursor.getColumnIndex("Registroinspecao_str"));
                    laudo.Registroreparo_str = cCursor.getString(cCursor.getColumnIndex("Registroreparo_str"));
                    laudo.Recomendacoes_str = cCursor.getString(cCursor.getColumnIndex("Recomendacoes_str"));
                    laudo.Empresa_str = cCursor.getString(cCursor.getColumnIndex("Empresa_str"));
                    laudo.Caminhoevidenciafotograficaum_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficaum_str"));
                    laudo.Caminhoevidenciafotograficadois_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficadois_str"));
                    laudo.Caminhoevidenciafotograficatres_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficatres_str"));
                    laudo.Caminhoevidenciafotograficaquatro_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficaquatro_str"));
                    laudo.Evidenciafotograficaum_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficaum_sync"));
                    laudo.Evidenciafotograficadois_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficadois_sync"));
                    laudo.Evidenciafotograficatres_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficatres_sync"));
                    laudo.Evidenciafotograficaquatro_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficaquatro_sync"));
                    laudo.Cnpj_str = cCursor.getString(cCursor.getColumnIndex("Cnpj_str"));
                    laudo.Datametodologiainspecao_str = cCursor.getString(cCursor.getColumnIndex("Datametodologiainspecao_str"));
                    laudo.LaudoComSequencia_str = cCursor.getString(cCursor.getColumnIndex("LaudoComSequencia_str"));
                    laudo.Laudo_str = cCursor.getString(cCursor.getColumnIndex("Laudo_str"));
                    laudo.Nomeresponsaveltecnico_str = cCursor.getString(cCursor.getColumnIndex("Nomeresponsaveltecnico_str"));
                    laudo.NumeroPedido_str = cCursor.getString(cCursor.getColumnIndex("NumeroPedido_str"));
                    laudo.Acessorio_str = cCursor.getString(cCursor.getColumnIndex("Acessorio_str"));
                    laudo.SYNC = cCursor.getInt(cCursor.getColumnIndex("SYNC"));
                    laudo.LAUDOID = cCursor.getInt(cCursor.getColumnIndex("LAUDOID"));

                    lstReturn.add(laudo);

                    cCursor.moveToNext();
                }
                cCursor.close();

                return lstReturn;
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }

    public List<TBLAUDO> Search(TBLAUDO pTBLAUDO){
        Cursor cCursor = null;
        DescricaoErro = "";
        List<TBLAUDO> lstReturn = new ArrayList<>();
        String strWHERE = "";

        try{
            String strSQL = "SELECT * FROM tblaudo ";
            strSQL += " WHERE Id_int = " + pTBLAUDO.Id_int;
            if(pTBLAUDO.Laudo_str.length() > 0)
                strWHERE += " AND Laudo_str LIKE '%" + pTBLAUDO.Laudo_str + "%'";
            if(pTBLAUDO.NumeroArt_str.length() > 0)
                strWHERE += " AND NumeroArt_str LIKE '%" + pTBLAUDO.NumeroArt_str + "%'";
            if(pTBLAUDO.Acessorio_str.length() > 0)
                strWHERE += " AND Acessorio_str LIKE '%" + pTBLAUDO.Acessorio_str + "%'";
            if(pTBLAUDO.Tag_str.length() > 0)
                strWHERE += " AND Tag_str LIKE '%" + pTBLAUDO.Tag_str + "%'";
            if(pTBLAUDO.Fabricante_str.length() > 0)
                strWHERE += " AND Fabricante_str LIKE '%" + pTBLAUDO.Fabricante_str + "%'";
            if(pTBLAUDO.Modelo_str.length() > 0)
                strWHERE += " AND Modelo_str LIKE '%" + pTBLAUDO.Modelo_str + "%'";
            if(pTBLAUDO.Empresa_str.length() > 0)
                strWHERE += " AND Empresa_str LIKE '%" + pTBLAUDO.Empresa_str + "%'";
            if(pTBLAUDO.Cnpj_str.length() > 0)
                strWHERE += " AND Cnpj_str LIKE '%" + pTBLAUDO.Cnpj_str + "%'";
            if(pTBLAUDO.Datametodologiainspecao_str.length() > 0)
                strWHERE += " AND Datametodologiainspecao_str LIKE '%" + pTBLAUDO.Datametodologiainspecao_str + "%'";
            if(pTBLAUDO.ConclusaoTecnica_str.length() > 0)
                strWHERE +=  " AND ConclusaoTecnica_str LIKE '%" + pTBLAUDO.ConclusaoTecnica_str + "%'";
            strSQL += strWHERE;
            cCursor = datatable.PesquisaCursorSQL(strSQL);
            if(datatable.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + datatable.DescricaoErro);
            else
            {
                cCursor.moveToFirst();
                while (cCursor.isAfterLast() == false) {
                    TBLAUDO laudo = new TBLAUDO();
                    laudo.NumeroPedido_str = cCursor.getString(cCursor.getColumnIndex("NumeroPedido_str"));
                    laudo.Id_int = cCursor.getInt(cCursor.getColumnIndex("Id_int"));
                    laudo.Id_int_fk = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk"));
                    laudo.Id_int_fk_2 = cCursor.getInt(cCursor.getColumnIndex("Id_int_fk_2"));
                    laudo.Fabricante_str = cCursor.getString(cCursor.getColumnIndex("Fabricante_str"));
                    laudo.Capacidade_str = cCursor.getString(cCursor.getColumnIndex("Capacidade_str"));
                    laudo.Contato_str = cCursor.getString(cCursor.getColumnIndex("Contato_str"));
                    laudo.ConclusaoTecnica_str = cCursor.getString(cCursor.getColumnIndex("ConclusaoTecnica_str"));
                    laudo.Telefone_str = cCursor.getString(cCursor.getColumnIndex("Telefone_str"));
                    laudo.Tag_str = cCursor.getString(cCursor.getColumnIndex("Tag_str"));
                    laudo.Setor_str = cCursor.getString(cCursor.getColumnIndex("Setor_str"));
                    laudo.Modelo_str = cCursor.getString(cCursor.getColumnIndex("Modelo_str"));
                    laudo.NumeroArt_str = cCursor.getString(cCursor.getColumnIndex("NumeroArt_str"));
                    laudo.DemaisInformacoesAcessorios_str = cCursor.getString(cCursor.getColumnIndex("DemaisInformacoesAcessorios_str"));
                    laudo.DemaisInformacoesEmpresa_str = cCursor.getString(cCursor.getColumnIndex("DemaisInformacoesEmpresa_str"));
                    laudo.Dimensoes_str = cCursor.getString(cCursor.getColumnIndex("Dimensoes_str"));
                    laudo.Metodologiainspecao_str = cCursor.getString(cCursor.getColumnIndex("Metodologiainspecao_str"));
                    laudo.Certificadofabricante_str = cCursor.getString(cCursor.getColumnIndex("Certificadofabricante_str"));
                    laudo.Registroinspecao_str = cCursor.getString(cCursor.getColumnIndex("Registroinspecao_str"));
                    laudo.Registroreparo_str = cCursor.getString(cCursor.getColumnIndex("Registroreparo_str"));
                    laudo.Recomendacoes_str = cCursor.getString(cCursor.getColumnIndex("Recomendacoes_str"));
                    laudo.Empresa_str = cCursor.getString(cCursor.getColumnIndex("Empresa_str"));
                    laudo.Caminhoevidenciafotograficaum_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficaum_str"));
                    laudo.Caminhoevidenciafotograficadois_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficadois_str"));
                    laudo.Caminhoevidenciafotograficatres_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficatres_str"));
                    laudo.Caminhoevidenciafotograficaquatro_str = cCursor.getString(cCursor.getColumnIndex("Caminhoevidenciafotograficaquatro_str"));
                    laudo.Evidenciafotograficaum_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficaum_sync"));
                    laudo.Evidenciafotograficadois_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficadois_sync"));
                    laudo.Evidenciafotograficatres_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficatres_sync"));
                    laudo.Evidenciafotograficaquatro_sync = cCursor.getInt(cCursor.getColumnIndex("Evidenciafotograficaquatro_sync"));
                    laudo.Cnpj_str = cCursor.getString(cCursor.getColumnIndex("Cnpj_str"));
                    laudo.Datametodologiainspecao_str = cCursor.getString(cCursor.getColumnIndex("Datametodologiainspecao_str"));
                    laudo.LaudoComSequencia_str = cCursor.getString(cCursor.getColumnIndex("LaudoComSequencia_str"));
                    laudo.Laudo_str = cCursor.getString(cCursor.getColumnIndex("Laudo_str"));
                    laudo.Nomeresponsaveltecnico_str = cCursor.getString(cCursor.getColumnIndex("Nomeresponsaveltecnico_str"));
                    laudo.NumeroPedido_str = cCursor.getString(cCursor.getColumnIndex("NumeroPedido_str"));
                    laudo.Acessorio_str = cCursor.getString(cCursor.getColumnIndex("Acessorio_str"));
                    laudo.SYNC = cCursor.getInt(cCursor.getColumnIndex("SYNC"));
                    laudo.LAUDOID = cCursor.getInt(cCursor.getColumnIndex("LAUDOID"));
                    lstReturn.add(laudo);
                    cCursor.moveToNext();
                }
                cCursor.close();

                return lstReturn;
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return null;
    }
}
