using IpsSistemas.DAO;
using IpsSistemas.Model;
using Microsoft.Reporting.WebForms;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;


using MySql.Data.MySqlClient;

using System.Configuration;
using System.Drawing;
using System.IO;

using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Runtime.Remoting.Contexts;
using System.Threading.Tasks;
using System.Web.Script.Serialization;
using System.Threading;

namespace IpsSistemas.Controllers
{
    public class OrdemServicoController : ApiController
    {
        #region Metodos

        [HttpPost]
        [Route("api/ordemservico/addos")]
        public OrdemServicoApi InsertApp(string ordemservico, string username)
        {

            OrdemServicoApi objOrdemServicoClasse = new OrdemServicoApi();
            PedidoClasse objPedidoClasse = new PedidoClasse();
           
            var response = new HttpResponseMessage();

            try
            {
                OrdemServicoClasse value = new JavaScriptSerializer().Deserialize<OrdemServicoClasse>(ordemservico);

                Thread.Sleep(1500);

                objOrdemServicoClasse.MSGSTATUS = MensagemData.KDStatus.kdNormal;
                return objOrdemServicoClasse;

            }// FIM try

            catch (Exception erro)
            {
                return new OrdemServicoApi { MSGSTATUS = MensagemData.KDStatus.kdErro, DESCRICAO = erro.Message };
            }

            return new OrdemServicoApi { MSGSTATUS = MensagemData.KDStatus.kdErro };
        }

        [HttpPost]
        [Route("api/ordemservico/uploadimage")]
        [AllowAnonymous]
        public MensagemData UploadImg(string image, string imagename, int status)
        {
            
           // string log = "";
            string strPath = System.Web.HttpContext.Current.Server.MapPath("~") + "Views\\EvidenciasFotograficas\\";
            string strPathTemp = System.Web.HttpContext.Current.Server.MapPath("~") + "Views\\TEMP\\";

            // string strPath = System.Web.HttpContext.Current.Server.MapPath("~/Views/EvidenciasFotograficas/");
            // string strPathTemp = System.Web.HttpContext.Current.Server.MapPath("~/Views/TEMP/");

            string strFileTxt = strPathTemp + imagename + ".txt";
            try
            {
                if (status == 1)
                {
                    File.Delete(strFileTxt);
                  //  log = "status " + Convert.ToString(status) + ". arquivo txt foi deletado.";
                }

                if (status != 3)
                {
                    using (StreamWriter sw = new StreamWriter(strFileTxt, true))
                    {
                        sw.Write(image);
                        sw.Dispose();
                        sw.Close();
                    }
                 //  log += "status " + Convert.ToString(status) + ". arquivo txt foi escrito";
                }
                else if (status == 3)
                {
                    File.Delete(strPath + imagename);
                    string strImage = System.IO.File.ReadAllText(strFileTxt);
                    byte[] NewBytes = Convert.FromBase64String(strImage);
                    MemoryStream ms1 = new MemoryStream(NewBytes);
                    System.Drawing.Image NewImage = System.Drawing.Image.FromStream(ms1);
                    NewImage.Save(strPath + imagename);
                    Thread.Sleep(1000);
                    File.Delete(strFileTxt);
                  //  log = "status " + Convert.ToString(status) + ". imagem foi convertida e salva";
                }
            
                return new MensagemData { MSGSTATUS = MensagemData.KDStatus.kdNormal };
            }
            catch (Exception ex)
            {
                return new MensagemData { MSGSTATUS = MensagemData.KDStatus.kdErro, DESCRICAO = ex.Message };
            }

        }



        #endregion



    }
}
