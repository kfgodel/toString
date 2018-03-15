package ar.com.kfgodel.v1.tostring.testobjects;

/**
 * This type represents a class with many properties in it
 * Created by kfgodel on 10/09/14.
 */
public class ManyProperties {

    private String id;
    private String name;
    private String property1;
    private String property2;
    private String property3;
    private String property4;
    private String property5;
    private String property6;
    private String property7;
    private String property8;
    private String property9;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProperty1() {
        return property1;
    }

    public void setProperty1(String property1) {
        this.property1 = property1;
    }

    public String getProperty2() {
        return property2;
    }

    public void setProperty2(String property2) {
        this.property2 = property2;
    }

    public String getProperty3() {
        return property3;
    }

    public void setProperty3(String property3) {
        this.property3 = property3;
    }

    public String getProperty4() {
        return property4;
    }

    public void setProperty4(String property4) {
        this.property4 = property4;
    }

    public String getProperty5() {
        return property5;
    }

    public void setProperty5(String property5) {
        this.property5 = property5;
    }

    public String getProperty6() {
        return property6;
    }

    public void setProperty6(String property6) {
        this.property6 = property6;
    }

    public String getProperty7() {
        return property7;
    }

    public void setProperty7(String property7) {
        this.property7 = property7;
    }

    public String getProperty8() {
        return property8;
    }

    public void setProperty8(String property8) {
        this.property8 = property8;
    }

    public String getProperty9() {
        return property9;
    }

    public void setProperty9(String property9) {
        this.property9 = property9;
    }

    public static ManyProperties createAllison() {
        ManyProperties allison = new ManyProperties();
        allison.id = "allis";
        allison.name = "Allison";
        allison.property1 = "value1";
        allison.property2 = "value2";
        allison.property3 = "value3";
        allison.property4 = "value4";
        allison.property5 = "value5";
        allison.property6 = "value6";
        allison.property7 = "value7";
        allison.property8 = "value8";
        allison.property9 = "value9";

        return allison;
    }

}
