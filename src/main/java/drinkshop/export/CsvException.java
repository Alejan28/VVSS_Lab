package drinkshop.export;

import java.io.IOException;

public class CsvException extends RuntimeException {
    public CsvException(String message) {
        super(message);
    }
    public CsvException (IOException e){
        super(e.getMessage());
    }
}
