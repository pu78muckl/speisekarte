/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpeisekarteTestModule } from '../../../test.module';
import { SpeisekarteDetailComponent } from 'app/entities/speisekarte/speisekarte-detail.component';
import { Speisekarte } from 'app/shared/model/speisekarte.model';

describe('Component Tests', () => {
    describe('Speisekarte Management Detail Component', () => {
        let comp: SpeisekarteDetailComponent;
        let fixture: ComponentFixture<SpeisekarteDetailComponent>;
        const route = ({ data: of({ speisekarte: new Speisekarte('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SpeisekarteTestModule],
                declarations: [SpeisekarteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SpeisekarteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SpeisekarteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.speisekarte).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
