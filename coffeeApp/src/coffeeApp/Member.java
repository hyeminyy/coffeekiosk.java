package coffeeApp;


import java.io.*;

public class Member {
	
	
	
	
    private String name;
    private String phoneNumber;
    private int stamp;
    
    //txt파일의 회원정보 업데이트 
    public void updateMemberInfo(int stamp) {
        // 회원 정보를 업데이트하기 위해 파일 입출력을 사용합니다.
        File file = new File("C:\\CafeAPP\\cafemember.txt");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("\t");
                if (tokens[0].equals(name) && tokens[1].equals(phoneNumber)) {
                    sb.append(tokens[0]).append("\t").append(tokens[1]).append("\t").append(stamp).append("\n");
                } else {
                    sb.append(line).append("\n");
                }
            }
            br.close();
            FileWriter fw = new FileWriter(file);
            fw.write(sb.toString());
            fw.close();
        } catch (IOException e) {
            System.out.println("입출력오류");
        }
    }
    
    //(스탬프 적립) 스탬프 멤버변수에 더해줌 
    public boolean addStamp(int numStampsToAdd) {
    	if (numStampsToAdd < 1) {
    		System.out.println("적립할 스탬프 개수는 1개 이상이어야 합니다.");
    		return false;
    	}
    	this.stamp += numStampsToAdd;
    	return true;
    }
    //(스탬프사용) 스탬프개수가 현재 가지고있는 스탬프 개수보다 작거나 같으면 스탬프 갯수차감.
    public boolean useStamp(int numStampsToUse) {
    	if (stamp >= numStampsToUse) {
    		stamp -= numStampsToUse;
    		updateMemberInfo(stamp);
    		return true;
    	} else {
    		return false;
    	}
    }
    
    public Member(String name, String phoneNumber, int stamp) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.stamp = stamp;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public int getStamp() {
        return stamp;
    }


    
    

    

}

