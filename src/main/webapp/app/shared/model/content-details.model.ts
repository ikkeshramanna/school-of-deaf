import { IContent } from 'app/shared/model/content.model';

export interface IContentDetails {
  id?: number;
  contentPictureContentType?: string;
  contentPicture?: any;
  contentSignPictureContentType?: string;
  contentSignPicture?: any;
  video?: string;
  content?: IContent;
}

export class ContentDetails implements IContentDetails {
  constructor(
    public id?: number,
    public contentPictureContentType?: string,
    public contentPicture?: any,
    public contentSignPictureContentType?: string,
    public contentSignPicture?: any,
    public video?: string,
    public content?: IContent
  ) {}
}
