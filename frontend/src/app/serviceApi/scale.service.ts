import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Scale } from '../Model/scale';
import { ScaleDetail, ScaleLocation, ScaleThreshold } from '../Model/scale-detail';

@Injectable({
  providedIn: 'root',
})
export class ScaleServiceApi {

  constructor(private http: HttpClient) {
  }

  getSettings(): Observable<ScaleLocation[]> {
    return this.http.get<ScaleLocation[]>(environment.apiUrl + 'api/settings');
  }

  getAllData(): Observable<Scale[]> {
    return this.http.get<Scale[]>(environment.apiUrl + 'api/data');
  }

  getAllDataWithRange(dateRangeOne: string, dateRangeTwo: string, showDetails: boolean = false): Observable<Scale[]> {
    return this.http.get<Scale[]>(
      environment.apiUrl + `api/data?from=${dateRangeOne}&to=${dateRangeTwo}&showDetailed=${showDetails}`
    );
  }

  updateScaleDetail(scaleDetail: ScaleDetail): Observable<ScaleDetail> {
    return this.http
      .put<ScaleDetail>(environment.apiUrl + 'api/settings/' + scaleDetail.deviceId, scaleDetail);
  }

  updateScaleThreshold(deviceId: number, scaleThreshold?: ScaleThreshold): Observable<ScaleThreshold> {
    return this.http.put<ScaleThreshold>(
      environment.apiUrl + 'api/threshold/' + deviceId, scaleThreshold
    );
  }

  getSingleScale(deviceId: string): Observable<any> {
    return this.http.get<any>(environment.apiUrl + `api/data/${deviceId}`);
  }

  getSingleScaleWithRange(deviceId: string, dateRangeOne: string, dateRangeTwo: string): Observable<any> {
    return this.http.get<any>(environment.apiUrl + `api/data/${deviceId}?from${dateRangeOne}&to${dateRangeTwo}`);
  }
}
