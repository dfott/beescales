import { Scale, ScaleDataSummary } from '../Model/scale';

type Search = 'weight' | 'batt' | 'avgTemp' | 'crop';

export function singleScaleToGraph(scales: Scale[], scaleId: string): any {
  let graphObj = {};
  const scale = scales.find((sc) => sc.deviceId === parseInt(scaleId));
  const attributes: Search[] = ['weight', 'batt', 'avgTemp', 'crop'];
  for (let att of attributes) {
    let data: any[] = [];
    if (scale?.data) {
      Object.keys(scale.data).forEach((day) => {
        const val = scale.data[day][att];
        const date = new Date(day);
        if (
          att === 'avgTemp' &&
          (scale.data[day][attributes[0]] === null || scale.data[day][attributes[0]] === undefined)
        ) {
          extractDetailedData(scale.data[day], data, att);
        } else {
          if (val === undefined || val === null) {
            extractDetailedData(scale.data[day], data, att);
          } else {
            data.push({
              name: date,
              value: [date, val.toFixed(2)],
            });
          }
        }
      });
      data.sort(function (a, b) {
        return a.name.valueOf() - b.name.valueOf();
      });
      graphObj = { ...graphObj, [att]: data };
    }
  }
  return graphObj;
}

function extractDetailedData(
  scaleDataSummary: ScaleDataSummary,
  data: any[],
  attribute: 'weight' | 'temp' | 'batt' | 'avgTemp' | 'crop'
): void {
  if (attribute === 'avgTemp') attribute = 'temp';
  for (let scaleData of scaleDataSummary.data) {
    const date = new Date(scaleData.epoche);
    data.push({
      name: date,
      value: [date, scaleData[attribute]],
    });
  }
}
