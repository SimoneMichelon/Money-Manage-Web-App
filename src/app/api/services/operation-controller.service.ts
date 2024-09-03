/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { getAllOperations } from '../fn/operation-controller/get-all-operations';
import { GetAllOperations$Params } from '../fn/operation-controller/get-all-operations';
import { getAllOperationsByVaultId } from '../fn/operation-controller/get-all-operations-by-vault-id';
import { GetAllOperationsByVaultId$Params } from '../fn/operation-controller/get-all-operations-by-vault-id';
import { OperationListsWrapper } from '../models/operation-lists-wrapper';

@Injectable({ providedIn: 'root' })
export class OperationControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
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
  getAllOperations$Response(params?: GetAllOperations$Params, context?: HttpContext): Observable<StrictHttpResponse<OperationListsWrapper>> {
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
  getAllOperations(params?: GetAllOperations$Params, context?: HttpContext): Observable<OperationListsWrapper> {
    return this.getAllOperations$Response(params, context).pipe(
      map((r: StrictHttpResponse<OperationListsWrapper>): OperationListsWrapper => r.body)
    );
  }

  /** Path part for operation `getAllOperationsByVaultId()` */
  static readonly GetAllOperationsByVaultIdPath = '/api/operation-management/operations/vault/{id}';

  /**
   * Get all Operations by Vault Id - ADMIN.
   *
   * Get all Operations by Vault given
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllOperationsByVaultId()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllOperationsByVaultId$Response(params: GetAllOperationsByVaultId$Params, context?: HttpContext): Observable<StrictHttpResponse<OperationListsWrapper>> {
    return getAllOperationsByVaultId(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all Operations by Vault Id - ADMIN.
   *
   * Get all Operations by Vault given
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllOperationsByVaultId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllOperationsByVaultId(params: GetAllOperationsByVaultId$Params, context?: HttpContext): Observable<OperationListsWrapper> {
    return this.getAllOperationsByVaultId$Response(params, context).pipe(
      map((r: StrictHttpResponse<OperationListsWrapper>): OperationListsWrapper => r.body)
    );
  }

}
