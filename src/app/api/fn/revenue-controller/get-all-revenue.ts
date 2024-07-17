/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { RevenueDto } from '../../models/revenue-dto';

export interface GetAllRevenue$Params {
}

export function getAllRevenue(http: HttpClient, rootUrl: string, params?: GetAllRevenue$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<RevenueDto>>> {
  const rb = new RequestBuilder(rootUrl, getAllRevenue.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<RevenueDto>>;
    })
  );
}

getAllRevenue.PATH = '/api/revenue-management/revenues';
