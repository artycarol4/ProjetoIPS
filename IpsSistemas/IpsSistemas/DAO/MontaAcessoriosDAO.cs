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
    public class MontaAcessoriosDAO 
    {
        Conecta c = new Conecta();

        //Web Api
        //Listar Dados 
        public List<MontaAcessoriosApi> ListarApi(int id)
        {

            List<MontaAcessoriosApi> objMontaAcessoriosClasse = new List<MontaAcessoriosApi>();
            string query = "";


            c.Conectar();


            query += "SELECT ma.idmontaacessorios_int, ma.idacessorios_int_fk, ma.idnumeropedido_int_fk, np.numeropedido_str, a.acessorio_str FROM tb_numeropedido AS np, tb_acessorios AS a , tb_montaacessorios AS ma WHERE ma.idacessorios_int_fk = a.idacessorios_int AND ma.idnumeropedido_int_fk = np.idnumeropedido_int AND ma.exibir_bool = 1 AND np.idnumeropedido_int = " + id + " ORDER BY a.acessorio_str ASC";


            MySqlCommand cd = new MySqlCommand();
            cd.Connection = c.cn;
            cd.CommandText = query;
            MySqlDataReader dr = cd.ExecuteReader();

            try
            {


                while (dr.Read())
                {

                    MontaAcessoriosApi obj = new MontaAcessoriosApi();

                    obj.Idmontaacessorios_int = Convert.ToInt32(dr["idmontaacessorios_int"].ToString());
                    obj.Acessorio_str = dr["acessorio_str"].ToString();
                   
                    objMontaAcessoriosClasse.Add(obj);

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

            return objMontaAcessoriosClasse;
        }//fim



    }
}