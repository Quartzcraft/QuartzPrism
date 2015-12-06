package uk.co.quartzcraft.prism.actions;

import uk.co.quartzcraft.prism.Prism;
import uk.co.quartzcraft.prism.appliers.PrismProcessType;

public class PrismProcessAction extends GenericAction {

    public class PrismProcessActionData {
        public String params = "";
        public String processType;
    }

    /**
	 * 
	 */
    private PrismProcessActionData actionData;

    /**
     * 
     * @param processType
     * @param parameters
     */
    public void setProcessData(PrismProcessType processType, String parameters) {

        actionData = new PrismProcessActionData();

        if( processType != null ) {
            actionData.params = parameters;
            actionData.processType = processType.name().toLowerCase();
        }
    }

    /**
	 * 
	 */
    @Override
    public void setData(String data) {
        this.data = data;
        if( data != null && !this.data.isEmpty() ) {
            actionData = gson.fromJson( data, PrismProcessActionData.class );
        }
    }

    /**
	 * 
	 */
    @Override
    public void save() {
        data = gson.toJson( actionData );
    }

    /**
     * 
     * @return
     */
    public String getProcessChildActionType() {
        return Prism.getActionRegistry().getAction( "prism-" + actionData.processType ).getName();
    }

    /**
	 * 
	 */
    @Override
    public String getNiceName() {
        return actionData.processType + " (" + actionData.params + ")";
    }
}