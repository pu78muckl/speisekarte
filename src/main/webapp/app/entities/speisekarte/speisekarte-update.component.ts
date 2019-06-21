import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ISpeisekarte } from 'app/shared/model/speisekarte.model';
import { SpeisekarteService } from './speisekarte.service';
import { ISpeise } from 'app/shared/model/speise.model';
import { SpeiseService } from 'app/entities/speise';

@Component({
    selector: 'jhi-speisekarte-update',
    templateUrl: './speisekarte-update.component.html'
})
export class SpeisekarteUpdateComponent implements OnInit {
    speisekarte: ISpeisekarte;
    isSaving: boolean;

    speises: ISpeise[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected speisekarteService: SpeisekarteService,
        protected speiseService: SpeiseService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ speisekarte }) => {
            this.speisekarte = speisekarte;
        });
        this.speiseService.query().subscribe(
            (res: HttpResponse<ISpeise[]>) => {
                this.speises = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.speisekarte.id !== undefined) {
            this.subscribeToSaveResponse(this.speisekarteService.update(this.speisekarte));
        } else {
            this.subscribeToSaveResponse(this.speisekarteService.create(this.speisekarte));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpeisekarte>>) {
        result.subscribe((res: HttpResponse<ISpeisekarte>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSpeiseById(index: number, item: ISpeise) {
        return item.id;
    }
}
