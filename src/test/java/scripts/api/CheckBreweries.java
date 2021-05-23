package scripts.api;

import static org.junit.Assert.fail;

import org.json.JSONArray;
import org.json.JSONObject;

import helpers.LogHelper;
import helpers.RestHelper;
import utils.Constants;

public class CheckBreweries {
	RestHelper restHelper;
	LogHelper logger;

	public CheckBreweries(RestHelper restHelper, LogHelper logger) {
		this.restHelper = restHelper;
		this.logger = logger;
	}

	public void runTest() throws Exception {
		// Check pre-conditions
		this.checkPreconditions();

		// Search and validate brewerie
		searchBrewerie("lagunitas");
	}

	// Check pre-conditions
	private void checkPreconditions() {
	}

	// Search and validate brewerie
	private void searchBrewerie(String query) throws Exception {
		JSONArray breweriesResponse = callAPI(Constants.SEARCH_BREWERIES_QUERY.replace(Constants.QUERY_PARAM, query));
		if (breweriesResponse.length() == 0) {
			fail("Se esperaba encontrar al menos una cervecería con el nombre '" + query + "'");
		} else {
			for (int i = 0; i < breweriesResponse.length(); i++) {
				JSONObject brewerie = breweriesResponse.getJSONObject(i);
				if (brewerie.getString(Constants.BREWERIE_NAME).equals("Lagunitas Brewing Co")) {
					JSONArray brewerieResponse = callAPI(Constants.SEARCH_BREWERIE_BY_ID.replace(Constants.ID_PARAM, brewerie.getString(Constants.BREWERIE_ID)));
					checkBrewerie(brewerieResponse.getJSONObject(0));
					break; // Validate ALL data ONLY if them are getting from database, this is an hardcoded example
				}
			}
		}
	}

	// Validate brewerie data
	private void checkBrewerie(JSONObject brewerieResponse) throws Exception {
		validateBrewerieData(Constants.BREWERIE_ID, 
		brewerieResponse.getString(Constants.BREWERIE_ID), "12039");

		validateBrewerieData(Constants.BREWERIE_NAME, 
		brewerieResponse.getString(Constants.BREWERIE_NAME), "Lagunitas Brewing Co");

		validateBrewerieData(Constants.BREWERIE_STREET, 
		brewerieResponse.getString(Constants.BREWERIE_STREET), "2607 W 17th St");

		validateBrewerieData(Constants.BREWERIE_PHONE, 
		brewerieResponse.getString(Constants.BREWERIE_PHONE), "7735222503");

		logger.logInfo("Brewerie 'Lagunitas Brewing Co' validated successfully");
	}

	// Validate brewerie data
	private void validateBrewerieData(String dataType, String result, String expected) {
		if (!result.toUpperCase().equals(expected.toUpperCase())) {
			fail("Se ha producido un error al validar los datos de la cervecería. En el dato '" + dataType + "' se esperaba '" + expected + "' y se encontró '" + result + "'");
		}
	}

	// Call API
	private JSONArray callAPI(String url) {
		return restHelper.sendGET(url, 200);
	}
}
