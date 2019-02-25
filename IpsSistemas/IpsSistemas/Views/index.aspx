<%@ Page Title="" Language="C#" MasterPageFile="~/Views/Principal.Master" AutoEventWireup="true" CodeBehind="index.aspx.cs" Inherits="IpsSistemas.Views.index" %>

<%@ Register Assembly="AjaxControlToolkit" Namespace="AjaxControlToolkit" TagPrefix="ajaxToolkit" %>
<asp:Content ID="Content1" ContentPlaceHolderID="head" runat="server">
</asp:Content>
<asp:Content ID="Content2" ContentPlaceHolderID="ContentPlaceHolder1" runat="server">
    <style>
        .container-imagem-index img {
            width: auto;
            height: auto;
        }

        .container-imagem-index {
            clear: both;
            width: 40%;
            height: 40%;
        }

        img {
            vertical-align: top;
        }

        .rodape {
            bottom: 0;
            width: 100%;
            margin-bottom: 20px;
        }

        .modal-sslg {
            width: 85%;
        }
    </style>

    <div class="container-fluid">
        <div class="rows">
            <img src="../img/IPSservicos9.png" alt="IPS Serviços" class="container-imagem-index center-block" />
        </div>
    </div>
    <br />
    <br />
    <br />
    <br />
    <br />
    <footer class="rodape center-block">
        <div class="text-center">
            <div class="fonterodape">
                <p>
                    &copy; <%: DateTime.Now.Year %> - Desenvolvido por Ventura Sistemas. Todos Direitos Reservados.
               gventurasistemas@gmail.com Whats 9-9426-9153
                </p>
            </div>
        </div>
    </footer>


    <!------------------------------------------------------------------------>
    <!-- ------------------------Formularios Modais------------------------ -->
    <!------------------------------------------------------------------------>
    <!-- Modal Popup -->
    <div id="MyPopup" class="modal fade">
        <div class="modal-dialog modal-sslg">
            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        &times;</button>
                    <h4 class="modal-title">IPS Web - Atualização</h4>
                </div>
                <asp:UpdatePanel ID="UpdatePanel1" runat="server">

                    <ContentTemplate>
                        <div class="modal-body">
                            <div class="rows">

                                <img src="../img/Ventura_Sistemas_01_200X57.png" alt="Ventura sistemas" />

                            </div>
                            <br />
                            <div class="rows">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <asp:Label ID="lblTextoModal" runat="server" Text=" Agora você recebe notificações das atualizações gratuitas por aqui, veja agora uma atualização gratuita disponível"></asp:Label>
                                    <a href="../Videos/GerenciarInspecoes22-11-2018.mp4" target="_blank">Clique aqui para ver o video em tela cheia</a>
                                </div>
                            </div>
                            <div class="rows">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <br />
                                </div>
                            </div>
                            <div class="rows">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                
                                <iframe height="500" src="../Videos/GerenciarInspecoes22-11-2018.mp4" class="col-xs-12 col-sm-12 col-md-12 col-lg-12 center-block"></iframe>                                                              
                                    </div>
                            </div>
                            <div class="rows">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <br />
                                    <br />
                                </div>
                            </div>
                            <asp:CheckBox ID="chkNaoExibirNovamente" runat="server" />Não exibir novamente                           
                            <br />
                            <asp:Label ID="lblStatus" runat="server" Text="" Visible="false"></asp:Label>
                        </div>
                    </ContentTemplate>
                </asp:UpdatePanel>
                <div class="modal-footer">
                    <asp:Button ID="btnOk" runat="server" Text="Ok" CssClass="btn btn-info" OnClick="btnOk_Click" />
                </div>
            </div>
        </div>
    </div>


    <!------------------------------------------------------------------------>
    <!---------------------------JAVA SCRIPT Modais--------------------------->
    <!------------------------------------------------------------------------>
    <!-- Modal Popup -->
    <script>
        function ShowPopup() {
            $("#MyPopup").modal("show");
        }

    </script>

</asp:Content>
