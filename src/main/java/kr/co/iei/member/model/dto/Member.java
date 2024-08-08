package kr.co.iei.member.model.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Member {
	private int memberNo;			//회원 고유 번호
	private String memberId;		//회원 아이디 (중복 X)
	private String memberPw;		//회원 비밀번호
	private String memberNickName;	//회원 별명 (중복 X)
	private String memberName;		//회원 본명
	private String memberGender;	//회원 성별 '남', '여'
	private String memberPhone;		//회원 전화번호
	private String memberAddr;		//회원 주소
	private int memberLevel;		//회원 등급 (관리자 1, 정회원 2)
	private Date enrollDate;		//회원 가입일
	
	private String profileMsg;		//회원 프로필 메세지
	private String profilePhoto;	//회원 프로필 사진
	private int totalCount;			//회원 홈피 토탈 조회수
	private int acorns;				//회원 보유 도토리
}
