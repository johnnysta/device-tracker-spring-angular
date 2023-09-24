import {DeviceTypeListItemModel} from "./device-type-list-item.model";
import {UsageTypeListItemModel} from "./usage-type-list-item.model";


export interface DeviceRegistrationInitDataModel {

  deviceTypeList: DeviceTypeListItemModel[];
  usageTypeList: UsageTypeListItemModel[];

}
