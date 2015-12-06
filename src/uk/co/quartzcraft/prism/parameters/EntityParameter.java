package uk.co.quartzcraft.prism.parameters;

import uk.co.quartzcraft.prism.actionlibs.MatchRule;
import uk.co.quartzcraft.prism.actionlibs.QueryParameters;
import org.bukkit.command.CommandSender;

import java.util.regex.Pattern;

public class EntityParameter extends SimplePrismParameterHandler {

    /**
	 * 
	 */
    public EntityParameter() {
        super( "Entity", Pattern.compile( "[~|!]?[\\w,]+" ), "e" );
    }

    /**
	 * 
	 */
    @Override
    public void process(QueryParameters query, String alias, String input, CommandSender sender) {
        MatchRule match = MatchRule.INCLUDE;
        if( input.startsWith( "!" ) ) {
            match = MatchRule.EXCLUDE;
        }
        final String[] entityNames = input.split( "," );
        if( entityNames.length > 0 ) {
            for ( final String entityName : entityNames ) {
                query.addEntity( entityName.replace( "!", "" ), match );
            }
        }
    }
}