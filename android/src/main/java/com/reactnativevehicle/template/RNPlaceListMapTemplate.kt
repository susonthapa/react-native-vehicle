package com.reactnativevehicle.template

import androidx.car.app.CarContext
import androidx.car.app.model.PlaceListMapTemplate
import com.facebook.react.bridge.ReadableMap
import com.reactnativevehicle.ReactCarRenderContext
import com.reactnativevehicle.ext.decode
import com.reactnativevehicle.ext.toActionStrip
import com.reactnativevehicle.ext.toItemList

/**
 * Creates [PlaceListMapTemplate] from the given props
 *
 * @constructor
 *
 *
 * @param context
 * @param renderContext
 */
class RNPlaceListMapTemplate(
  context: CarContext,
  renderContext: ReactCarRenderContext,
) : RNTemplate(context, renderContext) {

  override fun parse(props: ReadableMap): PlaceListMapTemplate {
    val placeList = props.decode<VHPlaceListMapTemplate>()!!
    val builder = PlaceListMapTemplate.Builder()
    builder.setTitle(placeList.title)
    // TODO(HeaderAction)
    placeList.isLoading?.let { builder.setLoading(it) }
    placeList.actionStrip?.let { builder.setActionStrip(it.toActionStrip(context, renderContext)) }
    builder.setItemList(placeList.children.toItemList(context, renderContext))
    return builder.build()
  }

  companion object {
    const val TAG = "RNPlaceListMapTemplate"
  }

}
