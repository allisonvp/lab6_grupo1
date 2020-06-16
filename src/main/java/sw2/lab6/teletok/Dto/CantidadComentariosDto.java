package sw2.lab6.teletok.Dto;

import java.time.LocalDateTime;

public interface CantidadComentariosDto {

    int getCantcoment();
    int getPostid();
    String getDescrip();
    LocalDateTime getDia();
    String getFoto();
    String getUsuario();

}
