package com.opentext.otmm.sc.api.beans;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.opentext.otmm.sc.api.OTMMMetadataElement;

public class TestOTMMFolder {
	private static final String FOLDER_JSON  = 
			"{\"folder_resource\":{\"folder\":{\"metadata\":{\"metadata_element_list\":[{\"id\":\"FNMT.FIELD.TIPO PRODUCTO\",\"type\":\"com.artesia.metadata.MetadataField\",\"value\":{\"value\":{\"type\":\"string\",\"value\":\"tp07\"}}},{\"id\":\"FNMT.FIELD.ID\",\"type\":\"com.artesia.metadata.MetadataField\",\"value\":{\"value\":{\"type\":\"string\",\"value\":\"12345\"}}},{\"id\":\"FNMT.FIELD.NOMBRE\",\"type\":\"com.artesia.metadata.MetadataField\",\"value\":{\"value\":{\"type\":\"string\",\"value\":\"Test-metadato\"}}}]},\"container_type_id\":\"FNMT.FOLDER.MAESTRO\",\"name\":\"Test2\",\"metadata_model_id\":\"FNMT.MODEL.MAESTRO\",\"security_policy_list\":[{\"id\":2},{\"id\":5}]}}}";
	
	@Test
	public void toJSON() {
		OTMMFolder folder = new OTMMFolder("Test2");
		folder.setContainerType("FNMT.FOLDER.MAESTRO");
		folder.addSecurityPolicie(2);
		folder.addSecurityPolicie(5);

		OTMMMetadataElement metadata1 = new OTMMMetadataElement("FNMT.FIELD.TIPO PRODUCTO", "com.artesia.metadata.MetadataField");
		metadata1.addValue("string", "tp07");
		folder.addMetadataElement(metadata1);		
		
		OTMMMetadataElement metadata2 = new OTMMMetadataElement("FNMT.FIELD.ID", "com.artesia.metadata.MetadataField");
		metadata2.addValue("string", "12345");
		folder.addMetadataElement(metadata2);
		
		OTMMMetadataElement metadata3 = new OTMMMetadataElement("FNMT.FIELD.NOMBRE", "com.artesia.metadata.MetadataField");
		metadata3.addValue("string", "Test-metadato");
		folder.addMetadataElement(metadata3);
		
		folder.setMetadataModelId("FNMT.MODEL.MAESTRO");
		
		assertEquals(FOLDER_JSON, folder.toJSON());
	}
}
