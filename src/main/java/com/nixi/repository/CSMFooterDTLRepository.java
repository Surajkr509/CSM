package com.nixi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nixi.model.CSMFooterDTL;

@Repository
public interface CSMFooterDTLRepository extends JpaRepository<CSMFooterDTL,Long> {

}
