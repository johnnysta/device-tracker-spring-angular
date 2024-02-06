import {Component, AfterViewInit, ViewChild, ElementRef} from '@angular/core';
import {} from 'googlemaps';
import {ActivatedRoute, RouterLink} from "@angular/router";


@Component({
  selector: 'app-show-device-on-map',
  templateUrl: './show-device-on-map.component.html',
  styleUrls: ['./show-device-on-map.component.css']
})


export class ShowDeviceOnMapComponent implements AfterViewInit {

  @ViewChild('gmapContainer', {static: false}) gmap!: ElementRef;

  deviceName!: string | null;

  map!: google.maps.Map;
  lat = 30.9716;
  lng = 175.5946;

  coordinates = new google.maps.LatLng(this.lat, this.lng);

  mapOptions: google.maps.MapOptions = {
    center: this.coordinates,
    zoom: 10
  };

  marker = new google.maps.Marker({
    position: this.coordinates,
    map: this.map
  });

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.paramMap.subscribe({
      next: param => {
        return this.deviceName = param.get('name');
      },
      error: err => console.log(err),
      complete: () => {}
    })

  }

  ngAfterViewInit() {
    this.mapInitializer();
  }

  async mapInitializer() {
    this.map = await new google.maps.Map(this.gmap.nativeElement, this.mapOptions);
    this.marker.setMap(this.map);
  }

}
