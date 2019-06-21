/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SpeisekarteTestModule } from '../../../test.module';
import { SpeiseUpdateComponent } from 'app/entities/speise/speise-update.component';
import { SpeiseService } from 'app/entities/speise/speise.service';
import { Speise } from 'app/shared/model/speise.model';

describe('Component Tests', () => {
    describe('Speise Management Update Component', () => {
        let comp: SpeiseUpdateComponent;
        let fixture: ComponentFixture<SpeiseUpdateComponent>;
        let service: SpeiseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SpeisekarteTestModule],
                declarations: [SpeiseUpdateComponent]
            })
                .overrideTemplate(SpeiseUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SpeiseUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpeiseService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Speise('123');
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.speise = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Speise();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.speise = entity;
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
