import React from "react";
import { Image } from "react-native";
import { useCarNavigation } from "react-native-vehicle";
import { ActionType } from "../../../src/types";

const GridTemplate = () => {
  const navigation = useCarNavigation()
  return (
    <grid-template title="Home" headerAction={{ actionType: ActionType.APP_ICON }}>
      <item-list>
        <grid-item
          title="List Template"
          image={Image.resolveAssetSource(require('./../images/gear.png'))}
          onPress={() => navigation.push('list-template')} />
        <grid-item
          title="Place List Map Template"
          image={Image.resolveAssetSource(require('./../images/gear.png'))}
          onPress={() => navigation.push('place-list-map-template')}
        />
        <grid-item
          title="Pane Template"
          image={Image.resolveAssetSource(require('./../images/gear.png'))}
          onPress={() => navigation.push('pane-template')}
        />
        <grid-item
          title="Navigation Template"
          image={Image.resolveAssetSource(require('./../images/gear.png'))}
          onPress={() => navigation.push('navigation-template')}
        />
      </item-list>
    </grid-template>
  )
}

export default GridTemplate