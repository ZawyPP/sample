package sk.zawy.lahodnosti.objects;

public class FBpost {
    private String created_time;
    private String message;
    private String story;
    private String description;
    private String caption;
    private String id;
    private String link;

    private String paging_previous;
    private String paging_next;

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getPaging_previous() {
        return paging_previous;
    }

    public void setPaging_previous(String paging_previous) {
        this.paging_previous = paging_previous;
    }

    public String getPaging_next() {
        return paging_next;
    }

    public void setPaging_next(String paging_next) {
        this.paging_next = paging_next;
    }
}
