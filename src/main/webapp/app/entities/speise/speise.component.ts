import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISpeise } from 'app/shared/model/speise.model';
import { AccountService } from 'app/core';
import { SpeiseService } from './speise.service';

@Component({
    selector: 'jhi-speise',
    templateUrl: './speise.component.html'
})
export class SpeiseComponent implements OnInit, OnDestroy {
    speises: ISpeise[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected speiseService: SpeiseService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.speiseService.query().subscribe(
            (res: HttpResponse<ISpeise[]>) => {
                this.speises = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSpeises();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISpeise) {
        return item.id;
    }

    registerChangeInSpeises() {
        this.eventSubscriber = this.eventManager.subscribe('speiseListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
