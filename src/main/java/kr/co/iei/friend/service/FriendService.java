package kr.co.iei.friend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.iei.friend.dao.FriendDao;
import kr.co.iei.friend.dto.Friend;


@Service
public class FriendService {
    @Autowired
    private FriendDao friendDao;

    public List<Friend> selectAllList(Friend f) {
        return friendDao.selectAllList(f);
    }
    
    @Transactional
    public int friendCancel(Friend f) {
        return friendDao.friendCancel(f);
    }

    public int friendRequest(Friend f) {
        return friendDao.friendRequest(f);
    }

    public List<Friend> selectList(Friend f, String keyword) {
        return friendDao.selectList(f, keyword);
    }
}