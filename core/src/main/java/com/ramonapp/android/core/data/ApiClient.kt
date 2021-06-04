package com.ramonapp.android.core.data

import com.google.gson.GsonBuilder
import com.ramonapp.android.core.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class ApiClient {

    companion object {
        private const val API_BASE_URL = BuildConfig.API_URL
        private const val CONNECTION_TIME_OUT = 60L
        private const val READ_TIME_OUT = 60L
        private const val WRITE_TIME_OUT = 60L

        private var mRetrofit: Retrofit? = null

        private var gson = GsonBuilder()
            .setLenient()
            .serializeNulls()
            .create()

        fun getRetrofitInstance(): Retrofit {
            return mRetrofit ?: Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(MyCallAdapterFactory())
                .client(getOkHttpClient())
                .build()
        }
        fun getOkHttpClient(): OkHttpClient {
            val okHttpClientBuilder: OkHttpClient.Builder

            okHttpClientBuilder = getUnsafeOkHttpClient().addInterceptor {
                val originalRequest: Request = it.request()
                val newUrl = originalRequest.url().newBuilder()
                    .addQueryParameter("access_key",BuildConfig.API_KEY).build()
                val builder =
                    originalRequest.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .url(newUrl)

                val newRequest = builder.build()
                it.proceed(newRequest)
            }.connectTimeout(CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)

            if (BuildConfig.DEBUG) {
                val httpLogInterceptor = HttpLoggingInterceptor()
                httpLogInterceptor.level = HttpLoggingInterceptor.Level.BODY

                okHttpClientBuilder.addInterceptor(httpLogInterceptor)
            }

            return okHttpClientBuilder.build()
        }

        private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {

                    override fun checkClientTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun checkServerTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient().newBuilder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, session -> true }

                return builder
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }
    }
}