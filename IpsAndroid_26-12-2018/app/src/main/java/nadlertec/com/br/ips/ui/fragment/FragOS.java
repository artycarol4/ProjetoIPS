package nadlertec.com.br.ips.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrognito.flashbar.Flashbar;

import java.util.ArrayList;
import java.util.List;

import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.cardview.OSCardViewAdapter;
import nadlertec.com.br.ips.model.TBOS;
import nadlertec.com.br.ips.repository.TBOSRepository;
import nadlertec.com.br.ips.setting.OnItemClickListener;
import nadlertec.com.br.ips.ui.activity.filter.FilterOS;
import nadlertec.com.br.ips.ui.activity.Laudo;
import nadlertec.com.br.ips.ui.activity.LaudoEdit;
import nadlertec.com.br.ips.util.Utilitario;

public class FragOS extends Fragment {
    Dialog dalProgress;
    private Flashbar flashbar = null;
    private static Activity acMain;
    private static Context cContext;
    public String strCAPTION = "LAUDOS";

    private static List<TBOS> lstDado;
    static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    static RecyclerView.Adapter mAdapter;
    private FloatingActionButton fabFilter;
    private static int intINDEX = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.frag_os, container, false);

        acMain = getActivity();
        cContext = getContext();

        mRecyclerView = (RecyclerView)rootView.findViewById(R.id.FragLaudoRvMain);
        mRecyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        fabFilter = (FloatingActionButton) rootView.findViewById(R.id.FragLaudoFabSearch);
        fabFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent minhaAcao = new Intent(acMain, FilterOS.class);
            startActivity(minhaAcao);
            }
        });

        TBOSRepository repository = new TBOSRepository(cContext);
        lstDado = repository.List();
        if(repository.DescricaoErro.length() > 0)
            lstDado = new ArrayList<>();
        mAdapter = new OSCardViewAdapter(lstDado,getContext(),onItemClickCallback);
        mRecyclerView.setAdapter(mAdapter);
        return rootView;
    }

    public static void Pesquisa(TBOS pTBOS){
        TBOSRepository repository = new TBOSRepository(cContext);
        try{
            lstDado = repository.Search(pTBOS);
            if(lstDado == null)
                lstDado = new ArrayList<>();

            mAdapter = new OSCardViewAdapter(lstDado,cContext,onItemClickCallback);
            mRecyclerView.setAdapter(mAdapter);


        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void MostraErroBar(String pMENSAGEM){
        flashbar = Utilitario.MensagemErroFbTitulo(acMain,false,strCAPTION,pMENSAGEM,R.drawable.ico_home);
        flashbar.show();
    }

    static private OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            final int id = view.getId();
            intINDEX = position;

            switch (id) {
                case R.id.CardViewOSLlAdd:
                    Intent minhaAcao = new Intent(acMain, LaudoEdit.class);
                    minhaAcao.putExtra("STBPEDIDO", lstDado.get(intINDEX));
                    acMain.startActivity(minhaAcao);
                    break;

                case R.id.CardViewOSLlDetalhe:
                    Intent minhaAcao2 = new Intent(acMain, Laudo.class);
                    minhaAcao2.putExtra("STBPEDIDO", lstDado.get(intINDEX));
                    acMain.startActivity(minhaAcao2);
                    break;
            }
        }
    };
}