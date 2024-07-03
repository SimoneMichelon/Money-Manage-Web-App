/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createRevenue } from '../fn/revenue-controller/create-revenue';
import { CreateRevenue$Params } from '../fn/revenue-controller/create-revenue';
import { deleteRevenueById } from '../fn/revenue-controller/delete-revenue-by-id';
import { DeleteRevenueById$Params } from '../fn/revenue-controller/delete-revenue-by-id';
import { getAllRevenue } from '../fn/revenue-controller/get-all-revenue';
import { GetAllRevenue$Params } from '../fn/revenue-controller/get-all-revenue';
import { getRevenueById } from '../fn/revenue-controller/get-revenue-by-id';
import { GetRevenueById$Params } from '../fn/revenue-controller/get-revenue-by-id';
import { RevenueDto } from '../models/revenue-dto';
import { updateRevenue } from '../fn/revenue-controller/update-revenue';
import { UpdateRevenue$Params } from '../fn/revenue-controller/update-revenue';

@Injectable({ providedIn: 'root' })
export class RevenueControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllRevenue()` */
  static readonly GetAllRevenuePath = '/api/revenue-management/revenues';

  /**
   * Get all Revenue.
   *
   * Get all Revenue
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllRevenue()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllRevenue$Response(params?: GetAllRevenue$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<RevenueDto>>> {
    return getAllRevenue(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all Revenue.
   *
   * Get all Revenue
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllRevenue$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllRevenue(params?: GetAllRevenue$Params, context?: HttpContext): Observable<Array<RevenueDto>> {
    return this.getAllRevenue$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<RevenueDto>>): Array<RevenueDto> => r.body)
    );
  }

  /** Path part for operation `updateRevenue()` */
  static readonly UpdateRevenuePath = '/api/revenue-management/revenues';

  /**
   * Update revenue.
   *
   * Update revenue by given data
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateRevenue()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateRevenue$Response(params: UpdateRevenue$Params, context?: HttpContext): Observable<StrictHttpResponse<RevenueDto>> {
    return updateRevenue(this.http, this.rootUrl, params, context);
  }

  /**
   * Update revenue.
   *
   * Update revenue by given data
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateRevenue$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateRevenue(params: UpdateRevenue$Params, context?: HttpContext): Observable<RevenueDto> {
    return this.updateRevenue$Response(params, context).pipe(
      map((r: StrictHttpResponse<RevenueDto>): RevenueDto => r.body)
    );
  }

  /** Path part for operation `createRevenue()` */
  static readonly CreateRevenuePath = '/api/revenue-management/revenues';

  /**
   * Create revenue.
   *
   * Creation revenue by given data
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createRevenue()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createRevenue$Response(params: CreateRevenue$Params, context?: HttpContext): Observable<StrictHttpResponse<RevenueDto>> {
    return createRevenue(this.http, this.rootUrl, params, context);
  }

  /**
   * Create revenue.
   *
   * Creation revenue by given data
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createRevenue$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createRevenue(params: CreateRevenue$Params, context?: HttpContext): Observable<RevenueDto> {
    return this.createRevenue$Response(params, context).pipe(
      map((r: StrictHttpResponse<RevenueDto>): RevenueDto => r.body)
    );
  }

  /** Path part for operation `getRevenueById()` */
  static readonly GetRevenueByIdPath = '/api/revenue-management/revenues/{id}';

  /**
   * Find Revenue by id.
   *
   * Get a Revenue by ID
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getRevenueById()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRevenueById$Response(params: GetRevenueById$Params, context?: HttpContext): Observable<StrictHttpResponse<RevenueDto>> {
    return getRevenueById(this.http, this.rootUrl, params, context);
  }

  /**
   * Find Revenue by id.
   *
   * Get a Revenue by ID
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getRevenueById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getRevenueById(params: GetRevenueById$Params, context?: HttpContext): Observable<RevenueDto> {
    return this.getRevenueById$Response(params, context).pipe(
      map((r: StrictHttpResponse<RevenueDto>): RevenueDto => r.body)
    );
  }

  /** Path part for operation `deleteRevenueById()` */
  static readonly DeleteRevenueByIdPath = '/api/revenue-management/revenues/{id}';

  /**
   * Delete revenue by id.
   *
   * Delete revenue by id
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteRevenueById()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteRevenueById$Response(params: DeleteRevenueById$Params, context?: HttpContext): Observable<StrictHttpResponse<RevenueDto>> {
    return deleteRevenueById(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete revenue by id.
   *
   * Delete revenue by id
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteRevenueById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteRevenueById(params: DeleteRevenueById$Params, context?: HttpContext): Observable<RevenueDto> {
    return this.deleteRevenueById$Response(params, context).pipe(
      map((r: StrictHttpResponse<RevenueDto>): RevenueDto => r.body)
    );
  }

}
