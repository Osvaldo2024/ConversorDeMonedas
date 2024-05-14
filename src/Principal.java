import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        int opcionElegida = 0;
        String monedaInicial = "";
        String codMonedaInicial = "";
        String monedaFinal = "";
        String codMonedaFinal = "";
        Double dineroIngresado = 0.0;
        Double dineroConvertido = 0.0;
        String direccionWebExchangesInicial = "https://v6.exchangerate-api.com/v6/76dc64f5bd4bed4edd149cec/pair/";
        String direccionWebExchangesFinal = "";

        Scanner teclado = new Scanner(System.in);

        try {
            while (opcionElegida != 7) {
                System.out.println("\n----- BIENVENID@ AL CONVERSOR DE MONEDAS -----\n");
                System.out.println("1) Dolar           ---> Peso Argentino");
                System.out.println("2) Peso Argentino  ---> Dolar");
                System.out.println("3) Dolar           ---> Real Brasileño");
                System.out.println("4) Real Brasileño  ---> Dolar");
                System.out.println("5) Dolar           ---> Peso Colombiano");
                System.out.println("6) Peso Colombiano ---> Dolar");
                System.out.println("7) Salir\n");
                System.out.println("Elija qué tipo de conversión desea hacer: ");

                opcionElegida = teclado.nextInt();

                switch (opcionElegida) {
                    case 1:
                        monedaInicial = "Dolares";
                        monedaFinal = "Pesos Argentinos";
                        codMonedaInicial = "USD";
                        codMonedaFinal = "ARS";
                        break;
                    case 2:
                        monedaInicial = "Pesos Argentinos";
                        monedaFinal = "Dolares";
                        codMonedaInicial = "ARS";
                        codMonedaFinal = "USD";
                        break;
                    case 3:
                        monedaInicial = "Dolares";
                        monedaFinal = "Reales Brasileños";
                        codMonedaInicial = "USD";
                        codMonedaFinal = "BRL";
                        break;
                    case 4:
                        monedaInicial = "Reales Brasileños";
                        monedaFinal = "Dolares";
                        codMonedaInicial = "BRL";
                        codMonedaFinal = "USD";
                        break;
                    case 5:
                        monedaInicial = "Dolares";
                        monedaFinal = "Pesos Colombianos";
                        codMonedaInicial = "USD";
                        codMonedaFinal = "COP";
                        break;
                    case 6:
                        monedaInicial = "Pesos Colombianos";
                        monedaFinal = "Dolares";
                        codMonedaInicial = "COP";
                        codMonedaFinal = "USD";
                        break;
                    case 7:
                        System.out.println("\nGracias por utilizar nuestros servicios.");
                        break;
                }
                if (opcionElegida < 7 && opcionElegida > 0) {
                    System.out.println("Elija la cantidad de " + monedaInicial + " que desea convertir a " + monedaFinal);
                    dineroIngresado = teclado.nextDouble();

                    direccionWebExchangesFinal = (direccionWebExchangesInicial + codMonedaInicial + "/" + codMonedaFinal + "/" + dineroIngresado);

                    try {
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(direccionWebExchangesFinal))
                                .build();
                        HttpResponse<String> response = client
                                .send(request, HttpResponse.BodyHandlers.ofString());

                        String json = response.body();

                        ConvertirMonedaAJava convertirMonedaAJava = new Gson().fromJson(json, ConvertirMonedaAJava.class);
                        dineroConvertido = Double.valueOf(convertirMonedaAJava.conversion_result());
                        System.out.println("\n" + dineroIngresado + " " + monedaInicial + " son " + dineroConvertido + " " + monedaFinal);

                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    if (opcionElegida != 7) {
                        System.out.println("Por favor ingrese una opción válida.");
                    }
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("ERROR.No se ha ingresado un número válido.");
        }
    }
}
