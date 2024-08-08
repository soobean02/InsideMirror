package kr.co.iei.friend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Friend {
	private int friendNo;
	private int memberNo;
	private int friendMemberNo;
}
