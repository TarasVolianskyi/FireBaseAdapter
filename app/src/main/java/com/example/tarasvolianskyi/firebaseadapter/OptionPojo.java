package com.example.tarasvolianskyi.firebaseadapter;

public class OptionPojo {

    private String optionId;
    private String optionName;
    private int optionRating;

    public OptionPojo() {
    }

    public OptionPojo(String optionId, String optionName, int optionRating) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.optionRating = optionRating;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public int getOptionRating() {
        return optionRating;
    }

    public void setOptionRating(int optionRating) {
        this.optionRating = optionRating;
    }
}
