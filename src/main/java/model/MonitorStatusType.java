package model;

public enum MonitorStatusType {
    NORMAL("System works normally", "-fx-background-color: #00FF00"),
    NOT_ENOUGH("Not enough stocks available\nfor purchase", "-fx-background-color: #FFFF00"),

    // Orange color:
    FULL("System holds enough stock,\npreventing selling", "-fx-background-color: #FFA500");

    private String style;
    private String content;

    public String getStyle() {
        return style;
    }

    public String getContent() {
        return content;
    }

    MonitorStatusType(String content, String style) {
        this.content = content;
        this.style = style;
    }
}
