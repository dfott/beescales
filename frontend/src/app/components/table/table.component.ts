import { Component, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { ScaleTableInfo } from 'src/app/Model/scale-table-info';

@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
export class TableComponent implements OnChanges {
  @Input() tableData!: ScaleTableInfo[];
  @Input() selectedScale: string = 'all';
  @Input() selectedLocation: string = 'all';
  displayedColumns: string[] = ['ts', 'deviceid', 'crop', 'weight', 'batt'];
  @ViewChild(MatPaginator) paginator!: MatPaginator;
  data!: MatTableDataSource<ScaleTableInfo>;

  constructor() {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.tableData || changes.selectedScale || changes.selectedLocation) {
      if (changes.tableData) {
        this.tableData = changes.tableData.currentValue;
      }
      this.data = new MatTableDataSource(this.filterTable(this.tableData));
      this.data.paginator = this.paginator;
    }
  }

  filterTable(tableData: ScaleTableInfo[]) {
    const locationTable = this.filterTableByLocation(tableData);
    return this.filterTableByScale(locationTable);
  }

  filterTableByLocation(tableData: ScaleTableInfo[]) {
    if (this.selectedLocation !== 'all') {
      return tableData.filter((val) => val.locationId === parseInt(this.selectedLocation));
    }
    return tableData;
  }

  filterTableByScale(locationTable: ScaleTableInfo[]) {
    if (this.selectedScale !== 'all') {
      return locationTable.filter((val) => val.deviceId === parseInt(this.selectedScale));
    }
    return locationTable;
  }
}
