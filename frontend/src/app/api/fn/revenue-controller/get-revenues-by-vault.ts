/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { RevenueDto } from '../../models/revenue-dto';

export interface GetRevenuesByVault$Params {
  id: number;
}

export function getRevenuesByVault(http: HttpClient, rootUrl: string, params: GetRevenuesByVault$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<RevenueDto>>> {
  const rb = new RequestBuilder(rootUrl, getRevenuesByVault.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<RevenueDto>>;
    })
  );
}

getRevenuesByVault.PATH = '/api/revenue-management/revenues/vault/{id}';
