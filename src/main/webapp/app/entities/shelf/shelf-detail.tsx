import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './shelf.reducer';
import { IShelf } from 'app/shared/model/shelf.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IShelfDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ShelfDetail = (props: IShelfDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { shelfEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="bookapplicationApp.shelf.detail.title">Shelf</Translate> [<b>{shelfEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="number">
              <Translate contentKey="bookapplicationApp.shelf.number">Number</Translate>
            </span>
          </dt>
          <dd>{shelfEntity.number}</dd>
          <dt>
            <span id="desc">
              <Translate contentKey="bookapplicationApp.shelf.desc">Desc</Translate>
            </span>
          </dt>
          <dd>{shelfEntity.desc}</dd>
        </dl>
        <Button tag={Link} to="/shelf" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/shelf/${shelfEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ shelf }: IRootState) => ({
  shelfEntity: shelf.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ShelfDetail);
