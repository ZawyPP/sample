package sk.zawy.lahodnosti.objects;

import sk.zawy.lahodnosti.accessories.TextEdit;

public class Event {

    private String id;
    private String name;
    private String kind;
    private String time;
    private String time2;
    private String date;
    private String capacity;
    private String price;
    private String price2;
    private String infoMain;
    private String actualCount;
    private String created;
    private String updated;
    private String url;
    private String vip;
    private int visibility;
    private int enable;
    private int published;
    private int onlineReservation;
    private String phoneReservation;

    public Event(String name, String time, String time2, String price, String price2,
                 String date, String kind, String capacity,
                 String infoMain, String id, String actualCount, String created, String updated,
                 int visibility, int enable, int published, int onlineReservation, String phoneReservation,String url) {
        this.name = name;
        this.time = time;
        this.time2 = time2;
        this.price = price;
        this.price2 = price2;
        this.kind = kind;
        this.date = date;
        this.capacity = capacity;
        this.infoMain = infoMain;
        this.id = id;
        this.actualCount=actualCount;
        this.updated = updated;
        this.created = created;
        this.enable  = enable;
        this.visibility = visibility;
        this.published= published;
        this.onlineReservation = onlineReservation;
        this.phoneReservation = phoneReservation;
        this.url = url;
    }

    public String getId() {
        return TextEdit.notNull(id);
    }

    public String getName() {
        return TextEdit.notNull(name);
    }

    public String getKind() {
        return TextEdit.notNull(kind);
    }

    public String getTime() {
        return TextEdit.notNull(time);
    }

    public String getDate() {
        return TextEdit.notNull(date);
    }

    public String getCapacity() {
        return TextEdit.notNull(capacity);
    }

    public String getPrice() {
        return TextEdit.notNull(price);
    }

    public String getPrice2() {
        return TextEdit.notNull(price2);
    }


    public String getTime2() {
        return TextEdit.notNull(time2);
    }

    public String getCreated() {
        return TextEdit.notNull(created);
    }

    public String getUpdated() {
        return updated;
    }

    public int isVisibility() {
        return visibility;
    }

    public int isEnable() {
        return enable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPrice2(String price2) {
        this.price2 = price2;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public int isPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getInfoMain() {
        return TextEdit.notNull(infoMain);
    }

    public void setInfoMain(String infoMain) {
        this.infoMain = infoMain;
    }

    public int isOnlineReservation() {
        return onlineReservation;
    }

    public void setOnlineReservation(int onlineReservation) {
        this.onlineReservation = onlineReservation;
    }

    public String getPhoneReservation() {
        return phoneReservation;
    }

    public void setPhoneReservation(String phoneReservation) {
        this.phoneReservation = phoneReservation;
    }

    public String getUrl() {
        return TextEdit.notNull(url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }
}
