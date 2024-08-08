/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createVault } from '../fn/vault-controller/create-vault';
import { CreateVault$Params } from '../fn/vault-controller/create-vault';
import { deleteVaultById } from '../fn/vault-controller/delete-vault-by-id';
import { DeleteVaultById$Params } from '../fn/vault-controller/delete-vault-by-id';
import { getAllVaults } from '../fn/vault-controller/get-all-vaults';
import { GetAllVaults$Params } from '../fn/vault-controller/get-all-vaults';
import { getAllVaultsByPrincipal } from '../fn/vault-controller/get-all-vaults-by-principal';
import { GetAllVaultsByPrincipal$Params } from '../fn/vault-controller/get-all-vaults-by-principal';
import { getAllVaultsByUserId } from '../fn/vault-controller/get-all-vaults-by-user-id';
import { GetAllVaultsByUserId$Params } from '../fn/vault-controller/get-all-vaults-by-user-id';
import { getVaultById } from '../fn/vault-controller/get-vault-by-id';
import { GetVaultById$Params } from '../fn/vault-controller/get-vault-by-id';
import { updateVault } from '../fn/vault-controller/update-vault';
import { UpdateVault$Params } from '../fn/vault-controller/update-vault';
import { VaultDto } from '../models/vault-dto';

@Injectable({ providedIn: 'root' })
export class VaultControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllVaults()` */
  static readonly GetAllVaultsPath = '/api/vault-management/vaults';

  /**
   * Get all Vault - ADMIN.
   *
   * Get all vault
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllVaults()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllVaults$Response(params?: GetAllVaults$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<VaultDto>>> {
    return getAllVaults(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all Vault - ADMIN.
   *
   * Get all vault
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllVaults$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllVaults(params?: GetAllVaults$Params, context?: HttpContext): Observable<Array<VaultDto>> {
    return this.getAllVaults$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<VaultDto>>): Array<VaultDto> => r.body)
    );
  }

  /** Path part for operation `updateVault()` */
  static readonly UpdateVaultPath = '/api/vault-management/vaults';

  /**
   * Update vault.
   *
   * Update vault by given data
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateVault()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateVault$Response(params: UpdateVault$Params, context?: HttpContext): Observable<StrictHttpResponse<VaultDto>> {
    return updateVault(this.http, this.rootUrl, params, context);
  }

  /**
   * Update vault.
   *
   * Update vault by given data
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateVault$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateVault(params: UpdateVault$Params, context?: HttpContext): Observable<VaultDto> {
    return this.updateVault$Response(params, context).pipe(
      map((r: StrictHttpResponse<VaultDto>): VaultDto => r.body)
    );
  }

  /** Path part for operation `createVault()` */
  static readonly CreateVaultPath = '/api/vault-management/vaults';

  /**
   * Create vault .
   *
   * Creation vault by given data
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createVault()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createVault$Response(params: CreateVault$Params, context?: HttpContext): Observable<StrictHttpResponse<VaultDto>> {
    return createVault(this.http, this.rootUrl, params, context);
  }

  /**
   * Create vault .
   *
   * Creation vault by given data
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createVault$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createVault(params: CreateVault$Params, context?: HttpContext): Observable<VaultDto> {
    return this.createVault$Response(params, context).pipe(
      map((r: StrictHttpResponse<VaultDto>): VaultDto => r.body)
    );
  }

  /** Path part for operation `getVaultById()` */
  static readonly GetVaultByIdPath = '/api/vault-management/vaults/{id}';

  /**
   * Find vault by id - ADMIN / GUEST.
   *
   * Find vault by id
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getVaultById()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVaultById$Response(params: GetVaultById$Params, context?: HttpContext): Observable<StrictHttpResponse<VaultDto>> {
    return getVaultById(this.http, this.rootUrl, params, context);
  }

  /**
   * Find vault by id - ADMIN / GUEST.
   *
   * Find vault by id
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getVaultById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getVaultById(params: GetVaultById$Params, context?: HttpContext): Observable<VaultDto> {
    return this.getVaultById$Response(params, context).pipe(
      map((r: StrictHttpResponse<VaultDto>): VaultDto => r.body)
    );
  }

  /** Path part for operation `deleteVaultById()` */
  static readonly DeleteVaultByIdPath = '/api/vault-management/vaults/{id}';

  /**
   * Delete vault by id.
   *
   * Delete vault by id
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteVaultById()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteVaultById$Response(params: DeleteVaultById$Params, context?: HttpContext): Observable<StrictHttpResponse<VaultDto>> {
    return deleteVaultById(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete vault by id.
   *
   * Delete vault by id
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteVaultById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteVaultById(params: DeleteVaultById$Params, context?: HttpContext): Observable<VaultDto> {
    return this.deleteVaultById$Response(params, context).pipe(
      map((r: StrictHttpResponse<VaultDto>): VaultDto => r.body)
    );
  }

  /** Path part for operation `getAllVaultsByPrincipal()` */
  static readonly GetAllVaultsByPrincipalPath = '/api/vault-management/vaults/user';

  /**
   * Get all vault by Principal.
   *
   * Get all vault Principal
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllVaultsByPrincipal()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllVaultsByPrincipal$Response(params?: GetAllVaultsByPrincipal$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<VaultDto>>> {
    return getAllVaultsByPrincipal(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all vault by Principal.
   *
   * Get all vault Principal
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllVaultsByPrincipal$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllVaultsByPrincipal(params?: GetAllVaultsByPrincipal$Params, context?: HttpContext): Observable<Array<VaultDto>> {
    return this.getAllVaultsByPrincipal$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<VaultDto>>): Array<VaultDto> => r.body)
    );
  }

  /** Path part for operation `getAllVaultsByUserId()` */
  static readonly GetAllVaultsByUserIdPath = '/api/vault-management/vaults/user/{id}';

  /**
   * Get all vault by User Id - ADMIN.
   *
   * Get all vault by User given
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllVaultsByUserId()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllVaultsByUserId$Response(params: GetAllVaultsByUserId$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<VaultDto>>> {
    return getAllVaultsByUserId(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all vault by User Id - ADMIN.
   *
   * Get all vault by User given
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllVaultsByUserId$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllVaultsByUserId(params: GetAllVaultsByUserId$Params, context?: HttpContext): Observable<Array<VaultDto>> {
    return this.getAllVaultsByUserId$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<VaultDto>>): Array<VaultDto> => r.body)
    );
  }

}
