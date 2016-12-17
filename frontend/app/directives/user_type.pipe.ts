import { Pipe, PipeTransform } from '@angular/core';

@Pipe({name: 'user_type'})
export class UserTypePipe implements PipeTransform {
    transform(value: string, args: string[]): any {
        switch(value) {
            case '1': return 'admin';
            case '2': return 'user';
            case '3': return 'guest';
        }
        return value;
    }
}