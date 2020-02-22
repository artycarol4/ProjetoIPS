package nadlertec.com.br.ips.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.flashbar.Flashbar;
import com.imagepicker.FilePickUtils;
import com.imagepicker.LifeCycleCallBackManager;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.model.MENSAGEM;
import nadlertec.com.br.ips.model.TBACESSORIO;
import nadlertec.com.br.ips.model.TBLAUDO;
import nadlertec.com.br.ips.model.TBOS;
import nadlertec.com.br.ips.repository.TBLAUDORepository;
import nadlertec.com.br.ips.service.GitHubService;
import nadlertec.com.br.ips.setting.CONSTANT;
import nadlertec.com.br.ips.setting.CustomTypefaceSpan;
import nadlertec.com.br.ips.setting.switchicon.SwitchIconView;
import nadlertec.com.br.ips.util.Mascara;
import nadlertec.com.br.ips.util.Utilitario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.imagepicker.FilePickUtils.CAMERA_PERMISSION;
import static com.imagepicker.FilePickUtils.STORAGE_PERMISSION_IMAGE;

public class LaudoEdit extends AppCompatActivity {
    Dialog dalProgress;
    private Flashbar flashbar = null;
    private static Activity acMain;
    private static Context cContext;
    public String strCAPTION = "LAUDO";
    private Menu mnuToolbar;

    private FilePickUtils filePickUtils;
    private LifeCycleCallBackManager lifeCycleCallBackManager;

    String[] vConcTec;
    adapterConcTec adpConcTec;
    private static Spinner spiConcTec;
    TextView tvConcTecErro;

    String[] vAcessorio;
    adapterAcessorio adpAcessorio;
    private static Spinner spiAcessorio;
    TextView tvAcessorioErro;
    List<TBACESSORIO> lstAcessorio;

    EditText etEvidencia1;
    SwitchIconView sivCameraEv01;
    SwitchIconView sivGaleriaEv01;
    EditText etEvidencia2;
    SwitchIconView sivCameraEv02;
    SwitchIconView sivGaleriaEv02;
    EditText etEvidencia3;
    SwitchIconView sivCameraEv03;
    SwitchIconView sivGaleriaEv03;
    EditText etEvidencia4;
    SwitchIconView sivCameraEv04;
    SwitchIconView sivGaleriaEv04;
    int intEvidencia = 0;

    TextInputLayout tilTAG;
    EditText etTAG;
    TextInputLayout tilFab;
    EditText etFab;
    TextInputLayout tilModelo;
    EditText etModelo;
    TextInputLayout tilCapacidade;
    EditText etCapacidade;
    TextInputLayout tilDimensao;
    EditText etDimensao;
    TextInputLayout tilObservacao;
    EditText etObservacao;
    TextInputLayout tilContato;
    EditText etContato;
    TextInputLayout tilSetor;
    EditText etSetor;
    TextInputLayout tilDDD;
    EditText etDDD;
    TextInputLayout tilTelefone;
    EditText etTelefone;
    TextInputLayout tilObservacao2;
    EditText etObservacao2;
    TextInputLayout tilMetodologia;
    EditText etMetodologia;
    TextInputLayout tilCertFab;
    EditText etCertFab;
    TextInputLayout tilRegInsp;
    EditText etRegInsp;
    TextInputLayout tilRegRep;
    EditText etRegRep;
    TextInputLayout tilContTec;
    EditText etContTec;
    TextInputLayout tilRecomendacao;
    EditText etRecomendacao;

    LinearLayout llPasso1;
    LinearLayout llPasso2;
    LinearLayout llMainSeguir;
    LinearLayout llSeguir;
    LinearLayout llMainGravar;
    LinearLayout llGravar;
    TextView tvTitGravar;
    ScrollView svMain;

    TBOS pedido;

    String[] vImage1;
    String[] vImage2;
    String[] vImage3;
    String[] vImage4;
    int intUpload = 0;
    int intCountPart = 0;

    String strLOG = "";
    private static boolean blnEdit = false;

