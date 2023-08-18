## CHALLENGE

### Los requerimientos son los siguientes:

    Debes desarrollar una API REST en Spring Boot utilizando java 11 o superior, con las siguientes funcionalidades:
    Debe contener un servicio llamado por api-rest que reciba 2 números, los sume, y le aplique una suba de un porcentaje que debe ser adquirido de un servicio externo (por ejemplo, si el servicio recibe 5 y 5 como valores, y el porcentaje devuelto por el servicio externo es 10, entonces (5 + 5) + 10% = 11). Se deben tener en cuenta las siguientes consideraciones:
    - El servicio externo puede ser un mock, tiene que devolver el % sumado.
    - Dado que ese % varía poco, podemos considerar que el valor que devuelve ese servicio no va cambiar por 30 minutos.
    - Si el servicio externo falla, se debe devolver el último valor retornado. Si no hay valor, debe retornar un error la api.
    - Si el servicio falla, se puede reintentar hasta 3 veces.
    Historial de todos los llamados a todos los endpoint junto con la respuesta en caso de haber sido exitoso. Responder en Json, con data paginada. El guardado del historial de llamadas no debe sumar tiempo al servicio invocado, y en caso de falla, no debe impactar el llamado al servicio principal.
    La api soporta recibir como máximo 3 rpm (request / minuto), en caso de superar ese umbral, debe retornar un error con el código http y mensaje adecuado. 
    El historial se debe almacenar en una database PostgreSQL. 
    Incluir errores http. Mensajes y descripciones para la serie 4XX. 
    Se deben incluir tests unitarios. 
    Esta API debe ser desplegada en un docker container. Este docker puede estar en un dockerhub público. La base de datos también debe correr en un contenedor docker. Recomendación usar docker compose 
    Debes agregar un Postman Collection o Swagger para que probemos tu API 
    Tu código debe estar disponible en un repositorio público, junto con las instrucciones de cómo desplegar el servicio y cómo utilizarlo. 
    Tener en cuenta que la aplicación funcionará de la forma de un sistema distribuido donde puede existir más de una réplica del servicio funcionando en paralelo. 
    Recuerda que son 15 días a contar desde el día del envío para que nos envíes tu respuesta.

### DESCRIPCION DE LA API-REST:
Consta de una api desarrollada con versión de java 17 y Spring-boot (2.6.13).

Puerto: 8080


El ejercicio recibe operaciones matemáticas ("Operation") dentro de las cuales se sumaran dos numeros, y al resultado se le añadirá un 10%, es decir, que se lo multiplicará por (1 + porcentaje).

La expresion matemática utilizada es la siguiente:

- resultado = (number1 + number2) * (1 + percentage)

#### OBTENCION DEL PORCENTAJE:
Para la obtención del porcentaje se utilizó una api externa: https://app.raw-labs.com/workspace/0.
(La misma permite generar endpoints públicos y simular la respuesta que se desea obtener utilizando js)
El curl del endpoint generado es el siguiente:

    curl --location 'https://dev-803847021644131.api.raw-labs.com/randomNumber' \
    --header 'Authorization: 1234'

Para la obtención del porcentaje, se indicó que el mismo suele variar cada 30 minutos aproximadamente.
Como el porcentaje mantiene su valorpor 30 minutos, se consideró schedulear un proceso batch que actualice el porcentaje cada media hora. 
Además, el costo de consultar continuamente al proveedor externo puede conllevar costos elevados, por lo que en este caso se lo estaría consultando una vez cada 30 minutos.

Este proceso corre en una única instancia, ya que se utilizó "Shedlock" a fin de correr una única vez este proceso batch cada 30 min.  

Además, se conserva un histórico de porcentajes dentro de la bd con su fecha y hora de obtención.

El proceso corre cada 30 minutos (como indica el enunciado), y realiza 5 intentos para obtener el nuevo porcentaje a aplicar.




#### NUEVA OPERACION:

Al momento de ingresar una nueva operación... A partir de un request se toman como parámetros los dos numeros a ingresar.
El porcentaje se obtiene a través de una consulta a la bd, en donde se obtiene el último porcentaje recibido hace 30 min 
(Si no se obtuvo ninguno o el cliente lanzó una excepción, se retornará el último valor registrado en la bd).

ENDPOINT:

    curl --location 'localhost:8080/challenge/api/v1/operation' \
    --header 'Content-Type: application/json' \
    --data '{
    "number1":1,
    "number2":5
    }'

RESPONSE:

    {
      "code": 200,
      "message": "OK",
      "timestamp": "2023-08-17T07:33:43.729162",
      "body": {
        "id": 4,
        "number1": 6.0,
        "number2": 5.0,
        "percentage": 34.0,
        "sum": 14.74,
        "date_operation": "2023-08-17T07:33:43.69026"
      }
    }


EXCEPCIONES:

- 400: BAD REQUEST:

      {
          "code": 400,
          "message": "Some of the numbers are null in request",
          "timestamp": "2023-08-17T07:26:20.321829"
      }


- 429: TOO MANY REQUEST
  
      {
        "code": 429,
        "message": "Too many requests (rate: 3 request/min)",
        "timestamp": "2023-08-17T11:02:52.611045"
      }

- 500: INTERNAL SERVER ERROR

      {
          "code": 500,
          "message": "External Client Exception: Do not exist Percentage Client.",
          "timestamp": "2023-08-17T07:26:20.321829"
      }



### CONSULTA DE OPERACIONES EXISTENTES:

Para obtener las consultas, se realizó un paginado cada 10 elementos obtenidos de la lista de Operaciones.

ENDPOINT:

    curl -X GET "http://localhost:8080/challenge/api/v1/operation?page=1" -H "accept: */*"

### INSTALL POSTGRESQL IN YOUR LOCAL:
MACOS: Abrir la termial e ingresar los siguientes comandos para la instalación de postgresql
1. Brew update
2. Brew install postgresql 

INICIAR POSTGRESQL LOCAL:

3. brew services start postgresql
4. psql postgres


### SWAGGER:
  
    LocalHost: http://localhost:8080/challenge/swagger-ui/


### DOCKER:
