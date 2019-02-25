package nadlertec.com.br.ips.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andrognito.flashbar.Flashbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.model.MENSAGEM;
import nadlertec.com.br.ips.model.TBLAUDO;
import nadlertec.com.br.ips.model.TBOS;
import nadlertec.com.br.ips.repository.TBLAUDORepository;
import nadlertec.com.br.ips.repository.TBOSRepository;
import nadlertec.com.br.ips.service.GitHubService;
import nadlertec.com.br.ips.setting.CONSTANT;
import nadlertec.com.br.ips.setting.switchicon.SwitchIconView;
import nadlertec.com.br.ips.ui.activity.Main;
import nadlertec.com.br.ips.util.Utilitario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragSync extends Fragment {
    Dialog dalProgress;
    private Flashbar flashbar = null;
    private static Activity acMain;
    private static Context cContext;
    public String strCAPTION = "SINCRONISMO";
    private TextView tvMensagem;
    private TextView tvResultado;
    private ProgressBar pbMain;
    private ProgressBar pbLaudo;
    private SwitchIconView sivSync;
    private List<TBLAUDO> lstLaudo;
    private List<String> lstLOG;
    private int intLaudo = 0;
    String[] vImage1;
    String[] vImage2;
    String[] vImage3;
    String[] vImage4;
    int intUpload = 0;
    int intCountImage = 0;
    int intCountPart = 0;
    int intCountLaudoOK = 0;
    int intCountLaudoErro = 0;
    int intCountImageOK = 0;
    int intCountImageErro = 0;
    private static int intCount = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_sync, container, false);

        acMain = getActivity();
        cContext = getContext();

        tvMensagem = (TextView)rootView.findViewById(R.id.FragSyncTvMensagem);
        tvMensagem.setText(getString(R.string.mensagemSincronizandoInfo));
        tvResultado = (TextView)rootView.findViewById(R.id.FragSyncTvResult);
        pbMain = (ProgressBar)rootView.findViewById(R.id.FragSyncPbProgress);
        pbMain.setMax(3);
        pbLaudo = (ProgressBar)rootView.findViewById(R.id.FragSyncPbLaudo);

        sivSync = (SwitchIconView)rootView.findViewById(R.id.FragSyncSivSync);

        lstLOG = new ArrayList<>();

        TBLAUDORepository repository = new TBLAUDORepository(cContext);
        lstLaudo = repository.ListNoSync();

        if(lstLaudo == null)
            lstLaudo = new ArrayList<>();
        else
            pbLaudo.setMax(lstLaudo.size());

        PreparaAmbiente();

        return rootView;
    }

    private void MostraErroBar(String pMENSAGEM){
        flashbar = Utilitario.MensagemErroFbTitulo(acMain,false,strCAPTION,pMENSAGEM,R.drawable.ico_home);
        flashbar.show();
    }

    private void GetOS(){
        new Thread(new Runnable() {
            public void run() {
                tvMensagem.setText("Sincronizando as OS...");
            }
        }).start();

        dalProgress = Utilitario.openProgress(acMain);
        dalProgress.show();
        try{
            GitHubService.ServiceOS execute = GitHubService.ServiceOS.retrofit.create(GitHubService.ServiceOS.class);
            final Call<List<TBOS>> call =  execute.Exec("Bearer " + Main.config.TOKEN);
            call.enqueue(new Callback<List<TBOS>>() {
                @Override
                public void onResponse(Call<List<TBOS>> call, Response<List<TBOS>> response) {
                    if(response.code() != 200){
                       lstLOG.add("OS ---> " + getString(R.string.erroAcessoServidor) + "\n");
                    }else if(response.body().get(0).MSGSTATUS == CONSTANT.kdErro){
                        lstLOG.add("OS ---> Erro: " + response.body().get(0).DESCRICAO + "\n");
                    }else{
                        pbMain.setProgress(3);
                        if(response.body().get(0).MSGSTATUS == CONSTANT.kdSemDado){
                            lstLOG.add(" OS ---> Sem dados para serem sincronizados\n");
                            SynLaudo();
                        }else if(response.body().get(0).MSGSTATUS == CONSTANT.kdNormal){
                            TBOSRepository repository = new TBOSRepository(cContext);
                            repository.SyncOS(response.body());
                            lstLOG.add(" OS ---> " + response.body().size() + " sincronizadas com sucesso.\n");
                        }else{
                            lstLOG.add(" OS ---> "+ getString(R.string.erroAcessoServidor) + "\n");
                        }
                    }
                    pbMain.setProgress(2);
                    SynLaudo();
                }
                @Override
                public void onFailure(Call<List<TBOS>> call, Throwable t) {
                    lstLOG.add("OS ---> " + getString(R.string.erroAcessoServidor)+"\n");
                    pbMain.setProgress(2);
                    SynLaudo();
                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
            dalProgress.dismiss();
        }
    }

    public void SynLaudo(){
        try{
          //  if(Utilitario.WifiOn(acMain)){
                tvMensagem.setText("Sincronizando os Laudos...");

                if(lstLaudo.size() == 0){
                    lstLOG.add("Laudo ---> OK, sem laudos para serem enviados.");
                    MostraResultado();
                }else{
                    pbLaudo.setMax(lstLaudo.size());
                    ValidaImagem();
                }
                /*
            }else{
                lstLOG.add("Laudo ---> " + getString(R.string.mensagemSemConexaoWifi));
                MostraResultado();
            }
            */
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void ValidaImagem(){
        try{
            if(lstLaudo.get(intLaudo).Caminhoevidenciafotograficaum_str.length() > 0){
                intUpload = 1;
                vImage1 = Utilitario.BitmapToString(lstLaudo.get(intLaudo).Caminhoevidenciafotograficaum_str,cContext);
                intCountImage++;
            }else{
                vImage1 = null;
            }

            if(lstLaudo.get(intLaudo).Caminhoevidenciafotograficadois_str.length() > 0){
                vImage2 = Utilitario.BitmapToString(lstLaudo.get(intLaudo).Caminhoevidenciafotograficadois_str,cContext);
                intCountImage++;
            }else{
                vImage2 = null;
            }

            if(lstLaudo.get(intLaudo).Caminhoevidenciafotograficatres_str.length() > 0){
                vImage3 = Utilitario.BitmapToString(lstLaudo.get(intLaudo).Caminhoevidenciafotograficatres_str,cContext);
                intCountImage++;
            }else{
                vImage3 = null;
            }

            if(lstLaudo.get(intLaudo).Caminhoevidenciafotograficaquatro_str.length() > 0){
                vImage4 = Utilitario.BitmapToString(lstLaudo.get(intLaudo).Caminhoevidenciafotograficaquatro_str,cContext);
                intCountImage++;
            }else{
                vImage4 = null;
            }

            SendImage();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void ErroImagem(){
        lstLOG.add("Laudo " + intLaudo + 1 + " - Erro: não será enviado devido a erro na sincronização da Imagem 0" + intUpload + "\n");
        intLaudo++;
        intCountPart = 0;
        intUpload = 0;
        intCountLaudoErro++;
    }

    private void ErroLaudo(String pERRO){
        lstLOG.add("Laudo " + intLaudo + 1 + " - Erro: "+ pERRO + "\n");
        intLaudo++;
        intCountPart = 0;
        intUpload = 0;
        intCountLaudoErro++;
    }

    private void UpdateStatusImage(){
        TBLAUDORepository repository = new TBLAUDORepository(cContext);
        try{

            switch (intUpload){
                case 1:
                    lstLaudo.get(intLaudo).Evidenciafotograficaum_sync = repository.UpdateStatusImage(lstLaudo.get(intLaudo).LAUDOID,"Evidenciafotograficaum_sync", 1);
                    break;

                case 2:
                    lstLaudo.get(intLaudo).Evidenciafotograficadois_sync = repository.UpdateStatusImage(lstLaudo.get(intLaudo).LAUDOID,"Evidenciafotograficadois_sync", 1);
                    break;

                case 3:
                    lstLaudo.get(intLaudo).Evidenciafotograficatres_sync = repository.UpdateStatusImage(lstLaudo.get(intLaudo).LAUDOID,"Evidenciafotograficatres_sync", 1);
                    break;

                case 4:
                    lstLaudo.get(intLaudo).Evidenciafotograficaquatro_sync = repository.UpdateStatusImage(lstLaudo.get(intLaudo).LAUDOID,"Evidenciafotograficaquatro_sync", 1);
                    break;
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void SendImage(){
        if(intUpload == 1 && vImage1 != null){
            if(intCountPart == vImage1.length){
                UploadImage("",lstLaudo.get(intLaudo).Caminhoevidenciafotograficaum_str,3);
            }else{
                UploadImage(vImage1[intCountPart],lstLaudo.get(intLaudo).Caminhoevidenciafotograficaum_str,(intCountPart == 0? 1 : 2));
            }
        }else if(intUpload == 2 && vImage2 != null){
            if(intCountPart == vImage2.length){
                UploadImage("",lstLaudo.get(intLaudo).Caminhoevidenciafotograficadois_str,3);
            }else{
                UploadImage(vImage2[intCountPart],lstLaudo.get(intLaudo).Caminhoevidenciafotograficadois_str,(intCountPart == 0? 1 : 2));
            }
        }else if(intUpload == 3 && vImage3 != null){
            if(intCountPart == vImage3.length){
                UploadImage("",lstLaudo.get(intLaudo).Caminhoevidenciafotograficatres_str,3);
            }else{
                UploadImage(vImage3[intCountPart],lstLaudo.get(intLaudo).Caminhoevidenciafotograficatres_str,(intCountPart == 0? 1 : 2));
            }
        }else if(intUpload == 4 && vImage4 != null){
            if(intCountPart == vImage4.length){
                UploadImage("",lstLaudo.get(intLaudo).Caminhoevidenciafotograficaquatro_str,3);
            }else{
                UploadImage(vImage4[intCountPart],lstLaudo.get(intLaudo).Caminhoevidenciafotograficaquatro_str,(intCountPart == 0? 1 : 2));
            }
        }else{
            intCountPart = 0;
            intUpload = 0;
            SendLaudo();
        }
    }

    private void SendLaudo(){
        try{
            GitHubService.ServiceSENDOS execute = GitHubService.ServiceSENDOS.retrofit.create(GitHubService.ServiceSENDOS.class);
            final Call<TBLAUDO> call =  execute.Exec(lstLaudo.get(intLaudo).JSON(), Main.config.USERNAME,"Bearer " + Main.config.TOKEN);
            call.enqueue(new Callback<TBLAUDO>() {
                @Override
                public void onResponse(Call<TBLAUDO> call, Response<TBLAUDO> response) {
                    if(response.code() != 200){
                        ErroLaudo("Não será possível cadastrar o Laudo devido a problemas no servidor.");
                    }else{
                        if(response.body() == null){
                            ErroLaudo("Não será possível cadastrar o Laudo devido a problemas no servidor.");
                        }else  if(response.body().MSGSTATUS == CONSTANT.kdErro){
                            ErroLaudo(response.body().DESCRICAO);
                        }else if(response.body().MSGSTATUS != CONSTANT.kdNormal){
                            ErroLaudo("Não será possível cadastrar o Laudo devido a problemas no servidor.");
                        }else{
                            response.body().SYNC = 1;
                            response.body().LAUDOID = lstLaudo.get(intLaudo).LAUDOID;
                            TBLAUDORepository repository = new TBLAUDORepository(cContext);
                            repository.UpdateAdd(response.body());
                            lstLOG.add("Laudo 0" + (intLaudo + 1) + " --> Ok, enviado com sucesso!\n");
                            intLaudo++;
                            pbLaudo.setProgress(intLaudo);

                            if(intLaudo == lstLaudo.size()){
                                MostraResultado();
                            }else{
                                ValidaImagem();
                            }
                        }
                    }
                }
                @Override
                public void onFailure(Call<TBLAUDO> call, Throwable t) {
                    dalProgress.dismiss();
                    ErroLaudo("Não será possível cadastrar o Laudo devido a problemas no servidor.");
                }
            });

        }catch (Exception ex){
            dalProgress.dismiss();
            ex.printStackTrace();
            ErroLaudo("Não será possível cadastrar o Laudo devido a problemas no servidor.");
        }
    }

    private void UploadImage(String pIMAGESTR, String pIMAGENAME, final int pSTATUS){
        try{
            GitHubService.ServiceUPLOADIMAGE execute = GitHubService.ServiceUPLOADIMAGE.retrofit.create(GitHubService.ServiceUPLOADIMAGE.class);
            final Call<MENSAGEM> call =  execute.Exec(pIMAGESTR, pIMAGENAME,pSTATUS,"Bearer " + Main.config.TOKEN);
            call.enqueue(new Callback<MENSAGEM>() {
                @Override
                public void onResponse(Call<MENSAGEM> call, Response<MENSAGEM> response) {
                    if(response.code() != 200){
                        ErroImagem();
                    }else{
                        if(response.body() == null){
                            ErroImagem();
                        }else  if(response.body().MSGSTATUS == CONSTANT.kdErro){
                            ErroImagem();
                        }else{
                            if(pSTATUS == 3){
                                UpdateStatusImage();
                                intCountPart = 0;
                                intUpload++;
                                intCountImageOK++;
                            }else{
                                intCountPart++;
                            }
                            SendImage();
                        }
                    }
                }
                @Override
                public void onFailure(Call<MENSAGEM> call, Throwable t) {
                    dalProgress.dismiss();
                    ErroImagem();
                }
            });

        }catch (Exception ex){
            dalProgress.dismiss();
            ex.printStackTrace();
            ErroImagem();
        }
    }

    private void MostraResultado(){
        String strTEMP = "";
        tvMensagem.setText("Sincronização finalizada.");
        pbMain.setProgress(0);
        for(int i =0; i < lstLOG.size();i++){
            strTEMP += lstLOG.get(i);
        }
        tvResultado.setText(strTEMP);
        if(dalProgress != null)
            dalProgress.dismiss();
    }

    private void PreparaAmbiente(){
        TBLAUDORepository repository = new TBLAUDORepository(cContext);

        tvMensagem.setText("Preparando o ambiente...");

        try{
            File file = new File(Environment.getExternalStorageDirectory(), getString(R.string.dirTMP));
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }

            File[] vPicture = file.listFiles();

            pbLaudo.setMax(vPicture == null ? 0 : vPicture.length);
            for (intCount = 0; intCount < (vPicture == null ? -1 : vPicture.length); intCount++)
            {
                pbLaudo.setProgress(intCount);
                if(!repository.ImageExist(vPicture[intCount].getName()))
                    vPicture[intCount].delete();
            }
            pbLaudo.setProgress(0);
            lstLOG.add("Preparação do ambiente --> OK\n");

        }catch(Exception ex){
            ex.printStackTrace();
            lstLOG.add("Preparação do ambiente --> Erro: " + ex.getMessage() + "\n");
        }
        if(Utilitario.OnLine(cContext))
            GetOS();
        else{
            lstLOG.add("OS --> " + getString(R.string.mensagemSemConexaoInternet) + " para sincronizar.\n");
            lstLOG.add("Laudos --> " + getString(R.string.mensagemSemConexaoWifi) + "\n");
            MostraResultado();
        }
    }
}
