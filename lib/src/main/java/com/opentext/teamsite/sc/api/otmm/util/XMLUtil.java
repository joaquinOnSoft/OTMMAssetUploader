package com.opentext.teamsite.sc.api.otmm.util;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.opentext.teamsite.sc.api.otmm.beans.OTMMAsset;
import com.opentext.teamsite.sc.api.otmm.beans.OTMMCollection;

public class XMLUtil {
	static final Logger logger = LogManager.getLogger(XMLUtil.class);

	public static String docToXML(Document doc) {
		String xmlString = null;

		return xmlString;
	}

	/**
	 * 
	 * @param otmmCollection
	 * @return
	 * @see https://www.tutorialspoint.com/java_xml/java_dom4j_create_document.htm
	 */
	public static Document assetsToDoc(List<OTMMAsset> assets) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("collection");

		for (OTMMAsset asset: assets) {
			Element assetElement = root.addElement("asset");
			assetElement.addElement("id").addText( asset.getId());			
			assetElement.addElement("name").addText(asset.getName());
			assetElement.addElement("mimeType").addText(asset.getMimeType());
			assetElement.addElement("deliveryServiceURL").addText(asset.getDeliveryServiceURL());
		}

		return doc;
	}

	/**
	 * Convert a list of OTMM assets to a XML string
	 * 
	 * @param assets - OTMM assets
	 * @return XML string
	 */
	public static String assetsToXML(List<OTMMAsset> assets) {
		String xmlString = null;
		
		Document doc = assetsToDoc(assets);
		if(doc != null) {
			xmlString = doc.asXML();
		}
		
		return xmlString;
	}

	/**
	 * 
	 * @param otmmCollections
	 * @return
	 * @see https://www.tutorialspoint.com/java_xml/java_dom4j_create_document.htm
	 */
	public static Document otmmCollectionsToDoc(Map<String, OTMMCollection> otmmCollections) {
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("collections");
		
		OTMMCollection collection = null;		
		for (String collectionId : otmmCollections.keySet()) {
			collection = otmmCollections.get(collectionId);		
			
			Element collectionElement = root.addElement("collection");
			collectionElement.addElement("id").addText( collection.getId());			
			collectionElement.addElement("name").addText(collection.getName());
			collectionElement.addElement("ownerName").addText(collection.getOwnerName());							
		}
		
		return doc;
	}

	/**
	 * 
	 * @param otmmCollections
	 * @return
	 */
	public static String otmmCollectionsToXML(Map<String, OTMMCollection> otmmCollections) {
		String xmlString = null;

		Document doc = otmmCollectionsToDoc(otmmCollections);
		if(doc != null) {
			xmlString = doc.asXML();
		}
						
		return xmlString;
	}
}
