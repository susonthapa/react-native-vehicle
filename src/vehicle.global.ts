import type React from "react";
import type { VehicleElement, ExtractElementByType } from "./types";

type NativeToJSXElement<Type extends VehicleElement["type"]> = Omit<
  ExtractElementByType<Type>,
  "children" | "type"
> & {
  children?: React.ReactNode;
};

declare global {
  // eslint-disable-next-line @typescript-eslint/no-namespace
  namespace JSX {
    interface IntrinsicElements {
      "item-list": NativeToJSXElement<"item-list">;
      "action": NativeToJSXElement<"action">;
      "pane": NativeToJSXElement<"pane">;
      "row": NativeToJSXElement<"row">;
      "grid-item": NativeToJSXElement<"grid-item">
      "sectioned-item-list": NativeToJSXElement<"sectioned-item-list">
      "toggle": NativeToJSXElement<"toggle">

      // Templates
      "navigation-template": NativeToJSXElement<"navigation-template">;
      "list-template": NativeToJSXElement<"list-template">;
      "place-list-map-template": NativeToJSXElement<"place-list-map-template">;
      "grid-template": NativeToJSXElement<"grid-template">
      "pane-template": NativeToJSXElement<"pane-template">;
    }
  }
}

export { };
