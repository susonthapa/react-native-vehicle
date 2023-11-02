import React, { useEffect } from 'react';

import {
  Screen,
  ScreenManager
} from 'react-native-vehicle';
import GridTemplate from './GridTemplate';
import ListTemplate from './ListTemplate';
import NavigationTemplate from './NavigationTemplate';
import PaneTemplate from './PaneTemplate';
import PlaceListMapTemplate from './PlaceListMapTemplate';


const Vehicle = () => {

  useEffect(() => {
    console.log(`TODO: Vehicle mounting`);
    return () => {
      console.log(`TODO: Vehicle unmounting`)
    }
  }, [])

  return (
    <ScreenManager>
      <Screen name="root" render={GridTemplate} />
      <Screen name="navigation-template" render={NavigationTemplate} />
      <Screen name="list-template" render={ListTemplate} />
      <Screen name="pane-template" render={PaneTemplate} />
      <Screen name="place-list-map-template" render={PlaceListMapTemplate} />
    </ScreenManager>
  );
};

export default Vehicle;
