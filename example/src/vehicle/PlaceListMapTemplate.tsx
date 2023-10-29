import React from "react";
import { Image } from "react-native";
import { ActionType } from "../../../src/types";

const PlaceListMapTemplate = () => {
  return (
    <place-list-map-template title='Hello World' headerAction={{ actionType: ActionType.BACK }}>
      <item-list>
        <row title='This is row One %d' metadata={{
          type: 'place',
          distance: {
            displayDistance: 100,
            displayUnit: 3,
          },
          icon: Image.resolveAssetSource(require('./../images/click.png')),
          latitude: 39.3266,
          longitude: -110.9646,
        }} onPress={() => { }} />
        <row title='This is row Two %d' metadata={{
          type: 'place',
          distance: {
            displayDistance: 100,
            displayUnit: 3,
          },
          icon: Image.resolveAssetSource(require('./../images/click.png')),
          latitude: 39.3336,
          longitude: -110.9694,
        }} onPress={() => { }} />
      </item-list>
    </place-list-map-template>
  )
}

export default PlaceListMapTemplate