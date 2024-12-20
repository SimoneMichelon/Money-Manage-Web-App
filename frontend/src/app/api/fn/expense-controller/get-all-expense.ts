/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { ExpenseDto } from '../../models/expense-dto';

export interface GetAllExpense$Params {
}

export function getAllExpense(http: HttpClient, rootUrl: string, params?: GetAllExpense$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ExpenseDto>>> {
  const rb = new RequestBuilder(rootUrl, getAllExpense.PATH, 'get');
  if (params) {
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<Array<ExpenseDto>>;
    })
  );
}

getAllExpense.PATH = '/api/expense-management/expenses';
