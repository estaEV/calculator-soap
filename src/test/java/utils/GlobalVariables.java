package utils;

public interface GlobalVariables {
    public static final String BASE_URL = "http://www.dneonline.com/";
    public static final String BASE_PATH = "calculator.asmx";

    //Available operations: Subtract, Add, Multiply, Divide
    String myEnvelope = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempuri.org/\">\n" +
            "   <soap:Header/>\n" +
            "   <soap:Body>\n" +
            "      <tem:${operation}>\n" +
            "         <tem:intA>${var1}</tem:intA>\n" +
            "         <tem:intB>${var2}</tem:intB>\n" +
            "      </tem:${operation}>\n" +
            "   </soap:Body>\n" +
            "</soap:Envelope>";
}
