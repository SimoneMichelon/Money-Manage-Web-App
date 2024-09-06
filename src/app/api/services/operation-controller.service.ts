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
import { getAllOperationsByPrincipal } from '../fn/operation-controller/get-all-operations-by-principal';
import { GetAllOperationsByPrincipal$Params } from '../fn/operation-controller/get-all-operations-by-principal';
import { getAllOperationsByVaultId } from '../fn/operation-controller/get-all-operations-by-vault-id';
import { GetAllOperationsByVaultId$Params } from '../fn/operation-controller/get-all-operations-by-vault-id';
import { OperationDto } from '../models/operation-dto';

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
