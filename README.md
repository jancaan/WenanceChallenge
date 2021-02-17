# Wenance Challenge - Carlos Janulik

![N|Solid](https://www.duna.cl/media/2020/12/Seg%C3%BAn-Bittax-una-reducci%C3%B3n-en-los-impuestos-de-Bitcoin-no-ayudar%C3%A1-al-crecimiento-de-la-industria.jpg)

Construir un MicroServicio que haciendo uso del siguiente servicio REST
[https://cex.io/api/last_price/BTC/USD](https://cex.io/api/last_price/BTC/USD).

	1) Realice una llamada recurrente cada 10 segundos, almacene los datos y que exponga a travÃ©s de un API REST las siguientes funcionalidades:
         a) Obtener el precio del Bitcoin en cierto timestamp.
         b) Conocer el promedio de valor entre dos timestamps asÃ­ como la diferencia porcentual entre ese valor promedio y el valor mÃ¡ximo almacenado para toda la serie temporal disponible.
         c) Devolver en forma paginada los datos almacenados con o sin filtro de timestamp. (Punto Extras)

	2) Incorporar un conjunto de test unitarios que demuestran la corrección de la solución.

	3) incorporar un archivo README.md que contenga una descripción de la solución propuesta asÃ­ como instrucciones de ejecución en entorno local.

Indicaciones:

- La aplicación deberÃ¡ estar desarrollada usando Springboot y subida a un repositorio en github con permisos públicos de acceso y clonado.(Java 1.8 o superior).

- La aplicación deberá poder ser ejecutada en entorno local.

- Solid, Clean Code.

## URLs:

	1)Repositorio github (código fuente): https://github.com/jancaan/bitcoin
	2)Swagger (documentación): http://localhost:8080/swagger-ui.html
	3)Consola de H2 (usuario:sa password: ): http://localhost:8080/h2-console/ 

	
##### Ejecución de app en entorno local (desde Eclipse):	
sobre com.wenance.challenge.Application.java /botón derecho / Run As -> Spring Boot App

##### Ejecución de test (desde Eclipse):	
sobre com.wenance.challenge.BitcoinTest.java /botón derecho / Run As -> JUnit Test

## Servicio que retorna el valor del bitcoin:
Request:

	https://cex.io/api/last_price/BTC/USD (GET)

Response: 

	{
        "lprice": "47882.3",
        "curr1": "BTC",
        "curr2": "USD"
    }

#Ejemplos de consumos de servicios creados:
###### 1.a) Obtener el precio del Bitcoin en cierto timestamp.

Request:

	http://localhost:8080/bitcoins/consultarValorBitcoinEnCiertoTimestamp?fechaHora=2021-02-16T15:03:20.174Z (GET)

Response: 

	{
		"id":3,
		"lprice":48286.8,
		"curr1":"BTC",
		"curr2":"USD",
		"createDate":"2021-02-16 15:03:20.174"
	}
    
###### 1.b) Conocer el promedio de valor entre dos timestamps asi como la diferencia porcentual entre ese valor promedio y el valor maximo almacenado para toda la serie temporal disponible.

Request:

	http://localhost:8080/bitcoins/consultarValorPromedioYDiferenciaPorcentual?fechaHoraDesde=2021-02-16T15%3A02%3A59.166Z&fechaHoraHasta=2021-02-16T15%3A03%3A09.758Z (GET)

Response: 

	{
	  "valorPromedio": 48083.25,
	  "valorMaximo": 48084.5,
	  "diferenciaPorcentualEntreValorPromedioYMaximo": 0.002599590304568
	}

###### 1.c) Devolver en forma paginada los datos almacenados con o sin filtro de timestamp.

Request:

	http://127.0.0.1:8080/bitcoins/consultarValoresBitcoinAlmacenados?fechaHoraDesde=2021-02-16T15%3A02%3A59.166Z&fechaHoraHasta=2021-02-16T15%3A06%3A21.789Z&resultadosPorPagina=3&numeroPagina=0 (GET)

Response: 

	[
		{
			"id":1,
			"lprice":48082.0,
			"curr1":"BTC",
			"curr2":"USD",
			"createDate":"2021-02-16 15:02:59.166"
		},
		{
			"id":2,
			"lprice":48084.5,
			"curr1":"BTC",
			"curr2":"USD",
			"createDate":"2021-02-16 15:03:09.758"
		},
		{
			"id":3,
			"lprice":48286.8,
			"curr1":"BTC",
			"curr2":"USD",
			"createDate":"2021-02-16 15:03:20.174"
		}
	]

#####Acerca de la solución
Para consultar cada 10 segundos los valores del bitcoin obtenidos, se utiliza un scheduler de Spring (está en BitcoinSchedule).
Las peticiones son recibidas por BitcoinController, el mismo se comunica con BitcoinService que posee la lógica de negocio, y este se comunica con la capa de acceso a datos (a través de BitcoinDAO).
Los datos que retornan los servicios son DTOs.
Para realizar la transformacion de objetos del modelo a DTO y viceversa se utilizó un Transformer (BitcoinTransformer).
La configuración se encuentra en los application.properties tanto en el src (para la ejecución de la app en entorno local) como en test (para los test creados con JUnit y MockMvc).


#####Mail de contacto: [jancaan@gmail.com](mailto:jancaan@gmail.com?subject=[Wenance%20Challenge])
