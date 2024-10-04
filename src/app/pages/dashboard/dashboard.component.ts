import { Component, HostListener, OnDestroy, OnInit, ViewChild } from '@angular/core';
import ApexCharts from 'apexcharts';
import { firstValueFrom } from 'rxjs';
import { CategoryReportDto, OperationDto, PriceHistoryObj, VaultDto, VaultSummary } from '../../api/models';
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
  colors?: string[];
  dataLabels?: any;
  plotOptions?: any;
  stroke?: any;
  legend?: any;
  tooltip?: any;
};

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
})
export class DashboardComponent implements OnInit, OnDestroy {

  @ViewChild("chart") donutChart!: ChartComponent;
  public revenueDonutChartOptions!: Partial<ChartOptions>;
  public expenseDonutChartOptions!: Partial<ChartOptions>;
  private areaChart: ApexCharts | null = null;

  selected: VaultDto | null = null;
  dataReport: VaultSummary | null = null;
  isOpen = false;
  vaults?: Array<VaultDto>;
  operations?: Array<OperationDto>;
  report?: Array<VaultSummary> = [];
  reportSet?: Array<PriceHistoryObj>;
  categoryReport?: {[key: string] : Array<CategoryReportDto>};
  priceData: { date: number, price: number }[] = [];
  categoryList?: string[];
  costList?: number[];
  dummyOperationSeries = [100]
  dummyOperationLabel= ["No Operation"]
  revenueData?: {
    name: string | undefined;
    percentage: number;
  }[];
  expenseData?: {
    name: string | undefined;
    percentage: number;
  }[];
  instoChoose : boolean = true;


