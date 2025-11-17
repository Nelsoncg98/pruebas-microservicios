package clinica.pagos.controller;

import clinica.pagos.dto.PagoRequestDto;
import clinica.pagos.dto.PagoResponseDto;
import clinica.pagos.model.EstadoPago;
import clinica.pagos.service.PagoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import clinica.pagos.service.PagoService;

@RestController
@RequestMapping("/pagos")

public class PagoController {
    @Autowired
    private PagoService pagoService;

    @PostMapping("/elaborar") 
    public ResponseEntity<PagoResponseDto> elaborarBoleta(@RequestBody PagoRequestDto pagoDTO) {
        PagoResponseDto pagoCreado = pagoService.elaborarBoleta(pagoDTO);
        return new ResponseEntity<>(pagoCreado, HttpStatus.CREATED);
    }

     @PostMapping("/pagar")
    public ResponseEntity<PagoResponseDto> pagarBoleta(@RequestBody PagoRequestDto pagoDTO) {
        PagoResponseDto pago = pagoService.pagarBoleta(pagoDTO);
        return ResponseEntity.ok(pago);
    }

   
}
