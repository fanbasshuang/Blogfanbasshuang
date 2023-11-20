package blog.com.controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import blog.com.model.entity.ProductEntity;
import blog.com.model.entity.UserEntity;
import blog.com.service.ProductService;
import jakarta.servlet.http.HttpSession;
@Controller
public class ProductController {
	@Autowired
	private ProductService productService;
	
	@Autowired
	private HttpSession session;
	
	//商品一览
	@GetMapping("/product/list")
	public String getProductList(Model model) {
		//セッションからログインしている人の情報を取得
		UserEntity admin = (UserEntity) session.getAttribute("admin");
		if(admin == null) {
			return "redirect:/login";
		}else {
			List<ProductEntity>productList = productService.selectAll(admin.getId());
				model.addAttribute("productList", productList);
				model.addAttribute("adminName", admin.getUsername());
				return "product_list.html";
			}
		}
	
	//商品登记页面表示
	@GetMapping("/product/register")
	public String getProductRegisterPage(Model model) {
		UserEntity admin = (UserEntity) session.getAttribute("admin");
		if(admin == null) {
			return "redirect:/login";
		}else {
				model.addAttribute("adminName", admin.getUsername());
				return "product_register.html";
			}
	}
	
	//商品登记的方法
	@PostMapping("/product/register/process")
	public String productRegister(@RequestParam String productName,
			@RequestParam String productCategory,
			@RequestParam MultipartFile productImage,
			@RequestParam String productDescription) {
				
		UserEntity admin = (UserEntity) session.getAttribute("admin");
		if(admin == null) {
			return "redirect:/login";
		}else {
			//文件名 取得
			/**現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォーマットを指定している。
			 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォーマットされた文字列を取得している。
			 * その後、blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入**/
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date()) + productImage.getOriginalFilename();
			try {
				Files.copy(productImage.getInputStream(),Path.of("src/main/resources/static/product-img/" + fileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(productService.createProduct(productCategory, productDescription, fileName, productName)) {
				return "redirect:/product/list";
			}else {
				return "redirect:/product/register";
			}
			
		}
		
	}
	
	//编辑页面取得
	//多次编辑页面 //非创建！ 必须已有blogId
	@GetMapping("/product/edit/{productId}")
	public String getProductEditPage(@PathVariable Long productId, Model model) {
		UserEntity admin = (UserEntity) session.getAttribute("admin");
		if(admin == null) {
			return "redirect:/login";
		}else {
				//model.addAttribute("adminName", admin.getAdminName());
				ProductEntity productList = productService.getProductPost(productId);
				if(productList == null) {
					return "redirect:/product/list";
				}else {
					model.addAttribute("productList", productList);
				}
				return "product_edit.html";
			}
	}
	
	//更新 方法
	@PostMapping("/product/edit/process")
	public String editProcess(@RequestParam Long productId,
							  @RequestParam String productName,
							  @RequestParam String productCategory,
							  @RequestParam MultipartFile productImage,
							  @RequestParam String productDescription) {
		UserEntity admin = (UserEntity) session.getAttribute("admin");
		if(admin == null) {
			return "redirect:/login";
		}else {
			//文件名 取得
			/**現在の日時情報を元に、ファイル名を作成しています。SimpleDateFormatクラスを使用して、日時のフォーマットを指定している。
			 * 具体的には、"yyyy-MM-dd-HH-mm-ss-"の形式でフォーマットされた文字列を取得している。
			 * その後、blogImageオブジェクトから元のファイル名を取得し、フォーマットされた日時文字列と連結して、fileName変数に代入**/
			String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-").format(new Date())
					+ productImage.getOriginalFilename();
			try {
				Files.copy(productImage.getInputStream(), Path.of("src/main/resources/static/product-img/" + fileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(productService.editProduct(productId, productCategory, productDescription, fileName, productName)) {
				return "redirect:/product/list";
			}else {
				return "redirect:/product/edit/" + productId;
			}
		
		}
		
	}
	
	//削除処理
	@PostMapping("/product/delete")
	public String delete(@RequestParam Long productId) {
		UserEntity admin = (UserEntity) session.getAttribute("admin");
		if (admin == null) {
			return "redirect:/login";
		}else {
			if(productService.deleteProduct(productId)) {
				return "redirect:/product/list";
			}else {
				return "redirect:/product/edit/"+productId;
			}	
		}
			
	}
	
	
}