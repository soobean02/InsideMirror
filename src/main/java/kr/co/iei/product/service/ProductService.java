package kr.co.iei.product.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.product.dao.ProductDao;
import kr.co.iei.product.dto.SellProduct;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Transactional
	public int updateAcorns(int acorns) {
		/*멤버 테이블에 도토리 넣어주기*/
		int result1 = productDao.updateAcorns(acorns);
		
		/*도토리 구매 이력 테이블에 도토리 정보 넣어주기*/
		int result2 = productDao.insertAcorns(acorns);
		
		if(result1>0 && result2>0) { // 도토리 insert 성공 1 반환
			return 1;
		}else { // 도토리 insert 실패 0 반환
			return 0;
		}
	}
	
	public List selectProductPhoto() {
		List product = productDao.selectProductPhoto();
		return product;
	}
}
