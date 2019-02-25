using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace IpsSistemas.Model
{
    public class PedidoClasse : CadastroClasse
    {
        public int IdInspetor_int_fk { get; set; }

        public int IdResponsavelTecnico_int_fk { get; set; }
        public int IdCliente_int_fk { get; set; }

        public string Cliente_str { get; set; }

        public string Responsaveltecnico_str { get; set; }

        public string Inspetor_str { get; set; }

        public string NumeroPedido_str { get; set; }

        public string NumeroArt_str { get; set; }
        public bool Finalizado_bool { get; set; }
    }
}