    private static String strFileUriImage = "";
    private boolean blnDDD = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laudo_edit);

        acMain = this;
        cContext = this;

        tilTAG = (TextInputLayout) findViewById(R.id.LaudoTilTAG);
        etTAG = (EditText) findViewById(R.id.LaudoEtTAG);
        etTAG.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilTAG.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilFab = (TextInputLayout) findViewById(R.id.LaudoTilFab);
        etFab = (EditText) findViewById(R.id.LaudoEtFab);
        etFab.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilFab.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilModelo = (TextInputLayout) findViewById(R.id.LaudoTilModelo);
        etModelo = (EditText) findViewById(R.id.LaudoEtModelo);
        etModelo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilModelo.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilCapacidade = (TextInputLayout) findViewById(R.id.LaudoTilCapacidade);
        etCapacidade = (EditText) findViewById(R.id.LaudoEtCapacidade);
        etCapacidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilCapacidade.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilDimensao = (TextInputLayout) findViewById(R.id.LaudoTilDimensao);
        etDimensao = (EditText) findViewById(R.id.LaudoEtDimensao);
        etDimensao.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilDimensao.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilObservacao = (TextInputLayout) findViewById(R.id.LaudoTilObservacao);
        etObservacao = (EditText) findViewById(R.id.LaudoEtObservacao);
        etObservacao.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilObservacao.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilContato = (TextInputLayout) findViewById(R.id.LaudoTilContato);
        etContato = (EditText) findViewById(R.id.LaudoEtContato);
        etContato.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilContato.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilSetor = (TextInputLayout) findViewById(R.id.LaudoTilSetor);
        etSetor = (EditText) findViewById(R.id.LaudoEtSetor);
        etSetor.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilSetor.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilDDD = (TextInputLayout) findViewById(R.id.LaudoTilDDD);
        etDDD = (EditText) findViewById(R.id.LaudoEtDDD);
        etDDD.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilDDD.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );
        etDDD.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                    blnDDD = true;
            }
        });

        tilTelefone = (TextInputLayout) findViewById(R.id.LaudoTilTelefone);
        etTelefone = (EditText) findViewById(R.id.LaudoEtTelefone);
        etTelefone.addTextChangedListener(Mascara.insert(Mascara.TELEFONE_MASK, etTelefone));
        etTelefone.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilTelefone.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilObservacao2 = (TextInputLayout) findViewById(R.id.LaudoTilObservacao2);
        etObservacao2 = (EditText) findViewById(R.id.LaudoEtObervacao2);
        etObservacao2.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilObservacao2.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );
        etObservacao2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus && blnDDD){
                    etTelefone.requestFocus();
                    blnDDD = false;
                }

            }
        });

        tilMetodologia = (TextInputLayout) findViewById(R.id.LaudoTilMetodologia);
        etMetodologia = (EditText) findViewById(R.id.LaudoEtMetodologia);
        etMetodologia.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilMetodologia.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        filePickUtils = new FilePickUtils(this, onFileChoose);
        lifeCycleCallBackManager = filePickUtils.getCallBackManager();

        etEvidencia1 = (EditText) findViewById(R.id.LaudoEtEvidencia1);
        etEvidencia1.setEnabled(false);
        sivCameraEv01 = (SwitchIconView) findViewById(R.id.LaudoSivEvidencia1Foto);
        sivCameraEv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sivCameraEv01.isIconEnabled())
                    return;
                intEvidencia = 1;
                filePickUtils.setMaxHeight(CONSTANT.MAX_HEIGHT);
                filePickUtils.setMaxWidth(CONSTANT.MAX_WIDTH);
                filePickUtils.requestImageCamera(CAMERA_PERMISSION, false, true);
            }
        });
        sivGaleriaEv01 = (SwitchIconView) findViewById(R.id.LaudoSivEvidencia1Galeria);
        sivGaleriaEv01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sivGaleriaEv01.isIconEnabled())
                    return;
                intEvidencia = 1;
                filePickUtils.setMaxHeight(CONSTANT.MAX_HEIGHT);
                filePickUtils.setMaxWidth(CONSTANT.MAX_WIDTH);
                filePickUtils.requestImageGallery(STORAGE_PERMISSION_IMAGE, false, true);
            }
        });

        etEvidencia2 = (EditText) findViewById(R.id.LaudoEtEvidencia2);
        etEvidencia2.setEnabled(false);
        sivCameraEv02 = (SwitchIconView) findViewById(R.id.LaudoSivEvidencia2Foto);
        sivCameraEv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEvidencia1.getText().length() == 0)
                    return;
                intEvidencia = 2;
                filePickUtils.setMaxHeight(CONSTANT.MAX_HEIGHT);
                filePickUtils.setMaxWidth(CONSTANT.MAX_WIDTH);
                filePickUtils.requestImageCamera(CAMERA_PERMISSION, false, true);
            }
        });
        sivGaleriaEv02 = (SwitchIconView) findViewById(R.id.LaudoSivEvidencia2Galeria);
        sivGaleriaEv02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEvidencia1.getText().length() == 0)
                    return;
                intEvidencia = 2;
                filePickUtils.setMaxHeight(CONSTANT.MAX_HEIGHT);
                filePickUtils.setMaxWidth(CONSTANT.MAX_WIDTH);
                filePickUtils.requestImageGallery(STORAGE_PERMISSION_IMAGE, false, true);
            }
        });

        etEvidencia3 = (EditText) findViewById(R.id.LaudoEtEvidencia3);
        etEvidencia3.setEnabled(false);
        sivCameraEv03 = (SwitchIconView) findViewById(R.id.LaudoSivEvidencia3Foto);
        sivCameraEv03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEvidencia1.getText().length() == 0 || etEvidencia2.getText().length() == 0)
                    return;
                intEvidencia = 3;
                filePickUtils.setMaxHeight(CONSTANT.MAX_HEIGHT);
                filePickUtils.setMaxWidth(CONSTANT.MAX_WIDTH);
                filePickUtils.requestImageCamera(CAMERA_PERMISSION, false, true);
            }
        });
        sivGaleriaEv03 = (SwitchIconView) findViewById(R.id.LaudoSivEvidencia3Galeria);
        sivGaleriaEv03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEvidencia1.getText().length() == 0 || etEvidencia2.getText().length() == 0)
                    return;
                intEvidencia = 3;
                filePickUtils.setMaxHeight(CONSTANT.MAX_HEIGHT);
                filePickUtils.setMaxWidth(CONSTANT.MAX_WIDTH);
                filePickUtils.requestImageGallery(STORAGE_PERMISSION_IMAGE, false, true);
            }
        });
        etEvidencia4 = (EditText) findViewById(R.id.LaudoEtEvidencia4);
        etEvidencia4.setEnabled(false);
        sivCameraEv04 = (SwitchIconView) findViewById(R.id.LaudoSivEvidencia4Foto);
        sivCameraEv04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEvidencia1.getText().length() == 0 || etEvidencia2.getText().length() == 0 || etEvidencia3.getText().length() == 0)
                    return;
                intEvidencia = 4;
                filePickUtils.setMaxHeight(CONSTANT.MAX_HEIGHT);
                filePickUtils.setMaxWidth(CONSTANT.MAX_WIDTH);
                filePickUtils.requestImageCamera(CAMERA_PERMISSION, false, true);
            }
        });
        sivGaleriaEv04 = (SwitchIconView) findViewById(R.id.LaudoSivEvidencia4Galeria);
        sivGaleriaEv04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEvidencia1.getText().length() == 0 || etEvidencia2.getText().length() == 0 || etEvidencia3.getText().length() == 0)
                    return;
                intEvidencia = 4;
                filePickUtils.setMaxHeight(CONSTANT.MAX_HEIGHT);
                filePickUtils.setMaxWidth(CONSTANT.MAX_WIDTH);
                filePickUtils.requestImageGallery(STORAGE_PERMISSION_IMAGE, false, true);
            }
        });

        tilCertFab = (TextInputLayout) findViewById(R.id.LaudoTilCertFabr);
        etCertFab = (EditText) findViewById(R.id.LaudoEtCertFabr);
        etCertFab.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilCertFab.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilRegInsp = (TextInputLayout) findViewById(R.id.LaudoTilRegInsp);
        etRegInsp = (EditText) findViewById(R.id.LaudoEtRegInsp);
        etRegInsp.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilRegInsp.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilRegRep = (TextInputLayout) findViewById(R.id.LaudoTilRegRep);
        etRegRep = (EditText) findViewById(R.id.LaudoEtRegRep);
        etRegRep.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilRegRep.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tilRecomendacao = (TextInputLayout) findViewById(R.id.LaudoTilRecomendacao);
        etRecomendacao = (EditText) findViewById(R.id.LaudoEtRecomendacao);
        etRecomendacao.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilRecomendacao.setErrorEnabled(false);
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}}
        );

        tvConcTecErro = (TextView) findViewById(R.id.LaudoTvConcTecErro);
        tvConcTecErro.setVisibility(View.GONE);
        vConcTec = getResources().getStringArray(R.array.ConclusaoTecnica);
        adpConcTec = new adapterConcTec(this, R.layout.spinner_custom_filter, vConcTec);
        spiConcTec = (Spinner)findViewById(R.id.LaudoSpiConcTec);
        spiConcTec.setAdapter(adpConcTec);
        spiConcTec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                if(posicao > 0)
                    tvConcTecErro.setVisibility(View.GONE);
                pedido.laudo.ConclusaoTecnica_str = vConcTec[posicao];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvAcessorioErro = (TextView) findViewById(R.id.LaudoTvAcessorioErro);
        tvAcessorioErro.setVisibility(View.GONE);
        spiAcessorio = (Spinner)findViewById(R.id.LaudoSpiAcessorio);
        spiAcessorio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                if(posicao > 0)
                    tvAcessorioErro.setVisibility(View.GONE);
                pedido.laudo.Acessorio_str = pedido.AcessorioList.get(posicao).Acessorio_str;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        llMainGravar = (LinearLayout) findViewById(R.id.LaudoLlMainGravar);
        llGravar = (LinearLayout) findViewById(R.id.LaudoLlGravar);
        llGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tvTitGravar.getText().toString().equals(getString(R.string.btnCONTINUAR))){
                    intUpload = 1;
                    intCountPart = 0;
                    ProximoPasso();
                }else{
                    if(!Validacao()){
                        MostraErroBar(getString(R.string.erroVerifiqueCampoErro),"");
                    }else{
                        Gravar();
                    }
                }

            }
        });

        svMain = (ScrollView) findViewById(R.id.LaudoSvMain);
        llPasso1 = (LinearLayout) findViewById(R.id.LaudoLlPasso1);
        llPasso2 = (LinearLayout) findViewById(R.id.LaudoLlPasso2);
        tvTitGravar = (TextView) findViewById(R.id.LaudoTvTitGravar);

        Bundle bMessage = getIntent().getExtras();
        if(bMessage != null){
            pedido = (TBOS) getIntent().getExtras().get("STBPEDIDO");
            if(pedido.laudo == null){
                TBLAUDORepository repository = new TBLAUDORepository(cContext);
                blnEdit = false;
                TBLAUDO laudo = new TBLAUDO();
                pedido.laudo = laudo;
                laudo.Id_int = pedido.Id_int;
                llPasso1.setVisibility(View.GONE);
                llPasso2.setVisibility(View.GONE);
                tvTitGravar.setText(getString(R.string.btnCONTINUAR));

                repository.ListContato(laudo);

                if(laudo.Telefone_str != null){
                    etDDD.setText(Utilitario.TELDDD(laudo.Telefone_str));
                    etTelefone.setText(Utilitario.TELSEMDDD(laudo.Telefone_str));
                    etSetor.setText(Utilitario.TELSEMDDD(laudo.Setor_str));
                    etContato.setText(Utilitario.TELSEMDDD(laudo.Contato_str));
                }

                PopulaAcessorio();
                etMetodologia.setText("VISUAL SENSITIVA / DIMENSIONAL");
                /*
                etFab.setText("TESTEFABRI");
                etCapacidade.setText("TESTE CAPACIDADE");
                etContato.setText("TESTE CONTATO");
                etDDD.setText("13");
                etTelefone.setText("997421148");
                etTAG.setText("TESTE TAG");
                etSetor.setText("MANUTENÇÃO");
                etModelo.setText("TESTE MODELO");
                etDimensao.setText("TESTE DIMENSAO");

                etCertFab.setText("TESTE CERT FAB");
                etRegInsp.setText("TESTE REG INSP");
                etRegRep.setText("REG RESP");
                etRecomendacao.setText("VERIFICAR A");
                etObservacao.setText("NÃO É POSSÍVEL");
                etObservacao2.setText("OBS02");
                */

            }else{
                PopulaAcessorio();
                blnEdit = true;
                if(pedido.laudo.LAUDOID > 0){
                    for(int i = 0; i < pedido.AcessorioList.size();i++){
                        if(pedido.AcessorioList.get(i).Acessorio_str.equals(pedido.laudo.Acessorio_str)){
                            spiAcessorio.setSelection(i);
                            break;
                        }
                    }

                    for(int i = 0; i < vConcTec.length;i++){
                        if(pedido.laudo.ConclusaoTecnica_str.equals(vConcTec[i])){
                            spiConcTec.setSelection(i);
                            break;
                        }
                    }

                    etEvidencia1.setText(pedido.laudo.Caminhoevidenciafotograficaum_str);
                    etEvidencia2.setText(pedido.laudo.Caminhoevidenciafotograficadois_str);
                    etEvidencia3.setText(pedido.laudo.Caminhoevidenciafotograficatres_str);
                    etEvidencia4.setText(pedido.laudo.Caminhoevidenciafotograficaquatro_str);

                    ProximoPasso();

                    etFab.setText(pedido.laudo.Fabricante_str);
                    etCapacidade.setText(pedido.laudo.Capacidade_str);
                    etContato.setText(pedido.laudo.Contato_str);
                    etDDD.setText(Utilitario.TELDDD(pedido.laudo.Telefone_str));
                    etTelefone.setText(Utilitario.TELSEMDDD(pedido.laudo.Telefone_str));
                    etTAG.setText(pedido.laudo.Tag_str);
                    etSetor.setText(pedido.laudo.Setor_str);
                    etModelo.setText(pedido.laudo.Modelo_str);
                    etDimensao.setText(pedido.laudo.Dimensoes_str);
                    etMetodologia.setText(pedido.laudo.Metodologiainspecao_str);
                    etCertFab.setText(pedido.laudo.Certificadofabricante_str);
                    etRegInsp.setText(pedido.laudo.Registroinspecao_str);
                    etRegRep.setText(pedido.laudo.Registroreparo_str);
                    etRecomendacao.setText(pedido.laudo.Recomendacoes_str);
                    etObservacao.setText(pedido.laudo.DemaisInformacoesAcessorios_str);
                    etObservacao2.setText(pedido.laudo.DemaisInformacoesEmpresa_str);

                    tvTitGravar.setText(getString(R.string.btnGRAVAR));
                }
            }

        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        AjustaActionBar();

        spiAcessorio.requestFocus();
    }

    private void PopulaAcessorio(){
        if(pedido.AcessorioList == null)
            pedido.AcessorioList = new ArrayList<>();

        if(pedido.AcessorioList.size() > 0){
            if(pedido.AcessorioList.get(0).Idmontaacessorios_int != 0){
                TBACESSORIO acessorio = new TBACESSORIO();
                acessorio.Acessorio_str = "";
                acessorio.Idmontaacessorios_int = 0;
                pedido.AcessorioList.add(0,acessorio);
            }
        }

        vAcessorio = new String[pedido.AcessorioList.size()];
        adpAcessorio = new adapterAcessorio(this, R.layout.spinner_custom_filter, vAcessorio);
        spiAcessorio.setAdapter(adpAcessorio);
    }

    private void ProximoPasso(){
        llPasso1.setVisibility(View.VISIBLE);
        llPasso2.setVisibility(View.VISIBLE);
        sivCameraEv01.setIconEnabled(false);
        sivGaleriaEv01.setIconEnabled(false);
        sivCameraEv02.setIconEnabled(false);
        sivGaleriaEv02.setIconEnabled(false);
        sivCameraEv03.setIconEnabled(false);
        sivGaleriaEv03.setIconEnabled(false);
        sivCameraEv04.setIconEnabled(false);
        sivGaleriaEv04.setIconEnabled(false);
        tvTitGravar.setText("GRAVAR");
    }

    private void SendImage(){
        if(intUpload == 1 && vImage1 != null){
            if(intCountPart == 1){
                UploadImage("",pedido.laudo.Caminhoevidenciafotograficaum_str,3);
            }else{
                String dataFoto = JuntarFoto(vImage1);
                UploadImage(dataFoto,pedido.laudo.Caminhoevidenciafotograficaum_str,1);
            }

        }else if(intUpload == 2 && vImage2 != null){

            if(intCountPart == 1){
                UploadImage("",pedido.laudo.Caminhoevidenciafotograficadois_str,3);
            }else{
                String dataFoto = JuntarFoto(vImage2);
                UploadImage(dataFoto,pedido.laudo.Caminhoevidenciafotograficadois_str,1);
            }

        }else if(intUpload == 3 && vImage3 != null){

            if(intCountPart == 1){
                UploadImage("",pedido.laudo.Caminhoevidenciafotograficatres_str,3);
            }else{
                String dataFoto = JuntarFoto(vImage3);
                UploadImage(dataFoto,pedido.laudo.Caminhoevidenciafotograficatres_str,1);
            }

        }else if(intUpload == 4 && vImage4 != null){

            if(intCountPart == 1){
                UploadImage("",pedido.laudo.Caminhoevidenciafotograficaquatro_str,3);
            }else{
                String dataFoto = JuntarFoto(vImage4);
                UploadImage(dataFoto,pedido.laudo.Caminhoevidenciafotograficaquatro_str,1);
            }

        }else{
            intCountPart = 0;
            intUpload = 0;
            SendOS();
        }
    }

    private void Gravar(){
        TBLAUDORepository repository = new TBLAUDORepository(cContext);

        try{
            pedido.laudo.Id_int = pedido.Id_int;
            pedido.laudo.Id_int_fk = pedido.Id_int_fk;
            pedido.laudo.Id_int_fk_2 = pedido.Id_int_fk_2;
            pedido.laudo.Fabricante_str= etFab.getText().toString();
            pedido.laudo.Capacidade_str= etCapacidade.getText().toString();
            pedido.laudo.Contato_str= etContato.getText().toString();
            pedido.laudo.NumeroArt_str = pedido.NumeroArt_str;
            pedido.laudo.Telefone_str= etDDD.getText().toString() + etTelefone.getText().toString().replace("-","");
            pedido.laudo.Tag_str= etTAG.getText().toString();
            pedido.laudo.Setor_str= etSetor.getText().toString();
            pedido.laudo.Modelo_str= etModelo.getText().toString();
            pedido.laudo.Dimensoes_str= etDimensao.getText().toString();
            pedido.laudo.Metodologiainspecao_str= etMetodologia.getText().toString();
            pedido.laudo.Certificadofabricante_str= etCertFab.getText().toString();
            pedido.laudo.Registroinspecao_str= etRegInsp.getText().toString();
            pedido.laudo.Registroreparo_str= etRegRep.getText().toString();
            if(etRecomendacao.getText().toString().length() == 0)
                etRecomendacao.setText("N.A.");
            pedido.laudo.Recomendacoes_str= etRecomendacao.getText().toString();
            if(etObservacao.getText().toString().length() == 0)
                etObservacao.setText("N.A.");
            pedido.laudo.DemaisInformacoesAcessorios_str= etObservacao.getText().toString();
            pedido.laudo.Caminhoevidenciafotograficaum_str= etEvidencia1.getText().toString();
            pedido.laudo.Caminhoevidenciafotograficadois_str= etEvidencia2.getText().toString();
            pedido.laudo.Caminhoevidenciafotograficatres_str= etEvidencia3.getText().toString();
            pedido.laudo.Caminhoevidenciafotograficaquatro_str= etEvidencia4.getText().toString();
            if(etObservacao2.getText().toString().length() == 0)
                etObservacao2.setText("N.A.");
            pedido.laudo.DemaisInformacoesEmpresa_str= etObservacao2.getText().toString();

            if(pedido.laudo.LAUDOID == 0){
                repository.Insert(pedido.laudo);
            }else{
                repository.Update(pedido.laudo);
            }

            if (repository.DescricaoErro.length() > 0)
                throw new Exception("Erro: " + repository.DescricaoErro);

            if(Utilitario.WifiOn(acMain)){
                dalProgress = Utilitario.openProgress(cContext);
                dalProgress.show();
                ValidaImagem();
                intUpload = 1;
                SendImage();
            }
            else{
                strLOG += "Laudo --> gravado na base local com sucesso.";
                MostraResultado();
            }



        }catch(Exception ex){
            ex.printStackTrace();
            MostraErroBar("Erro ao tentar gravar o Laudo.","");
            return;
        }
    }

    private void ValidaImagem(){
        try{
            if(pedido.laudo.Caminhoevidenciafotograficaum_str.length() > 0 && vImage1 == null)
                vImage1 = Utilitario.BitmapToString(pedido.laudo.Caminhoevidenciafotograficaum_str,cContext);
            if(pedido.laudo.Caminhoevidenciafotograficadois_str.length() > 0)
                vImage2 = Utilitario.BitmapToString(pedido.laudo.Caminhoevidenciafotograficadois_str,cContext);
            if(pedido.laudo.Caminhoevidenciafotograficatres_str.length() > 0)
                vImage3 = Utilitario.BitmapToString(pedido.laudo.Caminhoevidenciafotograficatres_str,cContext);
            if(pedido.laudo.Caminhoevidenciafotograficaquatro_str.length() > 0)
                vImage4 = Utilitario.BitmapToString(pedido.laudo.Caminhoevidenciafotograficaquatro_str,cContext);

            vImage1 = Utilitario.BitmapToString(pedido.laudo.Caminhoevidenciafotograficaum_str,cContext);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void OpenDetalhe(){
        Intent minhaAcao2 = new Intent(acMain, Laudo.class);
        minhaAcao2.putExtra("STBPEDIDO", pedido);
        acMain.startActivity(minhaAcao2);
        acMain.finish();
    }

    private void MostraErroBar(String pMENSAGEM, String pCAPTION){
        flashbar = Utilitario.MensagemErroFbTitulo(acMain,false,pCAPTION.length() == 0 ? strCAPTION : pCAPTION,pMENSAGEM,R.drawable.ico_laudo);
        flashbar.show();
    }

    private void MostraErroSaiBar(String pMENSAGEM){
        flashbar = new Flashbar.Builder(acMain)
                .gravity(Flashbar.Gravity.TOP)
                .title(strCAPTION)
                .message(pMENSAGEM)
                .backgroundColorRes(R.color.dialogError)
                .showIcon(1.0f, ImageView.ScaleType.FIT_START)
                .icon(R.drawable.ico_laudo)
                .iconColorFilterRes(R.color.branco)
                .showOverlay()
                .positiveActionText(getString(R.string.btnOK))
                .positiveActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar bar) {
                        bar.dismiss();
                        acMain.finish();
                    }
                })
                .overlayBlockable()
                .build();
        flashbar.show();
    }

    private void ValidaMostraErro(EditText pEditText, TextInputLayout pTextInputLayout, boolean pOK){
        try{
            if(pEditText.getText().length() == 0){
                pTextInputLayout.setError(getString(R.string.erroCampoObrigatorio));
                pOK = false;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private boolean Validacao(){
        boolean blnOK = true;

        try{
            ValidaMostraErro(etTAG,tilTAG,blnOK);
            ValidaMostraErro(etFab,tilFab,blnOK);
            ValidaMostraErro(etModelo,tilModelo,blnOK);
            ValidaMostraErro(etCapacidade,tilCapacidade,blnOK);
            ValidaMostraErro(etDimensao,tilDimensao,blnOK);
            //ValidaMostraErro(etObservacao,tilObservacao,blnOK);
            ValidaMostraErro(etContato,tilContato,blnOK);
            ValidaMostraErro(etSetor,tilSetor,blnOK);
            //ValidaMostraErro(etObservacao2,tilObservacao2,blnOK);
            ValidaMostraErro(etMetodologia,tilMetodologia,blnOK);
            ValidaMostraErro(etCertFab,tilCertFab,blnOK);
            ValidaMostraErro(etRegInsp,tilRegInsp,blnOK);
            ValidaMostraErro(etRegRep,tilRegRep,blnOK);
            //ValidaMostraErro(etRecomendacao,tilRecomendacao,blnOK);

            if(etDDD.getText().length() == 1){
                tilDDD.setError(getString(R.string.erroObrigatorio));
                blnOK = false;
            }

            if(etTelefone.getText().length() > 0 && etTelefone.getText().length() < 9){
                tilTelefone.setError(getString(R.string.erroCampoInvalido));
                blnOK = false;
            }

            if(pedido.laudo.Acessorio_str.length() == 0){
                tvAcessorioErro.setVisibility(View.VISIBLE);
                blnOK = false;
            }

            if(pedido.laudo.ConclusaoTecnica_str.length() == 0){
                tvConcTecErro.setVisibility(View.VISIBLE);
                blnOK = false;
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

        return blnOK;
    }

    public class adapterConcTec extends ArrayAdapter<String> {

        public adapterConcTec(Context ctx, int txtViewResourceId, String[] objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        public void setError(View v, CharSequence s) {
            if(s.equals(""))
                tvConcTecErro.setVisibility(View.GONE);
            else
                tvConcTecErro.setVisibility(View.VISIBLE);
        }

        @Override public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.spinner_custom_filter, parent, false);
            TextView main_text = (TextView) mySpinner.findViewById(R.id.SpinnerCustomTvDescricao);
            main_text.setText(vConcTec[position].toUpperCase());
            return mySpinner;
        }
    }

    public class adapterAcessorio extends ArrayAdapter<String> {

        public adapterAcessorio(Context ctx, int txtViewResourceId, String[] objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        public void setError(View v, CharSequence s) {
            if(s.equals(""))
                tvAcessorioErro.setVisibility(View.GONE);
            else
                tvAcessorioErro.setVisibility(View.VISIBLE);
        }

        @Override public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.spinner_custom_filter, parent, false);
            TextView main_text = (TextView) mySpinner.findViewById(R.id.SpinnerCustomTvDescricao);
            main_text.setText(pedido.AcessorioList.get(position).Acessorio_str.toUpperCase());
            return mySpinner;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (lifeCycleCallBackManager != null) {
            lifeCycleCallBackManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (lifeCycleCallBackManager != null) {
            lifeCycleCallBackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void GeraImagem(){
        dalProgress = Utilitario.openProgress(cContext);
        dalProgress.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                String[] vImage;
                File root = new File(Environment.getExternalStorageDirectory(), getString(R.string.dirTMP));
                root.mkdirs();

                String[] vFile = strFileUriImage.split("/");

                File imageFile = new File(strFileUriImage);

                BitmapFactory.Options opts = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(strFileUriImage, opts);

                if(bitmap.getHeight() > bitmap.getWidth()){
                    String strTitulo = "";
                    switch (intEvidencia){
                        case 1:
                            strTitulo = getString(R.string.hintEvidencia01).toUpperCase();
                            break;

                        case 2:
                            strTitulo = getString(R.string.hintEvidencia02).toUpperCase();
                            break;

                        case 3:
                            strTitulo = getString(R.string.hintEvidencia03).toUpperCase();
                            break;

                        case 4:
                            strTitulo = getString(R.string.hintEvidencia04).toUpperCase();
                            break;
                    }
                    dalProgress.dismiss();
                    MostraErroBar(getString(R.string.erroImagemHorizontal),strTitulo);
                    return;
                }

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat mdformat = new SimpleDateFormat("ddmmyyyy_hhmmss");
                String strFile = mdformat.format(calendar.getTime()) + "_" + vFile[vFile.length - 1];

                File file = new File(root, strFile);

                try {
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    Bitmap bmTeste = bitmap;
                    out.flush();
                    out.close();

                    switch (intEvidencia){
                        case 1:
                            vImage1 = Utilitario.BitmapToString(bitmap);
                            pedido.laudo.Caminhoevidenciafotograficaum_str = strFile;
                            etEvidencia1.setText(strFile);
                            break;

                        case 2:
                            vImage2 = Utilitario.BitmapToString(bitmap);
                            pedido.laudo.Caminhoevidenciafotograficadois_str = strFile;
                            etEvidencia2.setText(strFile);
                            break;

                        case 3:
                            vImage3 = Utilitario.BitmapToString(bitmap);
                            pedido.laudo.Caminhoevidenciafotograficatres_str = strFile;
                            etEvidencia3.setText(strFile);
                            break;

                        case 4:
                            vImage4 = Utilitario.BitmapToString(bitmap);
                            pedido.laudo.Caminhoevidenciafotograficaquatro_str = strFile;
                            etEvidencia4.setText(strFile);
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dalProgress.dismiss();
            }
        }, 1000);
    }

    private FilePickUtils.OnFileChoose onFileChoose = new FilePickUtils.OnFileChoose() {
        @Override public void onFileChoose(String fileUri, int requestCode) {
            strFileUriImage = fileUri;
            GeraImagem();
        }
    };

    private void SendOS(){
        try{
            pedido.laudo.NumeroArt_str = pedido.NumeroArt_str;
            GitHubService.ServiceSENDOS execute = GitHubService.ServiceSENDOS.retrofit.create(GitHubService.ServiceSENDOS.class);
            final Call<TBLAUDO> call =  execute.Exec(pedido.laudo.JSON(), Main.config.USERNAME,"Bearer " + Main.config.TOKEN);
            call.enqueue(new Callback<TBLAUDO>() {
                @Override
                public void onResponse(Call<TBLAUDO> call, Response<TBLAUDO> response) {
                    if(response.code() != 200){
                        strLOG += "Laudo --> Não será possível cadastrar o Laudo devido a problemas no servidor.";
                    }else{
                        if(response.body() == null){
                            strLOG += "Laudo --> Não será possível cadastrar o Laudo devido a problemas no servidor.";
                        }else  if(response.body().MSGSTATUS == CONSTANT.kdErro){
                            strLOG += "Laudo --> Erro ao tentar enviar a OS.\n" + response.body().DESCRICAO;
                        }else if(response.body().MSGSTATUS != CONSTANT.kdNormal){
                            strLOG += "Laudo --> Não será possível cadastrar o Laudo devido a problemas no servidor.";
                        }else{
                            response.body().SYNC = 1;
                            response.body().LAUDOID = pedido.laudo.LAUDOID;
                            TBLAUDORepository repository = new TBLAUDORepository(cContext);
                            repository.UpdateAdd(response.body());
                            if(blnEdit)
                                strLOG += "Laudo --> Atualizado e enviado com sucesso.";
                            else
                                strLOG += "Laudo --> Enviado com sucesso.";
                        }
                    }
                    MostraResultado();
                }
                @Override
                public void onFailure(Call<TBLAUDO> call, Throwable t) {
                    dalProgress.dismiss();
                    strLOG += "Laudo --> Não será possível cadastrar o Laudo devido a problemas no servidor.";
                    MostraResultado();
                }
            });

        }catch (Exception ex){
            dalProgress.dismiss();
            ex.printStackTrace();
            MostraErroBar("Não será possível cadastrar o Laudo devido a problemas no servidor.","");
        }
    }

    private void UploadImage(String pIMAGESTR, String pIMAGENAME, final int pSTATUS){
        try{

            Map<String, String> data = new HashMap<>();
            data.put("image", pIMAGESTR);
            data.put("imagename", pIMAGENAME);
            data.put("status", String.valueOf(pSTATUS));

            GitHubService.ServiceUPLOADIMAGE execute = GitHubService.ServiceUPLOADIMAGE.retrofit.create(GitHubService.ServiceUPLOADIMAGE.class);
            final Call<MENSAGEM> call =  execute.Exec(data,"Bearer " + Main.config.TOKEN);
            call.enqueue(new Callback<MENSAGEM>() {
                @Override
                public void onResponse(Call<MENSAGEM> call, Response<MENSAGEM> response) {
                    if(response.code() != 200){
                        strLOG += "Imagem 0" + intUpload + " --> Erro ao enviar a imagem, problemas no servidor.\n";
                    }else{
                        if(response.body() == null){
                            strLOG += "Imagem 0" + intUpload + " --> Erro ao enviar a imagem, problemas no servidor.\n";
                        }else  if(response.body().MSGSTATUS == CONSTANT.kdErro){
                            strLOG += "Imagem 0" + intUpload + " --> Erro ao enviar a imagem, " + response.body().DESCRICAO + "\n";
                        }else{
                            if(pSTATUS == 3){
                                UpdateStatusImage();
                                intCountPart = 0;
                                intUpload++;
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
                    MostraErroBar("Não será possível cadastrar o Laudo devido a problemas no servidor.","");
                }
            });

        }catch (Exception ex){
            dalProgress.dismiss();
            ex.printStackTrace();
            MostraErroBar("Não será possível cadastrar o Laudo devido a problemas no servidor.","");
        }
    }

    private void UpdateStatusImage(){
        TBLAUDORepository repository = new TBLAUDORepository(cContext);
        try{

            switch (intUpload){
                case 1:
                    pedido.laudo.Evidenciafotograficaum_sync = repository.UpdateStatusImage(pedido.laudo.LAUDOID,"Evidenciafotograficaum_sync", 1);
                    if(repository.DescricaoErro.length() > 0){
                        strLOG += "Imagem 01 --> Erro ao tentar enviar a imagem.\n";
                    }else{
                        strLOG += "Imagem 01 --> Enviada com sucesso.\n";
                    }
                    break;

                case 2:
                    pedido.laudo.Evidenciafotograficadois_sync = repository.UpdateStatusImage(pedido.laudo.LAUDOID,"Evidenciafotograficadois_sync", 1);
                    if(repository.DescricaoErro.length() > 0){
                        strLOG += "Imagem 02 --> Erro ao tentar enviar a imagem.\n";
                    }else{
                        strLOG += "Imagem 02 --> Enviada com sucesso.\n";
                    }
                    break;

                case 3:
                    pedido.laudo.Evidenciafotograficatres_sync = repository.UpdateStatusImage(pedido.laudo.LAUDOID,"Evidenciafotograficatres_sync", 1);
                    if(repository.DescricaoErro.length() > 0){
                        strLOG += "Imagem 03 --> Erro ao tentar enviar a imagem.\n";
                    }else{
                        strLOG += "Imagem 03 --> Enviada com sucesso.\n";
                    }
                    break;

                case 4:
                    pedido.laudo.Evidenciafotograficaquatro_sync = repository.UpdateStatusImage(pedido.laudo.LAUDOID,"Evidenciafotograficaquatro_sync", 1);
                    if(repository.DescricaoErro.length() > 0){
                        strLOG += "Imagem 04 --> Erro ao tentar enviar a imagem.\n";
                    }else{
                        strLOG += "Imagem 04 --> Enviada com sucesso.\n";
                    }
                    break;
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void MostraResultado(){
        if(dalProgress != null)
            dalProgress.dismiss();

        if(blnEdit){
            flashbar = new Flashbar.Builder(acMain)
                    .gravity(Flashbar.Gravity.TOP)
                    .title("LAUDO")
                    .message(strLOG)
                    .backgroundColorRes(R.color.dialogInfo)
                    .showIcon(1.0f, ImageView.ScaleType.FIT_START)
                    .icon(R.drawable.ico_laudo)
                    .iconColorFilterRes(R.color.branco)
                    .showOverlay()
                    .positiveActionText(acMain.getString(R.string.btnOK))
                    .positiveActionTapListener(new Flashbar.OnActionTapListener() {
                        @Override
                        public void onActionTapped(@NotNull Flashbar bar) {
                            bar.dismiss();
                            if(blnEdit){
                                Laudo.Refresh();
                                Toast.makeText(getApplicationContext(), "Laudo atualizado.", Toast.LENGTH_SHORT).show();
                                acMain.finish();
                            }else{
                                OpenDetalhe();
                            }

                        }
                    })
                    .overlayBlockable()
                    .build();
            flashbar.show();
        }else{
            flashbar = new Flashbar.Builder(acMain)
                    .gravity(Flashbar.Gravity.TOP)
                    .title("LAUDO")
                    .message(strLOG + "\n" + "Gostaria de inseir outro Registro?")
                    .backgroundColorRes(R.color.dialogInfo)
                    .showIcon(1.0f, ImageView.ScaleType.FIT_START)
                    .icon(R.drawable.ico_laudo)
                    .iconColorFilterRes(R.color.branco)
                    .showOverlay()
                    .positiveActionText("SIM")
                    .negativeActionText("NÃO")
                    .positiveActionTapListener(new Flashbar.OnActionTapListener() {
                        @Override
                        public void onActionTapped(@NotNull Flashbar bar) {
                            bar.dismiss();
                            Intent minhaAcao2 = new Intent(acMain, LaudoEdit.class);
                            pedido.laudo = null;
                            minhaAcao2.putExtra("STBPEDIDO", pedido);
                            acMain.startActivity(minhaAcao2);
                            acMain.finish();
                        }
                    })
                    .negativeActionTapListener(new Flashbar.OnActionTapListener() {
                        @Override
                        public void onActionTapped(@NotNull Flashbar bar) {
                            bar.dismiss();
                            OpenDetalhe();
                        }
                    })
                    .overlayBlockable()
                    .build();
            flashbar.show();
        }


    }

    private void AjustaActionBar(){
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayUseLogoEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.img_seta);

        getSupportActionBar().setElevation(0);

        Typeface tfTituloBold = Typeface.createFromAsset(getAssets(), getString(R.string.fontTituloBold));

        SpannableStringBuilder SS = new SpannableStringBuilder("LAUDO");
        SS.setSpan(new CustomTypefaceSpan("LAUDO", tfTituloBold), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        supportActionBar.setTitle(SS);

        Spannable text = new SpannableString("LAUDO");
        text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        supportActionBar.setTitle(text);

    }

    private String JuntarFoto(String[] data){
        String retorno = "";
        for(int i = 0; i < data.length; i++){
            retorno+= data[i];
        }

        return retorno;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit, menu);

        mnuToolbar = menu;

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();

                break;

            default:break;
        }

        return true;
    }
}
