package com.gabriaum.skills.util.inventory.controller.list;

import com.gabriaum.skills.util.inventory.configuration.layout.Layout;
import com.gabriaum.skills.util.inventory.configuration.layout.frame.Frame;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LayoutController {

    private final Map<Integer, Layout> layoutMap = new LinkedHashMap<>();

    public LayoutController(String[] layout) {
        int slot = 0;

        for (String l : layout) {
            for (char c : l.toCharArray()) {
                switch (c) {
                    case 'X':
                        put(slot, new Layout(slot, Frame.SKIP));
                        break;

                    case 'O':
                        put(slot, new Layout(slot, Frame.ITEM));
                        break;

                    case '<':
                        put(slot, new Layout(slot, Frame.PREVIOUS_PAGE));
                        break;

                    case '>':
                        put(slot, new Layout(slot, Frame.NEXT_PAGE));
                        break;
                }

                slot++;
            }
        }
    }

    public List<Layout> getItemsLayouts(int startIndex, int pageEnd) {
        return layoutMap.values().stream().filter(layout -> layout.getFrame().equals(Frame.ITEM))
                .filter(layout -> layout.getSlot() >= startIndex && layout.getSlot() <= pageEnd).collect(Collectors.toList());
    }

    public void put(int slot, Layout layout) {
        layoutMap.put(slot, layout);
    }

    public Layout get(int slot) {
        return layoutMap.get(slot);
    }

    public int getItemCount() {
        return layoutMap.values().stream().filter(layout -> layout.getFrame().equals(Frame.ITEM)).toArray().length;
    }

    public int getFirstSlot(Frame frame) {
       return layoutMap.values().stream().filter(layout -> layout.getFrame().equals(frame)).findFirst().get().getSlot();
    }

    public Iterator<Layout> iterator() {
        return layoutMap.values().iterator();
    }
}
