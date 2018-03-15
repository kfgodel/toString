package ar.com.kfgodel.v1.tostring.testobjects;

/**
 * This type is a test object with basic primitive types
 * Created by kfgodel on 10/09/14.
 */
public class Person {

    private String id;
    private String name;
    private Integer age;
    private Double height;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public static Person createFred(){
        Person fred = new Person();
        fred.age = 42;
        fred.id = "1";
        fred.name = "Fred";
        fred.height = 6.7;
        return fred;
    }

}
