package ar.com.kfgodel.tostring.impl.render.buffer;

import java.util.Arrays;
import java.util.List;

/**
 * This type represents a buffer composed of a single String (for optimization purposes)
 * Created by kfgodel on 16/09/14.
 */
public class SingleStringBuffer implements RenderingBuffer {

    private String content;

    @Override
    public String printOnString() {
        return content;
    }

    @Override
    public void addPart(Object part) {
        throw  new UnsupportedOperationException(this.getClass().getSimpleName() + " doesn't support adding part: " +part );
    }

    @Override
    public List<Object> getParts() {
        return Arrays.asList(this.content);
    }

    @Override
    public int getEstimatedSize() {
        return this.content.length();
    }

    public static SingleStringBuffer create(Object content) {
        SingleStringBuffer buffer = new SingleStringBuffer();
        buffer.content = String.valueOf(content);
        return buffer;
    }

}
