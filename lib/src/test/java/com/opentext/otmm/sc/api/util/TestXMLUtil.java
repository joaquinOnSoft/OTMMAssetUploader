package com.opentext.otmm.sc.api.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.opentext.otmm.sc.api.beans.OTMMAsset;
import com.opentext.otmm.sc.api.beans.OTMMCollection;
import com.opentext.otmm.sc.api.util.XMLUtil;

public class TestXMLUtil {
	private static final String XML_OTMM_COLLECTION = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<collections>"  
			+ 	"<collection>" 
			+ 		"<id>37565d42b090cae4774605bd1c9dd85c75063ad5</id>" 
			+ 		"<name>Auckland</name>"
			+ 		"<ownerName>admin, otmm</ownerName>"
			+ 	"</collection>" 
			+ "</collections>";
	
	private static final String XML_ASSETS_IN_COLLECTION = 
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" 
			+ "<collection>"
			+ 	"<asset>"
			+ 		"<id>f3bb958da1da37f8db6bc95988fa4c46071745a7</id>"
			+ 		"<name>Auckland_Harbor-1.JPG</name><mimeType>image/jpeg</mimeType>"
			+ 		"<deliveryServiceURL>http://mydemoserver.com/adaptivemedia/rendition?id=45fe8cbf156f06d18e293f95eb0c89faba9af16e</deliveryServiceURL>"
			+ "</asset>"
			+ "<asset>"
			+ 		"<id>b8fc7a4377053c4cb8c004cef29136cc5db759d4</id>"
			+ 		"<name>Mudbrick-1.JPG</name><mimeType>image/jpeg</mimeType>"
			+ 		"<deliveryServiceURL>http://mydemoserver.com/adaptivemedia/rendition?id=b8fc7a4377053c4cb8c004cef29136cc5db759d4</deliveryServiceURL>"
			+ "</asset>"
			+ "</collection>";
	
	@Test
	void otmmCollectionsToXML() {
		OTMMCollection col = new OTMMCollection("37565d42b090cae4774605bd1c9dd85c75063ad5", "Auckland");
		col.setOwnerName("admin, otmm");
		
		Map<String, OTMMCollection> otmmCollections = new HashMap<String, OTMMCollection>();
		otmmCollections.put("37565d42b090cae4774605bd1c9dd85c75063ad5", col);
		
		String xml = XMLUtil.otmmCollectionsToXML(otmmCollections);
		assertNotNull(xml);
		assertEquals(XML_OTMM_COLLECTION, xml);
	}
	
	@Test
	void assetsToXML() {
		OTMMAsset asset1 = new OTMMAsset("f3bb958da1da37f8db6bc95988fa4c46071745a7", "Auckland_Harbor-1.JPG");
		asset1.setMimeType("image/jpeg");
		asset1.setDeliveryServiceURL("http://mydemoserver.com/adaptivemedia/rendition?id=45fe8cbf156f06d18e293f95eb0c89faba9af16e");
		
		OTMMAsset asset2 = new OTMMAsset("b8fc7a4377053c4cb8c004cef29136cc5db759d4", "Mudbrick-1.JPG");
		asset2.setMimeType("image/jpeg");
		asset2.setDeliveryServiceURL("http://mydemoserver.com/adaptivemedia/rendition?id=b8fc7a4377053c4cb8c004cef29136cc5db759d4");
		
		List<OTMMAsset> assets = new LinkedList<OTMMAsset>();
		assets.add(asset1);
		assets.add(asset2);
		
		String xml = XMLUtil.assetsToXML(assets);
		assertNotNull(xml);
		assertEquals(XML_ASSETS_IN_COLLECTION, xml);		
	}
}
