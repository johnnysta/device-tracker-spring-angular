import {Component, AfterViewInit, ViewChild, ElementRef, OnInit} from '@angular/core';
import {} from 'googlemaps';
import {ActivatedRoute} from "@angular/router";
import {TrackingDataListItemModel} from "../../models/tracking-data-list-item.model";
import {TrackingService} from "../../services/tracking.service";


@Component({
  selector: 'app-show-device-on-map',
  templateUrl: './show-device-on-map.component.html',
  styleUrls: ['./show-device-on-map.component.css']
})


export class ShowDeviceOnMapComponent implements OnInit, AfterViewInit {

  @ViewChild('gmapContainer', {static: false}) gmap!: ElementRef;

  deviceName!: string | null;
  deviceId!: number;
  map!: google.maps.Map;
  trackingDataList!: TrackingDataListItemModel[];

  private ngOnInitPromise!: Promise<void>;

  constructor(private route: ActivatedRoute,
              private trackingService: TrackingService) {
  }

  ngOnInit() {
    this.ngOnInitPromise = new Promise<void>((resolve) => {
      this.route.paramMap.subscribe({
        next: param => {
          this.deviceName = param.get('deviceName');
          this.deviceId = Number(param.get('deviceId'));
          console.log("this.deviceName" + this.deviceName);
          console.log(" this.deviceId" + this.deviceId);
          this.trackingService.getTrackingData(this.deviceId).subscribe({
            next: data => {
              this.trackingDataList = data;
              console.log("this.trackingDataList.length " + this.trackingDataList.length);
              resolve();
            },
            error: err => console.log("Error: No tracking data!"),
            complete: () => {
            }
          });
        },
        error: err => console.log(err)
      });
    });
  }


  ngAfterViewInit() {
    this.ngOnInitPromise.then(() => {
      if (this.trackingDataList) {
        this.mapInitializer();
      } else {
        console.log("No tracking data!");
      }
    });
  }


  async mapInitializer() {

    let mapOptions: google.maps.MapOptions = {
      center: {
        lat: this.trackingDataList[this.trackingDataList.length - 1].latitude,
        lng: this.trackingDataList[this.trackingDataList.length - 1].longitude
      },
      zoom: 10
    };

    let devicePath = new google.maps.Polyline({
      path: this.trackingDataList.map(item => {
        return {lat: item.latitude, lng: item.longitude}
      }),
      // geodesic: true,
      strokeColor: "#FF0000",
      strokeOpacity: 1.0,
      strokeWeight: 2,
    });

    let marker = new google.maps.Marker({
      position: {
        lat: this.trackingDataList[this.trackingDataList.length - 1].latitude,
        lng: this.trackingDataList[this.trackingDataList.length - 1].longitude
      },
      map: this.map
    });


    this.map = await new google.maps.Map(this.gmap.nativeElement, mapOptions);
    marker.setMap(this.map);
    devicePath.setMap(this.map);
  }

}
