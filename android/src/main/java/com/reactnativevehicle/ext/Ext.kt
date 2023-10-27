package com.reactnativevehicle.ext

import android.content.Context
import androidx.car.app.model.Action
import androidx.car.app.model.ActionStrip
import androidx.car.app.model.CarColor
import androidx.car.app.model.CarIcon
import androidx.car.app.model.GridItem
import androidx.car.app.model.ItemList
import androidx.core.graphics.drawable.IconCompat
import com.facebook.common.references.CloseableReference
import com.facebook.datasource.DataSources
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.image.CloseableBitmap
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableNativeMap
import com.facebook.react.views.imagehelper.ImageSource
import com.reactnativevehicle.ReactCarRenderContext
import com.reactnativevehicle.template.VHAction
import com.reactnativevehicle.template.VHActionStrip
import com.reactnativevehicle.template.VHGridItem
import com.reactnativevehicle.template.VHIcon
import com.reactnativevehicle.template.VHItemList

fun ReadableMap.isLoading(): Boolean {
  return try {
    getBoolean("isLoading")
  } catch (e: Exception) {
    // check for children props
    (getArray("children")?.size() ?: 0) == 0
  }
}

fun ReactCarRenderContext.invokeCallback(callbackId: Int) {
  // TODO(Add Params support)
  val params = WritableNativeMap()
  params.putInt("id", callbackId)
  eventCallback?.invoke(params)
}

fun Int.toCarColor(): CarColor {
  return when (this) {
    2 -> CarColor.PRIMARY
    3 -> CarColor.SECONDARY
    4 -> CarColor.RED
    5 -> CarColor.GREEN
    6 -> CarColor.BLUE
    7 -> CarColor.YELLOW
    else -> CarColor.DEFAULT
  }
}

fun VHAction.toAction(context: Context, renderContext: ReactCarRenderContext): Action {
  val builder = Action.Builder()
  title?.let { builder.setTitle(it) }
  icon?.let { builder.setIcon(it.toCarIcon(context)) }
  backgroundColor?.let { builder.setBackgroundColor(it.toCarColor()) }
  onPress?.let { builder.setOnClickListener { renderContext.invokeCallback(it) } }

  return builder.build()
}

fun VHActionStrip.toActionStrip(
  context: Context,
  renderContext: ReactCarRenderContext
): ActionStrip {
  val builder = ActionStrip.Builder()
  actions.forEach {
    builder.addAction(it.toAction(context, renderContext))
  }
  return builder.build()
}

fun VHIcon.toCarIcon(context: Context): CarIcon {
  val source = ImageSource(context, uri)
  val imageRequest = ImageRequestBuilder.newBuilderWithSource(source.uri).build()
  val dataSource = Fresco.getImagePipeline().fetchDecodedImage(imageRequest, context)
  val result = DataSources.waitForFinalResult(dataSource) as CloseableReference<CloseableBitmap>
  val bitmap = result.get().underlyingBitmap

  CloseableReference.closeSafely(result)
  dataSource.close()

  return CarIcon.Builder(IconCompat.createWithBitmap(bitmap)).build()
}

fun VHGridItem.toGridItem(context: Context, renderContext: ReactCarRenderContext): GridItem {
  val builder = GridItem.Builder()
  builder.setTitle(title)
  isLoading?.let { builder.setLoading(it) }
  text?.let { builder.setText(it) }
  image?.let { builder.setImage(it.toCarIcon(context)) }
  onPress?.let { builder.setOnClickListener { renderContext.invokeCallback(it) } }

  return builder.build()
}

fun VHItemList.toItemList(context: Context, renderContext: ReactCarRenderContext): ItemList {
  val builder = ItemList.Builder()
  noItemMessage?.let { builder.setNoItemsMessage(it) }
  children.forEach {
    if (it is VHGridItem) {
      builder.addItem(it.toGridItem(context, renderContext))
    }
  }

  return builder.build()
}
