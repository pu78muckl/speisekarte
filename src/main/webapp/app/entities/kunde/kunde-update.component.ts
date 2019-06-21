import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IKunde } from 'app/shared/model/kunde.model';
import { KundeService } from './kunde.service';

@Component({
    selector: 'jhi-kunde-update',
    templateUrl: './kunde-update.component.html'
})
export class KundeUpdateComponent implements OnInit {
    kunde: IKunde;
    isSaving: boolean;

    constructor(protected kundeService: KundeService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ kunde }) => {
            this.kunde = kunde;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.kunde.id !== undefined) {
            this.subscribeToSaveResponse(this.kundeService.update(this.kunde));
        } else {
            this.subscribeToSaveResponse(this.kundeService.create(this.kunde));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IKunde>>) {
        result.subscribe((res: HttpResponse<IKunde>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
