import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { IContentDetails, ContentDetails } from 'app/shared/model/content-details.model';
import { ContentDetailsService } from './content-details.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IContent } from 'app/shared/model/content.model';
import { ContentService } from 'app/entities/content/content.service';

@Component({
  selector: 'jhi-content-details-update',
  templateUrl: './content-details-update.component.html',
})
export class ContentDetailsUpdateComponent implements OnInit {
  isSaving = false;
  contents: IContent[] = [];

  editForm = this.fb.group({
    id: [],
    contentPicture: [],
    contentPictureContentType: [],
    contentSignPicture: [],
    contentSignPictureContentType: [],
    video: [],
    content: [],
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected contentDetailsService: ContentDetailsService,
    protected contentService: ContentService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentDetails }) => {
      this.updateForm(contentDetails);

      this.contentService.query().subscribe((res: HttpResponse<IContent[]>) => (this.contents = res.body || []));
    });
  }

  updateForm(contentDetails: IContentDetails): void {
    this.editForm.patchValue({
      id: contentDetails.id,
      contentPicture: contentDetails.contentPicture,
      contentPictureContentType: contentDetails.contentPictureContentType,
      contentSignPicture: contentDetails.contentSignPicture,
      contentSignPictureContentType: contentDetails.contentSignPictureContentType,
      video: contentDetails.video,
      content: contentDetails.content,
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: any, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('schoolOfDeafApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contentDetails = this.createFromForm();
    if (contentDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.contentDetailsService.update(contentDetails));
    } else {
      this.subscribeToSaveResponse(this.contentDetailsService.create(contentDetails));
    }
  }

  private createFromForm(): IContentDetails {
    return {
      ...new ContentDetails(),
      id: this.editForm.get(['id'])!.value,
      contentPictureContentType: this.editForm.get(['contentPictureContentType'])!.value,
      contentPicture: this.editForm.get(['contentPicture'])!.value,
      contentSignPictureContentType: this.editForm.get(['contentSignPictureContentType'])!.value,
      contentSignPicture: this.editForm.get(['contentSignPicture'])!.value,
      video: this.editForm.get(['video'])!.value,
      content: this.editForm.get(['content'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContentDetails>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IContent): any {
    return item.id;
  }
}
