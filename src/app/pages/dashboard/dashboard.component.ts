import { Component, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import ApexCharts from 'apexcharts';
import { firstValueFrom } from 'rxjs';
import { OperationDto, PriceHistoryObj, VaultDto, VaultSummary } from '../../api/models';
import { OperationControllerService, VaultControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

import { ChartComponent } from "ng-apexcharts";

import {
  ApexChart,
  ApexNonAxisChartSeries,
  ApexResponsive
} from "ng-apexcharts";

export type ChartOptions = {
  series: ApexNonAxisChartSeries;
  chart: ApexChart;
  responsive: ApexResponsive[];
  labels: any;
  colors?: string[]; // Aggiungi la proprietà colors (opzionale)
  dataLabels?: any;
  plotOptions?: any;
  stroke?: any;
};


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit, OnDestroy {

  @ViewChild("chart") donutChart!: ChartComponent;
  public donutChartOptions!: Partial<ChartOptions>;
  private areaChart: ApexCharts | null = null;

  selected: VaultDto | null = null;
  dataReport: VaultSummary | null = null;
  isOpen = false;
  vaults?: Array<VaultDto>;
  operations?: Array<OperationDto>;
  report?: Array<VaultSummary> = [];
  reportSet?: Array<PriceHistoryObj>;
  priceData: { date: number, price: number }[] = [];
  categoryList?: string[];
  costList?: number[];


  constructor(
    private vaultControllerService: VaultControllerService,
    private operationControllerService: OperationControllerService,
    private authService: AuthService,
  ) {
    this.donutChartOptions = {
      series: [44, 55, 13],
      chart: {
        type: "donut",
        width: '350px',
        height: '350px'
      },
      labels: this.categoryList,
      colors: [
        '#2E6F9E', 
        '#3C8CBB', 
        '#4DA3D7', 
        '#5BB7E3', 
        '#69C6E9',
        '#77D6F0', 
        '#85E1F7', 
        '#93EBFF',
        '#A1E6FF',
        '#B3F1FF',
        '#C4F5FF',
        '#D1FAFF',
        '#E0FDFF',
        '#E8FFFF',
        '#F0FFFF',
        '#F6FFFF',
        '#D8E6E6'
      ],
      responsive: [
        {
          breakpoint: 480,
          options: {
            chart: {
              width: '200px',
            },
            legend: {
              position: "bottom"
            }
          }
        }
      ],
      dataLabels: {
        enabled: true
      },
      plotOptions: {
        pie: {
          donut: {
            size: '70%',
            labels: {
              show: true,
              name: {
                show: true,
                fontSize: '16px',
                fontWeight: 800,
                color: '#D8E6E6'
              },
              value: {
                show: true,
                fontSize: '16px',
                fontWeight: 400,
                color: '#D8E6E6'
              }
            }
          }
        }
      },
      stroke: {
        show: true,
        width: 3,
        colors: ['']
      }
    };    
  }

  async ngOnInit(): Promise<void> {
    try {
      await this.getSummaryReport();
      await this.getVaultsByPrincipal();
      await this.getOperationsByPrincipal();
      await this.loadChart();
    } catch (error) {
      console.error('Error during initialization:', error);
    }
  }

  async loadChart() {
    await this.getReportHistory();
    if (this.reportSet && this.reportSet.length > 0) {
      this.transformData(this.reportSet);
      this.getCategoryListFromOperation(); 
  
      this.donutChartOptions.labels = this.categoryList;
  
      if (this.areaChart) {
        this.areaChart.destroy();
      }
  
      this.initChart();
    } else {
      console.error('No data available for the chart.');
      this.authService.logout();
    }
  }
  

  async getVaultsByPrincipal() {
    try {
      this.vaults = await firstValueFrom(this.vaultControllerService.getAllVaultsByPrincipal());
      if (this.vaults.length > 0) {
        this.selected = this.vaults[0];
      }

      if (this.report && this.report.length > 0) {
        this.dataReport = this.report.find(a => a.vaultId == this.selected?.id)!;
      } else {
        console.error('Report is undefined or empty.');
      }
    } catch (error) {
      console.log("Dati Non Disponibili");
    }
  }

  async getOperationsByPrincipal() {
    try {
      this.operations = await firstValueFrom(this.operationControllerService.getAllOperationsByPrincipal());
      this.getCategoryListFromOperation();

    } catch (error) {
      console.log("Operazioni Non Disponibili");
    }
  }

  async getSummaryReport() {
    try {
      this.report = await firstValueFrom(this.vaultControllerService.getVaultsReport());
    } catch (error) {
      console.log("Error: Summary not Available");
    }
  }

  async getReportHistory() {
    try {
      this.reportSet = await firstValueFrom(this.operationControllerService.getVaultHistoryReport({ id: this.selected?.id! }));
    } catch (error) {
      console.log("Error: Report not Available");
    }
  }

  getCategoryListFromOperation() {
    const uniqueCategories = new Set<string>();
  
    this.operations?.forEach(operation => {
      if (operation.category) {
        uniqueCategories.add(operation.category.categoryName);
      }
    });
  
    this.categoryList = Array.from(uniqueCategories) as string[]; 

    console.log(this.categoryList)
  }


  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  async selectOption(option: VaultDto | null) {
    this.selected = option;
    this.isOpen = false;

    if (this.report && this.report.length > 0) {
      this.dataReport = this.report.find(a => a.vaultId == this.selected?.id)!;
    } else {
      console.error('Report is undefined or empty.');
    }

    await this.loadChart();
  }

  @HostListener('document:click', ['$event'])
  closeDropdown(event: MouseEvent) {
    const target = event.target as HTMLElement;
    if (!target.closest('.custom-select')) {
      this.isOpen = false;
    }
  }

  chosen(chosenId: number): boolean {
    return chosenId == this.selected?.id;
  }

  initChart() {
    const dates = this.priceData.map(data => [data.date, data.price]);
    const options: ApexCharts.ApexOptions = {
      chart: {
        type: 'area',
        stacked: false,
        height: '100%',
        zoom: { type: 'x', enabled: true, autoScaleYaxis: true },
        toolbar: { autoSelected: 'zoom' },
      },
      series: [{ name: "Capital", data: dates, color: '#2575BB' }],
      dataLabels: { enabled: false },
      markers: { size: 0 },
      title: {
        text: "Balance",
        align: 'left',
        style: { color: '#EEF0F4' }
      },
      fill: {
        type: 'gradient',
        gradient: {
          shadeIntensity: 1,
          inverseColors: false,
          opacityFrom: 0.5,
          opacityTo: 0,
          stops: [0, 90, 100]
        }
      },
      yaxis: {
        labels: {
          formatter: val => val.toFixed(2),
          style: { colors: '#80818b' }
        },
        title: { text: "Capital", style: { color: "#444b5c" } },
      },
      xaxis: {
        type: 'datetime',
        labels: { style: { colors: '#80818b' } },
      },
      tooltip: {
        shared: false,
        y: {
          formatter: val => val.toFixed(2)
        }
      },
      stroke: {
        curve: 'smooth',
        width: 5,
      },
      grid: {
        show: true,
        borderColor: '#3B4758',
        position: 'back',
        xaxis: {
          lines: { show: false },
        },
        yaxis: {
          lines: { show: true },
        },
      }
    };

    this.areaChart = new ApexCharts(document.querySelector("#areaChart") as HTMLElement, options);
    this.areaChart.render();
  }

  transformData(reportSet: Array<PriceHistoryObj>): void {
    this.priceData = reportSet.map(data => ({
      date: new Date(data.date!).getTime(),
      price: data.capital !== undefined ? data.capital : 0
    }));
  }

  onScroll(event: WheelEvent): void {
    event.preventDefault(); 
  }

  ngOnDestroy() {
    if (this.areaChart) {
      this.areaChart.destroy();
    }
  }
}
