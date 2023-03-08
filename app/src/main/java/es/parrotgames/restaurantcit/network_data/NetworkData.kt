package es.parrotgames.restaurantcit.network_data

import android.content.Context
import androidx.activity.ComponentActivity
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.facebook.applinks.AppLinkData
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext

class NetworkData(private val context: Context) {

    suspend fun getDeep(): Any? = suspendCancellableCoroutine {
        AppLinkData.fetchDeferredAppLinkData(context) { data ->
            it.resumeWith(Result.success(data?.targetUri))
//            it.resumeWith(Result.success("myapp://test1/test!3/test-3/test 4/test5/ыtest6"))
        }
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun getGadid(): String = withContext(Dispatchers.IO) {
        AdvertisingIdClient.getAdvertisingIdInfo(context).id.toString()
    }
}