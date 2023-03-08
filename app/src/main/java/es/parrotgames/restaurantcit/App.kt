package es.parrotgames.restaurantcit

import android.app.Application
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import com.onesignal.OneSignal
import dagger.hilt.android.HiltAndroidApp
import es.parrotgames.restaurantcit.joker_library.utils.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var scope: CoroutineScope

    override fun onCreate() {
        super.onCreate()

        scope.launch {
            OneSignal.initWithContext(this@App)
            OneSignal.setAppId(Const.ONESIGNAL_APP_ID)

            @Suppress("BlockingMethodInNonBlockingContext")
            OneSignal.setExternalUserId(
                AdvertisingIdClient.getAdvertisingIdInfo(this@App).id.toString()
            )
        }
    }
}