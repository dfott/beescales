import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Graph } from 'src/app/Model/graph';
import { EChartsOption } from 'echarts';
import { ScaleDetail } from '../../Model/scale-detail';

export interface DateTimeRange {
  startTime: string;
  endTime: string;
}

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.scss'],
})
export class GraphComponent implements OnChanges {
  @Input() currentGraphData: Graph[] = [];
  @Input() scaleDetails: ScaleDetail[] = [];
  @Input() isLoading: boolean = false;
  @Input() timeRange!: DateTimeRange;
  @Input() selectableScaleDetails: ScaleDetail[] = [];
  @Input() selectedScale: string = 'all';
  @Input() singleScaleDetail: any = [];
  dataOptions: any;
  loadingOpts = {
    text: 'Daten werden geladen...',
    color: '#6c9335',
    textColor: '#000',
    maskColor: 'rgba(255, 255, 255, 0.8)',
  };
  options: EChartsOption = {
    tooltip: {
      trigger: 'axis',
      axisPointer: {
        type: 'cross',
      },
    },
    legend: {
      data: [],
    },
    xAxis: {
      type: 'time',
      splitLine: {
        show: false,
      },
    },
    yAxis: {
      type: 'value',
    },
    dataZoom: [
      {
        type: 'slider',
        xAxisIndex: 0,
        filterMode: 'empty',
      },
      {
        type: 'slider',
        yAxisIndex: 0,
        filterMode: 'empty',
      },
      {
        type: 'inside',
        xAxisIndex: 0,
        filterMode: 'empty',
      },
      {
        type: 'inside',
        yAxisIndex: 0,
        filterMode: 'empty',
      },
    ],
    series: [],
  };

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.currentGraphData || changes.scaleDetails || changes.selectableScaleDetails || changes.selectedScale) {
      this.dataOptions = {
        series: this.getSeries(this.currentGraphData),
        legend: {
          data: this.getDeviceNames(),
        },
      };
    }
  }

  getSeries(graphList: Graph[]) {
    let general;
    let details;
    if (this.selectedScale === 'all') {
      general = this.generalScaleData(graphList, false);
      details = this.detailsScaleData(true);
    } else {
      general = this.generalScaleData(graphList, true);
      details = this.detailsScaleData(false);
    }
    return details.concat(general);
  }

  detailsScaleData(empty: boolean) {
    let details: any[] = [];
    let val = this.singleScaleDetail;
    if (Object.keys(val).length === 0) {
      val = { weight: [], batt: [], avgTemp: [], crop: [] };
    }
    Object.keys(val).forEach((data) => {
      details.push({
        name: data,
        type: 'line',
        data: empty ? [] : val[data],
        color: this.setNameColors(data),
      });
    });
    return details;
  }

  generalScaleData(graphList: Graph[], empty: boolean) {
    return graphList.map((graph) => {
      return {
        name: this.scaleDetails.find((sd) => sd.deviceId === graph.deviceId)?.name,
        type: 'line',
        data: empty ? [] : this.selectableScaleDetails.find((val) => val.deviceId === graph.deviceId) ? graph.data : [],
        color: graph.color,
      };
    });
  }

  getDeviceNames() {
    if (this.selectedScale === 'all') {
      return this.selectableScaleDetails.map((sd) => sd.name);
    }
    return ['weight', 'batt', 'avgTemp', 'crop'];
  }

  setNameColors(attribute: string) {
    switch (attribute) {
      case 'weight':
        return `rgb(${25},${255},${25})`;
      case 'batt':
        return `rgb(${50},${50},${255})`;
      case 'avgTemp':
        return `rgb(${184},${15},${10})`;
      case 'crop':
        return `rgb(${84},${15},${120})`;
      default:
        return 'red';
    }
  }
}
