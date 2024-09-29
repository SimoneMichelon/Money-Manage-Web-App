import { Component, HostListener, OnInit } from '@angular/core';
import ApexCharts from 'apexcharts';
import { firstValueFrom } from 'rxjs';
import { OperationDto, PriceHistoryObj, VaultDto, VaultSummary } from '../../api/models';
import { OperationControllerService, VaultControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss',
})
export class DashboardComponent implements OnInit {

  constructor(private vaultControllerService: VaultControllerService,
    private operationControllerService: OperationControllerService,
    private authService: AuthService,
  ) { }

  selected: VaultDto | null = null;
  dataReport: VaultSummary | null = null;
  isOpen = false;
  vaults?: Array<VaultDto>;
  operations?: Array<OperationDto>;
  report?: Array<VaultSummary> = [];
  reportSet?: Array<PriceHistoryObj>;


  async ngOnInit(): Promise<void> {
    try {
      await this.getSummaryReport();
      await this.getVaultsByPrincipal();
      await this.getOperationsByPrincipal();
      this.loadChart();
      
    } catch (error) {
      console.error('Error during initialization:', error);
    }
  }

  async loadChart() {
    await this.getReportHistory(); // Fetch report history first
    this.transformData(this.reportSet!); // Then transform data
    this.initChart(); // Finally, initialize the chart
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
      this.authService.logout();
    }
  }

  async getOperationsByPrincipal() {
    try {
      this.operations = await firstValueFrom(this.operationControllerService.getAllOperationsByPrincipal());
    } catch (error) {
      this.authService.logout();
    }
  }

  async getSummaryReport() {
    try {
      this.report = await firstValueFrom(this.vaultControllerService.getVaultsReport());
    } catch (error) {
      console.log("Error: Report not Available");
    }
  }

  async getReportHistory() {
    try {
      this.reportSet = await firstValueFrom(this.operationControllerService.getVaultHistoryReport({ id: this.dataReport?.vaultId! }));
    } catch (error) {
      this.authService.logout();
    }
  }



  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  selectOption(option: VaultDto | null) {
    this.selected = option;
    this.isOpen = false;

    if (this.report && this.report.length > 0) {
      this.dataReport = this.report.find(a => a.vaultId == this.selected?.id)!;


    } else {
      console.error('Report is undefined or empty.');
    }

    this.loadChart();
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
        zoom: {
          type: 'x',
          enabled: true,
          autoScaleYaxis: true
        },
        toolbar: {
          autoSelected: 'zoom'
        }
      },
      series: [{
        name: "Capital",
        data: dates,
        color: '#2575BB'
      }],
      dataLabels: {
        enabled: false,
      },
      markers: {
        size: 0
      },
      title: {
        text: "Balance",
        align: 'left',
        style: {
          color: '#EEF0F4'
        }
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
          formatter: function (val) {
            return val.toFixed(2); 
          },
          style: {
            colors: '#80818b'
          }
        },
        title: {
          text: "Capital",
          style : {
            color : "#444b5c"
          }
        },
      },
      xaxis: {
        type: 'datetime',
        labels : {
          style : {
            colors : '#80818b'
          }
        },


      },
      tooltip: {
        shared: false,
        y: {
          formatter: function (val) {
            return val.toFixed(2); 
          }
        }
      },
      stroke: {
        curve: 'smooth',
        width: 5, 
      },
      grid: {
        show: true, 
        borderColor: '#3B4758', 
        strokeDashArray: 0, 
        position: 'back', 
        xaxis: {
          lines: {
            show: false, 

          },
        },
        yaxis: {
          lines: {
            show: true,
          },
        },
      }
    };

    const chart = new ApexCharts(document.querySelector("#chart") as HTMLElement, options);
    chart.render();
  }

  priceData: { date: number, price: number }[] = [];

  transformData(reportSet: Array<PriceHistoryObj>): void {

    this.priceData = reportSet
      .map(data => ({
        date: new Date(data.date!).getTime(),
        price: data.capital !== undefined ? data.capital : 0
      }));

  }

  onScroll(event: WheelEvent): void {
    event.preventDefault(); // Previene il comportamento di scroll della pagina
  }

}