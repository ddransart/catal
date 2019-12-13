package com.sid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sid.dao.ProduitRepository;
import com.sid.entities.Produit;

@SpringBootApplication
public class CatalSpringMvcApplication {
	//public class CatalSpringMvcApplication implements CommandLineRunner {

	@Autowired
	private ProduitRepository produitRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CatalSpringMvcApplication.class, args);
	}

	/*
	 * @Override public void run(String... args) throws Exception {
	 * produitRepository.save(new Produit(null, "PC 5648", 6000, 12));
	 * produitRepository.save(new Produit(null, "Imprimante HP", 1200, 10));
	 * produitRepository.save(new Produit(null, "PC Compaq", 9000, 1));
	 * produitRepository.save(new Produit(null, "Scanner 5660", 1000, 10));
	 * produitRepository.findAll().forEach(p-> {
	 * System.out.println(p.getDesignation()); });
	 * 
	 * }
	 */

}
