package com.reactnativevehicle.template

import androidx.car.app.CarContext
import androidx.car.app.model.DateTimeWithZone
import androidx.car.app.model.Distance
import androidx.car.app.navigation.model.Lane
import androidx.car.app.navigation.model.LaneDirection
import androidx.car.app.navigation.model.Maneuver
import androidx.car.app.navigation.model.MessageInfo
import androidx.car.app.navigation.model.NavigationTemplate
import androidx.car.app.navigation.model.RoutingInfo
import androidx.car.app.navigation.model.Step
import androidx.car.app.navigation.model.TravelEstimate
import com.facebook.react.bridge.ReadableMap
import com.reactnativevehicle.ReactCarRenderContext
import com.reactnativevehicle.ext.decode
import com.reactnativevehicle.ext.toActionStrip
import com.reactnativevehicle.ext.toCarIcon
import java.util.TimeZone

/**
 * Creates [NavigationTemplate] from the given props
 *
 * @constructor
 * @see RNTemplate
 *
 * @param context
 * @param renderContext
 */
class RNNavigationTemplate(
  context: CarContext,
  renderContext: ReactCarRenderContext,
) : RNTemplate(context, renderContext) {

  private fun parseStep(step: VHStep): Step {
    return Step.Builder().apply {
      addLane(parseLane(step.lane))
      step.cue?.let { setCue(it) }
      step.lanesImage?.let { setLanesImage(it.toCarIcon(context)) }
      setManeuver(parseManeuver(step.maneuver))
      step.road?.let { setRoad(it) }
    }.build()
  }

  private fun parseLane(lane: VHLane): Lane {
    val laneBuilder = Lane.Builder()
    return laneBuilder.addDirection(LaneDirection.create(lane.shape, lane.isRecommended)).build()
  }

  private fun parseManeuver(maneuver: VHManeuver): Maneuver {
    val type = maneuver.type
    val builder = Maneuver.Builder(type)
    builder.setIcon(maneuver.icon.toCarIcon(context))
    if (type == Maneuver.TYPE_ROUNDABOUT_ENTER_AND_EXIT_CW_WITH_ANGLE
      || type == Maneuver.TYPE_ROUNDABOUT_ENTER_AND_EXIT_CCW_WITH_ANGLE
    ) {
      builder.setRoundaboutExitAngle(maneuver.roundaboutExitAngle)
    }

    if (type == Maneuver.TYPE_ROUNDABOUT_ENTER_AND_EXIT_CW
      || type == Maneuver.TYPE_ROUNDABOUT_ENTER_AND_EXIT_CCW
      || type == Maneuver.TYPE_ROUNDABOUT_ENTER_AND_EXIT_CW_WITH_ANGLE
      || type == Maneuver.TYPE_ROUNDABOUT_ENTER_AND_EXIT_CCW_WITH_ANGLE
    ) {
      builder.setRoundaboutExitNumber(maneuver.roundaboutExitNumber)
    }

    return builder.build()
  }

  private fun parseDistance(distance: VHDistance): Distance {
    return Distance.create(distance.displayDistance, distance.displayUnit)
  }

  private fun parseMessageInfo(info: VHMessageInfo): MessageInfo {
    val builder = MessageInfo.Builder(info.title)
    info.icon?.let { builder.setImage(it.toCarIcon(context)) }
    return builder.build()
  }

  private fun parseTravelEstimate(estimate: VHTravelEstimate): TravelEstimate {
    val destinationDateTime = DateTimeWithZone.create(
      estimate.destinationTime.timeSinceEpochMillis,
      TimeZone.getTimeZone(estimate.destinationTime.id),
    )
    val builder = TravelEstimate.Builder(
      parseDistance(estimate.remainingDistance),
      destinationDateTime,
    )
    builder.setRemainingTimeSeconds(estimate.remainingTimeSeconds)
    return builder.build()
  }

  private fun parseRoutingInfo(info: VHRoutingInfo): RoutingInfo {
    return RoutingInfo.Builder()
      .apply {
        setLoading(info.isLoading)
        setCurrentStep(parseStep(info.step), parseDistance(info.distance))
        info.junctionImage?.let { setJunctionImage(it.toCarIcon(context)) }
        info.nextStep?.let { setNextStep(parseStep(it)) }
      }.build()
  }

  private fun parseNavigationInfo(info: VHNavigationInfo): NavigationTemplate.NavigationInfo {
    if (info is VHRoutingInfo) {
      return parseRoutingInfo(info)
    } else {
      return parseMessageInfo(info as VHMessageInfo)
    }
  }

  override fun parse(props: ReadableMap): NavigationTemplate {
    val navigationProps = props.decode<VHNavigationTemplate>()!!
    val builder = NavigationTemplate.Builder()
    navigationProps.actionStrip.let {
      builder.setActionStrip(
        it.toActionStrip(
          context,
          renderContext
        )
      )
    }
    navigationProps.mapActionStrip?.let {
      builder.setMapActionStrip(
        it.toActionStrip(
          context,
          renderContext
        )
      )
    }

    navigationProps.navigationInfo?.let { builder.setNavigationInfo(parseNavigationInfo(it)) }
    navigationProps.destinationTravelEstimate?.let {
      builder.setDestinationTravelEstimate(
        parseTravelEstimate(it)
      )
    }

    return builder.build()
  }

  companion object {
    const val TAG = "RNNavigationTemplate"
  }

}
