package com.valensi.bookapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Shelf.
 */
@Entity
@Table(name = "shelf")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Shelf implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "number")
    private Integer number;

    @Column(name = "jhi_desc")
    private String desc;

    @OneToMany(mappedBy = "shelf")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Book> books = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public Shelf number(Integer number) {
        this.number = number;
        return this;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDesc() {
        return desc;
    }

    public Shelf desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public Shelf books(Set<Book> books) {
        this.books = books;
        return this;
    }

    public Shelf addBook(Book book) {
        this.books.add(book);
        book.setShelf(this);
        return this;
    }

    public Shelf removeBook(Book book) {
        this.books.remove(book);
        book.setShelf(null);
        return this;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Shelf)) {
            return false;
        }
        return id != null && id.equals(((Shelf) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Shelf{" +
            "id=" + getId() +
            ", number=" + getNumber() +
            ", desc='" + getDesc() + "'" +
            "}";
    }
}
