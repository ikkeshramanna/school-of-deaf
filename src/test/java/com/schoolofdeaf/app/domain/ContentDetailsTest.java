package com.schoolofdeaf.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.schoolofdeaf.app.web.rest.TestUtil;

public class ContentDetailsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentDetails.class);
        ContentDetails contentDetails1 = new ContentDetails();
        contentDetails1.setId(1L);
        ContentDetails contentDetails2 = new ContentDetails();
        contentDetails2.setId(contentDetails1.getId());
        assertThat(contentDetails1).isEqualTo(contentDetails2);
        contentDetails2.setId(2L);
        assertThat(contentDetails1).isNotEqualTo(contentDetails2);
        contentDetails1.setId(null);
        assertThat(contentDetails1).isNotEqualTo(contentDetails2);
    }
}
