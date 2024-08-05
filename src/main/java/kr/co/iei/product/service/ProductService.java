package kr.co.iei.product.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.product.dao.ProductDao;

@Service
public class ProductService {
	@Autowired
	private ProductDao productDao;
	
	@Transactional
	public int updateAcorns(int acorns) {
		int result = productDao.updateAcorns(acorns);
		return result;
	}
}
