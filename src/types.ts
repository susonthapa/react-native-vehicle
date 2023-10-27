import type React from "react";
import type { ImageResolvedAssetSource } from "react-native";
import { SectionList } from "react-native";

type HeaderAction = "back" | "app_icon";
type CarColor =
  | "blue"
  | "green"
  | "primary"
  | "red"
  | "secondary"
  | "yellow"
  | "default";
type PressHandler = (event: {}) => any
interface PlaceMetadata {
  type: "place";
  distance: Distance,
  icon: ImageResolvedAssetSource,
  latitude: number;
  longitude: number;
}

interface ActionStrip {
  actions: Omit<Action, "type">[];
}

type Step = {
  lane: {
    shape: number,
    isRecommended: boolean,
  },
  cue?: string,
  lanesImage: ImageResolvedAssetSource,
  maneuver?: {
    type: number,
    icon: ImageResolvedAssetSource,
    roundaboutExitAngle: number,
    roundaboutExitNumber: number,
  },
  road?: string,
}

type Distance = {
  displayDistance: number,
  displayUnit: number,
}

type RoutingInfo = {
  type: "routingInfo",
  step: Step,
  isLoading: boolean,
  distance: Distance,
  junctionImage?: ImageResolvedAssetSource,
  nextStep?: Step,
}

type MessageInfo = {
  type: "messageInfo",
  title: string,
  icon?: ImageResolvedAssetSource,
}

type TravelEstimate = {
  remainingDistance: Distance,
  destinationTime: {
    timeSinceEpochMillis: number,
    id: string,
  },
  remainingTimeSeconds: number,
}

type Metadata = PlaceMetadata;

interface CommonAttributes {
  key?: string | number;
}

interface Action extends CommonAttributes {
  type: "action";
  title?: string;
  icon?: ImageResolvedAssetSource;
  backgroundColor?: CarColor;
  onPress?: (event: {}) => any;
}

interface Row extends CommonAttributes {
  type: "row";
  title: string;
  texts?: string[];
  image?: ImageResolvedAssetSource;
  onPress?: PressHandler;
  metadata?: Metadata | undefined;
}

interface GridItem extends CommonAttributes {
  type: "grid-item",
  isLoading?: boolean,
  title: string,
  text?: string,
  image?: ImageResolvedAssetSource,
  onPress?: PressHandler,
}

interface ItemList extends CommonAttributes {
  type: "item-list";
  noItemMessage?: string,
  children: (GridItem | Row)[];
}

interface SectionedItemList extends CommonAttributes {
  type: "sectioned-item-list"
  header: string,
  children: ItemList
}

interface ListTemplate extends CommonAttributes {
  type: "list-template";
  title: string;
  isLoading?: boolean;
  headerAction?: HeaderAction;
  actionStrip?: ActionStrip;
  children: SectionedItemList[];
}

interface GridTemplate extends CommonAttributes {
  type: "grid-template",
  isLoading?: boolean,
  title?: string,
  headerAction?: HeaderAction,
  actionStrip?: ActionStrip,
  children: ItemList,
}

interface PlaceListMapTemplate extends CommonAttributes {
  type: "place-list-map-template";
  title: string;
  headerAction?: HeaderAction;
  isLoading?: boolean;
  actionStrip?: ActionStrip;
  children: ItemList;
}

interface Pane extends CommonAttributes {
  type: "pane"
  isLoading?: boolean,
  actionList?: Action[],
  image?: ImageResolvedAssetSource
  children: Row[]
}

interface PaneTemplate extends CommonAttributes {
  type: "pane-template";
  title: string;
  headerAction?: HeaderAction;
  actionStrip?: ActionStrip;
  children: Pane;
}

interface NavigationTemplate extends CommonAttributes {
  type: "navigation-template",
  id: string,
  actionStrip: ActionStrip,
  mapActionStrip?: ActionStrip,
  navigationInfo?: RoutingInfo | MessageInfo,
  destinationTravelEstimate?: TravelEstimate,
  component: React.ComponentType<any>,
}

interface Screen extends CommonAttributes {
  type: "screen";
  name: string;
  render: (props?: any) => React.ReactElement<VehicleTemplate>;
  children: VehicleTemplate[] | VehicleTemplate;
}

interface ScreenManager extends CommonAttributes {
  type: "screen-manager";
  children: Screen[];
}

export type VehicleTemplate =
  | PaneTemplate
  | ListTemplate
  | GridTemplate
  | NavigationTemplate
  | PlaceListMapTemplate;

export type ExtractElementByType<Type extends VehicleElement["type"]> =
  Extract<VehicleElement, { type: Type }>;
export type VehicleElement =
  | VehicleTemplate
  | Row
  | GridItem
  | ItemList
  | ScreenManager
  | Screen
  | Pane
  | Action
  | SectionedItemList;
export type ElementType = VehicleElement["type"];
export interface Route {
  name: string;
  routeParams?: any;
  render?: React.FC;
}
export interface RootContainer {
  type: "root-container";
  stack: Route[];
  prevStack: Route[];
  children?: VehicleElement[];
}