# react-native-vehicle
Android Auto and CarPlay library support for ReactNative. This library only supports Android Auto at the moment.

## Android Auto

![Kapture 2023-11-02 at 17 51 48](https://github.com/susonthapa/react-native-vehicle/assets/33973551/2c68fb1b-edca-40f3-b2ca-120ba7d87a43)


⚠️This is still a WIP, so use it at your own risk.⚠️

## Installation
This library is not published to NPM yet. So, you will have to add it like this.

```sh
yarn add https://github.com/susonthapa/react-native-vehicle
```

## Usage
This library uses a custom reconciler to expose declarative API to the JS side. Here is how you would create a List Template.

```typescript
const ListTemplate = () => {
  const navigation = useCarNavigation()
  return (
    <list-template title={'List Template'} headerAction={{ actionType: ActionType.BACK }}>
      <sectioned-item-list header='Sectioned Item List'>
        <item-list>
          <row key={1} title='Row One' onPress={navigation.pop} />
        </item-list>
        <item-list>
          <row key={2} title='Row Two' onPress={navigation.pop} />
        </item-list>
        <item-list>
          <row key={3} title='Row Three' onPress={navigation.pop} />
        </item-list>
      </sectioned-item-list>
    </list-template>
  );
};
```

### Navigation
This library exposes `ScreenManager` and `Screen` components to handle the navigation. If you're familiar with `react-navigation` then you should feel right at home.

This is how you define  your navigation stack. Note the main component should use the name `root`.

```typescript
const Vehicle = () => {
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
```

### Templates
#### Grid Template
<img width="868" alt="image" src="https://github.com/susonthapa/react-native-vehicle/assets/33973551/687eeb5c-20d7-44e7-b245-2f32f4fc77af">

```typescript
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
```

#### List Template
<img width="912" alt="image" src="https://github.com/susonthapa/react-native-vehicle/assets/33973551/697ae5e9-b3d8-4fa8-aa24-966f014d5de6">

```typescript
const ListTemplate = () => {
  const navigation = useCarNavigation()
  return (
    <list-template title={'List Template'} headerAction={{ actionType: ActionType.BACK }}>
      <sectioned-item-list header='Sectioned Item List'>
        <item-list>
          <row key={1} title='Row One' onPress={navigation.pop} />
        </item-list>
        <item-list>
          <row key={2} title='Row Two' onPress={navigation.pop} />
        </item-list>
        <item-list>
          <row key={3} title='Row Three' onPress={navigation.pop} />
        </item-list>
      </sectioned-item-list>
    </list-template>
  );
};
```

#### Pane Template
<img width="912" alt="image" src="https://github.com/susonthapa/react-native-vehicle/assets/33973551/89aa19dd-b0ba-40a7-8ec1-18c7f0609d56">

```typescript
const PaneTemplate = () => {
  return (
    <pane-template title="Pane Template" headerAction={{ actionType: ActionType.BACK }}>
      <pane>
        <row title="Pane Item One" />
        <row title="Pane Item Two" />
        <row title="Pane Item Three" />
        <row title="Pane Item Four" />
      </pane>
    </pane-template>
  )
}
```

#### PlaceListMap Template
<img width="912" alt="image" src="https://github.com/susonthapa/react-native-vehicle/assets/33973551/e6a81e48-1af8-46b4-b751-868cba837394">

```typescript
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
```

#### Navigation Template
<img width="912" alt="image" src="https://github.com/susonthapa/react-native-vehicle/assets/33973551/2da8aef0-fd7d-486e-8119-27a9a588e370">

Please make sure the template has a proper ID set.

```typescript
const TestMap = () => {
  const [count, setCount] = useState(0)
  useEffect(() => {
    const interval = setInterval(() => {
      setCount((count) => count + 1)
    }, 1000)
    return () => {
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
```


## Contributing

See the [contributing guide](CONTRIBUTING.md) to learn how to contribute to the repository and the development workflow.

## License

MIT

---

Made with [create-react-native-library](https://github.com/callstack/react-native-builder-bob)
