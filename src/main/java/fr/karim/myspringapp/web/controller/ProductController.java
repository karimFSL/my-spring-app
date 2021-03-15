package fr.karim.myspringapp.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import fr.karim.myspringapp.dao.ProductDao;
import fr.karim.myspringapp.model.Product;
import fr.karim.myspringapp.web.exceptions.ProduitIntrouvableException;

/**
 * @author Admin
 *
 */
@RestController
public class ProductController {

	@Autowired
	private ProductDao productDao;

	@RequestMapping(value = "/Produits", method = RequestMethod.GET)
	public List<Product> listeProduits() {
		return productDao.findAll();
	}

	// Récupérer un produit par son Id
	@GetMapping(value = "/Produits/{id}")
	public Product afficherUnProduit(@PathVariable int id) {
		
		Product produit = productDao.findById(id);
		
		if(produit==null) 
			throw new ProduitIntrouvableException("Le produit avec l'id " + id + " est INTROUVABLE. Écran Bleu si je pouvais.");
		
		
		return produit;
	}

	@GetMapping(value = "test/produits/{prixLimit}")
	public List<Product> testeDeRequetes(@PathVariable int prixLimit) {
		return productDao.findByPrixGreaterThan(prixLimit);
	}

	@GetMapping(value = "test/produits2/{recherche}")
	public List<Product> testeDeRequetes(@PathVariable String recherche) {
		return productDao.findByNomLike("%" + recherche + "%");
	}

	// ajouter un produit
	@PostMapping(value = "/Produits")
	public ResponseEntity<Object> ajouterProduit(@RequestBody Product product) {

		System.out.println("tsssssssssssestt" + product);
		Product productAdded = productDao.save(product);

		if (productAdded == null)
			return ResponseEntity.noContent().build();

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(productAdded.getId()).toUri();

		return ResponseEntity.created(location).build();
	}

}
