package priv.cuim.java.xmldemo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;


public class XmlDomDemo {
	public static int  nodeNumCount=0;

	public static void main(String[] args) {
		String filePath="."+File.separator+"conf"+File.separator+"ini.xml";
		

		try {
			//readRootNode(filePath);
			//traverseXmlElement(filePath);
			 xmlWrite(filePath);
	
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public static void readRootNode(String filePath) throws ParserConfigurationException, SAXException, IOException{
		InputStream xmlFileIn=XmlDomDemo.class.getClassLoader().getResourceAsStream(filePath);
		 
	    if(xmlFileIn==null){
	    	System.out.println("xml配置文件路径不正确");
	    	return;
	    }
	    
	    DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
	    DocumentBuilder bu=dbf.newDocumentBuilder();
	    
	    Document doc=bu.parse(xmlFileIn);
	    
		Element root=doc.getDocumentElement();
		
		if(root!=null){
//			System.out.println("rootEleName:"+root.getTagName());
//			System.out.println("rootEleContent:"+root.getTextContent());
//			System.out.println("**********************************");
			
			NodeList nodeList=root.getChildNodes();
			if(nodeList!=null){
				System.out.println("nodeList.Length:"+nodeList.getLength());
				
				for(int i=0;i<nodeList.getLength();i++){
				  if(nodeList.item(i).getNodeType()==Node.ELEMENT_NODE){
					  System.out.print("元素名:"+nodeList.item(i).getNodeName());
					  System.out.println("---熟悉名:"+((Element)nodeList.item(i)).getAttribute("name"));
				  }
				}
			}
		}
	}
	
	
	/**
	 * 便利xml中的所有元素,并输出当前元素的name属性值,如果没有name属性则输入"当前元素无name属性"
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	
	public static void traverseXmlElement(String filePath) throws ParserConfigurationException, SAXException, IOException{
		InputStream xmlFileIn=XmlDomDemo.class.getClassLoader().getResourceAsStream(filePath);
		 
	    if(xmlFileIn==null){
	    	System.out.println("xml配置文件路径不正确");
	    	return;
	    }
		
		DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		DocumentBuilder db=dbf.newDocumentBuilder();
		
		Document root=db.parse(xmlFileIn);
		
		if(root!=null){
			NodeList nodeList=root.getChildNodes();
			if(nodeList!=null){
				nodeNumCount=0;
				for(int i=0;i<nodeList.getLength();i++){
					traverseNode(nodeList.item(i));
				}
			}
		}
		
	}
	
	public static void xmlWrite(String filePath) throws ParserConfigurationException, SAXException, IOException, TransformerException{
		InputStream xmlFileIn=XmlDomDemo.class.getClassLoader().getResourceAsStream(filePath);
		
		 if(xmlFileIn==null){
		    	System.out.println("xml配置文件路径不正确");
		    	return;
		 }
		 
		 DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
		 DocumentBuilder db=dbf.newDocumentBuilder();
		 Document doc=db.parse(xmlFileIn);
		 Element root=doc.getDocumentElement();
		 
		 if(root==null){
			 return ;
		 }
		 root.setAttribute("data-name", "4MINUTE-WHY");
		 
		 NodeList nodeList=root.getChildNodes();
		 if((nodeList==null)||(nodeList.getLength()==0)){
			 return;
		 }
		 
		 for(int i=0;i<nodeList.getLength();i++){
			//判断节点类型是否是元素
			if(nodeList.item(i).getNodeType()==Node.ELEMENT_NODE){
				String nameAttr=nodeList.item(i).getAttributes().getNamedItem("name").getNodeValue();
				if("c1".equals(nameAttr)||"c2".equals(nameAttr)){
					root.removeChild(nodeList.item(i));
				}
				
				if("c3".equals(nameAttr)){
					Element addEle=doc.createElement("girl");
					addEle.setAttribute("name", "金允智");
					nodeList.item(i).appendChild(addEle);
				}
			}
		 }
		 
		 
		 Element addCollege=doc.createElement("college");
		 addCollege.setAttribute("name", "The hunter school");
		 Text text=doc.createTextNode("国际猎人学校地处南美洲委内瑞拉玻利瓦尔");
		 addCollege.appendChild(text);
		 root.appendChild(addCollege);
		 
		 
		 //保存修改后的文档
		 TransformerFactory tff=TransformerFactory.newInstance();
		 Transformer tf=tff.newTransformer();
		 DOMSource domS=new DOMSource(doc);
		 
		 //保存路径
		 String writeSavePath="src"+File.separator+"conf"+File.separator+"writeSave.xml";
		 File writeSaveFile=new File(writeSavePath);
		 if(writeSaveFile.exists()){
			 writeSaveFile.delete();
		 }
		 
		 writeSaveFile.createNewFile();
		 
		 FileOutputStream out=new FileOutputStream(writeSaveFile);
		 StreamResult strResult=new StreamResult(out);
		 tf.transform(domS, strResult);
		 
		 
		 System.out.println(writeSaveFile.getAbsolutePath());

	}
	
	private static void traverseNode(Node node){
		//参数有效性验证
		if(node==null){
			return;
		}
		//如果当前节点类型不是元素,则返回void
		if(node.getNodeType()!=Node.ELEMENT_NODE){
			return;
		}
		nodeNumCount++;
		String nodeName=node.getNodeName();
		String nameAttr="";
		
		Node nameAttrNode=node.getAttributes().getNamedItem("name");
		nameAttr=(nameAttrNode==null)?"当前元素没有name属性":nameAttrNode.getNodeValue();    
		
		System.out.println("第"+nodeNumCount+"个元素:元素名<"+nodeName+">  name属性值:<"+nameAttr+">");
		
		NodeList currNodeList=node.getChildNodes();
		
		for(int i=0;i<currNodeList.getLength();i++){
			traverseNode(currNodeList.item(i));
		}
	}

}
