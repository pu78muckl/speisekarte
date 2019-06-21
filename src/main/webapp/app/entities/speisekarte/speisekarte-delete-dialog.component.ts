import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpeisekarte } from 'app/shared/model/speisekarte.model';
import { SpeisekarteService } from './speisekarte.service';

@Component({
    selector: 'jhi-speisekarte-delete-dialog',
    templateUrl: './speisekarte-delete-dialog.component.html'
})
export class SpeisekarteDeleteDialogComponent {
    speisekarte: ISpeisekarte;

    constructor(
        protected speisekarteService: SpeisekarteService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: string) {
        this.speisekarteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'speisekarteListModification',
                content: 'Deleted an speisekarte'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-speisekarte-delete-popup',
    template: ''
})
export class SpeisekarteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ speisekarte }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SpeisekarteDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.speisekarte = speisekarte;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
