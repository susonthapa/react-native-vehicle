package com.reactnativevehicle.template

import androidx.car.app.CarContext
import androidx.car.app.model.GridTemplate
import com.facebook.react.bridge.ReadableMap
import com.reactnativevehicle.ReactCarRenderContext
import com.reactnativevehicle.ext.decode
import com.reactnativevehicle.ext.toAction
import com.reactnativevehicle.ext.toActionStrip
import com.reactnativevehicle.ext.toItemList

class RNGridTemplate(
  context: CarContext,
  renderContext: ReactCarRenderContext,
) : RNTemplate(context, renderContext) {
  override fun parse(props: ReadableMap): GridTemplate {
    val grid = props.decode<VHGridTemplate>()!!
    return GridTemplate.Builder().apply {
      grid.isLoading?.let { setLoading(it) }
      grid.title?.let { setTitle(it) }
      grid.headerAction?.let { setHeaderAction(it.toAction(context, renderContext)) }
      grid.actionStrip?.let { setActionStrip(it.toActionStrip(context, renderContext)) }
      if (grid.children.isNotEmpty()) {
        setSingleList(grid.children.first().toItemList(context, renderContext))
      }
    }.build()
  }

  companion object {
    const val TAG = "RNGridTemplate"
  }
}
