package com.schoolofdeaf.app.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ContentDetails.
 */
@Entity
@Table(name = "content_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ContentDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "content_picture")
    private byte[] contentPicture;

    @Column(name = "content_picture_content_type")
    private String contentPictureContentType;

    @Lob
    @Column(name = "content_sign_picture")
    private byte[] contentSignPicture;

    @Column(name = "content_sign_picture_content_type")
    private String contentSignPictureContentType;

    @Column(name = "video")
    private String video;

    @ManyToOne
    @JsonIgnoreProperties(value = "contentDetails", allowSetters = true)
    private Content content;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getContentPicture() {
        return contentPicture;
    }

    public ContentDetails contentPicture(byte[] contentPicture) {
        this.contentPicture = contentPicture;
        return this;
    }

    public void setContentPicture(byte[] contentPicture) {
        this.contentPicture = contentPicture;
    }

    public String getContentPictureContentType() {
        return contentPictureContentType;
    }

    public ContentDetails contentPictureContentType(String contentPictureContentType) {
        this.contentPictureContentType = contentPictureContentType;
        return this;
    }

    public void setContentPictureContentType(String contentPictureContentType) {
        this.contentPictureContentType = contentPictureContentType;
    }

    public byte[] getContentSignPicture() {
        return contentSignPicture;
    }

    public ContentDetails contentSignPicture(byte[] contentSignPicture) {
        this.contentSignPicture = contentSignPicture;
        return this;
    }

    public void setContentSignPicture(byte[] contentSignPicture) {
        this.contentSignPicture = contentSignPicture;
    }

    public String getContentSignPictureContentType() {
        return contentSignPictureContentType;
    }

    public ContentDetails contentSignPictureContentType(String contentSignPictureContentType) {
        this.contentSignPictureContentType = contentSignPictureContentType;
        return this;
    }

    public void setContentSignPictureContentType(String contentSignPictureContentType) {
        this.contentSignPictureContentType = contentSignPictureContentType;
    }

    public String getVideo() {
        return video;
    }

    public ContentDetails video(String video) {
        this.video = video;
        return this;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Content getContent() {
        return content;
    }

    public ContentDetails content(Content content) {
        this.content = content;
        return this;
    }

    public void setContent(Content content) {
        this.content = content;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContentDetails)) {
            return false;
        }
        return id != null && id.equals(((ContentDetails) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContentDetails{" +
            "id=" + getId() +
            ", contentPicture='" + getContentPicture() + "'" +
            ", contentPictureContentType='" + getContentPictureContentType() + "'" +
            ", contentSignPicture='" + getContentSignPicture() + "'" +
            ", contentSignPictureContentType='" + getContentSignPictureContentType() + "'" +
            ", video='" + getVideo() + "'" +
            "}";
    }
}