  constructor(
    private vaultControllerService: VaultControllerService,
    private operationControllerService: OperationControllerService,
    private authService: AuthService,
  ) {
    this.revenueDonutChartOptions = {
      series: [0],
      chart: {
        type: "donut",
        width: '300px',
        height: '200px',
      },
      labels: [''],
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
        enabled: true,
        formatter: (val: number, opts: any) => {
          const total = opts.w.globals.seriesTotals.reduce((a: number, b: number) => a + b, 0);
          const percentage = ((val / total) * 100).toFixed(2);
          return `${percentage}%`;
        },
        dropShadow: {
          enabled: true,
          top: 1,
          left: 1,
          blur: 3,
          opacity: 0.5
        },
        style: {
          fontSize: '12px',
          fontWeight: 'bold',
          colors: ['#FFFFFF']
        }
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
                color: '#FFFFFF'
              },
              value: {
                show: true,
                fontSize: '16px',
                fontWeight: 400,
                color: '#FFFFFF'
              }
            }
          }
        }
      },
      stroke: {
        show: true,
        width: 5,
        colors: ['']
      },
      legend: {
        show: true,
        position: 'right',
        verticalAlign: 'middle',
        floating: false,
        fontSize: '14px',
        labels: {
          useSeriesColors: false,
          colors:  '#FFFFFF',
        }
      }
    };
    
    
    this.expenseDonutChartOptions = {
      series: [100],
      chart: {
        type: "donut",
        width: '300px',
        height: '300px'
      },
      labels: ['No Operations'],
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
        enabled: true,
        formatter: (val: number, opts: any) => {
          const total = opts.w.globals.seriesTotals.reduce((a: number, b: number) => a + b, 0);
          const percentage = ((val / total) * 100).toFixed(2);
          return `${percentage}%`;
        },
        dropShadow: {
          enabled: true,
          top: 1,
          left: 1,
          blur: 3,
          opacity: 0.5
        },
        style: {
          fontSize: '12px',
          fontWeight: 'bold',
          colors: ['#FFFFFF']
        }
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
                color: '#FFFFFF'
              },
              value: {
                show: true,
                fontSize: '16px',
                fontWeight: 400,
                color: '#FFFFFF'
              }
            }
          }
        }
      },
      stroke: {
        show: true,
        width: 5,
        colors: ['']
      },
      legend: {
        show: true,
        position: 'right', // Position the legend to the right
        verticalAlign: 'middle',
        floating: false,
        fontSize: '14px',
        labels: {
          useSeriesColors: false, // Use the colors specified above
          colors:  '#FFFFFF',
        }
      }
    };
    
  }    

  async ngOnInit(): Promise<void> {
    try {
      await this.getSummaryReport();
      await this.getVaultsByPrincipal();
      await this.getOperationsByVault();
      await this.getOperationCategoryReportPerVault();
      await this.loadCharts();
    } catch (error) {
      console.error('Error during initialization:', error);
    }
  }

  async loadCharts() {
    await this.getReportHistory();
    if (this.reportSet && this.reportSet.length > 0) {
      this.transformData(this.reportSet);
  
      if (this.areaChart) {
        this.areaChart.destroy();
      }
  
      this.initChart();

      
    } else {
      console.error('No data available for the chart.');
    }

    await this.getOperationCategoryReportPerVault();
    this.loadExpenseDonutChart();
    this.loadRevenueDonutChart();
  }

  async getOperationCategoryReportPerVault() {
    try {
      this.categoryReport = await firstValueFrom(this.operationControllerService.getOperationCategoryReportPerVault({ id : this.selected?.id!}));
      this.loadRevenueDonutChart();
      this.loadExpenseDonutChart();
    } catch (error) {
      console.log("Operazioni Non Disponibili");
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

  async getOperationsByVault() {
    try {
      this.operations = await firstValueFrom(this.operationControllerService.getAllOperationsByVaultId({ id : this.selected?.id!}));
    } catch (error) {
      console.log("Operazioni Non Disponibili");
    }
  }

  async getSummaryReport() {
    try {
      this.report = await firstValueFrom(this.vaultControllerService.getVaultsReport());
    } catch (error) {
      console.log("Error: Summary not Available");
      this.authService.logout();
    }
  }

  async getReportHistory() {
    try {
      this.reportSet = await firstValueFrom(this.operationControllerService.getVaultHistoryReport({ id: this.selected?.id! }));

    } catch (error) {
      console.log("Error: Report not Available");
    }
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

    await this.loadCharts();
    await this.getOperationsByVault();
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
        zoom: { type: 'x', enabled: false, autoScaleYaxis: true },
        toolbar: { autoSelected: 'zoom' },
      },
      series: [{ name: "Capital", data: dates, color: '#2575BB' }],
      dataLabels: { enabled: false },
      markers: { size: 1 },
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
    try{
      this.areaChart.render();
    }catch(error){
      console.log
    }
  }

  transformData(reportSet: Array<PriceHistoryObj>): void {
    this.priceData = reportSet.map(data => ({
      date: new Date(data.date!).getTime(),
      price: data.capital!
    }));
  }

  ngOnDestroy() {
    if (this.areaChart) {
      this.areaChart.destroy();
    }
  }

  isExpense(operation : OperationDto) : boolean{
    return operation.type == 'EXPENSE'
  }

  loadExpenseDonutChart(){
    const expenseData = this.categoryReport!['EXPENSE'].map(entry => ({
      name: entry.categoryDTO?.categoryName,
      percentage: entry.percentage!
    }));
    
    this.expenseDonutChartOptions.labels = expenseData.map(data => data.name);
    this.expenseDonutChartOptions.series = expenseData.map(data => data.percentage);

    // if(!this.expDonutChartUp()){
    //   this.expenseDonutChartOptions.series = this.dummyOperationSeries;
    //   this.expenseDonutChartOptions.labels = this.dummyOperationLabel;
    // }
  }

  loadRevenueDonutChart(){
    this.revenueData = this.categoryReport!['REVENUE'].map(entry => ({
      name: entry.categoryDTO?.categoryName,
      percentage: entry.percentage!
    }));
    
    this.revenueDonutChartOptions.labels = this.revenueData.map(data => data.name);
    this.revenueDonutChartOptions.series = this.revenueData.map(data => data.percentage);

    // if(!this.revDonutChartUp()){
    //   this.revenueDonutChartOptions.series = this.dummyOperationSeries;
    //   this.revenueDonutChartOptions.labels = this.dummyOperationLabel;
    // }
  }

  expDonutChartUp(){
    return (this.expenseDonutChartOptions.labels.length > 0 && this.expenseDonutChartOptions.series!.length > 0);
  }

  revDonutChartUp(){
    return (this.revenueDonutChartOptions!.labels.length > 0 && this.revenueDonutChartOptions.series!.length > 0);
  }

}
