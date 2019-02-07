package inscriptions;

import commandLineMenus.Menu;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("toto");
		MenuClient menuClient = new MenuClient();
		Menu menuHome = menuClient.menuHome();
		System.out.println("tata");
		menuHome.start();

	}

}
