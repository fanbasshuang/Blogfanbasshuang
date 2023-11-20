package blog.com.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import blog.com.model.entity.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Long> {

	//保存处理  save
		UserEntity save(UserEntity adminentity);
		//1行代码取得
		//SELECT * FROM admin WHERE admin_email=?
		UserEntity findByEmail(String email);
		//SELECT * FROM admin WHERE admin_email=?And password = ?
		UserEntity findByEmailAndPassword(String email,String password);
}
