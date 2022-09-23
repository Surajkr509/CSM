package com.nixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nixi.model.CMSPhotoGallery;

@Repository
public interface CMSPhotoGalleryRepository extends JpaRepository<CMSPhotoGallery, Long> {

}
