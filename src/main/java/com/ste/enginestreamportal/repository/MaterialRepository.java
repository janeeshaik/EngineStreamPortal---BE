package com.ste.enginestreamportal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ste.enginestreamportal.model.Material;
import com.ste.enginestreamportal.util.DatabaseQueries;

public interface MaterialRepository extends JpaRepository<Material, Long>,PagingAndSortingRepository<Material, Long>,JpaSpecificationExecutor<Material>{

	final int PAGE_SIZE = 20;
	
	@Query("select m from Material m inner join m.plant p on p.id = m.id")
	public List<Material> getMaterialDataUsingQyery();
	
	@Query("select m from Material m where :data = '%null%' or ("
			+ "m.materialNumber like :data or "
			+ "m.materialDescription like :data or "
			+ "m.lastPOUnitPrice like :data or "
			+ "m.last1stYearIssueQuantity like :data or "
			+ "m.last2ndYearIssueQuantity like :data or "
			+ "m.last3rdYearIssueQuantity like :data )"
			)
	public List<Material> searchMaterial(String data);
	
	@Query("select m from Material m where m.id = :id")
	public Material findMaterialById(Long id);

	/*@Query(value = "select a.*, b.* from Material a inner join (select *, SUM(Quantity) qty from Batch group by Material) b on a.id = b.Material",
			nativeQuery = true)
	public List<Object[]> findAllMaterials();*/

	/*
	select a.*, b.* from Material a
inner join
(select p.*,q.qty from Batch p
inner join
(select Material, SUM(Quantity) qty from Batch group by Material) q
on p.Material = q.Material) b
on a.id = b.Material
	 */

	@Query(value = DatabaseQueries.MATERIAL_BATCH_QUERY,
			nativeQuery = true)
	public List<Object[]> findAllMaterials();
}