package nadlertec.com.br.ips.ui.activity.filter;

import android.app.Activity;
import android.content.Context;
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
import nadlertec.com.br.ips.model.TBOS;
import nadlertec.com.br.ips.ui.fragment.FragOS;

public class FilterOS extends AppCompatActivity {
    private static Activity acMain;
    private static Context cContext;

    static String[] vOS = null;
    adapterOS adpOS;
    private static Spinner spiOS;
    private String strFilter = "";
    private AutoCompleteTextView actvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_os);

        cContext = this;
        acMain = this;

        vOS = getResources().getStringArray(R.array.CampPesqOS);
        adpOS = new adapterOS(this, R.layout.spinner_custom, vOS);
        spiOS = (Spinner)findViewById(R.id.FilterOSSpiOS);
        spiOS.setAdapter(adpOS);
        spiOS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
                strFilter = vOS[posicao];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        LinearLayout llCancel = (LinearLayout) findViewById(R.id.FilterOSLlCancelar);
        llCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayout llFiltrar = (LinearLayout) findViewById(R.id.FilterOSLlFiltrar);
        llFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TBOS os = new TBOS();
                if(strFilter.equals("OS"))
                    os.NumeroPedido_str = actvSearch.getText().toString();
                else if(strFilter.equals("CLIENTE"))
                    os.Cliente_str = actvSearch.getText().toString();
                else if(strFilter.equals("INSPETOR"))
                    os.Inspetor_str = actvSearch.getText().toString();
                FragOS.Pesquisa(os);
                finish();
            }
        });

        actvSearch = (AutoCompleteTextView) findViewById(R.id.FilterOSAcSearch);
    }

    public class adapterOS extends ArrayAdapter<String> {

        public adapterOS(Context ctx, int txtViewResourceId, String[] objects) {
            super(ctx, txtViewResourceId, objects);
        }

        @Override public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {
            return getCustomView(position, cnvtView, prnt);
        }

        @Override public View getView(int pos, View cnvtView, ViewGroup prnt) {
            return getCustomView(pos, cnvtView, prnt);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getLayoutInflater();
            View mySpinner = inflater.inflate(R.layout.spinner_custom, parent, false);
            TextView main_text = (TextView) mySpinner.findViewById(R.id.SpinnerCustomTvDescricao);
            main_text.setText(vOS[position].toUpperCase());
            return mySpinner;
        }
    }
}
