package coffeeApp;

public class Add {
	private String addName;
    private int price;


     public Add(String addName, int price) { 
        this.addName = addName;
        this.price = price;
    }


    public String getAddName() {
        return addName;
    }

    public void setAddName(String addName) {
        this.addName = addName;
    }
    
    
	public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


	
}
