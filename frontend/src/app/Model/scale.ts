export interface Scale {
  color: string;
  data: ScaleData;
  deviceId: number;
  deviceName: string;
  locationName: string;
  locationId: number;
}


export interface ScaleData {
  [day: string]: ScaleDataSummary;
}

export interface ScaleDataSummary {
  avgTemp: number;
  batt: number;
  crop: number;
  data: ScaleDataDetail[]
  maxTemp: number;
  minTemp: number;
  weight: number;
}

export interface ScaleDataDetail {
  id: number;
  deviceid: number;
  ts: Date;
  weight: number;
  corr: number;
  temp: number;
  batt: number;
  crop: number;
  altitude: number;
  latitude: number;
  longitude: number;
  epoche: Date;
  windDirection: number;
  windSpeed: number;
  airPressure: number;
  humidity: number;
  airTemp: number;
}
