package com.schoolofdeaf.app.repository;

import com.schoolofdeaf.app.domain.ContentDetails;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the ContentDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentDetailsRepository extends JpaRepository<ContentDetails, Long> {
}
