package br.com.felipevalboeno.gestao_vagas.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//Classe global controladora pra tratar exceções no Springboot
@ControllerAdvice
public class ExceptionHandlerController {
    
private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource message){
        this.messageSource = message;

    }

    /*Método que percorre todos os erros obtidos na requisição
    e pra cada erro adiciona na lista e trata com mensagem customizada
     */
    //#region
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<ErrorMessageDTO> dto = new ArrayList<>();

        e.getBindingResult().getFieldErrors().forEach(err -> {
        String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
        ErrorMessageDTO error = new ErrorMessageDTO(message, err.getField());    
        dto.add(error);


        }); 

        return new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST);
    }
    //#endregion

}
