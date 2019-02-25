package nadlertec.com.br.ips.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.andrognito.flashbar.Flashbar;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.cardview.LaudoCardViewAdapter;
import nadlertec.com.br.ips.model.TBLAUDO;
import nadlertec.com.br.ips.model.TBOS;
import nadlertec.com.br.ips.repository.TBLAUDORepository;
import nadlertec.com.br.ips.setting.CustomTypefaceSpan;
import nadlertec.com.br.ips.setting.OnItemClickListener;
import nadlertec.com.br.ips.ui.activity.filter.FilterLaudo;
import nadlertec.com.br.ips.ui.activity.filter.FilterOS;

public class Laudo  extends AppCompatActivity {
    private static Activity acMain;
    private static Context cContext;
    private static List<TBLAUDO> lstDado;
    static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    static RecyclerView.Adapter mAdapter;
    private static int intINDEX = -1;
    private Menu mnuToolbar;
    private static TBOS pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.laudo);

        acMain = this;
        cContext = this;

        mRecyclerView = (RecyclerView)findViewById(R.id.LaudoRvMain);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        Bundle bMessage = getIntent().getExtras();
        if(bMessage != null){
            TBLAUDORepository repository = new TBLAUDORepository(cContext);
            pedido = (TBOS) getIntent().getExtras().get("STBPEDIDO");
            if(pedido != null){
                lstDado = repository.List(pedido.Id_int);
            }
        }

        if(lstDado == null){
            lstDado = new ArrayList<>();
            AvisaSemDado();
        }else if(lstDado.size() == 0){
            AvisaSemDado();
        }

        mAdapter = new LaudoCardViewAdapter(lstDado,this,onItemClickCallback);
        mRecyclerView.setAdapter(mAdapter);

        AjustaActionBar();
    }

    static private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            final int id = view.getId();
            intINDEX = position;

            switch (id) {
                case R.id.CardViewLaudoIvDetalhe:
                    lstDado.get(position).DETALHE = lstDado.get(position).DETALHE == 0 ? 1: 0;
                    mAdapter.notifyDataSetChanged();
                    break;

                case R.id.CardViewLaudoTvTitDetalhe:
                    lstDado.get(position).DETALHE = lstDado.get(position).DETALHE == 0 ? 1: 0;
                    mAdapter.notifyDataSetChanged();
                    break;

                case R.id.CardViewLaudoTvDeletar:
                    ConfirmaDelecao();
                    break;

                case R.id.CardViewLaudoIvDeletar:
                    ConfirmaDelecao();
                    break;

                case R.id.CardViewLaudoTvEditar:
                    Editar();
                    break;

                case R.id.CardViewLaudoIvEditar:
                    Editar();
                    break;
            }
        }
    };

    public static void Pesquisa(TBLAUDO pTBLAUDO){
        TBLAUDORepository repository = new TBLAUDORepository(cContext);
        try{
            lstDado = repository.Search(pTBLAUDO);
            if(lstDado == null)
                lstDado = new ArrayList<>();

            mAdapter = new LaudoCardViewAdapter(lstDado,cContext,onItemClickCallback);
            mRecyclerView.setAdapter(mAdapter);

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private static void Editar(){
        pedido.laudo = lstDado.get(intINDEX);
        Intent minhaAcao = new Intent(acMain, LaudoEdit.class);
        minhaAcao.putExtra("STBPEDIDO", pedido);
        acMain.startActivity(minhaAcao);
    }

    public static void Refresh(){
        TBLAUDORepository repository = new TBLAUDORepository(cContext);
        TBLAUDO laudo = repository.ListByID(lstDado.get(intINDEX).LAUDOID);
        if(laudo != null)
            lstDado.set(intINDEX,laudo);
        mAdapter.notifyDataSetChanged();
    }

    private static  void AvisaSemDado(){
        Flashbar flashbar = new Flashbar.Builder(acMain)
                .gravity(Flashbar.Gravity.TOP)
                .title("LAUDOS")
                .message("Sem informações cadastradas.")
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
                        acMain.finish();
                    }
                })
                .overlayBlockable()
                .build();
        flashbar.show();
    }

    private static  void ConfirmaDelecao(){
        Flashbar flashbar = new Flashbar.Builder(acMain)
                .gravity(Flashbar.Gravity.TOP)
                .title("LAUDO")
                .message("Confirma a deleção do Laudo :" + lstDado.get(intINDEX).Laudo_str)
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
                        Delete();
                    }
                })
                .negativeActionText(acMain.getString(R.string.btnCANCELAR))
                .negativeActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar bar) {
                        bar.dismiss();
                    }
                })
                .overlayBlockable()
                .build();
        flashbar.show();
    }

    private static void Delete(){
        TBLAUDORepository repository = new TBLAUDORepository(cContext);

        try{
            repository.Delete(lstDado.get(intINDEX).LAUDOID);

            if(repository.DescricaoErro.length() > 0)
                throw new Exception(repository.DescricaoErro);

            lstDado.remove(intINDEX);
            mAdapter.notifyDataSetChanged();
            Toast.makeText(acMain, "Laudo deletado com sucesso!!", Toast.LENGTH_LONG).show();

        }catch(Exception ex){
            ex.printStackTrace();
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

        SpannableStringBuilder SS = new SpannableStringBuilder("LAUDOS");
        SS.setSpan(new CustomTypefaceSpan("LAUDOS", tfTituloBold), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        supportActionBar.setTitle(SS);

        Spannable text = new SpannableString("LAUDOS");
        text.setSpan(new ForegroundColorSpan(Color.WHITE), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        supportActionBar.setTitle(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_laudo, menu);

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
