/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SpeisekarteTestModule } from '../../../test.module';
import { SpeiseDeleteDialogComponent } from 'app/entities/speise/speise-delete-dialog.component';
import { SpeiseService } from 'app/entities/speise/speise.service';

describe('Component Tests', () => {
    describe('Speise Management Delete Component', () => {
        let comp: SpeiseDeleteDialogComponent;
        let fixture: ComponentFixture<SpeiseDeleteDialogComponent>;
        let service: SpeiseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SpeisekarteTestModule],
                declarations: [SpeiseDeleteDialogComponent]
            })
                .overrideTemplate(SpeiseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SpeiseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SpeiseService);
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
