/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zachtronics.yodastories.tile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Mike
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "data")
public class TileData {

    @XmlValue
    private String base64Bytes;

    public TileData() {
        super();
    }

    public TileData(String base64Bytes) {
        this();
        
        this.base64Bytes = base64Bytes;
    }

    public String getBase64Bytes() {
        return base64Bytes;
    }

    public void setBase64Bytes(String base64Bytes) {
        this.base64Bytes = base64Bytes;
    }

    @XmlAttribute(name = "encoding")
    public String getEncoding() {
        return "base64";
    }
}
