/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { PriceHistoryObj } from '../../models/price-history-obj';

export interface GetVaultHistoryReport$Params {
  id: number;
}

export function getVaultHistoryReport(http: HttpClient, rootUrl: string, params: GetVaultHistoryReport$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<PriceHistoryObj>>> {
  const rb = new RequestBuilder(rootUrl, getVaultHistoryReport.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<PriceHistoryObj>>;
    })
  );
}

getVaultHistoryReport.PATH = '/api/operation-management/report/vault/{id}';
