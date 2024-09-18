import {
  Directive,
  TemplateRef,
  ViewContainerRef,
  Input,
  OnInit,
} from '@angular/core';
import { AuthDataService } from '../data-services/auth-data.service';

@Directive({
  selector: '[appAuthRole]',
})
export class AuthRoleDirective implements OnInit {
  @Input() userRole: string;

  constructor(
    private authDataService: AuthDataService,
    private templateRef: TemplateRef<any>,
    private viewContainerRef: ViewContainerRef
  ) {}

  ngOnInit() {
    this.checkUserRole();
  }

  checkUserRole() {
    this.authDataService.authenticatedUser.subscribe((authenticatedUser) => {
      if (authenticatedUser) {
        const roleIndex = authenticatedUser.roles.findIndex(
          (role) => role === this.userRole
        );

        if (roleIndex > -1) {
          this.viewContainerRef.createEmbeddedView(this.templateRef);
        }
      }
    });
  }
}
