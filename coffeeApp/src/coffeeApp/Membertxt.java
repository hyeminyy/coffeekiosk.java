package coffeeApp;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Membertxt {
	
	
	public static int stamp = 0;
	Scanner scanner = new Scanner(System.in);
	
	

	//관리자 메뉴 
	public  void admin() {
	    ArrayList<String[]> members = readMembersFromFile();

	    adminLogin();
	    showMembers();
	    
	    while (true) {
	    	 
	    	
	        System.out.println("원하는 작업을 선택하세요:");
	        System.out.println("1. 회원가입");
	        System.out.println("2. 회원 정보 수정");
	        System.out.println("3. 회원 정보 삭제");
	        System.out.println("4. 회원 목록 보기");
	        System.out.println("5. 프로그램 종료");

	        int choice = scanner.nextInt();
	        scanner.nextLine();

	        switch (choice) {
	            case 1:
	                register();
	            	showMembers();
	                break;
	            case 2:
	                update(members);
	                break;
	            case 3:
	                delete(members);
	                break;
	            case 4:
	                showMembers();
	                break;
	            case 5:
	                System.out.println("프로그램을 종료합니다.");
	                System.exit(0);
	                break;
	            default:
	                System.out.println("잘못된 입력입니다.");
	                break;
	        }
	    }
	}

	//관리자 로그인
    public  void adminLogin() {
        System.out.print("관리자 id: ");
        String id = scanner.next();
        System.out.print("관리자 pw: ");
        String pw = scanner.next();

        if (id.equals("admin") && pw.equals("admin")) {
            System.out.println("로그인 성공");
            // 로그인 성공시 수행할 코드 작성
        } else {
            System.out.println("로그인 실패");
            // 로그인 실패시 수행할 코드 작성
        }

    }

	
	//파일에서 회원정보를 읽어들여 ArrayList형태로 반환 
	public static ArrayList<String[]> readMembersFromFile() {
	    ArrayList<String[]> members = new ArrayList<>();
	    File file = new File("C:\\CafeAPP\\cafemember.txt");

	    try (Scanner scanner = new Scanner(file)) {
	        while (scanner.hasNextLine()) {
	            String[] info = scanner.nextLine().split("\t");
	            members.add(info);
	        }
	    } catch (FileNotFoundException e) {
	        System.out.println("파일을 찾을 수 없습니다.");
	    }

	    return members;
	}

	//ArrayList에서 회원정보를 삭제
	public static void delete(ArrayList<String[]> members) {
	    Scanner sc = new Scanner(System.in);
	    System.out.print("삭제할 회원의 이름을 입력하세요 : ");
	    String nameToDelete = sc.nextLine();
	    System.out.print("삭제할 회원의 전화번호 뒷자리를 입력하세요 : ");
	    String phoneNumberSuffix = sc.nextLine();

	    boolean found = false;
	    for (int i = 0; i < members.size(); i++) {
	        String[] memberInfo = members.get(i);
	        if (memberInfo[0].equals(nameToDelete) && memberInfo[1].endsWith(phoneNumberSuffix)) {
	            members.remove(i);
	            found = true;
	            System.out.println("회원 " + nameToDelete + "의 정보가 삭제되었습니다.");

	            // 파일에 삭제된 정보 저장
	            try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\CafeAPP\\cafemember.txt"))) {
	                for (String[] member : members) {
	                    writer.println(String.join("\t", member));
	                }
	                System.out.println("파일이 수정되었습니다.");
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            break;
	        }
	    }

	    if (!found) {
	        System.out.println("회원 " + nameToDelete + "을 찾을 수 없거나 전화번호 뒷자리가 일치하지 않습니다.");
	    }
	}
 

	//txt파일에 등록된 회원정보를 출력 
	public static void showMembers() {
	    ArrayList<String[]> members = readMembersFromFile();
	    if (members.isEmpty()) {
	        System.out.println("등록된 회원이 없습니다.");
	    } else {
	        System.out.println("[회원 정보]");
	        System.out.println("----------------------------------");
	        System.out.println("이름\t전화번호\t스탬프");

	        for (String[] memberInfo : members) {
	            System.out.println(memberInfo[0]  +"\t"+ memberInfo[1] + "\t" + memberInfo[2]);
	        }

	        System.out.println("----------------------------------");
	    }
	}

	//이름+전화번호 뒷자리를 입력받고 파일에 저장하며 회원가입. 
	public static void register() {
		
		Scanner sc = new Scanner(System.in);
		
		File file = new File("C:\\CafeAPP\\cafemember.txt");
	
		
		//예외처리
		try (FileWriter filewriter = new FileWriter(file,true)) {
			if(file.isFile()&&file.canWrite()) {
				//if 지정한파일이 진짜 파일인지&&파일을 쓸 수 있는 권한이 있다면
				System.out.println("회원가입을 위해 이름과 전화번호 뒷자리를 등록해주세요");
				System.out.println("이름 : ");
				filewriter.append(sc.nextLine());
				//아이디 비밀번호 구분을 위해 \t
				filewriter.append("\t");
				System.out.println("전화번호 뒷자리 : ");
				filewriter.append(sc.nextLine());
				//포인트 초기화
				filewriter.append("\t"+stamp);
				filewriter.append("\r");
				
				//그 이후, 회원가입하는 사람을을 구분하기위해 개행
				System.out.println("회원가입이 완료되었습니다.");
				
			}
		}catch(IOException e){
			e.printStackTrace();}
	}
	
	//회원정보 수정 	
	public static void update(ArrayList<String[]> members) {
	    Scanner sc = new Scanner(System.in);
	    System.out.print("수정할 회원의 이름을 입력하세요: ");
	    String name = sc.nextLine();
	    System.out.print("수정할 회원의 전화번호 뒷자리를 입력하세요: ");
	    String phoneNumberSuffix = sc.nextLine();

	    boolean found = false;
	    for (int i = 0; i < members.size(); i++) {
	        String[] info = members.get(i);
	        if (info[1].endsWith(phoneNumberSuffix) && info[0].equals(name)) {
	            found = true;
	            System.out.println("수정할 정보를 선택하세요:");
	            System.out.println("1. 이름 수정");
	            System.out.println("2. 전화번호 뒷자리 수정");
	            System.out.println("3. 스탬프 수정");
	            int choice = sc.nextInt();
	            sc.nextLine();

	            switch (choice) {
	                case 1:
	                    System.out.print("새로운 이름을 입력하세요 : ");
	                    String newName = sc.nextLine();
	                    info[0] = newName;
	                    break;
	                case 2:
	                    System.out.print("새로운 전화번호 뒷자리를 입력하세요 : ");
	                    String newPhoneNumber = sc.nextLine();
	                    info[1] = newPhoneNumber;
	                    break;
	                case 3:
	                    System.out.print("변경할 스탬프수를 입력하세요 : ");
	                    int newPoint = sc.nextInt();
	                    sc.nextLine();
	                    info[2] = Integer.toString(newPoint);
	                    break;
	                default:
	                    System.out.println("잘못된 입력입니다.");
	                    break;
	            }

	            // 파일에 수정된 정보 저장
	            try (PrintWriter writer = new PrintWriter(new FileWriter("C:\\CafeAPP\\cafemember.txt"))) {
	                for (String[] member : members) {
	                    writer.println(String.join("\t", member));
	                }
	                System.out.println("파일이 수정되었습니다.");
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	            break;
	        }
	    }

	    if (!found) {
	        System.out.println("해당 전화번호 뒷자리와 이름을 가진 회원이 존재하지 않습니다.");
	    }
	}


	
}