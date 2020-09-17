import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Shelf from './shelf';
import ShelfDetail from './shelf-detail';
import ShelfUpdate from './shelf-update';
import ShelfDeleteDialog from './shelf-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ShelfUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ShelfUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ShelfDetail} />
      <ErrorBoundaryRoute path={match.url} component={Shelf} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ShelfDeleteDialog} />
  </>
);

export default Routes;
