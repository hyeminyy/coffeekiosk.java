package coffeeApp;

public class Coffee {

	private String coffeeName;
    private int price;


     public Coffee(String coffeeName, int price) { 
        this.coffeeName = coffeeName;
        this.price = price;
    }


    public String getCoffeeName() {
        return coffeeName;
    }

    public void setCoffeeName(String coffeeName) {
        this.coffeeName = coffeeName;
    }
    
    
	public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}