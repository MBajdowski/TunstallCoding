package FileAnalysis;

import lombok.Getter;

@Getter
public class HistogramData {
    private int x;
    private int y;

    public HistogramData(final int x) {
        this.x = x;
    }

    public void increment() {
        y++;
    }
}
