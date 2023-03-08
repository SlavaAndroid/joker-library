package com.bringsgame.library.link_builder

import androidx.core.net.toUri
import com.bringsgame.library.util.Params
import java.util.*

class LinkBuilder {
    fun build(
        afData: MutableMap<String, Any>? = null,
        fbData: Any? = null,
        gadid: String,
        afUID: String? = null
    ): String {
        return Params.BASE_URL.toUri().buildUpon().apply {
            appendQueryParameter(Params.SECURE_GET_PARAMETR, Params.SECURE_KEY)
            appendQueryParameter(Params.DEV_TMZ_KEY, TimeZone.getDefault().id)
            appendQueryParameter(Params.GADID_KEY, gadid)
            appendQueryParameter(Params.DEEPLINK_KEY, fbData.toString())
            appendQueryParameter(
                Params.SOURCE_KEY,
                when (fbData) {
                    null -> afData?.get("media_source").toString()
                    else -> "deeplink"
                }
            )
            appendQueryParameter(
                Params.AF_ID_KEY,
                when (fbData) {
                    null -> afUID
                    else -> "null"
                }
            )
            appendQueryParameter(Params.ADSET_ID_KEY, afData?.get("adset_id").toString())
            appendQueryParameter(Params.CAMPAIGN_ID_KEY, afData?.get("campaign_id").toString())
            appendQueryParameter(Params.APP_CAMPAIGN_KEY, afData?.get("campaign").toString())
            appendQueryParameter(Params.ADSET_KEY, afData?.get("adset").toString())
            appendQueryParameter(Params.ADGROUP_KEY, afData?.get("adgroup").toString())
            appendQueryParameter(Params.ORIG_COST_KEY, afData?.get("orig_cost").toString())
            appendQueryParameter(Params.AF_SITEID_KEY, afData?.get("af_siteid").toString())
        }.toString()
    }
}