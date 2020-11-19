package com.schoolofdeaf.app.web.rest;

import com.schoolofdeaf.app.SchoolOfDeafApp;
import com.schoolofdeaf.app.domain.ContentDetails;
import com.schoolofdeaf.app.repository.ContentDetailsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ContentDetailsResource} REST controller.
 */
@SpringBootTest(classes = SchoolOfDeafApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class ContentDetailsResourceIT {

    private static final byte[] DEFAULT_CONTENT_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_PICTURE_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CONTENT_SIGN_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CONTENT_SIGN_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_CONTENT_SIGN_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CONTENT_SIGN_PICTURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_VIDEO = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO = "BBBBBBBBBB";

    @Autowired
    private ContentDetailsRepository contentDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContentDetailsMockMvc;

    private ContentDetails contentDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentDetails createEntity(EntityManager em) {
        ContentDetails contentDetails = new ContentDetails()
            .contentPicture(DEFAULT_CONTENT_PICTURE)
            .contentPictureContentType(DEFAULT_CONTENT_PICTURE_CONTENT_TYPE)
            .contentSignPicture(DEFAULT_CONTENT_SIGN_PICTURE)
            .contentSignPictureContentType(DEFAULT_CONTENT_SIGN_PICTURE_CONTENT_TYPE)
            .video(DEFAULT_VIDEO);
        return contentDetails;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentDetails createUpdatedEntity(EntityManager em) {
        ContentDetails contentDetails = new ContentDetails()
            .contentPicture(UPDATED_CONTENT_PICTURE)
            .contentPictureContentType(UPDATED_CONTENT_PICTURE_CONTENT_TYPE)
            .contentSignPicture(UPDATED_CONTENT_SIGN_PICTURE)
            .contentSignPictureContentType(UPDATED_CONTENT_SIGN_PICTURE_CONTENT_TYPE)
            .video(UPDATED_VIDEO);
        return contentDetails;
    }

    @BeforeEach
    public void initTest() {
        contentDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentDetails() throws Exception {
        int databaseSizeBeforeCreate = contentDetailsRepository.findAll().size();
        // Create the ContentDetails
        restContentDetailsMockMvc.perform(post("/api/content-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentDetails)))
            .andExpect(status().isCreated());

        // Validate the ContentDetails in the database
        List<ContentDetails> contentDetailsList = contentDetailsRepository.findAll();
        assertThat(contentDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        ContentDetails testContentDetails = contentDetailsList.get(contentDetailsList.size() - 1);
        assertThat(testContentDetails.getContentPicture()).isEqualTo(DEFAULT_CONTENT_PICTURE);
        assertThat(testContentDetails.getContentPictureContentType()).isEqualTo(DEFAULT_CONTENT_PICTURE_CONTENT_TYPE);
        assertThat(testContentDetails.getContentSignPicture()).isEqualTo(DEFAULT_CONTENT_SIGN_PICTURE);
        assertThat(testContentDetails.getContentSignPictureContentType()).isEqualTo(DEFAULT_CONTENT_SIGN_PICTURE_CONTENT_TYPE);
        assertThat(testContentDetails.getVideo()).isEqualTo(DEFAULT_VIDEO);
    }

    @Test
    @Transactional
    public void createContentDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentDetailsRepository.findAll().size();

        // Create the ContentDetails with an existing ID
        contentDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentDetailsMockMvc.perform(post("/api/content-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ContentDetails in the database
        List<ContentDetails> contentDetailsList = contentDetailsRepository.findAll();
        assertThat(contentDetailsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllContentDetails() throws Exception {
        // Initialize the database
        contentDetailsRepository.saveAndFlush(contentDetails);

        // Get all the contentDetailsList
        restContentDetailsMockMvc.perform(get("/api/content-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentPictureContentType").value(hasItem(DEFAULT_CONTENT_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contentPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT_PICTURE))))
            .andExpect(jsonPath("$.[*].contentSignPictureContentType").value(hasItem(DEFAULT_CONTENT_SIGN_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].contentSignPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_CONTENT_SIGN_PICTURE))))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO)));
    }
    
    @Test
    @Transactional
    public void getContentDetails() throws Exception {
        // Initialize the database
        contentDetailsRepository.saveAndFlush(contentDetails);

        // Get the contentDetails
        restContentDetailsMockMvc.perform(get("/api/content-details/{id}", contentDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contentDetails.getId().intValue()))
            .andExpect(jsonPath("$.contentPictureContentType").value(DEFAULT_CONTENT_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.contentPicture").value(Base64Utils.encodeToString(DEFAULT_CONTENT_PICTURE)))
            .andExpect(jsonPath("$.contentSignPictureContentType").value(DEFAULT_CONTENT_SIGN_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.contentSignPicture").value(Base64Utils.encodeToString(DEFAULT_CONTENT_SIGN_PICTURE)))
            .andExpect(jsonPath("$.video").value(DEFAULT_VIDEO));
    }
    @Test
    @Transactional
    public void getNonExistingContentDetails() throws Exception {
        // Get the contentDetails
        restContentDetailsMockMvc.perform(get("/api/content-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentDetails() throws Exception {
        // Initialize the database
        contentDetailsRepository.saveAndFlush(contentDetails);

        int databaseSizeBeforeUpdate = contentDetailsRepository.findAll().size();

        // Update the contentDetails
        ContentDetails updatedContentDetails = contentDetailsRepository.findById(contentDetails.getId()).get();
        // Disconnect from session so that the updates on updatedContentDetails are not directly saved in db
        em.detach(updatedContentDetails);
        updatedContentDetails
            .contentPicture(UPDATED_CONTENT_PICTURE)
            .contentPictureContentType(UPDATED_CONTENT_PICTURE_CONTENT_TYPE)
            .contentSignPicture(UPDATED_CONTENT_SIGN_PICTURE)
            .contentSignPictureContentType(UPDATED_CONTENT_SIGN_PICTURE_CONTENT_TYPE)
            .video(UPDATED_VIDEO);

        restContentDetailsMockMvc.perform(put("/api/content-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedContentDetails)))
            .andExpect(status().isOk());

        // Validate the ContentDetails in the database
        List<ContentDetails> contentDetailsList = contentDetailsRepository.findAll();
        assertThat(contentDetailsList).hasSize(databaseSizeBeforeUpdate);
        ContentDetails testContentDetails = contentDetailsList.get(contentDetailsList.size() - 1);
        assertThat(testContentDetails.getContentPicture()).isEqualTo(UPDATED_CONTENT_PICTURE);
        assertThat(testContentDetails.getContentPictureContentType()).isEqualTo(UPDATED_CONTENT_PICTURE_CONTENT_TYPE);
        assertThat(testContentDetails.getContentSignPicture()).isEqualTo(UPDATED_CONTENT_SIGN_PICTURE);
        assertThat(testContentDetails.getContentSignPictureContentType()).isEqualTo(UPDATED_CONTENT_SIGN_PICTURE_CONTENT_TYPE);
        assertThat(testContentDetails.getVideo()).isEqualTo(UPDATED_VIDEO);
    }

    @Test
    @Transactional
    public void updateNonExistingContentDetails() throws Exception {
        int databaseSizeBeforeUpdate = contentDetailsRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContentDetailsMockMvc.perform(put("/api/content-details")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(contentDetails)))
            .andExpect(status().isBadRequest());

        // Validate the ContentDetails in the database
        List<ContentDetails> contentDetailsList = contentDetailsRepository.findAll();
        assertThat(contentDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteContentDetails() throws Exception {
        // Initialize the database
        contentDetailsRepository.saveAndFlush(contentDetails);

        int databaseSizeBeforeDelete = contentDetailsRepository.findAll().size();

        // Delete the contentDetails
        restContentDetailsMockMvc.perform(delete("/api/content-details/{id}", contentDetails.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ContentDetails> contentDetailsList = contentDetailsRepository.findAll();
        assertThat(contentDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
