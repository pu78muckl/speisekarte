import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Speise } from 'app/shared/model/speise.model';
import { SpeiseService } from './speise.service';
import { SpeiseComponent } from './speise.component';
import { SpeiseDetailComponent } from './speise-detail.component';
import { SpeiseUpdateComponent } from './speise-update.component';
import { SpeiseDeletePopupComponent } from './speise-delete-dialog.component';
import { ISpeise } from 'app/shared/model/speise.model';

@Injectable({ providedIn: 'root' })
export class SpeiseResolve implements Resolve<ISpeise> {
    constructor(private service: SpeiseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Speise> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Speise>) => response.ok),
                map((speise: HttpResponse<Speise>) => speise.body)
            );
        }
        return of(new Speise());
    }
}

export const speiseRoute: Routes = [
    {
        path: 'speise',
        component: SpeiseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Speises'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'speise/:id/view',
        component: SpeiseDetailComponent,
        resolve: {
            speise: SpeiseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Speises'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'speise/new',
        component: SpeiseUpdateComponent,
        resolve: {
            speise: SpeiseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Speises'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'speise/:id/edit',
        component: SpeiseUpdateComponent,
        resolve: {
            speise: SpeiseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Speises'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const speisePopupRoute: Routes = [
    {
        path: 'speise/:id/delete',
        component: SpeiseDeletePopupComponent,
        resolve: {
            speise: SpeiseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Speises'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
