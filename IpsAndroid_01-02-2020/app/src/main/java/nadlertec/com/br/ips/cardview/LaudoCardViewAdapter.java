package nadlertec.com.br.ips.cardview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.model.TBLAUDO;
import nadlertec.com.br.ips.setting.OnItemClickListener;
import nadlertec.com.br.ips.util.Utilitario;

public class LaudoCardViewAdapter extends RecyclerView.Adapter<LaudoCardViewAdapter.ViewHolder> {
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;
    private Context context;
    private List<TBLAUDO> items;

    public LaudoCardViewAdapter(List<TBLAUDO> Dado, Context context, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.context = context;
        this.items = Dado;
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setItems(List<TBLAUDO> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llResultado;
        private TextView tvResultado;
        private TextView tvLaudo;
        private TextView tvNArt;
        private TextView tvAcessorio;
        private TextView tvTAG;
        private TextView tvFabricante;
        private ImageView ivEditar;
        private TextView tvEditar;
        private TextView tvDetalhe;
        private ImageView ivDetalhe;
        private TextView tvDeletar;
        private ImageView ivDeletar;
        private LinearLayout llDetalhe;
        private TextView tvModelo;
        private TextView tvCapacidade;
        private TextView tvDimensao;
        private TextView tvObsAcess;
        private TextView tvCNPJ;
        private TextView tvEmpresa;
        private TextView tvConatato;
        private TextView tvSetor;
        private TextView tvTelefone;
        private TextView tvObsEmpresa;
        private TextView tvDtMetodologia;
        private TextView tvCertFabricante;
        private TextView tvRegInsp;
        private TextView tvRegReparo;
        private TextView tvRecomendacao;


        public ViewHolder(View itemView) {
            super(itemView);

            this.llResultado = (LinearLayout) itemView.findViewById(R.id.CardViewLaudoLlResultado);
            this.tvResultado = (TextView) itemView.findViewById(R.id.CardViewLaudoTvResultado);
            this.tvLaudo = (TextView) itemView.findViewById(R.id.CardViewLaudoTvLaudo);
            this.tvNArt = (TextView) itemView.findViewById(R.id.CardViewLaudoTvNArt);
            this.tvAcessorio = (TextView) itemView.findViewById(R.id.CardViewLaudoTvAcessorio);
            this.tvTAG = (TextView) itemView.findViewById(R.id.CardViewLaudoTvTAG);
            this.tvFabricante = (TextView) itemView.findViewById(R.id.CardViewLaudoTvFab);
            this.ivEditar = (ImageView) itemView.findViewById(R.id.CardViewLaudoIvEditar);
            this.tvEditar = (TextView) itemView.findViewById(R.id.CardViewLaudoTvEditar);
            this.tvDetalhe = (TextView) itemView.findViewById(R.id.CardViewLaudoTvTitDetalhe);
            this.ivDetalhe = (ImageView) itemView.findViewById(R.id.CardViewLaudoIvDetalhe);
            this.tvDeletar = (TextView) itemView.findViewById(R.id.CardViewLaudoTvDeletar);
            this.ivDeletar = (ImageView) itemView.findViewById(R.id.CardViewLaudoIvDeletar);
            this.llDetalhe = (LinearLayout) itemView.findViewById(R.id.CardViewLaudoLlDetalhe);
            this.tvModelo = (TextView) itemView.findViewById(R.id.CardViewLaudoTvModelo);
            this.tvCapacidade = (TextView) itemView.findViewById(R.id.CardViewLaudoTvCapacidade);
            this.tvDimensao = (TextView) itemView.findViewById(R.id.CardViewLaudoTvDimensao);
            this.tvObsAcess = (TextView) itemView.findViewById(R.id.CardViewLaudoTvObsAcess);
            this.tvCNPJ = (TextView) itemView.findViewById(R.id.CardViewLaudoTvCNPJ);
            this.tvEmpresa = (TextView) itemView.findViewById(R.id.CardViewLaudoTvEmpresa);
            this.tvConatato = (TextView) itemView.findViewById(R.id.CardViewLaudoTvContato);
            this.tvSetor = (TextView) itemView.findViewById(R.id.CardViewLaudoTvSetor);
            this.tvTelefone = (TextView) itemView.findViewById(R.id.CardViewLaudoTvTelefone);
            this.tvObsEmpresa = (TextView) itemView.findViewById(R.id.CardViewLaudoTvObsEmpresa);
            this.tvDtMetodologia = (TextView) itemView.findViewById(R.id.CardViewLaudoTvDtMet);
            this.tvCertFabricante = (TextView) itemView.findViewById(R.id.CardViewLaudoTvCertFab);
            this.tvRegInsp = (TextView) itemView.findViewById(R.id.CardViewLaudoTvRegInsp);
            this.tvRegReparo = (TextView) itemView.findViewById(R.id.CardViewLaudoTvRegReparo);
            this.tvRecomendacao = (TextView) itemView.findViewById(R.id.CardViewLaudoTvRecomendacao);
        }

        public void bind(final TBLAUDO pMODEL, final int pIndex) {

            llDetalhe.setVisibility(pMODEL.DETALHE == 0 ? View.GONE : View.VISIBLE);
            ivDetalhe.setImageResource(pMODEL.DETALHE == 0 ? R.drawable.ico_seta_down : R.drawable.ico_seta_open);

            if(pMODEL.ConclusaoTecnica_str.equals("Aprovado 3 meses")){
                Utilitario.setDrawable(context, R.drawable.fundo_cv_laudo_verde, llResultado);
            }else if(pMODEL.ConclusaoTecnica_str.equals("Aprovado 6 meses")){
                Utilitario.setDrawable(context, R.drawable.fundo_cv_laudo_verde, llResultado);
            }else if(pMODEL.ConclusaoTecnica_str.equals("Aprovado 12 meses")){
                Utilitario.setDrawable(context, R.drawable.fundo_cv_laudo_verde, llResultado);
            }else if(pMODEL.ConclusaoTecnica_str.equals("Reprovado/Recomendações")){
                Utilitario.setDrawable(context, R.drawable.fundo_cv_laudo_amarelo, llResultado);
            }else if(pMODEL.ConclusaoTecnica_str.equals("Reprovado")){
                Utilitario.setDrawable(context, R.drawable.fundo_cv_laudo_vermelho, llResultado);
            }

            this.tvResultado.setText(pMODEL.ConclusaoTecnica_str);
            this.tvLaudo.setText(pMODEL.Laudo_str);
            this.tvNArt.setText(pMODEL.NumeroArt_str);
            this.tvAcessorio.setText(pMODEL.Acessorio_str);
            this.tvTAG.setText(pMODEL.Tag_str);
            this.tvFabricante.setText(pMODEL.Fabricante_str);
            this.tvModelo.setText(pMODEL.Modelo_str);
            this.tvCapacidade.setText(pMODEL.Capacidade_str);
            this.tvDimensao.setText(pMODEL.Dimensoes_str);
            this.tvObsAcess.setText(pMODEL.DemaisInformacoesAcessorios_str);
            this.tvCNPJ.setText(Utilitario.FormataCNPJ(pMODEL.Cnpj_str));
            this.tvEmpresa.setText(pMODEL.Empresa_str);
            this.tvConatato.setText(pMODEL.Contato_str);
            this.tvSetor.setText(pMODEL.Setor_str);
            this.tvTelefone.setText(Utilitario.FormataTEL(pMODEL.Telefone_str));
            this.tvObsEmpresa.setText(pMODEL.DemaisInformacoesEmpresa_str);//????
            this.tvDtMetodologia.setText(pMODEL.Datametodologiainspecao_str);
            this.tvCertFabricante.setText(pMODEL.Certificadofabricante_str);
            this.tvRegInsp.setText(pMODEL.Registroinspecao_str);
            this.tvRegReparo.setText(pMODEL.Registroreparo_str);
            this.tvRecomendacao.setText(pMODEL.Recomendacoes_str);

            this.tvEditar.setVisibility(pMODEL.SYNC == 0 ? View.VISIBLE : itemView.GONE);
            this.ivEditar.setVisibility(pMODEL.SYNC == 0 ? View.VISIBLE : itemView.GONE);

            this.tvDetalhe.setOnClickListener(new OnItemClickListener(pIndex, onItemClickCallback));
            this.tvDeletar.setOnClickListener(new OnItemClickListener(pIndex, onItemClickCallback));
            this.ivDetalhe.setOnClickListener(new OnItemClickListener(pIndex, onItemClickCallback));
            this.ivDeletar.setOnClickListener(new OnItemClickListener(pIndex, onItemClickCallback));
            this.ivEditar.setOnClickListener(new OnItemClickListener(pIndex, onItemClickCallback));
            this.tvEditar.setOnClickListener(new OnItemClickListener(pIndex, onItemClickCallback));

        }

    }

    @Override
    public LaudoCardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(this.context).inflate(R.layout.cardview_laudo, parent, false);
        return new LaudoCardViewAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(LaudoCardViewAdapter.ViewHolder holder, int position) {
        holder.bind(this.items.get(position),position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}