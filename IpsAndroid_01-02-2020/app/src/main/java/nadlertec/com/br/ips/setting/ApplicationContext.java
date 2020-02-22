package nadlertec.com.br.ips.setting;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class ApplicationContext  extends Application {
    private static Context context;
    public void onCreate(){
        context=getApplicationContext();
    }

    public static Context getCustomAppContext(){
        return context;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
