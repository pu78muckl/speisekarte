/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SpeisekarteTestModule } from '../../../test.module';
import { SpeiseDetailComponent } from 'app/entities/speise/speise-detail.component';
import { Speise } from 'app/shared/model/speise.model';

describe('Component Tests', () => {
    describe('Speise Management Detail Component', () => {
        let comp: SpeiseDetailComponent;
        let fixture: ComponentFixture<SpeiseDetailComponent>;
        const route = ({ data: of({ speise: new Speise('123') }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SpeisekarteTestModule],
                declarations: [SpeiseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SpeiseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SpeiseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.speise).toEqual(jasmine.objectContaining({ id: '123' }));
            });
        });
    });
});
