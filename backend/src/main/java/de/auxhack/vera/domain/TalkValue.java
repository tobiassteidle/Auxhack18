package de.auxhack.vera.domain;

/**
 * backend
 * Author: tsteidle
 * Created: 19.10.18
 */
public class TalkValue {

    private String locale;
    private String text;
    private boolean isMasculine;

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMasculine() {
        return isMasculine;
    }

    public void setMasculine(boolean masculine) {
        isMasculine = masculine;
    }
}
