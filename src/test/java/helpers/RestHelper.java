package helpers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RestHelper {
	LogHelper logger;
	
	public RestHelper() {
		logger = new LogHelper();
	}

	public JSONArray sendPOST(String url, Map<String, String> request, int expectedHttpCode) {
		try {
			URL endpoint = new URL(url);
			URLConnection conn = endpoint.openConnection();
			HttpURLConnection http = (HttpURLConnection) conn;
			http.setRequestMethod("POST");
			http.setDoOutput(true);
			byte[] out = new JSONObject(request).toString().getBytes(StandardCharsets.UTF_8);
			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			http.connect();
			try (OutputStream os = http.getOutputStream()) {
				os.write(out);
			}
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
        	for (int c; (c = reader.read()) >= 0;) {
				sb.append((char)c);
			}
			if (http.getResponseCode() == expectedHttpCode) {
				try {
					JSONObject jsonObject = new JSONObject(sb.toString());
					JSONArray jsonArray = new JSONArray();
					jsonArray.put(jsonObject);
					return jsonArray;
				} catch (JSONException err) {
					return new JSONArray(sb.toString());
				} catch (Exception err) {
					throw err;
				}
			} else {
				throw new Exception("El código HTTP no coincide con el resultado esperado. Se esperaba el código " + expectedHttpCode + " y se obtuvo el código " + http.getResponseCode());
			}
		} catch (Exception err) {
			logger.logError("Se ha producido un error al invocar el endpoint '" + url + "': " + err.getMessage());
		}
		return null;
	}

	public JSONArray sendGET(String url, int expectedHttpCode) {
		try {
			URL endpoint = new URL(url);
			URLConnection conn = endpoint.openConnection();
			HttpURLConnection http = (HttpURLConnection) conn;
			http.setRequestMethod("GET");
			http.connect();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
        	for (int c; (c = reader.read()) >= 0;) {
				sb.append((char)c);
			}
			if (http.getResponseCode() == expectedHttpCode) {
				try {
					JSONObject jsonObject = new JSONObject(sb.toString());
					JSONArray jsonArray = new JSONArray();
					jsonArray.put(jsonObject);
					return jsonArray;
				} catch (JSONException err) {
					return new JSONArray(sb.toString());
				} catch (Exception err) {
					throw err;
				}
			} else {
				throw new Exception("El código HTTP no coincide con el resultado esperado. Se esperaba el código " + expectedHttpCode + " y se obtuvo el código " + http.getResponseCode());
			}
		} catch (Exception err) {
			logger.logError("Se ha producido un error al invocar el endpoint '" + url + "': " + err.getMessage());
		}
		return null;
	}
}