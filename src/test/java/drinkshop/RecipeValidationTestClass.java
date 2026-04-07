package drinkshop;
import drinkshop.domain.IngredientReteta;
import drinkshop.domain.Reteta;
import drinkshop.service.validator.RetetaValidator;
import drinkshop.service.validator.ValidationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


class RecipeValidationTestClass {
    private Reteta recipe;
    private RetetaValidator validator;

    @BeforeEach
    void setUp() {
        recipe = new Reteta(0, null);
        validator = new RetetaValidator();
    }
    @AfterEach
    void tearDown() {
        recipe = null;
        validator = null;
    }

    @Test
    void F02_TC01(){
        try{
            validator.validate(recipe);
        }catch (Exception e){
            assert(e.getMessage().equals("Product ID invalid!\nLista de ingrediente este null!\n"));
        }
    }

    @Test
    void F02_TC02(){
        try{
            recipe.setId(1);
            validator.validate(recipe);
        }catch (Exception e){
            assert(e.getMessage().equals("Lista de ingrediente este null!\n"));
        }
    }
    @Test
    void F02_TC03(){
        recipe.setId(1);
        recipe.setIngrediente(new ArrayList<>());

        Exception e = assertThrows(ValidationException.class,
                () -> validator.validate(recipe));

        assertEquals("Lista de ingrediente este goala!\n", e.getMessage());
    }
    ArrayList<IngredientReteta> createIngredientList(Integer noIngredients, Boolean valid){
        ArrayList<IngredientReteta> ingredients = new ArrayList<IngredientReteta>();
        int sign= valid ? 1 : -1;
        for(int i=0;i<noIngredients;i++){
            IngredientReteta ingredient = new IngredientReteta("name"+i, sign*(i+1));
            ingredients.add(ingredient);
        }
        return ingredients;
    }
    @Test
    void F02_TC04(){
        recipe.setId(1);
        ArrayList<IngredientReteta> list = createIngredientList(1,true);
        recipe.setIngrediente(list);
        assertDoesNotThrow(() -> validator.validate(recipe));
    }

    @Test
    void F02_TC05(){
        recipe.setId(1);
        ArrayList<IngredientReteta> list = createIngredientList(2,false);
        recipe.setIngrediente(list);
        assertThrows(Exception.class,
                () -> validator.validate(recipe));
    }

    @Test
    void F02_TC06(){
        recipe.setId(0);
        ArrayList<IngredientReteta> list = new ArrayList<>();
        recipe.setIngrediente(list);
        Exception e = assertThrows(ValidationException.class,
                () -> validator.validate(recipe));

        assertEquals("Product ID invalid!\nLista de ingrediente este goala!\n", e.getMessage());
    }

    @Test
    void F02_TC07(){
        recipe.setId(0);
        ArrayList<IngredientReteta> list = createIngredientList(5,false);
        recipe.setIngrediente(list);
        assertThrows(ValidationException.class,
                () -> validator.validate(recipe));

    }

    @Test
    void F02_TC08(){
        recipe.setId(1);
        ArrayList<IngredientReteta> list = createIngredientList(4,true);
        recipe.setIngrediente(list);
        assertDoesNotThrow(() -> validator.validate(recipe));
    }

    @Test
    void F02_TC09(){
        recipe.setId(1);
        ArrayList<IngredientReteta> list = createIngredientList(6,true);
        recipe.setIngrediente(list);
        assertDoesNotThrow(() -> validator.validate(recipe));
    }

    @Test
    void F02_TC010(){
        recipe.setId(1);
        ArrayList<IngredientReteta> list = createIngredientList(3,true);
        recipe.setIngrediente(list);
        assertDoesNotThrow(() -> validator.validate(recipe));
    }
}
