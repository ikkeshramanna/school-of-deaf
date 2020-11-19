import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SchoolOfDeafSharedModule } from 'app/shared/shared.module';
import { ContentDetailsComponent } from './content-details.component';
import { ContentDetailsDetailComponent } from './content-details-detail.component';
import { ContentDetailsUpdateComponent } from './content-details-update.component';
import { ContentDetailsDeleteDialogComponent } from './content-details-delete-dialog.component';
import { contentDetailsRoute } from './content-details.route';

@NgModule({
  imports: [SchoolOfDeafSharedModule, RouterModule.forChild(contentDetailsRoute)],
  declarations: [
    ContentDetailsComponent,
    ContentDetailsDetailComponent,
    ContentDetailsUpdateComponent,
    ContentDetailsDeleteDialogComponent,
  ],
  entryComponents: [ContentDetailsDeleteDialogComponent],
})
export class SchoolOfDeafContentDetailsModule {}
