import { Component, OnInit } from '@angular/core';
import { ScaleDetail, ScaleLocation } from '../../Model/scale-detail';
import { ScaleServiceApi } from '../../serviceApi/scale.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
})
export class SettingsComponent implements OnInit {

  scaleLocations: ScaleLocation[] = [];

  constructor(private scaleServiceApi: ScaleServiceApi) {}

  ngOnInit(): void {
    this.scaleServiceApi.getSettings().subscribe((res) => {
      this.scaleLocations = res;
    });
  }

  saveDetail(scaleDetail: ScaleDetail): void {
    scaleDetail.isUpdating = true;
    forkJoin([this.scaleServiceApi.updateScaleDetail(scaleDetail),
    this.scaleServiceApi.updateScaleThreshold(scaleDetail.deviceId, scaleDetail.scaleThreshold)])
      .subscribe(() => {
        scaleDetail.isUpdating = false;
      }, () => {
        scaleDetail.isUpdating = false;
      })
  }
}
