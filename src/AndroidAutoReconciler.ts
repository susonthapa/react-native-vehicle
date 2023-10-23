import React from "react";
import Reconciler, { OpaqueRoot } from "react-reconciler";

import { AppRegistry, DeviceEventEmitter } from "react-native";
import {
  DefaultEventPriority
} from 'react-reconciler/constants';
import { AndroidAutoModule } from "./AndroidAuto";
import { RootView } from "./AndroidAutoReact";
import type {
  AndroidAutoElement,
  ExtractElementByType,
  RootContainer
} from "./types";

type Container = RootContainer | AndroidAutoElement;

type ScreenContainer = ExtractElementByType<"screen">;

function applyProps(instance: any, allProps: any) {
  for (const [key, value] of Object.entries(allProps)) {
    if (key !== "children") {
      instance[key] = value;
    }
  }
}

function addChildren(parentInstance: Container | null) {
  if (parentInstance && !("children" in parentInstance)) {
    (parentInstance as any).children = [];
  }

  return (parentInstance as any)?.children ?? [];
}

function appendChild(parentInstance: Container, child: Container) {
  addChildren(parentInstance).push(child);
}

function removeChild(parentInstance: Container, child: Container): void {
  addChildren(parentInstance);

  if ("children" in parentInstance) {
    parentInstance.children = (parentInstance.children as any).filter(
      (currentChild: Container) => currentChild !== child
    );
  }
}

function insertBefore(
  parentInstance: Container,
  child: Container,
  beforeChild: Container
): void {
  addChildren(parentInstance);

  if ("children" in parentInstance && Array.isArray(parentInstance.children)) {
    const index = parentInstance.children.indexOf(beforeChild as any);
    parentInstance.children.splice(index, 1, child as any);
  }
}

const Renderer = Reconciler<
  Container,
  any,
  AndroidAutoElement,
  any,
  any,
  any,
  any,
  any,
  any,
  any,
  any,
  any,
  any
>({
  supportsMutation: true,
  supportsPersistence: false,
  createInstance(
    type,
    allProps,
    _rootContainerInstance,
    _hostContext,
    _internalInstanceHandle
  ) {
    const { children, ...props } = allProps;

    if (type.toString() === 'navigation-template') {
      // register this root
      AppRegistry.registerComponent(allProps.id, () => allProps.component)
    }

    const element = {
      type,
      ...(children ? { children: [] } : {}),
      ...props,
    };

    return element;
  },
  createTextInstance(_text, _fragment) {
    return {};
  },
  appendInitialChild: appendChild,
  finalizeInitialChildren() {
    return false;
  },
  prepareUpdate(_instance, _type, oldProps, newProps) {
    const updateProps: Record<string, unknown> = {};

    let needsUpdate = false;

    for (const key in oldProps) {
      if (!Reflect.has(oldProps, key) || key === "children") {
        continue;
      }

      if (!(key in newProps)) {
        needsUpdate = true;
        updateProps[key] = undefined;
      } else if (oldProps[key] !== newProps[key]) {
        needsUpdate = true;
        updateProps[key] = newProps[key];
      }
    }

    for (const key in newProps) {
      if (!Reflect.has(newProps, key) || key === "children") {
        continue;
      }

      if (!(key in oldProps)) {
        needsUpdate = true;
        updateProps[key] = newProps[key];
      }
    }

    return needsUpdate ? updateProps : null;
  },
  shouldSetTextContent: () => false,
  getRootHostContext: () => {
    return {};
  },
  getChildHostContext: (context) => {
    return context;
  },
  getPublicInstance: (instance) => instance,
  prepareForCommit: () => null,
  resetAfterCommit(containerInfo: Container) {
    if (containerInfo.type !== "root-container") {
      console.log("Root container must be a RootContainer");
      return;
    }

    const topStack = containerInfo.stack[containerInfo.stack.length - 1];
    console.log("Sending updated UI to native thread", topStack);

    if (!topStack) {
      console.log("Stack is still empty");
      return;
    }

    const node = containerInfo.children?.find(
      (item) => item.type === "screen" && item.name === topStack.name
    ) as ScreenContainer;

    if (!node || !node.children) {
      console.log(
        `${topStack.name} screen has no render method or its render method returns nothing`,
        node
      );
      return;
    }

    const template = Array.isArray(node.children)
      ? node.children.flat().filter(Boolean)[0]
      : node.children;

    if (!template) {
      console.log("No proper template found for route ", topStack.name);
      return;
    }

    if (
      containerInfo.prevStack.length === containerInfo.stack.length ||
      (containerInfo.prevStack.length === 0 && node.name === "root")
    ) {
      if (containerInfo.prevStack.length === 0) {
        console.log("Initial render of root");
      }
      AndroidAutoModule.setTemplate(node.name, template);
    } else if (containerInfo.stack.length > containerInfo.prevStack.length) {
      AndroidAutoModule.pushScreen(node.name, template);
    } else if (
      containerInfo.stack.length ===
      containerInfo.prevStack.length - 1
    ) {
      AndroidAutoModule.popScreen();
      AndroidAutoModule.setTemplate(node.name, template);
    }

    containerInfo.prevStack = containerInfo.stack;
  },
  preparePortalMount: () => { },
  scheduleTimeout: setTimeout,
  cancelTimeout: clearTimeout,
  noTimeout: -1,
  supportsMicrotasks: false,
  isPrimaryRenderer: true,

  getCurrentEventPriority: () => DefaultEventPriority,
  getInstanceFromNode: () => undefined,
  beforeActiveInstanceBlur: () => {},
  afterActiveInstanceBlur: () => {},
  prepareScopeUpdate: () => {},
  getInstanceFromScope: () => null,
  detachDeletedInstance: () => {},

  appendChild,
  appendChildToContainer: appendChild,
  insertBefore,
  insertInContainerBefore: insertBefore,

  removeChild,
  removeChildFromContainer: removeChild,
  commitUpdate: applyProps,
  clearContainer(container?: Container) {
    if (container && "children" in container) {
      container.children = [];
    }
  },

  supportsHydration: false,
});

let root: OpaqueRoot | undefined
export function render(element: React.ReactNode) {
  DeviceEventEmitter.removeAllListeners('android_auto:ready')

  function callReconciler(
    element: React.ReactNode,
    containerInfo: RootContainer
  ) {
    if (!root) {
      root = Renderer.createContainer(containerInfo as any, 0, null, false, null, '', (error) => {
        console.log("ERROR: ", error);
      }, null);
      console.log("Initializing AndroidAuto module");
      AndroidAutoModule.init();
    }

    Renderer.updateContainer(element as any, root, null, () => {
      AndroidAutoModule.invalidate("root");
    });

    if (!root) {
      Renderer.getPublicRootInstance(root);
    }
  }

  DeviceEventEmitter.addListener("android_auto:ready", () => {
    console.log("CarContext: Ready");
    const initialStack: any[] = [];
    const containerInfo = {
      type: "root-container",
      stack: initialStack,
      prevStack: initialStack,
    } as RootContainer;

    callReconciler(
      React.createElement(RootView, { containerInfo }, element),
      containerInfo
    );
  });
}