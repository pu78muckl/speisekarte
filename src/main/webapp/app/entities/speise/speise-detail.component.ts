import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpeise } from 'app/shared/model/speise.model';

@Component({
    selector: 'jhi-speise-detail',
    templateUrl: './speise-detail.component.html'
})
export class SpeiseDetailComponent implements OnInit {
    speise: ISpeise;

    constructor(protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ speise }) => {
            this.speise = speise;
        });
    }

    previousState() {
        window.history.back();
    }
}
