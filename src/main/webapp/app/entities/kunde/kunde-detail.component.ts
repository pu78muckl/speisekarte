import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKunde } from 'app/shared/model/kunde.model';

@Component({
    selector: 'jhi-kunde-detail',
    templateUrl: './kunde-detail.component.html'
})
export class KundeDetailComponent implements OnInit {
    kunde: IKunde;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ kunde }) => {
            this.kunde = kunde;
        });
    }

    previousState() {
        window.history.back();
    }
}
