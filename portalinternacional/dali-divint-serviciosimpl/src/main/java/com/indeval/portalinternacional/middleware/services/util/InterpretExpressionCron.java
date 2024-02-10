/**
 * 
 */
package com.indeval.portalinternacional.middleware.services.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kode
 *
 */
public class InterpretExpressionCron {
	private final String MINUTOS=" minuto(s)";
	private final String HORAS=" hr(s)";
	private final int HRSPM=11;
	private final String WORDINTERVALO="cada ";
	private final String WORDALL="Todos ";
	private final String WORDANY="Cualquier ";

	private final static Logger log = LoggerFactory.getLogger(BitacoraEventosCorporativosUtil.class); 
	
	public String   getHourOfCronExpresion(String expresion)
	{
		
		String[] cron=expresion.split(" ");
		if(cron.length != 7){
			return "Error en cron";
		}
		String minutos="";
		String hrs="";
		String exp=" ";

			minutos = caracteresCronHora(cron[1]);
			hrs = caracteresCronHora(cron[2]);
			
			if(hrs.indexOf(WORDINTERVALO) != -1)
			{				
				exp = hrs+HORAS;			
			}
			else{
				
				if(minutos.indexOf(WORDINTERVALO) != -1)
				{
					exp = minutos+MINUTOS;
				}
				else
				{
						int carValue= Integer.parseInt(hrs);						
						int minValue= Integer.parseInt(minutos);


						if(minValue <= 9)
						{
							minutos = "0"+minutos;
						}
						
						
						if(carValue > HRSPM)
						{
							exp = hrs+":"+minutos+" pm";
						}
						else
						{
							exp = hrs+":"+minutos+" am";
						}			
				}
			}
		
		
		return exp;
	}
	
	
	public String   getTypeOfCronExpresion(String expresion)
	{

		String[] cron=expresion.split(" ");
		String dayMonth="";
		String month="";
		String dayOfWeek=" ";
		
		dayMonth = caracteresCronHora(cron[3]);
		month = caracteresCronHora(cron[4]);
		dayOfWeek = caracteresCronHora(cron[5]);
		
		boolean  dayMonthBoolean=true;
		boolean  dayOfWeekBoolean=true;
			
			
		if(dayMonth.indexOf(WORDINTERVALO) != -1)
		{				
			dayMonth= dayMonth+" d\u00EDa(s)";	
			dayMonthBoolean=false;
		}		
		if(dayMonth.indexOf(WORDALL) != -1)
		{				
			dayMonth= dayMonth+" los dias";
			dayMonthBoolean=false;
		}
		if(dayMonth.indexOf(WORDANY) != -1)
		{				
			dayMonth= dayMonth+" d\u00EDa";	
			dayMonthBoolean=false;
		}
		
		if(month.indexOf(WORDINTERVALO) != -1)
		{				
			month= month+" mes(es)";
		}		
		if(month.indexOf(WORDALL) != -1)
		{				
			month= month+" los meses ";
		}
		
		if(dayOfWeek.indexOf(WORDINTERVALO) != -1)
		{				
			dayOfWeek= dayOfWeek+" de la semana";
			dayOfWeekBoolean=false;
		}		
		if(dayOfWeek.indexOf(WORDALL) != -1)
		{				
			dayOfWeek= dayOfWeek+" los d\u00EDas  semana";
			dayOfWeekBoolean=false;
		}
		if(dayOfWeek.indexOf(WORDANY) != -1)
		{				
			dayOfWeek= dayOfWeek+"d\u00EDa de la semana";
			dayOfWeekBoolean=false;
		}
		
		String	tmp= (dayMonthBoolean==false?dayMonth:"D\u00EDa:"+dayMonth)+" de "+month+"  , en "+(dayOfWeekBoolean==false?dayOfWeek:" en los d\u00EDas:"+getDias(dayOfWeek));
		
		
		if(dayMonthBoolean==true && dayOfWeekBoolean==false)
		{
			tmp= (dayMonthBoolean==false?dayMonth:"D\u00EDa:"+dayMonth)+" de "+month;
			
		}
		if(dayMonthBoolean==false && dayOfWeekBoolean==true)
		{
			tmp= month+"  , "+(dayOfWeekBoolean==false?dayOfWeek:" en los d\u00EDas:"+getDias(dayOfWeek));		
		}
		if(dayMonthBoolean==false && dayOfWeekBoolean==false)
		{
			tmp= dayMonth+" de "+month;		
		}
		return tmp;
	}
	
	
	public String getDias(String diasParam)
	{
		String dias="";

		String[] diasWordEn={"SUN","MON","TUE","WED","THU","FRI","SAT"};
		String[] diasWordEs={"Domingo","Lunes","Martes","Mi\u00E9rcoles","Jueves","Viernes","Sabado"};
		log.info("parametro dias:"+diasParam);
		int cont=0;
		String[] diasSelec={diasParam};

		
		if(diasParam.indexOf(",") !=- 1)
		{
			diasSelec=diasParam.split(",");		
		}
		if(diasParam.indexOf("-") !=- 1)
		{
			String[] intervalosGuion = diasParam.split("-");
			
			String iniIntervalo=intervalosGuion[0];
			String finIntervalo=intervalosGuion[1];
		
				List<String> listIntervalo= new ArrayList<String>();
				boolean inicioTrue=false;
				days:
					for (String diasVec : diasWordEn)
					{
						if(diasVec.equals(iniIntervalo))
						{
							inicioTrue=true;
						}
					
						if(inicioTrue)
						{
							listIntervalo.add(diasVec);
						}
						if(diasVec.equals(finIntervalo))
						{
							break days;
						}

						cont++;	
					}
				 diasSelec = new String[listIntervalo.size()];
				 diasSelec = listIntervalo.toArray(diasSelec);

			
		}

		for (String dia : diasSelec)
		{
			cont=0;
			days:
				for (String diasVec : diasWordEn)
				{
					if(dia.equals(diasVec))
					{
						dias+=diasWordEs[cont]+" ";
						break days;
					}
					cont++;	
				}		
		}
				

		
		return dias;
		
	}
	public String caracteresCronHora(String car )
	{
		String expUsuario="";
		String palabraInicial=WORDINTERVALO;
		
		int posDiagonal=car.indexOf("/");
		String[] expCadaSplit= new String[2];
		
		if(posDiagonal != -1)
		{	
			expCadaSplit=car.split("/");
			expUsuario=palabraInicial+expCadaSplit[1];
		}
		else
		{
			if(car.equals("*"))
			{
				expUsuario=WORDALL;				
			}
			else
			{
				if(car.equals("?"))
				{
					expUsuario=WORDANY;				
				}
				else
				{
					expUsuario=car;
				}
			}
		}
		
		return expUsuario;
	}
}
