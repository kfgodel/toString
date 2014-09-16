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
    private Object lastPart;

    @Override
    public String printOnString() {
        List<CharSequence> stringSegments = flattenParts();
        StringBuilder builder = new StringBuilder();
        for (CharSequence stringSegment : stringSegments) {
            builder.append(stringSegment);
        }
        return builder.toString();
    }

    @Override
    public void addPart(Object part) {
        if(part instanceof DelayedPartitionable){
            // We delay its partition until rendering time
            createPart(part);
        } else if (part instanceof RenderingBuffer){
            // We flatten other buffer into us
            List<Object> otherBuffer = ((RenderingBuffer) part).getParts();
            otherBuffer.forEach(this::addPart);
        } else{
            // Everything else should be a String
            String stringed = String.valueOf(part);
            this.compactWithLastPart(stringed);
        }
    }

    /**
     * We try to append the given string to the last segment, thus reducing the number of parts in this buffer.
     * If the last segment is a String or StringBuilder we can append this new sstring in a single part
     *
     * @param addedText The added text
     */
    private void compactWithLastPart(String addedText) {
        Object lastPart = getLastPart();
        if (lastPart instanceof StringBuilder) {
            // We can append to it
            ((StringBuilder) lastPart).append(addedText);
        } else if (lastPart instanceof String) {
            //We can replace it with a joined builder
            this.joinLastWithBuilder((String) lastPart, addedText);
        } else {
            this.createPart(addedText);
        }
    }

    /**
     * Replaces last part of this buffer with a joined StringBuilder of the two strings
     * @param lastPart Current last part
     * @param addedText The added text
     */
    private void joinLastWithBuilder(String lastPart, String addedText) {
        StringBuilder builder = new StringBuilder();
        builder.append(lastPart);
        builder.append(addedText);
        this.parts.set(this.getLastIndex(), builder);
    }

    /**
     * Creates a new part for the given object as is
     * @param part The object to be considered a new part of this buffer
     */
    private void createPart(Object part) {
        parts.add(part);
    }

    @Override
    public int getEstimatedSize() {
        int totalSize = 0;
        for (Object part : parts) {
            int partSize;
            if(part instanceof CompositeRenderPart){
                partSize = ((DelayedPartitionable) part).getEstimatedSize();
            }else{
                partSize = ((CharSequence)part).length();
            }
            totalSize += partSize;
        }
        return totalSize;
    }

    /**
     * Creates a list of strings flattening any part that is composed of other parts
     * @return The list of strings ready to print
     */
    private List<CharSequence> flattenParts() {
        List<CharSequence> flattened = new ArrayList<>();
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
            CharSequence stringValue = (CharSequence) currentPart;
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

    /**
     * Returns the last part of this buffer or null if none available
     * @return The last part or null
     */
    public Object getLastPart() {
        int lastIndex = getLastIndex();
        if(lastIndex < 0){
            // There's no last part yet
            return null;
        }
        Object lastPart = this.parts.get(lastIndex);
        return lastPart;
    }

    private int getLastIndex() {
        return this.parts.size() - 1;
    }
}
