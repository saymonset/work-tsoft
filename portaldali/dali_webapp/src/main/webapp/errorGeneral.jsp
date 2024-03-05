<%@page pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter"%>

<html>
	<head>
		<title>Error en la aplicaci&oacute;n.</title>

		<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" type="text/css" media="all"
			href="<%=request.getContextPath()%>/css/dali.css" />


	</head>
	<body>

		<table width="925px" align="center" cellpadding="0px"
			cellspacing="0px">
			<tr>
				<td>
					<div id="wrap">
						<div id="header">

							<!-- <img class="logo" src="images/logo_indeval.png" width="50" height="50" alt="INDEVAL" /> -->
							<ul id="top">

							</ul>


						</div>

						<div class="clear">
						</div>
						<div>
							<ul id="nav">
								<li>
									<a class="active" id="menuTrigger" href="/dali/" target="_top">INICIO</a>
								</li>
							</ul>
						</div>
						<div class="clear">
						</div>

						<div id="contenido_pag">
							<div id="tituloContenido">
								<div class='descripcion'>
									<h1 class="titulo">
										Ocurrió un error en la aplicaci&#243;n.
									</h1>
									<p>

									</p>
								</div>
							</div>

							<div class="seccion texto">
								<div class="warning">
									<p>
										Un error no esperado ha ocurrido mientras se procesaba su
										solicitud. Si el error persiste, favor
										de comunicarse con el personal de soporte t&#233;cnico.
									</p>
								</div>
							</div>
						</div>
					</div>
				</td>
			</tr>
		</table>

	</body>
</html>
