import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { SchoolOfDeafTestModule } from '../../../test.module';
import { ContentDetailsDetailComponent } from 'app/entities/content-details/content-details-detail.component';
import { ContentDetails } from 'app/shared/model/content-details.model';

describe('Component Tests', () => {
  describe('ContentDetails Management Detail Component', () => {
    let comp: ContentDetailsDetailComponent;
    let fixture: ComponentFixture<ContentDetailsDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ contentDetails: new ContentDetails(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolOfDeafTestModule],
        declarations: [ContentDetailsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }],
      })
        .overrideTemplate(ContentDetailsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ContentDetailsDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load contentDetails on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.contentDetails).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
