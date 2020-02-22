package nadlertec.com.br.ips.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.andrognito.flashbar.Flashbar;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;

import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.setting.CONSTANT;

public class Utilitario {

    public static Dialog openProgress(Context cCONTEXT){
        AlertDialog.Builder adbProgress = new AlertDialog.Builder(cCONTEXT);
        adbProgress.setView(R.layout.dialog_progress);
        Dialog dProgressTemp = adbProgress.create();
        dProgressTemp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dProgressTemp.setCancelable(false);
        return dProgressTemp;
    }

    public static Flashbar MensagemErroFbTitulo(Activity pActivity, Boolean pTOP, String pTITULO, String pMENSAGEM, int pICON){
        return new Flashbar.Builder(pActivity)
                .gravity(Flashbar.Gravity.TOP)
                .title(pTITULO)
                .message(pMENSAGEM)
                .backgroundColorRes(R.color.dialogError)
                .showIcon(1.0f, ImageView.ScaleType.FIT_START)
                .icon(pICON)
                .iconColorFilterRes(R.color.branco)
                .showOverlay()
                .positiveActionText(pActivity.getString(R.string.btnOK))
                .positiveActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar bar) {
                        bar.dismiss();
                    }
                })
                .overlayBlockable()
                .build();
    }

    public static Flashbar MensagemInfoBar(Activity pActivity, Boolean pTOP, String pTITULO, String pMENSAGEM, int pICON){
        return new Flashbar.Builder(pActivity)
                .gravity(Flashbar.Gravity.TOP)
                .title(pTITULO)
                .message(pMENSAGEM)
                .backgroundColorRes(R.color.dialogInfo)
                .showIcon(1.0f, ImageView.ScaleType.FIT_START)
                .icon(pICON)
                .iconColorFilterRes(R.color.branco)
                .showOverlay()
                .positiveActionText(pActivity.getString(R.string.btnOK))
                .positiveActionTapListener(new Flashbar.OnActionTapListener() {
                    @Override
                    public void onActionTapped(@NotNull Flashbar bar) {
                        bar.dismiss();
                    }
                })
                .overlayBlockable()
                .build();
    }

    public static boolean WifiOn(Activity pActivity){
        try{
            ConnectivityManager connManager = (ConnectivityManager) pActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            return mWifi.isConnected();

        }catch(Exception ex){
            return false;
        }
    }

    public static final void setDrawable(Context pContext, int pDRAWABLE, LinearLayout pLilearLaout) {
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            pLilearLaout.setBackgroundDrawable( pContext.getResources().getDrawable(pDRAWABLE) );
        } else {
            pLilearLaout.setBackground( pContext.getResources().getDrawable(pDRAWABLE));
        }
    }

    public static String[] BitmapToString(Bitmap bmp){
        String[] vReturn;
        try{
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            int intCount = 0;
            int intLength = (encodedImage.length() / CONSTANT.LENGTHIMAGE) + ((encodedImage.length() % CONSTANT.LENGTHIMAGE) > 0 ? 1 : 0);
            vReturn = new String[intLength];
            for(int i = 0; i < vReturn.length; i++){
                vReturn[intCount] = encodedImage.substring(i * CONSTANT.LENGTHIMAGE,
                                                            (i * CONSTANT.LENGTHIMAGE) + CONSTANT.LENGTHIMAGE > encodedImage.length() ?
                                                             encodedImage.length() :
                                                            (i * CONSTANT.LENGTHIMAGE) + CONSTANT.LENGTHIMAGE
                                                                         );
                intCount++;
            }

            return vReturn;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String[] BitmapToString(String pIMAGE, Context cContext){
        String[] vReturn;
        try{
            File root = new File(Environment.getExternalStorageDirectory(), cContext.getString(R.string.dirTMP));
            File file = new File(root, pIMAGE);
            Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            int intCount = 0;
            int intLength = (encodedImage.length() / CONSTANT.LENGTHIMAGE) + ((encodedImage.length() % CONSTANT.LENGTHIMAGE) > 0 ? 1 : 0);
            vReturn = new String[intLength];
            for(int i = 0; i < vReturn.length; i++){
                vReturn[intCount] = encodedImage.substring(i * CONSTANT.LENGTHIMAGE,
                        (i * CONSTANT.LENGTHIMAGE) + CONSTANT.LENGTHIMAGE > encodedImage.length() ?
                                encodedImage.length() :
                                (i * CONSTANT.LENGTHIMAGE) + CONSTANT.LENGTHIMAGE
                );
                intCount++;
            }

            return vReturn;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static String VlrString(String pSTRING){
        if(pSTRING == null)
            return "null";
        else if(pSTRING == "")
            return "null";
        else
            return "'" + pSTRING + "'";
    }

    public  static boolean OnLine(Context pContext) {
        boolean conectado;
        ConnectivityManager conectivtyManager = (ConnectivityManager) pContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (conectivtyManager.getActiveNetworkInfo() != null
                && conectivtyManager.getActiveNetworkInfo().isAvailable()
                && conectivtyManager.getActiveNetworkInfo().isConnected()) {
            conectado = true;
        } else {
            conectado = false;
        }
        return conectado;
    }

    public static String FormataTEL(String pTELEFONE){
        String strTemp = "";
        try{
            strTemp = pTELEFONE.replace("(","").replace(")","").replace("-","");
            if(strTemp.length() == 0)
                return "";
            else if(strTemp.length() == 8){
                return strTemp.substring(0,4) + "-" + strTemp.substring(4,8);
            }else if(strTemp.length() == 10){
                return "(" + strTemp.substring(0,2) + ") " + strTemp.substring(2,6) + "-" + strTemp.substring(6,10);
            }else if(strTemp.length() == 11){
                return "(" + strTemp.substring(0,2) + ") " + strTemp.substring(2,7) + "-" + strTemp.substring(7,11);
            }else{
                return strTemp;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return "";
    }

    public static String FormataCNPJ(String pCNPJ){
        String strTemp = "";
        try{
            strTemp = pCNPJ.replace(".","").replace("/","").replace("-","");
            if(strTemp.length() == 0)
                return "";
            else if(strTemp.length() == 14){
                return strTemp.substring(0,2) + "." + strTemp.substring(2,5) + "." + strTemp.substring(5,8) + "/" + strTemp.substring(8,10) + strTemp.substring(10,12) + "-" + strTemp.substring(12,14);
            }else{
                return strTemp;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return "";
    }

    public static String TELDDD(String pTELEFONE){
        String strTemp = "";
        try{
            strTemp = pTELEFONE.replace("(","").replace(")","").replace("-","");
            if(strTemp.length() == 0)
                return "";
            else if(strTemp.length() == 8){
                return "";
            }else if(strTemp.length() == 10 || strTemp.length() == 11){
                return strTemp.substring(0,2);
            }else{
                return "";
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return "";
    }

    public static String TELSEMDDD(String pTELEFONE){
        String strTemp = "";
        try{
            strTemp = pTELEFONE.replace("(","").replace(")","").replace("-","");
            if(strTemp.length() == 0)
                return "";
            else if(strTemp.length() == 8){
                return strTemp;
            }else if(strTemp.length() == 10){
                return strTemp.substring(2,6) + strTemp.substring(6,10);
            }else if(strTemp.length() == 11){
                return strTemp.substring(2,7) + "-" + strTemp.substring(7,11);
            }else{
                return strTemp;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return "";
    }
}
