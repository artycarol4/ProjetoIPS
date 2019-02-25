using System;
using System.Web.Http;
using Microsoft.Owin;
using Microsoft.Owin.Cors;
using Microsoft.Owin.Security.OAuth;
using Owin;

[assembly: OwinStartup(typeof(IpsSistemas.Token.Startup))]

namespace IpsSistemas.Token
{
    public class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            // configuracao WebApi
            var config = new HttpConfiguration();

            // configurando rotas
            config.MapHttpAttributeRoutes();

            config.Routes.MapHttpRoute(
                name: "DefaultApi",
                routeTemplate: "api/{controller}/{id}",
                defaults: new { id = RouteParameter.Optional }
            );

                
               
            // ativando cors
            app.UseCors(CorsOptions.AllowAll);

            // ativando tokens de acesso
            AtivandoAccessTokens(app);

            // ativando configuração WebApi
            app.UseWebApi(config);

            config.Formatters.Remove(config.Formatters.XmlFormatter);
            config.Formatters.JsonFormatter.Indent = true;

        }

        private void AtivandoAccessTokens(IAppBuilder app)
        {
            // configurando fornecimento de tokens
            var opcoesConfiguracaoToken = new OAuthAuthorizationServerOptions()
            {
                AllowInsecureHttp = true,
                TokenEndpointPath = new PathString("/token"),
                AccessTokenExpireTimeSpan = TimeSpan.FromDays(5),
                Provider = new ProviderToken()
            };

            // ativando o uso de access tokens            
            app.UseOAuthAuthorizationServer(opcoesConfiguracaoToken);
            app.UseOAuthBearerAuthentication(new OAuthBearerAuthenticationOptions());
        }


    }

}