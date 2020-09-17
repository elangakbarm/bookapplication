package com.valensi.bookapp.web.rest;

import com.valensi.bookapp.domain.Shelf;
import com.valensi.bookapp.repository.ShelfRepository;
import com.valensi.bookapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.valensi.bookapp.domain.Shelf}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ShelfResource {

    private final Logger log = LoggerFactory.getLogger(ShelfResource.class);

    private static final String ENTITY_NAME = "shelf";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ShelfRepository shelfRepository;

    public ShelfResource(ShelfRepository shelfRepository) {
        this.shelfRepository = shelfRepository;
    }

    /**
     * {@code POST  /shelves} : Create a new shelf.
     *
     * @param shelf the shelf to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new shelf, or with status {@code 400 (Bad Request)} if the shelf has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/shelves")
    public ResponseEntity<Shelf> createShelf(@RequestBody Shelf shelf) throws URISyntaxException {
        log.debug("REST request to save Shelf : {}", shelf);
        if (shelf.getId() != null) {
            throw new BadRequestAlertException("A new shelf cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Shelf result = shelfRepository.save(shelf);
        return ResponseEntity.created(new URI("/api/shelves/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /shelves} : Updates an existing shelf.
     *
     * @param shelf the shelf to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated shelf,
     * or with status {@code 400 (Bad Request)} if the shelf is not valid,
     * or with status {@code 500 (Internal Server Error)} if the shelf couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/shelves")
    public ResponseEntity<Shelf> updateShelf(@RequestBody Shelf shelf) throws URISyntaxException {
        log.debug("REST request to update Shelf : {}", shelf);
        if (shelf.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Shelf result = shelfRepository.save(shelf);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, shelf.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /shelves} : get all the shelves.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of shelves in body.
     */
    @GetMapping("/shelves")
    public ResponseEntity<List<Shelf>> getAllShelves(Pageable pageable) {
        log.debug("REST request to get a page of Shelves");
        Page<Shelf> page = shelfRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /shelves/:id} : get the "id" shelf.
     *
     * @param id the id of the shelf to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the shelf, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/shelves/{id}")
    public ResponseEntity<Shelf> getShelf(@PathVariable Long id) {
        log.debug("REST request to get Shelf : {}", id);
        Optional<Shelf> shelf = shelfRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(shelf);
    }

    /**
     * {@code DELETE  /shelves/:id} : delete the "id" shelf.
     *
     * @param id the id of the shelf to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/shelves/{id}")
    public ResponseEntity<Void> deleteShelf(@PathVariable Long id) {
        log.debug("REST request to delete Shelf : {}", id);
        shelfRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
