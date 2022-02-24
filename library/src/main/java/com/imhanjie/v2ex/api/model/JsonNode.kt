package com.imhanjie.v2ex.api.model

import com.google.gson.annotations.SerializedName

data class JsonNode(
    @SerializedName("avatar_large") var avatarLarge: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("avatar_normal") var avatarNormal: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("topics") var topics: Int? = null,
    @SerializedName("footer") var footer: String? = null,
    @SerializedName("header") var header: String? = null,
    @SerializedName("title_alternative") var titleAlternative: String? = null,
    @SerializedName("avatar_mini") var avatarMini: String? = null,
    @SerializedName("stars") var stars: Int? = null,
    @SerializedName("aliases") var aliases: ArrayList<String> = arrayListOf(),
    @SerializedName("root") var root: Boolean? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("parent_node_name") var parentNodeName: String? = null
)