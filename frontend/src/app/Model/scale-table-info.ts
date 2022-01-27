export interface ScaleTableInfo {
  timestamp: string;
  deviceId: number;
  weight: number;
  battary: number;
  avgTemp?: number;
  crop: number;
  locationId?: number;
}
