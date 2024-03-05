package com.indeval.dali.tests;


import static org.jboss.arquillian.graphene.Graphene.guardAjax;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.graphene.page.Location;
import org.jboss.arquillian.graphene.page.Page;
import org.jboss.arquillian.testng.Arquillian;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.csvreader.CsvReader;
import com.indeval.dali.dao.BitacoraOperacionesDao;
import com.indeval.dali.dao.CuentaNombradaDao;
import com.indeval.dali.dao.InstitucionDao;
import com.indeval.dali.dao.PosicionNombradaDao;
import com.indeval.dali.pages.HeaderPage;
import com.indeval.dali.pages.LoginPage;

public abstract class BaseTest extends Arquillian{

	@Drone
	protected WebDriver browser;
	
	@Page
	private LoginPage loginPage;
	
	@Page
	private HeaderPage headerPage;
	
	private ApplicationContext applicationContext; 
	
	private Properties daliProperties;
	
	private CuentaNombradaDao cuentaNombradaDao;
	
	private InstitucionDao institucionDao;
		
	private PosicionNombradaDao posicionNombradaDao;
	
	private BitacoraOperacionesDao bitacoraOperacionesDao;
	
	private boolean loginOk=false;
	
	@BeforeMethod
	public void initTest(){
		if(!loginOk){
			login(getProperty("usuario"),getProperty("password"));
			loginOk=true;
		}
	}
	
	@BeforeClass
	public void init(){
		
		
		try {

			BufferedReader in = new BufferedReader(
					   new InputStreamReader(
			                      new FileInputStream(this.getClass().getClassLoader().getResource("DaliTest.properties").getPath()), "UTF8"));
			if(daliProperties==null)
				daliProperties=new Properties();
			// load a properties file
			daliProperties.load(in);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} 
		
		
		applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		cuentaNombradaDao=(CuentaNombradaDao)applicationContext.getBean("cuentaNombradaDao");
		
		institucionDao=(InstitucionDao)applicationContext.getBean("institucionDao");
		
		posicionNombradaDao=(PosicionNombradaDao)applicationContext.getBean("posicionNombradaDao");
		
		bitacoraOperacionesDao=(BitacoraOperacionesDao)applicationContext.getBean("bitacoraOperacionesDao");
		
	}
	
	
	
	
	
	public void goTo(Class<?> clase){
		Location location=clase.getAnnotation(Location.class);
		browser.get(getProperty("contextRoot")+"/"+getProperty("contextPath")+"/"+location.value());
	}
	
	
	
	public String getProperty(String key){
		return daliProperties.getProperty(key);
	}
	
	
	public String login(String usuario,String password){
		
			goTo(LoginPage.class);

			String mensaje=loginPage.login(usuario, password);

			
			return mensaje;
		
	}
	
	public String loginMenssagesError(String usuario,String password){
		
		goTo(LoginPage.class);

		String mensaje=loginPage.loginMessageError(usuario, password);

		
		return mensaje;
	
	}
	
	
	public void logout(){

		goTo(HeaderPage.class);
		
		headerPage.getBtnLogout().click();
		
		
	}
	
	public void setFechaCalendario(WebElement componenteFecha,Calendar fecha){
		
		
		int semana=fecha.get(Calendar.WEEK_OF_MONTH);
		int diaSemana=fecha.get(Calendar.DAY_OF_WEEK);
		
		WebElement dia=componenteFecha.findElement(By.xpath(".//table/tbody/tr["+(3+semana)+"]/td["+diaSemana+"]"));
		
		guardAjax(dia).click();
		
	}
	
	
	
