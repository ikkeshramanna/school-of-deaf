import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IContentDetails } from 'app/shared/model/content-details.model';
import { ContentDetailsService } from './content-details.service';
import { ContentDetailsDeleteDialogComponent } from './content-details-delete-dialog.component';

@Component({
  selector: 'jhi-content-details',
  templateUrl: './content-details.component.html',
})
export class ContentDetailsComponent implements OnInit, OnDestroy {
  contentDetails?: IContentDetails[];
  eventSubscriber?: Subscription;

  constructor(
    protected contentDetailsService: ContentDetailsService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.contentDetailsService.query().subscribe((res: HttpResponse<IContentDetails[]>) => (this.contentDetails = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInContentDetails();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IContentDetails): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType = '', base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInContentDetails(): void {
    this.eventSubscriber = this.eventManager.subscribe('contentDetailsListModification', () => this.loadAll());
  }

  delete(contentDetails: IContentDetails): void {
    const modalRef = this.modalService.open(ContentDetailsDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.contentDetails = contentDetails;
  }
}
