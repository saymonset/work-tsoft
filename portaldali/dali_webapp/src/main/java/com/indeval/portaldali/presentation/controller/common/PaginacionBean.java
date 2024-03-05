/**
 * CMMV - Portal Dali
 * Copyrigth (c) 2017 CMMV. All Rights Reserved.
 */
package com.indeval.portaldali.presentation.controller.common;

import com.indeval.portaldali.middleware.services.modelo.PaginaVO;

/**
 * Manage bean con los metodos necesarios para la paginacion de resultados de consultas.
 * 
 * @author Pablo Balderas
 */
public class PaginacionBean extends ControllerBase {

	/** Objeto para la paginacion */
	protected PaginaVO paginaVO = new PaginaVO();
	
    /** El numero de pagina actual de la consulta */
    private int numeroPagina = -1;

    /** La cantidad de registros que seran presentados en una pagina de resultados */
    private int registrosPorPagina = -1;

    /** El total de paginas de resultados productos de la consulta */
    private int totalPaginas = -1;   
	
	
    /**
     * Metodo base para poder ejecutar la consulta
     */
    public String ejecutarConsulta() {
        return null;
    }
    
    /**
     * Actualiza la informacion de la paginacion al avanzar el numero de paginas solicitadas.
     * @param paginas el numero de paginas a avanzar.
     */
    public void avanzar(int paginas) {
        int numeropPag = getNumeroPagina();
        if (paginaVO.getTotalRegistros() > 0) {
            if (numeropPag + paginas > getTotalPaginas()) {
                numeropPag = getTotalPaginas();
            }
            else {
                numeropPag += paginas;
            }
            paginaVO.setOffset((numeropPag - 1) * paginaVO.getRegistrosXPag() + 0);
        }
        ejecutarConsulta();

    }

    /**
     * Actualiza la informacion de la paginacion al retroceder el numero de paginas solicitadas.
     * @param paginas el numero paginas a retroceder.
     */
    public void retroceder(int paginas) {
        int numeropPag = getNumeroPagina();
        if (paginaVO.getTotalRegistros() > 0) {
            if ((numeropPag - paginas) < 1) {
                numeropPag = 1;
            }
            else {
                numeropPag -= paginas;
            }
        }
        paginaVO.setOffset(((numeropPag-1) * paginaVO.getRegistrosXPag()));
        ejecutarConsulta();
    }

    /**
     * Actualiza la informacion de la paginacion al establecer la primera pagina de resultados como la pagina actual.
     */
    public String irPrimeraPagina() {
        if (paginaVO.getTotalRegistros() > 0) {
            numeroPagina = 1;
        }
        paginaVO.setOffset(0);
        ejecutarConsulta();
        return null;
    }

    /**
     * Actualiza la informacion de la paginacion al establecer la ultima pagina de resultados como la pagina actual.
     */
    public String irUltimaPagina() {
        getNumeroPagina();
        if (paginaVO.getTotalRegistros() > 0) {
            paginaVO.setOffset((getTotalPaginas() - 1) * paginaVO.getRegistrosXPag() + 0);
        }
        ejecutarConsulta();
        return null;
    }

