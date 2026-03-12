package drinkshop.repository.file;

import drinkshop.domain.TipBautura;

public class FileTipBauturaRepository extends FileAbstractRepository<Integer, TipBautura> {
    public FileTipBauturaRepository(String fileName) {
        super(fileName);
        loadFromFile();
        TipBautura.setCounter(entities.size()+1);
    }

    @Override
    protected TipBautura extractEntity(String line) {
        String[] elems = line.split(";");

        int id = Integer.parseInt(elems[0]);
        String name = elems[1].trim();
        return new TipBautura(id, name);
    }

    @Override
    protected String createEntityAsString(TipBautura entity) {
        return entity.getId() + "; " +
                entity.getNume();
    }

    @Override
    protected Integer getId(TipBautura entity) {
        return entity.getId();
    }
}
