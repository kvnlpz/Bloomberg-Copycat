public class RSSItem {
    String title;
    String link;
    String description;
    int sentimentScore;

    public RSSItem(String title, String link) {
        this.title = title;
        this.link = link;
        this.description = "";
    }

    public RSSItem(String title, String link, String description) {
        this.title = title;
        this.link = link;
        this.description = description;
    }

    public RSSItem(String title, String link, String description, int sentimentScore) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.sentimentScore = sentimentScore;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "RSSItem{" +
                "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
