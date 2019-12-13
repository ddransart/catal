package com.sid.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sid.dao.ProduitRepository;
import com.sid.entities.Produit;

@Controller
public class ProduitController {

	@Autowired
	private ProduitRepository produitRepository;

	// La page par défaut
	@GetMapping("/")
	public String defaultHomePage() {
		return "redirect:/user/index";
	}

	@GetMapping("/user/index")
	public String chercher(Model produitModel, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "motCle", defaultValue = "") String mc) {

		// List<Produit> produits=produitRepository.findAll(); // on recupère le
		// traitement
		// Page<Produit> pageProduits=produitRepository.findAll(PageRequest.of(page,
		// 5));

		Page<Produit> pageProduits = produitRepository.findByDesignationContains(mc, PageRequest.of(page, 5));
		produitModel.addAttribute("listProduits", pageProduits.getContent()); // on stocke dans le modele la liste des
																				// produits
		produitModel.addAttribute("pages", new int[pageProduits.getTotalPages()]);
		produitModel.addAttribute("currentPage", page);
		produitModel.addAttribute("motCle", mc);
		return "produits"; // on retourne la vue et cette vue est la page produits.html
	}

	// Supprimer un produit
	@GetMapping("/admin/delete")
	public String delete(Long id, int page, String motCle) {
		produitRepository.deleteById(id);
		return "redirect:/user/index?page=" + page + "&motCle=" + motCle;
	}

	// Accéder au formulaire d'ajout du produit
	@GetMapping("/admin/saveFormProduit")
	public String form(Model produitModel) {
		produitModel.addAttribute("prod", new Produit());
		return "formProduit";
	}

	// Accéder au formulaire d'ajout du produit
	@GetMapping("/admin/editFormProduit")
	public String form(Model produitModel, Long id) {
		Produit p = produitRepository.findById(id).get();
		produitModel.addAttribute("prodt", p);
		return "editFormProduit";
	}

	// Enregistrer le produit via le bouton save, après encodage du produit
	@PostMapping("/admin/saveProduit")
	public String saveProduit(Produit produit) {
		// public String saveProduit(Model produitModel, @Valid Produit p, BindingResult
		// bindingResult) {
		/*
		 * if(bindingResult.hasErrors()) { //produitModel.addAttribute("p", new
		 * Produit()); return "formProduit"; // on revient vers le formulaire sans
		 * enregistrer si les erreurs sont détectées par spring security }
		 */
		produitRepository.save(produit);
		// return "formProduit"; // pour rester sur le formulaire d'enregistrement après
		// avoir cliqué sur le bouton "envoyer"
		return "redirect:/user/index"; // retourner à l'accueil
	}

	// La page par défaut
	@GetMapping("/403")
	public String notAuthorized() {
		return "403";
	}

	// La page par défaut
	@GetMapping("/login")
	public String login() {
		return "loginForm";
	}
}
