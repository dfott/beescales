export interface Graph {
  deviceId: number;
  color: string;
  data: any[]; // GraphData[]
}

export interface GraphData {
  name: Date;
  value: [Date, number];
}
