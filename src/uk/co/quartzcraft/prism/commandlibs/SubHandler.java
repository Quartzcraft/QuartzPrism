package uk.co.quartzcraft.prism.commandlibs;

import java.util.List;

public interface SubHandler {
    public void handle(CallInfo call);

    public List<String> handleComplete(CallInfo call);
}