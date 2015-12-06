package uk.co.quartzcraft.prism.database.mysql;

import uk.co.quartzcraft.prism.Prism;

public class DeleteQueryBuilder extends SelectQueryBuilder {

    /**
     * 
     * @param plugin
     */
    public DeleteQueryBuilder(Prism plugin) {
        super( plugin );
    }

    /**
	 * 
	 */
    @Override
    public String select() {
        return "DELETE FROM " + tableNameData + " USING " + tableNameData + 
        " LEFT JOIN " + tableNameDataExtra + " ex ON (" + tableNameData + ".id = ex.data_id) ";
    }

    /**
	 * 
	 */
    @Override
    protected String group() {
        return "";
    }

    /**
	 * 
	 */
    @Override
    protected String order() {
        return "";
    }

    /**
	 * 
	 */
    @Override
    protected String limit() {
        return "";
    }
}