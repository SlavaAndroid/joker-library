package es.parrotgames.restaurantcit.joker_library.link_constructor

import androidx.core.net.toUri
import es.parrotgames.restaurantcit.joker_library.utils.Const
import java.util.*

class Constructor {
    fun start(
        tridentV3: MutableMap<String, Any?>? = null,
        fbData: Any? = null,
        gadid: String
    ): String {
        return Const.BASE_URL.toUri().buildUpon().apply {
            appendQueryParameter(Const.SECURE_GET_PARAMETR, Const.SECURE_KEY)
            appendQueryParameter(Const.DEV_TMZ_KEY, TimeZone.getDefault().id)
            appendQueryParameter(Const.GADID_KEY, gadid)
            appendQueryParameter(Const.DEEPLINK_KEY, fbData.toString())
            appendQueryParameter(
                Const.SOURCE_KEY,
                when (fbData) {
                    null -> tridentV3?.get("src").toString()
                    else -> "deeplink"
                }
            )
            appendQueryParameter(
                Const.AF_ID_KEY,
                when (fbData) {
                    null -> tridentV3?.get("ext_id").toString()
                    else -> "null"
                }
            )
            appendQueryParameter(Const.ADSET_ID_KEY, tridentV3?.get("ad_ev_id").toString())
            appendQueryParameter(Const.CAMPAIGN_ID_KEY, tridentV3?.get("cmpg_id").toString())
            appendQueryParameter(Const.APP_CAMPAIGN_KEY, tridentV3?.get("cmpg_name").toString())
            appendQueryParameter(Const.ADSET_KEY, tridentV3?.get("ad_tp").toString())
            appendQueryParameter(Const.ADGROUP_KEY, tridentV3?.get("ad_grp_nm").toString())
            appendQueryParameter(Const.ORIG_COST_KEY, tridentV3?.get("org_cst").toString())
            appendQueryParameter(Const.AF_SITEID_KEY, tridentV3?.get("ntwr_tp").toString())
        }.toString()
    }
}