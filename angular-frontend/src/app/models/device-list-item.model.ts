export interface DeviceListItemModel {
  deviceName: string;
  deviceId: number;
  userId: number;
  deviceType: string;
  usageTypeList: string[];
  isTracked: boolean;
}
