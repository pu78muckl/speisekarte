import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IKunde } from 'app/shared/model/kunde.model';
import { AccountService } from 'app/core';
import { KundeService } from './kunde.service';

@Component({
    selector: 'jhi-kunde',
    templateUrl: './kunde.component.html'
})
export class KundeComponent implements OnInit, OnDestroy {
    kundes: IKunde[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected kundeService: KundeService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.kundeService.query().subscribe(
            (res: HttpResponse<IKunde[]>) => {
                this.kundes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInKundes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IKunde) {
        return item.id;
    }

    registerChangeInKundes() {
        this.eventSubscriber = this.eventManager.subscribe('kundeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
