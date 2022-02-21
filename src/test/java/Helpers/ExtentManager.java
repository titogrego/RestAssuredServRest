package Helpers;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.File;

public class ExtentManager {
    private static final ExtentReports extentReports = new ExtentReports();

    public synchronized static ExtentReports getExtentReports() {
        String customerCode= System.getProperty("customerCode");

        if(customerCode==null){
           customerCode = "Desenvolvimento";
        }else{
            if (customerCode.equals("HML")) {
                customerCode = "Homologação";
            }
            if (customerCode.equals("DEV")) {
                customerCode = "Desenvolvimento";
            }
        }




        String pathFile =  System.getProperty("user.dir")+ File.separator +"TestReport"+ File.separator +"relatorioTest.html";
        ExtentSparkReporter reporter = new ExtentSparkReporter(pathFile);
        String css ="#img {\n" +
                "    width: 120px;\n" +
                "    height: 35px;\n" +
                "    float: left;\n" +
                "    position: absolute;\n" +
                "    margin-left: -40%;\n" +
                "    margin-top: -3%;\n" +
                "}";

        String js = " $(document).ready(function() {\n" +


                "$('link[rel=\"shortcut icon\"]').attr('href','https://static.wixstatic.com/media/ca9c70_a2c1081b24b94550ae6394e3bf1fd7dd%7Emv2.png/v1/fill/w_32%2Ch_32%2Clg_1%2Cusm_0.66_1.00_0.01/ca9c70_a2c1081b24b94550ae6394e3bf1fd7dd%7Emv2.png');\n" +
                "$('.logo').attr('style','background-image: url(\"https://static.wixstatic.com/media/ca9c70_a2c1081b24b94550ae6394e3bf1fd7dd%7Emv2.png/v1/fill/w_32%2Ch_32%2Clg_1%2Cusm_0.66_1.00_0.01/ca9c70_a2c1081b24b94550ae6394e3bf1fd7dd%7Emv2.png\")');\n"+
                "$('.nav-logo > a').attr('href','https://www.frameworkdigital.com.br/');\n"+
                "$('.nav-logo > a').attr('target','_blank');\n"+
                "        });";

        reporter.config().setDocumentTitle("Teste de API Restassured");
        reporter.config().setEncoding("utf-8");
        reporter.config().setReportName(
                "<img id='img' src='https://static.wixstatic.com/media/ca9c70_a667af8468be4f52a1a22b32c9d9c9c4~mv2.png/v1/crop/x_149,y_232,w_549,h_107/fill/w_195,h_33,al_c,q_85,usm_0.66_1.00_0.01/Logo_Roxa-02.webp' />\n" +
                        "\t<span>Relat&oacute;rio de execu&ccedil;&atilde;o dos testes</span>\n" );
        reporter.config().setJs(js);
        reporter.config().setCss(css);
        reporter.config().setTheme(Theme.DARK);
        String OS = System.getProperty("os.name");
        extentReports.attachReporter(reporter);
        extentReports.setSystemInfo("Ambiente de teste", customerCode);
        extentReports.setSystemInfo("Autor", "Tito Neto");

        extentReports.setSystemInfo("Sistema Operacional", OS);
        return extentReports;
    }
}