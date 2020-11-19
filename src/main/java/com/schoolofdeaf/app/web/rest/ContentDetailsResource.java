package com.schoolofdeaf.app.web.rest;

import com.schoolofdeaf.app.domain.ContentDetails;
import com.schoolofdeaf.app.repository.ContentDetailsRepository;
import com.schoolofdeaf.app.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.schoolofdeaf.app.domain.ContentDetails}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ContentDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ContentDetailsResource.class);

    private static final String ENTITY_NAME = "contentDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentDetailsRepository contentDetailsRepository;

    public ContentDetailsResource(ContentDetailsRepository contentDetailsRepository) {
        this.contentDetailsRepository = contentDetailsRepository;
    }

    /**
     * {@code POST  /content-details} : Create a new contentDetails.
     *
     * @param contentDetails the contentDetails to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentDetails, or with status {@code 400 (Bad Request)} if the contentDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/content-details")
    public ResponseEntity<ContentDetails> createContentDetails(@RequestBody ContentDetails contentDetails) throws URISyntaxException {
        log.debug("REST request to save ContentDetails : {}", contentDetails);
        if (contentDetails.getId() != null) {
            throw new BadRequestAlertException("A new contentDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentDetails result = contentDetailsRepository.save(contentDetails);
        return ResponseEntity.created(new URI("/api/content-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /content-details} : Updates an existing contentDetails.
     *
     * @param contentDetails the contentDetails to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentDetails,
     * or with status {@code 400 (Bad Request)} if the contentDetails is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentDetails couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/content-details")
    public ResponseEntity<ContentDetails> updateContentDetails(@RequestBody ContentDetails contentDetails) throws URISyntaxException {
        log.debug("REST request to update ContentDetails : {}", contentDetails);
        if (contentDetails.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentDetails result = contentDetailsRepository.save(contentDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentDetails.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /content-details} : get all the contentDetails.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contentDetails in body.
     */
    @GetMapping("/content-details")
    public List<ContentDetails> getAllContentDetails() {
        log.debug("REST request to get all ContentDetails");
        return contentDetailsRepository.findAll();
    }

    /**
     * {@code GET  /content-details/:id} : get the "id" contentDetails.
     *
     * @param id the id of the contentDetails to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentDetails, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/content-details/{id}")
    public ResponseEntity<ContentDetails> getContentDetails(@PathVariable Long id) {
        log.debug("REST request to get ContentDetails : {}", id);
        Optional<ContentDetails> contentDetails = contentDetailsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(contentDetails);
    }

    /**
     * {@code DELETE  /content-details/:id} : delete the "id" contentDetails.
     *
     * @param id the id of the contentDetails to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/content-details/{id}")
    public ResponseEntity<Void> deleteContentDetails(@PathVariable Long id) {
        log.debug("REST request to delete ContentDetails : {}", id);
        contentDetailsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
