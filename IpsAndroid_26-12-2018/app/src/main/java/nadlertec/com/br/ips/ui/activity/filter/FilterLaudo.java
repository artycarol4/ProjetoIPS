package nadlertec.com.br.ips.ui.activity.filter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.model.TBLAUDO;
import nadlertec.com.br.ips.ui.activity.Laudo;

public class FilterLaudo  extends AppCompatActivity {
    private static Activity acMain;
    private static Context cContext;

    static String[] vLaudo = null;
    adapterLaudo adpLaudo;
    private static Spinner spiLaudo;
    private String strFilter = "";
    private AutoCompleteTextView actvSearch;

    TBLAUDO laudo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_laudo);

        cContext = this;
        acMain = this;

        laudo = new TBLAUDO();

        vLaudo = getResources().getStringArray(R.array.CampPesqLaudo);
        adpLaudo = new adapterLaudo(this, R.layout.spinner_custom, vLaudo);
        spiLaudo = (Spinner)findViewById(R.id.FilterLaudoSpiLaudo);
        spiLaudo.setAdapter(adpLaudo);
        spiLaudo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int pLaudoicao, long id) {
                strFilter = vLaudo[pLaudoicao];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        LinearLayout llCancel = (LinearLayout) findViewById(R.id.FilterLaudoLlCancelar);
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayout llFiltrar = (LinearLayout) findViewById(R.id.FilterLaudoLlFiltrar);
        llFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(strFilter.equals("LAUDO"))
                    laudo.Laudo_str = actvSearch.getText().toString();
                else if(strFilter.equals("NUMERO ART"))
                    laudo.NumeroArt_str = actvSearch.getText().toString();
                else if(strFilter.equals("ACESSÓRIO"))
                    laudo.Acessorio_str = actvSearch.getText().toString();
                else if(strFilter.equals("TAG"))
                    laudo.Tag_str = actvSearch.getText().toString();
                else if(strFilter.equals("FABRICANTE"))
                    laudo.Fabricante_str = actvSearch.getText().toString();
                else if(strFilter.equals("MODELO"))
                    laudo.Modelo_str = actvSearch.getText().toString();
                else if(strFilter.equals("EMPRESA"))
                    laudo.Empresa_str = actvSearch.getText().toString();
                else if(strFilter.equals("CNPJ"))
                    laudo.Cnpj_str = actvSearch.getText().toString();
                else if(strFilter.equals("DATA CADASTRO"))
                    laudo.Datametodologiainspecao_str = actvSearch.getText().toString();
                else if(strFilter.equals("RESULTADO"))
                    laudo.ConclusaoTecnica_str = actvSearch.getText().toString();

                Laudo.Pesquisa(laudo);
                finish();
            }
        });

        Intent itOSID = getIntent();
        if(itOSID != null){
            laudo.Id_int = Integer.parseInt(itOSID.getStringExtra("OSID"));
        }
        actvSearch = (AutoCompleteTextView) findViewById(R.id.FilterLaudoAcSearch);
    }

    public class adapterLaudo extends ArrayAdapter<String> {

        public adapterLaudo(Context ctx, int txtViewResourceId, String[] objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override public View getDropDownView(int pLaudoition, View cnvtView, ViewGroup prnt) {
            return getCustomView(pLaudoition, cnvtView, prnt);
        }

        @Override public View getView(int pLaudo, View cnvtView, ViewGroup prnt) {
            return getCustomView(pLaudo, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.spinner_custom, parent, false);
            TextView main_text = (TextView) mySpinner.findViewById(R.id.SpinnerCustomTvDescricao);
            main_text.setText(vLaudo[position].toUpperCase());
            return mySpinner;
        }
    }
}
