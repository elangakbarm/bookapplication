package com.valensi.bookapp.web.rest;

import com.valensi.bookapp.BookapplicationApp;
import com.valensi.bookapp.domain.Shelf;
import com.valensi.bookapp.repository.ShelfRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ShelfResource} REST controller.
 */
@SpringBootTest(classes = BookapplicationApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ShelfResourceIT {

    private static final Integer DEFAULT_NUMBER = 1;
    private static final Integer UPDATED_NUMBER = 2;

    private static final String DEFAULT_DESC = "AAAAAAAAAA";
    private static final String UPDATED_DESC = "BBBBBBBBBB";

    @Autowired
    private ShelfRepository shelfRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restShelfMockMvc;

    private Shelf shelf;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shelf createEntity(EntityManager em) {
        Shelf shelf = new Shelf()
            .number(DEFAULT_NUMBER)
            .desc(DEFAULT_DESC);
        return shelf;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Shelf createUpdatedEntity(EntityManager em) {
        Shelf shelf = new Shelf()
            .number(UPDATED_NUMBER)
            .desc(UPDATED_DESC);
        return shelf;
    }

    @BeforeEach
    public void initTest() {
        shelf = createEntity(em);
    }

    @Test
    @Transactional
    public void createShelf() throws Exception {
        int databaseSizeBeforeCreate = shelfRepository.findAll().size();
        // Create the Shelf
        restShelfMockMvc.perform(post("/api/shelves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelf)))
            .andExpect(status().isCreated());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeCreate + 1);
        Shelf testShelf = shelfList.get(shelfList.size() - 1);
        assertThat(testShelf.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testShelf.getDesc()).isEqualTo(DEFAULT_DESC);
    }

    @Test
    @Transactional
    public void createShelfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shelfRepository.findAll().size();

        // Create the Shelf with an existing ID
        shelf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShelfMockMvc.perform(post("/api/shelves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelf)))
            .andExpect(status().isBadRequest());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShelves() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        // Get all the shelfList
        restShelfMockMvc.perform(get("/api/shelves?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shelf.getId().intValue())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].desc").value(hasItem(DEFAULT_DESC)));
    }
    
    @Test
    @Transactional
    public void getShelf() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        // Get the shelf
        restShelfMockMvc.perform(get("/api/shelves/{id}", shelf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(shelf.getId().intValue()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.desc").value(DEFAULT_DESC));
    }
    @Test
    @Transactional
    public void getNonExistingShelf() throws Exception {
        // Get the shelf
        restShelfMockMvc.perform(get("/api/shelves/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShelf() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();

        // Update the shelf
        Shelf updatedShelf = shelfRepository.findById(shelf.getId()).get();
        // Disconnect from session so that the updates on updatedShelf are not directly saved in db
        em.detach(updatedShelf);
        updatedShelf
            .number(UPDATED_NUMBER)
            .desc(UPDATED_DESC);

        restShelfMockMvc.perform(put("/api/shelves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedShelf)))
            .andExpect(status().isOk());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
        Shelf testShelf = shelfList.get(shelfList.size() - 1);
        assertThat(testShelf.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testShelf.getDesc()).isEqualTo(UPDATED_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingShelf() throws Exception {
        int databaseSizeBeforeUpdate = shelfRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShelfMockMvc.perform(put("/api/shelves")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(shelf)))
            .andExpect(status().isBadRequest());

        // Validate the Shelf in the database
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShelf() throws Exception {
        // Initialize the database
        shelfRepository.saveAndFlush(shelf);

        int databaseSizeBeforeDelete = shelfRepository.findAll().size();

        // Delete the shelf
        restShelfMockMvc.perform(delete("/api/shelves/{id}", shelf.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Shelf> shelfList = shelfRepository.findAll();
        assertThat(shelfList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
