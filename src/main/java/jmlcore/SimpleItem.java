package jmlcore;

public class SimpleItem {

    public /*@ spec_public nullable @*/ String title;
    public /*@ spec_public nullable @*/ String description;
    public /*@ spec_public nullable @*/ String status;
    public /*@ spec_public nullable @*/ String type;

    /*@
      requires title != null && title.length() > 0;
      requires description != null;
      requires status != null && status.length() > 0;
      requires type != null && type.length() > 0;
      assignable this.*;
      ensures this.title != null && this.title.equals(title);
      ensures this.description != null && this.description.equals(description);
      ensures this.status != null && this.status.equals(status);
      ensures this.type != null && this.type.equals(type);
    @*/
    public SimpleItem(String title, String description, String status, String type) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.type = type;
    }

    /*@
      requires s1 != null;
      requires s2 != null;
      ensures \result == s1.equals(s2);
      pure
    @*/
    public static boolean strEq(String s1, String s2) {
        return s1.equals(s2);
    }
}