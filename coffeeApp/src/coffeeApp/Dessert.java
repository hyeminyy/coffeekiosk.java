package coffeeApp;
public class Dessert {

	private String dessertName;
    private int price;


     public Dessert(String dessertName, int price) { 
        this.dessertName = dessertName;
        this.price = price;
    }


    public String getDessertName() {
        return dessertName;
    }

    public void setDessertName(String dessertName) {
        this.dessertName = dessertName;
    }
    
    
	public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


	
}