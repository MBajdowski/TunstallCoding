package Gui.presenter.events;

import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import lombok.Getter;

import java.io.File;

@Getter
public class CodeFileEvent extends Event {

    private File fileToCode;
    private String fileToDecodeFolder;
    private int codeWordLength;

    public CodeFileEvent(final File fileToCode, final String fileToDecodeFolder, final int codeWordLength, @NamedArg("eventType") EventType<? extends Event> eventType) {
        super(eventType);
        setProperties(fileToCode, fileToDecodeFolder, codeWordLength);
    }

    public CodeFileEvent(final File fileToCode, final String fileToDecodeFolder, final int codeWordLength, @NamedArg("source") Object source, @NamedArg("target") EventTarget target, @NamedArg("eventType") EventType<? extends Event> eventType) {
        super(source, target, eventType);
        setProperties(fileToCode, fileToDecodeFolder, codeWordLength);
    }

    private void setProperties(File fileToCode, String fileToDecodeFolder, int codeWordLength) {
        this.fileToCode = fileToCode;
        this.fileToDecodeFolder = fileToDecodeFolder;
        this.codeWordLength = codeWordLength;
    }
}
