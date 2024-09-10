/* tslint:disable */
/* eslint-disable */
import { Category } from '../models/category';
import { ThirdParty } from '../models/third-party';
import { Vault } from '../models/vault';
export interface OperationDto {
  amount?: number;
  category?: Category;
  causal?: string;
  endDate?: string;
  id?: number;
  isProgrammed?: boolean;
  startDate?: string;
  thirdPartys?: ThirdParty;
  type?: 'REVENUE' | 'EXPENSE';
  vault?: Vault;
}
