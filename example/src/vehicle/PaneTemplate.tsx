import React from "react";
import { ActionType } from "../../../src/types";

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

export default PaneTemplate