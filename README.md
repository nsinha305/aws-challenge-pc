# aws-challenge-pc

Problem Statement : 

Using Java, write a micro service that invokes AWS elastic search and make it available using API gateway. 

Given the test data provided, the search should be allowed by Plan name, Sponsor name and Sponsor State.

Steps Followed:

1. Create a domain in AWS Elastic Search
2. Create the index using index.sh
3. Given the layout text file provided, run ConvertLayoutToJSON.java to get the mapping for the index in JSON. For the fields PLAN_NAME, SPONSOR_DFE_NAME, and SPONS_DFE_MAIL_US_STATE, I added the parameter "index" : "not_analyzed", to ensure an exact match on search.
4. Create the mapping by running mapping.sh
5. Given the csv, run ConvertDataToJSON.java, to create 56 json files comprising of the data in the csv. 56 files were created because the data upload had to be done in chunks.
6. Upload data by running upload_data.sh
7. Create a Lambda Function - in the java/LambdaTest Project to consume a parameter value and name and query the data.
8. Expose that Lambda Function as an API from the API Gateway. The method type is GET and the parameters are passed via path-parameters

URL for Search

1. Search by plan-name
   https://wnhdagx2q6.execute-api.us-east-2.amazonaws.com/prod/getData/plan-name/{plan-name}
   For example : https://wnhdagx2q6.execute-api.us-east-2.amazonaws.com/prod/getData/plan-name/BADGER%20MINING%20CORPORATION%20HEALTH%20PLAN

2. Search by sponsor-name
   https://wnhdagx2q6.execute-api.us-east-2.amazonaws.com/prod/getData/sponsor-name/{sponsor-name}
   For example : https://wnhdagx2q6.execute-api.us-east-2.amazonaws.com/prod/getData/sponsor-name/BADGER%20MINING%20CORPORATION

3. Search by sponsor-state
   https://wnhdagx2q6.execute-api.us-east-2.amazonaws.com/prod/getData/sponsor-state/{sponsor-state}
   For example : https://wnhdagx2q6.execute-api.us-east-2.amazonaws.com/prod/getData/sponsor-state/CA