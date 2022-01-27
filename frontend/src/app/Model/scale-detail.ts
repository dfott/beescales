export interface ScaleLocation {
  id: number;
  name: string;
  scales: ScaleDetail[];
}

export interface ScaleDetail {
  deviceId: number;
  name: string;
  color: string;
  locationId: number;

  isUpdating?: boolean;
  scaleThreshold?: ScaleThreshold;
}

export interface ScaleThreshold {
  id: number;
  absolute: boolean;
  active: boolean;
  min: number;
  max: number;
}
