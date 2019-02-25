using IpsSistemas.DAO;
using IpsSistemas.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace IpsSistemas.Controllers
{
    public class PedidosController : ApiController
    {
        // GET api/pedidos
        public IEnumerable<PedidoApi> Get()
        {
            PedidosDAO objPedido = new PedidosDAO();
            List<PedidoApi> lstReturn = objPedido.Listar();
            if (lstReturn == null)
            {
                lstReturn = new List<PedidoApi>();
                lstReturn.Add(new PedidoApi { MSGSTATUS = MensagemData.KDStatus.kdSemDado });
            }
            else if (lstReturn.Count == 0)
            {
                lstReturn = new List<PedidoApi>();
                lstReturn.Add(new PedidoApi { MSGSTATUS = MensagemData.KDStatus.kdSemDado });
            }

            return lstReturn;
        }

        // GET api/pedidos/5
        public PedidoClasse Get(int id)
        {
            PedidosDAO objPedido = new PedidosDAO();
            PedidoApi pedido = objPedido.Listar().Where(x => x.Id_int == id).FirstOrDefault();
            if (pedido == null)
                pedido = new PedidoApi { MSGSTATUS = MensagemData.KDStatus.kdSemDado };
            else
                pedido.MSGSTATUS = MensagemData.KDStatus.kdNormal;

            return pedido;
        }

       
    }
}
