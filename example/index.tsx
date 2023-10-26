/**
 * @format
 */
import React from 'react';
import { AppRegistry } from 'react-native';
import App from './src/App';
import Vehicle from './src/Vehicle';

import { render } from 'react-native-vehicle';

AppRegistry.registerRunnable('vehicle', () => {
  render(React.createElement(Vehicle));
});

AppRegistry.registerComponent('main', () => App);