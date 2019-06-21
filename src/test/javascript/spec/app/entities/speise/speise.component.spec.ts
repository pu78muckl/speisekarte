/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SpeisekarteTestModule } from '../../../test.module';
import { SpeiseComponent } from 'app/entities/speise/speise.component';
import { SpeiseService } from 'app/entities/speise/speise.service';
import { Speise } from 'app/shared/model/speise.model';

describe('Component Tests', () => {
    describe('Speise Management Component', () => {
        let comp: SpeiseComponent;
        let fixture: ComponentFixture<SpeiseComponent>;
        let service: SpeiseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SpeisekarteTestModule],
                declarations: [SpeiseComponent],
                providers: []
            })
                .overrideTemplate(SpeiseComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SpeiseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpeiseService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Speise('123')],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.speises[0]).toEqual(jasmine.objectContaining({ id: '123' }));
        });
    });
});
