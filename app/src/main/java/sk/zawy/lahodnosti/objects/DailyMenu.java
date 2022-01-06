package sk.zawy.lahodnosti.objects;

public class DailyMenu {

    private String id;
    private String menu;
    private String day;
    private String soup;
    private String mailMeal;
    private String photo;
    private String soup2;
    private String mainMeal2;
    private String photo2;
    private String soup3;
    private String mainMeal3;
    private String photo3;
    private String notification;
    private int published;
    private String created_at;
    private String updated_at;
    private int capacity;
    private double price;

    public DailyMenu(String id, String menu, String day, String soup, String mainMeal,
                     String photo, String soup2, String mainMeal2, String photo2, String soup3,
                     String mainMeal3, String photo3, String notification, int capacity,
                     double price, int published, String created_at, String updated_at) {

        this.id = id;
        this.menu = menu;
        this.day = day;
        this.soup = soup;
        this.mailMeal = mainMeal;
        this.photo = photo;
        this.soup2 = soup2;
        this.mainMeal2 = mainMeal2;
        this.photo2 = photo2;
        this.soup3 = soup3;
        this.mainMeal3 = mainMeal3;
        this.photo3 = photo3;
        this.notification = notification;
        this.capacity = capacity;
        this.price = price;
        this.published = published;
        this.created_at = created_at;
        this.updated_at = updated_at;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSoup() {
        return soup;
    }

    public void setSoup(String soup) {
        this.soup = soup;
    }

    public String getMailMeal() {
        return mailMeal;
    }

    public void setMailMeal(String mailMeal) {
        this.mailMeal = mailMeal;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getSoup2() {
        return soup2;
    }

    public void setSoup2(String soup2) {
        this.soup2 = soup2;
    }

    public String getMainMeal2() {
        return mainMeal2;
    }

    public void setMainMeal2(String mainMeal2) {
        this.mainMeal2 = mainMeal2;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getSoup3() {
        return soup3;
    }

    public void setSoup3(String soup3) {
        this.soup3 = soup3;
    }

    public String getMainMeal3() {
        return mainMeal3;
    }

    public void setMainMeal3(String mainMeal3) {
        this.mainMeal3 = mainMeal3;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public int getPublished() {
        return published;
    }

    public void setPublished(int published) {
        this.published = published;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
