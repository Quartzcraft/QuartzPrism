package uk.co.quartzcraft.prism.purge;

import uk.co.quartzcraft.prism.Prism;
import uk.co.quartzcraft.prism.actionlibs.QueryParameters;

public class LogPurgeCallback implements PurgeCallback {

    /**
     * Simply log the purges, being done automatically
     */
    @Override
    public void cycle(QueryParameters param, int cycle_rows_affected, int total_records_affected, boolean cycle_complete) {
        Prism.debug( "Purge cycle cleared " + cycle_rows_affected + " rows." );
        if( cycle_complete ) {
            Prism.log( "Cleared " + total_records_affected + " rows from the database. Using:"
                    + param.getOriginalCommand() );
        }
    }
}