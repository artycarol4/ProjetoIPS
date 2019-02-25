package nadlertec.com.br.ips.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.view.Menu;
import android.view.View;

import com.andrognito.flashbar.Flashbar;

import java.util.ArrayList;
import java.util.List;

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.model.HelpLiveo;
import br.liveo.navigationliveo.NavigationLiveo;
import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.model.CONFIG;
import nadlertec.com.br.ips.model.MENU;
import nadlertec.com.br.ips.repository.MENURepository;
import nadlertec.com.br.ips.setting.CustomTypefaceSpan;
import nadlertec.com.br.ips.ui.fragment.FragHome;
import nadlertec.com.br.ips.ui.fragment.FragSync;
import nadlertec.com.br.ips.ui.fragment.FragOS;
import nadlertec.com.br.ips.util.Preferencia;
import nadlertec.com.br.ips.util.Utilitario;

public class Main extends NavigationLiveo implements OnItemClickListener {
    Dialog dalProgress;
    private Flashbar flashbar = null;
    private static FragmentManager fragmentManager;
    private HelpLiveo mHelpLiveo;
    public List<String> mListNameItem;
    private static Activity acMain;
    private static Context cContext;
    List<MENU> lstMenu;
    public static CONFIG config;
    private static Flashbar fbMensagem = null;
    private Menu mnuToolbar;
    private static String strTAG = "";
    private static int intIndexFragment = 0;
    Typeface tfTituloBold;
    private String strCAPTION = "HOME";

    @Override
    public void onInt(Bundle savedInstanceState){
        acMain = this;
        cContext = this;

        tfTituloBold = Typeface.createFromAsset(getAssets(), getString(R.string.fontTituloBold));
        config = Preferencia.Read(cContext,cContext.getString(R.string.keyCONFIG),CONFIG.class);

        this.userBackground.setImageResource(R.drawable.img_menu);
        this.userPhoto.setVisibility(View.GONE);
        this.userName.setText(config.USERNAME);
        this.userName.setTextColor(Color.parseColor("#9e9e9e"));

        mHelpLiveo = new HelpLiveo();
        mListNameItem = new ArrayList<>();
        lstMenu = MENURepository.List();
        for (MENU mnu : lstMenu) {
            if(mnu.ICONE == 0 && mnu.TELA == "" && mnu.CAPTION == 0){
                mHelpLiveo.addSeparator(); // Item separator
            }else{
                if(mnu.CONTADOR > 0){
                    mHelpLiveo.add(getString(mnu.ICONENAME), mnu.ICONE, mnu.CAPTION);
                }else{
                    if(mnu.CAPTION > 0 && mnu.ICONENAME == 0 && mnu.TELA == ""){
                        mHelpLiveo.addSubHeader(getString(mnu.CAPTION));
                    }else{
                        mHelpLiveo.add(getString(mnu.ICONENAME), mnu.ICONE);
                    }
                }
            }
        }

        with(this).startingPosition(0).addAllHelpItem(mHelpLiveo.getHelp())
        .colorItemSelected(R.color.item_selecionado_menu)
        .colorNameSubHeader(R.color.corBotao)
        .footerItem(R.string.mnuCaptionSINCRONIZAR, R.drawable.ico_sync)
        .setOnPrepareOptionsMenu(onPrepare)
        .setOnClickFooter(onClickFooter)
        .build();

        int position = this.getCurrentPosition();
        this.setElevationToolBar(position != 2 ? 15 : 0);

        fragmentManager = getSupportFragmentManager();
    }

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment mFragment = new FragSync();
            FragmentManager mFragmentManager = getSupportFragmentManager();
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
            closeDrawer();
        }
    };

    @Override
    public void onItemClick(int position) {
        MENU menu;
        Fragment mFragment = null;
        FragmentManager mFragmentManager = getSupportFragmentManager();

        menu = lstMenu.get(position);

        intIndexFragment = position;
        supportInvalidateOptionsMenu();

        switch (menu.TELA) {
            case "LAUDO":
                mFragment = new FragOS();
                strTAG = menu.TELA;
                break;

            default:
                //if(Utilitario.OnLine(cContext))
                //    mFragment = new FragSync();
                //else
                    mFragment = new FragHome();
                strTAG = "HOME";
                break;
        };

        if (mFragment != null){
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment, strTAG).commit();
        }

        setElevationToolBar(position != 2 ? 15 : 0);

    }

    private void AjustaActionBar(){
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayUseLogoEnabled(true);
        supportActionBar.setLogo(R.drawable.img_logo_actionbar);

        getSupportActionBar().setElevation(0);

        SpannableStringBuilder SS = new SpannableStringBuilder("");
        SS.setSpan(new CustomTypefaceSpan("", tfTituloBold), 0, SS.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        supportActionBar.setTitle(SS);

        if(mnuToolbar != null) {
            this.mnuToolbar.clear();
            this.onCreateOptionsMenu(this.mnuToolbar);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        AjustaActionBar();
    }


}
