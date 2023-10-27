package com.reactnativevehicle.ext

import com.reactnativevehicle.template.VHGridItem
import com.reactnativevehicle.template.VHItem
import com.reactnativevehicle.template.VHMessageInfo
import com.reactnativevehicle.template.VHNavigationInfo
import com.reactnativevehicle.template.VHRoutingInfo
import com.reactnativevehicle.template.VHRow
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import java.lang.reflect.Type

interface Codable

object MoshiParser {
  private val moshi = Moshi.Builder()
    .add(
      PolymorphicJsonAdapterFactory.of(VHNavigationInfo::class.java, "type")
        .withSubtype(VHRoutingInfo::class.java, "routingInfo")
        .withSubtype(VHMessageInfo::class.java, "messageInfo")
    )
    .add(
      PolymorphicJsonAdapterFactory.of(VHItem::class.java, "type")
        .withSubtype(VHRow::class.java, "row")
        .withSubtype(VHGridItem::class.java, "grid-item")
    )
    .build()

  fun <T> fromJsonString(json: String, type: Type): T? {
    return moshi.adapter<T>(type).fromJson(json)
  }

  fun <T> toJsonString(obj: T, type: Type): String? {
    return moshi.adapter<T>(type).toJson(obj)
  }
}
