package com.reactnativevehicle.template

import com.reactnativevehicle.ext.Codable

data class VHAction(
  val title: String?,
  val icon: VHIcon?,
  val backgroundColor: Int?,
  val onPress: Int?,
) : Codable

data class VHActionStrip(
  val actions: List<VHAction>
) : Codable

data class VHIcon(
  val uri: String
) : Codable

data class VHItemList(
  val noItemMessage: String?,
  val children: List<VHItem>
) : Codable

interface VHItem

data class VHRow(
  val title: String,
  val texts: List<String>?,
  val image: VHIcon?,
  val onPress: Int?,
  val metadata: VHPlaceMetadata?,
) : Codable, VHItem

data class VHGridItem(
  val isLoading: Boolean?,
  val title: String,
  val text: String?,
  val image: VHIcon?,
  val onPress: Int?
) : Codable, VHItem

data class VHPlaceMetadata(
  val distance: VHDistance,
  val icon: String,
  val latitude: Double,
  val longitude: Double
) : Codable

data class VHDistance(
  val displayDistance: Double,
  val displayUnit: Int
) : Codable

data class VHDateTimeWithZone(
  val id: String,
  val timeSinceEpochMillis: Int
) : Codable

data class VHTravelEstimate(
  val remainingDistance: VHDistance,
  val destinationTime: VHDateTimeWithZone,
  val remainingTimeSeconds: Int
) : Codable

data class VHSectionedItemList(
  val header: String,
  val children: VHItemList
) : Codable

interface VHNavigationInfo

data class VHRoutingInfo(
  val step: VHStep,
  val isLoading: Boolean,
  val distance: VHDistance,
  val junctionImage: String?,
  val nextStep: VHStep?
) : Codable, VHNavigationInfo

data class VHMessageInfo(
  val title: String,
  val icon: String?
) : Codable, VHNavigationInfo

data class VHStep(
  val lane: VHLane,
  val cue: String?,
  val lanesImage: String?,
  val maneuver: VHManeuver,
  val road: String?
) : Codable

data class VHLane(
  val shape: Int,
  val isRecommended: Boolean
) : Codable

data class VHManeuver(
  val type: Int,
  val icon: String,
  val roundaboutExitAngle: Double,
  val roundaboutExitNumber: Int
) : Codable

// Grid Template
data class VHGrid(
  val isLoading: Boolean?,
  val title: String?,
  val headerAction: Int?,
  val actionStrip: VHActionStrip,
  val children: VHItemList
) : Codable

// List Template
data class VHList(
  val title: String,
  val isLoading: Boolean?,
  val headerAction: Int?,
  val actionStrip: VHActionStrip?,
  val children: List<VHSectionedItemList>,
) : Codable

// PlaceListMap Template
data class VHPlaceListMap(
  val title: String,
  val headerAction: Int?,
  val isLoading: Boolean?,
  val actionStrip: VHActionStrip?,
  val children: List<VHRow>
) : Codable

// Pan Template
data class VHPane(
  val title: String,
  val headerAction: Int?,
  val actionStrip: VHActionStrip?,
  val children: List<VHItemList>
) : Codable

// Navigation Template
data class VHNavigation(
  val id: String,
  val actionStrip: VHActionStrip?,
  val mapActionStrip: VHActionStrip?,
  val navigationInfo: VHNavigationInfo?,
  val destinationTravelEstimate: VHTravelEstimate?
) : Codable



