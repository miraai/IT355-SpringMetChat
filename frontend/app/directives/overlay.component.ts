import { Component, OnInit } from '@angular/core';
import {OverlayService} from "../services/overlay.service";

@Component({
    moduleId: module.id,
    selector: 'overlay',
    templateUrl: 'overlay.component.html'
})

export class OverlayComponent {
    message: any;

    constructor(private overlayService: OverlayService) { }

    ngOnInit() {
        this.overlayService.getMessage().subscribe(message => { this.message = message; });
    }
}