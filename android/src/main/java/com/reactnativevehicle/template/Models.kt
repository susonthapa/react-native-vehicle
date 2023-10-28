package com.reactnativevehicle.template

import com.reactnativevehicle.ext.Codable
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VHAction(
  val actionType: Int?,
  val title: String?,
  val icon: VHIcon?,
  val backgroundColor: Int?,
  val onPress: Int?,
) : Codable

@JsonClass(generateAdapter = true)
data class VHActionStrip(
  val actions: List<VHAction>
) : Codable

@JsonClass(generateAdapter = true)
data class VHIcon(
  val uri: String
) : Codable

@JsonClass(generateAdapter = true)
data class VHItemList(
  val noItemMessage: String?,
  val children: List<VHItem>
) : Codable

interface VHItem

@JsonClass(generateAdapter = true)
data class VHRow(
  val type: String,
  val title: String,
  val texts: List<String>?,
  val image: VHIcon?,
  val onPress: Int?,
  // TODO(Fix this)
  val metadata: VHMetadata?,
) : Codable, VHItem

@JsonClass(generateAdapter = true)
data class VHGridItem(
  val type: String,
  val isLoading: Boolean?,
  val title: String,
  val text: String?,
  val image: VHIcon?,
  val onPress: Int?
) : Codable, VHItem

@JsonClass(generateAdapter = true)
data class VHMetadata(
  val distance: VHDistance,
  val icon: String,
  val latitude: Double,
  val longitude: Double
) : Codable

@JsonClass(generateAdapter = true)
data class VHDistance(
  val displayDistance: Double,
  val displayUnit: Int
) : Codable

@JsonClass(generateAdapter = true)
data class VHDateTimeWithZone(
  val id: String,
  val timeSinceEpochMillis: Long
) : Codable

@JsonClass(generateAdapter = true)
data class VHTravelEstimate(
  val remainingDistance: VHDistance,
  val destinationTime: VHDateTimeWithZone,
  val remainingTimeSeconds: Long
) : Codable

@JsonClass(generateAdapter = true)
data class VHSectionedItemList(
  val header: String,
  val children: List<VHItemList>
) : Codable

interface VHNavigationInfo

@JsonClass(generateAdapter = true)
data class VHRoutingInfo(
  val type: String,
  val step: VHStep,
  val isLoading: Boolean,
  val distance: VHDistance,
  val junctionImage: VHIcon?,
  val nextStep: VHStep?
) : Codable, VHNavigationInfo

@JsonClass(generateAdapter = true)
data class VHMessageInfo(
  val type: String,
  val title: String,
  val icon: VHIcon?
) : Codable, VHNavigationInfo

@JsonClass(generateAdapter = true)
data class VHStep(
  val lane: VHLane,
  val cue: String?,
  val lanesImage: VHIcon?,
  val maneuver: VHManeuver,
  val road: String?
) : Codable

@JsonClass(generateAdapter = true)
data class VHLane(
  val shape: Int,
  val isRecommended: Boolean
) : Codable

@JsonClass(generateAdapter = true)
data class VHManeuver(
  val type: Int,
  val icon: VHIcon,
  val roundaboutExitAngle: Int,
  val roundaboutExitNumber: Int
) : Codable

// Grid Template
@JsonClass(generateAdapter = true)
data class VHGridTemplate(
  val isLoading: Boolean?,
  val title: String?,
  val headerAction: VHAction?,
  val actionStrip: VHActionStrip?,
  val children: List<VHItemList>
) : Codable

// List Template
@JsonClass(generateAdapter = true)
data class VHListTemplate(
  val title: String,
  val isLoading: Boolean?,
  val headerAction: VHAction?,
  val actionStrip: VHActionStrip?,
  val children: List<VHSectionedItemList>,
) : Codable

// PlaceListMap Template
@JsonClass(generateAdapter = true)
data class VHPlaceListMapTemplate(
  val title: String,
  val headerAction: VHAction?,
  val isLoading: Boolean?,
  val actionStrip: VHActionStrip?,
  val children: List<VHItemList>
  // TODO(add Place)
) : Codable

// Pane Template
@JsonClass(generateAdapter = true)
data class VHPane(
  val isLoading: Boolean?,
  val children: List<VHRow>,
  val actionList: List<VHAction>?,
  val image: VHIcon?
) : Codable

@JsonClass(generateAdapter = true)
data class VHPaneTemplate(
  val title: String?,
  val headerAction: VHAction?,
  val actionStrip: VHActionStrip?,
  val children: List<VHPane>
) : Codable

// Navigation Template
@JsonClass(generateAdapter = true)
data class VHNavigationTemplate(
  val id: String,
  val actionStrip: VHActionStrip,
  val mapActionStrip: VHActionStrip?,
  val navigationInfo: VHNavigationInfo?,
  val destinationTravelEstimate: VHTravelEstimate?
) : Codable



