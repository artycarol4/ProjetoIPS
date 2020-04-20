package nadlertec.com.br.ips.model;

import android.content.Context;
import android.os.Environment;

import com.google.gson.Gson;
import com.imagepicker.AppUtils;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import nadlertec.com.br.ips.R;

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

    public void saveTxt(Context context, int laudoCount) {
        File file = new File(Environment.getExternalStorageDirectory(), context.getString(R.string.dirTMP) + "/" + NumeroPedido_str + "/Laudo_" + laudoCount + "/Laudo_" + laudoCount + ".txt");
        ArrayList<String> saveText = new ArrayList<>();
        saveText.add(String.format("Cliente: %s", Cliente_str));
        saveText.add(String.format("Responsavel técnico: %s", Responsaveltecnico_str));
        saveText.add(String.format("Inspetor: %s", Inspetor_str));
        saveText.add(String.format("O.S.: %s", NumeroPedido_str));
        saveText.add(String.format("Número Art: %s", NumeroArt_str));
        saveText.add(String.format("Quantidade: %s", Quantidade_int));

        saveText.add("=============================================");
        saveText.add("Laudo:");
        saveText.add("");

        saveText.add(String.format("Acessório: %s", laudo.Acessorio_str));
        saveText.add(String.format("Número Art: %s", laudo.NumeroArt_str));
        saveText.add(String.format("Número pedido do cliente: %s", laudo.Numeropedidocliente_str));
        saveText.add(String.format("Fabricante: %s", laudo.Fabricante_str));
        saveText.add(String.format("Capacidade: %s", laudo.Capacidade_str));
        saveText.add(String.format("Contato: %s", laudo.Contato_str));
        saveText.add(String.format("Telefone: %s", laudo.Telefone_str));
        saveText.add(String.format("Tag: %s", laudo.Tag_str));
        saveText.add(String.format("Setor: %s", laudo.Setor_str));
        saveText.add(String.format("Modelo: %s", laudo.Modelo_str));
        saveText.add(String.format("Número do pedido: %s", laudo.NumeroPedido_str));
        saveText.add(String.format("Dimensões: %s", laudo.Dimensoes_str));
        saveText.add(String.format("Demais informações acessórios: %s", laudo.DemaisInformacoesAcessorios_str));
        saveText.add(String.format("Demais informações empresa: %s", laudo.DemaisInformacoesEmpresa_str));
        saveText.add(String.format("Metodologia inspeção: %s", laudo.Metodologiainspecao_str));
        saveText.add(String.format("Certificado do fabricante: %s", laudo.Certificadofabricante_str));
        saveText.add(String.format("Registro inspeção: %s", laudo.Registroinspecao_str));
        saveText.add(String.format("Registro  reparo: %s", laudo.Registroreparo_str));
        saveText.add(String.format("Conclusão técnica: %s", laudo.ConclusaoTecnica_str));
        saveText.add(String.format("Recomendações: %s", laudo.Recomendacoes_str));
        saveText.add(String.format("Nome do responsável técnico: %s", laudo.Nomeresponsaveltecnico_str));
        saveText.add(String.format("Data metodologia inspeção: %s", laudo.Datametodologiainspecao_str));

        AppUtils.Save(file, saveText.toArray(new String[saveText.size()]));
    }
}
