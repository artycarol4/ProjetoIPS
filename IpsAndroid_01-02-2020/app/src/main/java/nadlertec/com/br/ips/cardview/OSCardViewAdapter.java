package nadlertec.com.br.ips.cardview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.model.TBOS;
import nadlertec.com.br.ips.setting.OnItemClickListener;
import nadlertec.com.br.ips.util.Utilitario;

public class OSCardViewAdapter extends RecyclerView.Adapter<OSCardViewAdapter.ViewHolder> {
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;
    private Context context;
    private List<TBOS> items;

    public OSCardViewAdapter(List<TBOS> Dado, Context context, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.context = context;
        this.items = Dado;
        this.onItemClickCallback = onItemClickCallback;
    }

    public void setItems(List<TBOS> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOS;
        private TextView tvCliente;
        private TextView tvInspetor;
        private TextView tvConcluido;
        private LinearLayout llStatus;
        private LinearLayout llAdd;
        private LinearLayout llDetalhe;

        public ViewHolder(View itemView) {
            super(itemView);

            this.tvOS = (TextView) itemView.findViewById(R.id.CardViewOSTvOS);
            this.tvCliente = (TextView) itemView.findViewById(R.id.CardViewOSTvCliente);
            this.tvInspetor = (TextView) itemView.findViewById(R.id.CardViewOSTvInspetor);
            this.tvConcluido = (TextView) itemView.findViewById(R.id.CardViewOSTvConcluido);
            this.llStatus = (LinearLayout) itemView.findViewById(R.id.CardViewOSLlStatus);
            this.llAdd = (LinearLayout) itemView.findViewById(R.id.CardViewOSLlAdd);
            this.llDetalhe = (LinearLayout) itemView.findViewById(R.id.CardViewOSLlDetalhe);
        }

        public void bind(final TBOS pCORE, final int pIndex) {
            this.tvOS.setText(pCORE.NumeroPedido_str);
            this.tvInspetor.setText(pCORE.Inspetor_str);
            this.tvCliente.setText(pCORE.Cliente_str);
            this.tvInspetor.setText(pCORE.Inspetor_str);

            Utilitario.setDrawable(context,pCORE.Finalizado_bool ? R.drawable.fundo_cv_os_verde : R.drawable.fundo_cv_os_red,llStatus);

            this.tvConcluido.setText("CONCLUÍDO : " + (pCORE.Finalizado_bool ? "SIM" : "NÃO"));

            this.llDetalhe.setOnClickListener(new OnItemClickListener(pIndex, onItemClickCallback));
            this.llAdd.setOnClickListener(new OnItemClickListener(pIndex, onItemClickCallback));
            this.llStatus.setOnClickListener(new OnItemClickListener(pIndex, onItemClickCallback));
        }
    }

    @Override
    public OSCardViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(this.context).inflate(R.layout.cardview_os, parent, false);
        return new OSCardViewAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(OSCardViewAdapter.ViewHolder holder, int position) {
        holder.bind(this.items.get(position),position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

}