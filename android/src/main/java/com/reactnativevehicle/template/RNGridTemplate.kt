package com.reactnativevehicle.template

import androidx.car.app.CarContext
import androidx.car.app.model.GridTemplate
import com.facebook.react.bridge.ReadableMap
import com.reactnativevehicle.ReactCarRenderContext
import com.reactnativevehicle.ext.decode
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
      // TODO(Fix this)
//      grid.headerAction?.let { setHeaderAction(Action()) }
      setActionStrip(grid.actionStrip.toActionStrip(context, renderContext))
      setSingleList(grid.children.toItemList(context, renderContext))
    }.build()
  }

  companion object {
    const val TAG = "RNGridTemplate"
  }
}
