import { Directive, forwardRef } from '@angular/core';
import { NG_VALIDATORS, FormControl } from '@angular/forms';


@Directive({
    selector: '[validateNumber][ngModel],[validateNumber][formControl]',
    providers: [
        { provide: NG_VALIDATORS, useExisting: forwardRef(() => NumberValidator), multi: true }
    ]
})
export class NumberValidator {

    validator: Function;

    constructor() {
        this.validator = (c: FormControl) => {
            return c.value > 0 ? null : {
                validateNumber: {
                    valid: false
                }
            };
        };;
    }

    validate(c: FormControl) {
        return this.validator(c);
    }
}