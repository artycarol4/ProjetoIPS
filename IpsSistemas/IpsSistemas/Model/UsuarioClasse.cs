using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace IpsSistemas.Model
{
    public class UsuarioClasse : CadastroClasse
    {
        public string Senha_str { get; set; }
        public bool Cliente_bool { get; set; }
        public bool NotificacaoAtualizacao_bool { get; set; }

        
        
    }
}