import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpeisekarte } from 'app/shared/model/speisekarte.model';

@Component({
    selector: 'jhi-speisekarte-detail',
    templateUrl: './speisekarte-detail.component.html'
})
export class SpeisekarteDetailComponent implements OnInit {
    speisekarte: ISpeisekarte;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ speisekarte }) => {
            this.speisekarte = speisekarte;
        });
    }

    previousState() {
        window.history.back();
    }
}
