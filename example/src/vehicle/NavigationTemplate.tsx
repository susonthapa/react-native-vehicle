import React, { useEffect, useState } from "react";
import { Image, Text, View } from "react-native";
import { useCarNavigation } from "react-native-vehicle";
import { ActionType } from "../../../src/types";

const TestMap = () => {
  const [count, setCount] = useState(0)
  useEffect(() => {
    console.log(`Mounting TestMap`);
    const interval = setInterval(() => {
      setCount((count) => count + 1)
    }, 1000)
    return () => {
      console.log(`Unmounting TestMap`);
      clearInterval(interval)
    }
  }, [])
  return (
    <View style={{ width: 100, height: 100, backgroundColor: 'red' }}>
      <Text>This is a TestMap {count}</Text>
    </View>
  )
}

const NavigationTemplate = () => {
  const navigation = useCarNavigation()
  useEffect(() => {
    console.log('Mounting NavigationTemplate')
    return () => console.log('Unmounting NavigationTemplate')
  }, [])
  return (
    <navigation-template actionStrip={{
      actions: [
        {
          actionType: ActionType.BACK,
        },
        {
          title: 'Action One',
          onPress: () => navigation.push('grid-menu'),
        },
        {
          icon: Image.resolveAssetSource(require('./../images/click.png')),
        },
        {
          icon: Image.resolveAssetSource(require('./../images/click.png')),
        },
      ]
    }}
      mapActionStrip={{
        actions: [
          {
            icon: Image.resolveAssetSource(require('./../images/click.png')),
          },
          {
            icon: Image.resolveAssetSource(require('./../images/click.png')),
          },
          {
            icon: Image.resolveAssetSource(require('./../images/click.png')),
          },
          {
            icon: Image.resolveAssetSource(require('./../images/click.png')),
          },
        ]
      }}
      // navigationInfo={{
      //   type: 'routingInfo',
      //   info: {
      //     step: {
      //       lane: {
      //         shape: 8,
      //         isRecommended: true,
      //       },
      //       cue: 'Hello Step',
      //       lanesImage: Image.resolveAssetSource(require('./../images/click.png')),
      //       maneuver: {
      //         type: 6,
      //         icon: Image.resolveAssetSource(require('./../images/click.png')),
      //         roundaboutExitAngle: 10,
      //         roundaboutExitNumber: 2,
      //       },
      //       road: 'Custom Road',
      //     },
      //     distance: {
      //       displayDistance: 10,
      //       displayUnit: 2,
      //     },
      //     // junctionImage: Image.resolveAssetSource(require('./../images/click.png')),
      //     isLoading: false,
      //     nextStep: {
      //       lane: {
      //         shape: 8,
      //         isRecommended: true,
      //       },
      //       cue: 'Next Step',
      //       lanesImage: Image.resolveAssetSource(require('./../images/click.png')),
      //       maneuver: {
      //         type: 6,
      //         icon: Image.resolveAssetSource(require('./../images/click.png')),
      //         roundaboutExitAngle: 10,
      //         roundaboutExitNumber: 2,
      //       },
      //       road: 'Next Custom Road',
      //     }
      //   }
      // }}
      destinationTravelEstimate={{
        remainingDistance: {
          displayDistance: 10,
          displayUnit: 3,
        },
        destinationTime: {
          timeSinceEpochMillis: 1666056013736,
          id: 'America/Cayman',
        },
        remainingTimeSeconds: 60000,
      }}

      id='test-vehicle'
      component={TestMap}
    />
  );
};

export default NavigationTemplate