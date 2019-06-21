import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISpeisekarte } from 'app/shared/model/speisekarte.model';
import { AccountService } from 'app/core';
import { SpeisekarteService } from './speisekarte.service';

@Component({
    selector: 'jhi-speisekarte',
    templateUrl: './speisekarte.component.html'
})
export class SpeisekarteComponent implements OnInit, OnDestroy {
    speisekartes: ISpeisekarte[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        protected speisekarteService: SpeisekarteService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected accountService: AccountService
    ) {}

    loadAll() {
        this.speisekarteService.query().subscribe(
            (res: HttpResponse<ISpeisekarte[]>) => {
                this.speisekartes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSpeisekartes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISpeisekarte) {
        return item.id;
    }

    registerChangeInSpeisekartes() {
        this.eventSubscriber = this.eventManager.subscribe('speisekarteListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
