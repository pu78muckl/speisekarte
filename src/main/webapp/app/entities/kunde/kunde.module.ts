import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SpeisekarteSharedModule } from 'app/shared';
import {
    KundeComponent,
    KundeDetailComponent,
    KundeUpdateComponent,
    KundeDeletePopupComponent,
    KundeDeleteDialogComponent,
    kundeRoute,
    kundePopupRoute
} from './';

const ENTITY_STATES = [...kundeRoute, ...kundePopupRoute];

@NgModule({
    imports: [SpeisekarteSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [KundeComponent, KundeDetailComponent, KundeUpdateComponent, KundeDeleteDialogComponent, KundeDeletePopupComponent],
    entryComponents: [KundeComponent, KundeUpdateComponent, KundeDeleteDialogComponent, KundeDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SpeisekarteKundeModule {}
