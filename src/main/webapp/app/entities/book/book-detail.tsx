import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './book.reducer';
import { IBook } from 'app/shared/model/book.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IBookDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookDetail = (props: IBookDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { bookEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="bookapplicationApp.book.detail.title">Book</Translate> [<b>{bookEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="title">
              <Translate contentKey="bookapplicationApp.book.title">Title</Translate>
            </span>
          </dt>
          <dd>{bookEntity.title}</dd>
          <dt>
            <span id="author">
              <Translate contentKey="bookapplicationApp.book.author">Author</Translate>
            </span>
          </dt>
          <dd>{bookEntity.author}</dd>
          <dt>
            <span id="publisher">
              <Translate contentKey="bookapplicationApp.book.publisher">Publisher</Translate>
            </span>
          </dt>
          <dd>{bookEntity.publisher}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="bookapplicationApp.book.price">Price</Translate>
            </span>
          </dt>
          <dd>{bookEntity.price}</dd>
          <dt>
            <Translate contentKey="bookapplicationApp.book.shelf">Shelf</Translate>
          </dt>
          <dd>{bookEntity.shelf ? bookEntity.shelf.number : ''}</dd>
        </dl>
        <Button tag={Link} to="/book" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/book/${bookEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ book }: IRootState) => ({
  bookEntity: book.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookDetail);
