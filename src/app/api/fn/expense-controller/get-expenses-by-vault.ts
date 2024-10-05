/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ExpenseDto } from '../../models/expense-dto';

export interface GetExpensesByVault$Params {
  id: number;
}

export function getExpensesByVault(http: HttpClient, rootUrl: string, params: GetExpensesByVault$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ExpenseDto>>> {
  const rb = new RequestBuilder(rootUrl, getExpensesByVault.PATH, 'get');
  if (params) {
    rb.path('id', params.id, {});
  }

  return http.request(
    rb.build({ responseType: 'blob', accept: '*/*', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<ExpenseDto>>;
    })
  );
}

getExpensesByVault.PATH = '/api/expense-management/expenses/vault/{id}';
