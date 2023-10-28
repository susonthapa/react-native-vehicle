package com.reactnativevehicle.template

import androidx.car.app.CarContext
import androidx.car.app.model.Template
import com.facebook.react.bridge.ReadableMap
import com.reactnativevehicle.ReactCarRenderContext

/**
 * Base class for parsing the template based on the props passed from ReactNative
 *
 * @property context
 * @property renderContext
 */
abstract class RNTemplate(
  protected val context: CarContext,
  protected val renderContext: ReactCarRenderContext,
) {

  /**
   * Function that should be implemented by the children of this class
   *
   * @param props the props that was passed from the ReactNative
   * @return the template
   */
  abstract fun parse(props: ReadableMap): Template

  companion object {
    const val TAG = "RNTemplate"
  }

}
