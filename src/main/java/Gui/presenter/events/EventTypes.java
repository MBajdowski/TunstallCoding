package Gui.presenter.events;

import javafx.event.EventType;

public class EventTypes {

    public static EventType<CodeFileEvent> CODE = new EventType<>("CODE");
    public static EventType<DecodeFileEvent> DECODE = new EventType<>("DECODE");

}
