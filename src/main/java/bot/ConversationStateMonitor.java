package bot;

import java.util.HashMap;
import java.util.Map;

public class ConversationStateMonitor {
    private Map <String, State> conversatonsStates = new HashMap<>();

    public State getState(String id){
        if(conversatonsStates.containsKey(id))
            return conversatonsStates.get(id);
        else {
            setState(id,State.NONE);
        }
        return State.NONE;
    }
    public void setState(String id, State state){
        conversatonsStates.put(id,state);
    }
enum State {
    NONE,
    AWAIT_FOR_PHONE,
    AWAIT_FOR_CODE
    }
}

