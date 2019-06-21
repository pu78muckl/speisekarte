import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SpeisekarteKundeModule } from './kunde/kunde.module';
import { SpeisekarteSpeiseModule } from './speise/speise.module';
import { SpeisekarteSpeisekarteModule } from './speisekarte/speisekarte.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SpeisekarteKundeModule,
        SpeisekarteSpeiseModule,
        SpeisekarteSpeisekarteModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SpeisekarteEntityModule {}
