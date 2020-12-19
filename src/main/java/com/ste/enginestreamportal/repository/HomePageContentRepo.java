package com.ste.enginestreamportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ste.enginestreamportal.model.HomePageContentEntity;


@Repository
public interface HomePageContentRepo extends JpaRepository<HomePageContentEntity, Long>{

}
