package com.reactnativevehicle.ext

import android.content.Context
import android.text.Spannable
import android.text.SpannableStringBuilder
import androidx.car.app.model.Action
import androidx.car.app.model.ActionStrip
import androidx.car.app.model.CarColor
import androidx.car.app.model.CarIcon
import androidx.car.app.model.CarLocation
import androidx.car.app.model.Distance
import androidx.car.app.model.DistanceSpan
import androidx.car.app.model.GridItem
import androidx.car.app.model.ItemList
import androidx.car.app.model.Metadata
import androidx.car.app.model.Place
import androidx.car.app.model.PlaceMarker
import androidx.car.app.model.Row
import androidx.car.app.model.Toggle
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
import com.reactnativevehicle.template.VHCarLocation
import com.reactnativevehicle.template.VHDistance
import com.reactnativevehicle.template.VHGridItem
import com.reactnativevehicle.template.VHIcon
import com.reactnativevehicle.template.VHItemList
import com.reactnativevehicle.template.VHMetadata
import com.reactnativevehicle.template.VHPlace
import com.reactnativevehicle.template.VHPlaceMarker
import com.reactnativevehicle.template.VHRow
import com.reactnativevehicle.template.VHToggle

fun ReadableMap.isLoading(): Boolean {
  return try {
    getBoolean("isLoading")
  } catch (e: Exception) {
    // check for children props
    (getArray("children")?.size() ?: 0) == 0
  }
}

fun ReactCarRenderContext.invokeCallback(callbackId: Int, parameters: WritableNativeMap? = null) {
  val params = parameters ?: WritableNativeMap()
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
  when (actionType) {
    1 -> {
      return Action.APP_ICON
    }

    2 -> {
      return Action.BACK
    }

    3 -> {
      return Action.PAN
    }
  }
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
    } else {
      builder.addItem((it as VHRow).toRow(context, renderContext))
    }
  }

  return builder.build()
}

fun VHToggle.toToggle(renderContext: ReactCarRenderContext): Toggle {
  val builder = Toggle.Builder {
    val params = WritableNativeMap()
    params.putBoolean("isChecked", it)
    renderContext.invokeCallback(onCheckedChange, params)
  }
  builder.setChecked(isChecked)

  return builder.build()
}

fun VHCarLocation.toCarLocation(): CarLocation {
  return CarLocation.create(lat, lng)
}

fun VHPlaceMarker.toPlaceMarker(context: Context): PlaceMarker {
  val builder = PlaceMarker.Builder()
  builder.setIcon(icon.toCarIcon(context), iconType)
  label?.let { builder.setLabel(it) }
  color?.let { builder.setColor(it.toCarColor()) }
  return builder.build()
}

fun VHDistance.toDistance(): Distance {
  return Distance.create(displayDistance, displayUnit)
}

fun VHPlace.toPlace(context: Context): Place {
  val builder = Place.Builder(location.toCarLocation())
  builder.setMarker(marker.toPlaceMarker(context))
  return builder.build()
}

fun VHMetadata.toMetadata(context: Context): Metadata {
  val builder = Metadata.Builder()
  builder.setPlace(place.toPlace(context))
  return builder.build()
}

fun VHRow.toRow(context: Context, renderContext: ReactCarRenderContext): Row {
  val builder = Row.Builder()
  builder.setTitle(title)
  texts?.forEach { builder.addText(it) }
  image?.let { builder.setImage(it.toCarIcon(context)) }
  onPress?.let { builder.setOnClickListener { renderContext.invokeCallback(it) } }
  isBrowsable?.let { builder.setBrowsable(it) }
  toggle?.let { builder.setToggle(it.toToggle(renderContext)) }
  metadata?.let {
    if (it.distance != null) {
      val distance = it.distance.toDistance()
      val span = DistanceSpan.create(distance)
      val start = title.indexOf("%d")
      val spannableBuilder = SpannableStringBuilder(title)
      spannableBuilder.setSpan(span, start, start + 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
      builder.setTitle(spannableBuilder)
    }
    builder.setMetadata(it.toMetadata(context))
  }

  return builder.build()
}
