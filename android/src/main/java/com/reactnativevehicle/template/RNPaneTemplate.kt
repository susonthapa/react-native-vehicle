package com.reactnativevehicle.template

import androidx.car.app.CarContext
import androidx.car.app.model.Pane
import androidx.car.app.model.PaneTemplate
import com.facebook.react.bridge.ReadableMap
import com.reactnativevehicle.ReactCarRenderContext
import com.reactnativevehicle.ext.decode
import com.reactnativevehicle.ext.toAction
import com.reactnativevehicle.ext.toActionStrip
import com.reactnativevehicle.ext.toCarIcon
import com.reactnativevehicle.ext.toRow

/**
 * Creates [PaneTemplate] from the given props
 *
 * @constructor
 * @see RNTemplate
 *
 * @param context
 * @param renderContext
 */
class RNPaneTemplate(
  context: CarContext,
  renderContext: ReactCarRenderContext,
) : RNTemplate(context, renderContext) {

  override fun parse(props: ReadableMap): PaneTemplate {
    val pane = props.decode<VHPaneTemplate>()!!
    val paneChildren = pane.children
    val paneBuilder = Pane.Builder()

    paneChildren.isLoading?.let { paneBuilder.setLoading(it) }
    paneChildren.image?.let { paneBuilder.setImage(it.toCarIcon(context)) }
    paneChildren.actionList?.forEach {
      paneBuilder.addAction(it.toAction(context, renderContext))
    }
    paneChildren.children.forEach {
      paneBuilder.addRow(it.toRow(context, renderContext))
    }

    val builder = PaneTemplate.Builder(paneBuilder.build())
    pane.title?.let { builder.setTitle(it) }
    // TODO(Fix headerAction)
    pane.actionStrip?.let { builder.setActionStrip(it.toActionStrip(context, renderContext)) }
    return builder.build()
  }

  companion object {
    const val TAG = "RNPaneTemplate"
  }
}
