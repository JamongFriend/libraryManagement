package project.libraryManagement.domain;

public enum Category {
    GENERAL("총류"),
    PHILOSOPHY("철학"),
    RELIGION("종교"),
    SOCIAL_SCIENCE("사회과학"),
    NATURAL_SCIENCE("자연과학"),
    TECHNOLOGY("기술과학"),
    ARTS("예술"),
    LANGUAGE("언어"),
    LITERATURE("문학"),
    HISTORY("역사");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
