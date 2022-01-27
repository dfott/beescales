import { Injectable } from '@angular/core';
import { DateHelper } from '../helpers/date-helper';

export interface TimeRangeConfig {
  [rangeValue: string]: {
    getRange(diff: number, startTime?: Date, endTime?: Date): ScaleApiParams
  }
}

export interface ScaleApiParams {
  startTime: string,
  endTime: string,
  showDetails: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class DateService {

  public config: TimeRangeConfig = {
    'none': {
      getRange(diff: number, startTime: Date, endTime: Date): ScaleApiParams {
        const [d1, d2, showDetails] = DateHelper.getMultipleDayRange(diff, startTime, endTime);
        return { startTime: d1, endTime: d2, showDetails };
      }
    },
    '1t': {
      getRange(diff: number = 0): ScaleApiParams {
        const [startTime, endTime] = DateHelper.getDayRange(diff);
        return { startTime, endTime, showDetails: true };
      }
    },
    '1w': {
      getRange(diff: number = 0): ScaleApiParams {
        const [startTime, endTime] = DateHelper.getWeekRange(diff);
        return { startTime, endTime, showDetails: false };
      }
    },
    '1m': {
      getRange(diff: number = 0): ScaleApiParams {
        const [startTime, endTime] = DateHelper.getMonthRange(diff + 1, diff);
        return { startTime, endTime, showDetails: false };
      }
    },
    '6m': {
      getRange(diff: number = 0): ScaleApiParams {
        const [startTime, endTime] = DateHelper.getMonthRange(6 + (diff * 6), diff * 6);
        return { startTime, endTime, showDetails: false };
      }
    },
    '1y': {
      getRange(diff: number = 0): ScaleApiParams {
        const [startTime, endTime] = DateHelper.getMonthRange(12 + (diff * 12), diff * 12);
        return { startTime, endTime, showDetails: false };
      }
    }
  }
}
