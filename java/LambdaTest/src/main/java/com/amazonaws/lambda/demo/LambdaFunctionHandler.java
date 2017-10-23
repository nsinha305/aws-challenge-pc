package com.amazonaws.lambda.demo;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class LambdaFunctionHandler implements RequestHandler<MyLambdaRequest, MyLambdaResponse> {

	@Override
	public MyLambdaResponse handleRequest(MyLambdaRequest input, Context context) {
		context.getLogger().log("Input: " + input);
		MyLambdaResponse lambdaResponse = new MyLambdaResponse();
		String query = "";
		String url = "https://search-nsinha-pc-6x4ctzvktckl5u3rbktxjduoj4.us-east-2.es.amazonaws.com/form/_search?q=";
		try {
			query = getQueryString(input);
			String response = ClientBuilder.newClient().target(url + query + "&pretty=true&size=1000").request()
					.accept(MediaType.APPLICATION_JSON).get(String.class);
			JSONObject jResponse = (new JSONObject(response)).getJSONObject("hits");
			lambdaResponse.setCount(jResponse.getJSONArray("hits").length());
			lambdaResponse.setResponseResult(jResponse.getJSONArray("hits").toString(3));
		} catch (Exception e) {
			e.printStackTrace();
		}
		context.getLogger().log("Response : " + lambdaResponse);
		return lambdaResponse;
	}

	private String getQueryString(MyLambdaRequest input) throws JSONException {
		String query = "";
		query += input.getParamName()+"%3A%22"+input.getParamValue()+"%22";
		return query;
	}
}