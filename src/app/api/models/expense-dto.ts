/* tslint:disable */
/* eslint-disable */
import { CategoryDto } from '../models/category-dto';
import { ThirdPartyDto } from '../models/third-party-dto';
import { VaultDto } from '../models/vault-dto';
export interface ExpenseDto {
  amount: number;
  categoryDTO: CategoryDto;
  causal: string;
  endDate: string;
  id?: number;
  isProgrammed: boolean;
  startDate: string;
  thirdPartyDTO: ThirdPartyDto;
  vaultDTO: VaultDto;
}
