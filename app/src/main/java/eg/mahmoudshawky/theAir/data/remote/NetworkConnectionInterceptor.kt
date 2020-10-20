package eg.mahmoudshawky.theAir.data.remote

import android.content.Context
import eg.mahmoudshawky.theAir.BuildConfig
import eg.mahmoudshawky.theAir.R
import eg.mahmoudshawky.theAir.utils.extensions.isNetworkConnected
import okhttp3.Interceptor
import okhttp3.Response


class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!context.isNetworkConnected()) {
            throw Failure.NetworkException(context.getString(R.string.msg_no_internet))
        }

        val original = chain.request()
        val originalHttpUrl = original.url

        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("language", "en-US")
            .addQueryParameter("region", "EG")
            .build()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
            /*.addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")*/
            .url(url)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}