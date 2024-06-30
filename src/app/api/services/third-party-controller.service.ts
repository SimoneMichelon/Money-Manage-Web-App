/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createThirdParty } from '../fn/third-party-controller/create-third-party';
import { CreateThirdParty$Params } from '../fn/third-party-controller/create-third-party';
import { deleteThirdPartyById } from '../fn/third-party-controller/delete-third-party-by-id';
import { DeleteThirdPartyById$Params } from '../fn/third-party-controller/delete-third-party-by-id';
import { getAllThirdParties } from '../fn/third-party-controller/get-all-third-parties';
import { GetAllThirdParties$Params } from '../fn/third-party-controller/get-all-third-parties';
import { getThirdPartyById } from '../fn/third-party-controller/get-third-party-by-id';
import { GetThirdPartyById$Params } from '../fn/third-party-controller/get-third-party-by-id';
import { ThirdPartyDto } from '../models/third-party-dto';
import { updateThirdParty } from '../fn/third-party-controller/update-third-party';
import { UpdateThirdParty$Params } from '../fn/third-party-controller/update-third-party';

@Injectable({ providedIn: 'root' })
export class ThirdPartyControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllThirdParties()` */
  static readonly GetAllThirdPartiesPath = '/api/third-party-management/third-parties';

  /**
   * Get all Third Parties.
   *
   * Get all Third Parties
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllThirdParties()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllThirdParties$Response(params?: GetAllThirdParties$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ThirdPartyDto>>> {
    return getAllThirdParties(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all Third Parties.
   *
   * Get all Third Parties
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllThirdParties$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllThirdParties(params?: GetAllThirdParties$Params, context?: HttpContext): Observable<Array<ThirdPartyDto>> {
    return this.getAllThirdParties$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<ThirdPartyDto>>): Array<ThirdPartyDto> => r.body)
    );
  }

  /** Path part for operation `updateThirdParty()` */
  static readonly UpdateThirdPartyPath = '/api/third-party-management/third-parties';

  /**
   * Update Third Party.
   *
   * Update a Third Party
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateThirdParty()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateThirdParty$Response(params: UpdateThirdParty$Params, context?: HttpContext): Observable<StrictHttpResponse<ThirdPartyDto>> {
    return updateThirdParty(this.http, this.rootUrl, params, context);
  }

  /**
   * Update Third Party.
   *
   * Update a Third Party
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateThirdParty$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateThirdParty(params: UpdateThirdParty$Params, context?: HttpContext): Observable<ThirdPartyDto> {
    return this.updateThirdParty$Response(params, context).pipe(
      map((r: StrictHttpResponse<ThirdPartyDto>): ThirdPartyDto => r.body)
    );
  }

  /** Path part for operation `createThirdParty()` */
  static readonly CreateThirdPartyPath = '/api/third-party-management/third-parties';

  /**
   * Create Third Party.
   *
   * Create a Third Party
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createThirdParty()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createThirdParty$Response(params: CreateThirdParty$Params, context?: HttpContext): Observable<StrictHttpResponse<ThirdPartyDto>> {
    return createThirdParty(this.http, this.rootUrl, params, context);
  }

  /**
   * Create Third Party.
   *
   * Create a Third Party
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createThirdParty$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createThirdParty(params: CreateThirdParty$Params, context?: HttpContext): Observable<ThirdPartyDto> {
    return this.createThirdParty$Response(params, context).pipe(
      map((r: StrictHttpResponse<ThirdPartyDto>): ThirdPartyDto => r.body)
    );
  }

  /** Path part for operation `getThirdPartyById()` */
  static readonly GetThirdPartyByIdPath = '/api/third-party-management/third-parties/{id}';

  /**
   * Find Third Party by ID.
   *
   * Get a Third Party by ID
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getThirdPartyById()` instead.
   *
   * This method doesn't expect any request body.
   */
  getThirdPartyById$Response(params: GetThirdPartyById$Params, context?: HttpContext): Observable<StrictHttpResponse<ThirdPartyDto>> {
    return getThirdPartyById(this.http, this.rootUrl, params, context);
  }

  /**
   * Find Third Party by ID.
   *
   * Get a Third Party by ID
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getThirdPartyById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getThirdPartyById(params: GetThirdPartyById$Params, context?: HttpContext): Observable<ThirdPartyDto> {
    return this.getThirdPartyById$Response(params, context).pipe(
      map((r: StrictHttpResponse<ThirdPartyDto>): ThirdPartyDto => r.body)
    );
  }

  /** Path part for operation `deleteThirdPartyById()` */
  static readonly DeleteThirdPartyByIdPath = '/api/third-party-management/third-parties/{id}';

  /**
   * Delete Third Party by ID.
   *
   * Delete a Third Party by ID
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteThirdPartyById()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteThirdPartyById$Response(params: DeleteThirdPartyById$Params, context?: HttpContext): Observable<StrictHttpResponse<ThirdPartyDto>> {
    return deleteThirdPartyById(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete Third Party by ID.
   *
   * Delete a Third Party by ID
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteThirdPartyById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteThirdPartyById(params: DeleteThirdPartyById$Params, context?: HttpContext): Observable<ThirdPartyDto> {
    return this.deleteThirdPartyById$Response(params, context).pipe(
      map((r: StrictHttpResponse<ThirdPartyDto>): ThirdPartyDto => r.body)
    );
  }

}
