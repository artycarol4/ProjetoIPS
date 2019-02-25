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
    public partial class Login : System.Web.UI.Page
    {
       
        protected void Page_Load(object sender, EventArgs e)
        {
            if (!IsPostBack)
            {


            }

        }

        protected void btnEntrar_Click(object sender, EventArgs e)
        {          

            try
            {
                lblStatus.Text = "";
                lblStatus.Visible = false;
                Response.Redirect("Index.aspx");
            }

            catch (Exception erro)
            {
                lblStatus.Visible = true;
                lblStatus.Text = "Erro ao tentar fazer login!" + erro;

            }
        }
    }
}