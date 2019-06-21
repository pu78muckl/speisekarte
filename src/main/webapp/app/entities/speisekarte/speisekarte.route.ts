import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Speisekarte } from 'app/shared/model/speisekarte.model';
import { SpeisekarteService } from './speisekarte.service';
import { SpeisekarteComponent } from './speisekarte.component';
import { SpeisekarteDetailComponent } from './speisekarte-detail.component';
import { SpeisekarteUpdateComponent } from './speisekarte-update.component';
import { SpeisekarteDeletePopupComponent } from './speisekarte-delete-dialog.component';
import { ISpeisekarte } from 'app/shared/model/speisekarte.model';

@Injectable({ providedIn: 'root' })
export class SpeisekarteResolve implements Resolve<ISpeisekarte> {
    constructor(private service: SpeisekarteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Speisekarte> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Speisekarte>) => response.ok),
                map((speisekarte: HttpResponse<Speisekarte>) => speisekarte.body)
            );
        }
        return of(new Speisekarte());
    }
}

export const speisekarteRoute: Routes = [
    {
        path: 'speisekarte',
        component: SpeisekarteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Speisekartes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'speisekarte/:id/view',
        component: SpeisekarteDetailComponent,
        resolve: {
            speisekarte: SpeisekarteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Speisekartes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'speisekarte/new',
        component: SpeisekarteUpdateComponent,
        resolve: {
            speisekarte: SpeisekarteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Speisekartes'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'speisekarte/:id/edit',
        component: SpeisekarteUpdateComponent,
        resolve: {
            speisekarte: SpeisekarteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Speisekartes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const speisekartePopupRoute: Routes = [
    {
        path: 'speisekarte/:id/delete',
        component: SpeisekarteDeletePopupComponent,
        resolve: {
            speisekarte: SpeisekarteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Speisekartes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
