package com.opentext.otmm.sc.api.beans;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.opentext.otmm.sc.api.OTMMAPIFolders;
import com.opentext.otmm.sc.api.OTMMMetadataElement;

public class TestOTMMFolder {
	private static final String FOLDER_JSON  = 
			"{\n" +
			"    \"folder_resource\": {\n" +
			"        \"folder\": {\n" +
			"            \"name\": \"Test2\",\n" +
			"            \"container_type_id\": \"FNMT.FOLDER.MAESTRO\",\n" +
			"            \"security_policy_list\": [{\n" +
			"                    \"id\": 5\n" +
			"                }, {\n" +
			"                    \"id\": 2\n" +
			"                }\n" +
			"            ],\n" +
			"            \"metadata\": {\n" +
			"                \"metadata_element_list\": [{\n" +
			"                        \"id\": \"FNMT.FIELD.TIPO PRODUCTO\",\n" +
			"                        \"type\": \"com.artesia.metadata.MetadataField\",\n" +
			"                        \"value\": {\n" +
			"                            \"value\": {\n" +
			"                                \"type\": \"string\",\n" +
			"                                \"value\": \"tp07\"\n" +
			"                            }\n" +
			"                        }\n" +
			"                    }, {\n" +
			"                        \"id\": \"FNMT.FIELD.ID\",\n" +
			"                        \"type\": \"com.artesia.metadata.MetadataField\",\n" +
			"                        \"value\": {\n" +
			"                            \"value\": {\n" +
			"                                \"type\": \"string\",\n" +
			"                                \"value\": \"12345\"\n" +
			"                            }\n" +
			"                        }\n" +
			"                    }, {\n" +
			"                        \"id\": \"FNMT.FIELD.NOMBRE\",\n" +
			"                        \"type\": \"com.artesia.metadata.MetadataField\",\n" +
			"                        \"value\": {\n" +
			"                            \"value\": {\n" +
			"                                \"type\": \"string\",\n" +
			"                                \"value\": \"Test-metadato\"\n" +
			"                            }\n" +
			"                        }\n" +
			"                    }\n" +
			"                ]\n" +
			"            },\n" +
			"            \"metadata_model_id\": \"FNMT.MODEL.MAESTRO\"\n" +
			"        }\n" +
			"    }\n" +
			"}";
	
	@Test
	public void toJSON() {
		OTMMFolder folder = new OTMMFolder("123456", "Test2");
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
