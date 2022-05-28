import database.Connect;

public class Main {
	
	Connect con = Connect.getInstance();
	public Main() {
		Connect con = new Connect();
		home hh = new home();
	}

	public static void main(String[] args) {
		new Main();
	}

}
