export class DateHelper {

  static getDayRange(diff: number = 0): [string, string] {
    const startTime = new Date();
    startTime.setDate(startTime.getDate() - diff);
    this.setStartOfDay(startTime);
    const endTime = new Date(startTime);
    this.setEndOfDay(endTime);
    return [DateHelper.dateToISO(startTime),
      DateHelper.dateToISO(endTime)];
  }

  static getMultipleDayRange(diff: number, d1: Date, d2: Date): [string, string, boolean] {
    const startTime = new Date(d1);
    this.setStartOfDay(startTime);
    const endTime = new Date(d2);
    this.setStartOfDay(endTime);
    // calculate the number of days between two dates
    // source: https://stackoverflow.com/questions/542938/how-to-calculate-number-of-days-between-two-dates
    const millisecondsPerDay = 24 * 60 * 60 * 1000;
    // @ts-ignore
    let daysBetween = (this.treatAsUTC(endTime) - this.treatAsUTC(startTime)) / millisecondsPerDay;
    daysBetween = daysBetween > 1 ? daysBetween : 1;
    startTime.setDate(startTime.getDate() - (diff * daysBetween));
    endTime.setDate(endTime.getDate() - (diff * daysBetween));
    if (daysBetween <= 1) {
      this.setEndOfDay(endTime);
    }
    return [DateHelper.dateToISO(startTime),
      DateHelper.dateToISO(endTime), daysBetween <= 3];
  }

  private static treatAsUTC(date: Date): Date {
    const result = new Date(date);
    result.setMinutes(result.getMinutes() - result.getTimezoneOffset());
    return result;
  }

  static getWeekRange(diff: number = 0): [string, string] {
    const startTime = new Date();
    startTime.setDate(startTime.getDate() - (7 * (diff + 1)));
    const endTime = new Date(startTime);
    endTime.setDate(endTime.getDate() + 7);
    return [DateHelper.dateToISO(startTime),
      DateHelper.dateToISO(endTime)];
  }

  static getMonthRange(firstDiff: number, secondDiff: number): [string, string] {
    const startTime = new Date();
    startTime.setMonth(startTime.getMonth() - firstDiff);
    this.setStartOfDay(startTime);
    const endTime = new Date();
    endTime.setMonth(endTime.getMonth() - secondDiff)
    this.setEndOfDay(endTime);
    return [DateHelper.dateToISO(startTime),
      DateHelper.dateToISO(endTime)];
  }

  static dateToISO(date: Date): string {
    date.setHours(date.getHours() + 1);
    return date.toISOString();
  }

  private static setEndOfDay(date: Date): void {
    date.setHours(23, 59, 59, 59);
  }

  private static setStartOfDay(date: Date): void {
    date.setHours(0, 0, 0, 0);
  }
}
