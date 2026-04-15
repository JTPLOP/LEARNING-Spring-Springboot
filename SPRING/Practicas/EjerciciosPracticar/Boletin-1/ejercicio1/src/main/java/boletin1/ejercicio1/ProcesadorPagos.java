package boletin1.ejercicio1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProcesadorPagos {

    private ValidadorTarjeta validador;
    private NotificadorEmail notificador;
    private GatewayStripe gateway;

}