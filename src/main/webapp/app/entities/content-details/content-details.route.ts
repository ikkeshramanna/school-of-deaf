import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContentDetails, ContentDetails } from 'app/shared/model/content-details.model';
import { ContentDetailsService } from './content-details.service';
import { ContentDetailsComponent } from './content-details.component';
import { ContentDetailsDetailComponent } from './content-details-detail.component';
import { ContentDetailsUpdateComponent } from './content-details-update.component';

@Injectable({ providedIn: 'root' })
export class ContentDetailsResolve implements Resolve<IContentDetails> {
  constructor(private service: ContentDetailsService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContentDetails> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contentDetails: HttpResponse<ContentDetails>) => {
          if (contentDetails.body) {
            return of(contentDetails.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContentDetails());
  }
}

export const contentDetailsRoute: Routes = [
  {
    path: '',
    component: ContentDetailsComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'schoolOfDeafApp.contentDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: ContentDetailsDetailComponent,
    resolve: {
      contentDetails: ContentDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'schoolOfDeafApp.contentDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: ContentDetailsUpdateComponent,
    resolve: {
      contentDetails: ContentDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'schoolOfDeafApp.contentDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: ContentDetailsUpdateComponent,
    resolve: {
      contentDetails: ContentDetailsResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'schoolOfDeafApp.contentDetails.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
