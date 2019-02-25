using IpsSistemas.DAO;
using IpsSistemas.Model;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace IpsSistemas.Views
{
    public partial class Principal : System.Web.UI.MasterPage
    {
      
        protected void Page_Load(object sender, EventArgs e)
        {
            
         
        }

        protected void AnchorButton_ServerClick(object sender, EventArgs e)
        {

            //Grava no session sessionDados
            Session["sessionLogin"] = "NaoLiberado";

            //Desloga do sistema
            FormsAuthentication.SignOut();
            Response.Redirect("Login.aspx");
            //Volta para a página de Login (página descrita no web.config)
            FormsAuthentication.RedirectToLoginPage();

        }






    }
}