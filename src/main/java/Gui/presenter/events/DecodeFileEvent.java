package Gui.presenter.events;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import lombok.Getter;

import java.io.File;

@Getter
public class DecodeFileEvent extends Event {

    private File fileToDecode;
    private String fileToDecodeFolder;

    public DecodeFileEvent(final File fileToDecode, final String fileToDecodeFolder, @NamedArg("eventType") EventType<? extends Event> eventType) {
        super(eventType);
        setProperties(fileToDecode, fileToDecodeFolder);
    }

    public DecodeFileEvent(final File fileToDecode, final String fileToDecodeFolder, @NamedArg("source") Object source, @NamedArg("target") EventTarget target, @NamedArg("eventType") EventType<? extends Event> eventType) {
        super(source, target, eventType);
        setProperties(fileToDecode, fileToDecodeFolder);
    }

    private void setProperties(File fileToDecode, String fileToDecodeFolder) {
        this.fileToDecode = fileToDecode;
        this.fileToDecodeFolder = fileToDecodeFolder;
    }
}
