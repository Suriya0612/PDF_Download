package com.pdf.download.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.pdf.download.Entity.Filedetails;

public interface FilesRepository extends JpaRepository<Filedetails,Long> {

	Optional<Filedetails> findByFilename(String filename);

}
