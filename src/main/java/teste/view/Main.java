package teste.view;

import javax.swing.UIManager;

public class Main {

	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		initFrame();
		initFrame();
	}

	private static void initFrame() {
		JFTesteCache frame = new JFTesteCache();
		frame.setVisible(true);
		frame.iniciarBuscasFreneticas();
	}

}
