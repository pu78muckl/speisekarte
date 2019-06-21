/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SpeisekarteTestModule } from '../../../test.module';
import { KundeComponent } from 'app/entities/kunde/kunde.component';
import { KundeService } from 'app/entities/kunde/kunde.service';
import { Kunde } from 'app/shared/model/kunde.model';

describe('Component Tests', () => {
    describe('Kunde Management Component', () => {
        let comp: KundeComponent;
        let fixture: ComponentFixture<KundeComponent>;
        let service: KundeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SpeisekarteTestModule],
                declarations: [KundeComponent],
                providers: []
            })
                .overrideTemplate(KundeComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(KundeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KundeService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Kunde('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.kundes[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
