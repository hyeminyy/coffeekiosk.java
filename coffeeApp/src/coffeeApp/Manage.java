package coffeeApp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Manage { 
	

	Scanner scanner = new Scanner(System.in);
	
	private ArrayList<Member> memberList;
	
	//Manage클래스의 생성자 
	public Manage() {
		memberList = new ArrayList<Member>();
	}
	
	//회원정보를 입력받아 ArrayList인 memberList에 추가
	public void addMember(Member member) {
		memberList.add(member);
	}
	//
//회원 이름을 입력받아 파일에 저장된 회원정보를 검색해서 해당 회원 객체를 반환
	public Member findMember(String name) {
	    try (BufferedReader br = new BufferedReader(new FileReader("C:\\CafeAPP\\cafemember.txt"))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] memberInfo = line.split("\t"); // 탭 문자로 분리
	            if (memberInfo.length >= 2) { // 두 번째 원소가 있는 경우에만 처리
	                String name1 = memberInfo[0];
	                String phoneNumber = memberInfo[1];
	                int point = Integer.parseInt(memberInfo[2]);
	                if (name1.equals(name)) { // 이름으로 회원 검색
	                    return new Member(name1, phoneNumber, point);
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return null; // 검색 결과가 없는 경우
	}

	public boolean addPoint(Member member, int totalPrice) {
		// TODO Auto-generated method stub
		return false;
	}			

	
	
}


//	public void removeMember(String id) {
//		
//		for(Member member : memberList) {
//			if(member.getName().equals(id)) {
//				memberList.remove(member);
//				System.out.println("회원 정보가 삭제되었습니다.");
//				return;
//			}
//		}
//		System.out.println("해당 회원에 대한 정보를 찾을수가 없습니다.");
//	}



//	public boolean login(String name, int phoneNumber) {
//	    for(Member member : memberList) {
//	        if(member.getName().equals(name) && member.getPhoneNumber().equals(phoneNumber)) {
//	            return true;
//	        }
//	    }
//	    return false;
//	}
