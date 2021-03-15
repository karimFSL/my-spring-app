package fr.karim.myspringapp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.karim.myspringapp.model.Product;

public interface ProductDao extends JpaRepository<Product, Integer> {
	public List<Product> findAll();

	public Product findById(int id);
	
	List<Product> findByPrixGreaterThan(int prixLimit);
	
	List<Product> findByNomLike(String recherche);

	public Product save(Product product);
}