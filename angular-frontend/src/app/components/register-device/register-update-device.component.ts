import {Component, OnInit} from '@angular/core';
import {DeviceRegistrationInitDataModel} from "../../models/device-registration-init-data.model";
import {DeviceService} from "../../services/device.service";
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AuthenticatedUserModel} from "../../models/authenticated-user.model";
import {AccountService} from "../../services/account.service";
import {ActivatedRoute, Router} from "@angular/router";
import {DeviceDetailsDataModel} from "../../models/device-details-data.model";


@Component({
  selector: 'app-register-device',
  templateUrl: './register-update-device.component.html',
  styleUrls: ['./register-update-device.component.css']
})
export class RegisterUpdateDeviceComponent implements OnInit {

  initData!: DeviceRegistrationInitDataModel;
  deviceForm: FormGroup;
  loggedInUser!: AuthenticatedUserModel;
  existingDeviceData!: DeviceDetailsDataModel;


  constructor(private deviceService: DeviceService,
              private formBuilder: FormBuilder,
              private accountService: AccountService,
              private router: Router,
              private route: ActivatedRoute) {
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

    this.route.paramMap.subscribe({
      next: param => {
        const idFromParamMap = param.get('id');
        console.log("idFromParamMap: " + idFromParamMap);
        if (idFromParamMap) {
          this.deviceService.getDeviceById(Number(idFromParamMap)).subscribe({
            next:
              (data) => {
                this.existingDeviceData = data;
              },
            error: () => {
            },
            complete: () => {
              this.fillDeviceForm(this.existingDeviceData);
            }
          });
        }
      }
    })

  }

  submitData() {
    const deviceData: DeviceDetailsDataModel = this.deviceForm.value;
    deviceData.usageTypeList = this.createUsageTypeArrayToSend();
    deviceData.userId = this.loggedInUser.id;
    if (this.existingDeviceData) {
      deviceData.deviceId = this.existingDeviceData.deviceId;
      this.deviceService.sendDeviceUpdate(deviceData).subscribe(
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
    } else {
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


    console.log("id2: " + this.loggedInUser.id);

    console.log("id3: " + deviceData.userId);
    console.log(this.deviceForm.value.usageTypeList.toString());
    console.log(this.deviceForm.value.deviceType.toString());

  }

  //cerating contorls for check box array
  private createCheckboxControls() {
    const formArray = this.deviceForm.get('usageTypeList') as FormArray;
    for (const usageType of this.initData.usageTypeList) {
      const formControl = new FormControl(false); // Initialize with a default value
      formArray.push(formControl);
    }
  }

  private createUsageTypeArrayToSend(): string[] {
    console.log("UsageTypeList from form: " + this.deviceForm.value.usageTypeList);
    return this.deviceForm.value.usageTypeList
      .map((type: string, index: number) =>
        type ? this.initData.usageTypeList[index].name : null
      )
      .filter((type: string) => type !== null);
  }

  private fillDeviceForm(existingDeviceData: DeviceDetailsDataModel) {
    this.deviceForm.patchValue({
      'deviceName': existingDeviceData.deviceName,
      'imeiNumber': existingDeviceData.imeiNumber,
      'deviceType': existingDeviceData.deviceType
    });
    this.fillCheckboxControls();
  }

  private fillCheckboxControls() {
    const formArray = this.deviceForm.get('usageTypeList') as FormArray;
    for (let i = 0; i < formArray.length; i++) {
      const checkboxControl = formArray.at(i);
      // console.log("checkboxControl: " + JSON.stringify(checkboxControl));
      if (this.existingDeviceData.usageTypeList.includes(this.initData.usageTypeList[i].name)) {
        checkboxControl.setValue(true);
      } else {
        checkboxControl.setValue(false);
      }
    }
  }


  existingDeviceDataContains(usageTypeName: string): boolean {
    // console.log(existingDeviceData.usageTypeList);
    if (this.existingDeviceData) {
      return this.existingDeviceData.usageTypeList.includes(usageTypeName);
    } else {
      return false;
    }
  }
}
