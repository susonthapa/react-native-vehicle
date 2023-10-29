import React from "react";
import { useCarNavigation } from "react-native-vehicle";
import { ActionType } from "../../../src/types";


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

export default ListTemplate