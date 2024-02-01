import {Component, Input, OnInit} from '@angular/core';
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {DeviceListItemModel} from "../../models/device-list-item.model";
import {DeviceService} from "../../services/device.service";
import {DeviceTrackStatusChangeModel} from "../../models/device-track-status-change.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-list-devices',
  templateUrl: './list-devices.component.html',
  styleUrls: ['./list-devices.component.css']
})
export class ListDevicesComponent implements OnInit {

  // @ts-ignore
  @Input() public loggedInUser: AuthenticatedUserModel;

  devicesList!: DeviceListItemModel[];

  currentDeviceId!: number;
  currentDeviceName!: string;

  constructor(private deviceService: DeviceService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.loadDevices();
  }

  loadDevices() {
    this.deviceService.getDevicesByUserId(this.loggedInUser.id).subscribe({
      next: value => {
        this.devicesList = value;
      },
      error: err => {
        console.log(err);
      },
      complete: () => {
        console.log("Device data retrieved successfully");
        this.devicesList.forEach((item, index, array) => {
          console.log(item.deviceName);
          console.log((item.isTracked) ? "true" : "false")
        });
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

  openDeleteModal(id: number, name: string) {
    const modelDiv = document.getElementById('confirmDeleteModal');
    if (modelDiv != null) {
      console.log("Delete Modal opened..")
      modelDiv.style.display = 'block';
      this.currentDeviceId = id;
      this.currentDeviceName = name;
    }
  }

  closeDeleteModal() {
    const modelDiv = document.getElementById('confirmDeleteModal');
    if (modelDiv != null) {
      modelDiv.style.display = 'none';
    }
  }


  deleteDevice() {
    this.deviceService.deleteDeviceById(this.currentDeviceId).subscribe({
      next: () => {
      },
      error: (err: any) => {
        console.log(err)
      },
      complete: () => {
        console.log("Device with id " + this.currentDeviceId + " was deleted successfully.");
        this.loadDevices();
      }
    });
    this.closeDeleteModal();
  }


  updateDevice(id: number) {
    this.router.navigate(['device-registration-update', id]);
  }

  goToDeviceRegistration() {
    this.router.navigate(['device-registration-update']);
  }
}
