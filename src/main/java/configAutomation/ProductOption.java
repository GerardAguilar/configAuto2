package configAutomation;

public class ProductOption {
	public String combinedString;
	public String action;
	public String attributeIdPair;
	public String selection;
	
	public ProductOption(String menu, String option) {
		combinedString = menu + " " + option;
		System.out.println("ProductOption String: " + menu);
		action = menu.split(":")[0];
		attributeIdPair = menu.split(":")[1];
		selection = option;
	}
}
