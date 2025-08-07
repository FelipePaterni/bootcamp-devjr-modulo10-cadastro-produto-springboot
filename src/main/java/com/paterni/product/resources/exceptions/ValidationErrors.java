package com.paterni.product.resources.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrors extends StandardError{
    private List<String> erros = new ArrayList<>();
    public void addError(String error){
        this.erros.add(error);
    }
    public List<String> getErros() {
        
        return erros;
    }
    

}
