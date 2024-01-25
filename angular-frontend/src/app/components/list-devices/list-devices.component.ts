import {Component, Input, OnInit} from '@angular/core';
import {AccountListItemModel} from "../../models/account-list-item.model";
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {DeviceListItemModel} from "../../models/device-list-item.model";
import {DeviceService} from "../../services/device.service";
import {DeviceTrackStatusChangeModel} from "../../models/device-track-status-change.model";

@Component({
  selector: 'app-list-devices',
  templateUrl: './list-devices.component.html',
  styleUrls: ['./list-devices.component.css']
})
export class ListDevicesComponent implements OnInit {

  // @ts-ignore
  @Input() public loggedInUser: AuthenticatedUserModel;

  devicesList!: DeviceListItemModel[];

  constructor(private deviceService: DeviceService) {
  }

  ngOnInit(): void {
    this.deviceService.getDevicesByUserId(this.loggedInUser.id).subscribe({
      next: value => {
        this.devicesList = value;
      },
      error: err => {
        console.log(err);
      },
      complete: () => {
        console.log("Device data retrieved successfully");
      }
    });
  }


  toggleTracking(i: number) {
    this.devicesList[i].isTracked = !this.devicesList[i].isTracked;
    const deviceStatusChange: DeviceTrackStatusChangeModel = {
      deviceId: this.devicesList[i].deviceId,
      isTracked: this.devicesList[i].isTracked,
    };
    console.log("device id: " + this.devicesList[i].deviceId);
    console.log("device istracked: " + this.devicesList[i].isTracked);
    this.deviceService.setTrackedStatus(deviceStatusChange).subscribe(
      {
        next: () => {
        },
        error: () => {
        },
        complete: () => {
          console.log("Tracking status changed on backend.")
        }
      });

  }
}
