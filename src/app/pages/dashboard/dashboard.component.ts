import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import ApexCharts from 'apexcharts';
import { firstValueFrom } from 'rxjs';
import { OperationDto, PriceHistoryObj, VaultDto, VaultSummary } from '../../api/models';
import { OperationControllerService, VaultControllerService } from '../../api/services';
import { AuthService } from '../../security/auth.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'], // Corretto 'styleUrl' in 'styleUrls'
})
export class DashboardComponent implements OnInit, OnDestroy {
  selected: VaultDto | null = null;
  dataReport: VaultSummary | null = null;
  isOpen = false;
  vaults?: Array<VaultDto>;
  operations?: Array<OperationDto>;
  report?: Array<VaultSummary> = [];
  reportSet?: Array<PriceHistoryObj>;
  private chart: ApexCharts | null = null; // Riferimento al grafico
  priceData: { date: number, price: number }[] = [];

  constructor(
    private vaultControllerService: VaultControllerService,
    private operationControllerService: OperationControllerService,
    private authService: AuthService,
  ) {}

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

      // Distruggi il grafico precedente se esiste
      if (this.chart) {
        this.chart.destroy();
      }

      // Inizializza il grafico con i nuovi dati
      this.initChart();
    } else {
      console.error('No data available for the chart.');
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

    this.chart = new ApexCharts(document.querySelector("#chart") as HTMLElement, options);
    this.chart.render();
  }

  transformData(reportSet: Array<PriceHistoryObj>): void {
    this.priceData = reportSet.map(data => ({
      date: new Date(data.date!).getTime(),
      price: data.capital !== undefined ? data.capital : 0
    }));
  }

  onScroll(event: WheelEvent): void {
    event.preventDefault(); // Previene il comportamento di scroll della pagina
  }

  ngOnDestroy() {
    // Distruggi il grafico quando il componente viene distrutto
    if (this.chart) {
      this.chart.destroy();
    }
  }
}
