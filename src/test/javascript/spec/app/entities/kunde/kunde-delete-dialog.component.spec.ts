/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SpeisekarteTestModule } from '../../../test.module';
import { KundeDeleteDialogComponent } from 'app/entities/kunde/kunde-delete-dialog.component';
import { KundeService } from 'app/entities/kunde/kunde.service';

describe('Component Tests', () => {
    describe('Kunde Management Delete Component', () => {
        let comp: KundeDeleteDialogComponent;
        let fixture: ComponentFixture<KundeDeleteDialogComponent>;
        let service: KundeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SpeisekarteTestModule],
                declarations: [KundeDeleteDialogComponent]
            })
                .overrideTemplate(KundeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(KundeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KundeService);
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
