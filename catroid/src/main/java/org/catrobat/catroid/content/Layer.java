package org.catrobat.catroid.content;

import java.io.Serializable;

public class Layer implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int zIndex;
    private boolean visible = true;
    private String shaderName; // optional shader associated with this layer

    public Layer() {
    }

    public Layer(String name, int zIndex) {
        this.name = name;
        this.zIndex = zIndex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZIndex() {
        return zIndex;
    }

    public void setZIndex(int zIndex) {
        this.zIndex = zIndex;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getShaderName() {
        return shaderName;
    }

    public void setShaderName(String shaderName) {
        this.shaderName = shaderName;
    }
}
