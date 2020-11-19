import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SchoolOfDeafTestModule } from '../../../test.module';
import { ContentDetailsComponent } from 'app/entities/content-details/content-details.component';
import { ContentDetailsService } from 'app/entities/content-details/content-details.service';
import { ContentDetails } from 'app/shared/model/content-details.model';

describe('Component Tests', () => {
  describe('ContentDetails Management Component', () => {
    let comp: ContentDetailsComponent;
    let fixture: ComponentFixture<ContentDetailsComponent>;
    let service: ContentDetailsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [SchoolOfDeafTestModule],
        declarations: [ContentDetailsComponent],
      })
        .overrideTemplate(ContentDetailsComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ContentDetailsComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ContentDetailsService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ContentDetails(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.contentDetails && comp.contentDetails[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
