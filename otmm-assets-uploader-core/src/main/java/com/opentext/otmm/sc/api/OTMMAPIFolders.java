package com.opentext.otmm.sc.api;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.opentext.otmm.sc.api.beans.OTMMAsset;
import com.opentext.otmm.sc.api.beans.OTMMFolder;

public class OTMMAPIFolders extends OTMMAPI {

	private static final String POST__CREATE_A_FOLDER = "folders/{id}";
	private static final String GET__RETRIEVE_ALL_ROOT_FOLDERS = "folders/rootfolders";
	private static final String GET__RETRIEVE_ALL_CHILDREN_OF_A_FOLDER = "folders/{id}/children";

	public OTMMAPIFolders(String urlBase) {
		super(urlBase);
	}

	public OTMMAPIFolders(String urlBase, String version) {
		super(urlBase, version);
	}

	public OTMMAPIFolders(String urlBase, int version) {
		super(urlBase, version);
	}

	/**
	 * <strong>Create a Folder</strong> Create a sub-folder under the specified
	 * Folder. In the supplied FolderRepresentation, one needs to set only folder
	 * name, folder type id, metadata and security policies. In metadata, set only
	 * field id and value. <strong>Method</strong>: POST <strong>URL</strong>:
	 * /v6/folders/{id}
	 * 
	 * @param sessionId - Session identifier (provided by `sessions` method)
	 * @see https://www.baeldung.com/httpclient-post-http-request#post-with-json
	 */
	public OTMMFolder createAFolder(String sessionId, String parentFolderId, OTMMFolder folder) {
		
		if (folder != null) {
			
			StringEntity entity = null;
			entity = new StringEntity(folder.toJSON(), ContentType.APPLICATION_JSON);

			if (entity != null) {
				List<HttpEntity> entities = new LinkedList<HttpEntity>();
				entities.add(entity);

				String response = post(POST__CREATE_A_FOLDER.replace("{id}", parentFolderId),
						getDefaultHeaders(sessionId), 
						entities);

				if (response != null) {
					try {
						JSONObject json = new JSONObject(response);
						if (json != null) {
							// TODO add all the properties to the OTMMFolder object

							JSONObject metadata = json.getJSONObject("folder_resource").getJSONObject("folder")
									.getJSONObject("asset_content_info").getJSONObject("master_content_info")
									.getJSONObject("metadata");

							if (metadata != null) {
								folder = new OTMMFolder(metadata.getString("id"), metadata.getString("name"),
										metadata.getString("type"));
							}
						}
					} catch (JSONException e) {
						logger.error("/otmmapi/v" + version + "/folders/{id} (Response to JSON conversion) ", e);
						folder = null;
					}
				}
			}
		}
		return folder;
	}

	/**
	 * Retrieve all Root Folders <strong>Method</strong>: GET <strong>URL</strong>:
	 * /v6/folders/rootfolders
	 * 
	 * @param sessionId - Session identifier (provided by `sessions` method)
	 * @see https://developer.opentext.com/apis/14ba85a7-4693-48d3-8c93-9214c663edd2/06c4a79f-3f4a-4a5a-aab9-9519740b27c7/1d6ec9c5-7620-456e-b52f-cfffb2734eb0#operation/getRootFolders
	 */
	public List<OTMMAsset> retrieveAllRootFolders(String sessionId) {
		List<OTMMAsset> assets = null;

		String response = get(GET__RETRIEVE_ALL_ROOT_FOLDERS, getDefaultHeaders(sessionId));

		if (response != null) {
			try {
				JSONObject json = new JSONObject(response);
				if (json != null) {
					assets = new LinkedList<OTMMAsset>();

					JSONArray jsonArray = json.getJSONObject("folders_resource").getJSONArray("folder_list");

					JSONObject colObj = null;
					String id, name = null;
					for (int i = 0, size = jsonArray.length(); i < size; i++) {
						colObj = (JSONObject) jsonArray.get(i);
						id = colObj.getString("asset_id");
						name = colObj.getString("name");

						// TODO add all the properties to the OTMMAsset object

						OTMMAsset asset = new OTMMAsset(id, name);

						assets.add(asset);
					}
				}
			} catch (JSONException e) {
				logger.error("/otmmapi/v" + version + "/folders/rootfolders (Response to JSON conversion) ", e);
			}
		}

		return assets;
	}

	/**
	 * Retrieve all children (Assets and Folders) of a Folder
	 * <strong>Method</strong>: GET <strong>URL</strong>: /v6/folders/{id}/children
	 * 
	 * @param sessionId - Session identifier (provided by `sessions` method)
	 * @see https://developer.opentext.com/apis/14ba85a7-4693-48d3-8c93-9214c663edd2/06c4a79f-3f4a-4a5a-aab9-9519740b27c7/1d6ec9c5-7620-456e-b52f-cfffb2734eb0#operation/getChildren
	 */
	public List<OTMMAsset> retrieveAllChildrenOfAFolder(String sessionId, String folderId) {
		List<OTMMAsset> assets = null;

		String response = get(GET__RETRIEVE_ALL_CHILDREN_OF_A_FOLDER.replace("{id}", folderId),
				getDefaultHeaders(sessionId));

		if (response != null) {
			try {
				JSONObject json = new JSONObject(response);
				if (json != null) {
					assets = new LinkedList<OTMMAsset>();

					JSONArray jsonArray = json.getJSONObject("folder_children").getJSONArray("asset_list");

					JSONObject colObj = null;
					String id, name = null;
					for (int i = 0, size = jsonArray.length(); i < size; i++) {
						colObj = (JSONObject) jsonArray.get(i);
						id = colObj.getString("asset_id");
						name = colObj.getString("name");

						// TODO add all the properties to the OTMMAsset object
						// TODO refactor: extract a method to generate the asset list

						OTMMAsset asset = new OTMMAsset(id, name);

						assets.add(asset);
					}
				}
			} catch (JSONException e) {
				logger.error(
						"/otmmapi/v" + version + "/folders/" + folderId + "/childrens (Response to JSON conversion) ",
						e);
			}
		}

		return assets;
	}
}
