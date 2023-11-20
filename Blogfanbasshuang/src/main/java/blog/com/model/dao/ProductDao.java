package blog.com.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.model.entity.ProductEntity;
import jakarta.transaction.Transactional;
@Repository
@Transactional
public interface ProductDao extends JpaRepository<ProductEntity, Long> {
	ProductEntity save(ProductEntity productEntity);
	List<ProductEntity>findAll();
	ProductEntity findByProductId(Long productId);
	ProductEntity findByProductName(String productName);
	//消除method productId
	int deleteByProductId(Long productId);

}
