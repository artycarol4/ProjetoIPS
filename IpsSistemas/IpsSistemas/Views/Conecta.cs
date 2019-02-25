using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Configuration;

namespace IpsSistemas.Views
{
    public class Conecta
    {       
        public MySqlConnection cn = new MySqlConnection();
       

        //Abrir conexão
        public void Conectar()
        {
            try
            {
                cn.ConnectionString = System.Configuration.ConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString;
                cn.Open();
               
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }


        //Abrir conexão para fazer Login
        public void Login()
        {
            try
            {
                cn.ConnectionString = WebConfigurationManager.ConnectionStrings["ConnectionString"].ConnectionString;
                cn.Open();
               
            }
            catch (Exception ex)
            {
                throw ex;
            }
        }

        //Fechar conexão
        public void Desconectar()
        {
            cn.Close();
           
        }

    }
}