	/**
	 * Metodo privado que suma un dia habil a la fecha actual y regresa la fecha
	 * Vencimiento.
	 *
	 * @return	Fecha Vencimiento
	 */
	protected Date obtenerFechaVencimiento() {

		Calendar fecha = Calendar.getInstance();
		fecha.setTime(new Date());
		int diaSem = fecha.get(Calendar.DAY_OF_WEEK);
		if (diaSem == 6) {
			fecha.add(Calendar.DATE, 3);
		} else {
			fecha.add(Calendar.DATE, 1);
		}
		Date fechaVencimiento = fecha.getTime();

		return fechaVencimiento;
	}

	
	protected void esperarDescargaArchivo(File file){
		int i=0;
		while(!file.exists()&&i<10){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(file.exists()){
				break;
			}
			i++;
		}
	}
	
	
	protected String[][] getDataPool(String fileName) throws IOException {
		List<List<String>> arrList = new ArrayList<List<String>>();
		URL url = this.getClass().getClassLoader().getResource(fileName);
		File file = new File(url.getPath());
		CsvReader csvReader = new CsvReader(new InputStreamReader(new FileInputStream(file.getPath()), "UTF-8"));							
		csvReader.readHeaders();
		
		// Obtener los datos del archivo *.csv y ponerlos en una lista de listas
		while(csvReader.readRecord()){
			
			List<String> list = new ArrayList<String>();
			
			for(String header:csvReader.getHeaders()){
				list.add(csvReader.get(header));
			}
			
			

			arrList.add(list);
		}
		csvReader.close();
		// Declarar y rellenar un arreglo bidimencional con el tamaño de la lista para su uso en las pruebas.
		String[][] data = getDatapoolAssArray(arrList);

		return data;        
	}
	
	
	private String[][] getDatapoolAssArray(List <List<String>> arrList){
		String[][] dp = new String[arrList.size()][arrList.get(0).size()];
		for(int i=0; i<arrList.size();i++){
			for(int j=0; j<arrList.get(i).size(); j++){
				dp[i][j] = arrList.get(i).get(j);
			}
		}
		return dp;
	}
	
	public void forceSendKeys(WebElement element, String text) throws InterruptedException{
		if (element != null){
			// Se tiene que poner un delay para que el navegador reaccione ante los caracteres
			Thread.sleep(100);
			element.clear();
			Thread.sleep(100);
			JavascriptExecutor js=(JavascriptExecutor)browser;
			try{
				js.executeScript("arguments[0].value=arguments[1]", element, text);
				Thread.sleep(100);
				js.executeScript("arguments[0].focus;arguments[0].onchange();return true;",element);
				Thread.sleep(100);
			}catch(WebDriverException wde){
				System.err.println("Ocurrio un error al ejecutar comando js:"+wde);
			}
		}
	}
	
	public void selectOption(Select select, String value){
		
		List<WebElement> options=select.getOptions();
		
		for( int i=0; i<options.size(); i++){
			
			if(options.get(i).getText().startsWith(value)){
				select.selectByIndex(i);			
				break;
			}
		}
		
	}
	
	
	public String getValueByRow(WebElement resultado,int row, int col){
		try{			
			if(resultado!=null){
				List<WebElement> tableRows=resultado.findElements(By.tagName("tr"));
				if(tableRows!=null){
					List<WebElement> tableCols=tableRows.get(row).findElements(By.tagName("td"));
					return tableCols.get(col).getText().replaceAll("\\$ ","").replaceAll(",","").trim();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	public void cambiarInstitucion(String institucion) throws InterruptedException{
		
		goTo(HeaderPage.class);
		
		headerPage.cambiarInstitucion(institucion);
	}
	
	public InstitucionDao getInstitucionDao() {
		return institucionDao;
	}
	
	public PosicionNombradaDao getPosicionNombradaDao() {
		return posicionNombradaDao;
	}
	
	public CuentaNombradaDao getCuentaNombradaDao() {
		return cuentaNombradaDao;
	}
	
	public BitacoraOperacionesDao getBitacoraOperacionesDao() {
		return bitacoraOperacionesDao;
	}
	
	public void setBrowser(WebDriver browser) {
		this.browser = browser;
	}
	
	public void setHeaderPage(HeaderPage headerPage) {
		this.headerPage = headerPage;
	}
	
	public HeaderPage getHeaderPage() {
		return headerPage;
	}
	
	public WebDriver getBrowser() {
		return browser;
	}
}
