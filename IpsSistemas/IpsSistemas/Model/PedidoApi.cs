using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace IpsSistemas.Model
{
    public class PedidoApi : PedidoClasse
    {
        public MensagemData.KDStatus MSGSTATUS { get; set; }
        public string DESCRICAO { get; set; }
        public List<MontaAcessoriosApi> AcessorioList { get; set; }
    }
}