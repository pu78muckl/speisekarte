import { IId } from 'app/shared/model/id.model';
import { ISpeisekarte } from 'app/shared/model/speisekarte.model';
import { IKunde } from 'app/shared/model/kunde.model';

export interface ISpeise {
    id?: string;
    bezeichnung?: string;
    preis?: number;
    beschreibung?: string;
    kunde?: IKunde;
    speisekarte?: ISpeisekarte;
}

export class Speise implements ISpeise {
    constructor(
        public id?: string,
        public bezeichnung?: string,
        public preis?: number,
        public beschreibung?: string,
        public kunde?: IKunde,
        public speisekarte?: ISpeisekarte
    ) {}
}
