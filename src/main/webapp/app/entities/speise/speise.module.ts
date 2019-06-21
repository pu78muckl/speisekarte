import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpeisekarteSharedModule } from 'app/shared';
import {
    SpeiseComponent,
    SpeiseDetailComponent,
    SpeiseUpdateComponent,
    SpeiseDeletePopupComponent,
    SpeiseDeleteDialogComponent,
    speiseRoute,
    speisePopupRoute
} from './';

const ENTITY_STATES = [...speiseRoute, ...speisePopupRoute];

@NgModule({
    imports: [SpeisekarteSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SpeiseComponent, SpeiseDetailComponent, SpeiseUpdateComponent, SpeiseDeleteDialogComponent, SpeiseDeletePopupComponent],
    entryComponents: [SpeiseComponent, SpeiseUpdateComponent, SpeiseDeleteDialogComponent, SpeiseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SpeisekarteSpeiseModule {}
