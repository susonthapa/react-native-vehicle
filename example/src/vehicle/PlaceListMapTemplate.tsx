import React from "react";
import { Image } from "react-native";
import { ActionType, DistanceUnit, MarkerIconType } from "../../../src/types";

const PlaceListMapTemplate = () => {
  return (
    <place-list-map-template title='Hello World' headerAction={{ actionType: ActionType.BACK }}>
      <item-list>
        <row title='This is row One %d' metadata={{
          place: {
            location: {
              lat: 39.3266,
              lng: -110.9646
            },
            marker: {
              icon: Image.resolveAssetSource(require('./../images/click.png')),
              iconType: MarkerIconType.IMAGE,
            }
          },
          distance: {
            displayDistance: 100,
            displayUnit: DistanceUnit.UNIT_KILOMETERS,
          },
        }} onPress={() => { }} />

        <row title='This is row Two %d' metadata={{
          place: {
            location: {
              lat: 29.3266,
              lng: -110.9646
            },
            marker: {
              icon: Image.resolveAssetSource(require('./../images/click.png')),
              iconType: MarkerIconType.IMAGE,
            }
          },
          distance: {
            displayDistance: 100,
            displayUnit: DistanceUnit.UNIT_KILOMETERS,
          },
        }} onPress={() => { }} />
      </item-list>
    </place-list-map-template>
  )
}

export default PlaceListMapTemplate