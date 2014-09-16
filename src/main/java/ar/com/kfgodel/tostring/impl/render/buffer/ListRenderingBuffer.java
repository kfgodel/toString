package ar.com.kfgodel.tostring.impl.render.buffer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This type implements a RenderingBuffer with a list of parts
 * Created by kfgodel on 14/09/14.
 */
public class ListRenderingBuffer implements RenderingBuffer {

    private List<Object> parts;

    @Override
    public String printOnString() {
        List<String> stringSegments = flattenParts();
        StringBuilder builder = new StringBuilder();
        for (String stringSegment : stringSegments) {
            builder.append(stringSegment);
        }
        return builder.toString();
    }

    @Override
    public void addPart(Object part) {
        if(part instanceof DelayedPartitionable){
            // We delay its partition until rendering time
            parts.add(part);
        } else if (part instanceof RenderingBuffer){
            // We flatten other buffer into us
            List<Object> otherBuffer = ((RenderingBuffer) part).getParts();
            otherBuffer.forEach(this::addPart);
        } else{
            // Everything else should be a String
            String stringed = String.valueOf(part);
            parts.add(stringed);
        }
    }

    @Override
    public int getEstimatedSize() {
        int totalSize = 0;
        for (Object part : parts) {
            int partSize;
            if(part instanceof CompositeRenderPart){
                partSize = ((CompositeRenderPart) part).getEstimatedSize();
            }else{
                partSize = ((String)part).length();
            }
            totalSize += partSize;
        }
        return totalSize;
    }

    /**
     * Creates a list of strings flattening any part that is composed of other parts
     * @return The list of strings ready to print
     */
    private List<String> flattenParts() {
        List<String> flattened = new ArrayList<>();
        LinkedList<Object> pendingParts = new LinkedList<Object>(this.parts);
        while(!pendingParts.isEmpty()){
            Object currentPart = pendingParts.pop();
            if(currentPart instanceof CompositeRenderPart){
                // We flatten composites into parts
                List<Object> subParts = ((CompositeRenderPart) currentPart).getParts();
                pendingParts.addAll(0, subParts);
                continue;
            }
            // Everything else should already be a String
            String stringValue = (String) currentPart;
            flattened.add(stringValue);
        }
        return flattened;
    }

    public static ListRenderingBuffer create() {
        ListRenderingBuffer buffer = new ListRenderingBuffer();
        buffer.parts = new ArrayList<>();
        return buffer;
    }

    @Override
    public List<Object> getParts() {
        return this.parts;
    }
}
