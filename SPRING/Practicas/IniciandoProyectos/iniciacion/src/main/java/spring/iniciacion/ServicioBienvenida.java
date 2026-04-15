package spring.iniciacion;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;


/*Este ejercicio basico trata sobre implementar un código de esta clase 
utilizando la anotación estereotipo correspondiente a la capa de lógica de 
negocio y el "hook" de ciclo de vida necesario  para imprimir en
 consola el texto "¡Bean creado automáticamente!" */


@Service 
/*Esta anotacion le indicamos a Spring que esta clase se encargara
de la logica de negocio. */

public class ServicioBienvenida {
    
@PostConstruct
/*Esta anotacion es la que le permitira saber a Spring el HOOK de ciclo de vida
esta anotacion se encarga de ejecutar X codigo luego de que Spring cree el 
objeto.*/

public void alArrancar() {
    System.out.println("El bean fue creado automaticamente.");
}











}
