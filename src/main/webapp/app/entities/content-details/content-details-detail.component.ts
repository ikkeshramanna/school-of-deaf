import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IContentDetails } from 'app/shared/model/content-details.model';

@Component({
  selector: 'jhi-content-details-detail',
  templateUrl: './content-details-detail.component.html',
})
export class ContentDetailsDetailComponent implements OnInit {
  contentDetails: IContentDetails | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentDetails }) => (this.contentDetails = contentDetails));
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
