package teste.view;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;
import teste.cache.externalinfos.ExternalInfoCacheManager;
import teste.cache.externalinfos.ExternalInfos;

@SuppressWarnings("serial")
public class JFTesteCache extends JFrame{

	private JLabel jlCount = new JLabel(" ");
	
	public JFTesteCache() {
		
		jlCount.setFont(jlCount.getFont().deriveFont(Font.BOLD,18f));
		
		setLayout(new MigLayout(new LC().wrapAfter(3)));
		add(jlCount, new CC().wrap());
		for(ExternalInfoCacheManager<?> c : ExternalInfos.INFOS){
			add(new JPCacheInfo(c), new CC().width("100%").height("100%"));
		}

		pack();
		setMinimumSize(getSize());
		setLocationRelativeTo(null);
		setTitle("Teste cache");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private class JPCacheInfo extends JPanel{
		private ExternalInfoCacheManager<?> info;
		private JTextArea jtaConsole = new JTextArea();

		public JPCacheInfo(ExternalInfoCacheManager<?> info) {
			this.info = info;

			jtaConsole.setLineWrap(true);
			jtaConsole.setWrapStyleWord(true);
			jtaConsole.setEditable(false);

			setLayout(new MigLayout());
			add(new JLabel(info.getExternalInfoClass().getSimpleName()));
			add(new JLabel("Duração da cache(millis): "+info.getCacheDurationMillis()), new CC().alignX("right").wrap());
			add(new JScrollPane(jtaConsole), new CC().spanX().width("300:100%:").height("200:100%:"));
		}

		public void buscarInfo(){
			try {
				jtaConsole.setText(""+info.getCachedInfo());
			} catch (Exception e) {
				e.printStackTrace();
				jtaConsole.setText("ERROR!");
			}
		}
		
	}
	
	public void iniciarBuscasFreneticas(){
		
		new Thread(){
			@Override
			public void run(){
				int cont = 0;
				while (isVisible()) {
					try {
						Thread.sleep(100);
						cont++;
						jlCount.setText("Verificações: "+cont);
						for(Component c : getContentPane().getComponents()){
							if(c instanceof JPCacheInfo){
								((JPCacheInfo)c).buscarInfo();
							}
						}
					} catch (Exception e) {}
				}
			}
		}.start();

	}
}
