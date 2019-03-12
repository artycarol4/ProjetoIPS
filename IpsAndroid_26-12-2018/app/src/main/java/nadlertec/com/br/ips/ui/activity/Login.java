package nadlertec.com.br.ips.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.andrognito.flashbar.Flashbar;

import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.database.DataBase;
import nadlertec.com.br.ips.model.CONFIG;
import nadlertec.com.br.ips.model.TOKEN;
import nadlertec.com.br.ips.service.GitHubService;
import nadlertec.com.br.ips.setting.editpassword.EditTextPassword;
import nadlertec.com.br.ips.util.Preferencia;
import nadlertec.com.br.ips.util.Utilitario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private Flashbar flashbar = null;
    Dialog dalProgress;
    TextInputLayout tilUsuario;
    EditText etUsuario;
    TextInputLayout tilPassword;
    EditTextPassword etpPassword;
    private Context cContext;
    private Activity acMain;
    private static String strCaption = "LOGIN";
    CONFIG config;
    private int REQUEST_PERMISSION = 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        cContext = this;
        acMain = this;

        config = Preferencia.Read(cContext,getString(R.string.keyCONFIG),CONFIG.class);
        if(config == null)
            config = new CONFIG();
        tilUsuario = (TextInputLayout)findViewById(R.id.LoginTilUsuario);
        etUsuario = (EditText)findViewById(R.id.LoginEtUsuario);

        tilPassword = (TextInputLayout)findViewById(R.id.LoginTilPassword);
        etpPassword = (EditTextPassword)findViewById(R.id.LoginEtpPassword);

        LinearLayout llEntrar = (LinearLayout) findViewById(R.id.LoginLlAcessar);
        llEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AbreApp();

                if (!Validacao()) {
                    MostraErroBar("Verifique os campos com erros.");
                } else {
                    if(Utilitario.OnLine(cContext)){
                        Login();
                    }else{
                        if(!config.PASSWORD.equals(etpPassword.getText().toString())){
                            MostraErroBar(cContext.getString(R.string.erroUsuarioIncorreto));
                        }else{
                            AbreApp();
                        }
                    }

                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION);
        }
        if(config.USERNAME != null)
            if(config.USERNAME != "")
                etUsuario.setText(config.USERNAME);
        //etpPassword.setText("123");
        //etUsuario.setText("ROMULO");
    }

    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
            } else {
                // User refused to grant permission.
            }
        }
    }

    private boolean Validacao(){
        boolean blnOK = true;

        if(etUsuario.getText().length() == 0){
            tilUsuario.setError(getString(R.string.erroCampoObrigatorio));
            blnOK = false;
        }else if(etUsuario.getText().length() < 3){
            tilUsuario.setError(getString(R.string.erroMinimo3Caracter));
            blnOK = false;
        }

        if(etpPassword.getText().length() == 0){
            tilPassword.setError(getString(R.string.erroCampoObrigatorio));
            blnOK = false;
        }else if(etpPassword.getText().length() < 3){
            tilPassword.setError(getString(R.string.erroMinimo3Caracter));
            blnOK = false;
        }

        return blnOK;
    }

    private void MostraErroBar(String pMENSAGEM){
        flashbar = Utilitario.MensagemErroFbTitulo(acMain,false,strCaption,pMENSAGEM,R.drawable.ico_login);
        flashbar.show();
    }

    private void Login(){
        dalProgress = Utilitario.openProgress(cContext);
        dalProgress.show();

        try{

            GitHubService.ServiceTOKEN execute = GitHubService.ServiceTOKEN.retrofit.create(GitHubService.ServiceTOKEN.class);
            final Call<TOKEN> call =  execute.Exec(etUsuario.getText().toString(),etpPassword.getText().toString(),"password");
            call.enqueue(new Callback<TOKEN>() {
                @Override
                public void onResponse(Call<TOKEN> call, Response<TOKEN> response) {
                    if(response.code() != 200 && response.code() != 400){
                        dalProgress.dismiss();
                        MostraErroBar(cContext.getString(R.string.erroAcessoServidor));
                    }else if(response.code() == 400){
                        dalProgress.dismiss();
                        MostraErroBar(cContext.getString(R.string.erroUsuarioIncorreto));
                        etpPassword.setText("");
                    }else{
                        if(response.body() == null){
                            dalProgress.dismiss();
                            MostraErroBar(cContext.getString(R.string.erroAcessoServidor));
                        }else  if(response.body().error != ""){
                            dalProgress.dismiss();
                            MostraErroBar(cContext.getString(R.string.erroUsuarioIncorreto));
                        }else{
                            config.TOKEN = response.body().access_token;
                            config.USERNAME = etUsuario.getText().toString();
                            config.PASSWORD = etpPassword.getText().toString();
                            Preferencia.Write(cContext,getString(R.string.keyCONFIG),config);
                            dalProgress.dismiss();
                            AbreApp();
                        }
                    }
                }
                @Override
                public void onFailure(Call<TOKEN> call, Throwable t) {
                    dalProgress.dismiss();
                    MostraErroBar(cContext.getString(R.string.erroAcessoServidor));
                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void AbreApp(){
        try {
            DataBase.InicializaBaseCopia(cContext.getString(R.string.DataBaseName), "1", cContext.getString(R.string.LogCat), cContext);
            config.UPDATEBASE = 1;
            Preferencia.Write(cContext,cContext.getString(R.string.keyCONFIG),config);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent minhaAcao = new Intent(acMain,Main.class);
        acMain.startActivity(minhaAcao);
        acMain.finish();
    }

}
