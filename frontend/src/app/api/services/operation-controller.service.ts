/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { CategoryReportDto } from '../models/category-report-dto';
import { getAllOperations } from '../fn/operation-controller/get-all-operations';
import { GetAllOperations$Params } from '../fn/operation-controller/get-all-operations';
import { getAllOperationsByPrincipal } from '../fn/operation-controller/get-all-operations-by-principal';
import { GetAllOperationsByPrincipal$Params } from '../fn/operation-controller/get-all-operations-by-principal';
import { getAllOperationsByVaultId } from '../fn/operation-controller/get-all-operations-by-vault-id';
import { GetAllOperationsByVaultId$Params } from '../fn/operation-controller/get-all-operations-by-vault-id';
import { getOperationCategoryReportPerVault } from '../fn/operation-controller/get-operation-category-report-per-vault';
import { GetOperationCategoryReportPerVault$Params } from '../fn/operation-controller/get-operation-category-report-per-vault';
import { getVaultHistoryReport } from '../fn/operation-controller/get-vault-history-report';
import { GetVaultHistoryReport$Params } from '../fn/operation-controller/get-vault-history-report';
import { OperationDto } from '../models/operation-dto';
import { PriceHistoryObj } from '../models/price-history-obj';

@Injectable({ providedIn: 'root' })
export class OperationControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getVaultHistoryReport()` */
  static readonly GetVaultHistoryReportPath = '/api/operation-management/report/vault/{id}';

  /**
   * Get the vault report.
   *
   * Get the vault report
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getVaultHistoryReport()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVaultHistoryReport$Response(params: GetVaultHistoryReport$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<PriceHistoryObj>>> {
    return getVaultHistoryReport(this.http, this.rootUrl, params, context);
  }

  /**
   * Get the vault report.
   *
   * Get the vault report
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getVaultHistoryReport$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVaultHistoryReport(params: GetVaultHistoryReport$Params, context?: HttpContext): Observable<Array<PriceHistoryObj>> {
    return this.getVaultHistoryReport$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<PriceHistoryObj>>): Array<PriceHistoryObj> => r.body)
    );
  }

  /** Path part for operation `getOperationCategoryReportPerVault()` */
  static readonly GetOperationCategoryReportPerVaultPath = '/api/operation-management/report/category/vault/{id}';

  /**
   * Get the category report on vault id.
   *
   * Get the category report on vault id
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getOperationCategoryReportPerVault()` instead.
   *
   * This method doesn't expect any request body.
   */
  getOperationCategoryReportPerVault$Response(params: GetOperationCategoryReportPerVault$Params, context?: HttpContext): Observable<StrictHttpResponse<{
[key: string]: Array<CategoryReportDto>;
}>> {
    return getOperationCategoryReportPerVault(this.http, this.rootUrl, params, context);
  }

  /**
   * Get the category report on vault id.
   *
   * Get the category report on vault id
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getOperationCategoryReportPerVault$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getOperationCategoryReportPerVault(params: GetOperationCategoryReportPerVault$Params, context?: HttpContext): Observable<{
[key: string]: Array<CategoryReportDto>;
}> {
    return this.getOperationCategoryReportPerVault$Response(params, context).pipe(
      map((r: StrictHttpResponse<{
[key: string]: Array<CategoryReportDto>;
}>): {
[key: string]: Array<CategoryReportDto>;
} => r.body)
    );
  }

  /** Path part for operation `getAllOperations()` */
  static readonly GetAllOperationsPath = '/api/operation-management/operations';

  /**
   * Get all Operations - ADMIN.
   *
   * Get all Operations
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllOperations()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllOperations$Response(params?: GetAllOperations$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<OperationDto>>> {
    return getAllOperations(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all Operations - ADMIN.
   *
   * Get all Operations
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllOperations$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllOperations(params?: GetAllOperations$Params, context?: HttpContext): Observable<Array<OperationDto>> {
    return this.getAllOperations$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<OperationDto>>): Array<OperationDto> => r.body)
    );
  }

  /** Path part for operation `getAllOperationsByVaultId()` */
  static readonly GetAllOperationsByVaultIdPath = '/api/operation-management/operations/vault/{id}';

  /**
   * Get all Operations by Vault Id.
   *
   * Get all Operations by Vault
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllOperationsByVaultId()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllOperationsByVaultId$Response(params: GetAllOperationsByVaultId$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<OperationDto>>> {
    return getAllOperationsByVaultId(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all Operations by Vault Id.
   *
   * Get all Operations by Vault
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllOperationsByVaultId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllOperationsByVaultId(params: GetAllOperationsByVaultId$Params, context?: HttpContext): Observable<Array<OperationDto>> {
    return this.getAllOperationsByVaultId$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<OperationDto>>): Array<OperationDto> => r.body)
    );
  }

  /** Path part for operation `getAllOperationsByPrincipal()` */
  static readonly GetAllOperationsByPrincipalPath = '/api/operation-management/operations/principal';

  /**
   * Get all Operations by principal.
   *
   * Get all Operations by principal
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllOperationsByPrincipal()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllOperationsByPrincipal$Response(params?: GetAllOperationsByPrincipal$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<OperationDto>>> {
    return getAllOperationsByPrincipal(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all Operations by principal.
   *
   * Get all Operations by principal
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllOperationsByPrincipal$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllOperationsByPrincipal(params?: GetAllOperationsByPrincipal$Params, context?: HttpContext): Observable<Array<OperationDto>> {
    return this.getAllOperationsByPrincipal$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<OperationDto>>): Array<OperationDto> => r.body)
    );
  }

}
