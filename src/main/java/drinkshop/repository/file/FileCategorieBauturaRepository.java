package drinkshop.repository.file;

import drinkshop.domain.CategorieBautura;


public class FileCategorieBauturaRepository extends FileAbstractRepository<Integer, CategorieBautura>{
    public FileCategorieBauturaRepository(String fileName) {
        super(fileName);
        loadFromFile();
        CategorieBautura.setCounter(entities.size()+1);
    }

    @Override
    protected CategorieBautura extractEntity(String line) {
        String[] elems = line.split(";");

        int id = Integer.parseInt(elems[0]);
        String name = elems[1].trim();
        return new CategorieBautura(id, name);
    }

    @Override
    protected String createEntityAsString(CategorieBautura entity) {
        return entity.getId() + "; " +
                entity.getNume();
    }

    @Override
    protected Integer getId(CategorieBautura entity) {
        return entity.getId();
    }
}
