package nadlertec.com.br.ips.repository;

import java.util.ArrayList;
import java.util.List;

import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.model.MENU;

public class MENURepository {
    public static List<MENU> List() {
        List<MENU> lstMenu = new ArrayList<MENU>();

        MENU mnMenu = new MENU();

        mnMenu = new MENU();
        mnMenu.ORDEM = lstMenu.size();
        mnMenu.CAPTION = 0;
        mnMenu.ICONE = 0;
        mnMenu.CONTADOR = 0;
        mnMenu.TELA = "";
        lstMenu.add(mnMenu);

        mnMenu = new MENU();
        mnMenu.ORDEM = lstMenu.size();
        mnMenu.CAPTION = R.string.mnuCaptionLAUDOS;
        mnMenu.ICONE = R.drawable.ico_laudo;
        mnMenu.ICONENAME= R.string.mnuCaptionLAUDOS;
        mnMenu.CONTADOR = 0;
        mnMenu.TELA = "LAUDO";
        lstMenu.add(mnMenu);

        return lstMenu;
    }
}
