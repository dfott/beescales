import { Graph } from '../Model/graph';
import { Scale, ScaleDataSummary } from '../Model/scale';

export function scaleToGraphArray(scales: Scale[]): Graph[] {
  return scales.map(scale => {
    let data: any[] = extractData(scale);
    data.sort(function (a, b) {
      return a.name.valueOf() - b.name.valueOf();
    });
    return {
      deviceId: scale.deviceId,
      color: scale.color,
      data: data,
    };
  });
}

function extractData(scale: Scale): any[] {
  let data: any[] = [];
  Object.keys(scale.data).forEach(day => {
    const value = scale.data[day];
    const date = new Date(day);
    if (value.weight !== undefined && value.weight !== null) {
      data.push({
        name: date,
        value: [date, value.weight.toFixed(2)]
      });
    }
    extractDetailedData(value, data);
  });
  return data;
}

function extractDetailedData(scaleDataSummary: ScaleDataSummary, data: any[]): void {
  scaleDataSummary.data.forEach(detail => {
      const date = new Date(detail.epoche);
      data.push({
        name: date,
        value: [date, detail.weight],
      });
    }
  );
}
