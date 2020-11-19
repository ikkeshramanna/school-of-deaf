package com.schoolofdeaf.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Content.
 */
@Entity
@Table(name = "content")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Content implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "content_name", nullable = false)
    private String contentName;

    @OneToMany(mappedBy = "content")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<ContentDetails> contentDetails = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "contents", allowSetters = true)
    private Category contentCategory;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContentName() {
        return contentName;
    }

    public Content contentName(String contentName) {
        this.contentName = contentName;
        return this;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public Set<ContentDetails> getContentDetails() {
        return contentDetails;
    }

    public Content contentDetails(Set<ContentDetails> contentDetails) {
        this.contentDetails = contentDetails;
        return this;
    }

    public Content addContentDetails(ContentDetails contentDetails) {
        this.contentDetails.add(contentDetails);
        contentDetails.setContent(this);
        return this;
    }

    public Content removeContentDetails(ContentDetails contentDetails) {
        this.contentDetails.remove(contentDetails);
        contentDetails.setContent(null);
        return this;
    }

    public void setContentDetails(Set<ContentDetails> contentDetails) {
        this.contentDetails = contentDetails;
    }

    public Category getContentCategory() {
        return contentCategory;
    }

    public Content contentCategory(Category category) {
        this.contentCategory = category;
        return this;
    }

    public void setContentCategory(Category category) {
        this.contentCategory = category;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Content)) {
            return false;
        }
        return id != null && id.equals(((Content) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Content{" +
            "id=" + getId() +
            ", contentName='" + getContentName() + "'" +
            "}";
    }
}
