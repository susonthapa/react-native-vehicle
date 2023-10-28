package com.reactnativevehicle.ext

import android.util.Log
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.squareup.moshi.Types

inline fun <reified T> ReadableMap.decode(): T? {
  try {
    val hashMap = toHashMap()
    val jsonString = MoshiParser.toJsonString(hashMap, Map::class.java) ?: return null
    return MoshiParser.fromJsonString<T>(jsonString, T::class.java)
  } catch (e: Exception) {
    Log.w("DataExt", "Failed to decode the passed ReadableMap from JS!", e)
    return null
  }
}

inline fun <reified T> ReadableArray.decode(): List<T>? {
  try {
    val arrayList = toArrayList().toList()
    val jsonString = MoshiParser.toJsonString(arrayList, List::class.java) ?: return null
    return MoshiParser.fromJsonString<List<T>>(
      jsonString,
      Types.newParameterizedType(List::class.java, T::class.java)
    )
  } catch (e: Exception) {
    Log.w("DataExt", "Failed to decode the passed ReadableArray from JS!", e)
    return null
  }
}

fun Codable.encode(): WritableMap {
  val json = MoshiParser.toJsonString(this, this::class.java)!!
  val hashMap = MoshiParser.fromJsonString<Map<String, *>>(json, Map::class.java)
  return Arguments.makeNativeMap(hashMap)
}

@JvmName("encodeCodable")
fun Collection<Codable>.encode(): ReadableArray {
  return encodeInternal(this)
}

@JvmName("encodeNumber")
fun Collection<Number>.encode(): ReadableArray {
  return encodeInternal(this)
}

@JvmName("encodeBoolean")
fun Collection<Boolean>.encode(): ReadableArray {
  return encodeInternal(this)
}

@JvmName("encodeString")
fun Collection<String>.encode(): ReadableArray {
  return encodeInternal(this)
}

private fun encodeInternal(args: Collection<*>): ReadableArray {
  val json = MoshiParser.toJsonString(args, List::class.java)!!
  val array = MoshiParser.fromJsonString<List<*>>(json, List::class.java)
  return Arguments.makeNativeArray(array)
}
