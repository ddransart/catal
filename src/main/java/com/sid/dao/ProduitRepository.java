package com.sid.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.entities.Produit;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
	
	// Recherche des produits par dont la désignation contient le mot-clé mc
	// Doit retourner une page (donc la liste des produits par page)
	public Page<Produit> findByDesignationContains(String mc, Pageable p);
	
	

}
