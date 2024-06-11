package com.pdf.download.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pdf.download.Entity.FolderDetails;

public interface FolderDetailsRepository extends JpaRepository<FolderDetails,Long> {

}
