/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { RevenueDto } from '../../models/revenue-dto';

export interface GetRevenueById$Params {
  id: number;
}

export function getRevenueById(http: HttpClient, rootUrl: string, params: GetRevenueById$Params, context?: HttpContext): Observable<StrictHttpResponse<RevenueDto>> {
  const rb = new RequestBuilder(rootUrl, getRevenueById.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<RevenueDto>;
    })
  );
}

getRevenueById.PATH = '/api/revenue-management/revenues/{id}';