    /**
     * Indica si se muestra el boton de ir al primer registro
     * @return true si muestra el boton; false en caso contrario
     */
    public boolean isIrAlPrimero() {
        boolean resultado = false;
        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() > 1) {
                resultado = true;
            }
        }
        return resultado;
    }

    /**
     * Indica si se muestra el boton de ir al ultimo registro
     * @return true si muestra el boton; false en caso contrario
     */
    public boolean isIrAlUltimo() {
        boolean resultado = false;
        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() < getTotalPaginas()) {
                resultado = true;
            }
        }
        return resultado;
    }

    /**
     * Indica si se muestra el boton de avanzar
     * @return true si muestra el boton; false en caso contrario
     */
    public boolean isAvanzar() {
        boolean resultado = false;
        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() < getTotalPaginas()) {
                resultado = true;
            }
        }
        return resultado;
    }

    /**
     * Indica si se muestra el boton de ir al primer registro
     * @return true si muestra el boton; false en caso contrario
     */
    public boolean isAvanzarRapido() {
        boolean resultado = false;
        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() < getTotalPaginas()) {
                resultado = true;
            }
        }
        return resultado;
    }

    /**
     * Indica si se muestra el boton de retroceder
     * @return true si muestra el boton; false en caso contrario
     */
    public boolean isRetroceder() {
        boolean resultado = false;
        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() > 1) {
                resultado = true;
            }
        }
        return resultado;
    }

    /**
     * Indica si se muestra el boton de retroceder rapido
     * @return true si muestra el boton; false en caso contrario
     */
    public boolean isRetrocederRapido() {
        boolean resultado = false;
        if (paginaVO.getTotalRegistros() > 0) {
            if (getNumeroPagina() > 1) {
                resultado = true;
            }
        }
        return resultado;
    }
    
    /**
     * Metodo para refrescar pagina actual
     */
    public String irPaginaActual() {
    	int numeroPag = getNumeroPagina();
    	 if (paginaVO.getTotalRegistros() > 0) {                                                      
             paginaVO.setOffset((numeroPag - 1) * paginaVO.getRegistrosXPag() + 0);
         }
         ejecutarConsulta();
         return null;
    }
    
    /**
     * Metodo para avanzar una pagina
     * @return null
     */
    public String avanzarPagina() {
        avanzar(1);
        return null;
    }

    /**
     * Metodo para avanzar tres paginas
     * @return null
     */
    public String avanzarPaginasRapido() {
        avanzar(3);
        return null;
    }

    /**
     * Metodo para retroceder tres paginas
     * @return null
     */
    public String retrocederPaginasRapido() {
        retroceder(3);
        return null;
    }

    /**
     * Metodo para retroceder una pagina
     * @return null
     */
    public String retrocederPagina() {
        retroceder(1);
        return null;
    }
    
    /**
     * @return the registrosPorPagina
     */
    public int getRegistrosPorPagina() {
        if (paginaVO != null && paginaVO.getRegistrosXPag() > 0) {
            registrosPorPagina = paginaVO.getRegistrosXPag();
        }
        return registrosPorPagina;
    }

    /**
     * @param registrosPorPagina the registrosPorPagina to set
     */
    public void setRegistrosPorPagina(int registrosPorPagina) {
        this.registrosPorPagina = registrosPorPagina;
    }

    /**
     * @return the numeroPagina
     */
    public int getNumeroPagina() {
    	if (paginaVO != null && paginaVO.getRegistrosXPag() > 0) {

			numeroPagina = (int) Math.ceil(paginaVO.getOffset().doubleValue() / paginaVO.getRegistrosXPag().doubleValue()) + 1;
			int total = getTotalPaginas();
			if(numeroPagina > total) {
				numeroPagina = total;
			}

		}
		return numeroPagina;
    }

    /**
     * @param numeroPagina the numeroPagina to set
     */
    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    /**
     * @return the totalPaginas
     */
    public int getTotalPaginas() {
        if (paginaVO != null && paginaVO.getTotalRegistros() > 0) {
            totalPaginas = (int) Math.ceil((paginaVO.getTotalRegistros().doubleValue() / paginaVO.getRegistrosXPag()
                    .doubleValue()));
        }
        return totalPaginas;
    }

    /**
     * @param totalPaginas the totalPaginas to set
     */
    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

	/**
	 * Método para obtener el atributo paginaVO
	 * @return El atributo paginaVO
	 */
	public PaginaVO getPaginaVO() {
		return paginaVO;
	}

	/**
	 * Método para establecer el atributo paginaVO
	 * @param paginaVO El valor del atributo paginaVO a establecer.
	 */
	public void setPaginaVO(PaginaVO paginaVO) {
		this.paginaVO = paginaVO;
	}
}
