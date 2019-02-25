using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace IpsSistemas.Model
{
    public class OrdemServicoClasse 
    {

        public int IdOrdemServicoase_int { get; set; }
        public int IdNumeroPedido_int_fk { get; set; }
        public string Numeropedidocliente_str { get; set; }
        public string Fabricante_str { get; set; }
        public string Capacidade_str { get; set; }
        public string Empresa_str { get; set; }
        public string Cnpj_str { get; set; }
        public string Contato_str { get; set; }
        public string Telefone_str { get; set; }
        public string Tag_str { get; set; }
        public string Proprietario_str { get; set; }
        public string Setor_str { get; set; }
        public string Modelo_str { get; set; }
        public string NumeroPedido_str { get; set; }
        public string Laudo_str { get; set; }

        public string LaudoComSequencia_str { get; set; }

        public string Dimensoes_str { get; set; }

        public string DemaisInformacoesAcessorios_str { get; set; }

        public string DemaisInformacoesEmpresa_str { get; set; }

        public string Metodologiainspecao_str { get; set; }
        public DateTime Datametodologiainspecao_dt { get; set; }

        public string Certificadofabricante_str { get; set; }
        public string Registroinspecao_str { get; set; }
        public string Registroreparo_str { get; set; }

        public string ConclusaoTecnica_str { get; set; }




        //  public string Caminhologotipocliente_str { get; set; }
        public string Caminhoevidenciafotograficaum_str { get; set; }
        public string Caminhoevidenciafotograficadois_str { get; set; }
        public string Caminhoevidenciafotograficatres_str { get; set; }
        public string Caminhoevidenciafotograficaquatro_str { get; set; }




        public bool Registrosaquisicao_bool { get; set; }
        public bool Registrosinspecao_bool { get; set; }


        public string Recomendacoes_str { get; set; }



        public string Caminhoassinaturainspetor_str { get; set; }
        public string Nomeresponsaveltecnico_str { get; set; }
        public string Caminhoassinaturaresponsaveltecnico_str { get; set; }

        public string Numeroart_str { get; set; }
        public bool Finalizado_bool { get; set; }

        public bool Pdfgerado_bool { get; set; }

    }
}