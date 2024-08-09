package kr.co.iei.friend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.iei.friend.dao.FriendDao;
import kr.co.iei.friend.dto.Friend;

@Service
public class FriendService {
    @Autowired
    private FriendDao friendDao;

	public List selectAllList() {
		List list = friendDao.selectAllList();
		return null;
	}

   
}



 