package ar.com.kfgodel.tostring.testobjects;

/**
 * This is a test type whose fields are ordered
 * Created by kfgodel on 10/09/14.
 */
public class OrderedFieldsObject {

    private Integer first = 1;
    private Integer middle  = 2;
    private Integer last  = 3;

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getMiddle() {
        return middle;
    }

    public void setMiddle(Integer middle) {
        this.middle = middle;
    }

    public Integer getLast() {
        return last;
    }

    public void setLast(Integer last) {
        this.last = last;
    }
}
