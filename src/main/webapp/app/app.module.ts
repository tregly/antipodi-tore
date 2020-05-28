import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AntipodiStoreSharedModule } from 'app/shared/shared.module';
import { AntipodiStoreCoreModule } from 'app/core/core.module';
import { AntipodiStoreAppRoutingModule } from './app-routing.module';
import { AntipodiStoreHomeModule } from './home/home.module';
import { AntipodiStoreEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    AntipodiStoreSharedModule,
    AntipodiStoreCoreModule,
    AntipodiStoreHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AntipodiStoreEntityModule,
    AntipodiStoreAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class AntipodiStoreAppModule {}
