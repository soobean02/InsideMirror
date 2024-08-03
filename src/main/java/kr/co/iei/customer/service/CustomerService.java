package kr.co.iei.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.customer.dao.CustomerDao;

@Service
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;
}
