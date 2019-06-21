/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SpeisekarteTestModule } from '../../../test.module';
import { SpeisekarteComponent } from 'app/entities/speisekarte/speisekarte.component';
import { SpeisekarteService } from 'app/entities/speisekarte/speisekarte.service';
import { Speisekarte } from 'app/shared/model/speisekarte.model';

describe('Component Tests', () => {
    describe('Speisekarte Management Component', () => {
        let comp: SpeisekarteComponent;
        let fixture: ComponentFixture<SpeisekarteComponent>;
        let service: SpeisekarteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SpeisekarteTestModule],
                declarations: [SpeisekarteComponent],
                providers: []
            })
                .overrideTemplate(SpeisekarteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SpeisekarteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpeisekarteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Speisekarte('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.speisekartes[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
