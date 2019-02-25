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
    public class PedidosDAO 
    {
        Conecta c = new Conecta();
       
        //Listar Dados 
        public List<PedidoApi> Listar()
        {
            MontaAcessoriosDAO acessoriodao = new MontaAcessoriosDAO();
            List<PedidoApi> objPedidoClasse = new List<PedidoApi>();
            string query = "";


            c.Conectar();


            query += "SELECT idnumeropedido_int,idcliente_int_fk,idinspetor_int_fk,idresponsaveltecnico_int_fk,cliente_str,inspetor_str,responsaveltecnico_str,numeropedido_str,numeroart_str,finalizado_bool,  cadastradopor_str,  datacadastro_dt,alteradopor_str,dataalteracao_dt, exibir_bool, quantidadeacessorios_int, finalizado_bool FROM tb_numeropedido WHERE exibir_bool = 1 AND finalizado_bool = 0 ORDER BY idnumeropedido_int DESC";


            MySqlCommand cd = new MySqlCommand();
            cd.Connection = c.cn;
            cd.CommandText = query;
            MySqlDataReader dr = cd.ExecuteReader();

            try
            {


                while (dr.Read())
                {

                    PedidoApi obj = new PedidoApi();

                    obj.MSGSTATUS = MensagemData.KDStatus.kdNormal;
                    obj.Id_int = Convert.ToInt32(dr["idnumeropedido_int"].ToString());
                    obj.IdCliente_int_fk = Convert.ToInt32(dr["idcliente_int_fk"].ToString());
                    obj.IdInspetor_int_fk = Convert.ToInt32(dr["idinspetor_int_fk"].ToString());
                    obj.IdResponsavelTecnico_int_fk = Convert.ToInt32(dr["idresponsaveltecnico_int_fk"].ToString());

                    obj.Cliente_str = dr["cliente_str"].ToString();
                    obj.Inspetor_str = dr["inspetor_str"].ToString();
                    obj.Responsaveltecnico_str = dr["responsaveltecnico_str"].ToString();

                    obj.NumeroPedido_str = dr["numeropedido_str"].ToString();
                    obj.NumeroArt_str = dr["numeroart_str"].ToString();
                    obj.Quantidade_int = Convert.ToInt32(dr["quantidadeacessorios_int"].ToString());
                    obj.Finalizado_bool = Convert.ToBoolean(dr["finalizado_bool"].ToString());

                    obj.AcessorioList = acessoriodao.ListarApi(obj.Id_int);

                    objPedidoClasse.Add(obj);

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

            return objPedidoClasse;
        }//fim

    }
}