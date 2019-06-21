import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISpeise } from 'app/shared/model/speise.model';
import { SpeiseService } from './speise.service';
import { ISpeisekarte } from 'app/shared/model/speisekarte.model';
import { SpeisekarteService } from 'app/entities/speisekarte';
import { IKunde } from 'app/shared/model/kunde.model';
import { KundeService } from 'app/entities/kunde';

@Component({
    selector: 'jhi-speise-update',
    templateUrl: './speise-update.component.html'
})
export class SpeiseUpdateComponent implements OnInit {
    speise: ISpeise;
    isSaving: boolean;

    ids: IKunde[];

    speisekartes: ISpeisekarte[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected speiseService: SpeiseService,
        protected idService: KundeService,
        protected speisekarteService: SpeisekarteService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ speise }) => {
            this.speise = speise;
        });
        this.idService.query().subscribe(
            (res: HttpResponse<IKunde[]>) => {
                this.ids = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.speisekarteService.query().subscribe(
            (res: HttpResponse<ISpeisekarte[]>) => {
                this.speisekartes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.speise.id !== undefined) {
            this.subscribeToSaveResponse(this.speiseService.update(this.speise));
        } else {
            this.subscribeToSaveResponse(this.speiseService.create(this.speise));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpeise>>) {
        result.subscribe((res: HttpResponse<ISpeise>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackIdById(index: number, item: IKunde) {
        return item.id;
    }

    trackSpeisekarteById(index: number, item: ISpeisekarte) {
        return item.id;
    }
}
