package jmlcore;

public class SimpleItemDTO {
    public /*@ spec_public nullable @*/ String title;
    public /*@ spec_public nullable @*/ String description;

    /*@
      requires t != null && t.length() > 0;
      requires d != null;
      assignable this.*;
      ensures title != null && title.equals(t);
      ensures description != null && description.equals(d);
    @*/
    public SimpleItemDTO(String t, String d) {
        this.title = t;
        this.description = d;
    }
}