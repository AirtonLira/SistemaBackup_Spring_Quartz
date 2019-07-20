package salema.servicobackup.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.quartz.SchedulerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class configuracoes {
	
	public ArrayList<ArrayList<String>> diretorios;
	public String logs;
	public Boolean valido;
	
	
	//Metodo para leitura do arquivo de configuracao com os diretorios de backups, metodo ira retornar duas listas.
    //Contendo os diretorios de arquivos de backup e quais diretorios a transferir.
	public configuracoes() throws ParserConfigurationException, SAXException, IOException  {

    	File config = new File("config.xml");
    	
    	//Inicializa uma fabrica de documentos
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	
    	//Cria um construtor de documentos
    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    	
    	//Converte o arquivo em um documento utilizando o construtor
    	Document doc = dBuilder.parse(config);
    	
    	doc.getDocumentElement().normalize();
    	
    	//Cria lista do tipo string para os path do diretorio
    	ArrayList<String> diretorios = new ArrayList<String>();
    	diretorios.addAll(new ArrayList<String>());
    	
    	ArrayList<String> backups = new ArrayList<String>();
    	
    	
    	ArrayList<ArrayList<String>> retorno = new ArrayList<ArrayList<String>>();
    	
    	//Obtem uma lista de nos do elemento diretorios
    	NodeList nList = doc.getElementsByTagName("diretorios");
    	
    	//Obtem o nó de logs do xml
    	NodeList nodeLogs = doc.getElementsByTagName("logs"); 
    	
    	//Obtem o node ja convertendo para um Elemento e obtendo o atributo desse elemento
    	this.logs = ((Element) nodeLogs.item(0)).getAttribute("path");
    	
    	
    	for (int temp = 0; temp < nList.getLength(); temp++) {
    		Node nNode = nList.item(temp);
    		Element eElement = (Element) nNode;
    		
    		diretorios.add(eElement.getAttribute("path"));
    	}
    	
    	nList = doc.getElementsByTagName("backup");
    	
       	for (int temp = 0; temp < nList.getLength(); temp++) {
    		Node nNode = nList.item(temp);
    		Element eElement = (Element) nNode;
    		
    		backups.add(eElement.getAttribute("path"));
    	}
       	retorno.add(0, diretorios);
       	retorno.add(1, backups);
       	
		this.diretorios = retorno;
		
		validador();
	}
	
	/* Rotina para realizar validação do xml
	 * Identificando se a estrutura esta totalmente complenta
	 */
	public void validador() {
		int contador = 0;
		int somatorio = 0;
		ArrayList<ArrayList<String>> configatual = this.diretorios;
		
		//Quantidade de diretorios para mover é menor ou maior que o total de diretorios de backup.
		for(int i = 0; i < configatual.size(); i++) {
			contador += 1;
			somatorio += configatual.get(i).size();
		}
		this.valido = (somatorio % contador) == 0 ? true : false;
	

	}
	
	

}

