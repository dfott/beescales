import { Scale, ScaleDataSummary } from '../Model/scale';
import { ScaleTableInfo } from '../Model/scale-table-info';

export function scaleToTableArray(scales: Scale[]): ScaleTableInfo[] {
  return [].concat.apply(
    [],
    scales.map((scale) => {
      let data: ScaleTableInfo[] = extractData(scale);
      data.sort(function (a, b) {
        return new Date(a.timestamp).valueOf() - new Date(b.timestamp).valueOf();
      });
      return data;
    }) as any[]
  );
}

function extractData(scale: Scale): ScaleTableInfo[] {
  let data: ScaleTableInfo[] = [];
  Object.keys(scale.data).forEach((day) => {
    const value = scale.data[day];
    if (value.weight !== undefined && value.weight !== null) {
      const scaleValue: ScaleTableInfo = {
        timestamp: day,
        deviceId: scale.deviceId,
        locationId: scale.locationId,
        name: scale.deviceName,
        crop: Math.round(value.crop * 100) / 100,
        weight: Math.round(value.weight * 100) / 100,
        battary: Math.round(value.batt * 100) / 100,
      };
      data.push(scaleValue);
    }
    extractDetailedData(value, data, scale);
  });
  return data;
}

function extractDetailedData(scaleDataSummary: ScaleDataSummary, data: any[], scale: Scale): void {
  scaleDataSummary.data.forEach((detail) => {
    const scaleValue: ScaleTableInfo = {
      timestamp: detail.epoche.toString(),
      deviceId: detail.deviceid,
      name: scale.deviceName,
      locationId: scale.locationId,
      crop: Math.round(detail.crop * 100) / 100,
      weight: Math.round(detail.weight * 100) / 100,
      battary: Math.round(detail.batt * 100) / 100,
    };
    data.push(scaleValue);
  });
}
