package drinkshop.domain;

import java.io.Serializable;

public class CategorieBautura implements Serializable {
    private static int counter = 1;
    private Integer id;
    private String nume;

    public CategorieBautura(Integer id){
        this.id=id;
        this.nume="N/A";
    }
    public CategorieBautura(Integer id, String nume){
        this.id=id;
        this.nume=nume;

    }

    public Integer getId(){
        return id;
    }

    public void steId(Integer id){
        this.id=id;
    }

    public String getNume(){
        return nume;

    }
    public void setNume(String nume){
        this.nume=nume;
    }
    @Override
    public String toString() {
        return this.nume;
    }

    public static void setCounter(int newCounter){
        counter=newCounter;
    }

    public static int getCounter(){
        return counter;
    }

}