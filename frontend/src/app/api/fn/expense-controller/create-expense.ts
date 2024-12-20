/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ExpenseDto } from '../../models/expense-dto';

export interface CreateExpense$Params {
      body: ExpenseDto
}

export function createExpense(http: HttpClient, rootUrl: string, params: CreateExpense$Params, context?: HttpContext): Observable<StrictHttpResponse<ExpenseDto>> {
  const rb = new RequestBuilder(rootUrl, createExpense.PATH, 'post');
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

createExpense.PATH = '/api/expense-management/expenses';
