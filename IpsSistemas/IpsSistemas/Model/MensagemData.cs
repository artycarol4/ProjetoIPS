using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace IpsSistemas.Model
{
    public class MensagemData
    {
        public enum KDStatus
        {
            kdNormal = 1,
            kdSemDado = 2,
            kdTokenInvalido = 8,
            kdTokenExpirado = 9,
            kdErro = 99
        }

        public string DESCRICAO;
        public string TOKEN;
        public decimal RETURNID;
        public KDStatus MSGSTATUS;
        public string RETURNSTR;
    }
}