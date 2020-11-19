import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        loadChildren: () => import('./category/category.module').then(m => m.SchoolOfDeafCategoryModule),
      },
      {
        path: 'content',
        loadChildren: () => import('./content/content.module').then(m => m.SchoolOfDeafContentModule),
      },
      {
        path: 'content-details',
        loadChildren: () => import('./content-details/content-details.module').then(m => m.SchoolOfDeafContentDetailsModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class SchoolOfDeafEntityModule {}
