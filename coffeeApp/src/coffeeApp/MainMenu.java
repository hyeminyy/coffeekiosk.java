package coffeeApp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {
	private Manage manage;
	private Scanner scanner;
	public static void main(String[] args) {
		MainMenu menu = new MainMenu();
		menu.run();
		
	}
	
	public MainMenu() {
		manage  = new Manage();
		scanner = new Scanner(System.in);
	}
	
	//루프를 이용해 사용자 메뉴 선택 
	public void run() {
		while(true) {
			System.out.println("======================");
			System.out.println("[1]☕ 회원 가입 ☕");
			System.out.println("[2]🕊️ 비회원 주문하기🕊️");
			System.out.println("[3]🧑 회원 주문하기 🧑");
			System.out.println("[4]🔳     종료     🔳");
			System.out.println("[5]🧑 관리자 모드 🧑");
			System.out.println("======================");
			System.out.print("메뉴를 선택하세요 : ");
			int choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
			case 1: //회원 가입
				signUp();
				break;
			case 2: //비회원 주문 
				purchase();
				break;
			case 3: //회원 로그인 후 주문
				memberPurchase();
				break;
			case 4: // 종료
				System.out.println("프로그램을 종료합니다.");
				return;
			case 5: // 관리자모드 호출
				Membertxt admin = new Membertxt();
				admin.admin();
			default:
				System.out.println("잘못된 선택입니다.");
				break;
		}	
		}
		
	}
	
	//회원등록
	private void signUp() {
    	Membertxt.register();
    	run();
    	}
	
	//비회원 구매 
	private void purchase() {
		System.out.println("구매하실 제품을 선택해주세요.");
		List<Coffee> coffeeList = getCoffeeList();	
		 System.out.println("======================");
		 System.out.println("     ☕ 커피 메뉴판");  
		 System.out.println("======================");
		 
		 
		//커피리스트 출력 - for문 
		 for(Coffee coffee : coffeeList) {
		 System.out.println(coffee.getCoffeeName() + " " + coffee. getPrice()+"원" );}
		 System.out.println("======================");
		 System.out.print("음료를 선택하세요.: ");	
		 
		 
		 
		 
		//커피선택 정수입력 
		int choice = scanner.nextInt();  
		//커피선택 입력값 잘못 입력했을시.. 
		if(choice < 1 || choice>coffeeList.size()) {
			System.out.println("잘못된 선택입니다.");
			return;
			
		}
		//선택된 커피의 정보를 selectedCoffee 변수에 저장.
		Coffee selectedCoffee = coffeeList.get(choice - 1); //리스트는 0부터시작하기떄문에 -1 
	    System.out.print("수량을 입력하세요: "); 
	    //커피 수량 입력
	    int amount = scanner.nextInt();
	    scanner.nextLine();
	    System.out.println("[1] 바로 결제하기 [2] 추가구매(디저트/추가옵션)");
	    int coffeOnly=scanner.nextInt();
	    
	    //바로결제 
	    if(coffeOnly==1) {
	        CtotalPayment(selectedCoffee, amount);
			int totalPice = CtotalPrice(selectedCoffee, amount);
			 CprintReceipt(selectedCoffee, amount, 0, totalPice);
			
			return;
			//추가구매 
		}else if(coffeOnly==2) {
				
		    	
				//디저트 주문
			    List<Dessert> dessertList = getDessertList();	
			    //디저트 주문수량 카운트 
			    int secondchoice ; 
		   	 	System.out.println("======================");
		   	 	System.out.println("    🍰 디저트 메뉴판"); 
				System.out.println("======================");

				
				//디저트 메뉴 출력 
				for( Dessert dessert1 : dessertList) {
						System.out.println(dessert1.getDessertName() + " " + dessert1.getPrice()+"원" );
				 }
				System.out.println("======================");
				System.out.print("번호 선택 : ");
		    	secondchoice = scanner.nextInt();
		    	if(secondchoice>dessertList.size()) {
					System.out.println("잘못된 선택입니다.");
					return;
				}
		    	
				//커피+디저트를 선택하는 경우
		    		Dessert selectedDessert = dessertList.get(secondchoice - 1);
		    		
				    System.out.print("수량을 입력해주세요: "); //디저트 수량 입력 
				    int amount1 = scanner.nextInt();
			
				    //음료 추가 옵션 선택하기 
				    List<Add> addList = getAddList();	
				    int thirdchoice = 0;
					 System.out.println("======================");
					 System.out.println("    ☕ 음료 추가옵션"); 
					 System.out.println("======================");
					 System.out.println("[0] 옵션 추가 안하기 ");
					//음료 추가옵션 출력
					 for(Add add1 : addList) {
						 System.out.println(add1.getAddName() + " " + add1.getPrice()+"원" );}
					 System.out.println("======================");
					 //추가옵션 메뉴선택 
						 System.out.print("번호 선택 : ");
						 thirdchoice = scanner.nextInt();
						 
						 //옵션추가 안하기 
					    	if(thirdchoice==0) { 
						   		 System.out.print("결제 방법을 선택해주세요. [1] 카드 [2] 현금 ");
							 		int paymentMethod = scanner.nextInt();
							 		scanner.nextLine();
							 		//커피+디저트 구매시 결제부분출력
							 		CDtotalpayMent(paymentMethod, selectedCoffee, amount, selectedDessert, amount1);
						    		
						    		int totalPice = CDtotalPrice(selectedCoffee, amount, selectedDessert, amount1);
						    		CDprintReceipt(selectedCoffee, amount, selectedDessert, amount1, paymentMethod, totalPice);		    	
					    	}
					    	
							 Add selectedAdd = addList.get(thirdchoice - 1);
							 System.out.print("수량을 입력해주세요: ");
						     int amount2 =  scanner.nextInt();
							 
					   		 System.out.print("결제 방법을 선택해주세요. [1] 카드 [2] 현금 ");
						 		int paymentMethod = scanner.nextInt();
						 		scanner.nextLine();
						 		//전체 구매시 결제 프로세스 출력
						 		CDAtotalpayMent(paymentMethod, selectedCoffee, amount, selectedDessert, amount1, selectedAdd, amount2);
					    		
					    		int totalPice = CDAtotalPrice(selectedCoffee, amount, selectedDessert, amount1, selectedAdd, amount2);
					    		CDAprintReceipt(selectedCoffee, amount, selectedDessert, amount1, selectedAdd, amount2, paymentMethod, totalPice);
			   	
								}
				
				
				
			}
	    
		

	    
	    
	    

	
	
	//회원구매
	
	public void memberPurchase() {
		purchase();

		System.out.println("등록하신 이름과 전화번호 뒷자리를 입력하세요");
		System.out.println("이름 : ");
		String name = scanner.nextLine();
		System.out.println("전화번호 뒷자리 : ");
		String phoneNumber = scanner.nextLine();
		
		login(name, phoneNumber);
		
	}
	
	
	public void login(String name, String phoneNumber) {

		int pass= 0 ; //로그인 여부 확인용 
		
		//어디에서 파일을 가져올건지 파일 입출력 선언. 
		File file = new File("C:\\CafeAPP\\cafemember.txt");
		try {						//예외처리
			FileReader filereader = new FileReader(file);
			BufferedReader bufReader = new BufferedReader(filereader);
			//String 변수 생성 => 완전히 파일이 다 읽혀질때까지 반복
			String line ="";
			try {					//예외처리2
				while((line = bufReader.readLine())!=null ) {
					
					//로그인이 되었는지 안되었는지 확인하기위해서 pass변수 생성
					// 같은 문자열이 있다면 그 인덱스 첫 텍스트문자의 위치값 전송,못 찾으면 -1
					int passName = line.indexOf(name);
					int passPn = line.indexOf(phoneNumber);
					System.out.println();
					
					//로그인 여부 
					if (passName != -1 && passPn != -1) { //읽은 데이터파일에서 찾았다면 출력  
					    System.out.println(name + "님 반갑습니다.");
					    String[] tokens = line.split("\t");
					    int stamp = Integer.parseInt(tokens[2]);
						 System.out.println("======================");
					    System.out.println(" 🎃 무료 아아메 Stamp 현황 : " + stamp);
						 System.out.println("======================");
					    System.out.println(" Stamp 사용하기 - [1]");
					    System.out.println(" Stamp 적립하기 - [2]");
					    int stampSelec=scanner.nextInt();
					    if(stampSelec==1) {
					    	//스탬프 사용하기     
		                    Member member = new Member(name, phoneNumber, stamp); // Member 인스턴스 생성
		                    System.out.println("아아메 몇잔을 교환하시겠습니까?");
		                    int numStampsToUse = scanner.nextInt();
		                    boolean isStampUsed = member.useStamp(numStampsToUse * 10); // 한 번에 10개씩 사용 가능하도록 수정
		                    if(isStampUsed) {
		               		 System.out.println("======================");
		                        System.out.println(numStampsToUse + "잔의 아메리카노를 무료로 즐기세요! ☕️");
		               		 System.out.println("======================");
		                        stamp -= numStampsToUse * 10; // 사용한 스탬프 개수만큼 차감
		                        
		                        member.updateMemberInfo(stamp);
		                        System.out.println("남은 스탬프 :" + stamp);
		                        System.out.println("======================");

		                     
		                    } else {
		                            System.out.println("보유하신 스탬프가 부족합니다.");
	                        }
					    	
		                    //스탬프 적립하기
					    }else if(stampSelec==2){
					    		System.out.println("1번 주문당 스탬프 총 1개가 적립됩니다.");
					    		Member member = new Member(name, phoneNumber, stamp); // Member 인스턴스 생성
					    		int numStampsToAdd = 1;
					    	    boolean isStampAdded = member.addStamp(numStampsToAdd); 
					    		member.updateMemberInfo(stamp);
					    		if(isStampAdded) {
					    	        System.out.println("스탬프가 총 " + numStampsToAdd + "개 적립되었습니다.");
					    	        stamp += numStampsToAdd; // 적립한 스탬프 개수만큼 추가
					    	        member.updateMemberInfo(stamp);
					    	        System.out.println("남은 스탬프 :" + stamp);
					    	        
					    	       //결제~영수증 출력 (미구현)     	        
					    	        
					    	        
					    	        
					    	        
					    	    } else {
					    	        System.out.println("스탬프 적립에 실패하였습니다.");
					    	    }
					    	    System.out.println("프로그램 종료-[1] 다시주문-[2]");
		                        int endChoice=scanner.nextInt();
		                        if(endChoice==1) {
		                        	return;
		                        }else if(endChoice==2) {
		                        	run();
		                        }
					    		
					    }
					    
					    
					    
					    pass = 1; //로그인 성공 pass=1; 
					    if (pass == 1) {
					        
					    }
					}
				}
				if(pass==0) { 
					System.out.println("등록되지 않았거나 잘못입력하셨습니다");
					System.out.println("회원가입하기 -[1] 처음으로-[2]");
					int setChoice = scanner.nextInt();
					if(setChoice==1) {
						//회원가입
						signUp();
					}else if(setChoice==2) {
						//처음으로 돌아가기
						run();
					}
				}
			} catch (IOException e) { //예외처리2 
				e.printStackTrace();}
		}catch (FileNotFoundException e) { //예외처리
			// TODO Auto-generated catch block
			e.printStackTrace();}
		
		
	
}
	
	
	//Coffee 리스트 : 4번 수정하기 
	public List<Coffee> getCoffeeList() { //커피리스트
	    List<Coffee> coffeeList = new ArrayList<Coffee>();
	    coffeeList.add(new Coffee("[1] 아메리카노", 2000));
	    coffeeList.add(new Coffee("[2] 카페라떼", 3000));
	    coffeeList.add(new Coffee("[3] 카푸치노", 3000));
	    coffeeList.add(new Coffee("[4] 프라푸치노", 2500));
	    coffeeList.add(new Coffee("[5] 에스프레소", 2500));
	    coffeeList.add(new Coffee("[6] 초코라떼", 3500));
	    coffeeList.add(new Coffee("[7] 녹차라떼", 3500));
	    coffeeList.add(new Coffee("[8] 딸기라떼", 4000));
	    coffeeList.add(new Coffee("[9] 아이스티", 2000));
	    coffeeList.add(new Coffee("[10] 청포도에이드", 3000));
	    coffeeList.add(new Coffee("[11] 자몽에이드", 3000));
	    coffeeList.add(new Coffee("[12] 레몬에이드", 3000));
	    
	    
	    
	    return coffeeList;
	}
	//Dessert 리스트
	public List<Dessert> getDessertList() { //디저트 리스트
	    List<Dessert> dessertList = new ArrayList<Dessert>();
	    dessertList.add(new Dessert("[1] 초코 마카롱", 3000));
	    dessertList.add(new Dessert("[2] 딸기 마카롱", 3000));
	    dessertList.add(new Dessert("[3] 허니 브레드", 6000));
	    dessertList.add(new Dessert("[4] 에그타르트", 3000));
	    dessertList.add(new Dessert("[5] 딸기 케이크", 6000));
	    dessertList.add(new Dessert("[6] 초코 케이크", 6000));
	    dessertList.add(new Dessert("[7] 선택 x", 0));

	    
	    
	    return dessertList;
	}

	//추가옵션 리스트(샷추가..시럽추가..사이즈업..휘핑..)
	public List<Add> getAddList() { //추가 리스트
	    List<Add> addList = new ArrayList<Add>();
	    addList.add(new Add("[1] 샷 1번 추가", 500));
	    addList.add(new Add("[2] 샷 2번 추가", 1000));
	    addList.add(new Add("[3] 시럽 1번 추가", 0));
	    addList.add(new Add("[4] 시럽 2번 추가", 0));
	    addList.add(new Add("[5] 사이즈 업", 1000));
	    addList.add(new Add("[6] 휘핑 크림 추가", 500));
	    addList.add(new Add("[7] 선택 x", 0));

	    
	    
	    return addList;
	}
	

	//(커피+디저트+추가) 총 금액 계산
	public int CDAtotalPrice(Coffee coffee, int coffeeAmount, Dessert dessert, int dessertAmount, Add add, int addAmount) {
	    int coffeePrice = coffee.getPrice() * coffeeAmount;
	    int dessertPrice = dessert.getPrice() * dessertAmount;
	    int addPrice = add.getPrice() * addAmount;
	    return coffeePrice + dessertPrice + addPrice;

		}
	//(커피+디저트+추가) 현금 결제 부분 구현
	public void CDAtotalpayMent(int paymentMethod, Coffee coffee, int coffeeAmount, Dessert dessert, int dessertAmount, Add add, int addAmount) {
	    if (paymentMethod == 1) {
	        String answer1 = "";
	        while (!answer1.equalsIgnoreCase("y") && !answer1.equalsIgnoreCase("n")) {
	            System.out.print("카드로 결제하시겠습니까?[Y] [N]");
	            answer1 = scanner.nextLine();

	            if (answer1.equalsIgnoreCase("y")) {
	                try {
	                    System.out.println("카드를 승인중입니다..");
	                    for (int i = 0; i < 3; i++) {
	                        System.out.println(".");
	                        Thread.sleep(1000);
	                    }
	                    System.out.println("");
	                    System.out.println("[결제완료]");
	                    System.out.println("");

	                    break;

	                } catch (InterruptedException e) {
	                    // InterruptedException 예외 처리
	                    e.printStackTrace();
	                }

	            } else if (answer1.equalsIgnoreCase("n")) {
	                paymentMethod = 0;
	                break;
	            } else {
	                System.out.println("잘못된 입력입니다.");
	            }
	        }
	    } else if (paymentMethod == 2) {
	        System.out.println("현금 결제를 진행합니다.");
	        CDAtotalPurchase(coffee, coffeeAmount, dessert, dessertAmount, add, addAmount);
	    } else {
	        System.out.println("잘못된 입력입니다.");
	    }
	}

	public void CDAtotalPurchase(Coffee coffee, int coffeeAmount, Dessert dessert, int dessertAmount, Add add, int addAmount) {
	    int CDAtotalPrice = (coffee.getPrice() * coffeeAmount) + (dessert.getPrice() * dessertAmount) + (add.getPrice() * addAmount);
	    System.out.println("총 결제금액은 " + CDAtotalPrice + "원 입니다.");
	    int paymentAmount = 0;
	    while (paymentAmount < CDAtotalPrice) {
	        System.out.print("지불한 금액을 입력해주세요 : ");
	        paymentAmount = scanner.nextInt();
	        scanner.nextLine();

	        if (paymentAmount < CDAtotalPrice) {
	            System.out.println("지불할 금액이 부족합니다");
	        }
	    }
	    System.out.println("거스름돈은 " + (paymentAmount - CDAtotalPrice) + "원 입니다");
	    System.out.println("[결제완료]");
	}
		
	//(커피+디저트+추가)영수증	출력 (커피 번호,커피수량| 디저트 번호 디저트양 | 추가번호 / 추가양 |결제방식 | 총금액 | ) 
	public void CDAprintReceipt(Coffee selectedCoffee, int coffeeAmount, Dessert selectedDessert, int dessertAmount, Add selectedAdd, int addAmount, int paymentMethod, int totalPrice) {
		LocalDateTime now = LocalDateTime.now();
		String printNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
		System.out.println("");
		System.out.println("[영수증]");
		System.out.println(printNow);
		System.out.println("------------------------------");
		System.out.println("제품명\t\t수량\t가격");
		System.out.println("------------------------------");
		System.out.printf("%s\t%d\t%d원\n", selectedCoffee.getCoffeeName(), coffeeAmount, selectedCoffee.getPrice() * coffeeAmount);
		System.out.printf("%s\t%d\t%d원\n", selectedDessert.getDessertName(), dessertAmount, selectedDessert.getPrice() * dessertAmount);
		System.out.printf("%s\t%d\t%d원\n", selectedAdd.getAddName(), addAmount, selectedAdd.getPrice() * addAmount);
		System.out.println("------------------------------");
		System.out.println("결제 방법: " + (paymentMethod == 1 ? "카드" : "현금"));
		System.out.println("총 결제 금액: " + totalPrice + "원");
		System.out.println("------------------------------");
		run();
	}
	
	// (커피만)총 금액 계산 (커피 번호,커피수량) 
	public int CtotalPrice(Coffee coffee, int coffeeAmount) {
	    int coffeePrice = coffee.getPrice() * coffeeAmount;
	    return coffeePrice;
	    
	    
		}
	//커피만 결제시 결제 프로세스
	public void CtotalPayment(Coffee coffee, int coffeeAmount) {
	    int totalPice = CtotalPrice(coffee, coffeeAmount);

	    int paymentMethod = 0;
	    while (paymentMethod != 1 && paymentMethod != 2) {
	        System.out.print("결제 방법을 선택해주세요. [1] 카드 / [2] 현금 : ");
	        paymentMethod = scanner.nextInt();
	        scanner.nextLine();
	    }

	    if (paymentMethod == 1) {
	        String answer1 = "";
	        while (!answer1.equalsIgnoreCase("y") && !answer1.equalsIgnoreCase("n")) {
	            System.out.print("카드로 결제하시겠습니까?[Y] [N]");
	            answer1 = scanner.nextLine();

	            if (answer1.equalsIgnoreCase("y")) {
	                try {
	                    System.out.println("카드를 승인중입니다..");
	                    for (int i = 0; i < 3; i++) {
	                        System.out.println(".");
	                        Thread.sleep(1000);
	                    }
	                    System.out.println("");
	                    System.out.println("[결제완료]");
	                    System.out.println("");

	                    break;

	                } catch (InterruptedException e) {
	                    // InterruptedException 예외 처리
	                    e.printStackTrace();
	                }

	            } else if (answer1.equalsIgnoreCase("n")) {
	                paymentMethod = 0;
	                break;
	            } else {
	                System.out.println("잘못된 입력입니다.");
	            }
	        }
	    } else if (paymentMethod == 2) {
	        CtotalPurchase(coffee, coffeeAmount, totalPice);
	    } else {
	        System.out.println("잘못된 입력입니다.");
	    }
	}
	//커피만 결제시 현금결제 
	public void CtotalPurchase(Coffee coffee, int coffeeAmount, int totalPice) {
	    int paymentAmount = 0;
	    while (paymentAmount < totalPice) {
	        System.out.print("지불한 금액을 입력해주세요 : ");
	        paymentAmount = scanner.nextInt();
	        scanner.nextLine();

	        if (paymentAmount < totalPice) {
	            System.out.println("지불할 금액이 부족합니다");
	        }
	    }
	    System.out.println("거스름돈은 " + (paymentAmount - totalPice) + "원 입니다");
	    System.out.println("[결제완료]");
	}
		
	//(커피만)영수증	출력2(커피 번호,커피수량|결제방식 | 총금액 | ) 
	public void CprintReceipt(Coffee selectedCoffee, int coffeeAmount, int paymentMethod, int totalPrice) {
		LocalDateTime now = LocalDateTime.now();
		String printNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
		System.out.println("");
		System.out.println("[영수증]");
		System.out.println(printNow);
		System.out.println("------------------------------");
		System.out.println("제품명\t\t수량\t가격");
		System.out.println("------------------------------");
		System.out.printf("%s\t%d\t%d원\n", selectedCoffee.getCoffeeName(), coffeeAmount, selectedCoffee.getPrice() * coffeeAmount);
		System.out.println("------------------------------");
		System.out.println("결제 방법: " + (paymentMethod == 1 ? "카드" : "현금"));
		System.out.println("총 결제 금액: " + totalPrice + "원");
		System.out.println("------------------------------");
		run();
	}
	
	// 총 금액 계산
	public int CDtotalPrice(Coffee coffee, int coffeeAmount, Dessert dessert, int dessertAmount) {
		int coffeePrice = coffee.getPrice() * coffeeAmount;
		int dessertPrice = dessert.getPrice() * dessertAmount;
		return coffeePrice + dessertPrice ;
		
	}
	
	public void CDtotalpayMent(int paymentMethod, Coffee coffee, int coffeeAmount, Dessert dessert, int dessertAmount) {
	    int totalPice = CDtotalPrice(coffee, coffeeAmount, dessert, dessertAmount);
	    
	    if (paymentMethod == 1) {
	        String answer1 = "";
	        while (!answer1.equalsIgnoreCase("y") && !answer1.equalsIgnoreCase("n")) {
	            System.out.print("카드로 결제하시겠습니까?[Y] [N]");
	            answer1 = scanner.nextLine();

	            if (answer1.equalsIgnoreCase("y")) {
	                try {
	                    System.out.println("카드를 승인중입니다..");
	                    for (int i = 0; i < 3; i++) {
	                        System.out.println(".");
	                        Thread.sleep(1000);
	                    }
	                    System.out.println("");
	                    System.out.println("[결제완료]");
	                    System.out.println("");

	                    break;

	                } catch (InterruptedException e) {
	                    // InterruptedException 예외 처리
	                    e.printStackTrace();
	                }

	            } else if (answer1.equalsIgnoreCase("n")) {
	                paymentMethod = 0;
	                break;
	            } else {
	                System.out.println("잘못된 입력입니다.");
	            }
	        }
	    } else if (paymentMethod == 2) {
	        System.out.println("현금 결제를 진행합니다.");
	        CDtotalPurchase(coffee, coffeeAmount, dessert, dessertAmount, totalPice);
	    } else {
	        System.out.println("잘못된 입력입니다.");
	    }
	}

	public void CDtotalPurchase(Coffee coffee, int coffeeAmount, Dessert dessert, int dessertAmount, int totalPice) {
	    int paymentAmount = 0;
	    while (paymentAmount < totalPice) {
	        System.out.print("지불한 금액을 입력해주세요 : ");
	        paymentAmount = scanner.nextInt();
	        scanner.nextLine();

	        if (paymentAmount < totalPice) {
	            System.out.println("지불할 금액이 부족합니다");
	        }
	    }
	    System.out.println("거스름돈은 " + (paymentAmount - totalPice) + "원 입니다");
	    System.out.println("[결제완료]");
	}
	
	//회원주문

	//(커피+디저트)영수증 출력1 (커피 번호,커피수량| 디저트 번호 디저트양 |결제방식 | 총금액 | ) 
	public void CDprintReceipt(Coffee selectedCoffee, int coffeeAmount,Dessert selectedDessert, int dessertAmount, int paymentMethod, int totalPrice) {
		LocalDateTime now = LocalDateTime.now();
		String printNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
	    System.out.println("");
		System.out.println("[영수증]");
		System.out.println(printNow);
	    System.out.println("------------------------------");
	    System.out.println("제품명\t\t수량\t가격");
	    System.out.println("------------------------------");
	    System.out.printf("%s\t%d\t%d원\n", selectedCoffee.getCoffeeName(), coffeeAmount, selectedCoffee.getPrice() * coffeeAmount);
	    System.out.printf("%s\t%d\t%d원\n", selectedDessert.getDessertName(), dessertAmount, selectedDessert.getPrice() * dessertAmount);
	    System.out.println("------------------------------");
	    System.out.println("결제 방법: " + (paymentMethod == 1 ? "카드" : "현금"));
	    System.out.println("총 결제 금액: " + totalPrice + "원");
	    System.out.println("------------------------------");
	    run();
	}
}	
	