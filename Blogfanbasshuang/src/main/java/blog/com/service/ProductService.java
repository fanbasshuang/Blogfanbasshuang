package blog.com.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.model.dao.ProductDao;
import blog.com.model.entity.ProductEntity;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	
	//商品一览 的方法
	//longin adminID==null
	//adminDao.findAll();
	public List<ProductEntity> selectAll(Long adminId){
		if(adminId == null) {
			return null;
		}else {
			return productDao.findAll();
		}
	}
	
	//商品登记方法
	//商品名不存在的场合
	//登记
	//true
	//false
	public boolean createProduct(String category,
			String description, String image, String name) {
		if(productDao.findByProductName(name)==null) {
			productDao.save(new ProductEntity(category, description, image, name));
			return true;
		}else {
			return false;
		}
	
	}
	//编辑 情报 表示 的方法
	public ProductEntity getProductPost(Long productId) {
		if(productId == null) {
			return null;
		}else {
			return productDao.findByProductId(productId);
		}
	}
	//编辑 内容 保存 的方法
	//product_id == null false
	//!=null 更新  true
	//編集した内容を保存するメソッド
	public boolean editProduct(Long productId,String productCategory,
			String productDescription,String productImage,String productName) {
		if(productId == null) {
			return false;
		}else {
			//更新処理
			productDao.save(new ProductEntity(productId,productCategory,productDescription,productImage,productName));
			return true;
		}
	}
	//削除
	//productId == null  false
	//削除处理 true
	public boolean deleteProduct(Long productId) {
		if(productId == null) {
			return false;
		}else {
			productDao.deleteByProductId(productId);
			return true;
		}
	}
	
}