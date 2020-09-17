import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IShelf } from 'app/shared/model/shelf.model';
import { getEntities as getShelves } from 'app/entities/shelf/shelf.reducer';
import { getEntity, updateEntity, createEntity, reset } from './book.reducer';
import { IBook } from 'app/shared/model/book.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IBookUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const BookUpdate = (props: IBookUpdateProps) => {
  const [shelfId, setShelfId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { bookEntity, shelves, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/book' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getShelves();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...bookEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="bookapplicationApp.book.home.createOrEditLabel">
            <Translate contentKey="bookapplicationApp.book.home.createOrEditLabel">Create or edit a Book</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : bookEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="book-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="book-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="titleLabel" for="book-title">
                  <Translate contentKey="bookapplicationApp.book.title">Title</Translate>
                </Label>
                <AvField id="book-title" type="text" name="title" />
              </AvGroup>
              <AvGroup>
                <Label id="authorLabel" for="book-author">
                  <Translate contentKey="bookapplicationApp.book.author">Author</Translate>
                </Label>
                <AvField id="book-author" type="text" name="author" />
              </AvGroup>
              <AvGroup>
                <Label id="publisherLabel" for="book-publisher">
                  <Translate contentKey="bookapplicationApp.book.publisher">Publisher</Translate>
                </Label>
                <AvField id="book-publisher" type="text" name="publisher" />
              </AvGroup>
              <AvGroup>
                <Label id="priceLabel" for="book-price">
                  <Translate contentKey="bookapplicationApp.book.price">Price</Translate>
                </Label>
                <AvField id="book-price" type="string" className="form-control" name="price" />
              </AvGroup>
              <AvGroup>
                <Label for="book-shelf">
                  <Translate contentKey="bookapplicationApp.book.shelf">Shelf</Translate>
                </Label>
                <AvInput id="book-shelf" type="select" className="form-control" name="shelf.id">
                  <option value="" key="0" />
                  {shelves
                    ? shelves.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.number}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/book" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  shelves: storeState.shelf.entities,
  bookEntity: storeState.book.entity,
  loading: storeState.book.loading,
  updating: storeState.book.updating,
  updateSuccess: storeState.book.updateSuccess,
});

const mapDispatchToProps = {
  getShelves,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(BookUpdate);
