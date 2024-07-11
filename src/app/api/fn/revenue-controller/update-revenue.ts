/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { RevenueDto } from '../../models/revenue-dto';

export interface UpdateRevenue$Params {
      body: RevenueDto
}

export function updateRevenue(http: HttpClient, rootUrl: string, params: UpdateRevenue$Params, context?: HttpContext): Observable<StrictHttpResponse<RevenueDto>> {
  const rb = new RequestBuilder(rootUrl, updateRevenue.PATH, 'put');
  if (params) {
    rb.body(params.body, 'application/json');
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

updateRevenue.PATH = '/api/revenue-management/revenues';
