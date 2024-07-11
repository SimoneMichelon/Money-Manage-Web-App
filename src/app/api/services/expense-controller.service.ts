/* tslint:disable */
/* eslint-disable */
import { HttpClient, HttpContext } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { BaseService } from '../base-service';
import { ApiConfiguration } from '../api-configuration';
import { StrictHttpResponse } from '../strict-http-response';

import { createExpense } from '../fn/expense-controller/create-expense';
import { CreateExpense$Params } from '../fn/expense-controller/create-expense';
import { deleteExpenseById } from '../fn/expense-controller/delete-expense-by-id';
import { DeleteExpenseById$Params } from '../fn/expense-controller/delete-expense-by-id';
import { ExpenseDto } from '../models/expense-dto';
import { getAllExpense } from '../fn/expense-controller/get-all-expense';
import { GetAllExpense$Params } from '../fn/expense-controller/get-all-expense';
import { getExpenseById } from '../fn/expense-controller/get-expense-by-id';
import { GetExpenseById$Params } from '../fn/expense-controller/get-expense-by-id';
import { updateExpense } from '../fn/expense-controller/update-expense';
import { UpdateExpense$Params } from '../fn/expense-controller/update-expense';

@Injectable({ providedIn: 'root' })
export class ExpenseControllerService extends BaseService {
  constructor(config: ApiConfiguration, http: HttpClient) {
    super(config, http);
  }

  /** Path part for operation `getAllExpense()` */
  static readonly GetAllExpensePath = '/api/expense-management/expenses';

  /**
   * Get all Expense.
   *
   * Get all Expense
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getAllExpense()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllExpense$Response(params?: GetAllExpense$Params, context?: HttpContext): Observable<StrictHttpResponse<Array<ExpenseDto>>> {
    return getAllExpense(this.http, this.rootUrl, params, context);
  }

  /**
   * Get all Expense.
   *
   * Get all Expense
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getAllExpense$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getAllExpense(params?: GetAllExpense$Params, context?: HttpContext): Observable<Array<ExpenseDto>> {
    return this.getAllExpense$Response(params, context).pipe(
      map((r: StrictHttpResponse<Array<ExpenseDto>>): Array<ExpenseDto> => r.body)
    );
  }

  /** Path part for operation `updateExpense()` */
  static readonly UpdateExpensePath = '/api/expense-management/expenses';

  /**
   * Update expense.
   *
   * Update expense by given data
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `updateExpense()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateExpense$Response(params: UpdateExpense$Params, context?: HttpContext): Observable<StrictHttpResponse<ExpenseDto>> {
    return updateExpense(this.http, this.rootUrl, params, context);
  }

  /**
   * Update expense.
   *
   * Update expense by given data
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `updateExpense$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  updateExpense(params: UpdateExpense$Params, context?: HttpContext): Observable<ExpenseDto> {
    return this.updateExpense$Response(params, context).pipe(
      map((r: StrictHttpResponse<ExpenseDto>): ExpenseDto => r.body)
    );
  }

  /** Path part for operation `createExpense()` */
  static readonly CreateExpensePath = '/api/expense-management/expenses';

  /**
   * Create expense.
   *
   * Creation expense by given data
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `createExpense()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createExpense$Response(params: CreateExpense$Params, context?: HttpContext): Observable<StrictHttpResponse<ExpenseDto>> {
    return createExpense(this.http, this.rootUrl, params, context);
  }

  /**
   * Create expense.
   *
   * Creation expense by given data
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `createExpense$Response()` instead.
   *
   * This method sends `application/json` and handles request body of type `application/json`.
   */
  createExpense(params: CreateExpense$Params, context?: HttpContext): Observable<ExpenseDto> {
    return this.createExpense$Response(params, context).pipe(
      map((r: StrictHttpResponse<ExpenseDto>): ExpenseDto => r.body)
    );
  }

  /** Path part for operation `getExpenseById()` */
  static readonly GetExpenseByIdPath = '/api/expense-management/expenses/{id}';

  /**
   * Find Expense by id.
   *
   * Get a Expense by ID
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `getExpenseById()` instead.
   *
   * This method doesn't expect any request body.
   */
  getExpenseById$Response(params: GetExpenseById$Params, context?: HttpContext): Observable<StrictHttpResponse<ExpenseDto>> {
    return getExpenseById(this.http, this.rootUrl, params, context);
  }

  /**
   * Find Expense by id.
   *
   * Get a Expense by ID
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `getExpenseById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  getExpenseById(params: GetExpenseById$Params, context?: HttpContext): Observable<ExpenseDto> {
    return this.getExpenseById$Response(params, context).pipe(
      map((r: StrictHttpResponse<ExpenseDto>): ExpenseDto => r.body)
    );
  }

  /** Path part for operation `deleteExpenseById()` */
  static readonly DeleteExpenseByIdPath = '/api/expense-management/expenses/{id}';

  /**
   * Delete expense by id.
   *
   * Delete expense by id
   *
   * This method provides access to the full `HttpResponse`, allowing access to response headers.
   * To access only the response body, use `deleteExpenseById()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteExpenseById$Response(params: DeleteExpenseById$Params, context?: HttpContext): Observable<StrictHttpResponse<ExpenseDto>> {
    return deleteExpenseById(this.http, this.rootUrl, params, context);
  }

  /**
   * Delete expense by id.
   *
   * Delete expense by id
   *
   * This method provides access only to the response body.
   * To access the full response (for headers, for example), `deleteExpenseById$Response()` instead.
   *
   * This method doesn't expect any request body.
   */
  deleteExpenseById(params: DeleteExpenseById$Params, context?: HttpContext): Observable<ExpenseDto> {
    return this.deleteExpenseById$Response(params, context).pipe(
      map((r: StrictHttpResponse<ExpenseDto>): ExpenseDto => r.body)
    );
  }

}
