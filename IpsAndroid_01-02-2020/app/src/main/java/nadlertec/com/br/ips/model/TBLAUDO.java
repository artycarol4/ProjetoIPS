package nadlertec.com.br.ips.model;

import com.google.gson.Gson;

import java.io.Serializable;

public class TBLAUDO implements Serializable {
    public long LAUDOID = 0;
    public int Id_int = 0;
    public int Id_int_fk = 0;
    public int Id_int_fk_2 = 0;
    public String NomeUsuario_str= "";
    public String Acessorio_str= "";
    public String Numeropedidocliente_str= "";
    public String NumeroArt_str = "";
    public String Fabricante_str= "";
    public String Capacidade_str= "";
    public String Empresa_str= "";
    public String Cnpj_str= "";
    public String Contato_str= "";
    public String Telefone_str= "";
    public String Tag_str= "";
    public String Proprietario_str= "";
    public String Setor_str= "";
    public String Modelo_str= "";
    public String NumeroPedido_str= "";
    public String Laudo_str= "";
    public String LaudoComSequencia_str= "";
    public String Dimensoes_str= "";
    public String DemaisInformacoesAcessorios_str= "";
    public String DemaisInformacoesEmpresa_str= "";
    public String Metodologiainspecao_str= "";
    public String Certificadofabricante_str= "";
    public String Registroinspecao_str= "";
    public String Registroreparo_str= "";
    public String ConclusaoTecnica_str= "";
    public String Caminhoevidenciafotograficaum_str= "";
    public int Evidenciafotograficaum_sync = 0;
    public String Caminhoevidenciafotograficadois_str= "";
    public int Evidenciafotograficadois_sync = 0;
    public String Caminhoevidenciafotograficatres_str= "";
    public int Evidenciafotograficatres_sync = 0;
    public String Caminhoevidenciafotograficaquatro_str= "";
    public int Evidenciafotograficaquatro_sync = 0;
    public String Recomendacoes_str= "";
    public String Nomeresponsaveltecnico_str= "";
    public String Datametodologiainspecao_str = "";
    public int SYNC = 0;
    public int DETALHE = 0;
    public int MSGSTATUS = 0;
    public String DESCRICAO = "";
    public String JSON() {
        return new Gson().toJson(this, TBLAUDO.class);
    }
}
