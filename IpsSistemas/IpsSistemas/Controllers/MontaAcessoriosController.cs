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
    public class MontaAcessoriosController : ApiController
    {
        // GET: api/MontaAcessorios
        public IEnumerable<MontaAcessoriosApi> Get(int id)
        {
            MontaAcessoriosDAO objMontaAcessoriosDAO = new MontaAcessoriosDAO();
            List<MontaAcessoriosApi> lstAcessorio = objMontaAcessoriosDAO.ListarApi(id);
            if (lstAcessorio == null)
            {
                lstAcessorio = new List<MontaAcessoriosApi>();
                lstAcessorio.Add(new MontaAcessoriosApi { MSGSTATUS = MensagemData.KDStatus.kdSemDado });
            }
            else if (lstAcessorio.Count == 0)
            {
                lstAcessorio.Add(new MontaAcessoriosApi { MSGSTATUS = MensagemData.KDStatus.kdSemDado });
            }
            return lstAcessorio;
        }

      
    }
}
