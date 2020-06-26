package main.item;

public class Book extends Production {

    public static final int TITLE_LENGTH = 10;
    public static final String TITLE_CAPTION = "題名";
    public static final int CREATER_LENGTH = 6;
    public static final String CREATER_CAPTION = "作者";
    public static final String NAME = "本";
    

    public boolean setTitle(String title) {
        // BookShelfManagerで入力チェックしているが、ここでも入力チェック
        if(title.length() <= TITLE_LENGTH && 0 < title.length()) {
            super.title = title;
            return true;
        }
        else {
            return false;
        }
    }

    public boolean setPerson(String person) {
        // BookShelfManagerで入力チェックしているが、ここでも入力チェック
        if(person.length() <= CREATER_LENGTH && 0 < person.length()) {
            super.person = person;
            return true;
        }
        else {
            return false;
        }
    }
}

