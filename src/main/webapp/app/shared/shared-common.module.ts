import { NgModule } from '@angular/core';

import { SpeisekarteSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [SpeisekarteSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [SpeisekarteSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SpeisekarteSharedCommonModule {}
