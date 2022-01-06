package sk.zawy.lahodnosti.objects;

public class Texts {

    private String id;
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;
    private String img1;
    private String img2;
    private String img3;
    private String img4;
    private int published;
    private String created;
    private String updated;

    public Texts(String id, String text1, String text2, String text3, String text4,
                 String text5, String img1, String img2, String img3, String img4,
                 int published, String created, String updated) {

        this.id=id;
        this.text1=text1;
        this.text2=text2;
        this.text3=text3;
        this.text4=text4;
        this.text5=text5;
        this.img1=img1;
        this.img2=img2;
        this.img3=img3;
        this.img4=img4;
        this.published=published;
        this.created=created;
        this.updated=updated;

    }


    public String getId() {
        return id;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public String getText3() {
        return text3;
    }

    public String getText4() {
        return text4;
    }

    public String getText5() {
        return text5;
    }

    public String getImg1() {
        return img1;
    }

    public String getImg2() {
        return img2;
    }

    public String getImg3() {
        return img3;
    }

    public String getImg4() {
        return img4;
    }

    public int getPublished() {
        return published;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }
}
