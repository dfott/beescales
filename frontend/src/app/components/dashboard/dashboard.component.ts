import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Scale } from 'src/app/Model/scale';
import { ScaleServiceApi } from 'src/app/serviceApi/scale.service';
import { ScaleService } from 'src/app/services/scale.service';
import { ScaleDetail, ScaleLocation } from '../../Model/scale-detail';
import { DateService, TimeRangeConfig } from '../../services/date.service';
import { scaleToGraphArray } from '../../helpers/scaleToGraphMapper';
import { MatDatepickerInputEvent } from '@angular/material/datepicker';
import { FormControl, FormGroup } from '@angular/forms';
import { scaleToTableArray } from 'src/app/helpers/scaleToTableMapper';
import { ScaleTableInfo } from 'src/app/Model/scale-table-info';
import { Graph } from 'src/app/Model/graph';
import { MatButtonToggle } from '@angular/material/button-toggle';
import { singleScaleToGraph } from 'src/app/helpers/singleScaleDetailMapper';
import { BreakpointObserver, BreakpointState } from '@angular/cdk/layout';
import { DateHelper } from '../../helpers/date-helper';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit {
  @ViewChild('emptyButtonToggle') emptyButtonToggle!: ElementRef<MatButtonToggle>;

  // graph properties
  selectedLocation: string = 'all';
  selectedScale: string = 'all';
  selectedYear: string = '1m';
  currentGraphData: Graph[] = [];
  scaleDetails: ScaleDetail[] = [];
  selectableScaleDetails: ScaleDetail[] = [];
  scaleLocations: ScaleLocation[] = [];
  singleScaleDetail: Graph[] = [];
  scaleData: Scale[] = [];
  timeSlideCounter: number = 0;
  startTime: string = '';
  endTime: string = '';
  // table properties
  tableData: ScaleTableInfo[] = [];
  isFetchingData = true;
  showContainer: boolean = true;
  timeRangeConfig: TimeRangeConfig = this.dateService.config;

  // used to display and control the date picker range
  datePickerRangeForm = new FormGroup({
    start: new FormControl(),
    end: new FormControl(),
  });
  // used to calculate the difference, between the last selected date range
  datePickerRangeSelected: { startTime: Date; endTime: Date } = {
    startTime: new Date(),
    endTime: new Date(),
  };

  constructor(
    private dateService: DateService,
    private scaleServiceApi: ScaleServiceApi,
    private scaleService: ScaleService,
    public breakpointObserver: BreakpointObserver
  ) {}

  ngOnInit(): void {
    this.scaleService.getGraphScales().subscribe((res) => {
      this.scaleData = res;
      this.scaleDetails = this.getScaleDetails(this.scaleData);
      if (this.selectableScaleDetails.length === 0) {
        this.selectableScaleDetails = this.scaleDetails;
      }
      this.scaleLocations = this.getScaleLocations(this.scaleData);
      this.currentGraphData = scaleToGraphArray(this.scaleData);
      this.tableData = scaleToTableArray(this.scaleData);
      if (this.selectedScale !== 'all') {
        this.changeScale(this.selectedScale);
      }
      this.isFetchingData = false;
    });
    this.scaleService.getCounter().subscribe((res) => {
      this.timeSlideCounter = res;
      this.changeYear({ value: this.selectedYear });
      if (this.selectedScale !== 'all') {
        this.changeScale(this.selectedScale);
      }
    });
    this.breakpointObserver.observe(['(max-width: 460px)']).subscribe((state: BreakpointState) => {
      this.showContainer = state.matches;
    });
  }

  changeYear(e: any) {
    if (this.selectedYear !== e.value) {
      this.selectedYear = e.value;
      this.scaleService.setCounter(0);
    } else {
      this.selectedYear = e.value;
      this.isFetchingData = true;
      const { startTime, endTime, showDetails } = this.timeRangeConfig[e.value].getRange(
        this.timeSlideCounter,
        new Date(this.datePickerRangeSelected.startTime),
        new Date(this.datePickerRangeSelected.endTime)
      );
      this.startTime = startTime;
      const endDate = new Date(endTime);
      endDate.setHours(endDate.getHours() - 2);
      this.endTime = DateHelper.dateToISO(endDate);
      this.datePickerRangeForm.setValue({ start: this.startTime, end: this.endTime });
      this.scaleServiceApi.getAllDataWithRange(startTime, endTime, showDetails).subscribe((res) => {
        this.scaleService.setGraphScales(res);
      });
    }
  }

  handleDatePickerChange(event: MatDatepickerInputEvent<Date>) {
    if (event.value) {
      this.datePickerRangeSelected = {
        startTime: this.datePickerRangeForm.value.start,
        endTime: this.datePickerRangeForm.value.end,
      };
      this.scaleService.setCounter(0);
      // @ts-ignore - we need to access the private property here to simulate a click
      this.emptyButtonToggle['_buttonElement'].nativeElement.click();
    }
  }

  handleChangeDate(left: boolean) {
    if (left) {
      this.scaleService.setCounter(this.timeSlideCounter + 1);
    } else {
      this.scaleService.setCounter(this.timeSlideCounter - 1);
    }
  }

  changeScale(scaleId: string) {
    this.selectedScale = scaleId;
    this.singleScaleDetail = singleScaleToGraph(this.scaleData, scaleId);
  }

  changeLocation(locationId: string) {
    this.selectedLocation = locationId;
    this.selectedScale = 'all';
    if (locationId === 'all') {
      this.selectableScaleDetails = this.scaleDetails;
    } else {
      this.selectableScaleDetails = this.scaleDetails.filter(
        (scaleDetail) => scaleDetail.locationId === parseInt(locationId)
      );
    }
  }

  getScaleDetails(data: Scale[]): ScaleDetail[] {
    return data.map((d) => {
      return {
        deviceId: d.deviceId,
        name: d.deviceName,
        locationId: d.locationId,
        color: d.color,
      };
    });
  }

  getScaleLocations(data: Scale[]): ScaleLocation[] {
    const locations: ScaleLocation[] = [];
    data.forEach((d) => {
      if (!locations.find((l: ScaleLocation) => l.id === d.locationId)) {
        locations.push({
          id: d.locationId,
          name: d.locationName,
          scales: [],
        });
      }
    });
    return locations;
  }
}
