/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createCredential } from '../fn/credential-controller/create-credential';
import { CreateCredential$Params } from '../fn/credential-controller/create-credential';
import { CredentialDto } from '../models/credential-dto';
import { getAllCredentials } from '../fn/credential-controller/get-all-credentials';
import { GetAllCredentials$Params } from '../fn/credential-controller/get-all-credentials';

@Injectable({ providedIn: 'root' })
export class CredentialControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllCredentials()` */
  static readonly GetAllCredentialsPath = '/auth-management/credentials';

  /**
   * Get all Credentials.
   *
   * Get all credentials
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllCredentials()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllCredentials$Response(params?: GetAllCredentials$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<CredentialDto>>> {
    return getAllCredentials(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all Credentials.
   *
   * Get all credentials
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllCredentials$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllCredentials(params?: GetAllCredentials$Params, context?: HttpContext): Observable<Array<CredentialDto>> {
    return this.getAllCredentials$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<CredentialDto>>): Array<CredentialDto> => r.body)
    );
  }

  /** Path part for operation `createCredential()` */
  static readonly CreateCredentialPath = '/auth-management/credentials';

  /**
   * Create new Credentials.
   *
   * Create credential by given data
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createCredential()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createCredential$Response(params: CreateCredential$Params, context?: HttpContext): Observable<StrictHttpResponse<CredentialDto>> {
    return createCredential(this.http, this.rootUrl, params, context);
  }

  /**
   * Create new Credentials.
   *
   * Create credential by given data
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createCredential$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createCredential(params: CreateCredential$Params, context?: HttpContext): Observable<CredentialDto> {
    return this.createCredential$Response(params, context).pipe(
      map((r: StrictHttpResponse<CredentialDto>): CredentialDto => r.body)
    );
  }

}
