import {Component, OnInit} from '@angular/core';
import {DeviceRegistrationInitDataModel} from "../../models/device-registration-init-data.model";
import {DeviceService} from "../../services/device.service";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DeviceRegistrationDataModel} from "../../models/device-registration-data.model";


@Component({
  selector: 'app-register-device',
  templateUrl: './register-device.component.html',
  styleUrls: ['./register-device.component.css']
})
export class RegisterDeviceComponent implements OnInit {
  initData!: DeviceRegistrationInitDataModel;
  deviceForm: FormGroup;


  constructor(private deviceService: DeviceService, private formBuilder: FormBuilder) {
    this.deviceForm = this.formBuilder.group({
      deviceName: ['', Validators.required],
      userId: [null, Validators.required],
      deviceType: [],
      usageTypeList: this.formBuilder.array([])
    })


  }

  ngOnInit(): void {
    this.deviceService.getDeviceFormInitData().subscribe({
      next: value => {
        this.initData = value;
        this.createCheckboxControls();
      },
      error: err => {
        console.log(err)
      },
      complete: () => {
        console.log(this.initData)
      }
    })
  }

  submitData() {
    const deviceData: DeviceRegistrationDataModel = this.deviceForm.value;
    deviceData.usageTypeList = this.createUsageTypeArrayToSend();
    console.log(this.deviceForm.value.usageTypeList.toString());
    //console.log(this.createUsageTypeArrayToSend().toString());
    // this.deviceService.sendDeviceRegistration(deviceData).subscribe(
    //   {next: ()=> {},
    //     error: (err) => {console.log(err)},
    //     complete: () =>{}
    //   });
  }

  //cerating contorls for check box array
  private createCheckboxControls() {
    const formArray = this.deviceForm.get('usageTypeList') as FormArray;
    for (const usageType of this.initData.usageTypeList) {
      const formControl = new FormControl(false); // Initialize with a default value
      formArray.push(formControl);
    }
  }

  //ctring array for true-false values array
  private createUsageTypeArrayToSend(): string[] {
    return this.deviceForm.value.usageTypeList
      .map((type: string, index: number) => type ? this.initData.usageTypeList[index].name : null)
      .filter((type: string) => type !== null);
  }

}
