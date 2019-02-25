using IpsSistemas.Model;
using IpsSistemas.Views;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;

namespace IpsSistemas.DAO
{
    public class PesquisaDAO
    {
        Conecta c = new Conecta();

        //Pesquisar String
        public DataTable Pesquisar(string query)
        {
            DataTable dt = new DataTable();
            c.Conectar();
            MySqlDataAdapter da = new MySqlDataAdapter(query, c.cn);
            da.Fill(dt);
            c.Desconectar();
            return dt;

        }


    }
}