using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace IpsSistemas.Model
{
    public class CadastroClasse
    {
        public int Id_int { get; set; }
        public int Id_int_fk { get; set; }
        public int Id_int_fk_2 { get; set; }


        public int IdNumeroPedido_int { get; set; }
        public int Quantidade_int { get; set; }
        public string NomeOuCliente_str { get; set; }
        public string NomeUsuario_str { get; set; }

        public string Inspetor_str { get; set; }
        public string Descricao_str { get; set; }

        public string Caminho_str { get; set; }
        public string Crea_str { get; set; }
        public string ResponsavelTecnico_str { get; set; }

        public string Acessorio_str { get; set; }
        public string Cpf_str { get; set; }
        public string Rg_str { get; set; }
        public string DataInicial_str { get; set; }
        public string DataFinal_str { get; set; }


        public string Cadastradopor_str { get; set; }
        public DateTime Datacadastro_dt { get; set; }
        public string Alteradopor_str { get; set; }
        public DateTime Dataalteracao_dt { get; set; }
        public bool Exibir_bool { get; set; }
    }
}