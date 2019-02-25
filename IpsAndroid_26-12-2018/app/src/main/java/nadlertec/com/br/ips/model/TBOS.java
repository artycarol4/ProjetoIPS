package nadlertec.com.br.ips.model;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;

public class TBOS implements Serializable {
    public int Idinspetor_int_fk =  0;
    public int Idresponsaveltecnico_int_fk =  0;
    public int Idcliente_int_fk =  0;
    public String Cliente_str =  "";
    public String Responsaveltecnico_str =  "";
    public String Inspetor_str =  "" ;
    public String NumeroPedido_str =  "";
    public String NumeroArt_str =  "";
    public boolean Finalizado_bool;
    public int Id_int =  0;
    public int Id_int_fk =  0;
    public int Id_int_fk_2 =  0;
    public int Idnumeropedido_int =  0;
    public int Quantidade_int =  0;
    public String Nomeoucliente_str =  "";
    public String NomeUsuario_str =  "";
    public String Descricao_str =  "";
    public String Caminho_str =  "";
    public String Crea_str =  "";
    public String Acessorio_str =  "";
    public String Cpf_str =  "";
    public String Rg_str =  "";
    public String Cadastradopor_str =  "";
    public String Alteradopor_str =  "";
    public boolean Exibir_bool;
    public String DESCRICAO = "";
    public int MSGSTATUS = 0;
    public List<TBACESSORIO> AcessorioList;
    public TBLAUDO laudo;
    public String JSON(){
        return new Gson().toJson(this, TBOS.class);
    }
}
