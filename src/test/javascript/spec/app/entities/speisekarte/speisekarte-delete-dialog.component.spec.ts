/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SpeisekarteTestModule } from '../../../test.module';
import { SpeisekarteDeleteDialogComponent } from 'app/entities/speisekarte/speisekarte-delete-dialog.component';
import { SpeisekarteService } from 'app/entities/speisekarte/speisekarte.service';

describe('Component Tests', () => {
    describe('Speisekarte Management Delete Component', () => {
        let comp: SpeisekarteDeleteDialogComponent;
        let fixture: ComponentFixture<SpeisekarteDeleteDialogComponent>;
        let service: SpeisekarteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SpeisekarteTestModule],
                declarations: [SpeisekarteDeleteDialogComponent]
            })
                .overrideTemplate(SpeisekarteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SpeisekarteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpeisekarteService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete('123');
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith('123');
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
