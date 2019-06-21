/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SpeisekarteTestModule } from '../../../test.module';
import { SpeisekarteUpdateComponent } from 'app/entities/speisekarte/speisekarte-update.component';
import { SpeisekarteService } from 'app/entities/speisekarte/speisekarte.service';
import { Speisekarte } from 'app/shared/model/speisekarte.model';

describe('Component Tests', () => {
    describe('Speisekarte Management Update Component', () => {
        let comp: SpeisekarteUpdateComponent;
        let fixture: ComponentFixture<SpeisekarteUpdateComponent>;
        let service: SpeisekarteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SpeisekarteTestModule],
                declarations: [SpeisekarteUpdateComponent]
            })
                .overrideTemplate(SpeisekarteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SpeisekarteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpeisekarteService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Speisekarte('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.speisekarte = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Speisekarte();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.speisekarte = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.create).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));
        });
    });
});
