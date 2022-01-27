import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { LogInComponent } from './components/log-in/log-in.component';
import { AuthGuard } from './guards/auth-guard.service';
import { SettingsComponent } from './components/settings/settings.component';

const routes: Routes = [
  { path: '', component: LogInComponent },
  { path: 'login', redirectTo: '/' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'settings', component: SettingsComponent, canActivate: [AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {
}
