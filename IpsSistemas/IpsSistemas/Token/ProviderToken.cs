using IpsSistemas.DAO;
using IpsSistemas.Model;
using Microsoft.Owin.Security.OAuth;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Claims;
using System.Threading.Tasks;
using System.Web;

namespace IpsSistemas.Token
{
    public class ProviderToken : OAuthAuthorizationServerProvider
    {

        public override async Task ValidateClientAuthentication(OAuthValidateClientAuthenticationContext context)
        {
            context.Validated();
        }

        public override async Task GrantResourceOwnerCredentials(OAuthGrantResourceOwnerCredentialsContext context)
        {

            UsuarioDAO objUsuarioDAO = new UsuarioDAO();
            string senhaCriptografada = objUsuarioDAO.CriptografarSenha(context.Password);
            UsuarioClasse objUsuario = objUsuarioDAO.Listar().FirstOrDefault(x => x.NomeOuCliente_str == context.UserName
                                && x.Senha_str == senhaCriptografada);




            if (objUsuario == null)
            {
                context.SetError("MSGSTATUS",
                  "Usuário não encontrado ou senha incorreta.");
                return;
            }
            else
            {

                // emitindo o token com informacoes extras
                // se o usuário existe
                var identidadeUsuario = new ClaimsIdentity(context.Options.AuthenticationType);
                context.Validated(identidadeUsuario);
            }


        }// FIM public override async Task GrantResourceOwnerCredentials


    }
}