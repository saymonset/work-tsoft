package com.indeval.portaldali.middleware.services.mercadodinero.test;


import junit.framework.TestCase;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Before;

import com.indeval.portaldali.middleware.dto.MiscelaneaFiscalDTO;
import com.thoughtworks.xstream.XStream;

public class MiscFiscalXmlTest extends TestCase {
	
	
	

	@Before
	public void setUp() throws Exception {
	}
	
	
	public void testXmltoObject(){
		XStream stream = new XStream();
		
		 stream.processAnnotations(MiscelaneaFiscalDTO.class);
		 stream.omitField(MiscelaneaFiscalDTO.class, "idInstitucionCompradora" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "idInstitucionVendedora" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "folioInstitucionCompradora" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "matchRequeridoId" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "folioInstitucionVendedora" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "diasPlazo" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "tipoOperacion" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "tipoValor" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "emisora" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "serie" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "cupon" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "fechaLiquidacion" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "folioUsuario" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "tasaReferencia" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "cantidadTitulosOp" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "cuentaComprador" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "cuentaVendedora" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "origenTransac" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "importe" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "tipoMensaje" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "fechaConcertacion" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "fechaHoraRegistro" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "participanteVO" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "FolioInstitucion" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "IdInstitucion" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "participanteVO" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "funcionDelMensaje" );
		 stream.omitField(MiscelaneaFiscalDTO.class, "mensaje" );		
		
		MiscelaneaFiscalDTO msg=(MiscelaneaFiscalDTO)stream.fromXML(trading);
		
//		System.out.println(" MiscelaneaFiscalDTO ["+ToStringBuilder.reflectionToString(msg)+"]");
		
		
	}
	
		
	
	String trading=
		" <TradingIn>"+
		"   <idInstitucionCompradora>01</idInstitucionCompradora>"+
		"   <idInstitucionVendedora>01</idInstitucionVendedora>"+
		"   <folioInstitucionCompradora>010</folioInstitucionCompradora>"+
		"   <matchRequeridoId>SI</matchRequeridoId>"+
		"   <folioInstitucionVendedora>025</folioInstitucionVendedora>"+
		"   <diasPlazo>0</diasPlazo>"+
		"   <tipoOperacion>M</tipoOperacion>"+
		"   <tipoValor>91</tipoValor>"+
		"   <emisora>CASITA</emisora>"+
		"   <serie>06-2</serie>"+
		"   <cupon>0039</cupon>"+
		"   <fechaLiquidacion>2009-11-11 00:00:00.0 CST</fechaLiquidacion>"+
		"   <folioUsuario>MD09160062</folioUsuario>"+
		"   <tasaReferencia>0</tasaReferencia>"+
		"   <cantidadTitulosOp>4000</cantidadTitulosOp>"+
		"   <cuentaComprador>1305</cuentaComprador>"+
		"   <cuentaVendedora>3004</cuentaVendedora>"+
		"   <origenTransac>03</origenTransac>"+
		"   <importe>0</importe>"+
		"   <tipoMensaje>542</tipoMensaje>"+
		"   <fechaConcertacion>20091111</fechaConcertacion>"+
		"   <fechaHoraRegistro>20091111 111856</fechaHoraRegistro>"+
		"   <participanteVO>"+
		"     <FolioInstitucion>025</FolioInstitucion>"+
		"     <IdInstitucion>01</IdInstitucion>"+
		"   </participanteVO>"+
		"   <funcionDelMensaje>nuevo</funcionDelMensaje>"+
		"   <mensaje>NTQyDQo6MTZSOkdFTkwNCjoyMEM6OlNFTUUvL01EMDkxNjAwNjItTQ0KOjIzRzpORVdNDQo6MTZTOkdFTkwNCjoxNlI6VFJBRERFVA0KOjk0Qjo6VFJBRC8vU0VDTQ0KOjk4QTo6U0VUVC8vMjAwOTExMTENCjo5OEM6OlRSQUQvLzIwMDkxMTExMTMwMDQxDQo6OTBCOjpERUFMLy9BQ1RVL01YTjk3LDgxMjU4MzY2DQo6MzVCOjkxL0NBU0lUQS8wNi0yLzAwMzkNCjoxNlI6RklBDQo6NzBFOjpGSUFOLy8yMDA5MDUwNA0KOjE2UzpGSUENCjoxNlM6VFJBRERFVA0KOjE2UjpGSUFDDQo6MzZCOjpTRVRULy9VTklULzQwMDAsDQo6OTdBOjpTQUZFLy8wMTAyNTMwMDQNCjoxNlM6RklBQw0KOjE2UjpTRVRERVQNCjoyMkY6OlNFVFIvL1RSQUQNCjoxNlI6U0VUUFJUWQ0KOjk1UTo6QlVZUi8vMDEwMTANCjo5N0E6OlNBRkUvLzEzMDUNCjoxNlM6U0VUUFJUWQ0KOjE2UjpTRVRQUlRZDQo6OTVQOjpQU0VULy9JTkRFTVhNTVhYWA0KOjE2UzpTRVRQUlRZDQo6MTZSOlNFVFBSVFkNCjo5NVE6OlJFQUcvLzAxMDI1DQpKT1JHRSBHQVJDSUEgQ0FSUkFOWkENCkdBQ0ozOTA5MjJHWTcNCjo5N0E6OlNBRkUvLzMwMDQNCjoxNlM6U0VUUFJUWQ0KOjE2UzpTRVRERVQNCg==</mensaje>"+
		"   <precioAdquisicion>97.81258366</precioAdquisicion>"+
		"   <fechaAdquisicion>2009-05-04 00:00:00.0 CDT</fechaAdquisicion>"+
		"   <cliente>JORGE GARCIA CARRANZA</cliente>"+
		"   <rfcCurp>GACJ390922GY7</rfcCurp>"+
		" </TradingIn>";	
	
	

}
