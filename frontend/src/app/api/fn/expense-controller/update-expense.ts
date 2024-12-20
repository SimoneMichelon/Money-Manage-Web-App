/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ExpenseDto } from '../../models/expense-dto';

export interface UpdateExpense$Params {
      body: ExpenseDto
}

export function updateExpense(http: HttpClient, rootUrl: string, params: UpdateExpense$Params, context?: HttpContext): Observable<StrictHttpResponse<ExpenseDto>> {
  const rb = new RequestBuilder(rootUrl, updateExpense.PATH, 'put');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<ExpenseDto>;
    })
  );
}

updateExpense.PATH = '/api/expense-management/expenses';
