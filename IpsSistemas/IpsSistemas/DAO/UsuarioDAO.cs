using IpsSistemas.Model;
using IpsSistemas.Views;
using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.Security;
namespace IpsSistemas.DAO
{
    public class UsuarioDAO 
    {
        Conecta c = new Conecta();

        bool retorno;

       
        public string CriptografarSenha(string senha)
        {

            try
            {


                MySqlParameter sPar = new MySqlParameter(senha, MySql.Data.MySqlClient.MySqlDbType.VarChar, 10);
                sPar.Value = FormsAuthentication.HashPasswordForStoringInConfigFile(senha, "sha1"); //Usado para criptografar
                senha = Convert.ToString(sPar.Value);


            }
            catch (Exception error)
            {
                senha = "";
            }
            finally
            {

            }
            return senha;


        }


       
        public List<UsuarioClasse> Listar()
        {

            List<UsuarioClasse> objUsuarioClasse = new List<UsuarioClasse>();
            string query = "";


            c.Conectar();

           
            query += " SELECT * FROM tb_usuarios ORDER BY idusuarios_int";


            MySqlCommand cd = new MySqlCommand();
            cd.Connection = c.cn;
            cd.CommandText = query;
            MySqlDataReader dr = cd.ExecuteReader();

            try
            {
                while (dr.Read())
                {
                    UsuarioClasse obj = new UsuarioClasse();

                    obj.Id_int = Convert.ToInt32(dr["idusuarios_int"].ToString());
                    obj.NomeOuCliente_str = dr["nome_str"].ToString();
                    obj.Senha_str = dr["senha_str"].ToString();

                    objUsuarioClasse.Add(obj);

                }

            }
            catch (Exception ex)
            {
                throw ex;
            }
            finally
            {
                if (c.cn.State == ConnectionState.Open)
                    c.Desconectar();
            }

            return objUsuarioClasse;
        }//fim


        
    }
}