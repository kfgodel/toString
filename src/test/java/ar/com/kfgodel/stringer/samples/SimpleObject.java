package ar.com.kfgodel.stringer.samples;

import ar.com.kfgodel.stringer.api.Stringer;

/**
 * This class represents a simple object with primitive properties
 * Date: 17/03/18 - 22:16
 */
public class SimpleObject {

  private Long id;
  private String name;
  private String telephone;
  private int age;
  private Stringer stringer;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTelephone() {
    return telephone;
  }

  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

}
