import {Component, OnInit} from '@angular/core';
import {DeviceTrackingSettingsDataModel} from "../../models/device-tracking-settings-data.model";
import {DeviceService} from "../../services/device.service";
import {ActivatedRoute} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-tracking-settings',
  templateUrl: './tracking-settings.component.html',
  styleUrls: ['./tracking-settings.component.css']
})
export class TrackingSettingsComponent implements OnInit {

  deviceTrackingSettingsDataModel!: DeviceTrackingSettingsDataModel;
  trackingSettingsForm: FormGroup;

  constructor(private deviceService: DeviceService,
              private activatedRoute: ActivatedRoute,
              private formBuilder: FormBuilder) {
    this.trackingSettingsForm = this.formBuilder.group({
      meteringFrequency: ['1', Validators.required],
      isGeofenceActive: ['false'],
      geofenceCenterLatitude: ['', Validators.required],
      geofenceCenterLongitude: ['', Validators.required],
      geofenceRadius: ['', Validators.required]
    })
  }

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: param => {
        const idFromParamMap = param.get('deviceId');
        console.log("idFromParamMap: " + idFromParamMap);
        if (idFromParamMap) {
          this.deviceService.getSettingsByDeviceId(Number(idFromParamMap)).subscribe({
            next:
              (data) => {
                this.deviceTrackingSettingsDataModel = data;
              },
            error: () => {
            },
            complete: () => {
              this.fillDeviceForm(this.deviceTrackingSettingsDataModel);
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

  }

  toggleGeofencing() {
    (this.trackingSettingsForm.get("geofenceCenterLatitude")?.disabled) ? this.trackingSettingsForm.get("geofenceCenterLatitude")?.enable() : this.trackingSettingsForm.get("geofenceCenterLatitude")?.disable();
    (this.trackingSettingsForm.get("geofenceCenterLongitude")?.disabled) ? this.trackingSettingsForm.get("geofenceCenterLongitude")?.enable() : this.trackingSettingsForm.get("geofenceCenterLongitude")?.disable();
    (this.trackingSettingsForm.get("geofenceRadius")?.disabled) ? this.trackingSettingsForm.get("geofenceRadius")?.enable() : this.trackingSettingsForm.get("geofenceRadius")?.disable();
  }
}
