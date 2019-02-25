<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Principal.Master" AutoEventWireup="true" CodeBehind="Login.aspx.cs" Inherits="IpsSistemas.Views.Login" %>

<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <link href="../css/login.css" rel="stylesheet" />


    <img src="../img/logoLogin2.png" alt="IPS Serviços" class="containeri center-block" />
    <div class="centralizar">
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" align="center">
            <div class="loginFonte" align="center" style="margin-bottom: 20px">BEM VINDO AO IPSWEB</div>
        </div>
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" align="center">
            <input id="txtUsuario" runat="server" class="text-uppercase inputLogin" style="margin-bottom: 20px" type="text" name="username" placeholder="Usuário" autofocus="autofocus" />
            <input id="txtSenha" runat="server" class="text-uppercase inputLogin" style="margin-bottom: 30px" type="password" placeholder="Senha" />
        </div>
        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" align="center">
            <asp:Button ID="btnEntrar" runat="server" CssClass="btn btn-primary buttonLogin" Style="margin-bottom: 35px" Text="Entrar" OnClick="btnEntrar_Click" />
        </div>

        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
            <asp:Label ID="lblStatus" runat="server" Text="Usuário e senha incorreto" Visible="false"></asp:Label>
        </div>

    </div>


    <asp:UpdateProgress ID="UpdateProgress1" runat="server">
        <ProgressTemplate>
            <div class="modalt">
                <div class="center">
                    <img src="../gif/35.gif" />
                </div>
            </div>
        </ProgressTemplate>
    </asp:UpdateProgress>
</asp:Content>
