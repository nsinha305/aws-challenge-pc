package com.amazonaws.lambda.demo;

import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.amazonaws.services.lambda.runtime.Context;

/**
 * A simple test harness for locally invoking your Lambda function handler.
 */
public class LambdaFunctionHandlerTest {

    private static MyLambdaRequest input;

    @BeforeClass
    public static void createInput() throws IOException {
    	input = new MyLambdaRequest();
        input.setParamName("SPONSOR_DFE_NAME");
        input.setParamValue("BADGER%20MINING%20CORPORATION");
    }

    private Context createContext() {
        TestContext ctx = new TestContext();

        // TODO: customize your context here if needed.
        ctx.setFunctionName("MyFunction");

        return ctx;
    }

    @Test
    public void testLambdaFunctionHandler() {
        LambdaFunctionHandler handler = new LambdaFunctionHandler();
        Context ctx = createContext();

        MyLambdaResponse output = handler.handleRequest(input, ctx);
        Assert.assertEquals(output.getCount(), 2);
        // TODO: validate output here if needed.
        //Assert.assertEquals("Hello from Lambda!", output);
    }
}
