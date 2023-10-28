package com.reactnativevehicle.template

import androidx.car.app.CarContext
import androidx.car.app.model.ListTemplate
import androidx.car.app.model.SectionedItemList
import com.facebook.react.bridge.ReadableMap
import com.reactnativevehicle.ReactCarRenderContext
import com.reactnativevehicle.ext.decode
import com.reactnativevehicle.ext.toAction
import com.reactnativevehicle.ext.toActionStrip
import com.reactnativevehicle.ext.toItemList

/**
 * Creates a [ListTemplate] from the given props
 *
 * @constructor
 * @see RNTemplate
 *
 * @param context
 * @param renderContext
 */
class RNListTemplate(
  context: CarContext,
  renderContext: ReactCarRenderContext,
) : RNTemplate(context, renderContext) {

  override fun parse(props: ReadableMap): ListTemplate {
    val list = props.decode<VHListTemplate>()!!
    val builder = ListTemplate.Builder()
    builder.setTitle(list.title)
    list.isLoading?.let { builder.setLoading(it) }
    list.actionStrip?.let { builder.setActionStrip(it.toActionStrip(context, renderContext)) }
    list.headerAction?.let { builder.setHeaderAction(it.toAction(context, renderContext)) }
    list.children.forEach {
      builder.addSectionedList(
        SectionedItemList.create(it.children.first().toItemList(context, renderContext), it.header),
      )
    }

    return builder.build()
  }

  companion object {
    const val TAG = "RNListTemplate"
  }

}
