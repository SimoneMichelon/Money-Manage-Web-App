/* tslint:disable */
/* eslint-disable */
import { ExpenseDto } from '../models/expense-dto';
import { RevenueDto } from '../models/revenue-dto';
export interface OperationListsWrapper {
  listExpense?: Array<ExpenseDto>;
  listRevenue?: Array<RevenueDto>;
}
