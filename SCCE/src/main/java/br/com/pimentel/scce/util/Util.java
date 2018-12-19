package br.com.pimentel.scce.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.MaskFormatter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class Util implements Serializable {

	private static final long serialVersionUID = 376261897566381925L;
	
	private static ArrayList<String> dadosPersistenceXML = new ArrayList<String>();

	public static MaskFormatter mascaraDeTextoCPF() throws ParseException {
		MaskFormatter cpf = new MaskFormatter("##/##/####");
		return cpf;
	}

	public static String procuraArquivo(JPanel container, String extensaoArquivo) {
		String pathArquivo = null;
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("." + extensaoArquivo, extensaoArquivo);
		chooser.setFileFilter(filter);
		chooser.setApproveButtonText("Selecionar");
		int returnVal = chooser.showOpenDialog(container);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			pathArquivo = chooser.getSelectedFile().getAbsolutePath();
		}
		return pathArquivo;
	}

	public static boolean verificarExistenciaAquivo(String caminhoArquivo) {
		File arquivo = new File(caminhoArquivo);
		if (arquivo.exists()) {
			return true;
		}
		return false;
	}

	public static void imprimeErro(String msg, String msgErro) {
		JOptionPane.showMessageDialog(null, msg, "Erro crï¿½tico", 0);
		System.err.println(msg);
		System.out.println(msgErro);
		System.exit(0);
	}

	public static BufferedImage setImagemDimensao(String caminhoImg, Integer imgLargura, Integer imgAltura) {
		Double novaImgLargura = null;
		Double novaImgAltura = null;
		Double imgProporcao = null;
		Graphics2D g2d = null;
		BufferedImage imagem = null, novaImagem = null;
		try {
			imagem = ImageIO.read(new File(caminhoImg));
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
			// Logger.getLogger(ManipularImagem.class.getName()).log(Level.SEVERE, null,
			// ex);
		}
		novaImgLargura = (double) imagem.getWidth();
		novaImgAltura = (double) imagem.getHeight();
		if (novaImgLargura >= imgLargura) {
			imgProporcao = (novaImgAltura / novaImgLargura);
			novaImgLargura = (double) imgLargura;
			novaImgAltura = (novaImgLargura * imgProporcao);
			while (novaImgAltura > imgAltura) {
				novaImgLargura = (double) (--imgLargura);
				novaImgAltura = (novaImgLargura * imgProporcao);
			}
		} else if (novaImgAltura >= imgAltura) {
			imgProporcao = (novaImgLargura / novaImgAltura);
			novaImgAltura = (double) imgAltura;
			while (novaImgLargura > imgLargura) {
				novaImgAltura = (double) (--imgAltura);
				novaImgLargura = (novaImgAltura * imgProporcao);
			}
		}
		novaImagem = new BufferedImage(novaImgLargura.intValue(), novaImgAltura.intValue(), BufferedImage.TYPE_INT_RGB);
		g2d = novaImagem.createGraphics();
		g2d.drawImage(imagem, 0, 0, novaImgLargura.intValue(), novaImgAltura.intValue(), null);

		return novaImagem;
	}

	public static BufferedImage setImagemDimensao(BufferedImage imagem, Integer imgLargura, Integer imgAltura) {
		Double novaImgLargura = null;
		Double novaImgAltura = null;
		Double imgProporcao = null;
		Graphics2D g2d = null;
		BufferedImage novaImagem = null;

		novaImgLargura = (double) imagem.getWidth();
		novaImgAltura = (double) imagem.getHeight();
		if (novaImgLargura >= imgLargura) {
			imgProporcao = (novaImgAltura / novaImgLargura);
			novaImgLargura = (double) imgLargura;
			novaImgAltura = (novaImgLargura * imgProporcao);
			while (novaImgAltura > imgAltura) {
				novaImgLargura = (double) (--imgLargura);
				novaImgAltura = (novaImgLargura * imgProporcao);
			}
		} else if (novaImgAltura >= imgAltura) {
			imgProporcao = (novaImgLargura / novaImgAltura);
			novaImgAltura = (double) imgAltura;
			while (novaImgLargura > imgLargura) {
				novaImgAltura = (double) (--imgAltura);
				novaImgLargura = (novaImgAltura * imgProporcao);
			}
		}
		novaImagem = new BufferedImage(novaImgLargura.intValue(), novaImgAltura.intValue(), BufferedImage.TYPE_INT_RGB);
		g2d = novaImagem.createGraphics();
		g2d.drawImage(imagem, 0, 0, novaImgLargura.intValue(), novaImgAltura.intValue(), null);

		return novaImagem;
	}

	public static byte[] getImgBytes(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "JPEG", baos);
		} catch (IOException ex) {
		}
		return baos.toByteArray();
	}

	public static BufferedImage getImgBuffered(byte[] image) {

		InputStream input = new ByteArrayInputStream(image);
		try {
			BufferedImage imagem = ImageIO.read(input);
			return imagem;
		} catch (IOException ex) {
		}
		return null;
	}

	public static void exibiImagemLabel(byte[] minhaimagem, javax.swing.JLabel label) {
		if (minhaimagem != null) {
			InputStream input = new ByteArrayInputStream(minhaimagem);
			try {
				BufferedImage imagem = ImageIO.read(input);
				label.setIcon(new ImageIcon(imagem));
			} catch (IOException ex) {
			}
		} else {
			label.setIcon(null);
		}
	}

	public static void relogio(final JLabel label) {
		new Thread() {
			@Override
			public void run() {
				while (true) {
					java.util.Date d = new Date();
					String data = java.text.DateFormat.getDateInstance(DateFormat.FULL).format(d);
					String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
					label.setText(data + "  [" + hora + "]");
					try {
						sleep(1000);
					} catch (Exception e) {
					}
				}
			}
		}.start();
	}
	
	public static ArrayList<String> getInformacoesRede() throws UnknownHostException, SocketException {		
		ArrayList<String> informacoesRede = new ArrayList<String>();		
		InetAddress localHost = InetAddress.getLocalHost();
		NetworkInterface networkInterface = NetworkInterface.getByInetAddress(localHost);		
		String tipoConexao = null;		
		if (networkInterface.getName().contains("w")) {
			tipoConexao= "Sem fio";
		}else {
			tipoConexao= "Cabo";
		}
		List<InterfaceAddress> lista = networkInterface.getInterfaceAddresses();
		informacoesRede.add("Tipo de conexÃ£o:" + tipoConexao);
		informacoesRede.add("Interface:" + networkInterface.getDisplayName());
		informacoesRede.add("IP:" + lista.get(0).getAddress().getHostAddress());
		lista.get(0).getAddress();
		informacoesRede.add("Host:" + InetAddress.getLocalHost().getHostName());
		return informacoesRede;
	}
	
	public static boolean verificarConexaoInternet()  {
		Boolean status = false;
		InetAddress endereco = null;
		try {
			endereco = InetAddress.getByName("8.8.8.8");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int timeout = 1000;
		try {
			if (endereco.isReachable(timeout)) {
				status = true;
			}
		} catch (IOException e) {
		}
		return status;
	}
	
	private static Element auxiliarLerPersistenceXML() {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(Util.class.getResource("/META-INF/persistence.xml"));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		Element root = doc.getRootElement();
		return root;
	}
	
	private static ArrayList<String> auxiliarLerPersistenceXML2(Element element){		
	    for (int i = 0, size = element.nodeCount(); i < size; i++) {
	        Node node = element.node(i);	        
	        if (node.valueOf("@value").length() >3) {
	        	dadosPersistenceXML.add(node.valueOf("@name") + "=" + node.valueOf("@value"));
			}
	        if (node instanceof Element) {
	        	auxiliarLerPersistenceXML2((Element) node);	            
	        }
	    }
	    return dadosPersistenceXML;
	}
	
	public static ArrayList<String> dadosPersistenceXML(){
		ArrayList<String> arrayAux = new ArrayList<String>();		
		Element element = auxiliarLerPersistenceXML();		
		arrayAux = auxiliarLerPersistenceXML2(element);		
		return arrayAux;		
	}
	
	public static void janelaMaximizada(Stage stage){
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		stage.setX(bounds.getMinX());
		stage.setY(bounds.getMinY());
		stage.setWidth(bounds.getWidth());
		stage.setHeight(bounds.getHeight());
	}
	
	public static void centralizarJanela(Stage stage){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize(); 
			System.out.println(d.height + " - " + d.width);
		stage.setX((d.height-stage.getHeight())/2);
		stage.setY((d.width-stage.getWidth())/2);
		stage.setResizable(false);
	}
	
//	public static String getServidorIP() throws IOException {
//		String url = ConfiguracaoBD.getURL();
//		String ip = url.substring(url.indexOf("/") + 2, url.lastIndexOf(":"));
//		String porta = url.substring(url.lastIndexOf(":") + 1, url.lastIndexOf("/"));
//		return ip + ":" + porta;
//	}

}
