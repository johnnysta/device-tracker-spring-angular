import {Component, OnInit} from '@angular/core';
import {DeviceRegistrationInitDataModel} from "../../models/device-registration-init-data.model";
import {DeviceService} from "../../services/device.service";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {DeviceRegistrationDataModel} from "../../models/device-registration-data.model";
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {AccountService} from "../../services/account.service";
import {Router} from "@angular/router";


@Component({
  selector: 'app-register-device',
  templateUrl: './register-device.component.html',
  styleUrls: ['./register-device.component.css']
})
export class RegisterDeviceComponent implements OnInit {
  initData!: DeviceRegistrationInitDataModel;
  deviceForm: FormGroup;
  loggedInUser!: AuthenticatedUserModel;


  constructor(private deviceService: DeviceService,
              private formBuilder: FormBuilder,
              private accountService: AccountService,
              private router: Router) {
    this.deviceForm = this.formBuilder.group({
      deviceName: ['', Validators.required],
      imeiNumber: ['', Validators.required],
      deviceType: [],
      usageTypeList: this.formBuilder.array([])
    })


  }

  ngOnInit(): void {
    this.accountService.loggedInUser.subscribe(
      {
        next: user => this.loggedInUser = user,
        error: err => console.log(err)
      });
    console.log("user: " + this.loggedInUser.userName);
    console.log("id: " + this.loggedInUser.id);

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
    console.log("id2: " + this.loggedInUser.id);
    deviceData.userId = this.loggedInUser.id;
    console.log("id3: " + deviceData.userId);
    console.log(this.deviceForm.value.usageTypeList.toString());
    console.log(this.deviceForm.value.deviceType.toString());
    this.deviceService.sendDeviceRegistration(deviceData).subscribe(
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

  //cerating contorls for check box array
  private createCheckboxControls() {
    const formArray = this.deviceForm.get('usageTypeList') as FormArray;
    for (const usageType of this.initData.usageTypeList) {
      const formControl = new FormControl(false); // Initialize with a default value
      formArray.push(formControl);
    }
  }

  //creating array for true-false values array
  private createUsageTypeArrayToSend(): string[] {
    return this.deviceForm.value.usageTypeList
      .map((type: string, index: number) => type ? this.initData.usageTypeList[index].name : null)
      .filter((type: string) => type !== null);
  }

}
