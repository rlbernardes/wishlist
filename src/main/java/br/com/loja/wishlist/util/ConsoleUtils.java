package br.com.loja.wishlist.util;

import br.com.loja.wishlist.exception.BusinessRunTimeException;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class ConsoleUtils {

    public static String getJsonFileAsString(String path) throws BusinessRunTimeException {
        String strReturn = "";
        File file = null;
        try {
            File resource = new ClassPathResource(path).getFile();
            strReturn = new String(Files.readAllBytes(resource.toPath()));
        } catch (FileNotFoundException e) {
            throw new BusinessRunTimeException("Fail to find file " + path + ".\n" + e.getMessage());
        } catch (IOException e) {
            throw new BusinessRunTimeException("Fail to read file " + path + ".\n" + e.getMessage());
        }
        return strReturn;
    }

}
