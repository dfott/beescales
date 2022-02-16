export interface ScaleTableInfo {
  timestamp: string;
  deviceId: number;
  weight: number;
  name: string;
  battary: number;
  avgTemp?: number;
  crop: number;
  locationId?: number;
}
