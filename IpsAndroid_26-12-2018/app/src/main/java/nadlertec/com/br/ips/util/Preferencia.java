package nadlertec.com.br.ips.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Preferencia {

    public static void Write(Context pContext, String pCHAVE, Object pOBJETO){
        SharedPreferences sharedPreferences = pContext.getSharedPreferences("PREFERENCIA", Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(pOBJETO);
        sharedPreferencesEditor.putString(pCHAVE, serializedObject);
        sharedPreferencesEditor.apply();
    }


    public static <GenericClass> GenericClass Read(Context pContext, String pCHAVE, Class<GenericClass> pCLASSTYPE){
        SharedPreferences sharedPreferences = pContext.getSharedPreferences("PREFERENCIA", Context.MODE_PRIVATE);
        if (sharedPreferences.contains(pCHAVE)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(pCHAVE, ""), pCLASSTYPE);
        }

        return null;
    }

    public static void LeBase(Context context, String[][] vParametro, String Chave) {
        try{
            SharedPreferences sharedPreferences;
            sharedPreferences = context.getSharedPreferences(Chave, Context.MODE_PRIVATE);

            for(int i = 0; i < vParametro.length;i++){
                if(vParametro[i][0] != null)
                    vParametro[i][1] = sharedPreferences.getString(vParametro[i][0], vParametro[i][0]);
            }

            sharedPreferences = null;
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public static void GravaBase(Context context,String[][] vParametro, String Chave) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences(Chave, Context.MODE_PRIVATE);

        try{
            SharedPreferences.Editor prefsPrivateEditor = sharedPreferences.edit();

            for(int i = 0; i < vParametro.length;i++){
                if(vParametro[i][0] != null)
                    prefsPrivateEditor.putString(vParametro[i][0], vParametro[i][1]);
            }

            prefsPrivateEditor.commit();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }
}
