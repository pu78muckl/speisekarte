import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpeisekarteSharedModule } from 'app/shared';
import {
    SpeisekarteComponent,
    SpeisekarteDetailComponent,
    SpeisekarteUpdateComponent,
    SpeisekarteDeletePopupComponent,
    SpeisekarteDeleteDialogComponent,
    speisekarteRoute,
    speisekartePopupRoute
} from './';

const ENTITY_STATES = [...speisekarteRoute, ...speisekartePopupRoute];

@NgModule({
    imports: [SpeisekarteSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SpeisekarteComponent,
        SpeisekarteDetailComponent,
        SpeisekarteUpdateComponent,
        SpeisekarteDeleteDialogComponent,
        SpeisekarteDeletePopupComponent
    ],
    entryComponents: [SpeisekarteComponent, SpeisekarteUpdateComponent, SpeisekarteDeleteDialogComponent, SpeisekarteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SpeisekarteSpeisekarteModule {}
