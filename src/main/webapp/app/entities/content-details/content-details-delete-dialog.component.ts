import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContentDetails } from 'app/shared/model/content-details.model';
import { ContentDetailsService } from './content-details.service';

@Component({
  templateUrl: './content-details-delete-dialog.component.html',
})
export class ContentDetailsDeleteDialogComponent {
  contentDetails?: IContentDetails;

  constructor(
    protected contentDetailsService: ContentDetailsService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contentDetailsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contentDetailsListModification');
      this.activeModal.close();
    });
  }
}
