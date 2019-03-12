package nadlertec.com.br.ips.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import nadlertec.com.br.ips.R;
import nadlertec.com.br.ips.model.MENSAGEM;
import nadlertec.com.br.ips.model.TBACESSORIO;
import nadlertec.com.br.ips.model.TBLAUDO;
import nadlertec.com.br.ips.model.TBOS;
import nadlertec.com.br.ips.model.TOKEN;
import nadlertec.com.br.ips.setting.ApplicationContext;
import nadlertec.com.br.ips.setting.CONSTANT;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface GitHubService {

    interface ServiceTOKEN {
        @FormUrlEncoded
        @POST("token")
        Call<TOKEN> Exec(@Field("username") String username,
                         @Field("password") String password,
                         @Field("grant_type") String grant_type);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .connectTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .writeTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .build();

        public static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationContext.getCustomAppContext().getString(R.string.serverAPI))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    interface ServiceOS {
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        @GET("pedidos")
        Call<List<TBOS>> Exec(@Header("Authorization") String Authorization);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .connectTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .writeTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .build();

        public static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationContext.getCustomAppContext().getString(R.string.serverAPI) + ApplicationContext.getCustomAppContext().getString(R.string.routAPI))
                .addConverterFactory(GsonConverterFactory.create())

                .client(okHttpClient)
                .build();
    }

    interface ServiceACESSORIO {
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        @GET("MontaAcessorios/{id_int}")
        Call<List<TBACESSORIO>> Exec(@Path("id_int") int id_int, @Header("Authorization") String Authorization);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .connectTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .writeTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .build();

        public static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationContext.getCustomAppContext().getString(R.string.serverAPI) + ApplicationContext.getCustomAppContext().getString(R.string.routAPI))
                .addConverterFactory(GsonConverterFactory.create())

                .client(okHttpClient)
                .build();
    }

    interface ServiceORDEMSERVICO {
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        @POST("OrdemServico/addos")
        Call<TBLAUDO> Exec(@Query("ordemservico") String laudo, @Header("Authorization") String Authorization);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .connectTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .writeTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .build();

        public static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationContext.getCustomAppContext().getString(R.string.serverAPI) + ApplicationContext.getCustomAppContext().getString(R.string.routAPI))
                .addConverterFactory(GsonConverterFactory.create())

                .client(okHttpClient)
                .build();
    }

    interface ServiceUPLOADIMAGE {
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        @POST("ordemservico/uploadimage")
        Call<MENSAGEM> Exec(@Body Map<String,String> options, @Header("Authorization") String Authorization);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .connectTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .writeTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .build();

        public static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationContext.getCustomAppContext().getString(R.string.serverAPI) + ApplicationContext.getCustomAppContext().getString(R.string.routAPI))
                .addConverterFactory(GsonConverterFactory.create())

                .client(okHttpClient)
                .build();
    }

    interface ServiceSENDOS {
        @Headers({ "Content-Type: application/json;charset=UTF-8"})
        @POST("ordemservico/addos")
        Call<TBLAUDO> Exec(@Query("ordemservico") String ordemservico, @Query("username") String username, @Header("Authorization") String Authorization);

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .connectTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .writeTimeout(CONSTANT.TimeOut, TimeUnit.SECONDS)
                .build();

        public static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApplicationContext.getCustomAppContext().getString(R.string.serverAPI) + ApplicationContext.getCustomAppContext().getString(R.string.routAPI))
                .addConverterFactory(GsonConverterFactory.create())

                .client(okHttpClient)
                .build();
    }
}