package com.ste.enginestreamportal.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ste.enginestreamportal.model.ImageUploadEntity;


@Repository
public interface FileUploadRepo extends JpaRepository<ImageUploadEntity, Integer>{

	@Query("from ImageUploadEntity order by position asc")
	List<ImageUploadEntity> findByPositionAsc();

}
