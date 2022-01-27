import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Scale } from '../Model/scale';

@Injectable({
  providedIn: 'root',
})
export class ScaleService {
  public graphScales: BehaviorSubject<Scale[]> = new BehaviorSubject<Scale[]>([]);
  public counter: BehaviorSubject<number> = new BehaviorSubject<number>(0);

  constructor() {}

  getCounter() {
    return this.counter.asObservable();
  }

  getGraphScales() {
    return this.graphScales.asObservable();
  }

  setCounter(value: number) {
    this.counter.next(value);
  }

  setGraphScales(scales: Scale[]) {
    this.graphScales.next(scales);
  }
}
