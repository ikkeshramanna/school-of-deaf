import { IContentDetails } from 'app/shared/model/content-details.model';
import { ICategory } from 'app/shared/model/category.model';

export interface IContent {
  id?: number;
  contentName?: string;
  contentDetails?: IContentDetails[];
  contentCategory?: ICategory;
}

export class Content implements IContent {
  constructor(
    public id?: number,
    public contentName?: string,
    public contentDetails?: IContentDetails[],
    public contentCategory?: ICategory
  ) {}
}
