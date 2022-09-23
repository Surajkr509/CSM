package com.nixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nixi.model.CMSFeedbackNComplaints;

@Repository
public interface CMSFbNCpRepository extends JpaRepository<CMSFeedbackNComplaints, Long> {

}
