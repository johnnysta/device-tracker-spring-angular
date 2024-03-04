import {Component, OnInit} from '@angular/core';
import {DeviceTrackingSettingsDataModel} from "../../models/device-tracking-settings-data.model";
import {DeviceService} from "../../services/device.service";
import {ActivatedRoute, Route, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {DeviceDetailsDataModel} from "../../models/device-details-data.model";

@Component({
  selector: 'app-tracking-settings',
  templateUrl: './tracking-settings.component.html',
  styleUrls: ['./tracking-settings.component.css']
})
export class TrackingSettingsComponent implements OnInit {

  deviceTrackingSettingsDataModel!: DeviceTrackingSettingsDataModel;
  trackingSettingsForm: FormGroup;
  currentDeviceId!: string | null;

  constructor(private deviceService: DeviceService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private formBuilder: FormBuilder) {
    this.trackingSettingsForm = this.formBuilder.group({
      meteringFrequency: ['1', Validators.required],
      isGeofenceActive: [],
      geofenceCenterLatitude: ['', Validators.required],
      geofenceCenterLongitude: ['', Validators.required],
      geofenceRadius: ['', Validators.required]
    })
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: param => {
        this.currentDeviceId = param.get('deviceId');
        console.log("currentDeviceId in onInit of tracking settings: " + this.currentDeviceId);
        if (this.currentDeviceId) {
          this.deviceService.getSettingsByDeviceId(Number(this.currentDeviceId)).subscribe({
            next:
              (data) => {
                console.log("Device's tracking settings retrieved successfully.")
                this.deviceTrackingSettingsDataModel = data;
                console.log(this.deviceTrackingSettingsDataModel.isGeofenceActive);
                this.fillDeviceForm(this.deviceTrackingSettingsDataModel);
                this.disableControlsByGeofencingOnOff();
              },
            error: () => {
              console.log("Error retrieving device's tracking settings.")
            },
            complete: () => {
            }
          });
        }
      }
    })
  }


  private fillDeviceForm(deviceTrackingSettingsDataModel: DeviceTrackingSettingsDataModel) {
    this.trackingSettingsForm.patchValue({
      'meteringFrequency': deviceTrackingSettingsDataModel.meteringFrequency,
      'isGeofenceActive': deviceTrackingSettingsDataModel.isGeofenceActive,
      'geofenceCenterLatitude': deviceTrackingSettingsDataModel.geofenceCenterLatitude,
      'geofenceCenterLongitude': deviceTrackingSettingsDataModel.geofenceCenterLongitude,
      'geofenceRadius': deviceTrackingSettingsDataModel.geofenceRadius
    });
  }

  submitData() {
    // const deviceSettingsData: DeviceTrackingSettingsDataModel = this.trackingSettingsForm.value;
    const deviceSettingsData: DeviceTrackingSettingsDataModel = this.trackingSettingsForm.getRawValue();
    console.log("IsgeofenceActive in submitData");
    console.log(deviceSettingsData);
    this.deviceService.setSettingsByDeviceId(Number(this.currentDeviceId), deviceSettingsData).subscribe(
      {
        next: () => {
        },
        error: (err) => {
          console.log(err)
        },
        complete: () => {
          this.router.navigate(['home']);
        }
      });
  }

  disableControlsByGeofencingOnOff() {
    console.log("In toggleGeofencing");
    if (this.trackingSettingsForm.get("isGeofenceActive")?.value) {
      this.trackingSettingsForm.get("geofenceCenterLatitude")?.enable();
      this.trackingSettingsForm.get("geofenceCenterLongitude")?.enable();
      this.trackingSettingsForm.get("geofenceRadius")?.enable();
    } else {
      this.trackingSettingsForm.get("geofenceCenterLatitude")?.disable();
      this.trackingSettingsForm.get("geofenceCenterLongitude")?.disable();
      this.trackingSettingsForm.get("geofenceRadius")?.disable();
    }
  }

  closeWithoutSaving() {
    this.router.navigate(['home']);
  }
}
