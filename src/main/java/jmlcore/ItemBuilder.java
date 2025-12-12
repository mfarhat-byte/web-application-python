// ========== ItemBuilder.java ==========
package jmlcore;

public class ItemBuilder {

    /*@
      requires dto != null;
      requires dto.title != null && dto.title.length() > 0;
      requires dto.description != null;
      requires status != null && status.length() > 0;
      requires type != null && type.length() > 0;
      ensures \result != null;
      ensures \result.title != null && \result.title.equals(dto.title);
      ensures \result.description != null && \result.description.equals(dto.description);
      ensures \result.status != null && \result.status.equals(status);
      ensures \result.type != null && \result.type.equals(type);
    @*/
    public static SimpleItem build(SimpleItemDTO dto, String status, String type) {
        SimpleItem item = new SimpleItem(dto.title, dto.description, status, type);
        return item;
    }
}