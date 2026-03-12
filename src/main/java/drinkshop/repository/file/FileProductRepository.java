package drinkshop.repository.file;

import drinkshop.domain.Product;
import drinkshop.domain.CategorieBautura;
import drinkshop.domain.TipBautura;
import drinkshop.repository.Repository;

public class FileProductRepository
        extends FileAbstractRepository<Integer, Product> {
    private Repository<Integer, TipBautura> typeRepo;
    private Repository<Integer, CategorieBautura> categoryRepo;


    public FileProductRepository(String fileName) {
        super(fileName);
        loadFromFile();
    }
    public FileProductRepository(String fileName, Repository<Integer, TipBautura> typeRepo, Repository<Integer, CategorieBautura> categoryRepo){
        super(fileName);
        this.typeRepo = typeRepo;
        this.categoryRepo = categoryRepo;
        loadFromFile();
    }
    @Override
    protected Integer getId(Product entity) {
        return entity.getId();
    }

    @Override
    protected Product extractEntity(String line) {

        String[] elems = line.split(",");

        int id = Integer.parseInt(elems[0]);
        String name = elems[1];
        double price = Double.parseDouble(elems[2]);
        int cat=Integer.parseInt(elems[3]);
        int type=Integer.parseInt(elems[4]);
        CategorieBautura categorie=categoryRepo.findOne(cat);
        TipBautura tip=typeRepo.findOne(type);

        return new Product(id, name, price, categorie, tip);
    }

    @Override
    protected String createEntityAsString(Product entity) {
        return entity.getId() + "," +
                entity.getNume() + "," +
                entity.getPret() + "," +
                entity.getCategorie().getId() + "," +
                entity.getTip().getId();
    }
}